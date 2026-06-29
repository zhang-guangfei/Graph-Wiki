package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.prepareOrder.PrepareOrderTransferDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WMPurchaseTagEnum;
import com.sales.ops.enums.prepareOrder.PrepareStatusEnum;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.sales.ops.feign.PrepareOrderFeignApi;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.enums.PurchaseTypeEnum;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.prestock.PreStockApplyMapper;
import com.smc.smccloud.mapper.prestock.PreStockDetailMapper;
import com.smc.smccloud.mapper.prestock.PreStockResultMapper;
import com.smc.smccloud.model.CustomerVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.enums.PreStockApplyStatusEnum;
import com.smc.smccloud.model.enums.PreStockDetailStatusEnum;
import com.smc.smccloud.model.enums.PreStockProcessTypeEnum;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PreStockNewServiceImpl implements PreStockNewService {

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private PrepareOrderFeignApi prepareOrderFeignApi;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private PreStockResultMapper preStockResultMapper;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private OpsWarehouseService opsWarehouseService;

    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;

    @Resource
    private PreStockNewService preStockNewService;

    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;

    @Resource
    private PreStockDetailMapper preStockDetailMapper;

    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;

    @Resource
    private PreStockApplyMapper preStockApplyMapper;

    /**
     * 专用备库订单号
     */
    private final static String PURCHASE_ORDER_BILLTYPE = "3";

    private final static String PREPARE_ORDER_BILLTYPE = "35";

    /**
     * 委托在库仓库采购单号
     */
    private final static String WTCG = "11";

    @Override
    public ResultVo<String> createPurchaseOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList, boolean autoFlag) {
        if (CollectionUtils.isEmpty(purchaseList)) {
            return ResultVo.success("没有采购处理项需要执行");
        }
        log.info("先行在库申请{} 开始执行采购处理.", applyInfo.getApplyNo());
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? Optional.ofNullable(applyInfo.getCustomerNo()).orElse("") : applyInfo.getUserNo();
        // 请购applyDeptNo=收货所在地营业所代码（有备库客户时取所属营业所代码，否则取申请部门代码）
        String applyDeptNo = applyInfo.getDeptNo();
        if (StringUtils.isNotBlank(customerNo)) {
            CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(customerNo);
            if (customerInfo == null) {
                log.error("生成采购订单失败,无法获取申请客户信息: {}", customerInfo);
                throw new BusinessException("生成采购订单失败,无法获取申请客户信息");
            }
            applyDeptNo = customerInfo.getHRUnitId();
        }


        /**
         * 提取型号清单
         */
        List<String> modelNoList = purchaseList.stream().map(PreStockResultDTO::getModelNo).collect(Collectors.toList());

        ResultVo<Map<String, Boolean>> mapResultVo = prepareOrderFeignApi.prepareOrderVerify(modelNoList);

        if (!mapResultVo.isSuccess()) {
            throw new BusinessException("生成采购订单失败," + mapResultVo.getMessage());
        }

        Map<String, Boolean> modelNoMap = mapResultVo.getData();

        List<PreStockResultDTO> prepareOrderList = new ArrayList<>();

        List<PreStockResultDTO> newPurchaseList = new ArrayList<>();

        /**
         * 获取准备订单的交货期天数
         */
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "31");
        if (!dataTypeCodesInfo.isSuccess() || StringUtils.isBlank(dataTypeCodesInfo.getData().getExtNote1())) {
            throw new BusinessException("未能获取准备订单交货期天数:" + dataTypeCodesInfo.getMessage());
        }

        for (PreStockResultDTO purchase : purchaseList) {

            // 增加限制，顾客在库PPL,顾客在库项目，战略在库（PJ）这三个类型暂时不生成准备订单
            if (InventoryTypeEnum.GK_PPL.getCode().equalsIgnoreCase(detailInfo.getStockType()) ||
                    InventoryTypeEnum.GK_PJ.getCode().equalsIgnoreCase(detailInfo.getStockType()) ||
                    InventoryTypeEnum.ZL_PJ.getCode().equalsIgnoreCase(detailInfo.getStockType())) {
                purchase.setPrepareOrderFlag(false);
                newPurchaseList.add(purchase);
                continue;
            }

            // 手动处理  ->  处理类型选择采购  无论满不满足准备订单 都需要走采购
            if (!purchase.getProcessType().equals("6") && !autoFlag) {
                purchase.setPrepareOrderFlag(false);
                newPurchaseList.add(purchase);
                continue;
            }

            if (purchase.getDlvDateJp() != null && DateUtil.getDiffDay(new Date(),purchase.getDlvDateJp()) > Integer.parseInt(dataTypeCodesInfo.getData().getExtNote1())) {
                /**
                 * 判断型号是否在中国工厂Bin清单
                 */
                if (modelNoMap.get(purchase.getModelNo())) {
                    purchase.setPrepareOrderFlag(true);
                    prepareOrderList.add(purchase);
                    continue;
                }
            }
            purchase.setPrepareOrderFlag(false);
            newPurchaseList.add(purchase);
        }


        List<RequestPurchaseInfoDto> list = new ArrayList<>(purchaseList.size());

        List<OrderStateVO> orderStateVOList = new ArrayList<>(purchaseList.size());
        /**
         * 采购单
         */
        if (CollectionUtils.isNotEmpty(newPurchaseList)) {
            int itemNo = 0;
            String pOrderNo = null;

            // 如果该申请上一次生成采购处理未超过一天,复用上一次的采购单号
            OpsRequestpurchase lastRequestPurchaseOrder = preStockResultMapper.getLastRequestPurchaseOrder(applyInfo.getApplyNo());
            if (lastRequestPurchaseOrder != null
                    && DateUtil.getDate(lastRequestPurchaseOrder.getRequesttime()).compareTo(DateUtil.getToday()) == 0) {
                pOrderNo = lastRequestPurchaseOrder.getOrderno();
                itemNo = lastRequestPurchaseOrder.getItemno();
            }

            if (StringUtils.isBlank(pOrderNo)) {
                // 生成专用备库订单号
                ResultVo<String> generatorResult = commonServiceFeignApi.generatorBillNo(applyInfo.getWarehouseCode().startsWith("W") ? WTCG : PURCHASE_ORDER_BILLTYPE);
                log.info("先行在库-生成采购单号 orderNo = {}", generatorResult);
                if (!generatorResult.isSuccess() || StringUtils.isBlank(generatorResult.getData())) {
                    return ResultVo.failure("生成采购订单失败,生成订单号异常: " + generatorResult.getMessage());
                }
                pOrderNo = generatorResult.getData();
            }

            RequestPurchaseInfoDto purchase;
            Map<String, WarehouseDO> warehouseMap = new HashMap<>();
            Date now = new Date();

            OrderStateVO orderStateVO;
            String orderType = applyInfo.getWarehouseCode().startsWith("W") ? OrderTypeEnum.wtzkOrder.getCode() : OrderTypeEnum.xxbkOrder.getCode();
            boolean isFromOPS = applyInfo.getApplyNo().startsWith("PT"); // 判断申请是OPS申请的还是门户申请的 bug-8871
            boolean isMasterWarehouse = opsWarehouseService.isMasterWarehouse(applyInfo.getWarehouseCode());

            // 生成采购单
            for (PreStockResultDTO result : newPurchaseList) {
                // 备库库存属性ID  Bug-9942
                if (detailInfo.getInventoryPropertyId() == null) {
                    detailInfo.setInventoryPropertyId(this.getOpsInventoryPropertyId(result.getToInventoryTypeCode(), customerNo, result.getToGroupCustomerNo(), result.getToPplNo(), result.getToProjectNo()));
                }
                itemNo++;
                purchase = new RequestPurchaseInfoDto();
                purchase.setOrderno(pOrderNo);
                purchase.setItemno(itemNo);
                purchase.setModelno(result.getModelNo());
                purchase.setQuantity(result.getOrderQty());
                // bug-11679 请购deptNo=申请deptNo, 请购applyDeptNo=收货所在地营业所代码（有备库客户时取所属营业所代码，否则取申请部门代码）
                // bug 12910 先行在库写入请购表代码优化
                ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(applyDeptNo);
                if(deptNoByHRSalesDeptNo.isSuccess() && deptNoByHRSalesDeptNo.getData() != null) {
                    purchase.setDeptno(deptNoByHRSalesDeptNo.getData());
                }
                purchase.setApplyDeptNo(applyDeptNo);
                purchase.setHopedeliverydate(result.getDlvDateJp()); // 期望货期
                purchase.setHopeexportdate(result.getDlvDateJp());
                if (isFromOPS) {
                    // OPS申请,需要设置制造出荷日HopeExportDate bug-8871
                    purchase.setHopeexportdate(result.getDlvDateJp());
                } else {
                    // 门户申请,不需要设置HopeExportDate bug-8871
                    purchase.setHopeexportdate(null);
                }
                purchase.setRequesttime(now); // 请购时间
                purchase.setInventorytypecode(result.getToInventoryTypeCode());
                purchase.setInventorypropertyid(detailInfo.getInventoryPropertyId());
                purchase.setCustomerno(Optional.ofNullable(applyInfo.getCustomerNo()).orElse(""));
                purchase.setUserno(applyInfo.getUserNo());
                purchase.setPpl(result.getToPplNo());
                purchase.setProjectcode(result.getToProjectNo());
                purchase.setGroupcustomerno(result.getToGroupCustomerNo());
                purchase.setTranstype(result.getTransType());
                purchase.setOrdtype(orderType);
                purchase.setPurchasetype(PurchaseTypeEnum.PRE.getCode());
                purchase.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
                // purchase.setRequestwarehouseid(result.getToWarehouseCode()); // 请购仓库
                purchase.setRequestwarehouseid(applyInfo.getWarehouseCode()); // bug 14242 请购仓应以申请单的备库仓为准
                if (isMasterWarehouse) {
                    purchase.setPurchasewarehouseid(purchase.getRequestwarehouseid()); // 采购仓库
                } else {
                    if (!warehouseMap.containsKey(purchase.getRequestwarehouseid())) {
                        warehouseMap.put(purchase.getRequestwarehouseid(), opsWarehouseService.getWarehouseInfoByCode(purchase.getRequestwarehouseid()));
                    }
                    purchase.setPurchasewarehouseid(warehouseMap.get(purchase.getRequestwarehouseid()).getParentCode()); // 采购仓库
                }
                purchase.setCorderno(applyInfo.getApplyNo());
                purchase.setSupplierid(result.getOrderStock());
                purchase.setSpecmark(result.getSpecMark()); // 组装标识
                purchase.setProducttag(result.getProductTag()); // ROHS10


                //add by A78028 20231017 from bug 12284
                RequestPurchaseInfo infoJson = new RequestPurchaseInfo();
                infoJson.setVip(Optional.ofNullable(applyInfo.getVip()).orElse(false));
                purchase.setInfojson(infoJson);
                purchase.setEndUser(Optional.ofNullable(applyInfo.getCustomerNo()).orElse("")); // bug19576 采购发单给老生管对于最终代码的传值，先行在库补库取客户代码
                list.add(purchase);
                result.setOrderNo(pOrderNo + "-" + itemNo);

                orderStateVO = new OrderStateVO();
                orderStateVO.setOrderNo(result.getOrderNo());
                orderStateVO.setRorderNo(pOrderNo);
                orderStateVO.setItemNo(purchase.getItemno());
                orderStateVO.setModelNo(purchase.getModelno());
                orderStateVO.setQuantity(purchase.getQuantity());
                orderStateVO.setOrderType(Integer.valueOf(purchase.getOrdtype()));
                orderStateVO.setStateCode(OrderStateEnum.Purchareing.code());
                orderStateVO.setStateDes("生成先行在库请购单");
                orderStateVO.setStateType(1);
                orderStateVO.setStateDate(now);
                orderStateVO.setCustDlvDate(purchase.getHopedeliverydate());
                orderStateVO.setCustomerNo(purchase.getCustomerno());
                orderStateVO.setUserNo(purchase.getUserno());
                orderStateVO.setDeptNo(purchase.getApplyDeptNo());
                orderStateVO.setPoTransType(purchase.getTranstype());
                orderStateVO.setTransType(purchase.getTranstype());
                orderStateVO.setWarehouseCode(purchase.getRequestwarehouseid());
                orderStateVO.setPoDlvDate(purchase.getHopedeliverydate());
                orderStateVO.setOrderDate(now);
                orderStateVO.setReceiveDate(now);
                orderStateVO.setOrderPsnNo("ops");
                orderStateVO.setOptUserNo("ops");
                orderStateVO.setOptUserName("ops");
                orderStateVO.setProvider("OPS");
                orderStateVOList.add(orderStateVO);
            }
            log.info("先行在库-采购处理 {} data = {}", applyInfo.getApplyNo(), JSON.toJSON(newPurchaseList));
        }

        List<OpsPrepareOrder> opsPrepareOrderList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(prepareOrderList)) {
            ResultVo<List<OpsPrepareOrder>> listResultVo = conventToOpsPrepareOrder(prepareOrderList, applyInfo, detailInfo);
            if(!listResultVo.isSuccess()) {
                return ResultVo.failure(listResultVo.getMessage());
            }
            opsPrepareOrderList = listResultVo.getData();
            log.info("先行在库-准备订单处理 {} data = {}", applyInfo.getApplyNo(), JSON.toJSON(opsPrepareOrderList));
        }
        return  preStockNewService.savePurchaseOrder(opsPrepareOrderList,list, orderStateVOList, detailInfo, purchaseList);
    }

    private Long getOpsInventoryPropertyId(String inventoryTypeCode, String customerNo, String groupCustomerNo, String pplNo, String projectNo) {
        if (InventoryTypeEnum.TY.getCode().equalsIgnoreCase(inventoryTypeCode)) {
            return 1L;
        } else {
            OpsInventoryProperty propertyVO = new OpsInventoryProperty();
            propertyVO.setInventoryTypeCode(inventoryTypeCode);
            if (inventoryTypeCode.startsWith("GK") && StringUtils.isNotBlank(customerNo)) {
                propertyVO.setCustomerNo(customerNo);
            }
            if ((inventoryTypeCode.endsWith("HY") || inventoryTypeCode.endsWith("JT")) && StringUtils.isNotBlank(groupCustomerNo)) {
                propertyVO.setGroupCustomerNo(groupCustomerNo);
            }
            if (inventoryTypeCode.endsWith("PPL") && StringUtils.isNotBlank(pplNo)) {
                propertyVO.setPpl(pplNo);
            }
            if (inventoryTypeCode.endsWith("PJ") && StringUtils.isNotBlank(projectNo)) {
                propertyVO.setProjectCode(projectNo);
            }
            CommonResult checkResult = opsPropertyFeignApi.findProperty(propertyVO);
            if (!checkResult.isSuccess() || checkResult.getData() == null) {
                throw new BusinessException("验证备库的inventoryPropertyId失败: " + checkResult.getMessage());
            }
            return Long.parseLong(checkResult.getData().toString());
        }
    }

    private ResultVo<List<OpsPrepareOrder>> conventToOpsPrepareOrder( List<PreStockResultDTO> prepareOrderList, PreStockApplyDO applyDO, PreStockDetailDO detailDO) {
        List<OpsPrepareOrder> opsPrepareOrderList = new ArrayList<>();
        if (CollectionUtils.isEmpty(prepareOrderList)) {
            return ResultVo.success(opsPrepareOrderList);
        }

        String batchNo = "ZB"+DateUtil.getDateWithyyyymmddhhmmss(new Date());

        int itemNo = 0;
        String prepareOrderNo = null;

        // 生成准备订单号
        ResultVo<String> generatorResult = commonServiceFeignApi.generatorBillNo(PREPARE_ORDER_BILLTYPE);
        log.info("先行在库-生成采购单号 orderNo = {}", generatorResult);
        if (!generatorResult.isSuccess() || StringUtils.isBlank(generatorResult.getData())) {
            return ResultVo.failure("生成采购订单失败,生成订单号异常: " + generatorResult.getMessage());
        }
        prepareOrderNo = generatorResult.getData();

        String customerNo = StringUtils.isBlank(applyDO.getUserNo()) ? Optional.ofNullable(applyDO.getCustomerNo()).orElse("") : applyDO.getUserNo();

        for (PreStockResultDTO item : prepareOrderList) {
            // 备库库存属性ID  Bug-9942
            if (detailDO.getInventoryPropertyId() == null) {
                detailDO.setInventoryPropertyId(this.getOpsInventoryPropertyId(item.getToInventoryTypeCode(), customerNo, item.getToGroupCustomerNo(), item.getToPplNo(), item.getToProjectNo()));
            }
            itemNo++;
            OpsPrepareOrder opsPrepareOrder = new OpsPrepareOrder();
            opsPrepareOrder.setOrderNo(prepareOrderNo);
            opsPrepareOrder.setItemNo(itemNo);
            opsPrepareOrder.setOrderFno(opsPrepareOrder.getOrderNo()+"-"+opsPrepareOrder.getItemNo());
            opsPrepareOrder.setBatchNo(batchNo);
            opsPrepareOrder.setPreQty(0);
            opsPrepareOrder.setSupplierCode(item.getOrderStock()); // 期望供应商
            opsPrepareOrder.setTransType(item.getTransType()); // 要求供应商运输方式
            opsPrepareOrder.setModelNo(item.getModelNo());
            opsPrepareOrder.setDlvDate(item.getDlvDateJp());
            opsPrepareOrder.setCustomerNo(applyDO.getCustomerNo());
            opsPrepareOrder.setAvailableCustomers(opsPrepareOrder.getCustomerNo());
            opsPrepareOrder.setApplyNo(applyDO.getApplyNo());
            opsPrepareOrder.setApplyId(applyDO.getId());
            opsPrepareOrder.setApplyQty(item.getOrderQty());
            opsPrepareOrder.setWarehouseCode(applyDO.getWarehouseCode());
            opsPrepareOrder.setRohs(this.getRohs10(detailDO.getSpecExpType()) ? "H" : null);
            opsPrepareOrder.setApplyPsn(applyDO.getApplyPsnNo());
            /**
             * 如果申请部门是hl转为营业所
             */

            ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(applyDO.getDeptNo());
            if(deptNoByHRSalesDeptNo.isSuccess() && deptNoByHRSalesDeptNo.getData() != null) {
                opsPrepareOrder.setApplyDeptNo(deptNoByHRSalesDeptNo.getData());
            } else {
                opsPrepareOrder.setApplyDeptNo(applyDO.getDeptNo());
            }
            opsPrepareOrder.setApplyTime(applyDO.getApplyTime());
            opsPrepareOrder.setInventoryPropertyId(detailDO.getInventoryPropertyId());
            opsPrepareOrder.setUseQty(0);
            opsPrepareOrder.setStatus(PrepareStatusEnum.dcl.getCode());
            opsPrepareOrder.setDelFlag(false);
            opsPrepareOrder.setCanUseFlag(false);
            opsPrepareOrder.setVersion(1);
            opsPrepareOrder.setCreateTime(new Date());
            opsPrepareOrder.setUpdateTime(new Date());
            opsPrepareOrder.setApplyDetailId(detailDO.getId());
            opsPrepareOrder.setRemainQty(0);
            try {
                opsPrepareOrder.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
                opsPrepareOrder.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            } catch (Exception e) {
                opsPrepareOrder.setCreateUser("ops");
                opsPrepareOrder.setUpdateUser("ops");
            }
            item.setOrderNo(opsPrepareOrder.getOrderFno());
            opsPrepareOrderList.add(opsPrepareOrder);
        }
        return ResultVo.success(opsPrepareOrderList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> savePurchaseOrder( List<OpsPrepareOrder> opsPrepareOrderList,  List<RequestPurchaseInfoDto> PurchaseOrderList, List<OrderStateVO> orderStateList,
                                               PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList) {

        if(CollectionUtils.isNotEmpty(PurchaseOrderList)) {
            CommonResult<String> addResult = requestPurchaseFeignApi.addRequest(PurchaseOrderList);
            log.info("先行在库-采购处理 {} 响应 = {}", PurchaseOrderList.get(0).getCorderno(), JSON.toJSONString(addResult));
            if (addResult.getCode() == 500) {
                // 重置请购处理数量
                //this.resetDetailQty(purchaseList, Collections.singletonList(detailInfo));
                return ResultVo.failure("生成采购订单失败: " + addResult.getMessage());
            }
        }

        /**
         * 创建准备订单
         */
        if(CollectionUtils.isNotEmpty(opsPrepareOrderList)) {
            ResultVo<String> stringResultVo = prepareOrderFeignApi.prepareOrderCreate(opsPrepareOrderList);
            if (!stringResultVo.isSuccess()) {
                return stringResultVo;
            }
        }

        // 保存采购处理结果
        Map<Long, String> detailOrderMap = new HashMap<>();
        PreStockResultDO result;
        for (PreStockResultDTO resultDTO : purchaseList) {
            result = BeanCopyUtil.copy(resultDTO, PreStockResultDO.class);

            if(resultDTO.getPrepareOrderFlag()) {
                result.setProcessType(PreStockProcessTypeEnum.prepareOrder.getCode());
            }

            result.setOptStatus("2"); // 已处理
            result.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            result.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            preStockResultMapper.insert(result);
            // 记录已申请采购数量
            detailInfo.setPoQty(Optional.ofNullable(detailInfo.getPoQty()).orElse(0) + result.getOrderQty());
            // 记录申请项的处理单号
            if (!"9".equals(result.getProcessType())) {
                detailOrderMap.put(result.getFromId(), detailOrderMap.getOrDefault(result.getFromId(), "") + result.getOrderNo() + ";");
            }
        }

        // 更新申请项处理状态
        this.updateDetailProcessState(Collections.singletonList(detailInfo), detailOrderMap);

        // 发送请购单状态消息
        this.sendRequestPurchaseOrderState(orderStateList);

        return ResultVo.success("采购执行完成");
    }

    @Override
    public ResultVo<String> rejectPrepareOrderUpPreStockStatus(RejectPrepareOrderUpStockStatusDto dto) {


        /**
         * 准备订单拒单
         * 修改先行在库主项申请的状态为3待处理, result表数据清理掉, 如果明细里有一项为待处理状态则主项申请状态为2处理中
         */
        LambdaUpdateWrapper<PreStockApplyDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(PreStockApplyDO::getId, dto.getApplyId())
                .set(PreStockApplyDO::getStatus, 3)
                .set(PreStockApplyDO::getUpdateTime,new Date());
        preStockApplyMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
        detailUpdateWrapper
                .eq(PreStockDetailDO::getApplyId, dto.getApplyId())
                .eq(PreStockDetailDO::getId, dto.getDetailId())
                .set(PreStockDetailDO::getStatus, 3)
                .set(PreStockDetailDO::getUpdateTime,new Date());
        preStockDetailMapper.update(null, detailUpdateWrapper);


        LambdaUpdateWrapper<PreStockResultDO> resultUpdateWrapper = new LambdaUpdateWrapper<>();
        resultUpdateWrapper.eq(PreStockResultDO::getApplyId, dto.getApplyId()).eq(PreStockResultDO::getFromId, dto.getDetailId());
        resultUpdateWrapper.set(PreStockResultDO::getOptStatus, "9").set(PreStockResultDO::getUpdateTime,new Date());
        preStockResultMapper.update(null, resultUpdateWrapper);

        return ResultVo.success("操作成功");
    }

    /**
     * 准备订单转订 result状态改为3已转定 新增一条对应数据
     */
    @Override
    public ResultVo<String> prepareOrderTransferWithPresotckResult(PrepareOrderTransferDto dto) {
        if (dto == null || StringUtils.isBlank(dto.getOrderFno())) {
            return ResultVo.failure("入参prestock_result关键信息为空");
        }
        LambdaQueryWrapper<PreStockResultDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreStockResultDO::getFromId, dto.getFromId()).eq(PreStockResultDO::getApplyId, dto.getApplyId());
        PreStockResultDO result = preStockResultMapper.selectOne(queryWrapper);
        if (result == null) {
            return ResultVo.failure("未找到对应的prestock_result");
        }

        LambdaUpdateWrapper<PreStockResultDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PreStockResultDO::getId, result.getId())
                .set(PreStockResultDO::getOptStatus, "3") // 已转定
                .set(PreStockResultDO::getUpdateTime, new Date());
        preStockResultMapper.update(null, updateWrapper);

        result.setId(null);
        result.setOptStatus("2"); // 已处理
        result.setOrderQty(dto.getQty());
        result.setOrderNo(dto.getPoNo());
        if (StringUtils.isNotBlank(dto.getDlvDate())) {
            result.setDlvDateJp(DateUtil.stringToDate(dto.getDlvDate()));
        }
        result.setProcessType("1");
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());
        result.setRemark("采购"+dto.getQty());
        preStockResultMapper.insert(result);
        return ResultVo.success("操作成功");
    }

    private Boolean getRohs10(Integer specExpType) {
        if (specExpType == null) {
            return Boolean.FALSE;
        } else {
            return (specExpType & 64) == 64;
        }
    }

    private void updateDetailProcessState(List<PreStockDetailDO> detailList, Map<Long, String> orderMap) {
        LambdaUpdateWrapper<PreStockDetailDO> updateWrapper = Wrappers.lambdaUpdate();
        PreStockDetailDO detailDO = new PreStockDetailDO();

        for (PreStockDetailDO detail : detailList) {
            if (detail.getQuantity().compareTo(0) <= 0) {
                detail.setStatus(PreStockDetailStatusEnum.finished.getCode()); // 已处理
            }
            if (StringUtils.isNotBlank(orderMap.get(detail.getId()))) {
                if (StringUtils.isBlank(detail.getOrderNos())) {
                    detail.setOrderNos(orderMap.get(detail.getId()));
                } else if (detail.getOrderNos().length() + orderMap.get(detail.getId()).length() < 400) { // 单号长度超过400截断
                    detail.setOrderNos(detail.getOrderNos() + ";" + orderMap.get(detail.getId()));
                }
            }
            detail.setReplyStatus(1); // 待回调门户信息
            Boolean noCancel = !PreStockApplyStatusEnum.cancel.getCode().equalsIgnoreCase(detail.getStatus());
            updateWrapper.clear();
            updateWrapper.set(PreStockDetailDO::getStatus, detail.getStatus())
                    .set(noCancel, PreStockDetailDO::getStockQty, detail.getStockQty())
                    .set(noCancel, PreStockDetailDO::getPoQty, detail.getPoQty())
                    .set(noCancel, PreStockDetailDO::getPrepareQty, detail.getPrepareQty())
                    .set(PreStockDetailDO::getSupplierCode, detail.getSupplierCode())
                    .set(PreStockDetailDO::getInterceptId, detail.getInterceptId())
                    .set(PreStockDetailDO::getProcessText, detail.getProcessText())
                    .set(StringUtils.isNotBlank(detail.getOrderNos()), PreStockDetailDO::getOrderNos, detail.getOrderNos())
                    .set(PreStockDetailDO::getInventoryPropertyId, detail.getInventoryPropertyId()) // 记录库存属性ID Bug-9942
                    .set(PreStockDetailDO::getReplyStatus, detail.getReplyStatus())
                    .set(PreStockDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
            updateWrapper.eq(PreStockDetailDO::getId, detail.getId());
            preStockDetailMapper.update(detailDO, updateWrapper);
        }
    }

    /**
     * 发送生成采购订单状态
     */
    private void sendRequestPurchaseOrderState(List<OrderStateVO> orderStateVOList) {
        ResultVo<String> addResult;
        for (OrderStateVO orderStateVO : orderStateVOList) {
            try {
                addResult = orderStateServiceFeignApi.addOrderState(orderStateVO);
                if (!addResult.isSuccess()) {
                    log.error("保存先行在库请购单状态失败: {}", addResult.getMessage());
                }
            } catch (Exception e) {
                log.error("保存先行在库请购单状态发生异常: {}", e.getMessage(), e);
            }
        }
    }
}
