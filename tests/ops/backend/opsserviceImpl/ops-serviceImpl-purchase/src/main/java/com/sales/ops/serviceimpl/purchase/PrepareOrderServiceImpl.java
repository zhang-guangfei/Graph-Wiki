package com.sales.ops.serviceimpl.purchase;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.easyexcel.EasyExcelUtil;
import com.sales.ops.common.easyexcel.handler.Custemhandler;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.CommonMqpper;
import com.sales.ops.db.extdao.OpsPrepareOrderDao;
import com.sales.ops.dto.ips.*;
import com.sales.ops.dto.ips.zb.*;
import com.sales.ops.dto.po.core.PoCalTransAndExportDateResultDto;
import com.sales.ops.dto.po.core.TransAndExportDateParamDto;
import com.sales.ops.dto.prepareOrder.*;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WMPurchaseTagEnum;
import com.sales.ops.enums.prepareOrder.IpsSendDeliveryInfoToOpsStatusEnum;
import com.sales.ops.enums.prepareOrder.PrepareStatusEnum;
import com.sales.ops.feign.DeliveryPoFeignApi;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.core.TransService;
import com.sales.ops.service.impinvoice.ImpInvoiceSysnCommonService;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.po.PoModelService;
import com.sales.ops.service.purchase.PrepareOrderService;
import com.sales.ops.service.purchase.PurchaseCreateService;
import com.sales.ops.serviceimpl.ips.IPSOutBoundTypeEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.PurchaseTypeEnum;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.prestock.RejectPrepareOrderUpStockStatusDto;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.PreStockFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;

@Slf4j
@Service
public class PrepareOrderServiceImpl implements PrepareOrderService {


    @Resource
    private InventorySupplierMapper inventorySupplierMapper;

    @Resource
    private OpsPrepareOrderMapper opsPrepareOrderMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private OpsWarehouseMapper opsWarehouseMapper;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;

    @Resource
    private DeliveryPoFeignApi deliveryPoFeignApi;

    @Resource
    private PreStockFeignApi preStockFeignApi;

    @Resource
    private OpsPsiParsefailLogMapper opsPsiParsefailLogMapper;

    @Resource
    private OpsPrepareOrderAccountMapper opsPrepareOrderAccountMapper;

    @Resource
    private OpsPrepareOrderReconciliationMapper opsPrepareOrderReconciliationMapper;

    @Resource
    private PoModelService poModelService;

    @Resource
    private OpsPrepareOrderReserveInfoMapper opsPrepareOrderReserveInfoMapper;

    @Resource
    private OpsPrepareOrderLiquidationMapper opsPrepareOrderLiquidationMapper;

    @Resource
    private TransService transService;

    @Resource
    private OpsPrepareOrderDao opsPrepareOrderDao;

    @Resource
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;

    @Resource
    private CommonMqpper commonMqpper;

    @Resource
    private PurchaseCreateService purchaseCreateService;

    @Resource
    private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

    @Resource
    private ImpInvoiceSysnCommonService impInvoiceSysnCommonService;


    @Override
    public ResultVo<Map<String, Boolean>> prepareOrderVerify(List<String> modelNos) {

        if (CollectionUtils.isEmpty(modelNos)) {
            return ResultVo.failure("入参型号列表为空");
        }

        Map<String , Boolean> map = new HashMap<>();

        List<String> supplyIds = new ArrayList<>();
        /**
         * 获取中国供应商
         */
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("6001");
        if (!dataCodes.isSuccess()) {
            return ResultVo.failure("获取中国供应商失败.");
        }
        List<DataCodeVO> dataCodeVOS = dataCodes.getData();
        for (DataCodeVO dataCodeVO : dataCodeVOS) {
            if ("1".equals(dataCodeVO.getExtNote1())) {
                supplyIds.add(dataCodeVO.getCode());
            }
        }

        for (String modelNo : modelNos) {

            InventorySupplierExample example = new InventorySupplierExample();
            example.createCriteria().andModelnoEqualTo(modelNo).andSupplieridIn(supplyIds).andBinflagEqualTo(1);
            List<InventorySupplier> delivery = inventorySupplierMapper.selectByExample(example);

            if(CollectionUtils.isNotEmpty(delivery)) {
                map.put(modelNo, true);
            } else {
                map.put(modelNo, false);
            }
        }

        return ResultVo.success(map);
    }

    @Override
    public ResultVo<String> prepareOrderCreate(List<OpsPrepareOrder> opsPrepareOrders) {

        if (CollectionUtils.isEmpty(opsPrepareOrders)) {
            return ResultVo.failure("入参为空");
        }

        for (OpsPrepareOrder opsPrepareOrder : opsPrepareOrders) {
            if (opsPrepareOrder.getPreQty() == null) {
                opsPrepareOrder.setPreQty(0);
            }
            if (opsPrepareOrder.getRemainQty() ==  null) {
                opsPrepareOrder.setRemainQty(0);
            }
            opsPrepareOrderMapper.insertSelective(opsPrepareOrder);
        }

        return ResultVo.success("准备订单创建成功");
    }

    @Override
    public ResultVo<String> prepareOrderPreHandle() {
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andStatusEqualTo(PrepareStatusEnum.dcl.getCode()).andDelFlagEqualTo(false);
        List<OpsPrepareOrder> prepareOrders = opsPrepareOrderMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(prepareOrders)) {
            return ResultVo.success("没有需要预处理订单");
        }
        /**
         *  准备订单请购预处理
         */
        for (OpsPrepareOrder prepareOrder : prepareOrders) {
            try {
                TransAndExportDateParamDto param = new TransAndExportDateParamDto();
                param.setSupplierId(prepareOrder.getSupplierCode());
                param.setModelNo(prepareOrder.getModelNo());
                param.setOrderQty(prepareOrder.getApplyQty());
                param.setOrdType("21"); // 先行在库
                param.setPreTransId(prepareOrder.getTransType());
                param.setHopeDeliveryDate(prepareOrder.getDlvDate());
                param.setPurchasetype("B"); // 先行在库订单
                param.setDeptNo(prepareOrder.getApplyDeptNo());

                if (StringUtils.isBlank(prepareOrder.getWarehouseCode())) {
                    OpsPrepareOrderExample e1 = new OpsPrepareOrderExample();
                    e1.createCriteria().andIdEqualTo(prepareOrder.getId());

                    OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
                    opsPrepareOrder.setStatus(PrepareStatusEnum.prepareFail.getCode());
                    opsPrepareOrder.setRemark("备库仓为空");
                    opsPrepareOrder.setUpdateTime(new Date());
                    opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, e1);
                    continue;
                }

                OpsWarehouse warehouse = opsWarehouseMapper.selectByPrimaryKey(prepareOrder.getWarehouseCode());

                if (warehouse == null) {
                    OpsPrepareOrderExample e1 = new OpsPrepareOrderExample();
                    e1.createCriteria().andIdEqualTo(prepareOrder.getId());

                    OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
                    opsPrepareOrder.setStatus(PrepareStatusEnum.prepareFail.getCode());
                    opsPrepareOrder.setRemark("备库仓无效");
                    opsPrepareOrder.setUpdateTime(new Date());
                    opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, e1);
                    continue;
                }

                /**
                 * 分库的采购仓是它的上级物流中心 请购仓是它自己
                 */
                if ("MASTER".equals(warehouse.getWarehouseType())) {
                    param.setpWarehouse(prepareOrder.getWarehouseCode());
                } else if ("SUB".equals(warehouse.getWarehouseType())){
                    param.setpWarehouse(warehouse.getParentCode());
                } else {
                    param.setpWarehouse(prepareOrder.getWarehouseCode());
                }
                param.setrWarehouse(prepareOrder.getWarehouseCode());

                PoCalTransAndExportDateResultDto calTransIdAndExportDate = transService.getCalTransIdAndExportDate(param);

                OpsPrepareOrderExample prepareOrderExample = new OpsPrepareOrderExample();
                prepareOrderExample.createCriteria().andIdEqualTo(prepareOrder.getId());

                OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
                opsPrepareOrder.setStatus(PrepareStatusEnum.dfs.getCode());
                opsPrepareOrder.setTransType(calTransIdAndExportDate.getCalTransId());
                opsPrepareOrder.setHopeExportDate(calTransIdAndExportDate.getCalDate());
                opsPrepareOrder.setUpdateTime(new Date());
                opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, prepareOrderExample);
            } catch (Exception e) {
                log.error("准备订单预处理出错: {}", JSONObject.toJSONString(prepareOrder), e);
                OpsPrepareOrderExample e1 = new OpsPrepareOrderExample();
                e1.createCriteria().andIdEqualTo(prepareOrder.getId());
                OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
                opsPrepareOrder.setStatus(PrepareStatusEnum.prepareFail.getCode());
                opsPrepareOrder.setRemark(e.getMessage());
                opsPrepareOrder.setUpdateTime(new Date());
                opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, e1);
            }
        }
        return ResultVo.success("准备订单预处理成功");
    }


    /**
     * 向PSI推送准备订单
     */
    @Override
    public ResultVo<String> pushPrepareOrderToPSI() {

        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andStatusEqualTo(PrepareStatusEnum.dfs.getCode()).andDelFlagEqualTo(false);

        List<OpsPrepareOrder> prepareOrders = opsPrepareOrderMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(prepareOrders)) {
            return ResultVo.success("无推送数据");
        }

        int batchSize = prepareOrders.size();

        String dateWithyyyymmddhhmmss = DateUtil.getDateWithyyyymmddhhmmss(new Date());


        /**
         * 北京仓对应的收货人信息
         */
        OpsWarehouseExample warehouseExample = new OpsWarehouseExample();
        warehouseExample.createCriteria().andWarehouseCodeEqualTo("KBJ").andDelflagEqualTo( false);
        List<OpsWarehouse> warehouseList = opsWarehouseMapper.selectByExample(warehouseExample);

        /**
         * 不能用批量插入，不然不知道哪个推送失败 无法控制错误数据以及准备订单的状态
         */
        for (OpsPrepareOrder prepareOrder : prepareOrders) {
            List<IpsReceiveOrderAllOriginalInfoDto> ipsList = new ArrayList<>();
            IpsReceiveOrderAllOriginalInfoDto ipsReceiveOrderAllOriginalInfoDto = conventToIpsReceiveOrderAllOriginalInfoDto(prepareOrder, dateWithyyyymmddhhmmss, batchSize,warehouseList.get(0));
            ipsList.add(ipsReceiveOrderAllOriginalInfoDto);
            CommonResult<String> stringCommonResult = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsList);
            if (!stringCommonResult.isSuccess()) {
                insertPsiParseFailData(prepareOrder.getId(), JSONObject.toJSONString(ipsReceiveOrderAllOriginalInfoDto), "OPS", "准备订单发送IPS失败:"+stringCommonResult.getMessage());
                OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
                opsPrepareOrder.setId(prepareOrder.getId());
                opsPrepareOrder.setStatus(PrepareStatusEnum.sendFail.getCode());
                opsPrepareOrder.setRemark("准备订单发送IPS失败:"+stringCommonResult.getMessage());
                opsPrepareOrderMapper.updateByPrimaryKeySelective(opsPrepareOrder);
                continue;
            }
            OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
            opsPrepareOrder.setId(prepareOrder.getId());
            opsPrepareOrder.setStatus(PrepareStatusEnum.yfs.getCode());
            opsPrepareOrder.setSendTime(new Date());
            opsPrepareOrderMapper.updateByPrimaryKeySelective(opsPrepareOrder);
        }
        return ResultVo.success("推送准备订单到IPS系统成功.");
    }

    /**
     * 从PSI拉取返信信息
     */
    @Override
    public ResultVo<String> pullPrepareOrderDeliveryInfoFromPsi() {

        ResultVo<List<IpsSendDeliveryInfoToOpsDto>> listResultVo = deliveryPoFeignApi.queryIpsSendDeliveryInfoToOpsList();
        if (!listResultVo.isSuccess()) {
            return ResultVo.failure("获取制造推送的准备订单信息失败.");
        }
        List<IpsSendDeliveryInfoToOpsDto> data = listResultVo.getData();
        if(CollectionUtils.isEmpty( data)) {
            return ResultVo.success("暂无可拉取的准备订单信息");
        }
        for (IpsSendDeliveryInfoToOpsDto dto : data) {
            if (StringUtils.isNotBlank(dto.getFactSupplier())) {
                ResultVo<String> supplierid = impInvoiceSysnCommonService.getOpsSupplierByConfig(dto.getFactSupplier(), "supplierid");
                if (supplierid.isSuccess()) {
                    dto.setFactSupplier(supplierid.getData());
                }
            }
            try {
                // 拒单
                if (IpsSendDeliveryInfoToOpsStatusEnum.P.getCode().equals(dto.getIdentificationCode()) || IpsSendDeliveryInfoToOpsStatusEnum.D.getCode().equals(dto.getIdentificationCode())) {
                    insertPrepareOrderWithPorD(dto);
                }

                // PSI已接单 -> OPS 已接单
                if (IpsSendDeliveryInfoToOpsStatusEnum.F.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithF(dto);
                }

                // PSI已分配 ->  OPS 备库中
                if (IpsSendDeliveryInfoToOpsStatusEnum.I.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithI(dto);
                }

                // PSI N已返信 -> OPS 备库中
                if (IpsSendDeliveryInfoToOpsStatusEnum.N.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithN(dto);
                }

                // PU已备库 -> ops备库中
                if (IpsSendDeliveryInfoToOpsStatusEnum.PU.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithPU(dto);
                }


                // PA待清算,清算中
                if(IpsSendDeliveryInfoToOpsStatusEnum.PA.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithPA(dto);
                }

                // PE 已清算
                if(IpsSendDeliveryInfoToOpsStatusEnum.PE.getCode().equals(dto.getIdentificationCode())) {
                    upPrepareOrderWithPE(dto);
                }
            } catch (Exception e) {
                insertPsiParseFailData(dto.getId(), JSONObject.toJSONString(dto), "ips_send_delivery_info_to_ops", "解析失败:"+e.getMessage());
            }

        }
        return ResultVo.success("拉取完毕");
    }

    @Override
    public void insertPsiParseFailData(Long id, String content, String sourceType,String remark) {
        OpsPsiParsefailLog opsPsiParsefailLog = new OpsPsiParsefailLog();
        opsPsiParsefailLog.setSourceId(id.toString());
        opsPsiParsefailLog.setSourceType(sourceType);
        opsPsiParsefailLog.setContent(content);
        opsPsiParsefailLog.setCreateTime(new Date());
        opsPsiParsefailLog.setRemark(remark);
        opsPsiParsefailLog.setHandleStatus(0);
        opsPsiParsefailLogMapper.insertSelective(opsPsiParsefailLog);
    }

    @Override
    public ResultVo<OpsPrepareOrderVO> getAvailablePrepareOrderList(CanUsePrepareOrderParam  param) {

        if (param == null) {
            return ResultVo.failure("入参为空");
        }

        if (param.getQuantity() <= 0) {
            return ResultVo.failure("请求数量必须大于 0");
        }
        /**
         * 顾客在库PPL
         * 顾客在库项目
         * 战略在库PJ
         * 顾客在库通用
         * 战略在库集团
         * 战略在库行业
         * 战略在库产品
         */

        CanUsePrepareOrderParam pp = new CanUsePrepareOrderParam();
        pp.setModelNo(param.getModelNo());
        List<OpsPrepareOrderVO> availablePrepareOrderList = opsPrepareOrderDao.getAvailablePrepareOrderList(pp);

        if(CollectionUtils.isEmpty(availablePrepareOrderList)) {
            return ResultVo.success(null);
        }
        String insufficientQtyMsg = "";
        for (OpsPrepareOrderVO item : availablePrepareOrderList) {
            boolean isMatch = false;

            // 1. 顾客在库 PPL
            if (isCustomerInLibraryPPLMatch(param, item)) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }
            }

            // 2. 顾客在库项目
            if (!isMatch && isCustomerInLibraryProjectMatch(param, item)) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }

            }

            // 3. 战略在库 PJ
            if (!isMatch && isStrategicProjectMatch(param, item)) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }

            }

            // 4. 顾客在库通用
            if (!isMatch && isCustomerInLibraryCommonMatch(param, item)) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }

            }

            // 5. 战略在库集团或行业
            if (!isMatch && isStrategicGroupOrIndustryMatch(param, item) && (item.getInventoryTypeCode().equals("ZL-HY") || item.getInventoryTypeCode().equals("ZL-JT"))) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }
            }

            // 6. 战略在库产品（无特殊条件）
            if (!isMatch && item.getInventoryTypeCode().equals("ZL-CP")) {
                if (!hasSufficientQuantity(item, param.getQuantity())) {
                    if(StringUtils.isBlank(insufficientQtyMsg)) {
                        insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                    }
                } else {
                    isMatch = true;
                }
            }
            if (isMatch) {
                return ResultVo.success(item);
            }
        }
        if (StringUtils.isNotBlank(param.getEndUserNo())) {
            for (OpsPrepareOrderVO item : availablePrepareOrderList) {
                if (StringUtils.isNotBlank(item.getAvailableCustomers()) && item.getAvailableCustomers().contains(param.getEndUserNo())) {
                    if (!hasSufficientQuantity(item, param.getQuantity())) {
                        if(StringUtils.isBlank(insufficientQtyMsg)) {
                            insufficientQtyMsg = item.getOrderFno()+"残数不足: "+(item.getRemainQty() - item.getPreQty());
                        }
                    } else {
                        return ResultVo.success(item);
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(insufficientQtyMsg)) {
            return ResultVo.failure(insufficientQtyMsg);
        }
        return ResultVo.success(null);
    }

    private boolean isCustomerInLibraryPPLMatch(CanUsePrepareOrderParam param, OpsPrepareOrderVO item) {
        return StringUtils.isNotBlank(param.getEndUserNo())
                && StringUtils.isNotBlank(item.getAvailableCustomers())
                && item.getAvailableCustomers().contains(param.getEndUserNo())
                && StringUtils.isNotBlank(param.getPpl())
                && StringUtils.isNotBlank(item.getPpl())
                && item.getPpl().equals(param.getPpl())
                && item.getInventoryTypeCode().equals("GK-PPL");
    }

    private boolean isCustomerInLibraryProjectMatch(CanUsePrepareOrderParam param, OpsPrepareOrderVO item) {
        return StringUtils.isNotBlank(param.getEndUserNo())
                && StringUtils.isNotBlank(item.getAvailableCustomers())
                && item.getAvailableCustomers().contains(param.getEndUserNo())
                && StringUtils.isNotBlank(param.getPj())
                && StringUtils.isNotBlank(item.getProjectCode())
                && item.getProjectCode().equals(param.getPj())
                && item.getInventoryTypeCode().equals("GK-PJ");
    }

    private boolean isStrategicProjectMatch(CanUsePrepareOrderParam param, OpsPrepareOrderVO item) {
        return StringUtils.isNotBlank(param.getPj())
                && StringUtils.isNotBlank(item.getProjectCode())
                && item.getProjectCode().equals(param.getPj())
                && item.getInventoryTypeCode().equals("ZL-PJ");
    }

    private boolean isCustomerInLibraryCommonMatch(CanUsePrepareOrderParam param, OpsPrepareOrderVO item) {
        return StringUtils.isNotBlank(param.getEndUserNo())
                && StringUtils.isNotBlank(item.getAvailableCustomers())
                && item.getAvailableCustomers().contains(param.getEndUserNo())
                && item.getInventoryTypeCode().equals("GK-TY");
    }

    private boolean isStrategicGroupOrIndustryMatch(CanUsePrepareOrderParam param, OpsPrepareOrderVO item) {

        if (StringUtils.isBlank(param.getGroupCustomerNo()) || StringUtils.isBlank(item.getGroupCustomerNo())) {
            return false;
        }

        List<String> paramGroupList = Arrays.asList(param.getGroupCustomerNo().split(","));
        List<String> itemGroupList = Arrays.asList(item.getGroupCustomerNo().split(","));

        for (String paramGroup : paramGroupList) {
            if (itemGroupList.contains(paramGroup)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSufficientQuantity(OpsPrepareOrderVO item, int requiredQty) {
        return (item.getRemainQty() - item.getPreQty()) >= requiredQty;
    }

    @Override
    public ResultVo<PageInfo<OpsPrepareOrderVO>> queryPrepareOrderList(PrepareOrderQueryParam param) {

        if (param == null) {
            return ResultVo.failure("参数错误,入参不可为空");
        }

        PageInfo<OpsPrepareOrderVO> opsPrepareOrderVOPageInfo = PageHelper.startPage(param.getPageNum(), param.getPageSize()).doSelectPageInfo(() -> opsPrepareOrderDao.queryPrepareOrderList(param));

        if (opsPrepareOrderVOPageInfo == null) {
            return ResultVo.success(null);
        }

        if (CollectionUtils.isNotEmpty(opsPrepareOrderVOPageInfo.getList())) {
            List<OpsPrepareOrderVO> opsPrepareOrderVOS = conventPrepareOrderList(opsPrepareOrderVOPageInfo.getList());
            opsPrepareOrderVOPageInfo.setList(opsPrepareOrderVOS);
        }

        return ResultVo.success(opsPrepareOrderVOPageInfo);
    }

    @Override
    public void exportPrepareOrder(PrepareOrderQueryParam param, HttpServletResponse response) {

        List<OpsPrepareOrderVO> opsPrepareOrderVOS = opsPrepareOrderDao.queryPrepareOrderList(param);

        if (CollectionUtils.isEmpty(opsPrepareOrderVOS)) {
            return;
        }
        conventPrepareOrderList(opsPrepareOrderVOS);
        try {
            String fileName = URLEncoder.encode("dataList", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            EasyExcel.write(response.getOutputStream(), OpsPrepareOrderVO.class)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(EasyExcelUtil.getStyleStrategy())
                    .sheet("Sheet1")
                    .doWrite(opsPrepareOrderVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    @Override
    public ResultVo<String> editPrepareOrderAvailableUser(EditPrepareOrderAvailableUserDto editDto) {
        if (editDto == null || StringUtils.isBlank(editDto.getOrderFno())) {
            return ResultVo.failure("参数错误,入参不可为空");
        }
        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setAvailableCustomers(editDto.getAvailableCustomers());
        opsPrepareOrder.setUpdateUser(editDto.getOptUser());
        opsPrepareOrder.setUpdateTime(new Date());

        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderFnoEqualTo(editDto.getOrderFno()).andDelFlagEqualTo( false);

        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

        return ResultVo.success("修改成功");
    }

    @Override
    public ResultVo<List<OpsPrepareOrderBomDetailDto>> getPrepareOrderBomDetail(String orderFNo) {
        if (StringUtils.isBlank(orderFNo)) {
            return ResultVo.failure("参数错误,入参不可为空");
        }
        OpsPrepareOrderReserveInfoExample example = new OpsPrepareOrderReserveInfoExample();
        example.createCriteria().andOrderFnoEqualTo(orderFNo).andDelFlagEqualTo( false);
        List<OpsPrepareOrderReserveInfo> opsPrepareOrderReserveInfos = opsPrepareOrderReserveInfoMapper.selectByExample(example);
        List<OpsPrepareOrderBomDetailDto> opsPrepareOrderBomDetailDtos = BeanCopyUtil.copyList(opsPrepareOrderReserveInfos, OpsPrepareOrderBomDetailDto.class);
        return ResultVo.success(opsPrepareOrderBomDetailDtos);
    }

    @Override
    public ResultVo<List<OpsPrepareOrderReconciliationDto>> getPrepareOrderVerifyInfo(String orderFNo) {
        if (StringUtils.isBlank(orderFNo)) {
            return ResultVo.failure("参数错误,入参不可为空");
        }

        OpsPrepareOrderReconciliationExample example = new OpsPrepareOrderReconciliationExample();
        example.createCriteria().andOrderFnoEqualTo(orderFNo).andDelFlagEqualTo( false).andQuantityGreaterThan(0);
        List<OpsPrepareOrderReconciliation> opsPrepareOrderReconciliations = opsPrepareOrderReconciliationMapper.selectByExample(example);

        List<OpsPrepareOrderReconciliationDto> opsPrepareOrderReconciliationDtos = BeanCopyUtil.copyList(opsPrepareOrderReconciliations, OpsPrepareOrderReconciliationDto.class);

        return ResultVo.success(opsPrepareOrderReconciliationDtos);
    }

    @Override
    public ResultVo<List<OpsPrepareOrderAccount>> getPrepareOrderAccountInfo(String orderFNo) {

        OpsPrepareOrderAccountExample example = new OpsPrepareOrderAccountExample();
        example.createCriteria().andOrderFnoEqualTo(orderFNo).andDelFlagEqualTo( false);
        List<OpsPrepareOrderAccount> opsPrepareOrderAccounts = opsPrepareOrderAccountMapper.selectByExample(example);

        return ResultVo.success(opsPrepareOrderAccounts);
    }

    @Override
    public ResultVo<List<OpsPrepareOrderLiquidationDto>> getPrepareOrderLiquidationInfo(String orderFNo) {

        OpsPrepareOrderLiquidationExample example = new OpsPrepareOrderLiquidationExample();
        example.createCriteria().andOrderFnoEqualTo(orderFNo).andDelFlagEqualTo( false);
        List<OpsPrepareOrderLiquidation> opsPrepareOrderLiquidations = opsPrepareOrderLiquidationMapper.selectByExample(example);
        List<OpsPrepareOrderLiquidationDto> opsPrepareOrderLiquidationDtos = BeanCopyUtil.copyList(opsPrepareOrderLiquidations, OpsPrepareOrderLiquidationDto.class);
        if (CollectionUtils.isNotEmpty(opsPrepareOrderLiquidationDtos)) {
            for(OpsPrepareOrderLiquidationDto item : opsPrepareOrderLiquidationDtos) {
                if(StringUtils.isNotBlank(item.getHandleWay())) {
                    item.setHandleWayName(HandleWayEnum.getCodeNameByCode(item.getHandleWay()));
                } else {
                    item.setHandleWayName(item.getHandleWay());
                }
            }
        }
        return ResultVo.success(opsPrepareOrderLiquidationDtos);
    }

    @Override
    public ResultVo<OpsPrepareOrder> getPrepareOrderInfo(String orderFno) {
        /**
         * 获取可以转订的准备订单
         * 1. 获取该准备订单的剩余数量和预占数量
         *    剩余数量 - 预占数量 > 0
         *  2. 状态为备库中 且 可被下游使用
         * */
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderFnoEqualTo(orderFno).andDelFlagEqualTo( false).andRemainQtyGreaterThan(0);
        List<OpsPrepareOrder> opsPrepareOrders = opsPrepareOrderMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(opsPrepareOrders)) {
            OpsPrepareOrder opsPrepareOrder = opsPrepareOrders.get(0);
            if (opsPrepareOrder.getRemainQty() - opsPrepareOrder.getPreQty() > 0 &&
                    opsPrepareOrder.getStatus() == PrepareStatusEnum.bkz.getCode() && opsPrepareOrder.getCanUseFlag()) {
                return ResultVo.success(opsPrepareOrders.get(0));
            }
        }
        return ResultVo.failure(orderFno+"未找到可以转订的准备订单");
    }

    @Override
    public ResultVo<String> prepareOrderTransfer(PrepareOrderTransferDto dto) {

        if (dto == null || StringUtils.isBlank(dto.getDlvDate())) {
            return ResultVo.failure("交货期不可为空");
        }

        ResultVo<OpsPrepareOrder> prepareOrderInfo = getPrepareOrderInfo(dto.getOrderFno());

        if (!prepareOrderInfo.isSuccess()) {
            return ResultVo.failure(prepareOrderInfo.getMessage());
        }

        OpsPrepareOrder opsPrepareOrder = prepareOrderInfo.getData();

        RequestPurchaseInfoDto purchase = new RequestPurchaseInfoDto();

        // 生成采购单号
        ResultVo<String> generatorResult = commonServiceFeignApi.generatorBillNo(opsPrepareOrder.getWarehouseCode().startsWith("W") ? "11" : "3");
        log.info("先行在库-生成采购单号 orderNo = {}", generatorResult);
        if (!generatorResult.isSuccess() || StringUtils.isBlank(generatorResult.getData())) {
            return ResultVo.failure("生成采购订单失败,生成订单号异常: " + generatorResult.getMessage());
        }
        // 准备订单号
        purchase.setPrepareOrderNo(opsPrepareOrder.getOrderFno());
        purchase.setOrderno(generatorResult.getData());
        purchase.setItemno(1);
        purchase.setModelno(opsPrepareOrder.getModelNo());
        purchase.setQuantity(dto.getQty());

        ResultVo<String> deptNoByHRSalesDeptNo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(opsPrepareOrder.getApplyDeptNo());
        if(deptNoByHRSalesDeptNo.isSuccess() && deptNoByHRSalesDeptNo.getData() != null) {
            purchase.setDeptno(deptNoByHRSalesDeptNo.getData());
        }
        purchase.setApplyDeptNo(opsPrepareOrder.getApplyDeptNo());

        Date date = DateUtil.stringToDate(dto.getDlvDate());

        purchase.setHopedeliverydate(date); // 期望货期
        purchase.setHopeexportdate(date);
        purchase.setRequesttime(new Date()); // 请购时间

        OpsInventoryPropertyExample inventoryPropertyExample = new OpsInventoryPropertyExample();
        inventoryPropertyExample.createCriteria().andInventoryPropertyIdEqualTo(opsPrepareOrder.getInventoryPropertyId()).andDelflagEqualTo(0);
        List<OpsInventoryProperty> opsInventoryProperties = opsInventoryPropertyMapper.selectByExample(inventoryPropertyExample);
        if (CollectionUtils.isEmpty(opsInventoryProperties)) {
            return ResultVo.failure("未找到对应的库存属性");
        }
        OpsInventoryProperty opsInventoryProperty = opsInventoryProperties.get(0);
        purchase.setInventorytypecode(opsInventoryProperty.getInventoryTypeCode());
        purchase.setInventorypropertyid(opsInventoryProperty.getInventoryPropertyId());

        // 查找先行在库申请信息
        PrestockApplyDto prestockApplyDto = commonMqpper.queryPrestockApply(opsPrepareOrder.getApplyId());
        if (prestockApplyDto == null) {
            return ResultVo.failure("未找到对应的先行在库申请信息");
        }
        purchase.setCustomerno(Optional.ofNullable(prestockApplyDto.getCustomerNo()).orElse(""));
        purchase.setUserno(prestockApplyDto.getUserNo());
        purchase.setPpl(opsInventoryProperty.getPpl());
        purchase.setProjectcode(opsInventoryProperty.getProjectCode());
        purchase.setGroupcustomerno(opsInventoryProperty.getGroupCustomerNo());
        purchase.setTranstype(opsPrepareOrder.getTransType());
        purchase.setOrdtype("21");
        purchase.setPurchasetype(PurchaseTypeEnum.PRE.getCode());
        purchase.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
        purchase.setRequestwarehouseid(opsPrepareOrder.getWarehouseCode()); // 请购仓库

        ResultVo<Boolean> masterWarehouse = commonServiceFeignApi.isMasterWarehouse(prestockApplyDto.getWarehouseCode());
        if (masterWarehouse.isSuccess() && masterWarehouse.getData()) {
            purchase.setPurchasewarehouseid(purchase.getRequestwarehouseid()); // 采购仓库
        } else {
            ResultVo<WarehouseVO> warehouseInfoByCode = commonServiceFeignApi.getWarehouseInfoByCode(purchase.getRequestwarehouseid());
            purchase.setPurchasewarehouseid(warehouseInfoByCode.getData().getParentCode()); // 采购仓库
        }
        purchase.setCorderno(prestockApplyDto.getApplyNo());
        purchase.setSupplierid(opsPrepareOrder.getSupplierCode());
        if (opsPrepareOrder.getRohs() != null && opsPrepareOrder.getRohs().equals("H")) {
            purchase.setProducttag("64");
        }

        RequestPurchaseInfo infoJson = new RequestPurchaseInfo();
        infoJson.setVip(Optional.ofNullable(prestockApplyDto.getVip()).orElse(false));
        purchase.setInfojson(infoJson);
        purchase.setEndUser(Optional.ofNullable(prestockApplyDto.getCustomerNo()).orElse("")); // bug19576 采购发单给老生管对于最终代码的传值，先行在库补库取客户代码

        List<RequestPurchaseInfoDto> list = new ArrayList<>();
        list.add(purchase);

        try {
            purchaseCreateService.addRequestPurchase(list);

            /**
             * 追加准备订单预占
             */
            PrepareOrderPreQtyUpdateDto updateDto = new PrepareOrderPreQtyUpdateDto();
            updateDto.setOrderFno(dto.getOrderFno());
            updateDto.setPreQty(dto.getQty());
            updateDto.setOldPreQty(opsPrepareOrder.getPreQty());
            ResultVo<String> stringResultVo = updatePreQty(updateDto);
            if (!stringResultVo.isSuccess()) {
                return stringResultVo;
            }

            /**
             * 原来的result表数据改为3已转定,新增一条result数据 状态为已处理 处理类型为采购
             */
            dto.setPoNo(purchase.getOrderno()+"-"+purchase.getItemno());
            preStockFeignApi.prepareOrderTransferWithPresotckResult(dto);


        } catch (Exception e) {
            return ResultVo.failure("生成采购订单失败"+e.getMessage());
        }

        return ResultVo.success("准备订单转订成功");
    }

    @Override
    public ResultVo<String> updatePreQty(PrepareOrderPreQtyUpdateDto dto) {
        try {
            OpsPrepareOrderExample example = new OpsPrepareOrderExample();
            example.createCriteria().andOrderFnoEqualTo(dto.getOrderFno()).andDelFlagEqualTo(false);

            OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
            opsPrepareOrder.setUpdateTime(new Date());
            opsPrepareOrder.setPreQty(dto.getOldPreQty()+dto.getPreQty());
            opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);
        } catch (Exception e) {
            return ResultVo.failure("更新预占失败"+e.getMessage());
        }
        return ResultVo.success("更新预占成功");
    }

    @Override
    public ResultVo<String> getDlvDays() {
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "31");
        if (dataTypeCodesInfo.isSuccess()) {
            return ResultVo.success(dataTypeCodesInfo.getData().getExtNote1());
        }
        return ResultVo.failure("未能从字典中获取交货期配置天数");
    }

    @Override
    public ResultVo<String> updateDlvDays(int dlvDays) {
        if (dlvDays <= 0) {
            return ResultVo.failure("更新交货期失败,输入值需要大于0");
        }
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "31");
        if (dataTypeCodesInfo.isSuccess()) {
            dataTypeCodesInfo.getData().setExtNote1(String.valueOf(dlvDays));
            ResultVo<Integer> integerResultVo = dictDataServiceFeignApi.updateDataParam(dataTypeCodesInfo.getData());
            if (integerResultVo.isSuccess()) {
                return ResultVo.success("更新交货期成功");
            }
        }
        return ResultVo.failure("更新交货期失败");
    }

    public List<OpsPrepareOrderVO> conventPrepareOrderList(List<OpsPrepareOrderVO> opsPrepareOrderVOS) {
        for (OpsPrepareOrderVO item : opsPrepareOrderVOS) {
            item.setStatusName(PrepareStatusEnum.getCodeNameByCode(item.getStatus()));
            if(StringUtils.isNotBlank(item.getManuFactSupplierCode())) {
                ResultVo<String> supplierName = commonServiceFeignApi.getSupplierName(item.getManuFactSupplierCode());
                if (supplierName.isSuccess() && StringUtils.isNotBlank(supplierName.getData())) {
                    item.setManuFactSupplierCodeName(supplierName.getData());
                } else {
                    item.setManuFactSupplierCodeName(item.getManuFactSupplierCode());
                }
            }

            if (StringUtils.isNotBlank(item.getSupplierExpinvCode())) {
                item.setSupplierExpinvCode(IPSOutBoundTypeEnum.getNameByCode(item.getSupplierExpinvCode()));
            }

//            if (StringUtils.isNotBlank(item.getApplyPsn())) {
//                ResultVo<String> employeeNameByNo = commonServiceFeignApi.getEmployeeNameByNo(item.getApplyPsn());
//            }
        }
        return opsPrepareOrderVOS;
    }

    public OpsPrepareOrderExample conventPrepareOrderExample(PrepareOrderQueryParam param) {
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        OpsPrepareOrderExample.Criteria criteria = example.createCriteria();

        criteria.andDelFlagEqualTo(false);

        if (StringUtils.isNotBlank(param.getOrderFno())) {
            criteria.andOrderFnoLike(param.getOrderFno());
        }

        if (StringUtils.isNotBlank(param.getManuFactSupplierCode())) {
            criteria.andManuFactSupplierCodeEqualTo(param.getManuFactSupplierCode());
        }

        if (StringUtils.isNotBlank(param.getModelNo())) {
            criteria.andModelNoLike(param.getModelNo());
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }

        if(StringUtils.isNotBlank(param.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(param.getCustomerNo());
        }

        if(StringUtils.isNotBlank(param.getApplyNo())) {
            criteria.andApplyNoEqualTo(param.getApplyNo());
        }

        if (CollectionUtils.isNotEmpty(param.getApplyDeptNos())) {
            criteria.andApplyDeptNoIn(param.getApplyDeptNos());
        }

        if (StringUtils.isNotBlank(param.getStartApplyDate()) && StringUtils.isNotBlank(param.getEndApplyDate())) {
            criteria.andApplyTimeGreaterThan(DateUtil.stringToDate(param.getStartApplyDate()));
            criteria.andApplyTimeLessThan(DateUtil.stringToDate(param.getEndApplyDate()));
        }

        example.setOrderByClause("id desc");

        return example;
    }

    /**
     * 已清算
     */
    private void upPrepareOrderWithPE(IpsSendDeliveryInfoToOpsDto dto) {
        if (StringUtils.isBlank(dto.getAddtionInfo())) {
            return;
        }
        PrepareOrderInfoDto prepareOrderInfoDto = JSONObject.parseObject(dto.getAddtionInfo(), PrepareOrderInfoDto.class);
        if (prepareOrderInfoDto == null) {
            return;
        }
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setLiquidationDate(dto.getSupplierOperateTime());
        opsPrepareOrder.setStatus(PrepareStatusEnum.yqs.getCode());
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
        if (StringUtils.isNotBlank(prepareOrderInfoDto.getCanbeDownstreamUsed()) && "1".equals(prepareOrderInfoDto.getCanbeDownstreamUsed())) {
            opsPrepareOrder.setCanUseFlag(true);
        } else {
            opsPrepareOrder.setCanUseFlag(false);
        }
        opsPrepareOrder.setRemainQty(dto.getOrdRemainingQty());  // 残数
        opsPrepareOrder.setUseQty(dto.getShippedQty()); // 使用数量
        opsPrepareOrder.setPreQty(0);
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

        if(CollectionUtils.isNotEmpty(prepareOrderInfoDto.getLiquiDationInfo())) {
            /**
             * 将该订单下之前的清算信息数据逻辑删除
             */
            OpsPrepareOrderLiquidationExample liquidationExample = new OpsPrepareOrderLiquidationExample();
            liquidationExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderLiquidation opsPrepareOrderLiquidation = new OpsPrepareOrderLiquidation();
            opsPrepareOrderLiquidation.setDelFlag(true);
            opsPrepareOrderLiquidation.setUpdateTime(new Date());
            opsPrepareOrderLiquidationMapper.updateByExampleSelective(opsPrepareOrderLiquidation, liquidationExample);

            for (LiquiDationInfoDto liquidationInfoDto : prepareOrderInfoDto.getLiquiDationInfo()) {
                OpsPrepareOrderLiquidation obj = new OpsPrepareOrderLiquidation();
                obj.setOrderFno(dto.getOrderNo()+"-"+dto.getOrderItem());
                obj.setModelNo(liquidationInfoDto.getLiquidationModel());
                obj.setQuantity(liquidationInfoDto.getLiquidationQty());
                obj.setHandleWay(liquidationInfoDto.getHandleWay());
                obj.setCreateTime(new Date());
                obj.setUpdateTime(new Date());
                obj.setDelFlag(false);
                obj.setCreateUser("OPS");
                obj.setUpdateUser("OPS");
                opsPrepareOrderLiquidationMapper.insertSelective(obj);
            }
        }

        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getReserveResInfo())) {
            /**
             * 将该订单下之前的订单备库信息逻辑删除
             */
            OpsPrepareOrderReserveInfoExample orderReserveInfoExample = new OpsPrepareOrderReserveInfoExample();
            orderReserveInfoExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderReserveInfo info = new OpsPrepareOrderReserveInfo();
            info.setDelFlag(true);
            info.setUpdateTime(new Date());
            opsPrepareOrderReserveInfoMapper.updateByExampleSelective(info,orderReserveInfoExample);

            for (ReserveResInfo item : prepareOrderInfoDto.getReserveResInfo()) {
                OpsPrepareOrderReserveInfo obj = new OpsPrepareOrderReserveInfo();
                obj.setOrderFno(dto.getOrderNo() + "-" + dto.getOrderItem());
                obj.setModelNo(item.getReserveModel());
                obj.setQuantity(item.getReserveQty());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setUpdateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateUser("OPS");
                opsPrepareOrderReserveInfoMapper.insertSelective(obj);
            }
        }

        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getVerificationInfo())) {
            /**
             * 将该订单下之前的核销信息数据逻辑删除
             */
            OpsPrepareOrderReconciliationExample opsPrepareOrderReconciliationExample = new OpsPrepareOrderReconciliationExample();
            opsPrepareOrderReconciliationExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderReconciliation opsPrepareOrderReconciliation = new OpsPrepareOrderReconciliation();
            opsPrepareOrderReconciliation.setDelFlag(true);
            opsPrepareOrderReconciliation.setUpdateTime(new Date());
            opsPrepareOrderReconciliationMapper.updateByExampleSelective(opsPrepareOrderReconciliation, opsPrepareOrderReconciliationExample);

            for (VerificationInfoDto verificationInfoDto : prepareOrderInfoDto.getVerificationInfo()) {
                OpsPrepareOrderReconciliation obj = new OpsPrepareOrderReconciliation();
                obj.setOrderFno(dto.getOrderNo()+"-"+dto.getOrderItem());
                obj.setPoOrderNo(verificationInfoDto.getVerificationOrderNo());
                obj.setPoItemNo(Integer.valueOf(verificationInfoDto.getVerificationOrderItem()));
                obj.setQuantity(verificationInfoDto.getUsedQty());
                if (StringUtils.isNotBlank(verificationInfoDto.getUsedDate())) {
                    obj.setUseTime(DateUtil.stringToDateTime(verificationInfoDto.getUsedDate()));
                }
                obj.setEndUser(verificationInfoDto.getVerificationOrderEndUser());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateTime(new Date());
                obj.setUpdateUser("OPS");
                opsPrepareOrderReconciliationMapper.insertSelective(obj);
            }
        }




    }

    /**
     * 待清算,清算中
     * @param dto
     */
    private void upPrepareOrderWithPA(IpsSendDeliveryInfoToOpsDto dto) {
        if (StringUtils.isBlank(dto.getAddtionInfo())) {
            return;
        }
        PrepareOrderInfoDto prepareOrderInfoDto = JSONObject.parseObject(dto.getAddtionInfo(), PrepareOrderInfoDto.class);
        if (prepareOrderInfoDto == null) {
            return;
        }
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();

        // 是否写入决算标识
        if (StringUtils.isNotBlank(prepareOrderInfoDto.getCanbeDownstreamUsed()) && "1".equals(prepareOrderInfoDto.getCanbeDownstreamUsed())) {
            opsPrepareOrder.setCanUseFlag(true);
        } else {
            opsPrepareOrder.setCanUseFlag(false);
        }
        // 决算清单是否确定：0 不确定；1 已确定
        // 0为待决算 写入备库表 1 为清算中 写入决算清单中
        if ("0".equals(prepareOrderInfoDto.getAckFinalAccount())) {
            opsPrepareOrder.setStatus(PrepareStatusEnum.djs.getCode());
        } else {
            opsPrepareOrder.setStatus(PrepareStatusEnum.qsz.getCode());
        }
        opsPrepareOrder.setVerificationDate(prepareOrderInfoDto.getPlanDeadlineDate());
        opsPrepareOrder.setFinalAccountDate(prepareOrderInfoDto.getFinalAccountDate());
        opsPrepareOrder.setTerminateDate(prepareOrderInfoDto.getTerminateDate());

        opsPrepareOrder.setRemainQty(dto.getOrdRemainingQty());  // 残数
        opsPrepareOrder.setUseQty(dto.getShippedQty()); // 使用数量
        opsPrepareOrder.setPreQty(0);
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getFinalAccountInfo())) {
            /**
             * 决算信息
             */
            OpsPrepareOrderAccountExample accountExample = new OpsPrepareOrderAccountExample();
            accountExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo(false);

            OpsPrepareOrderAccount account = new OpsPrepareOrderAccount();
            account.setDelFlag(true);
            account.setUpdateTime(new Date());
            opsPrepareOrderAccountMapper.updateByExampleSelective(account,accountExample);

            for (AccountInfoDto item : prepareOrderInfoDto.getFinalAccountInfo()) {
                OpsPrepareOrderAccount obj = new OpsPrepareOrderAccount();
                obj.setOrderFno(dto.getOrderNo() + "-" + dto.getOrderItem());
                obj.setModelNo(item.getFinalAccountModel());
                obj.setQuantity(item.getFinalAccountQty());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setUpdateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateUser("OPS");
                opsPrepareOrderAccountMapper.insertSelective(obj);
            }
        }


        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getReserveResInfo())) {
            if (CollectionUtils.isEmpty(prepareOrderInfoDto.getFinalAccountInfo())) {
                /**
                 * 将该订单下之前的订单备库信息逻辑删除
                 */
                OpsPrepareOrderReserveInfoExample orderReserveInfoExample = new OpsPrepareOrderReserveInfoExample();
                orderReserveInfoExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

                OpsPrepareOrderReserveInfo info = new OpsPrepareOrderReserveInfo();
                info.setDelFlag(true);
                info.setUpdateTime(new Date());
                opsPrepareOrderReserveInfoMapper.updateByExampleSelective(info,orderReserveInfoExample);

                for (ReserveResInfo item : prepareOrderInfoDto.getReserveResInfo()) {
                    OpsPrepareOrderReserveInfo obj = new OpsPrepareOrderReserveInfo();
                    obj.setOrderFno(dto.getOrderNo() + "-" + dto.getOrderItem());
                    obj.setModelNo(item.getReserveModel());
                    obj.setQuantity(item.getReserveQty());
                    obj.setDelFlag(false);
                    obj.setCreateTime(new Date());
                    obj.setUpdateTime(new Date());
                    obj.setCreateUser("OPS");
                    obj.setUpdateUser("OPS");
                    opsPrepareOrderReserveInfoMapper.insertSelective(obj);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getVerificationInfo())) {
            /**
             * 将该订单下之前的核销信息数据逻辑删除
             */
            OpsPrepareOrderReconciliationExample opsPrepareOrderReconciliationExample = new OpsPrepareOrderReconciliationExample();
            opsPrepareOrderReconciliationExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderReconciliation opsPrepareOrderReconciliation = new OpsPrepareOrderReconciliation();
            opsPrepareOrderReconciliation.setDelFlag(true);
            opsPrepareOrderReconciliation.setUpdateTime(new Date());
            opsPrepareOrderReconciliationMapper.updateByExampleSelective(opsPrepareOrderReconciliation, opsPrepareOrderReconciliationExample);

            for (VerificationInfoDto verificationInfoDto : prepareOrderInfoDto.getVerificationInfo()) {
                OpsPrepareOrderReconciliation obj = new OpsPrepareOrderReconciliation();
                obj.setOrderFno(dto.getOrderNo()+"-"+dto.getOrderItem());
                obj.setPoOrderNo(verificationInfoDto.getVerificationOrderNo());
                obj.setPoItemNo(Integer.valueOf(verificationInfoDto.getVerificationOrderItem()));
                obj.setQuantity(verificationInfoDto.getUsedQty());
                if (StringUtils.isNotBlank(verificationInfoDto.getUsedDate())) {
                    obj.setUseTime(DateUtil.stringToDateTime(verificationInfoDto.getUsedDate()));
                }
                obj.setEndUser(verificationInfoDto.getVerificationOrderEndUser());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateTime(new Date());
                obj.setUpdateUser("OPS");
                opsPrepareOrderReconciliationMapper.insertSelective(obj);
            }
        }

    }

    /**
     * 已接单N
     * @param dto
     */
    private void upPrepareOrderWithN(IpsSendDeliveryInfoToOpsDto dto) {
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setStatus(PrepareStatusEnum.bkz.getCode());
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());

        opsPrepareOrder.setManuOrderNo(dto.getSupplierSalesOrderNo());
        opsPrepareOrder.setSupplierExpinvCode(dto.getSupplierExpinvCode());
        opsPrepareOrder.setSupplierExpinvRemark(dto.getSupplierExpinvRemark());
        opsPrepareOrder.setManuFactSupplierCode(dto.getFactSupplier());
        if(StringUtils.isNotBlank(dto.getPromiseDateFromSupplierA())) {
            opsPrepareOrder.setManuDlvDate(DateUtil.stringToDate(dto.getPromiseDateFromSupplierA()));
        }
        opsPrepareOrder.setSupplierExpinvCode(dto.getSupplierExpinvCode());
        opsPrepareOrder.setSupplierExpinvRemark(dto.getSupplierExpinvRemark());

        if (StringUtils.isNotBlank(dto.getAddtionInfo())) {
            PrepareOrderInfoDto additionInfoDto = JSONObject.parseObject(dto.getAddtionInfo(), PrepareOrderInfoDto.class);
            if (additionInfoDto != null) {
                if (StringUtils.isNotBlank(additionInfoDto.getCanbeDownstreamUsed()) && "1".equals(additionInfoDto.getCanbeDownstreamUsed())) {
                    opsPrepareOrder.setCanUseFlag(true);
                } else {
                    opsPrepareOrder.setCanUseFlag(false);
                }
            }
        }

        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

    }

    /**
     * 已接单I
     * @param dto
     */
    private void upPrepareOrderWithI(IpsSendDeliveryInfoToOpsDto dto) {
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setStatus(PrepareStatusEnum.bkz.getCode());
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
        opsPrepareOrder.setManuOrderNo(dto.getSupplierSalesOrderNo());
        opsPrepareOrder.setSupplierExpinvCode(dto.getSupplierExpinvCode());
        opsPrepareOrder.setSupplierExpinvRemark(dto.getSupplierExpinvRemark());
        opsPrepareOrder.setManuFactSupplierCode(dto.getFactSupplier());
        opsPrepareOrder.setRemainQty(dto.getOrdRemainingQty());

        if (StringUtils.isNotBlank(dto.getAddtionInfo())) {
            PrepareOrderInfoDto additionInfoDto = JSONObject.parseObject(dto.getAddtionInfo(), PrepareOrderInfoDto.class);
            if (additionInfoDto != null) {
                if (StringUtils.isNotBlank(additionInfoDto.getCanbeDownstreamUsed()) && "1".equals(additionInfoDto.getCanbeDownstreamUsed())) {
                    opsPrepareOrder.setCanUseFlag(true);
                } else {
                    opsPrepareOrder.setCanUseFlag(false);
                }

                /**
                 * 订单备库信息
                 */
                if (CollectionUtils.isNotEmpty(additionInfoDto.getReserveResInfo())) {
                    /**
                     * 将该订单下之前的订单备库信息逻辑删除
                     */
                    OpsPrepareOrderReserveInfoExample orderReserveInfoExample = new OpsPrepareOrderReserveInfoExample();
                    orderReserveInfoExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

                    OpsPrepareOrderReserveInfo info = new OpsPrepareOrderReserveInfo();
                    info.setDelFlag(true);
                    info.setUpdateTime(new Date());
                    opsPrepareOrderReserveInfoMapper.updateByExampleSelective(info,orderReserveInfoExample);

                    for (ReserveResInfo item : additionInfoDto.getReserveResInfo()) {
                        OpsPrepareOrderReserveInfo obj = new OpsPrepareOrderReserveInfo();
                        obj.setOrderFno(dto.getOrderNo() +"-" + dto.getOrderItem());
                        obj.setModelNo(item.getReserveModel());
                        obj.setQuantity(item.getReserveQty());
                        obj.setDelFlag(false);
                        obj.setCreateTime(new Date());
                        obj.setUpdateTime(new Date());
                        obj.setCreateUser("OPS");
                        obj.setUpdateUser("OPS");
                        opsPrepareOrderReserveInfoMapper.insertSelective(obj);
                    }
                }
            }
        }
        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);
    }

    /**
     * 已接单F
     * @param dto
     */
    private void upPrepareOrderWithF(IpsSendDeliveryInfoToOpsDto dto) {
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setStatus(PrepareStatusEnum.yjd.getCode());
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
        // 接单时间
        opsPrepareOrder.setSupplierOperateTime(dto.getSupplierOperateTime());
        opsPrepareOrder.setManuOrderNo(dto.getSupplierSalesOrderNo());

        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

    }

    /**
     * 已拒单
     * @param dto
     */
    private void insertPrepareOrderWithPorD(IpsSendDeliveryInfoToOpsDto dto) {
        /**
         * 1. 修改ops_prepare_order对应该准备订单为已拒单状态
         * 2. 修改先行在库状态为待处理
         */
         OpsPrepareOrderExample example = new OpsPrepareOrderExample();
         example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                 .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        List<OpsPrepareOrder> opsPrepareOrders = opsPrepareOrderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(opsPrepareOrders)) {
            return;
        }
        OpsPrepareOrder preOrder = opsPrepareOrders.get(0);

         OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
         opsPrepareOrder.setStatus(PrepareStatusEnum.refuse.getCode());
         opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
         opsPrepareOrder.setUpdateTime(new Date());

         opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);

        RejectPrepareOrderUpStockStatusDto redto = new RejectPrepareOrderUpStockStatusDto();
        redto.setApplyId(preOrder.getApplyId());
        redto.setDetailId(preOrder.getApplyDetailId());

         preStockFeignApi.rejectPrepareOrderUpPreStockStatus(redto);
    }

    /**
     * 备库中
     * @param dto
     */
    private void upPrepareOrderWithPU(IpsSendDeliveryInfoToOpsDto dto) {
        if (StringUtils.isBlank(dto.getAddtionInfo())) {
            return;
        }
        PrepareOrderInfoDto prepareOrderInfoDto = JSONObject.parseObject(dto.getAddtionInfo(), PrepareOrderInfoDto.class);
        if (prepareOrderInfoDto == null) {
            return;
        }
        OpsPrepareOrderExample example = new OpsPrepareOrderExample();
        example.createCriteria().andOrderNoEqualTo(dto.getOrderNo())
                .andItemNoEqualTo(Integer.valueOf(dto.getOrderItem())).andDelFlagEqualTo(false);

        OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
        opsPrepareOrder.setStatus(PrepareStatusEnum.bkz.getCode());
        opsPrepareOrder.setRemark(dto.getOrderStatusDescription());
        opsPrepareOrder.setUpdateTime(new Date());
        opsPrepareOrder.setPreQty(0);
        opsPrepareOrder.setRemainQty(dto.getOrdRemainingQty());  // 残数
        opsPrepareOrder.setUseQty(dto.getShippedQty()); // 使用数量

        if (StringUtils.isNotBlank(prepareOrderInfoDto.getCanbeDownstreamUsed()) && "1".equals(prepareOrderInfoDto.getCanbeDownstreamUsed())) {
            opsPrepareOrder.setCanUseFlag(true);
        } else {
            opsPrepareOrder.setCanUseFlag(false);
        }
        opsPrepareOrder.setVerificationDate(prepareOrderInfoDto.getPlanDeadlineDate());

        opsPrepareOrderMapper.updateByExampleSelective(opsPrepareOrder, example);



        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getVerificationInfo())) {
            /**
             * 将该订单下之前的核销信息数据逻辑删除
             */
            OpsPrepareOrderReconciliationExample opsPrepareOrderReconciliationExample = new OpsPrepareOrderReconciliationExample();
            opsPrepareOrderReconciliationExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderReconciliation opsPrepareOrderReconciliation = new OpsPrepareOrderReconciliation();
            opsPrepareOrderReconciliation.setDelFlag(true);
            opsPrepareOrderReconciliation.setUpdateTime(new Date());
            opsPrepareOrderReconciliationMapper.updateByExampleSelective(opsPrepareOrderReconciliation, opsPrepareOrderReconciliationExample);

            for (VerificationInfoDto verificationInfoDto : prepareOrderInfoDto.getVerificationInfo()) {
                OpsPrepareOrderReconciliation obj = new OpsPrepareOrderReconciliation();
                obj.setOrderFno(dto.getOrderNo()+"-"+dto.getOrderItem());
                obj.setPoOrderNo(verificationInfoDto.getVerificationOrderNo());
                obj.setPoItemNo(Integer.valueOf(verificationInfoDto.getVerificationOrderItem()));
                obj.setQuantity(verificationInfoDto.getUsedQty());
                if (StringUtils.isNotBlank(verificationInfoDto.getUsedDate())) {
                    obj.setUseTime(DateUtil.stringToDateTime(verificationInfoDto.getUsedDate()));
                }
                obj.setEndUser(verificationInfoDto.getVerificationOrderEndUser());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateTime(new Date());
                obj.setUpdateUser("OPS");
                opsPrepareOrderReconciliationMapper.insertSelective(obj);
            }
        }

        /**
         * 订单备库信息
         */
        if (CollectionUtils.isNotEmpty(prepareOrderInfoDto.getReserveResInfo())) {
            /**
             * 将该订单下之前的订单备库信息逻辑删除
             */
            OpsPrepareOrderReserveInfoExample orderReserveInfoExample = new OpsPrepareOrderReserveInfoExample();
            orderReserveInfoExample.createCriteria().andOrderFnoEqualTo(dto.getOrderNo()+"-"+dto.getOrderItem()).andDelFlagEqualTo( false);

            OpsPrepareOrderReserveInfo info = new OpsPrepareOrderReserveInfo();
            info.setDelFlag(true);
            info.setUpdateTime(new Date());
            opsPrepareOrderReserveInfoMapper.updateByExampleSelective(info,orderReserveInfoExample);

            for (ReserveResInfo item : prepareOrderInfoDto.getReserveResInfo()) {
                OpsPrepareOrderReserveInfo obj = new OpsPrepareOrderReserveInfo();
                obj.setOrderFno(dto.getOrderNo() + "-" + dto.getOrderItem());
                obj.setModelNo(item.getReserveModel());
                obj.setQuantity(item.getReserveQty());
                obj.setDelFlag(false);
                obj.setCreateTime(new Date());
                obj.setUpdateTime(new Date());
                obj.setCreateUser("OPS");
                obj.setUpdateUser("OPS");
                opsPrepareOrderReserveInfoMapper.insertSelective(obj);
            }
        }
    }

    public IpsReceiveOrderAllOriginalInfoDto conventToIpsReceiveOrderAllOriginalInfoDto(OpsPrepareOrder prepareOrder,String batchNo,int batchSize,OpsWarehouse warehouse) {

        IpsReceiveOrderAllOriginalInfoDto dto = new IpsReceiveOrderAllOriginalInfoDto();  // 最外层实体

        IpsReceiveOrderContentAddDto content = new IpsReceiveOrderContentAddDto();  // content实体

        content.setSmccode("9501200");
        content.setPurchaseUnitCode("CN0");
        content.setSourceSystem("OPS");
        content.setReceivingConnector(warehouse.getLinkman());
        content.setReceivingAddress(warehouse.getAdress());
        content.setReceivingAddressCode(warehouse.getParentCode());
        content.setReceivingDepartmentTeleNo(warehouse.getLinkPhone());
        content.setRequestDepartmentNo(prepareOrder.getApplyDeptNo());

        ResultVo<String> deptNameByNo = commonServiceFeignApi.getDeptNameByNo(prepareOrder.getApplyDeptNo());
        if (deptNameByNo.isSuccess() && deptNameByNo.getData() != null) {
            content.setRequestDepartmentName(deptNameByNo.getData());
        }

        content.setSourceOrderNo(prepareOrder.getOrderNo());
        content.setSourceItemNo(String.valueOf(prepareOrder.getItemNo()));
        content.setOrderMasterNum("1");
        content.setModelNo(prepareOrder.getModelNo());
        content.setModelName(" ");
        if(StringUtils.isNotBlank(prepareOrder.getSupplierCode())) {
            ResultVo<String> supplierid = ipsPurchaseSendCommonService.getIpsTranstypeByConfig(prepareOrder.getSupplierCode(), "supplierid");
            if (supplierid.isSuccess()) {
                content.setExpSupplierNo(supplierid.getData());
            }
        }
        content.setQty(new BigDecimal(prepareOrder.getApplyQty()));
        content.setUnit("17");
        content.setSpecifiedDeliveryDate(prepareOrder.getDlvDate());
        if (StringUtils.isNotBlank(prepareOrder.getTransType())) {
            ResultVo<String> transtype = ipsPurchaseSendCommonService.getIpsTranstypeByConfig(prepareOrder.getTransType(), "transtype");
            if (transtype.isSuccess()) {
                content.setSpecifiedDeliveryWay(transtype.getData());
            }
        }
        content.setPurchaseOrderType("B");
        content.setOrderType("1");
        content.setRequestTime(new Date());

        /**
         * 客户信息
         */
        IpsCustomerInfoDto customerInfo = new IpsCustomerInfoDto();
        customerInfo.setCustomerOrderNo(prepareOrder.getOrderNo());
        customerInfo.setCustomerItemNo(String.valueOf(prepareOrder.getItemNo()));
        customerInfo.setIssueDate(DateUtil.dateToDateTimeString(new Date()));
        customerInfo.setRemarks(prepareOrder.getRemark());

        IpsEnduserInfoDto enduserInfo = new IpsEnduserInfoDto();
        enduserInfo.setPurchaseEnduserNo(prepareOrder.getCustomerNo());


        /**
         * 产品信息
         */
        IpsProductInfoDto productInfo = new IpsProductInfoDto();
        productInfo.setRohscode(prepareOrder.getRohs());
        productInfo.setIs3C(poModelService.judge3CModel(prepareOrder.getModelNo()) ? "3C":"");

        IpsReceiveContentInfoDto ipsReceiveContentInfoDto = new IpsReceiveContentInfoDto();
        ipsReceiveContentInfoDto.setProductInfo(productInfo);
        ipsReceiveContentInfoDto.setCustomerInfo(customerInfo);
        ipsReceiveContentInfoDto.setEnduserInfo(enduserInfo);

        content.setContentInfo(JSONObject.toJSONString(ipsReceiveContentInfoDto));

        String hostAddress = "";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "";
        }

        dto.setSourceType(hostAddress);
        dto.setSourceSystem("OPS");
        dto.setSrcOrderNo(prepareOrder.getOrderNo());
        dto.setSrcOrderItemNo(String.valueOf(prepareOrder.getItemNo()));
        dto.setBatchNo(batchNo);
        dto.setBatchNum(batchSize);
        dto.setCreateTime(new Date());
        dto.setCreateUser("OPS");
        dto.setContent(JSONObject.toJSONString(content));

        return dto;
    }




}
