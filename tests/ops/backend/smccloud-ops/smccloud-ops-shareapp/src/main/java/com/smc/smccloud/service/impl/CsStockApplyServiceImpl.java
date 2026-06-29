package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.flux.RoConfirm;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.dto.inventory.WmWTDoConfirmDto;
import com.sales.ops.dto.inventory.WmWTDoItemConfirmDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.CstmTypeEnum;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.TransOrderMapper;
import com.smc.smccloud.mapper.csstock.*;
import com.smc.smccloud.mapper.product.ProductBomMapper;
import com.smc.smccloud.mapper.returnorder.ReturnOrderMapper;
import com.smc.smccloud.mapper.salesInvoice.SalesImpAgentMapper;
import com.smc.smccloud.mapper.salesInvoice.SalesInvoiceMidInfoMapper;
import com.smc.smccloud.mapper.warehouse.WarehouseMapper;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.inventory.ModelWarehouseStockRequest;
import com.smc.smccloud.model.inventory.OpsInventoryVO;
import com.smc.smccloud.model.inventory.SpecStockVO;
import com.smc.smccloud.model.invoice.InvoiceBalaceDTO;
import com.smc.smccloud.model.prestock.PreStockApplyDetailDTO;
import com.smc.smccloud.model.prestock.PreStockDetailDTO;
import com.smc.smccloud.model.product.OrderModelInfoVO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import com.smc.smccloud.model.returnorder.ReturnOrderVO;
import com.smc.smccloud.model.salesinvoice.SalesImpAgentDO;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceMidInfoDO;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.utils.JasperHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Slf4j
@Service
public class CsStockApplyServiceImpl implements CsStockApplyService {

    private final static String TRANSFER_NO = "17";

    @Resource
    private CsApplyMapper csApplyMapper;
    @Resource
    private CsApplyDetailMapper csApplyDetailMapper;
    @Resource
    private CsStockSettingMapper csStockSettingMapper;
    @Resource
    private OpsWarehouseService warehouseService;
    @Resource
    private CsInventoryMapper csInventoryMapper;
    //    //入库Mapper
//    @Resource
//    private CsImpdataMapper csImpdataMapper;
    //出库Mapper
    @Resource
    private CsExpdetailMapper csExpdetailMapper;
    //退货Mapper
    @Resource
    private CsReturnApplyMapper csReturnApplyMapper;
    //退货DetailMapper
    @Resource
    private CsReturnDetailMapper csReturnDetailMapper;
    //月结Mapper
    @Resource
    private CsBalanceMapper csBalanceMapper;
    @Resource
    private OpsWarehouseService opsWarehouseService;
    @Resource
    private PreStockService preStockService;
    @Resource
    private CsTmpBalanceMapper csTmpBalanceMapper;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private InvoiceServiceFeignApi invoiceServiceFeignApi;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private TransStockFeignApi transStockFeignApi;
    //    @Resource
//    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private ProductBomService productBomService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CsTmpReturnCalcMapper csTmpReturnCalcMapper;
    @Resource
    private ReturnOrderService returnOrderService;
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Resource
    private CsBalanceCalcMasterMapper csBalanceCalcMasterMapper;
    @Resource
    private OpsWmFeignApi opsWmFeignApi;
    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private CsImpDataOutMapper csImpDataOutMapper;
    @Resource
    private SalesImpAgentMapper salesImpAgentMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private CSImportDataService csImportDataService;

    @Resource
    private CsStockApplyServiceImpl csStockApplyService;
    @Resource
    private SalesInvoiceMidInfoMapper salesInvoiceMidInfoMapper;
    @Resource
    private EventService eventService;

    @Resource
    private CsImpdataMapper csImpdataMapper;
    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;

    @Resource
    private ProductBomMapper productBomMapper;
    @Resource
    private TransOrderMapper transOrderMapper;
    @Resource
    private ReturnOrderMapper returnOrderMapper;


    @Override
    public ResultVo<PageInfo<CsApplyDO>> listCsStockApply(CsStockApplyRequest request) {
        if (request.getToDate() != null) {
            request.setToDate(DateUtil.getEndTime(request.getToDate()));
        }
        LambdaQueryWrapper<CsApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getApplyId()), CsApplyDO::getApplyId, request.getApplyId());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getCapplyNo()), CsApplyDO::getCApplyNo, request.getCapplyNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()), CsApplyDO::getStatus, request.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getCustomerNo()), CsApplyDO::getCustomerNo, request.getCustomerNo());
        queryWrapper.gt(PublicUtil.isNotEmpty(request.getFromDate()), CsApplyDO::getCreateTime, request.getFromDate());
        queryWrapper.lt(PublicUtil.isNotEmpty(request.getToDate()), CsApplyDO::getCreateTime, request.getToDate());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getStockCode()), CsApplyDO::getStockCode, request.getStockCode());
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<CsApplyDO> list = csApplyMapper.selectList(queryWrapper.orderByDesc(CsApplyDO::getApplyId));
        PageInfo<CsApplyDO> pageInfo = PageInfo.of(list);
        PageInfo<CsApplyDO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsApplyDO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public ResultVo<PageInfo<CsApplyDetailDO>> listCsStockApplyDetail(CsApplyDetailRequest request) {
        if (PublicUtil.isEmpty(request.getApplyId())) {
            return ResultVo.failure("请求错误");
        }
        LambdaQueryWrapper<CsApplyDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getApplyId()), CsApplyDetailDO::getApplyId, request.getApplyId());
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<CsApplyDetailDO> list = csApplyDetailMapper.selectList(queryWrapper.orderByDesc(CsApplyDetailDO::getApplyId));
        PageInfo<CsApplyDetailDO> pageInfo = PageInfo.of(list);
        PageInfo<CsApplyDetailDO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsApplyDetailDO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public ResultVo<PageInfo<CsStockSettingDO>> listCsStockSetting(CsStockSettingRequest request) {
        if (request.getToTime() != null) {
            request.setToTime(DateUtil.getEndTime(request.getToTime()));
        }
        LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()), CsStockSettingDO::getStatus, request.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getCustomerNo()), CsStockSettingDO::getCustomerNo, request.getCustomerNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getLocationNo()), CsStockSettingDO::getLocationNo, request.getLocationNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getModelNo()), CsStockSettingDO::getModelNo, request.getModelNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getStockCode()), CsStockSettingDO::getStockCode, request.getStockCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getReplType()), CsStockSettingDO::getReplType, request.getReplType());
        queryWrapper.like(PublicUtil.isNotEmpty(request.getSponsor()), CsStockSettingDO::getSponsor, request.getSponsor());
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<CsStockSettingDO> list = csStockSettingMapper.selectList(queryWrapper);
        PageInfo<CsStockSettingDO> pageInfo = PageInfo.of(list);
        PageInfo<CsStockSettingDO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsStockSettingDO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public ResultVo<List<CsStockReplenishmentVO>> listReplDetail(String agentNo, String warehouseCode) {
        // 先计算申请中和订货中的数量
        csStockSettingMapper.csRelCalcQty(warehouseCode);

        LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsStockSettingDO::getCustomerNo, agentNo);
        queryWrapper.eq(CsStockSettingDO::getStockCode, warehouseCode);
        queryWrapper.eq(CsStockSettingDO::getStatus, 1);
        queryWrapper.eq(CsStockSettingDO::getReplType, 0);//0自动周转 1不自动周转
        //1) <=20%  补全数
        //2) >20% <=80% 的补20%
        //3) >80%的不补
        List<CsStockSettingDO> list = csStockSettingMapper.selectList(queryWrapper);
        List<CsStockReplenishmentVO> csStockReplenishmentVOS = new ArrayList<>(list.size());
        CsStockReplenishmentVO csReplVO;

        for (CsStockSettingDO csStockSettingDO : list) {
            //总备库数（合同数）
            int stockQty = Optional.ofNullable(csStockSettingDO.getInitStockQty()).orElse(0);
            //补货单位（数量倍数）
            int unitQty = Optional.ofNullable(csStockSettingDO.getInitUnitQty()).orElse(0);
            //当前库存（用于测试模拟数值）
            int qtyOnHand = Optional.ofNullable(csStockSettingDO.getQtyOnhand()).orElse(0);
            int prepareQty = Optional.ofNullable(csStockSettingDO.getQtyPrepare()).orElse(0);
            int applyQty = Optional.ofNullable(csStockSettingDO.getApplyQty()).orElse(0);
            int ordQty = Optional.ofNullable(csStockSettingDO.getOrdQty()).orElse(0);
            int transQty = Optional.ofNullable(csStockSettingDO.getTransQty()).orElse(0);

//            ModelWarehouseStockRequest warehouseStock = new ModelWarehouseStockRequest();
            //必需: modelNo, warehouseCode, inventoryTypeCode)
//            warehouseStock.setModelNo(csStockSettingDO.getModelNo());
//            warehouseStock.setWarehouseCode(csStockSettingDO.getStockCode());
//            warehouseStock.setInventoryTypeCode("TY");//TY通用类型
//            ResultVo<Integer> onHandQtyResult = inventoryServiceFeignApi.getModelWarehouseStock(warehouseStock);
            //赋值在库数
            int onHandQty = qtyOnHand - prepareQty + applyQty + ordQty + transQty;
            //需补货数
            int replQty = this.calcCsReplQty(onHandQty, stockQty, unitQty);
            if (0 == replQty) {
                continue;
            }

            csReplVO = new CsStockReplenishmentVO();
            csReplVO.setCustomerNo(csStockSettingDO.getCustomerNo());
//            csReplVO.setInitStockMonth(csStockSettingDO.getInitStockMonth());
            csReplVO.setInitStockQty(stockQty);
            csReplVO.setModelNo(csStockSettingDO.getModelNo());
            csReplVO.setInitUnitQty(unitQty);
            csReplVO.setStockCode(csStockSettingDO.getStockCode());
            csReplVO.setOnhandQty(onHandQty);
            csReplVO.setReplQty(replQty);
            csReplVO.setApplyQty(applyQty + ordQty);
            //在途
            csReplVO.setTransQty(transQty);
            //在库可用
            csReplVO.setOnhandCanUseQty(qtyOnHand - prepareQty);
            csReplVO.setTotalQty(csReplVO.getOnhandCanUseQty() + applyQty + replQty + csReplVO.getTransQty());
            //E价格
            csReplVO.setEprice(csStockSettingDO.getEprice());
            //库存在途中的数量
//            ResultVo<Integer> sumTransQtyResult = inventoryServiceFeignApi.sumInventoryTransQty(csStockSettingDO.getModelNo());
//            if (PublicUtil.isNotEmpty(sumTransQtyResult)) {
//                csReplVO.setTransQty(sumTransQtyResult.getData());
//            }
            csStockReplenishmentVOS.add(csReplVO);
        }
        if (CollectionUtils.isEmpty(csStockReplenishmentVOS)) {
            return ResultVo.failure("无需补库数据");
        }
//        Page page = new Page(request.getPageNum(), request.getPageSize());
//        page.setTotal(csStockReplenishmentVOS.size());
//        int startIndex = (request.getPageNum() - 1) * request.getPageSize();
//        int endIndex = Math.min((startIndex + request.getPageSize()), csStockReplenishmentVOS.size());
//        page.addAll(csStockReplenishmentVOS.subList(startIndex, endIndex));
//        PageInfo<CsStockReplenishmentVO> pageInfo = new PageInfo<>(page);
        return ResultVo.success(csStockReplenishmentVOS);
    }

    /**
     * 计算委托在库需备库数量
     * <=20%  补全数
     * >20% <=80% 的补20%
     * >80%的不补
     * 总在库数都不超过备库数
     *
     * @param onHandQty 当前库存
     * @param stockQty  设置备库数
     * @param unitQty   规格单位
     * @return 需备库数量
     */
    public int calcCsReplQty(int onHandQty, int stockQty, int unitQty) {
        //库存数大于备库数=0
        if (onHandQty > stockQty || 0 == stockQty) {
            return 0;
        }
        //在库率（按在库率计算需补货数量）
        double onHandRate = 1.0 * onHandQty / stockQty;
        //在库率大于0.8（不补货）
        if (onHandRate > 0.8) {
            return 0;
        }
        int replQty;
        //在库率小于等于0.2（补满合同数）
        if (onHandRate <= 0.2) {
//            replQty = stockQty - onHandQty;
            replQty = stockQty;
            if (replQty % unitQty > 0) {
                replQty = replQty - replQty % unitQty;
            }
        } else {
            Double num = stockQty * 0.2d - (stockQty * 0.2d) % unitQty;
            if ((onHandQty + num) > stockQty) {
                return 0;
            }
            replQty = num.intValue();
        }
        return replQty;
    }

    @Override
    public ResultVo<String> createReplApply(String agentNo, String warehouseCode) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        //判断库房有效性及归属营业所
        WarehouseDO csWarehouse = warehouseService.getCsWarehouseDO(agentNo, warehouseCode);
        if (csWarehouse == null) {
            return ResultVo.failure(String.format("客户代码 %s 库房不存在 %s", agentNo, warehouseCode));
        }
//        if (StringUtils.isEmpty(csWarehouse.getDeptNo())) {
//            return ResultVo.failure(String.format("库房. %s %s,无营业所代码，请设置营业代码", agentNo, warehouseCode));
//        }
        // 计算委托在库补库清单
        ResultVo<List<CsStockReplenishmentVO>> csStockReplenishmentVOs = this.listReplDetail(agentNo, warehouseCode);
        if (!csStockReplenishmentVOs.isSuccess()) {
            return ResultVo.failure(csStockReplenishmentVOs.getMessage());
        }

        // 获取PropertyInfo
        int applyType = 1;
        OpsInventoryProperty propertyInfo = this.getCSApplyToPropertyVO(applyType, null, null, null, null, null);

        PreStockApplyDetailDTO createDto = new PreStockApplyDetailDTO();
        //createDto.setApplyNo(applyNo);
        createDto.setApplyType("3"); // 3-委托在库申请
        createDto.setReplType("1"); // 1-SMC提案
        createDto.setStatus("1"); // 编辑中
        createDto.setApplyTime(new Date());
        createDto.setApplyPsnNo(userDTO.getUserNo());
        createDto.setDeptNo(csWarehouse.getDeptNo());
        createDto.setWarehouseCode(warehouseCode);
        createDto.setStockType(propertyInfo.getInventoryTypeCode());

        List<PreStockDetailDTO> itemList = new ArrayList<>(csStockReplenishmentVOs.getData().size());
        PreStockDetailDTO item;
        int itemNo = 0;
        String expType = "0"; // 希望备库方式 0-系统自动
        String detailStatus = "1"; // 申请项处理状态 1-编辑中
        String speckMark = "0"; // 默认-正常
        String transType = "0"; // 默认-海运
        Date dlvDate = DateUtil.addDay(DateUtil.getToday(), 45);

        for (CsStockReplenishmentVO detail : csStockReplenishmentVOs.getData()) {
            itemNo++;
            item = new PreStockDetailDTO();
            item.setItemNo(itemNo);
            item.setApplyModelNo(detail.getModelNo());
            item.setModelNo(detail.getModelNo());
            item.setQuantity(detail.getReplQty());
            item.setExpType(expType);
            item.setStatus(detailStatus);
            item.setDlvDate(dlvDate);
            item.setRohs10(Boolean.FALSE);
            item.setSpecMark(speckMark);
            item.setTransType(transType);
            item.setEprice(detail.getEprice());
            item.setStockType(createDto.getStockType());
            itemList.add(item);
        }
        createDto.setDetails(itemList);

        ResultVo<String> createResult = preStockService.createPreStockApply(createDto);
        if (!createResult.isSuccess()) {
            return ResultVo.failure("申请失败: " + createResult.getMessage());
        }
        return ResultVo.success(String.format("生成申请ID: %s,项数: %s .", createResult.getData(), itemList.size()));

        /*int totalQty = csStockReplenishmentVOs.getData().size();
        CsApplyDO applyDO = new CsApplyDO();
        applyDO.setTotalQty(totalQty);
        applyDO.setCustomerNo(agentNo);
        applyDO.setStockCode(warehouseCode);
        applyDO.setStatus(1);
        applyDO.setApplyType(1);
        applyDO.setCreateUser(userDTO.getUserNo());
        applyDO.setDeptNo(csWarehouse.getDeptNo());
        this.csApplyMapper.insert(applyDO);

        CsApplyDetailDO detailDO;
        int itemNo = 0;

        for (CsStockReplenishmentVO detail : csStockReplenishmentVOs.getData()) {
            itemNo++;
            detailDO = new CsApplyDetailDO();
            detailDO.setItemNo(itemNo);
            detailDO.setApplyId(applyDO.getApplyId());
            detailDO.setModelNo(detail.getModelNo());
            detailDO.setCalcQty(detail.getReplQty());
            detailDO.setQuantity(detail.getReplQty());
            detailDO.setCreateUser(userDTO.getUserNo());
            detailDO.setStatus(1);
            this.csApplyDetailMapper.insert(detailDO);
        }
        return ResultVo.success(String.format("生成申请号: %s,项数: %s .", applyDO.getApplyId(), totalQty));*/
    }

    @Override
    public ResultVo<String> removeDetail(Long applyId, String modelNo) {
        CsApplyDO csApplyMaster = csApplyMapper.selectById(applyId);
        if (null == csApplyMaster || 9 == csApplyMaster.getStatus()) {
            return ResultVo.failure("该申请号已删除,请勿重复操作");
        }
        QueryWrapper<CsApplyDetailDO> queryDelWrapper = new QueryWrapper<>();
        queryDelWrapper.eq("apply_id", applyId);
        queryDelWrapper.eq("model_no", modelNo);
        int result = csApplyDetailMapper.delete(queryDelWrapper);
        if (result == 0) {
            return ResultVo.failure("错误，删除失败");
        }
        QueryWrapper<CsApplyDetailDO> queryItemCountWrapper = new QueryWrapper<>();
        queryItemCountWrapper.eq("apply_id", applyId);
        Integer total = csApplyDetailMapper.selectCount(queryItemCountWrapper);
        UpdateWrapper<CsApplyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", applyId);
        CsApplyDO master = new CsApplyDO();
        master.setTotalQty(total);
        //更新项总数
        result = csApplyMapper.update(master, updateWrapper);
        return result == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<String> removeApply(Long applyId) {
        CsApplyDO csApplyDO = csApplyMapper.selectById(applyId);
        if (null == csApplyDO) {
            return ResultVo.failure("删除错误");
        }
        if (csApplyDO.getStatus() != 1) {
            return ResultVo.failure("删除错误，请检查状态");
        }
        UpdateWrapper<CsApplyDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", applyId);
        CsApplyDO master = new CsApplyDO();
        master.setStatus(9);
        //master.setUpdateUser(GetUserInfo.getUserInfo());
        //更新项总数
        int result = csApplyMapper.update(master, updateWrapper);
        UpdateWrapper<CsApplyDetailDO> detailUpdateWrapper = new UpdateWrapper<>();
        detailUpdateWrapper.eq("id", applyId);
        CsApplyDetailDO detail = new CsApplyDetailDO();
        detail.setStatus(9);
        //detail.setUpdateUser(GetUserInfo.getUserInfo());
        //更新项总数
        csApplyDetailMapper.update(detail, detailUpdateWrapper);
        return result == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

   /* @Override
    public ResultVo<String> confirmCsApply(CsApplyConfirmDTO request) {
        CsApplyDO csApplyMaster = csApplyMapper.selectById(request.getApplyId());
        if (null == csApplyMaster) {
            return ResultVo.failure("申请号无效:" + request.getApplyId());
        }
        //编辑中才可以提交
        if (1 != csApplyMaster.getStatus() && 3 != csApplyMaster.getStatus()) {
            return ResultVo.failure("提交失败,状态不可提交,请检查申请状态");
        }
        LambdaQueryWrapper<CsApplyDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsApplyDetailDO::getApplyId, request.getApplyId())
                .eq(CsApplyDetailDO::getStatus, 1);
        List<CsApplyDetailDO> detailList = csApplyDetailMapper.selectList(queryWrapper);
        if (PublicUtil.isEmpty(detailList)) {
            return ResultVo.failure(String.format("申请号：%s 不可提交,无可提交数据", +request.getApplyId()));
        }
        //判断库房有效性及归属营业所
        WarehouseDO warehouse = warehouseService.getCsWarehouseDO(csApplyMaster.getCustomerNo(), csApplyMaster.getStockCode());
        if (PublicUtil.isEmpty(warehouse)) {
            return ResultVo.failure(String.format("库房不存在. %s", csApplyMaster.getStockCode()));
        }

        // 预处理减去已处理数量
        this.handleConfirmDetail(detailList);

        OpsInventoryPropertyVO toPropertyInfo = this.getCSApplyToPropertyVO(csApplyMaster.getApplyType(), csApplyMaster.getUserNo(),
                csApplyMaster.getPplNo(), csApplyMaster.getProjectNo(), csApplyMaster.getGroupCustomerNo());
        ResultVo<String> resultVo;
        // 先调在库
        if (StringUtils.isBlank(csApplyMaster.getOrderNo()) || !csApplyMaster.getOrderNo().contains("VT")) {
            resultVo = this.csTransfer(csApplyMaster, detailList, warehouse.getDeptNo(), toPropertyInfo);
            if (!resultVo.isSuccess()) {
                return resultVo;
            }
            // 更新申请项处理状态
            this.updateConfirmStatus(csApplyMaster, detailList, request.getApproveText());
        }

        // 调在库后还有余数,生成采购订单
        resultVo = this.csPurchase(csApplyMaster, detailList, warehouse.getParentCode(), toPropertyInfo);
        if (!resultVo.isSuccess()) {
            return resultVo;
        }
        // 更新申请项处理状态
        this.updateConfirmStatus(csApplyMaster, detailList, request.getApproveText());

        return ResultVo.success("提交成功.");
    }*/

    /*private void updateConfirmStatus(CsApplyDO csApplyMaster, List<CsApplyDetailDO> detailList, String answerText) {
        LambdaUpdateWrapper<CsApplyDetailDO> detailUpdateWrapper;
        CsApplyDetailDO detail;
        int quantity = 0;
        for (CsApplyDetailDO detailDO : detailList) {
            quantity += detailDO.getQuantity();
            detailUpdateWrapper = new LambdaUpdateWrapper<>();
            detailUpdateWrapper.eq(CsApplyDetailDO::getId, detailDO.getId());
            detail = new CsApplyDetailDO();
            if (detailDO.getQuantity() == 0) {
                detail.setStatus(2);
            }
            detail.setAnswerText(answerText);
            detail.setOrderNo(detailDO.getOrderNo());
            detail.setRemark(detailDO.getRemark());
            csApplyDetailMapper.update(detail, detailUpdateWrapper);
        }

        // 更新ApplyMaster审批状态: 3审批通过 4不通过
        LambdaUpdateWrapper<CsApplyDO> updateMasterWrapper = new LambdaUpdateWrapper<>();
        updateMasterWrapper.eq(CsApplyDO::getApplyId, csApplyMaster.getApplyId());
        CsApplyDO csApply = new CsApplyDO();
        if (quantity == 0) {
            csApply.setStatus(6);
        }
        csApply.setOrderNo(csApplyMaster.getOrderNo());
        csApply.setApplyTime(new Date());
        csApplyMapper.update(csApply, updateMasterWrapper);
    }*/

   /* private void handleConfirmDetail(List<CsApplyDetailDO> detailList) {
        for (CsApplyDetailDO detail : detailList) {
            if (StringUtils.isBlank(detail.getRemark())) {
                continue;
            }
            String[] remarks = detail.getRemark().split(";");
            for (String remark : remarks) {
                if (remark.startsWith("仓库")) {
                    int transferQty = Integer.parseInt(remark.substring(remark.indexOf("出在库") + 3));
                    detail.setQuantity(detail.getQuantity() - transferQty);
                }
                if (remark.startsWith("采购")) {
                    int purchaseQty = Integer.parseInt(remark.substring(remark.indexOf("采购") + 2));
                    detail.setQuantity(detail.getQuantity() - purchaseQty);
                }
            }
        }
    }*/

    /*
     * 出在库处理
     *
     * @param applyInfo      申请信息
     * @param detailList     申请项信息
     * @param toPropertyInfo toPropertyInfo
     * @return 处理结果
     */
/*    private ResultVo<String> csTransfer(CsApplyDO applyInfo, List<CsApplyDetailDO> detailList, String deptNo, OpsInventoryPropertyVO toPropertyInfo) {
        // 查询委托在库可调库仓库
        List<String> warehouseConfig = opsWarehouseService.getOpsWarehouseSalesBranchConfig(deptNo);
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            return ResultVo.failure("委托在库-请检查归属部门是否已设置出库规则");
        }

        List<TransOrderVO> transDtoList = new ArrayList<>(detailList.size());
        ResultVo<Integer> minPackUnitResult;
        TransOrderVO transDto;
        ModelWarehouseStockRequest dto;
        ResultVo<Integer> stockQtyResult;
        String transNo = null;
        int itemNo = 0;
        long fromInventoryPropertyId = 1L;

        for (CsApplyDetailDO detailDo : detailList) {

            // 查询型号的最小包装数量
            minPackUnitResult = productServiceFeignApi.getMinPackUnitByModelNo(detailDo.getModelNo());
            if (!minPackUnitResult.isSuccess()) {
                return ResultVo.failure("委托在库处理失败, 无法获取最小包装数: " + detailDo.getModelNo() + minPackUnitResult.getMessage());
            }
            int minPackUnit = Optional.ofNullable(minPackUnitResult.getData()).orElse(1);

            for (String warehouseCode : warehouseConfig) {
                if (detailDo.getQuantity() == 0) {
                    break; // 处理下一项
                }
                if (!warehouseCode.startsWith("K")) {
                    continue; // 委托在库仅出大库
                }
                // 判断在库是否充足
                dto = new ModelWarehouseStockRequest();
                dto.setModelNo(detailDo.getModelNo());
                dto.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
                dto.setWarehouseCode(warehouseCode);
                stockQtyResult = inventoryServiceFeignApi.getModelWarehouseStock(dto);
                if (!stockQtyResult.isSuccess()) {
                    log.error("委托在库-检查出在库库存失败: {} ===> {}", dto, stockQtyResult);
                    return ResultVo.failure("委托在库-检查出在库库存失败");
                }

                // 计算可能申请调库数量
                int tryTransferQty = stockQtyResult.getData().compareTo(detailDo.getQuantity()) > 0 ? detailDo.getQuantity() : stockQtyResult.getData();
                if (tryTransferQty == 0) {
                    continue;
                }
                // 计算不满足最小包装的数量
                int unPackQty = tryTransferQty % minPackUnit;
                // 计算申请调库数量
                int transferQty;
                if (unPackQty == 0) {
                    transferQty = tryTransferQty;
                } else {
                    transferQty = tryTransferQty - unPackQty;
                }
                if (transferQty == 0) {
                    continue;
                }
                if (itemNo == 0) { // 生成调库单号
                    ResultVo<String> billNoResult = commonServiceFeignApi.generatorBillNo(TRANSFER_NO);
                    if (!billNoResult.isSuccess()) {
                        return ResultVo.failure("生成委托在库调库单号失败: " + billNoResult.getMessage());
                    }
                    transNo = billNoResult.getData();
                }
                detailDo.setQuantity(detailDo.getQuantity() - transferQty);
                // 在库充足，出在库
                itemNo++;
                transDto = new TransOrderVO();
                transDto.setTransType(1);
                transDto.setTransNo(transNo);
                transDto.setItemNo(itemNo);
                transDto.setModelNo(detailDo.getModelNo());
                transDto.setQuantity(transferQty);
                transDto.setStatus(0);
                transDto.setFromNo(applyInfo.getApplyId().toString());
                transDto.setFromId(detailDo.getId());
                transDto.setFromType(4);
                // 调出
                transDto.setFromInventoryPropertyId(fromInventoryPropertyId);
                transDto.setFromInventoryTypeCode(InventoryTypeEnum.TY.getCode());
                transDto.setFromWarehouseCode(warehouseCode);
                // 调入
                transDto.setToInventoryPropertyId(toPropertyInfo.getInventoryPropertyId());
                transDto.setToInventoryTypeCode(toPropertyInfo.getInventoryTypeCode());
                transDto.setToCustomerNo(toPropertyInfo.getCustomerNo());
                transDto.setToPpl(toPropertyInfo.getPpl());
                transDto.setToProjectCode(toPropertyInfo.getProjectCode());
                transDto.setToGroupCustomerNo(toPropertyInfo.getGroupCustomerNo());
                transDto.setToWarehouseCode(applyInfo.getStockCode());

                transDtoList.add(transDto);
                if (StringUtils.isEmpty(detailDo.getRemark())) {
                    detailDo.setOrderNo(transNo + "-" + itemNo + ";");
                    detailDo.setRemark(String.format("仓库%s出在库%d;", warehouseCode, transferQty));
                } else {
                    detailDo.setOrderNo(detailDo.getOrderNo() + transNo + "-" + itemNo + ";");
                    detailDo.setRemark(detailDo.getRemark() + String.format("仓库%s出在库%d;", warehouseCode, transferQty));
                }
            }
        }
        // 申请出在库
        if (CollectionUtils.isNotEmpty(transDtoList)) {
            ResultVo<String> transResult = transStockFeignApi.transStock(transDtoList);
            log.info("委托在库-出在库 applyId = {}, result = {}", applyInfo.getApplyId(), JSON.toJSONString(transResult));
            if (!transResult.isSuccess()) {
                throw new RuntimeException("委托在库-出在库失败: " + transResult.getMessage());
            }
            applyInfo.setOrderNo(Optional.ofNullable(applyInfo.getOrderNo()).orElse("") + transDtoList.get(0).getTransNo() + ";");
        }
        return ResultVo.success("委托在库-出在库成功");
    }
*/
    /*
     * 生成采购处理
     *
     * @param applyInfo      申请信息
     * @param detailList     申请项信息
     * @param parentCode     采购物流中心
     * @param toPropertyInfo toPropertyInfo
     * @return 处理结果
     */
   /* private ResultVo<String> csPurchase(CsApplyDO applyInfo, List<CsApplyDetailDO> detailList, String parentCode, OpsInventoryPropertyVO toPropertyInfo) {
        List<OpsRequestpurchase> purchaseList = null;
        int purchaseQty = detailList.stream().mapToInt(CsApplyDetailDO::getQuantity).sum();
        if (purchaseQty > 0) {
            ResultVo<String> randomOrderNoGenerator = dictDataServiceFeignApi.getRandomOrderNoGenerator("9001", "11");// 订单类型9001 寄售订单11
            String rorderNo = randomOrderNoGenerator.getData();
            if (StringUtils.isBlank(rorderNo)) {
                return ResultVo.failure("订单号为空");
            }

            String deptNo = opsWarehouseService.getWarehouseDeptNoByWarehouseCode(applyInfo.getStockCode());
            if (StringUtils.isBlank(deptNo)) {
                deptNo = applyInfo.getDeptNo();
            }
            if (StringUtils.isBlank(deptNo)) {
                return ResultVo.failure("申请不能为空,生成采购失败");
            }

            purchaseList = new ArrayList<>(detailList.size());
            OpsRequestpurchase purchase;
            ResultVo<String> supplierResult;
            Date now = new Date();
            Date hopeDlvDate = DateUtil.addDay(now, 28);
            int itemNo = 0;
            for (CsApplyDetailDO detailDo : detailList) {
                if (detailDo.getQuantity() == 0) {
                    continue;
                }
                // 根据型号查询供应商
                supplierResult = productServiceFeignApi.getSupplierNoByModelNo(detailDo.getModelNo());
                if (!supplierResult.isSuccess() || supplierResult.getData() == null) {
                    log.error("获取型号{}供应商信息获取失败: {}", detailDo.getModelNo(), supplierResult);
                    return ResultVo.failure("委托在库-生成采购失败, 型号" + detailDo.getModelNo() + "的供应商信息获取失败: " + supplierResult.getMessage());
                }

                itemNo++;
                purchase = new OpsRequestpurchase();
                purchase.setOrderno(rorderNo);
                purchase.setItemno(itemNo);
                purchase.setApplyDeptNo(applyInfo.getDeptNo());
                purchase.setModelno(detailDo.getModelNo());
                purchase.setQuantity(detailDo.getQuantity());
                purchase.setDeptno(deptNo);
                purchase.setApplyDeptNo(applyInfo.getDeptNo());
                purchase.setCustomerno(applyInfo.getCustomerNo());
                purchase.setHopedeliverydate(hopeDlvDate);
                purchase.setRequesttime(now);
                purchase.setInventorypropertyid(toPropertyInfo.getInventoryPropertyId());
                purchase.setInventorytypecode(toPropertyInfo.getInventoryTypeCode());
                purchase.setUserno(toPropertyInfo.getCustomerNo());
                purchase.setPpl(toPropertyInfo.getPpl());
                purchase.setProjectcode(toPropertyInfo.getProjectCode());
                purchase.setGroupcustomerno(toPropertyInfo.getGroupCustomerNo());
                purchase.setRequestwarehouseid(applyInfo.getStockCode());
                purchase.setPurchasewarehouseid(parentCode);
                purchase.setOrdtype(OrderTypeEnum.WT);
                purchase.setPurchasetype(PurchaseTypeEnum.PRE.getTypeCode());
                purchase.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
                purchase.setCorderno(applyInfo.getApplyId().toString());
                purchase.setSupplierid(supplierResult.getData());
                if (detailDo.getSpecExpType() != null && ((detailDo.getSpecExpType() & 64) == 64)) {
                    purchase.setProducttag("0");
                }
                purchaseList.add(purchase);
                detailDo.setQuantity(0);

                if (StringUtils.isEmpty(detailDo.getRemark())) {
                    detailDo.setOrderNo(rorderNo + "-" + itemNo + ";");
                    detailDo.setRemark(String.format("采购%d;", purchase.getQuantity()));
                } else {
                    detailDo.setOrderNo(detailDo.getOrderNo() + rorderNo + "-" + itemNo + ";");
                    detailDo.setRemark(detailDo.getRemark() + String.format("采购%d;", purchase.getQuantity()));
                }
            }
        }
        // 申请采购
        if (CollectionUtils.isNotEmpty(purchaseList)) {
            CommonResult<String> purchaseResult = requestPurchaseFeignApi.addRequest(purchaseList);
            log.info("委托在库-采购 applyId = {}, result = {}", applyInfo.getApplyId(), JSON.toJSONString(purchaseResult));
            if (!purchaseResult.isSuccess()) {
                return ResultVo.failure("委托在库-生成采购失败: " + purchaseResult.getMessage());
            }
            applyInfo.setOrderNo(Optional.ofNullable(applyInfo.getOrderNo()).orElse("") + purchaseList.get(0).getOrderno() + ";");
        }
        return ResultVo.success("委托在库-生成采购");
    }
*/
    @Override
    public ResultVo<String> updateCsWarehouseStatus(String warehouseCode, Integer status) {
        WarehouseDO warehourseDO = warehouseService.getWarehouseInfoByCode(warehouseCode);
        if (null == warehourseDO) {
            return ResultVo.failure("操作错误,不存在");
        }
        if (1 == warehourseDO.getDelflag()) {
            return ResultVo.failure("已删除不能重复删除");
        }
        LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsStockSettingDO::getStockCode, warehourseDO.getWarehouseCode());
        List<CsStockSettingDO> csStockSettings = csStockSettingMapper.selectList(queryWrapper);
        if (!PublicUtil.isEmpty(csStockSettings)) {
            return ResultVo.failure("无法修改,存在在使用中库房型号数据，请先删除");
        }
        WarehouseDO updateWarehouse = new WarehouseDO();
        updateWarehouse.setWarehouseCode(warehouseCode);
        updateWarehouse.setDelflag(status);
        ResultVo<String> result = warehouseService.saveCsWarehouse(updateWarehouse);
        if (!result.isSuccess()) {
            return ResultVo.failure("修改错误");
        }
        return ResultVo.success("修改成功");
    }

    /**
     * 导入excel备库型号清单
     *
     * @param file
     * @return
     */
    @Override
    public ResultVo<String> importCsStockSettingData(MultipartFile file, Integer type) {
        Date nowDate = DateUtil.getNow();
        String filename = file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(xlsx)$") && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误");
        }
        ExcelHelper excel;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVo.failure("Excel创建失败");
        }
        List<CsStockSettingDO> stockSettingDOList = new ArrayList<>();
        Sheet sheet = excel.getSheet();

        CsStockSettingDO info;
        int lastRowNum = sheet.getLastRowNum();
        for (int row = 1; row <= lastRowNum; row++) {
//        while (true) {
            Row rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            int cel = 0;
            info = new CsStockSettingDO();
            info.setCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
            info.setStockCode(excel.getCellString(rows.getCell(cel++)).trim());
            info.setModelNo(excel.getCellString(rows.getCell(cel++)).trim());
//            info.setLocationNo(excel.getCellString(rows.getCell(cel++)).trim());
            info.setInitStockQty(excel.getCellInteger(rows.getCell(cel++)));
            info.setInitUnitQty(excel.getCellInteger(rows.getCell(cel++)));
            info.setReplType(excel.getCellInteger(rows.getCell(cel++)));
            Integer status = excel.getCellInteger(rows.getCell(cel++));
            if (PublicUtil.isEmpty(status)) {
                info.setStatus(1);
            } else {
                info.setStatus(status);
            }
            //提案人（提案方）
            info.setSponsor(excel.getCellString(rows.getCell(cel++)).trim());
            if (info.getSponsor().contains("SMC")) {
                info.setStockType(1);
            }
            if (info.getSponsor().contains("代理")) {
                info.setStockType(2);
            }
            //营业所代码
            info.setDeptNo(excel.getCellString(rows.getCell(cel)).trim());
            info.setStatus(1);
            info.setUpdateTime(DateUtil.getNow());
            info.setCreateTime(DateUtil.getNow());
            if (PublicUtil.isEmpty(info.getCustomerNo())) {
                continue;
            }
            if (info.getInitStockQty() < 1) {
                return ResultVo.success("导入错误" + row + "行,备货总数有误");
            }
//            if (info.getInitUnitQty() < 1) {
//                return ResultVo.failure("导入错误" + row + "行,备货单位数量有误");
//            }
            if (info.getInitStockQty() < info.getInitUnitQty()) {
                return ResultVo.failure("导入错误" + row + "行,备货总数不可小于备货单位数量");
            }
            if (PublicUtil.isEmpty(info.getDeptNo())) {
                return ResultVo.success("导入错误" + row + "行,营业所代码不可空");
            }
            if (PublicUtil.isEmpty(info.getSponsor())) {
                return ResultVo.success("导入错误" + row + "行,提案方不可空");
            }
            //E价格
            ResultVo<OrderModelInfoVO> infoForOrder = productServiceFeignApi.getModelInfoForOrder(info.getModelNo());
            if (infoForOrder.isSuccess() && infoForOrder.getData() != null) {
                info.setEprice(infoForOrder.getData().getEprice());
                Integer minPackeQty = Optional.ofNullable(infoForOrder.getData().getMinPackQty()).orElse(1);
                info.setInitUnitQty(minPackeQty);
            } else {
                info.setEprice(new BigDecimal(0));
            }
            stockSettingDOList.add(info);
//            row++;
        }
        if (stockSettingDOList.size() < 1) {
            return ResultVo.success("导入错误." + "无数据导入");
        }
        //客户代码个数
        long cstmCount = stockSettingDOList.stream().map(CsStockSettingDO::getCustomerNo).distinct().count();
        if (type == 1 && cstmCount > 1) {
            return ResultVo.failure("仅支持单次导入单个客户");
        }
        //库房代码个数
        long stockCodeCount = stockSettingDOList.stream().map(CsStockSettingDO::getStockCode).distinct().count();
        if (type == 1 && stockCodeCount > 1) {
            return ResultVo.failure("仅支持单次导入单个客户");
        }
        String agentNo = stockSettingDOList.get(0).getCustomerNo();
        String warehouseCode = stockSettingDOList.get(0).getStockCode();

        int count = 0;
        for (CsStockSettingDO csStockSettingDO : stockSettingDOList) {
            WarehouseDO warehouseDO = warehouseService.getCsWarehouseDO(csStockSettingDO.getCustomerNo(), csStockSettingDO.getStockCode());
            if (PublicUtil.isEmpty(warehouseDO)) {
                return ResultVo.failure(String.format("导入错误.%s 库房代码%s 非有效库房", csStockSettingDO.getCustomerNo(), csStockSettingDO.getStockCode()));
            }
            if (this.existCsStockSetting(csStockSettingDO)) {
                if (this.updateCsStockSetting(csStockSettingDO)) {
                    count++;
                }
            } else {
                int addCout = csStockSettingMapper.insert(csStockSettingDO);
                if (addCout >= 1) {
                    count++;
                }
            }
        }
        if (type == 1) {
            LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CsStockSettingDO::getCustomerNo, agentNo);
            queryWrapper.eq(CsStockSettingDO::getStockCode, warehouseCode);
            queryWrapper.lt(CsStockSettingDO::getUpdateTime, nowDate);

            csStockSettingMapper.delete(queryWrapper);
        }
        return ResultVo.success("已导入" + count + "条记录");
    }

    /**
     * 判断存在备库型号
     *
     * @param info
     * @return
     */
    private boolean existCsStockSetting(CsStockSettingDO info) {
        QueryWrapper<CsStockSettingDO> query = new QueryWrapper<>();

        query.eq("customer_no", info.getCustomerNo())
                .eq("stock_code", info.getStockCode())
                .eq("model_no", info.getModelNo());
        return csStockSettingMapper.selectCount(query) > 0;
    }

    /**
     * 获得备库型号
     *
     * @param agentNo
     * @param warehouseCode
     * @param modelNo
     * @return
     */
    @Override
    public CsStockSettingDO getCsStockSetting(String agentNo, String warehouseCode, String modelNo) {
        QueryWrapper<CsStockSettingDO> query = new QueryWrapper<>();
        query.eq("customer_no", agentNo)
                .eq("stock_code", warehouseCode)
                .eq("model_no", modelNo);
        return csStockSettingMapper.selectOne(query);
    }

    /**
     * 更新备库型号信息
     *
     * @param info
     * @return
     */
    private boolean updateCsStockSetting(CsStockSettingDO info) {
        LambdaUpdateWrapper<CsStockSettingDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CsStockSettingDO::getInitStockQty, info.getInitStockQty())
                .set(CsStockSettingDO::getInitUnitQty, info.getInitUnitQty())
                .set(CsStockSettingDO::getLocationNo, info.getLocationNo())
                .set(CsStockSettingDO::getReplType, info.getReplType())
                .set(CsStockSettingDO::getSponsor, info.getSponsor())
                .set(CsStockSettingDO::getEprice, info.getEprice())
                .set(CsStockSettingDO::getStockType, info.getStockType())
                .set(CsStockSettingDO::getUpdateTime, new Date())
                .eq(CsStockSettingDO::getCustomerNo, info.getCustomerNo())
                .eq(CsStockSettingDO::getStockCode, info.getStockCode())
                .eq(CsStockSettingDO::getModelNo, info.getModelNo());
        return csStockSettingMapper.update(null, updateWrapper) >= 1;
    }

    @Override
    public ResultVo<String> deleteCsStockSettingById(Long id) {
        CsStockSettingDO csStockSettingDO = new CsStockSettingDO();
        csStockSettingDO.setId(id);
        csStockSettingDO.setStatus(9);
        int result = csStockSettingMapper.updateById(csStockSettingDO);
        if (result != 1) {
            return ResultVo.failure("删除错误");
        }
        return ResultVo.success("成功删除");
    }

    @Override
    public ResultVo<String> addDetail(CsApplyDetailDTO csApplyDetailDTO) {
        CsApplyDO csApplyDO = this.csApplyMapper.selectById(csApplyDetailDTO.getApplyId());
        if (PublicUtil.isEmpty(csApplyDO)) {
            return ResultVo.failure("申请号不存在");
        }
        if (1 != csApplyDO.getStatus()) {
            return ResultVo.failure("非编辑状态,不可操作");
        }
        LambdaQueryWrapper<CsApplyDetailDO> queryDetailWrapper = new LambdaQueryWrapper<>();
        queryDetailWrapper.eq(CsApplyDetailDO::getApplyId, csApplyDetailDTO.getApplyId());
        queryDetailWrapper.eq(CsApplyDetailDO::getModelNo, csApplyDetailDTO.getModelNo());
        CsApplyDetailDO csApplyDetailDO = this.csApplyDetailMapper.selectOne(queryDetailWrapper);
        if (PublicUtil.isNotEmpty(csApplyDetailDO)) {
            return ResultVo.failure(String.format("型号%s 已存在，不可重复添加", csApplyDetailDTO.getModelNo()));
        }
        CsStockSettingDO csStockSettingDO = this.getCsStockSetting(csApplyDO.getCustomerNo(), csApplyDO.getStockCode(), csApplyDetailDO.getModelNo());
        if (PublicUtil.isEmpty(csStockSettingDO)) {
            return ResultVo.failure(String.format("错误.库房代码 %s 未设置备库型号 %s ，请检查型号", csApplyDO.getStockCode(), csApplyDetailDTO.getModelNo()));
        }
        if (1 != csStockSettingDO.getStatus()) {
            return ResultVo.failure(String.format("错误.库房代码 %s 型号 %s 已停用，请检查型号", csApplyDO.getStockCode(), csApplyDetailDTO.getModelNo()));
        }
        if (csStockSettingDO.getInitStockQty() < csApplyDetailDTO.getQuantity()) {
            return ResultVo.failure(String.format("错误.型号 %s 不可大于设置最大备库数 %s，请检查型号", csApplyDetailDTO.getModelNo(), csStockSettingDO.getInitStockQty()));
        }
        CsApplyDetailDO detailDO = new CsApplyDetailDO();
        detailDO.setApplyId(csApplyDO.getApplyId());
        detailDO.setQuantity(csApplyDetailDTO.getQuantity());
        detailDO.setModelNo(csApplyDetailDTO.getModelNo());
        detailDO.setRemark(csApplyDetailDTO.getRemark());
        int result = csApplyDetailMapper.insert(detailDO);
        if (result != 1) {
            return ResultVo.failure("新增错误");
        }
        return ResultVo.success("新增成功");
    }

    @Override
    public ResultVo<String> updateDetail(CsApplyDetailDTO csApplyDetailDTO) {

        CsApplyDO csApplyDO = this.csApplyMapper.selectById(csApplyDetailDTO.getApplyId());
        if (PublicUtil.isEmpty(csApplyDO)) {
            return ResultVo.failure("申请号不存在");
        }
        if (1 != csApplyDO.getStatus()) {
            return ResultVo.failure("申请状态编辑中才可修改");
        }
        CsStockSettingDO csStockSettingDO = this.getCsStockSetting(csApplyDO.getCustomerNo(), csApplyDO.getStockCode(), csApplyDetailDTO.getModelNo());
        if (PublicUtil.isEmpty(csStockSettingDO)) {
            return ResultVo.failure(String.format("型号%s不在备库清单内,请检查", csApplyDetailDTO.getModelNo()));
        }
        if (csApplyDetailDTO.getQuantity() > csStockSettingDO.getInitStockQty()) {
            return ResultVo.failure(String.format("错误.型号%s 不可超过最大备库数 %s ", csApplyDetailDTO.getModelNo(), csStockSettingDO.getInitStockQty()));
        }
        UpdateWrapper<CsApplyDetailDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", csApplyDetailDTO.getId());
        CsApplyDetailDO detailDO = new CsApplyDetailDO();
        detailDO.setQuantity(csApplyDetailDTO.getQuantity());
        int result = csApplyDetailMapper.update(detailDO, updateWrapper);
        if (result != 1) {
            return ResultVo.failure("更新错误");
        }
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<String> updateCsStockSetting(CsStockSettingDTO request) {

        CsStockSettingDO csStockSettingDO = this.getCsStockSetting(request.getCustomerNo(), request.getStockCode(), request.getModelNo());
        if (PublicUtil.isEmpty(csStockSettingDO)) {
            return ResultVo.failure("更新错误");
        }
//        if (PublicUtil.isEmpty(request.getLocationNo())) {
//            return ResultVo.failure("请填写货架号");
//        }
        CsStockSettingDO detailDO = new CsStockSettingDO();
        detailDO.setId(csStockSettingDO.getId());
        detailDO.setLocationNo(request.getLocationNo());
        detailDO.setInitStockMonth(request.getInitStockMonth());
        detailDO.setInitStockQty(request.getInitStockQty());
        detailDO.setInitUnitQty(request.getInitUnitQty());
        detailDO.setReplType(request.getReplType());
//        detailDO.setSponsor(request.getSponsor());
//        if (detailDO.getSponsor().contains("SMC")) {
//            detailDO.setStockType(0);
//        }
//        if (detailDO.getSponsor().contains("代理")) {
//            detailDO.setStockType(1);
//        }
        int result = csStockSettingMapper.updateById(detailDO);
        if (result != 1) {
            return ResultVo.failure("更新错误");
        }
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<String> updateCsSettingModelStatus(String agentNo, String warehouseCode, String modelNo, Integer status) {
        CsStockSettingDO csStockSettingDO = this.getCsStockSetting(agentNo, warehouseCode, modelNo);
        if (PublicUtil.isEmpty(csStockSettingDO)) {
            return ResultVo.failure("更新错误");
        }
        if (csStockSettingDO.getStatus().compareTo(status) == 0) {
            return ResultVo.failure("更变前后一致");
        }
        CsStockSettingDO detailDO = new CsStockSettingDO();
        detailDO.setId(csStockSettingDO.getId());
        detailDO.setStatus(status);
        int result = csStockSettingMapper.updateById(detailDO);
        if (result != 1) {
            return ResultVo.failure("更新错误");
        }
        return ResultVo.success(1 == status ? "已启动" : "已停用");
    }

    /**
     * 更变委托在库型号货架位
     *
     * @param request
     * @return
     */
    @Override
    public ResultVo<String> updateCsStockModelLocationNo(CsStockUpdateLocationDTO request) {
        if (PublicUtil.isEmpty(request.getLocationNo())) {
            return ResultVo.failure("需更变货架位不可为空");
        }
        //查询存在该型号信息
        CsStockSettingDO csStockSettingDO = this.getCsStockSetting(request.getAgentNo(), request.getWarehouseCode(), request.getModelNo());
        if (PublicUtil.isEmpty(csStockSettingDO)) {
            return ResultVo.failure("无该型号信息");
        }
        if (csStockSettingDO.getLocationNo().equals(request.getLocationNo())) {
            return ResultVo.failure(String.format("更新前后货位一致,%s.不可重新修改", request.getLocationNo()));
        }
        //更新货架号
        CsStockSettingDO detailDO = new CsStockSettingDO();
        detailDO.setId(csStockSettingDO.getId());
        detailDO.setLocationNo(request.getLocationNo());
        int result = csStockSettingMapper.updateById(detailDO);
        if (result != 1) {
            return ResultVo.failure("更新错误");
        }
        return ResultVo.success(String.format("更新成功。更变成:%s,原货位:%s", request.getLocationNo(), csStockSettingDO.getLocationNo()));
    }

    @Override
    public ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(CsImportDetailRequestDTO requestDTO) {

//        long startTimer = System.currentTimeMillis();
        log.info("入库明细查询参数: {}", JSONObject.toJSONString(requestDTO));
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getAgentNo()), CsImpdataDO::getAgentNo, requestDTO.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getBarcode()), CsImpdataDO::getBarcode, requestDTO.getBarcode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getCaseNo()), CsImpdataDO::getCaseNo, requestDTO.getCaseNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getDeliveryNo()), CsImpdataDO::getDeliveryNo, requestDTO.getDeliveryNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getImpType()), CsImpdataDO::getImpType, requestDTO.getImpType());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getModelNo()), CsImpdataDO::getModelNo, requestDTO.getModelNo());
        queryWrapper.likeRight(PublicUtil.isNotEmpty(requestDTO.getOrderNo()), CsImpdataDO::getOrderNo, requestDTO.getOrderNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getWarehouseCode()), CsImpdataDO::getWarehouseCode, requestDTO.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStatus()), CsImpdataDO::getStatus, requestDTO.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getInvoiceNo()), CsImpdataDO::getInvoiceNo, requestDTO.getInvoiceNo());
        if (StringUtils.isNotBlank(requestDTO.getMonthDate())) {
            String monthDate = requestDTO.getMonthDate();
            String[] split = monthDate.split("-");
            String firstDayOfMonth1 = DateUtil.getFirstDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            String lastDayOfMonth1 = DateUtil.getLastDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Date endTime = DateUtil.getEndTime(DateUtil.stringToDate(lastDayOfMonth1));
            queryWrapper.ge(CsImpdataDO::getReceiveTime, firstDayOfMonth1);
            queryWrapper.le(CsImpdataDO::getReceiveTime, endTime);
        }
        if (PublicUtil.isEmpty(requestDTO.getStatus())) {
            queryWrapper.ne(CsImpdataDO::getStatus, 9);
        }

//        if (requestDTO.getBeginDate() != null) {
//            requestDTO.setBeginDate(requestDTO.getBeginDate() + " 00:00:00");
//            queryWrapper.ge(CsImpdataDO::getImpDate, requestDTO.getBeginDate());
//        }
//        if (requestDTO.getEndDate() != null) {
//            requestDTO.setEndDate(requestDTO.getEndDate() + " 23:59:59");
//            queryWrapper.le(CsImpdataDO::getImpDate, requestDTO.getEndDate());
//        }
        if (PublicUtil.isEmpty(requestDTO.getDateType()) && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            requestDTO.setDateType(2);
        }

        if (PublicUtil.isNotEmpty(requestDTO.getDateType()) && requestDTO.getDateType() == 1 && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            queryWrapper.between(CsImpdataDO::getShipDate, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
        }
        if (PublicUtil.isNotEmpty(requestDTO.getDateType()) && requestDTO.getDateType() == 2 && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            queryWrapper.between(CsImpdataDO::getReceiveTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
        }

        if (null != requestDTO.getDataAuthoritySearchCondition()) {
            DataAuthoritySearchCondition authoritySearchCondition = requestDTO.getDataAuthoritySearchCondition();
            if (PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()) || PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority())) {
                queryWrapper.and(wrapper -> wrapper.in(PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority()), CsImpdataDO::getUserNo, authoritySearchCondition.getUserIdListByDataAuthority())
                        .or(PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority()) || PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()))
                        .in(PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()), CsImpdataDO::getAgentNo, authoritySearchCondition.getCustomerCodeListByDataAuthority()));
            }
//            log.info("出库查询："+authoritySearchCondition.toString());
        }
        queryWrapper.orderByDesc(CsImpdataDO::getOrderNo, CsImpdataDO::getImpDate, CsImpdataDO::getWarehouseCode);
        PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<CsImpdataDO> list = csImportDataService.listCSImportDataByWrapper(queryWrapper);
        PageInfo<CsImpdataDO> pageInfo = PageInfo.of(list);
        PageInfo<CsImportDetailVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsImportDetailVO.class);
        if (!voPageInfo.getList().isEmpty()) {
            Map<String, CustomerVO> customerMap = new HashMap<>();
            Map<String, String> nameMap = new HashMap<>();
            CustomerVO customerVO;

            for (CsImportDetailVO item : voPageInfo.getList()) {
                item.setImpDate(item.getReceiveTime());
                item.setSignStatusName(item.getSignTime() == null ? "未签收" : "已签收");
                // 设置库房名称
                if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
                    nameMap.put(item.getWarehouseCode(), commonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
                item.setWareHourseName(nameMap.get(item.getWarehouseCode()));
                // 客户名称
                if (StringUtils.isNotBlank(item.getAgentNo())) {
                    if (customerMap.containsKey(item.getAgentNo())) {
                        item.setAgentName(customerMap.get(item.getAgentNo()).getName());
                        item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerMap.get(item.getAgentNo()).getCustomerType()).orElse("")));
                    } else {
                        customerVO = commonService.getCustomerInfoByNo(item.getAgentNo());
                        if (customerVO != null) {
                            item.setAgentName(customerVO.getName());
                            item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerVO.getCustomerType()).orElse("")));
                            customerMap.put(item.getAgentNo(), customerVO);
                        }
                    }

                }
            }
        }
//        long endTimer = System.currentTimeMillis();
//        log.info("入库查询接口执行共计耗时:" + (endTimer - startTimer) / 1000 + "s");
        return ResultVo.success(voPageInfo);
    }

    @Override
    public void exportCalcRetrunData() {
        QueryWrapper<CsTmpReturnCalcDO> queryWrapper = new QueryWrapper<>();
        List<CsTmpReturnCalcDO> list = csTmpReturnCalcMapper.selectList(queryWrapper);

        Date endDate = DateUtil.getMonthFirstDate(new Date());
        Date startDate = DateUtil.addMonth(endDate, -6);
        String path = "template/CalcReturnData.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
//        int orderFreq = 0;
        for (CsTmpReturnCalcDO calcDO : list) {
            cel = 0;
//            orderFreq = 0;
            String name = warehouseMapper.getDeptByNo(calcDO.getDeptNo());
            excel.setCellValue(row, cel++, name); // 分公司
            String deptName = warehouseMapper.getDeptNameByNo(calcDO.getDeptNo());
            excel.setCellValue(row, cel++, deptName); // 营业部
            String deptName2 = warehouseMapper.getDeptNameByNo2(calcDO.getDeptNo());
            excel.setCellValue(row, cel++, deptName2); // 营业所
            excel.setCellValue(row, cel++, calcDO.getAgentNo());
            excel.setCellValue(row, cel++, calcDO.getApplyType() == 1 ? "SMC提案" : "代理提案");
            excel.setCellValue(row, cel++, calcDO.getModelNo());
            excel.setCellValue(row, cel++, calcDO.getQtyOnhand());
            excel.setCellValue(row, cel++, calcDO.getQtyprepare());
            excel.setCellValue(row, cel++, calcDO.getReturningQty());
            excel.setCellValue(row, cel++, calcDO.getOverTimeQty());
            excel.setCellValue(row, cel++, calcDO.getReturnQty());
            excel.setCellValue(row, cel++, calcDO.getLastImpDate());
            excel.setCellValue(row, cel++, calcDO.getMinShipDate());
            excel.setCellValue(row, cel++, calcDO.getOverDay());
//            List<ModelExpFreqVO> orderMoths = csTmpReturnCalcMapper.selectOrderMoths(calcDO.getModelNo(), calcDO.getAgentNo());
//            if (PublicUtil.isNotEmpty(orderMoths)) {
//                // 订货频率
//                ModelExpFreqVO modelExpFreqVO = orderMoths.get(0);
//                if (modelExpFreqVO.getM31() != null && modelExpFreqVO.getM32() != null && modelExpFreqVO.getM33() != null &&
//                        modelExpFreqVO.getM34() != null && modelExpFreqVO.getM35() != null && modelExpFreqVO.getM36() != null) {
//
//                    if (modelExpFreqVO.getM31() > 0) {
//                        orderFreq += 1;
//                    }
//                    if (modelExpFreqVO.getM32() > 0) {
//                        orderFreq += 1;
//                    }
//                    if (modelExpFreqVO.getM33() > 0) {
//                        orderFreq += 1;
//                    }
//                    if (modelExpFreqVO.getM34() > 0) {
//                        orderFreq += 1;
//                    }
//                    if (modelExpFreqVO.getM35() > 0) {
//                        orderFreq += 1;
//                    }
//                    if (modelExpFreqVO.getM36() > 0) {
//                        orderFreq += 1;
//                    }
//                }
//            }
            excel.setCellValue(row, cel++, calcDO.getOrders());
//            Integer customersOf6 = csTmpReturnCalcMapper.getCustomersOf6(calcDO.getAgentNo(), calcDO.getModelNo(), startDate, endDate);
            excel.setCellValue(row, cel++, calcDO.getCustomers());
            excel.setCellValue(row, cel++, calcDO.getEPrice());
            excel.setCellValue(row, cel, calcDO.getIsBinModel() != null && calcDO.getIsBinModel() == 1 ? "是" : "否");

            row++;
        }

        excel.writeToResponse(response, "CalcReturnData.xlsx");
    }

    @Override
    public ResultVo<String> confirmReceiveCsStock(CsStockImportOrderReceiveRequest request) {

        if (PublicUtil.isEmpty(request.getWarehouseCode())) {
            return ResultVo.failure("仓库代码不能为空");
        }
        if (ArrayUtils.isNotEmpty(request.getIds()) && ArrayUtils.isNotEmpty(request.getBarcodes())) {
            return ResultVo.failure("发票入库id和入库条码不可同时输入");
        }
        //查是否是可以入库
        QueryWrapper<CsImpdataDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(request.getAgentNo()), "agent_No", request.getAgentNo());
        queryWrapper.eq(StringUtils.isNotBlank(request.getWarehouseCode()), "warehouse_Code", request.getWarehouseCode());
        if (ArrayUtils.isNotEmpty(request.getBarcodes())) {
            queryWrapper.in("barcode", Arrays.asList(request.getBarcodes()));
        }
        if (ArrayUtils.isNotEmpty(request.getIds())) {
            queryWrapper.in("id", Arrays.asList(request.getIds()));
        }
        queryWrapper.eq("status", 1);
        List<CsImpdataDO> list = csImportDataService.listCSImportDataByWrapper(queryWrapper);
        if (PublicUtil.isEmpty(list)) {
            return ResultVo.failure("无待收货确认订单");
        }

        WmRoConfirmDto wmRoConfirmDto;
        CommonResult roResult;
        for (CsImpdataDO csImpdataDO : list) {
            //写入库存表
            //委托在库使用通用库房
            wmRoConfirmDto = new WmRoConfirmDto();
            //随机码
//            String uuid=  UUID.randomUUID().toString().replace("-", "").toLowerCase();
            wmRoConfirmDto.setReceiveCode(csImpdataDO.getBarcode());
            wmRoConfirmDto.setQty(String.valueOf(csImpdataDO.getQuantity()));//入库数量
            wmRoConfirmDto.setModelNo(csImpdataDO.getModelNo());
            wmRoConfirmDto.setWarehouseCode(csImpdataDO.getWarehouseCode());
            wmRoConfirmDto.setReceiveOrderCode(csImpdataDO.getRoId());//ROID指令，因委托在库没，给流水号
            wmRoConfirmDto.setWmsOrderCode(csImpdataDO.getBarcode());//WMS传的指令，因委托在库没，给流水号（用于收货扫描判断的谓一致）
            wmRoConfirmDto.setScanType("1");//固定值 1箱码扫描 2托盘扫描
            wmRoConfirmDto.setInventoryType("ZP");//固定值 ZP正品
            wmRoConfirmDto.setReceiveTime(new Date());//固定值 ZP正品
            wmRoConfirmDto.setUsername(request.getUserName());//操作人（非工号）
            wmRoConfirmDto.setInvoiceNo(csImpdataDO.getInvoiceNo());
//            roResult = opsWmFeignApi.wmRoConfirm(wmRoConfirmDto);
            roResult = opsWmFeignApi.wmRoConfirmHandle(wmRoConfirmDto); //bug10473 20220414 WuJiaWen
            if (!roResult.isSuccess()) {
                return ResultVo.failure("错误，Id: " + String.valueOf(csImpdataDO.getId()) + ".收货确认出错:" + roResult.getMessage());
            }
            //更变状态
            csImpdataDO.setStatus(2);
            csImpdataDO.setUpdateUser(request.getUserName());
            csImpdataDO.setReceivePsn(SMCApp.getLoginAuthDto().getUserNo());
            csImpdataDO.setReceiveTime(DateUtil.getNow());
            csImpdataDO.setLeftQty(csImpdataDO.getQuantity());
            csImpdataDO.setCostState(0);
            if (!csImportDataService.saveCSImportData(csImpdataDO)) {
                return ResultVo.failure("错误，Id: " + csImpdataDO.getId() + "收货确认出错");
            }
            //写入日志
        }
        return ResultVo.success("收货成功");
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> confirmExpOrder(CsExpDetailConfirmRequest request) {
        log.info("委托在库出库: {}", request);
        if (request.getWarehouseCode() == null) {
            return ResultVo.failure("仓库代码不可为空");
        }

        // 查看是否可以出库
        LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getExpOrderNo() != null && request.getExpOrderNo().length != 0) {
            queryWrapper.in(CsExpdetailDO::getExpOrderNo, Arrays.asList(request.getExpOrderNo()));
        }
        queryWrapper.eq(CsExpdetailDO::getWarehouseCode, request.getWarehouseCode());
        queryWrapper.eq(CsExpdetailDO::getStatus, "1");
        List<CsExpdetailDO> csExpdetailDOS = csExpdetailMapper.selectList(queryWrapper);

        if (csExpdetailDOS.isEmpty()) {
            return ResultVo.failure("暂无待出库数据.");
        }

        WmWTDoConfirmDto dto;
        WmWTDoItemConfirmDto itemDto;
        // 变更状态为2(已出库)
        UserDto userDto;
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        CommonResult confirm;
        Map<String, Integer> map = new HashMap<>();
        CsExpdetailDO expdetailDO;
        List<String> expOrderNos = new ArrayList<>();

        for (CsExpdetailDO item : csExpdetailDOS) {
            if (item.getExpOrderNo().contains("-") && item.getDoId().contains("-")) {
                String[] split = item.getExpOrderNo().split("-");
                if (Integer.valueOf(split[2]) > 0) {
                    String doid = item.getDoId().split("-")[0];
                    String expQty = item.getDoId().split("-")[1];
                    item.setDoId(doid);
                    item.setExpQty(Integer.valueOf(expQty));
                    expOrderNos.add(item.getExpOrderNo());
                }
            }
            expdetailDO = new CsExpdetailDO();
            expdetailDO.setStatus(2);
            expdetailDO.setExpTime(new Date());
            expdetailDO.setUpdateTime(new Date());
            expdetailDO.setId(item.getId());
            expdetailDO.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            if (map.containsKey(item.getDoId())) {
                int i = csExpdetailMapper.updateById(expdetailDO);
                if (i != 1) {
                    return ResultVo.failure(item.getExpOrderNo() + "出库失败.");
                }
                this.deductLeftQty(item);
                continue;
            }
            map.put(item.getDoId(), item.getExpQty());

            userDto = new UserDto();


            dto = new WmWTDoConfirmDto();
            itemDto = new WmWTDoItemConfirmDto();
            dto.setDeliveryOrderCode(item.getDoId());
            dto.setWarehouseCode(item.getWarehouseCode());
            itemDto.setQty(item.getExpQty().doubleValue());
            userDto.setUserName(userDTO.getUserNo());
            userDto.setIp(IpUtil.getIpAddress());
            dto.setItems(itemDto);
            dto.setUserDto(userDto);
            confirm = opsWmFeignApi.wmWTDoConfirm(dto);

            if (!confirm.isSuccess()) {
                log.error(item.getExpOrderNo() + "出库失败：" + confirm.getMessage());
                return ResultVo.failure(item.getExpOrderNo() + "出库失败：" + confirm.getMessage());
            }

            int i = csExpdetailMapper.updateById(expdetailDO);
            if (i != 1) {
                return ResultVo.failure(item.getExpOrderNo() + "出库失败.");
            }
            this.deductLeftQty(item);
        }

        for (String expOrderNo : expOrderNos) {
            queryWrapper.clear();
            String[] split = expOrderNo.split("-");
            queryWrapper.like(CsExpdetailDO::getExpOrderNo, split[0] + "-" + split[1]);
            queryWrapper.eq(CsExpdetailDO::getStatus, 1);
            List<CsExpdetailDO> doList = csExpdetailMapper.selectList(queryWrapper);

            if (CollectionUtil.isNotEmpty(doList)) {
                for (CsExpdetailDO csExpdetailDO : doList) {
                    csExpdetailDO.setStatus(2);
                    csExpdetailMapper.updateById(csExpdetailDO);
                    this.deductLeftQty(csExpdetailDO);
                }
            }
        }

        return ResultVo.success("出库成功,共出库" + csExpdetailDOS.size());
    }

    private void deductLeftQty(CsExpdetailDO expdetailDO) {
        LambdaQueryWrapper<CsImpdataDO> query = new LambdaQueryWrapper<>();
        query.eq(CsImpdataDO::getWarehouseCode, expdetailDO.getWarehouseCode());
        query.eq(CsImpdataDO::getModelNo, expdetailDO.getModelNo());
        query.eq(CsImpdataDO::getStatus, 2);
        query.gt(CsImpdataDO::getLeftQty, 0);
        query.orderByAsc(CsImpdataDO::getShipDate);

        List<CsImpdataDO> doList = csImpdataMapper.selectList(query);

        if (CollectionUtil.isEmpty(doList)) {
            return; //没有数量需要扣减
        }
        Integer quality = expdetailDO.getExpQty();
        for (CsImpdataDO impdataDO : doList) {
            if (quality >= impdataDO.getLeftQty()) {
                quality = quality - impdataDO.getLeftQty();
                impdataDO.setLeftQty(0);
                csImpdataMapper.updateById(impdataDO);
                continue;
            }
            if (quality < impdataDO.getLeftQty()) {
                impdataDO.setLeftQty(impdataDO.getLeftQty() - quality);
                csImpdataMapper.updateById(impdataDO);
                break;
            }
        }
    }

    /**
     * 出库明细清单
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResultVo<PageInfo<CsExportDetailVO>> listCsExportDetail(CsExportDetailRequestDTO requestDTO) {

        LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getModelNo()), CsExpdetailDO::getModelNo, requestDTO.getModelNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getAgentNo()), CsExpdetailDO::getAgentNo, requestDTO.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getWarehouseCode()), CsExpdetailDO::getWarehouseCode, requestDTO.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStatus()), CsExpdetailDO::getStatus, requestDTO.getStatus());
        queryWrapper.likeRight(PublicUtil.isNotEmpty(requestDTO.getExpOrderNo()), CsExpdetailDO::getExpOrderNo, requestDTO.getExpOrderNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getUserNo()), CsExpdetailDO::getUserNo, requestDTO.getUserNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getCorderNo()), CsExpdetailDO::getCorderNo, requestDTO.getCorderNo());

        if (PublicUtil.isEmpty(requestDTO.getStatus())) {
            queryWrapper.ne(CsExpdetailDO::getStatus, 4);
        }

        if (PublicUtil.isEmpty(requestDTO.getDateType()) && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            requestDTO.setDateType(2);
        }

        if (PublicUtil.isNotEmpty(requestDTO.getBeginDate()) && PublicUtil.isNotEmpty(requestDTO.getDateType())) {
            if (requestDTO.getDateType() == 1) {
                queryWrapper.between(CsExpdetailDO::getCreateTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
            }
            if (requestDTO.getDateType() == 2) {
                queryWrapper.between(CsExpdetailDO::getExpTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
            }
        }

        if (StringUtils.isNotBlank(requestDTO.getMonthDate())) {
            String monthDate = requestDTO.getMonthDate();
            String[] split = monthDate.split("-");
            String firstDayOfMonth1 = DateUtil.getFirstDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            String lastDayOfMonth1 = DateUtil.getLastDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Date endTime = DateUtil.getEndTime(DateUtil.stringToDate(lastDayOfMonth1));
            queryWrapper.ge(CsExpdetailDO::getExpTime, firstDayOfMonth1);
            queryWrapper.le(CsExpdetailDO::getExpTime, endTime);
        }

        if (null != requestDTO.getDataAuthoritySearchCondition()) {
            DataAuthoritySearchCondition authoritySearchCondition = requestDTO.getDataAuthoritySearchCondition();
            if (PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()) || PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority())) {
                queryWrapper.and(wrapper -> wrapper.in(PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority()), CsExpdetailDO::getUserNo, authoritySearchCondition.getUserIdListByDataAuthority())
                        .or(PublicUtil.isNotEmpty(authoritySearchCondition.getUserIdListByDataAuthority()) || PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()))
                        .in(PublicUtil.isNotEmpty(authoritySearchCondition.getCustomerCodeListByDataAuthority()), CsExpdetailDO::getAgentNo, authoritySearchCondition.getCustomerCodeListByDataAuthority()));
            }
//            log.info("出库查询："+authoritySearchCondition.toString());
        }
        queryWrapper.orderByDesc(CsExpdetailDO::getExpOrderNo, CsExpdetailDO::getExpTime, CsExpdetailDO::getWarehouseCode);
        log.info("出库明细接口参数: {}", JSONObject.toJSONString(requestDTO));
        PageInfo<CsExpdetailDO> pageInfo = PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize()).doSelectPageInfo(
                () -> csExpdetailMapper.selectList(queryWrapper));

        PageInfo<CsExportDetailVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsExportDetailVO.class);
        Map<String, CustomerVO> customerMap = new HashMap<>();
        Map<String, String> nameMap = new HashMap<>();
        CustomerVO customerVO;

        if (!voPageInfo.getList().isEmpty()) {
            for (CsExportDetailVO item : voPageInfo.getList()) {
                // 客户名称
                if (StringUtils.isNotBlank(item.getAgentNo())) {
                    if (customerMap.containsKey(item.getAgentNo())) {
                        item.setAgentName(customerMap.get(item.getAgentNo()).getName());
                        item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerMap.get(item.getAgentNo()).getCustomerType()).orElse("")));
                    } else {
                        customerVO = commonService.getCustomerInfoByNo(item.getAgentNo());
                        if (customerVO != null) {
                            item.setAgentName(customerVO.getName());
                            item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerVO.getCustomerType()).orElse("")));
                            customerMap.put(item.getAgentNo(), customerVO);
                        }
                    }
                }
                // 设置库房名称
                if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
                    nameMap.put(item.getWarehouseCode(), commonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
                item.setWareHourseName(nameMap.get(item.getWarehouseCode()));
                // 设置用户名称
                if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
                    nameMap.put(item.getUserNo(), commonService.getCustomerNameByNo(item.getUserNo()));
                }
                item.setUserName(nameMap.get(item.getUserNo()));
            }
        }
        return ResultVo.success(voPageInfo);
    }


    /**
     * 委托在库库存
     *
     * @param request
     * @return
     */
    @Override
    public ResultVo<PageInfo<CsInventoryVO>> listCsStockInventory(CsInventoryRequestDTO request, Page page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());

        log.info("委托在库库存接口参数 : {}", JSONObject.toJSONString(request));

        List<CsInventoryVO> csInventoryVOS = csInventoryMapper.listCsInventory(request);
        if (CollectionUtils.isNotEmpty(csInventoryVOS)) {
            Map<String, String> nameMap = new HashMap<>(csInventoryVOS.size());
            for (CsInventoryVO item : csInventoryVOS) {
                // 客户名称
                if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
                    nameMap.put(item.getUserNo(), commonService.getCustomerNameByNo(item.getUserNo()));
                }
                if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
                    nameMap.put(item.getCustomerNo(), commonService.getCustomerNameByNo(item.getCustomerNo()));
                }
                item.setUserName(nameMap.get(item.getUserNo()));
                item.setCustomerName(nameMap.get(item.getCustomerNo()));
                // 仓位号
                item.setCwcode(Optional.ofNullable(item.getWarehouseCode()).orElse(""));
                // 在库数
                item.setOnHouseNum(item.getQuantity());
                // 预约数
                item.setAppointmentNum(Optional.ofNullable(item.getPrepareQuantity()).orElse(0));
                // 可订货数
                item.setOrderQuantity(item.getQuantity() - item.getPrepareQuantity());
            }
        }
        PageInfo<CsInventoryVO> info = PageInfo.of(csInventoryVOS);
        return ResultVo.success(info);
    }


    /**
     * 月次结存
     *
     * @param request
     * @return
     */
    @Override
    public ResultVo<PageInfo<CsModelBalanceVO>> listMonthBalance(CsBalcenQryRequest request) {
        Page page = request.getPage();
        if (page == null) {
            page = new Page();
            page.setPageNumber(1);
            page.setPageSize(20);
        }
        log.info("月次结存查询参数: {}", JSON.toJSONString(request));
        LambdaQueryWrapper<CsModelBalanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getAgentNo()), CsModelBalanceDO::getAgentNo, request.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), CsModelBalanceDO::getWarehouseCode, request.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getModelNo()), CsModelBalanceDO::getModelNo, request.getModelNo());
        if (PublicUtil.isNotEmpty(request.getMonthDate())) {
//            Date date = DateUtil.addMonth(request.getMonthDate(), 1);
//            String yearMonth = DateUtil.getYearMonth(date);
            Date monthFirstDate = DateUtil.getMonthFirstDate(request.getMonthDate());
            Date endTime = DateUtil.getMonthEndDate(request.getMonthDate());
            queryWrapper.ge(CsModelBalanceDO::getMonthDate, monthFirstDate);
            queryWrapper.le(CsModelBalanceDO::getMonthDate, endTime);
        }
        PageInfo<CsModelBalanceDO> info = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> csBalanceMapper.selectList(queryWrapper));
        PageInfo<CsModelBalanceVO> csModelBalanceVOPageInfo = BeanCopyUtil.pageDto2Vo(info, CsModelBalanceVO.class);
        List<CsModelBalanceVO> list = csModelBalanceVOPageInfo.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, CustomerVO> customerMap = new HashMap<>();
            Map<String, String> nameMap = new HashMap<>();
            CustomerVO customerVO;
            for (CsModelBalanceVO item : list) {
                // 设置库房名称
                if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
                    nameMap.put(item.getWarehouseCode(), commonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
                item.setWareHourseName(nameMap.get(item.getWarehouseCode()));
                // 客户名称
                if (StringUtils.isNotBlank(item.getAgentNo())) {
                    if (customerMap.containsKey(item.getAgentNo())) {
                        item.setAgentName(customerMap.get(item.getAgentNo()).getName());
                        item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerMap.get(item.getAgentNo()).getCustomerType()).orElse("")));
                    } else {
                        customerVO = commonService.getCustomerInfoByNo(item.getAgentNo());
                        if (customerVO != null) {
                            item.setAgentName(customerVO.getName());
                            // 客户类型
                            item.setCustomerTypeName(CstmTypeEnum.getTypeName(Optional.ofNullable(customerVO.getCustomerType()).orElse("")));
                            customerMap.put(item.getAgentNo(), customerVO);
                        }
                    }
                }
            }
        }
        return ResultVo.success(csModelBalanceVOPageInfo);
    }


    /**
     * 同步更新型号信息（是否BIN品，是否LOT价，是否拆分，最小包装单位，近半年接单总和，订货频率，客户计数，月均数量，现有在库数，预约数量，在途数量，补库数量，保有月数）
     * 同步到cs_stock_setting
     *
     * @return
     */
    @Override
    public ResultVo<String> syncCsModelInfo() {
        return ResultVo.success("同步成功");
    }

    /**
     * 同步数据到委托在库入库清单表
     * 同步ops_core.expdetail to ops_sharedb.cs_impdata
     *
     * @return
     */
    @Override
    public ResultVo<String> syncCsImportData() {
        //this.csImpdataMapper.insertCsImpData();
        return ResultVo.failure("无需再同步由物流模块直接写入");
    }

    @Override
    public ResultVo<List<CsReturnCalcVo>> listCalcReturn(String agentNo, String warehouseCode, Integer calcType) {
        if (calcType == 1) {
            this.csReturnApplyMapper.CalcCsReturnAll();
            return ResultVo.success();
        } else {
            LambdaQueryWrapper<CsTmpReturnCalcDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CsTmpReturnCalcDO::getWarehouseCode, warehouseCode);
            queryWrapper.eq(CsTmpReturnCalcDO::getAgentNo, agentNo);
            List<CsTmpReturnCalcDO> list = this.csTmpReturnCalcMapper.selectList(queryWrapper);
            List<CsReturnCalcVo> csReturnCalcVos = BeanCopyUtil.copyList(list, CsReturnCalcVo.class);
            return ResultVo.success(csReturnCalcVos);
        }
    }

    @Override
    public ResultVo<PageInfo<CsTmpReturnVO>> listCalcReturnMaster(CsTmpReturnDTO dto) {
        if (dto.getCalcType() == 1) {
            this.csReturnApplyMapper.CalcCsReturnAll();
            return ResultVo.success();
        } else {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
            List<CsTmpReturnVO> list = csTmpReturnCalcMapper.listCsTmpReturnData(dto.getAgentNo(), dto.getWarehouseCode(), dto.getDeptNo());
            PageInfo<CsTmpReturnVO> pageInfo = PageInfo.of(list);

            return ResultVo.success(pageInfo);
        }
    }

    @Override
    public ResultVo<PageInfo<CsReturnCalcVo>> pageListCalcReturn(CalcReturnRequest request) {
        LambdaQueryWrapper<CsTmpReturnCalcDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), CsTmpReturnCalcDO::getWarehouseCode, request.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getAgentNo()), CsTmpReturnCalcDO::getAgentNo, request.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getApplyType()), CsTmpReturnCalcDO::getApplyType, request.getApplyType());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getModelNo()), CsTmpReturnCalcDO::getModelNo, request.getModelNo());
        queryWrapper.gt(request.getOverTimeQty(), CsTmpReturnCalcDO::getOverTimeQty, 0);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<CsTmpReturnCalcDO> list = this.csTmpReturnCalcMapper.selectList(queryWrapper);

        PageInfo<CsTmpReturnCalcDO> pageInfo = PageInfo.of(list);
        PageInfo<CsReturnCalcVo> returnCalcVoPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsReturnCalcVo.class);
        return ResultVo.success(returnCalcVoPageInfo);
    }

    /**
     * 生成退货数据
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> createReturnApply(String agentNo, String warehouseCode) {
        //判断库房有效性及归属营业所
        WarehouseDO warehourse = warehouseService.getCsWarehouseDO(agentNo, warehouseCode);
        if (PublicUtil.isEmpty(warehourse)) {
            return ResultVo.failure(String.format("客户代码 %s 库房不存在 %s", agentNo, warehouseCode));
        }
        if (PublicUtil.isEmpty(warehourse.getDeptNo())) {
            return ResultVo.failure(String.format("库房. %s %s,无营业所代码，请设置营业代码", agentNo, warehouseCode));
        }
        LambdaQueryWrapper<CsTmpReturnCalcDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsTmpReturnCalcDO::getWarehouseCode, warehouseCode);
        queryWrapper.eq(CsTmpReturnCalcDO::getAgentNo, agentNo);
        queryWrapper.gt(CsTmpReturnCalcDO::getReturnQty, 0);
        List<CsTmpReturnCalcDO> calcDOList = csTmpReturnCalcMapper.selectList(queryWrapper);

        if (calcDOList == null) {
            return ResultVo.failure("未查询到数据");
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        CsReturnApplyDO returnApplyDO = new CsReturnApplyDO();
        returnApplyDO.setAgentNo(agentNo);
        returnApplyDO.setWarehouseCode(warehouseCode);
        ResultVo<String> parentCode = commonServiceFeignApi.getWarehouseParentCode(warehouseCode);
        returnApplyDO.setToWareouseCode(parentCode.getData());
        returnApplyDO.setModelQty(calcDOList.stream().mapToInt(CsTmpReturnCalcDO::getReturnQty).sum());
        returnApplyDO.setTotalQty(calcDOList.size());
        returnApplyDO.setDeptNo(warehourse.getDeptNo());
        returnApplyDO.setApplyTime(new Date());
        returnApplyDO.setCreateUser(userDTO.getUserNo());
        returnApplyDO.setStatus(2);
        if (returnApplyDO.getTotalQty() == 0) {
            return ResultVo.failure("不可退0个");
        }
        int result = this.csReturnApplyMapper.insert(returnApplyDO);
        CsReturnDetailDO csReturnDetailDO;

        int itemNo = 1;
        String orderNo = "RC" + String.format("%06d", returnApplyDO.getApplyId());

        for (CsTmpReturnCalcDO csReturnCalcVo : calcDOList) {
            csReturnDetailDO = new CsReturnDetailDO();
            csReturnDetailDO.setApplyId(returnApplyDO.getApplyId());
            csReturnDetailDO.setModelNo(csReturnCalcVo.getModelNo());
            csReturnDetailDO.setQuantity(csReturnCalcVo.getReturnQty());
            csReturnDetailDO.setStatus(2);
            csReturnDetailDO.setReturnOrderNo(orderNo);
            csReturnDetailDO.setItemNo(itemNo);
            csReturnDetailDO.setApplyType(csReturnCalcVo.getApplyType());
            csReturnDetailDO.setCreateUser(userDTO.getUserNo());
            this.csReturnDetailMapper.insert(csReturnDetailDO);

            itemNo++;
        }

//        returnOrderService.saveReturnOrder(orderVOList);
        return ResultVo.success("生成成功.申请号：" + returnApplyDO.getApplyId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> confirmCsReturnApply(Integer applyId) {
        CsReturnApplyDO csReturnApplyDO = csReturnApplyMapper.selectById(applyId);

        LambdaQueryWrapper<CsReturnDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsReturnDetailDO::getApplyId, applyId);
        List<CsReturnDetailDO> doList = csReturnDetailMapper.selectList(queryWrapper);

        ReturnOrderVO orderVO;
        List<ReturnOrderVO> orderVOList = new ArrayList<>();
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        TransOrderVO transOrder;
        List<TransOrderVO> voList = new ArrayList<>();
//        TransOrderVO transOrder = null;
//        List<TransOrderVO> voList = new ArrayList<>();
        String applyNo = "CS-" + csReturnApplyDO.getApplyId();
        Date now = new Date();
//        Object savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        for (CsReturnDetailDO csReturnDetailDO : doList) {
            if (csReturnDetailDO.getQuantity() <= 0) {
                continue;
            }
            transOrder = new TransOrderVO();
            orderVO = new ReturnOrderVO();
            orderVO.setApplyNo(applyNo);
            orderVO.setStatus(1);
            orderVO.setModelNo(csReturnDetailDO.getModelNo());
            orderVO.setOrderQty(csReturnDetailDO.getQuantity());
//            orderVO.setReturnQty(csReturnDetailDO.getQuantity());
            orderVO.setApplyQty(csReturnDetailDO.getQuantity());
            if (PublicUtil.isEmpty(csReturnApplyDO.getApplyPerson())) {
                orderVO.setReason("退货");
            } else {
                orderVO.setReason(csReturnApplyDO.getApplyPerson());
            }
            orderVO.setDeptNo(csReturnApplyDO.getDeptNo());
            orderVO.setReturnFee(BigDecimal.ZERO);
            orderVO.setApplyTimes(1);
            orderVO.setOrderNo(csReturnDetailDO.getReturnOrderNo() + "-" + csReturnDetailDO.getItemNo());
            orderVO.setItemNo(csReturnDetailDO.getItemNo());
            orderVO.setApplyTime(now);
            orderVO.setApplicant(userDTO.getUserNo());
            orderVO.setCreateUser(userDTO.getUserNo());
            orderVO.setCustomerNo(csReturnApplyDO.getAgentNo());
            // 1 true 0 false
            orderVO.setToUserStock(0);
            orderVO.setOrderType(3);
            orderVO.setExpStocktype(String.valueOf(csReturnDetailDO.getApplyType()));
            orderVO.setExpStockCode(csReturnApplyDO.getWarehouseCode());
            orderVO.setRequestWarehouseCode(csReturnApplyDO.getToWareouseCode());
//            orderVO.setRcvWarehouseCode(csReturnApplyDO.getToWareouseCode());
            orderVOList.add(orderVO);

//             调库数据
            transOrder.setTransNo(csReturnDetailDO.getReturnOrderNo());
            transOrder.setTransType(1);
            transOrder.setItemNo(csReturnDetailDO.getItemNo());
            transOrder.setModelNo(csReturnDetailDO.getModelNo());
            transOrder.setQuantity(csReturnDetailDO.getQuantity());
            transOrder.setStatus(1);
            //transOrder.setFromInventoryPropertyId(1L);
            //transOrder.setToInventoryPropertyId(1L);
            transOrder.setFromInventoryTypeCode("GK-TY");
            transOrder.setFromCustomerNo(csReturnApplyDO.getAgentNo());
            transOrder.setToInventoryTypeCode("GK-TY");
            transOrder.setToCustomerNo(csReturnApplyDO.getAgentNo());
            transOrder.setFromNo(csReturnDetailDO.getReturnOrderNo());
            transOrder.setFromId(csReturnDetailDO.getId().longValue());
            transOrder.setFromType(2);
            transOrder.setFromWarehouseCode(csReturnApplyDO.getWarehouseCode());
            transOrder.setToWarehouseCode(csReturnApplyDO.getToWareouseCode());
            voList.add(transOrder);

        }

        ResultVo<String> resultVo = returnOrderService.saveReturnOrder(orderVOList);
        if (!resultVo.isSuccess()) {
            log.error("生成退货订单时出错:" + resultVo.getMessage());
            throw new RuntimeException(resultVo.getMessage());
        }
        //此接口为批量数量一个事务，如果单条失败，则全部回滚
        ResultVo<String> transStock = transStockFeignApi.transStockAll(voList);
        if (transStock.isSuccess()) {
            csReturnApplyDO.setStatus(3);
            csReturnApplyMapper.updateById(csReturnApplyDO);
            this.sendCsReturnApplyToEmail(applyId);
            return ResultVo.success("生成退货订单成功,申请号：" + applyNo);
        } else {
            log.error("调库时出错:" + resultVo.getMessage());
            throw new RuntimeException(transStock.getMessage());
        }
    }

    /**
     * 将退货PDF文件发送至邮箱
     *
     * @return
     */
    @Override
    public ResultVo<String> sendCsReturnApplyToEmail(Integer applyId) {
        ResultVo<PrintCsReturnDTO> pageData = this.printCsReturnByApplyId(applyId);
//        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        if (pageData == null) {
            return ResultVo.failure();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("img", Constants.IMG);
        map.put("applyId", pageData.getData().getApplyId());
        map.put("passTime", DateUtil.dateToDateTimeString(pageData.getData().getPrintTime()));
        map.put("returnCompany", pageData.getData().getReturnCompany());
        map.put("printTime", DateUtil.dateToDateTimeString(pageData.getData().getPrintTime()));
        map.put("contactPsn", pageData.getData().getContactPsn());
        map.put("contactTelno", pageData.getData().getContactTelno());
        map.put("warehouseName", pageData.getData().getWarehouseName());
        map.put("adress", pageData.getData().getAdress());
        map.put("linkman", pageData.getData().getLinkman());
        map.put("smcPhone", pageData.getData().getSmcPhone());
        PrintCsReturnDTO printCsReturnDTO = new PrintCsReturnDTO();
        printCsReturnDTO.setCsReturnData(pageData.getData().getCsReturnData());
        List<PrintCsReturnDTO> printCsReturnDTOS = new ArrayList<>(1);
        printCsReturnDTOS.add(printCsReturnDTO);
        InputStream inputStream = FileUtil.getTemplate("template/jasper/csReturnApply.jasper");
//        Map<String, Object> params = new HashMap<>();
        ResultVo<DataTypeVO> codesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "CS2");
//        ResultVo<EmployeeVO> info = commonServiceFeignApi.getEmployeeInfo(userDTO.getUserNo());
        String to = codesInfo.getData().getExtNote1();
        String cc = codesInfo.getData().getExtNote2();
        String tc = codesInfo.getData().getExtNote3();
//        String user = info.isSuccess() ? info.getData().getEmail() : "";
        try {
            String fileName = "服务备库退货申请.pdf";
            InputStream pdfToInputStrem = JasperHelper.savePdfToInputStrem(inputStream, map, printCsReturnDTOS);
            String subject = "服务备库退货申请";
            StringBuilder sb = new StringBuilder();
            sb.append("<span>您好：</span></br>");
            sb.append("<span>服务备库退货申请</span></br>");
            sb.append("<span>详情请见附件: [").append(fileName).append("]</span></br>");
            sb.append("本邮件由OPS系统自动发送，请勿直接回复本邮件");
            Map<String, InputStream> attachment = new LinkedHashMap<>(1);
            attachment.put(fileName, pdfToInputStrem);
            EmailUtil.send(javaMailSender, to, cc + ";" + tc, null, subject, sb.toString(), attachment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVo.success("成功");
    }


    /**
     * 查询退货申请清单
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResultVo<PageInfo<CsReturnApplyVO>> listReturnApply(CsReturnApplyRequestDTO requestDTO) {
        // add by 伍家闻 2022/11/10 bug 8626
        LambdaQueryWrapper<CsReturnApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getAgentNo()), CsReturnApplyDO::getAgentNo, requestDTO.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStockCode()), CsReturnApplyDO::getWarehouseCode, requestDTO.getStockCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStatus()), CsReturnApplyDO::getStatus, requestDTO.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getApplyId()), CsReturnApplyDO::getApplyId, requestDTO.getApplyId());
        if (PublicUtil.isNotEmpty(requestDTO.getFromDate()) && PublicUtil.isNotEmpty(requestDTO.getToDate())) {
            queryWrapper.between(CsReturnApplyDO::getCreateTime, requestDTO.getFromDate(), requestDTO.getToDate());
        }
        PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<CsReturnApplyDO> list = this.csReturnApplyMapper.selectList(queryWrapper);
        PageInfo<CsReturnApplyDO> pageInfo = PageInfo.of(list);
        PageInfo<CsReturnApplyVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsReturnApplyVO.class);
        return ResultVo.success(voPageInfo);
    }

    /**
     * 退货申请
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResultVo<PageInfo<CsReturnDetailVO>> listCsReturnApplyDetail(CsReturnDetailRequestDTO requestDTO) {
        LambdaQueryWrapper<CsReturnDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsReturnDetailDO::getApplyId, requestDTO.getApplyId());
        List<CsReturnDetailDO> list = this.csReturnDetailMapper.selectList(queryWrapper);
        PageInfo<CsReturnDetailDO> pageInfo = PageInfo.of(list);
        PageInfo<CsReturnDetailVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsReturnDetailVO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public List<CsReturnDetailVO> listCsReturnDetailByApplyId(Integer applyId) {
        LambdaQueryWrapper<CsReturnDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsReturnDetailDO::getApplyId, applyId);
        List<CsReturnDetailDO> list = this.csReturnDetailMapper.selectList(queryWrapper);
        List<CsReturnDetailVO> voPageInfo = BeanCopyUtil.copyList(list, CsReturnDetailVO.class);
        return voPageInfo;
    }

    @Override
    public ResultVo<PrintCsReturnDTO> printCsReturnByApplyId(Integer applyId) {
        CsReturnApplyDO csReturnApplyDO = this.csReturnApplyMapper.selectById(applyId);
        if (PublicUtil.isEmpty(csReturnApplyDO)) {
            return ResultVo.failure(String.format("申请号 %s 不存在", applyId));
        }
        WarehouseDO warehouseDO = warehouseService.getCsWarehouseDO(csReturnApplyDO.getAgentNo(), csReturnApplyDO.getWarehouseCode());
        if (PublicUtil.isEmpty(warehouseDO)) {
            return ResultVo.failure(String.format("申请号 %s 不存在", applyId));
        }
        WarehouseDO toWarehouseDO = warehouseService.getWarehouseInfoByCode(csReturnApplyDO.getToWareouseCode());

        PrintCsReturnDTO printCsReturnDTO = new PrintCsReturnDTO();
        printCsReturnDTO.setApplyId("CS-" + String.valueOf(applyId));
        printCsReturnDTO.setContactPsn(warehouseDO.getCustomerLinkman());
        printCsReturnDTO.setContactTelno(warehouseDO.getCustomerPhone());
        printCsReturnDTO.setReturnCompany(warehouseDO.getWarehouseName());
        if (toWarehouseDO != null) {
            printCsReturnDTO.setLinkman(toWarehouseDO.getRcvLinkman());
            printCsReturnDTO.setWarehouseName(toWarehouseDO.getWarehouseName());
            printCsReturnDTO.setAdress(toWarehouseDO.getAddress());
            printCsReturnDTO.setSmcPhone(toWarehouseDO.getRcvLinkTel());
        }
        printCsReturnDTO.setPrintTime(DateUtil.getNow());
        List<CsReturnDetailVO> csReturnDetailVOS = listCsReturnDetailByApplyId(applyId);
        CsReturnPrintDTO dto;
        List<CsReturnPrintDTO> list = new ArrayList<>();
        for (CsReturnDetailVO vo : csReturnDetailVOS) {
            if (vo.getQuantity() == 0) {
                continue;
            }
            dto = new CsReturnPrintDTO();
            dto.setOrderNo(vo.getReturnOrderNo() + "-" + vo.getItemNo());
            dto.setReturnQty(vo.getQuantity());
            dto.setModelNo(vo.getModelNo());
            dto.setRemark(vo.getRemark() == null ? "" : vo.getRemark());
            list.add(dto);
        }
        printCsReturnDTO.setCsReturnData(list);
        return ResultVo.success(printCsReturnDTO);
    }

    @Override
    public void printBalance(CsBalcenQryRequest request, HttpServletResponse response) {
        ResultVo<PageInfo<CsModelBalanceVO>> resultVo = this.listMonthBalance(request);

        if (resultVo == null || !resultVo.isSuccess()) {
            return;
        }
        WarehouseDO csWarehouse = warehouseService.getCsWarehouseDO(request.getAgentNo(), request.getWarehouseCode());
        if (csWarehouse == null) {
            return;
        }
        List<PrintCsBalanceVO> csBalanceData = BeanCopyUtil.copyList(resultVo.getData().getList(), PrintCsBalanceVO.class);
        for (PrintCsBalanceVO vo : csBalanceData) {
            if (PublicUtil.isEmpty(vo.getRemark())) {
                vo.setRemark("");
            }
        }
        PrintCsBalanceDTO csBalanceDTO = new PrintCsBalanceDTO();
        csBalanceDTO.setCsBalanceData(csBalanceData);
        Map<String, Object> params = new HashMap<>();
        params.put("warehouseName", csWarehouse.getWarehouseName());
        Date date = DateUtil.addMonth(request.getMonthDate(), 1);
        params.put("monthDate", DateUtil.getYearMonth(date));
        params.put("printTime", DateUtil.getStringToday());
        params.put("img", Constants.IMG);
        List<PrintCsBalanceDTO> list = new ArrayList<>(1);
        list.add(csBalanceDTO);
        InputStream inputStream = FileUtil.getTemplate("template/jasper/csBalance.jasper");
        try {
            String fileName = "服务备库月次报告.pdf";
            JasperHelper.showPdf(fileName, inputStream, response, params, list);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultVo<List<CsModelInfoVO>> listCSModelInfo(CsModelQryRequest csModelQryRequest) {
        log.info(csModelQryRequest.toString());
        if (csModelQryRequest == null) {
            return ResultVo.failure("参数不可为空.");
        }

        /*if (csModelQryRequest.getCalcType() == null) {
            return ResultVo.failure("计算类型不可为空");
        }*/

        if (StringUtils.isBlank(csModelQryRequest.getWarehouseCode()) && StringUtils.isBlank(csModelQryRequest.getCustomerNo())) {
            return ResultVo.failure("仓库代码和客户代码 两者不可都为空");
        }

        Date endDate = DateUtil.getMonthFirstDate(new Date());
        Date startDate = DateUtil.addMonth(endDate, -6);

        List<CsModelInfoVO> list = new ArrayList<>(csModelQryRequest.getModelNos().length);
        ResultVo<List<ModelExpFreqVO>> modelExpFreq;
        CsModelInfoVO csModelInfoVO;
        ResultVo<Boolean> binModel;
        Boolean canSplit;
        ResultVo<ProductInfoVO> resultVo;
        for (String modelNo : csModelQryRequest.getModelNos()) {
            csModelInfoVO = new CsModelInfoVO();

            csModelInfoVO.setModelNo(modelNo);

            // 是否Bin
//            binModel = binServiceFeignApi.isBinModel(modelNo);
//            csModelInfoVO.setIsBinData(binModel.getData());
            Integer count = productBomMapper.getBinCountByModelNo(modelNo);
            csModelInfoVO.setIsBinData(count > 0 ? true : false);

            // 是否拆分
            canSplit = productBomService.isCanSplit(modelNo);
            csModelInfoVO.setIsSplit(canSplit);


            //最小包装数量
            Integer minPackUnit = productBomMapper.getMinPackUnit(modelNo);
            csModelInfoVO.setMinPackageUnit(Optional.ofNullable(minPackUnit).orElse(0));
//            resultVo = productServiceFeignApi.getProductInfoByModelNo(modelNo);
//            if (resultVo.isSuccess()) {
//                csModelInfoVO.setMinPackageUnit(resultVo.getData().getMinPackUnit());
//            }

            // 逾期数量
            Integer overTimeQty = csTmpReturnCalcMapper.selectOverTimeQty(csModelQryRequest.getWarehouseCode(), csModelQryRequest.getCustomerNo(), modelNo);
            csModelInfoVO.setOverTimeQty(Optional.ofNullable(overTimeQty).orElse(0));

            int orderFreq = 0;
            ModelExpFreqRequest freqRequest=new ModelExpFreqRequest();
            freqRequest.setModelTYpe("1");
            freqRequest.setModelNo(modelNo);
            freqRequest.setStockcode( csModelQryRequest.getCustomerNo());
            freqRequest.setStockType(4);

            modelExpFreq = binServiceFeignApi.getModelExpFreq(freqRequest);
            if (!modelExpFreq.isSuccess()) {
                freqRequest.setStockType(3);
                modelExpFreq = binServiceFeignApi.getModelExpFreq(freqRequest);
            }
            csModelInfoVO.setSumRcvOrderNum(0);
            csModelInfoVO.setFreq(0);
            csModelInfoVO.setCustomersCount(0);
            csModelInfoVO.setAvgQtyCount(BigDecimal.ZERO);
            if (modelExpFreq.isSuccess()) {
                ModelExpFreqVO modelExpFreqVO = modelExpFreq.getData().get(0);
                // 近半年接单总和
                if (modelExpFreqVO.getM31() != null && modelExpFreqVO.getM32() != null && modelExpFreqVO.getM33() != null &&
                        modelExpFreqVO.getM34() != null && modelExpFreqVO.getM35() != null && modelExpFreqVO.getM36() != null) {
                    csModelInfoVO.setSumRcvOrderNum(modelExpFreqVO.getM31() + modelExpFreqVO.getM32() + modelExpFreqVO.getM33()
                            + modelExpFreqVO.getM34() + modelExpFreqVO.getM35() + modelExpFreqVO.getM36());

                    // 订货频率
                    if (modelExpFreqVO.getM31() > 0) {
                        orderFreq += 1;
                    }
                    if (modelExpFreqVO.getM32() > 0) {
                        orderFreq += 1;
                    }
                    if (modelExpFreqVO.getM33() > 0) {
                        orderFreq += 1;
                    }
                    if (modelExpFreqVO.getM34() > 0) {
                        orderFreq += 1;
                    }
                    if (modelExpFreqVO.getM35() > 0) {
                        orderFreq += 1;
                    }
                    if (modelExpFreqVO.getM36() > 0) {
                        orderFreq += 1;
                    }
                    csModelInfoVO.setFreq(orderFreq);
                }

                // 客户计数[该代理店近半年交易的用户个数]
                Integer customersOf6 = csTmpReturnCalcMapper.getCustomersOf6(csModelQryRequest.getCustomerNo(), modelNo, startDate, endDate);
                csModelInfoVO.setCustomersCount(customersOf6);
//                if (modelExpFreqVO.getCustomersOf12() != null) {
//                    Double ceil = Math.ceil(BigDecimalUtil.div(modelExpFreqVO.getCustomersOf12(), 2, 1).doubleValue());
//                    csModelInfoVO.setCustomersCount(ceil.intValue());
//                }
                // 月均数量
                BigDecimal div = BigDecimalUtil.div(csModelInfoVO.getSumRcvOrderNum(), 6, 2);
                double floor = Math.floor(div.doubleValue());
                csModelInfoVO.setAvgQtyCount(BigDecimal.valueOf(floor));
//                if (modelExpFreqVO.getAvgQtyOf12() != null) {
//                    csModelInfoVO.setAvgQtyCount(modelExpFreqVO.getAvgQtyOf12().divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP));
//                }
            }
            // 预约数量
            csModelInfoVO.setAppointmentNum(0);
            csModelInfoVO.setOnHourseNum(0);
            csModelInfoVO.setOnWayNum(0);
            csModelInfoVO.setMonths(0);
            LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CsStockSettingDO::getCustomerNo, csModelQryRequest.getCustomerNo());
            queryWrapper.eq(CsStockSettingDO::getModelNo, modelNo);
            CsStockSettingDO settingDO = csStockSettingMapper.selectOne(queryWrapper);
            if (settingDO != null) {
                csModelInfoVO.setOnWayNum(Optional.ofNullable(settingDO.getTransQty()).orElse(0));
            }

            List<OpsInventoryVO> inventoryVOList = productBomMapper.listInventoryQtyByModelNo(modelNo, csModelQryRequest.getWarehouseCode());
//            ResultVo<List<OpsInventoryVO>> inventQtyByModelNo = inventoryServiceFeignApi.findInventQtyByModelNo(csModelQryRequest.getWarehouseCode(), modelNo);
            if (CollectionUtil.isEmpty(inventoryVOList)) {
//                List<OpsInventoryVO> inventoryVOList = inventQtyByModelNo.getData();
                Integer sumQty = inventoryVOList.stream().mapToInt(OpsInventoryVO::getQuantity).sum();
                Integer sumPreQty = inventoryVOList.stream().mapToInt(OpsInventoryVO::getPrepareQuantity).sum();
                // 在库数
                csModelInfoVO.setOnHourseNum(sumQty - sumPreQty);
                // 预约数量
                csModelInfoVO.setAppointmentNum(sumPreQty);
                // 在途数量  初始化0(暂时不查)
//                Integer onHand = productBomMapper.getModelInventoryOnHand(csModelInfoVO.getModelNo(), csModelQryRequest.getCustomerNo());

                // 获取备库数
                ResultVo<CsStockSettingDTO> csStockSettingDTOResultVo = fingInitStockQtyByModelNo(modelNo, csModelQryRequest.getWarehouseCode());
                if (csStockSettingDTOResultVo.isSuccess()) {
                    Integer initStockQty = csStockSettingDTOResultVo.getData().getInitStockQty();
                    // 补库数量  在库+在途  <init_stock_qty*80%  则补货=init_stock_qty 反之 0
                    Integer replQty = this.calcCsReplQty(csModelInfoVO.getOnHourseNum(), initStockQty, csStockSettingDTOResultVo.getData().getInitUnitQty());
                    csModelInfoVO.setReplQty(replQty);
                    /*if ( (csModelInfoVO.getOnHourseNum() + csModelInfoVO.getOnWayNum())< initStockQty*0.8 ) {
                        csModelInfoVO.setReplQty(initStockQty);
                    } else {
                        csModelInfoVO.setReplQty(0);
                    }*/
                }
                // 保有月数   (在库数-预约+补库+在途数) / 月用量
//                if (csModelInfoVO.getAvgQtyCount() != null && csModelInfoVO.getAvgQtyCount().compareTo(BigDecimal.ZERO) != 0) {
//                    BigDecimal sub = BigDecimalUtil.sub(Optional.ofNullable(csModelInfoVO.getOnHourseNum()).orElse(0), Optional.ofNullable(sumPreQty).orElse(0));
//                    BigDecimal add = BigDecimalUtil.add(sub, Optional.ofNullable(csModelInfoVO.getReplQty()).orElse(0));
//                    BigDecimal add1 = BigDecimalUtil.add(add, Optional.ofNullable(csModelInfoVO.getOnWayNum()).orElse(0));
//                    BigDecimal div = BigDecimalUtil.div(add1, csModelInfoVO.getAvgQtyCount(), 2);
//                    Double floor = Math.floor(div.doubleValue());
//                    BigDecimal divide = BigDecimal.valueOf(csModelInfoVO.getOnHourseNum() + csModelInfoVO.getOnWayNum()).divide(csModelInfoVO.getAvgQtyCount(), 3, BigDecimal.ROUND_HALF_UP);
//                    csModelInfoVO.setMonths(floor.intValue());
//                }
            }
            // 专备数量
            csModelInfoVO.setSpecialQuantity(0);
            if (PublicUtil.isNotEmpty(csModelQryRequest.getUserNo())) {
                ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
                dto.setModelNos(Arrays.asList(modelNo));
                dto.setCustomerNo(csModelQryRequest.getUserNo());
                ResultVo<List<SpecStockVO>> inventory = inventoryServiceFeignApi.listCustomerSpecStock(dto);
                if (PublicUtil.isNotEmpty(inventory.getData())) {
                    csModelInfoVO.setSpecialQuantity(inventory.getData().stream().mapToInt(SpecStockVO::getAvaQty).sum());
                }
            }
            list.add(csModelInfoVO);
        }
        return ResultVo.success(list);
    }


    @Override
    public ResultVo<CsEAmountDTO> getCanUseEAmount(String customerNo) {
        CsWarehouseRequest request = new CsWarehouseRequest();
        request.setAgentNo(customerNo);
        request.setWarehouseType("WT");
        List<WarehouseDO> list = warehouseService.listWarehouse(request);
        if (CollectionUtil.isEmpty(list)) {
            return ResultVo.failure("未查到改客户的仓库信息");
        }
        String warhouseCode = list.get(0).getWarehouseCode();
        csStockSettingMapper.csReplSumCanUseQty(warhouseCode);
        CsEAmountDTO dto = new CsEAmountDTO();
        BigDecimal transEamount = BigDecimal.ZERO;
        BigDecimal preStockEamount = BigDecimal.ZERO;
        BigDecimal onHandEamount = BigDecimal.ZERO;
        BigDecimal maxEamount = BigDecimal.ZERO;

//        LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(CsStockSettingDO::getCustomerNo, customerNo);
//        List<CsStockSettingDO> doList = csStockSettingMapper.selectList(queryWrapper);
        List<CsStockSettingDO> doList = csStockSettingMapper.selectCsModelByNo(warhouseCode);

        for (CsStockSettingDO csStockSettingDO : doList) {
            // 在库
            onHandEamount = BigDecimalUtil.add(BigDecimalUtil.mul(csStockSettingDO.getEprice(), csStockSettingDO.getQtyOnhand()), onHandEamount);

            //在途
            transEamount = BigDecimalUtil.add(BigDecimalUtil.mul(csStockSettingDO.getEprice(), csStockSettingDO.getTransQty()), transEamount);

            // 预约
            preStockEamount = BigDecimalUtil.add(BigDecimalUtil.mul(csStockSettingDO.getOrdQty(), csStockSettingDO.getEprice()), preStockEamount);
        }
        WarehouseDO warehouseDO = opsWarehouseService.getCsWarehouseDO(customerNo, warhouseCode);
        maxEamount = warehouseDO == null ? BigDecimal.ZERO : warehouseDO.getMaxEamount();

        dto.setMaxEAmt(maxEamount);
        dto.setOnhandEAmt(onHandEamount);
        dto.setTransEAmt(transEamount);
        dto.setOrdIngEAmt(preStockEamount);
        BigDecimal usrEAmt = BigDecimalUtil.add(BigDecimalUtil.add(onHandEamount, preStockEamount), transEamount);
        dto.setCanUseEAmt(BigDecimalUtil.sub(maxEamount, usrEAmt));
        if (dto.getCanUseEAmt().compareTo(BigDecimal.ZERO) <= 0) {
            dto.setCanUseEAmt(BigDecimal.ZERO);
        }

        return ResultVo.success(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> recieveGoodsByDelivery(CsReceiveGoodsDTO dto) {
        log.info("签收货物:" + dto.toString());
        if (PublicUtil.isEmpty(dto.getWarehouseCode())) {
            return ResultVo.failure("仓库代码不能为空");
        }
        LambdaQueryWrapper<CsImpdataDO> query = new LambdaQueryWrapper<>();
        query.eq(CsImpdataDO::getWarehouseCode, dto.getWarehouseCode())
                .eq(CsImpdataDO::getStatus, 1)
                .eq(CsImpdataDO::getDeliveryNo, dto.getDeliveryNo())
                .isNull(CsImpdataDO::getSignTime);
        List<CsImpdataDO> list = csImportDataService.listCSImportDataByWrapper(query);
        if (PublicUtil.isEmpty(list)) {
            return ResultVo.failure(dto.getDeliveryNo() + "发票已签收或没有此发票数据");
        }
        CsImpdataDO impdataDO;
        for (CsImpdataDO csImpdataDO : list) {
            impdataDO = new CsImpdataDO();
            impdataDO.setId(csImpdataDO.getId());
            impdataDO.setSignTime(dto.getReceiveTime());
            impdataDO.setSignPsn(dto.getReceivePsn());
            csImpdataMapper.updateById(impdataDO);
        }
        RoSignConfirmDto param=new RoSignConfirmDto();
        param.setInvoice(dto.getDeliveryNo());
        param.setWarehouse(dto.getWarehouseCode());
        param.setUpdateTime(dto.getReceiveTime());
        param.setUserName(dto.getReceivePsn());
        CommonResult commonResult = opsWmFeignApi.signInvoiceForWms(param);
        if (!commonResult.isSuccess()) {
            throw new RuntimeException("发票签收失败:" + commonResult.getMessage());
        }
        RoConfirm confirmgoods = opsWmFeignApi.Confirmgoods(param);
        return ResultVo.success("签收成功");
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> recieveGoodsByDeliveryList(List<CsReceiveGoodsDTO> dtoList) {
        log.info("签收货物:" + dtoList.toString());
        if (CollectionUtil.isEmpty(dtoList)) {
            return ResultVo.failure("数据错误");
        }
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<CsImpdataDO> updateWrapper;
        List<CsImpdataDO> list;
        for (CsReceiveGoodsDTO dto : dtoList) {
            if (PublicUtil.isEmpty(dto.getWarehouseCode())) {
                return ResultVo.failure("仓库代码不能为空");
            }
            if (PublicUtil.isEmpty(dto.getReceiveTime())) {
                return ResultVo.success("签收时间不能为空");
            }
            queryWrapper.clear();
            queryWrapper.eq(CsImpdataDO::getWarehouseCode, dto.getWarehouseCode())
                    .eq(CsImpdataDO::getStatus, 1)
                    .eq(CsImpdataDO::getDeliveryNo, dto.getDeliveryNo())
                    .isNull(CsImpdataDO::getSignTime);
            list = csImportDataService.listCSImportDataByWrapper(queryWrapper);
            if (CollectionUtil.isEmpty(list)) {
                return ResultVo.failure(dto.getDeliveryNo() + "发票已签收或没有此发票数据");
            }
//            for (CsImpdataDO csImpdataDO : list) {
//                csImpdataDO.setSignTime(dto.getReceiveTime());
//                csImpdataDO.setSignPsn(dto.getReceivePsn());
//                csImportDataService.saveCSImportData(csImpdataDO);
//            }
            RoSignConfirmDto param=new RoSignConfirmDto();
            param.setInvoice(dto.getDeliveryNo());
            param.setWarehouse(dto.getWarehouseCode());
            param.setUpdateTime(dto.getReceiveTime());
            param.setUserName(dto.getReceivePsn());
            CommonResult commonResult = opsWmFeignApi.signInvoiceForWms(param);
            RoConfirm confirmgoods = opsWmFeignApi.Confirmgoods(param);
            if (!commonResult.isSuccess()) {
                throw new RuntimeException("发票签收失败:" + commonResult.getMessage());
            }
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CsImpdataDO::getDeliveryNo, dto.getDeliveryNo());
            updateWrapper.set(CsImpdataDO::getSignTime, dto.getReceiveTime());
            updateWrapper.set(CsImpdataDO::getSignPsn, dto.getReceivePsn());
            csImpdataMapper.update(null, updateWrapper);
        }

        return ResultVo.success("签收成功");
    }

    @Override
    public ResultVo<String> syncCsImpDataToCost() {
        Date endDate = DateUtil.getMonthFirstDate(new Date());
        Date firstDate = DateUtil.getLastMonthFirstDate(endDate);
        Date lastMonthEndDate = DateUtil.getLastMonthEndDate(new Date());
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(CsImpdataDO::getReceiveTime, firstDate)
                .le(CsImpdataDO::getReceiveTime, endDate)
                .eq(CsImpdataDO::getStatus, 2)
                .eq(CsImpdataDO::getCostState, 0);

        List<CsImpdataDO> list = csImportDataService.listCSImportDataByWrapper(queryWrapper);

        SalesImpAgentDO agentDO;
        LambdaUpdateWrapper<CsImpdataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (CsImpdataDO csImpdataDO : list) {
            agentDO = new SalesImpAgentDO();
            String invoiceNo = "VG" + DateUtil.getYearMonthCode(csImpdataDO.getReceiveTime());
            agentDO.setInvoiceNo(invoiceNo);
            agentDO.setOrderNo(csImpdataDO.getOrderNo());
            agentDO.setModelNo(csImpdataDO.getModelNo());
            agentDO.setQuantity(csImpdataDO.getQuantity());
            agentDO.setOptCode(0);
            agentDO.setOptDate(new Date());
            agentDO.setImpdate(lastMonthEndDate);
            agentDO.setDataSource("WT");
            agentDO.setPrice(BigDecimal.ZERO);
            agentDO.setAmount(BigDecimal.ZERO);
            ResultVo<ProductInfoVO> resultVo = productServiceFeignApi.getProductInfoByModelNo(agentDO.getModelNo());
            if (resultVo.isSuccess() && resultVo.getData() != null) {
                agentDO.setECode(Integer.valueOf(Optional.ofNullable(resultVo.getData().getECode()).orElse("0")));
            }
            agentDO.setWarehouseCode(csImpdataDO.getWarehouseCode());
            agentDO.setCustomerNo(csImpdataDO.getAgentNo());

            int insert = salesImpAgentMapper.insert(agentDO);
            if (insert != 1) {
                log.error("保存进salesImpAgent失败:" + agentDO.toString());
                throw new RuntimeException("保存进salesImpAgent失败");
            }

            updateWrapper.clear();
            updateWrapper.eq(CsImpdataDO::getId, csImpdataDO.getId());
            updateWrapper.set(CsImpdataDO::getCostState, 1);
            updateWrapper.set(CsImpdataDO::getBalanceCostDate, lastMonthEndDate);
            csImpdataMapper.update(null, updateWrapper);
        }
        return ResultVo.success("同步数据成功");
    }

    @Override
    public ResultVo<List<CsImpdataVO>> listCsImpdata() {
        List<CsImpdataVO> list = csImportDataService.listCsImpdata();
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> updateImpDataRoId(List<CsImpdataVO> list) {
        List<CsImpdataDO> doList = BeanCopyUtil.copyList(list, CsImpdataDO.class);
        try {
            for (CsImpdataDO impdataDO : doList) {
                csImportDataService.saveCSImportData(impdataDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultVo.failure(e.getMessage());
        }

        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<CsStockSettingDTO> fingInitStockQtyByModelNo(String modelNo, String stockCode) {
        if (StringUtils.isBlank(modelNo) && StringUtils.isBlank(stockCode)) {
            return ResultVo.failure("参数不可为空");
        }

        LambdaQueryWrapper<CsStockSettingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsStockSettingDO::getModelNo, modelNo)
                .eq(CsStockSettingDO::getStockCode, stockCode);
        CsStockSettingDO csStockSettingDO = csStockSettingMapper.selectOne(queryWrapper);
        if (csStockSettingDO == null) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(csStockSettingDO, CsStockSettingDTO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> applyRepl(CsApplyDTO dto) {
        log.info("委托在库申请: {}", dto.toString());
        if (dto == null || dto.getMaster() == null || dto.getMaster().getCApplyNo() == null) {
            return ResultVo.failure("申请不可为空");
        }
        if (dto.getItems().isEmpty()) {
            return ResultVo.failure("明细不可为空");
        }

        if (PublicUtil.isEmpty(dto.getMaster().getApplyType())) {
            return ResultVo.failure("申请类型不可为空,请检查applyType参数");
        }
        if (PublicUtil.isEmpty(dto.getMaster().getStockCode())) {
            return ResultVo.failure("仓库代码不能为空");
        }
//        if (master.getApplyType() == 2 && PublicUtil.isEmpty(master.getPplNo()) && PublicUtil.isEmpty(master.getProjectNo())
//                && PublicUtil.isEmpty(master.getUserNo()) && PublicUtil.isEmpty(master.getGroupCustomerNo())) {
//            return ResultVo.failure("申请项为代理提案时,4个专备字段不能都为空");
//        }

        CsApplyVO applyInfo = dto.getMaster();

        // 更备库设置数量 == true
//        dto.setIsChangeInitQty(true);
        if (dto.getIsChangeInitQty()) {
            int updateCoutn = 0;
            // 根据申请类型初始化备库数量
            if (applyInfo.getApplyType() == 1) {
                updateCoutn = csStockSettingMapper.updateQty("SMC提案");
            } else if (applyInfo.getApplyType() == 2) {
                updateCoutn = csStockSettingMapper.updateQty("代理提案");
            }
            if (updateCoutn == 0) {
                return ResultVo.failure("初始化备库设置数量失败.");
            }

            LambdaQueryWrapper<CsStockSettingDO> queryWrapper;
            CsStockSettingDO oldCSStockSettingDO;
            CsStockSettingDO csStockSettingDO;

            for (CsApplyDetailVO detailVO : dto.getItems()) {
                if (detailVO.getInitTotalQty() == null || detailVO.getInitUnitQty() == null) {
                    return ResultVo.failure("当变更设置数量的时,设置的备库数量或单位数量不可为空.");
                }
                // 判断该型号是否存在 存在修改 反之新增
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CsStockSettingDO::getStockCode, applyInfo.getStockCode())
                        .eq(CsStockSettingDO::getCustomerNo, applyInfo.getCustomerNo())
                        .eq(CsStockSettingDO::getModelNo, detailVO.getModelNo());
                oldCSStockSettingDO = csStockSettingMapper.selectOne(queryWrapper);
                if (oldCSStockSettingDO == null) {
                    csStockSettingDO = new CsStockSettingDO();
                    csStockSettingDO.setStockCode(applyInfo.getStockCode());
                    csStockSettingDO.setCustomerNo(applyInfo.getCustomerNo());
                    csStockSettingDO.setModelNo(detailVO.getModelNo());
                    csStockSettingDO.setInitStockQty(detailVO.getInitTotalQty());
                    csStockSettingDO.setDeptNo(applyInfo.getDeptNo());
                    if (applyInfo.getApplyType() == 1) {
                        csStockSettingDO.setStockType(1);
                        csStockSettingDO.setSponsor("SMC提案");
                    } else if (applyInfo.getApplyType() == 2) {
                        csStockSettingDO.setStockType(2);
                        csStockSettingDO.setSponsor("代理提案");
                    }
                    csStockSettingDO.setInitUnitQty(detailVO.getInitUnitQty());
                    int insert = csStockSettingMapper.insert(csStockSettingDO);
                    if (insert != 1) {
                        return ResultVo.failure("新增备库设置数量失败.");
                    }
                } else {
                    oldCSStockSettingDO.setInitStockQty(detailVO.getInitTotalQty());
                    oldCSStockSettingDO.setInitUnitQty(detailVO.getInitUnitQty());
                    int i = csStockSettingMapper.updateById(oldCSStockSettingDO);
                    if (i != 1) {
                        return ResultVo.failure("修改备库设置数量失败.");
                    }
                }
            }

            // 设置补库申请状态
            applyInfo.setStatus(2); // 待修改备库数量
        } else {
            applyInfo.setStatus(3); // 待生成订单
        }

        // 获取PropertyInfo
        // String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? applyInfo.getCustomerNo() : applyInfo.getUserNo();
        // add by WuJiaWen 2022/11/03 bug8467
        OpsInventoryProperty propertyInfo = this.getCSApplyToPropertyVO(applyInfo.getApplyType(), applyInfo.getCustomerNo(), applyInfo.getUserNo(),
                applyInfo.getPplNo(), applyInfo.getProjectNo(), applyInfo.getGroupCustomerNo());

        PreStockApplyDetailDTO createDto = new PreStockApplyDetailDTO();

        if (applyInfo.getApproveTime() == null) {
            createDto.setApproveTime(new Date());
        } else {
            createDto.setApproveTime(applyInfo.getApproveTime());
        }
        // 19814bug 增加po
        createDto.setCorderNo(applyInfo.getCorderNo());
        createDto.setApplyNo(applyInfo.getCApplyNo());
        createDto.setApplyType("3"); // 3-委托在库申请
        createDto.setReplType(applyInfo.getApplyType() == 1 ? "1" : "2");
        createDto.setStatus(applyInfo.getStatus() == 3 ? "3" : "1");
        createDto.setApplyTime(applyInfo.getApplyTime());
        createDto.setApplyPsnNo(applyInfo.getCreateUser());
        createDto.setDeptNo(applyInfo.getDeptNo());
        createDto.setRemark(applyInfo.getRemark());
        createDto.setWarehouseCode(applyInfo.getStockCode());
        createDto.setStockType(propertyInfo.getInventoryTypeCode());
        createDto.setVip(dto.getVipProjectFlag());
        if (createDto.getStockType().startsWith("GK")) {
            createDto.setCustomerNo(applyInfo.getCustomerNo());
            createDto.setUserNo(applyInfo.getUserNo());
        }
        if (createDto.getStockType().endsWith("PPL")) {
            createDto.setPplNo(propertyInfo.getPpl());
        }
        if (createDto.getStockType().endsWith("PJ")) {
            createDto.setProjectNo(propertyInfo.getProjectCode());
        }
        if (createDto.getStockType().endsWith("JT")) {
            createDto.setGroupCustomerNo(propertyInfo.getGroupCustomerNo());
        }
        List<PreStockDetailDTO> itemList = new ArrayList<>(dto.getItems().size());
        PreStockDetailDTO item;
        String expType = "0"; // 希望备库方式 0-系统自动
        String detailStatus = "3".equals(createDto.getStatus()) ? "2" : "1"; // 申请项处理状态 2-处理中
        String speckMark = "0"; // 默认-正常
        String transType = "0"; // 默认-海运
//        Date dlvDate = DateUtil.addDay(DateUtil.getToday(), 28);

        for (CsApplyDetailVO detail : dto.getItems()) {
            item = new PreStockDetailDTO();
            item.setCproductNo(detail.getCproductNo());
            item.setItemNo(detail.getItemNo());
            item.setApplyModelNo(detail.getModelNo());
            item.setModelNo(detail.getModelNo());
            item.setQuantity(detail.getQuantity());
            item.setExpType(expType);
            item.setStatus(detailStatus);
            item.setDlvDate(detail.getHopeDeliveryDate());
            item.setRohs10(Optional.ofNullable(detail.getRohs10()).orElse(Boolean.FALSE));
            item.setSpecMark(speckMark);
            item.setTransType(transType);
            item.setEprice(detail.getEprice());
            item.setPplNo(detail.getBomNo());
            item.setProjectNo(detail.getProjectNo());
            item.setStockType(createDto.getStockType());
            if (StringUtils.isNotBlank(item.getPplNo())) {
                item.setStockType(InventoryTypeEnum.GK_PPL.getCode());
            }
            if (StringUtils.isNotBlank(item.getProjectNo())) {
                item.setStockType(InventoryTypeEnum.GK_PJ.getCode());
            }
            // add by WuJiaWen 2022/11/11 bug8467
            if (StringUtils.isNotBlank(applyInfo.getCustomerNo()) && applyInfo.getCustomerNo().equals(applyInfo.getUserNo())) {
                item.setStockType(InventoryTypeEnum.TY.getCode());
                item.setPplNo(null);
                item.setProjectNo(null);
            }
            itemList.add(item);
        }
        createDto.setDetails(itemList);

        ResultVo<String> createResult = preStockService.createPreStockApply(createDto);
        if (!createResult.isSuccess()) {
            return ResultVo.failure("申请失败: " + createResult.getMessage());
        }
        return ResultVo.success("申请成功.");

        /*try {
            long id;
            BigDecimal eamont = BigDecimal.ZERO;
            for (CsApplyDetailVO item : dto.getItems()) {
                eamont = eamont.add(Optional.ofNullable(item.getEamount()).orElse(BigDecimal.ZERO));
            }
            QueryWrapper<CsApplyDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cApplyNo", applyInfo.getCApplyNo());
            int count = csApplyMapper.selectCount(queryWrapper);
            CsApplyDO csApplyDO;
            if (count == 0) {
                csApplyDO = BeanCopyUtil.copy(applyInfo, CsApplyDO.class);
                csApplyDO.setTotalQty(dto.getItems().size());
                csApplyDO.setEamount(eamont);
                int insert = csApplyMapper.insert(csApplyDO);
                if (insert != 1) {
                    return ResultVo.failure("写入申请主表失败.");
                }
                id = csApplyDO.getApplyId();
            } else {
                return ResultVo.success("已存在");
            }

            int itemNo = 0;
            for (CsApplyDetailVO detailVO : dto.getItems()) {
                if (detailVO.getItemNo() == null) {
                    itemNo++;
                } else {
                    itemNo = detailVO.getItemNo();
                }
                detailVO.setItemNo(itemNo);
                detailVO.setApplyId(id);
                detailVO.setStatus(1); // 默认1
                if (Optional.ofNullable(detailVO.getRohs10()).orElse(Boolean.FALSE)) {
                    detailVO.setSpecExpType(64);
                }
                int i = csApplyDetailMapper.insert(BeanCopyUtil.copy(detailVO, CsApplyDetailDO.class));
                if (i != 1) {
                    return ResultVo.failure("写入申请明细失败");
                }
            }
            return ResultVo.success("操作成功.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    // 月结确认
    @Override
    public ResultVo<String> confirmMonthBalance(CsMonthBalanceConfirmRequest request) {

        if (request == null) {
            return ResultVo.failure("参数不可为空.");
        }
        if (PublicUtil.isEmpty(request.getWarehouseCode()) && PublicUtil.isEmpty(request.getType())) {
            return ResultVo.failure("仓库代码不能为空");
        }

        if (StringUtils.isBlank(request.getMonthDate())) {
            return ResultVo.failure("月份不可为空");
        }

        LambdaQueryWrapper<CsModelBalanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CsModelBalanceDO::getMonthDate, request.getMonthDate() + "-01")
                .eq(StringUtils.isNotBlank(request.getWarehouseCode()), CsModelBalanceDO::getWarehouseCode, request.getWarehouseCode())
                .eq(StringUtils.isNotBlank(request.getAgentNo()), CsModelBalanceDO::getAgentNo, request.getAgentNo())
                .eq(CsModelBalanceDO::getStatus, "1");
        List<CsModelBalanceDO> csModelBalanceDOS = csBalanceMapper.selectList(queryWrapper);
        if (csModelBalanceDOS.isEmpty()) {
            return ResultVo.failure(request.getWarehouseCode() + " 月份:" + request.getMonthDate() + " 无未月结数据");
        }

        for (CsModelBalanceDO balanceDO : csModelBalanceDOS) {
            balanceDO.setConfirmUser(request.getOperator());
            balanceDO.setConfirmTime(new Date());
            balanceDO.setStatus(2);
            int i = csBalanceMapper.updateById(balanceDO);
            if (i != 1) {
                return ResultVo.failure(balanceDO.getWarehouseCode() + " " + balanceDO.getModelNo() + " " + balanceDO.getMonthDate() + "月结失败.");
            }
        }

        return ResultVo.success("月结成功");
    }

    // 月结
    @Override
    public ResultVo<String> monthBalance(CsMonthBalanceConfirmRequest request) {
        if (request == null) {
            return ResultVo.failure("参数不可为空.");
        }

        if (StringUtils.isBlank(request.getMonthDate())) {
            return ResultVo.failure("月份不可为空");
        }

        if (PublicUtil.isEmpty(request.getWarehouseCode())) {
            return ResultVo.failure("仓库代码不能为空");
        }

        // 判断是否在月结时间范围
//        Date currentDate = new Date();
//        String strCurrentDate = DateUtil.dateToDateTimeString(currentDate);
//        String day = String.valueOf(DateUtil.getDay(currentDate));
//        String[] days = new String[]{"21","22","23","24"};
//        if (!Arrays.asList(days).contains(day)) {
//            return ResultVo.failure("不在月结时间范围内[21-24]号,无法进行月结操作");
//        }
        // 获取上次月结的时间
//        Object lget = redisManager.Lget("ops:monthBalance:balanceTime", -1);
//        if (null == lget) {
//            return ResultVo.failure("获取上次月结时间失败.");
//        }
//        String lastBalanceTime= lget.toString();

        LambdaQueryWrapper<CsModelBalanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(request.getAgentNo()), CsModelBalanceDO::getAgentNo, request.getAgentNo())
//                .ge(CsModelBalanceDO::getMonthDate,lastBalanceTime)
//                .le(CsModelBalanceDO::getMonthDate,strCurrentDate)
                .eq(StringUtils.isNotBlank(request.getMonthDate()), CsModelBalanceDO::getMonthDate, request.getMonthDate() + "-01")
                .eq(StringUtils.isNotBlank(request.getWarehouseCode()), CsModelBalanceDO::getWarehouseCode, request.getWarehouseCode())
                .eq(CsModelBalanceDO::getStatus, "1");
        List<CsModelBalanceDO> csModelBalanceDOS = csBalanceMapper.selectList(queryWrapper);
        if (csModelBalanceDOS.isEmpty()) {
            return ResultVo.failure("客户:" + request.getAgentNo() + " 月份:" + request.getMonthDate() + " 已月结过了.");
        }
        // 变更状态为已月结.
        for (CsModelBalanceDO item : csModelBalanceDOS) {
            item.setStatus(2);
            item.setConfirmTime(new Date());
            item.setUpdateTime(new Date());
            item.setConfirmUser(request.getOperator());
//            item.setOperator(loginAuthDto.getRealName());
            int i = csBalanceMapper.updateById(item);
            if (i != 1) {
                return ResultVo.failure(item.getWarehouseCode() + " " + item.getModelNo() + " " + item.getMonthDate() + "月结失败.");
            }
            // 回改cs_impdata与cs_expdetail 的月结时间
        }
//        // 记录月结时间
//        redisManager.rightPush("ops:monthBalance:balanceTime",strCurrentDate);
//        // 将月结时间存入到redis<String>中,便于月次查询
//        redisManager.set("ops:monthBalance:confirmTime"+request.getMonthDate(),strCurrentDate);
        return ResultVo.success("月结成功");
    }

    @Override
    public ResultVo<String> addImpData(List<CsImportDataDTO> csImpdataDOS) {
        log.info("服务备库入库添加: {}", csImpdataDOS);
        if (null == csImpdataDOS || csImpdataDOS.size() < 1) {
            return ResultVo.failure("导入错误");
        }
        String invoiceNo = csImpdataDOS.get(0).getInvoiceNo();
        String warehouseCode = csImpdataDOS.get(0).getWarehouseCode();
        boolean result = csImportDataService.checkCSInvoice(warehouseCode, invoiceNo);
        if (result) {
            return ResultVo.failure("服务备库库房已存在发票号:" + invoiceNo + "-" + warehouseCode);
        }
        for (CsImportDataDTO csImpdata : csImpdataDOS) {
            csImpdata.setCreateUser("WMS");
            result = csImportDataService.saveCSImportData(BeanCopyUtil.copy(csImpdata, CsImpdataDO.class));
            if (!result) {
                return ResultVo.failure("写入申请明细失败");
            }
        }
        return ResultVo.success("操作成功.");
    }

    @Override
    public ResultVo<String> addExpData(List<CsExpdetailVO> csExpDataDOS) {
        log.info("委托在库出库添加: {}", csExpDataDOS);
        if (csExpDataDOS.isEmpty()) {
            log.error("委托在库出库添加错误:导入数据不可为空");
            return ResultVo.failure("导入数据不可为空");
        }
        for (CsExpdetailVO expdetailVO : csExpDataDOS) {
            if (StringUtils.isBlank(expdetailVO.getAgentNo()) || StringUtils.isBlank(expdetailVO.getWarehouseCode())
                    || StringUtils.isBlank(expdetailVO.getExpOrderNo()) || StringUtils.isBlank(expdetailVO.getModelNo())
                    || expdetailVO.getExpQty() == null) {
                log.error("委托在库出库添加错误:参数判断失败" + expdetailVO.getExpOrderNo());
                return ResultVo.failure("请检查是否漏掉非空参数.");
            }

            LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(CsExpdetailDO::getExpOrderNo, expdetailVO.getExpOrderNo())
                    .eq(CsExpdetailDO::getAgentNo, expdetailVO.getAgentNo())
                    .eq(CsExpdetailDO::getWarehouseCode, expdetailVO.getWarehouseCode())
                    .eq(CsExpdetailDO::getModelNo, expdetailVO.getModelNo())
                    .eq(CsExpdetailDO::getExpQty, expdetailVO.getExpQty());
            int count = csExpdetailMapper.selectCount(queryWrapper);
            if (count == 0) {
                int insert = csExpdetailMapper.insert(BeanCopyUtil.copy(expdetailVO, CsExpdetailDO.class));
                if (insert == 0) {
                    log.error("委托在库出库添加错误:写入失败" + expdetailVO.getExpOrderNo());
                    return ResultVo.failure(expdetailVO.getWarehouseCode() + "-" + expdetailVO.getExpOrderNo() + "-" + expdetailVO.getModelNo() + "-" + expdetailVO.getAgentNo() + " 添加失败.");
                }

            }
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public void exportCsImpData(CsImportDetailRequestDTO requestDTO) {
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getAgentNo()), CsImpdataDO::getAgentNo, requestDTO.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getBarcode()), CsImpdataDO::getBarcode, requestDTO.getBarcode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getCaseNo()), CsImpdataDO::getCaseNo, requestDTO.getCaseNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getDeliveryNo()), CsImpdataDO::getDeliveryNo, requestDTO.getDeliveryNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getImpType()), CsImpdataDO::getImpType, requestDTO.getImpType());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getModelNo()), CsImpdataDO::getModelNo, requestDTO.getModelNo());
        queryWrapper.likeRight(PublicUtil.isNotEmpty(requestDTO.getOrderNo()), CsImpdataDO::getOrderNo, requestDTO.getOrderNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getWarehouseCode()), CsImpdataDO::getWarehouseCode, requestDTO.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStatus()), CsImpdataDO::getStatus, requestDTO.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getInvoiceNo()), CsImpdataDO::getInvoiceNo, requestDTO.getInvoiceNo());
        if (StringUtils.isNotBlank(requestDTO.getMonthDate())) {
            String monthDate = requestDTO.getMonthDate();
            String[] split = monthDate.split("-");
            String firstDayOfMonth1 = DateUtil.getFirstDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            String lastDayOfMonth1 = DateUtil.getLastDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Date endTime = DateUtil.getEndTime(DateUtil.stringToDate(lastDayOfMonth1));
            queryWrapper.ge(CsImpdataDO::getReceiveTime, firstDayOfMonth1);
            queryWrapper.le(CsImpdataDO::getReceiveTime, endTime);
        }
        if (PublicUtil.isEmpty(requestDTO.getDateType()) && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            requestDTO.setDateType(2);
        }

        if (PublicUtil.isNotEmpty(requestDTO.getDateType()) && requestDTO.getDateType() == 1 && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            queryWrapper.between(CsImpdataDO::getShipDate, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
        }
        if (PublicUtil.isNotEmpty(requestDTO.getDateType()) && requestDTO.getDateType() == 2 && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            queryWrapper.between(CsImpdataDO::getReceiveTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
        }
        queryWrapper.orderByDesc(CsImpdataDO::getOrderNo, CsImpdataDO::getReceiveTime, CsImpdataDO::getWarehouseCode);

        List<CsImpdataDO> doList = csImportDataService.listCSImportDataByWrapper(queryWrapper);

        String path = "template/CsImpData.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        int row = 1;
        int cel;
        String status = "";
        for (CsImpdataDO impdataDO : doList) {
            cel = 0;
            excel.setCellValue(row, cel++, impdataDO.getAgentNo());
            excel.setCellValue(row, cel++, impdataDO.getWarehouseCode());
            excel.setCellValue(row, cel++, impdataDO.getOrderNo());
            excel.setCellValue(row, cel++, impdataDO.getModelNo());
            excel.setCellValue(row, cel++, impdataDO.getQuantity());
            if (impdataDO.getApplyType() != null && impdataDO.getApplyType() == 1) {
                excel.setCellValue(row, cel++, "SMC提案");
            } else {
                excel.setCellValue(row, cel++, "客户提案");
            }
            excel.setCellValue(row, cel++, impdataDO.getBarcode());
            excel.setCellValue(row, cel++, impdataDO.getCaseNo());
            excel.setCellValue(row, cel++, impdataDO.getDeliveryNo());
            excel.setCellValue(row, cel++, impdataDO.getUserNo());
            excel.setCellValue(row, cel++, impdataDO.getPplNo());
            excel.setCellValue(row, cel++, impdataDO.getProjectNo());
            excel.setCellValue(row, cel++, impdataDO.getReceiveTime());
            excel.setCellValue(row, cel++, impdataDO.getShipDate());
            if (impdataDO.getStatus() == 1) {
                status = "待收货";
            }
            if (impdataDO.getStatus() == 2) {
                status = "已入库";
            }
            if (impdataDO.getStatus() == 9) {
                status = "已取消";
            }
            excel.setCellValue(row, cel++, status);
            excel.setCellValue(row, cel++, impdataDO.getCreateUser());
            excel.setCellValue(row, cel++, impdataDO.getCreateTime());
            excel.setCellValue(row, cel++, impdataDO.getUpdateTime());
            row++;
        }

        excel.writeToResponse(response, "CsImpData.xlsx");
    }

    @Override
    public void exportCsExpData(CsExportDetailRequestDTO requestDTO) {
        LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getModelNo()), CsExpdetailDO::getModelNo, requestDTO.getModelNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getAgentNo()), CsExpdetailDO::getAgentNo, requestDTO.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getWarehouseCode()), CsExpdetailDO::getWarehouseCode, requestDTO.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getStatus()), CsExpdetailDO::getStatus, requestDTO.getStatus());
        queryWrapper.likeRight(PublicUtil.isNotEmpty(requestDTO.getExpOrderNo()), CsExpdetailDO::getExpOrderNo, requestDTO.getExpOrderNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getUserNo()), CsExpdetailDO::getUserNo, requestDTO.getUserNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(requestDTO.getCorderNo()), CsExpdetailDO::getCorderNo, requestDTO.getCorderNo());

        if (PublicUtil.isEmpty(requestDTO.getDateType()) && PublicUtil.isNotEmpty(requestDTO.getBeginDate())) {
            requestDTO.setDateType(2);
        }

        if (PublicUtil.isNotEmpty(requestDTO.getBeginDate()) && PublicUtil.isNotEmpty(requestDTO.getDateType())) {
            if (requestDTO.getDateType() == 1) {
                queryWrapper.between(CsExpdetailDO::getCreateTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
            }
            if (requestDTO.getDateType() == 2) {
                queryWrapper.between(CsExpdetailDO::getExpTime, DateUtil.getBeginTime(requestDTO.getBeginDate()), DateUtil.getEndTime(requestDTO.getEndDate()));
            }
        }

        if (StringUtils.isNotBlank(requestDTO.getMonthDate())) {
            String monthDate = requestDTO.getMonthDate();
            String[] split = monthDate.split("-");
            String firstDayOfMonth1 = DateUtil.getFirstDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            String lastDayOfMonth1 = DateUtil.getLastDayOfMonth1(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Date endTime = DateUtil.getEndTime(DateUtil.stringToDate(lastDayOfMonth1));
            queryWrapper.ge(CsExpdetailDO::getExpTime, firstDayOfMonth1);
            queryWrapper.le(CsExpdetailDO::getExpTime, endTime);
        }
        queryWrapper.orderByDesc(CsExpdetailDO::getExpOrderNo, CsExpdetailDO::getExpTime, CsExpdetailDO::getWarehouseCode);

        List<CsExpdetailDO> doList = csExpdetailMapper.selectList(queryWrapper);

        String path = "template/CsExpData.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        int row = 1;
        int cel;
        String status = "";
        for (CsExpdetailDO expdetailDO : doList) {
            cel = 0;
            excel.setCellValue(row, cel++, expdetailDO.getAgentNo());
            excel.setCellValue(row, cel++, expdetailDO.getUserNo());
            excel.setCellValue(row, cel++, expdetailDO.getWarehouseCode());
            excel.setCellValue(row, cel++, expdetailDO.getExpOrderNo());
            excel.setCellValue(row, cel++, expdetailDO.getModelNo());
            excel.setCellValue(row, cel++, expdetailDO.getExpQty());
            excel.setCellValue(row, cel++, expdetailDO.getPrice());
            excel.setCellValue(row, cel++, expdetailDO.getPplNo());
            excel.setCellValue(row, cel++, expdetailDO.getProjectNo());
            excel.setCellValue(row, cel++, expdetailDO.getExpTime());
            if (expdetailDO.getStatus() == 1) {
                status = "待出库";
            } else if (expdetailDO.getStatus() == 2) {
                status = "已出库";
            } else if (expdetailDO.getStatus() == 3) {
                status = "出库取消";
            }
            excel.setCellValue(row, cel++, status);
            excel.setCellValue(row, cel++, expdetailDO.getCreateUser());
            excel.setCellValue(row, cel++, expdetailDO.getCreateTime());
            excel.setCellValue(row, cel++, expdetailDO.getUpdateTime());

            row++;
        }

        excel.writeToResponse(response, "CsExpData.xlsx");
    }

    @Override
    public ResultVo<String> updateTransOrderStatus(CsImpdataDO csImpdataDO) {
        String[] split = csImpdataDO.getOrderNo().split("-");
        String transNo = split[0];
        Integer itemNo = Integer.valueOf(split[1]);
        transOrderMapper.updateStatusByOrderNo(transNo, itemNo, 6);
        return ResultVo.success("已成功修改状态");
    }

    @Override
    public void exportWareHouseData(CsWarehouseRequest request) {
        List<WarehouseDO> warehouseDOS = warehouseService.listWarehouse(request);
        if (warehouseDOS.isEmpty()) {
            return;
        }

        String path = "template/exportWareHouseData.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(inputStream);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
        for (WarehouseDO item : warehouseDOS) {
            cel = 0;
            excel.setCellValue(row, cel++, item.getWarehouseCode());
            excel.setCellValue(row, cel++, item.getWarehouseType());
            excel.setCellValue(row, cel++, item.getWarehouseName());
            excel.setCellValue(row, cel++, item.getCustomerNo());
            excel.setCellValue(row, cel++, item.getDeptNo());
            excel.setCellValue(row, cel++, item.getCustomerLinkman());
            excel.setCellValue(row, cel++, item.getCustomerPhone());
            excel.setCellValue(row, cel++, item.getCustomerMail());
            excel.setCellValue(row, cel++, item.getLinkMan());
            excel.setCellValue(row, cel++, item.getMail());
            excel.setCellValue(row, cel++, item.getAddress());
            excel.setCellValue(row, cel++, item.getSmcLinkman());
            excel.setCellValue(row, cel++, item.getSmcPhone());
            excel.setCellValue(row, cel++, item.getSmcMail());
            excel.setCellValue(row, cel++, item.getModifier());
            excel.setCellValue(row, cel++, item.getModifyTime());
            excel.setCellValue(row, cel, item.getDisableFlag() == 0 ? "可用" : "不可用");
            row++;
        }
        excel.writeToResponse(response, "exportWareHouseData.xlsx");
    }

    /**
     * 根据出库订单返扣委托在库入库订单的剩余数
     *
     * @param expOrderNo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calcImpOrderLeftQty(String expOrderNo) {
        if (StringUtils.isBlank(expOrderNo)) {
            return;
        }

        // 根据出库单号获取出库数据
        LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsExpdetailDO::getExpOrderNo, expOrderNo)
                .eq(CsExpdetailDO::getStatus, 2) // 1 待出库 2 已出库 4 取消
                .eq(CsExpdetailDO::getAllocateStatus, 0) // 0 未计算 1 已经计算
                .orderByDesc(CsExpdetailDO::getUpdateTime);
        List<CsExpdetailDO> csExpdetailDOS = csExpdetailMapper.selectList(queryWrapper);
        if (csExpdetailDOS.isEmpty()) {
           /* LambdaUpdateWrapper<CsExpdetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(CsExpdetailDO::getExpOrderNo, expOrderNo)
                    .set(CsExpdetailDO::getAllocateStatus, 1);
            csExpdetailMapper.update(null, lambdaUpdateWrapper);*/
            return;
        }

        LambdaQueryWrapper<CsImpdataDO> query = new LambdaQueryWrapper<>();
        List<CsImpdataDO> csImpdataDOS;
        List<CsImpdataDO> newImpDataList;

        // 遍历出库数据  => 根据库房代码和型号查找已入库订单数据
        for (CsExpdetailDO expdetailDO : csExpdetailDOS) {
            query.clear();
            query
                    .eq(CsImpdataDO::getWarehouseCode, expdetailDO.getWarehouseCode())
                    .eq(CsImpdataDO::getModelNo, expdetailDO.getModelNo())
                    .eq(CsImpdataDO::getStatus, 2) // 1 待收货 2 已入库
                    .ge(CsImpdataDO::getLeftQty, 0)
                    .orderByAsc(CsImpdataDO::getShipDate);
            csImpdataDOS = csImportDataService.listCSImportDataByWrapper(query);
            if (csImpdataDOS.isEmpty()) {
                continue;
//                return ResultVo.failure("出库单号: "+expdetailDO.getExpOrderNo()+" 型号: "+expdetailDO.getModelNo()+" 库房代码: "
//                                    +expdetailDO.getWarehouseCode()+"暂未查到入库数据.");
            }
            // 出库数量
            Integer expQty = expdetailDO.getExpQty();

            newImpDataList = csImpdataDOS.stream().peek(i -> {
                if (null == i.getQuantity()) {
                    i.setQuantity(0);
                }
                if (null == i.getExpQty()) {
                    i.setExpQty(0);
                }
            }).collect(Collectors.toList());

            // 总的剩余数量，小于客户订单的出库数，则不够出，退出
            double leftQty = newImpDataList.stream().mapToDouble(CsImpdataDO::getLeftQty).sum();
            if (leftQty < expQty) {
                continue;
            }

            try {
                for (CsImpdataDO impdataDO : newImpDataList) {

                    // 计算当前数据可出库数量
                    int canUpdateNum = impdataDO.getLeftQty();
                    // 如果可出库数量>=需出库数量 直接修改出库数量
                    if (canUpdateNum > expQty) {
                        if (impdataDO.getExpQty() == null) {
                            impdataDO.setExpQty(0);
                        }
                        impdataDO.setExpQty(impdataDO.getExpQty() + expQty);
                        impdataDO.setLeftQty(impdataDO.getLeftQty() - expQty);
                        impdataDO.setUpdateTime(new Date());
                        csImportDataService.saveCSImportData(impdataDO);
                        break;
                    }
                    // 如果可出库数量<=需出库数量 按照当前可最大出库修改出库数量
                    impdataDO.setExpQty(impdataDO.getExpQty() + canUpdateNum);
                    impdataDO.setLeftQty(impdataDO.getLeftQty() - expQty);
                    impdataDO.setUpdateTime(new Date());
                    csImportDataService.saveCSImportData(impdataDO);
                    expQty = expQty - canUpdateNum;
                    this.insertCsImpdataOut(impdataDO);
                }
                // 修改出库状态 (0 未计算出库 1 已经计算)
                expdetailDO.setAllocateStatus(1);
                expdetailDO.setUpdateTime(new Date());
                csExpdetailMapper.updateById(expdetailDO);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("修改出库数量失败 错误数据: " + JSONObject.toJSONString(expdetailDO));
                throw new BusinessException("修改出库数量失败 错误数据: " + JSONObject.toJSONString(expdetailDO));
            }
        }
    }

    private ResultVo<String> insertCsImpdataOut(CsImpdataDO impdataDO) {

        CsImpDataOutDO outDO = new CsImpDataOutDO();
        outDO.setAgentNo(impdataDO.getAgentNo());
        outDO.setCreateTime(new Date());
        outDO.setWarehouseCode(impdataDO.getWarehouseCode());
        outDO.setOrderNo(impdataDO.getOrderNo());
        outDO.setExpOrderNo(impdataDO.getDeliveryNo());
        outDO.setModelNo(impdataDO.getModelNo());
        csImpDataOutMapper.insert(outDO);

        return ResultVo.success();
    }

    @Override
//    @Transactional
    public void calcImpOrderLeftQty() {
        // 获取所有未计算的出库单号
        LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(CsExpdetailDO::getExpOrderNo)
                .eq(CsExpdetailDO::getAllocateStatus, 0)
                .groupBy(CsExpdetailDO::getExpOrderNo);
        // List<CsExpdetailDO> csExpdetailDOS = csExpdetailMapper.selectList(queryWrapper);
        PageInfo<CsExpdetailDO> pageInfo;
        int pageNum = 1;
        List<CsExpdetailDO> csExpdetailDOS;
        while (true) {
            pageInfo = PageHelper.startPage(pageNum, 1)
                    .doSelectPageInfo(() -> csExpdetailMapper.selectList(queryWrapper));

            csExpdetailDOS = pageInfo.getList();
            if (csExpdetailDOS.isEmpty()) {
                continue;
            }
            csStockApplyService.calcImpOrderLeftQty(csExpdetailDOS.get(0).getExpOrderNo());
            if (pageInfo.isIsLastPage()) {
                break;
            }
            pageNum++;
        }
    }

    @Override
    public ResultVo<String> updateCsTmpReturnCalcDataById(CsReturnCalcVo calcVo) {
        log.info(calcVo.toString());
        if (PublicUtil.isEmpty(calcVo.getId())) {
            return ResultVo.failure("未传入ID值");
        }
        CsTmpReturnCalcDO calcDO = new CsTmpReturnCalcDO();
        calcDO.setId(calcVo.getId());
        calcDO.setReturnQty(calcVo.getReturnQty());
        int update = csTmpReturnCalcMapper.updateById(calcDO);
        return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
    }

    @Override
    public ResultVo<String> deleteCsReturnApplyById(Integer id) {
        CsReturnApplyDO applyDO = new CsReturnApplyDO();
        applyDO.setApplyId(id);
        applyDO.setStatus(9);
        int update = csReturnApplyMapper.updateById(applyDO);
        return update == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    // Edit by DengDengHui, 2022-10-20 for bug-8370
    private OpsInventoryProperty getCSApplyToPropertyVO(Integer applyType, String customerNo, String userNo, String pplNo, String projectCode, String groupCustomerNo) {
        OpsInventoryProperty toPropertyVO = new OpsInventoryProperty();
        // add by WuJiaWen 2022/11/03 bug8467
        if (StringUtils.isNotBlank(customerNo) && customerNo.equals(userNo)) {
            toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
            toPropertyVO.setInventoryPropertyId(1L);
            return toPropertyVO;
        }

        if (Optional.ofNullable(applyType).orElse(0) == 1) {
            toPropertyVO.setInventoryPropertyId(1L);
            toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
        } else {
            toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
            if (StringUtils.isNotBlank(customerNo)) {
                toPropertyVO.setCustomerNo(customerNo);
                toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.GK_TY.getCode());
                if (StringUtils.isNotBlank(pplNo)) {
                    toPropertyVO.setPpl(pplNo);
                    toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.GK_PPL.getCode());
                }
                if (StringUtils.isNotBlank(projectCode)) {
                    toPropertyVO.setProjectCode(projectCode);
                    toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.GK_PJ.getCode());
                }
            } else {
                if (StringUtils.isNotBlank(projectCode)) {
                    toPropertyVO.setProjectCode(projectCode);
                    toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.ZL_PJ.getCode());
                }
                if (StringUtils.isNotBlank(groupCustomerNo)) {
                    toPropertyVO.setGroupCustomerNo(groupCustomerNo);
                    toPropertyVO.setInventoryTypeCode(InventoryTypeEnum.ZL_JT.getCode());
                }
            }
            CommonResult checkResult = opsPropertyFeignApi.findProperty(toPropertyVO);
            if (!checkResult.isSuccess() || checkResult.getData() == null) {
                log.error("委托在库-获取在库的inventoryPropertyId失败: params = {}", toPropertyVO);
                throw new BusinessException("委托在库-获取在库的inventoryPropertyId失败: " + checkResult.getMessage());
            }
            toPropertyVO.setInventoryPropertyId(Long.parseLong(checkResult.getData().toString()));
        }
        return toPropertyVO;
    } // end

    @Override
    public ResultVo<String> calCsImpExpQty() {
        // 查询委托在库仓库列表
        List<String> warehouseCodeList = warehouseService.listAllWTWarehouseCode();

        ResultVo<List<OpsInventoryVO>> wareHouseCodeInfoList;
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        List<CsImpdataDO> csImpdataDOS;
        int stockQty;

        for (String warehouseCode : warehouseCodeList) {
            // 根据仓库代码查询所有型号在库数
            wareHouseCodeInfoList = inventoryServiceFeignApi.findInventoryByWareHouseCode(warehouseCode);
            if (!wareHouseCodeInfoList.isSuccess()) {
                return ResultVo.failure(wareHouseCodeInfoList.getMessage());
            }
            if (CollectionUtils.isEmpty(wareHouseCodeInfoList.getData())) {
                log.info("仓库{}暂无在库数据", warehouseCode);
                continue;
            }

            for (OpsInventoryVO item : wareHouseCodeInfoList.getData()) {
                // 根据库房代码和型号获取入库数据（倒序）
                queryWrapper.clear();
                queryWrapper.select(CsImpdataDO::getId, CsImpdataDO::getWarehouseCode, CsImpdataDO::getModelNo,
                        CsImpdataDO::getShipDate, CsImpdataDO::getQuantity)
                        .eq(CsImpdataDO::getModelNo, item.getModelNo())
                        .eq(CsImpdataDO::getWarehouseCode, item.getWarehouseCode())
                        .eq(CsImpdataDO::getImpType, 1)
                        .eq(CsImpdataDO::getStatus, 2)
                        .orderByDesc(CsImpdataDO::getShipDate);
                csImpdataDOS = csImportDataService.listCSImportDataByWrapper(queryWrapper);
                if (CollectionUtils.isEmpty(csImpdataDOS)) {
                    log.info("{}, {} 无入库数据  -----", item.getWarehouseCode(), item.getModelNo());
                    continue;
                }
                // 在库数
                stockQty = Optional.ofNullable(item.getQuantity()).orElse(0);

                csImportDataService.calcExpQtyAndLeftQty(csImpdataDOS, stockQty);
                log.info("{}, {} 的CsImpData更新完毕", warehouseCode, item.getModelNo());
            }
            log.info("{} 的CsImpData出库数量和剩余数量数据更新完毕", warehouseCode);
        }
        return ResultVo.success("更新完毕.");
    }

    @Override
    public ResultVo<String> calcBalance(Date monthDate) {

        LambdaQueryWrapper<CsBalanceCalcMasterDO> quer = new LambdaQueryWrapper<>();
        quer.eq(CsBalanceCalcMasterDO::getMonthDate, monthDate);
//        quer.eq(CsBalanceCalcMasterDO::getStatus, 1);
        CsBalanceCalcMasterDO calcMasterDO = csBalanceCalcMasterMapper.selectOne(quer);

        Date openDate = DateUtil.stringToDate("2022-08-01");
        if (DateUtil.getDiffDay(monthDate, openDate) >= 1) {
            return ResultVo.failure("只能月结2022年8月之后的数据!");
        }

        Date monthFirstDate = DateUtil.getBeginTime(DateUtil.getMonthFirstDate(monthDate));
        Date date = DateUtil.clearTimeBitAfterMinute(DateUtil.getEndTime(DateUtil.getMonthEndDate(monthDate)));
        if (DateUtil.getDiffMinute(date, new Date()) < 0) {
            return ResultVo.failure("未到月结时间");
        }
        if (calcMasterDO != null && calcMasterDO.getStatus() == 2) {
            return ResultVo.failure("该月数据已确认，不可再月结计算");
        }
        if (calcMasterDO == null) {
            calcMasterDO = new CsBalanceCalcMasterDO();
            calcMasterDO.setMonthDate(monthFirstDate);
            calcMasterDO.setFromTime(monthFirstDate);
            calcMasterDO.setToTime(date);
            calcMasterDO.setStatus(1);
            csBalanceCalcMasterMapper.insert(calcMasterDO);
        }
        Date startDate = calcMasterDO.getFromTime();
        Date endDate = calcMasterDO.getToTime();

        CsTmpBalanceDO balanceDO;
        List<CsTmpBalanceDO> balanceList = new ArrayList<>();

        // 清空临时表数据
        csTmpBalanceMapper.deleteTmp();
        // 删除这个月的月结数据
        QueryWrapper<CsModelBalanceDO> query = new QueryWrapper<>();
        query.eq("status", 1);
        query.ge("month_date", monthFirstDate);
        query.le("month_date", date);
        csBalanceMapper.delete(query);

        // 开票数量
        ResultVo<List<InvoiceBalaceDTO>> balaceData = invoiceServiceFeignApi.getSalesInvoiceBalaceData(monthFirstDate, endDate);
        List<InvoiceBalaceDTO> list = balaceData.getData();
        if (PublicUtil.isNotEmpty(list)) {
            for (InvoiceBalaceDTO balaceDTO : list) {
                balanceDO = new CsTmpBalanceDO();
                balanceDO.setModelNo(balaceDTO.getModelNo());
                balanceDO.setQtyInvoice(balaceDTO.getQty());
                balanceDO.setWarehouseCode(balaceDTO.getStockNo());
                balanceList.add(balanceDO);
            }
        }
        log.info("开票数据" + balanceList.size() + "条");

        // 在库数
        List<CsTmpBalanceDO> onHandQty = csTmpBalanceMapper.listOnHandQty();
        balanceList.addAll(onHandQty);
        log.info("在库数据" + onHandQty.size() + "条");

        //当月入库数量
        UpdateWrapper<CsImpdataDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("balance_date", monthFirstDate);
        updateWrapper.ge("receive_time", startDate);
        updateWrapper.le("receive_time", endDate);
        updateWrapper.eq("status", 2);
        updateWrapper.eq("imp_type", 1);
        csImportDataService.updateCSImportDataByWrapper(null, updateWrapper);
        List<CsTmpBalanceDO> impQty = csTmpBalanceMapper.listImpQty(startDate, endDate);
        balanceList.addAll(impQty);
        log.info("入库数据" + impQty.size() + "条");

        //当月出库的数据
        UpdateWrapper<CsExpdetailDO> expWrapper = new UpdateWrapper<>();
        expWrapper.set("balance_date", monthFirstDate);
        expWrapper.ge("exp_time", startDate);
        expWrapper.le("exp_time", endDate);
        expWrapper.eq("status", 2);
        csExpdetailMapper.update(null, expWrapper);
        List<CsTmpBalanceDO> expQty = csTmpBalanceMapper.listExpQty(startDate, endDate);
        balanceList.addAll(expQty);
        log.info("出库数据" + expQty.size() + "条");

        //退货数量
        updateWrapper.clear();
        updateWrapper.set("balance_date", monthFirstDate);
        updateWrapper.lt("quantity", 0);
        updateWrapper.eq("status", 2);
        updateWrapper.eq("imp_type", 2);
        updateWrapper.ge("imp_date", startDate);
        updateWrapper.le("imp_date", endDate);
        csImportDataService.updateCSImportDataByWrapper(null, updateWrapper);
        List<CsTmpBalanceDO> returnQty = csTmpBalanceMapper.listReturnQty(startDate, endDate);
        balanceList.addAll(returnQty);
        log.info("退货数据" + returnQty.size() + "条");

        // 发货数量
//        updateWrapper.clear();
//        updateWrapper.set("balance_costDate", monthFirstDate);
//        updateWrapper.eq("status", 2);
//        updateWrapper.eq("imp_type", 1);
//        updateWrapper.ge("ship_date", startDate);
//        updateWrapper.le("ship_date", endDate);
//        csImportDataService.updateCSImportDataByWrapper(null, updateWrapper);
        List<CsTmpBalanceDO> shipQty = csTmpBalanceMapper.listShipQty(startDate, endDate);
        balanceList.addAll(shipQty);
        log.info("发货数据" + shipQty.size() + "条");

        //更新上月结存数
        Date LastMothDate = DateUtil.addMonth(monthFirstDate, -1);
        List<CsTmpBalanceDO> balanceData = csTmpBalanceMapper.selectLastMothBalanceData(LastMothDate);
        balanceList.addAll(balanceData);

        //保存到tmp_cs_balance
        for (CsTmpBalanceDO csTmpBalanceDO : balanceList) {
            if (PublicUtil.isEmpty(csTmpBalanceDO.getAgentNo())) {
                csTmpBalanceDO.setAgentNo(csTmpBalanceDO.getWarehouseCode().substring(1, 6));
            }
            csTmpBalanceMapper.insert(csTmpBalanceDO);
        }

        // 保存到cs_balance
        List<CsModelBalanceDO> doList = csTmpBalanceMapper.listCsBalanceData();
        for (CsModelBalanceDO modelBalanceDO : doList) {
            modelBalanceDO.setMonthDate(DateUtil.getMonthFirstDate(endDate));
            modelBalanceDO.setStatus(1);
            csBalanceMapper.insert(modelBalanceDO);
        }
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<List<CsBalanceCalcMasterDO>> getCalcbanceDate() {
//        Date lastBanceDate = csBalanceCalcMasterMapper.getLastBanceDate();
//        if (lastBanceDate != null) {
//            String dateTimeString = DateUtil.dateToDateTimeString(lastBanceDate);
//            return ResultVo.success(dateTimeString);
//        }
//        return ResultVo.success("");

        LambdaQueryWrapper<CsBalanceCalcMasterDO> query = new LambdaQueryWrapper<>();
        query.orderByAsc(CsBalanceCalcMasterDO::getMonthDate);
        List<CsBalanceCalcMasterDO> doList = csBalanceCalcMasterMapper.selectList(query);
        return ResultVo.success(doList);
    }

    @Override
    public ResultVo<String> updateCsBalanceDateById(Integer id) {
        CsBalanceCalcMasterDO masterDO = new CsBalanceCalcMasterDO();
        masterDO.setStatus(2);
        masterDO.setId(id);
        int i = csBalanceCalcMasterMapper.updateById(masterDO);
        return i == 1 ? ResultVo.success("成功") : ResultVo.failure("失败");
    }

    @Override
    public ResultVo<String> updateCsBalaceMothDate(CsBalanceCalcMasterDO masterDO) {
        LambdaQueryWrapper<CsBalanceCalcMasterDO> query = new LambdaQueryWrapper<>();
        query.gt(CsBalanceCalcMasterDO::getToTime, masterDO.getFromTime());
        query.eq(CsBalanceCalcMasterDO::getStatus, 2);
        Integer count = csBalanceCalcMasterMapper.selectCount(query);
        if (count >= 1) {
            return ResultVo.failure("设置的月结开始时间不能小于上次月结的结束时间");
        }

        Date date = DateUtil.clearTimeBitAfterMinute(DateUtil.getEndTime(masterDO.getToTime()));

        masterDO.setToTime(date);
        int i = csBalanceCalcMasterMapper.updateById(masterDO);
        return i == 1 ? ResultVo.success("设置成功") : ResultVo.failure("设置失败");
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> cancelCsExpDetailByOrderNo(List<String> orderNos) {
        log.info("委托在库出库取消订单号:" + orderNos.toString());
        for (String orderNo : orderNos) {
            LambdaQueryWrapper<CsExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CsExpdetailDO::getExpOrderNo, orderNo + "-0");

            CsExpdetailDO expDO = csExpdetailMapper.selectOne(queryWrapper);
            if (expDO == null) {
                return ResultVo.success("201", "未查询到订单号:" + orderNo + "的委托在库出库数据");
            }
            if (expDO.getStatus() != 1) {
                return ResultVo.failure("202", "出库单:" + orderNo + "的状态不是待出库");
            }

            CsExpdetailDO csExpdetailDO = new CsExpdetailDO();
            csExpdetailDO.setId(expDO.getId());
            csExpdetailDO.setStatus(4);
            csExpdetailDO.setUpdateTime(new Date());
            csExpdetailMapper.updateById(csExpdetailDO);
        }
        return ResultVo.success("取消委托在库出库成功");
    }

    @Override
    public void exportCsBalanceData(CsBalcenQryRequest request) {
        LambdaQueryWrapper<CsModelBalanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getAgentNo()), CsModelBalanceDO::getAgentNo, request.getAgentNo())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), CsModelBalanceDO::getWarehouseCode, request.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(request.getMonthDate()), CsModelBalanceDO::getMonthDate, request.getMonthDate())
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), CsModelBalanceDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getAgentNo()), CsModelBalanceDO::getAgentNo, request.getAgentNo());

        List<CsModelBalanceDO> doList = csBalanceMapper.selectList(queryWrapper);

        String path = "template/CsBalance.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;

        for (CsModelBalanceDO balanceDO : doList) {
            cel = 0;
            excel.setCellValue(row, cel++, balanceDO.getAgentNo());
            excel.setCellValue(row, cel++, balanceDO.getWarehouseCode());
            excel.setCellValue(row, cel++, balanceDO.getMonthDate());
            excel.setCellValue(row, cel++, balanceDO.getModelNo());
            excel.setCellValue(row, cel++, balanceDO.getOpeningQty());
            excel.setCellValue(row, cel++, balanceDO.getInQty());
            excel.setCellValue(row, cel++, balanceDO.getOutQty());
            excel.setCellValue(row, cel++, balanceDO.getReturnQty());
            excel.setCellValue(row, cel++, balanceDO.getBalanceQty());
            excel.setCellValue(row, cel++, balanceDO.getOnhandQty());
            excel.setCellValue(row, cel++, balanceDO.getQtyOpeningCost());
            excel.setCellValue(row, cel++, balanceDO.getQtyShip());
            excel.setCellValue(row, cel++, balanceDO.getQtyInvoice());
            excel.setCellValue(row, cel++, balanceDO.getQtyBalanceCost());
            excel.setCellValue(row, cel++, balanceDO.getRemark());
            excel.setCellValue(row, cel++, balanceDO.getCreateTime());
            excel.setCellValue(row, cel, balanceDO.getUpdateTime());
            row++;
        }
        excel.writeToResponse(response, "CsBalance.xlsx");
    }

    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResultVo<String> updateSalesInvoiceMidInfo(ReturnOrderDO orderDO) {
        LambdaQueryWrapper<SalesInvoiceMidInfoDO> query = new LambdaQueryWrapper<>();
        query.eq(SalesInvoiceMidInfoDO::getStateCode, 0);
        query.eq(SalesInvoiceMidInfoDO::getIsNew, 1);
        query.ne(SalesInvoiceMidInfoDO::getType, "DF"); // DF 删单二次审批 只删客单不删采购 type 是 DF
        query.eq(SalesInvoiceMidInfoDO::getApplyNo, orderDO.getApplyNo());
        query.eq(SalesInvoiceMidInfoDO::getApplynoItem, orderDO.getItemNo());
        SalesInvoiceMidInfoDO midInfoDO = salesInvoiceMidInfoMapper.selectOne(query);
        if (midInfoDO == null && orderDO.getOrderType() != 9) {
            return ResultVo.failure("开票中间表不存在:" + orderDO.getOrderNo());
        }
        if (midInfoDO != null) {
            midInfoDO.setStateCode(1);
            salesInvoiceMidInfoMapper.updateById(midInfoDO);
        }
        return ResultVo.success("更新salesInvoiceMidInfo成功");
    }

    @Override
    public ResultVo<String> updateCsReturnApplyStatus(Integer id) {
        CsReturnApplyDO applyDO = csReturnApplyMapper.selectById(id);
        if (applyDO == null) {
            return ResultVo.failure();
        }

        applyDO.setStatus(5);
        csReturnApplyMapper.updateById(applyDO);
        return ResultVo.success();
    }

}
