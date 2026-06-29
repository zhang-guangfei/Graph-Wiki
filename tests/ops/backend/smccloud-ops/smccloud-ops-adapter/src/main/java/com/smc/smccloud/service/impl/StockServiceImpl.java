package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.errorEnum.SalesInfoErrorCodeEnum;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.QAStatusEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.WarehouseSalesBranchConfigMapper;
import com.smc.smccloud.model.adapter.AdapterResult;
import com.smc.smccloud.model.adapter.stock.*;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.prestock.PreStockApplyDetailDTO;
import com.smc.smccloud.model.prestock.PreStockDetailDTO;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.model.stockassembly.TransferResult;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: B90034
 * Date: 2022-02-16 11:27
 * Description: 库存服务
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private WarehouseSalesBranchConfigMapper warehouseConfigMapper;
    @Resource
    private PreStockFeignApi preStockFeignApi;
    @Resource
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private SendMessage sendMessage;

    @Override
    public AdapterResult handleSubTreasuryCreateMQ(ChinaRegionWarehouseSupplyApply apply) {
        String applyType = "1"; // 申请类型 1-专备
        String replType = "1"; // SMC提案
        String status = "3"; // 申请状态 3-待处理
        String inventoryType; // 库存类型
        if (StringUtils.isBlank(apply.getCustomerNo())) {
            inventoryType = InventoryTypeEnum.TY.getCode();
        } else {
            if ("S0000".equals(apply.getCustomerNo())) { // 分库共有库存
                inventoryType = InventoryTypeEnum.TY.getCode();
            } else {
                inventoryType = InventoryTypeEnum.GK_TY.getCode();
            }
        }
        if (StringUtils.isNotBlank(apply.getCustomerBaseNo())) {
            inventoryType = InventoryTypeEnum.ZL_JT.getCode();
        }

        // 先行在库申请信息
        PreStockApplyDetailDTO createDto = new PreStockApplyDetailDTO();
        createDto.setApplyNo(apply.getId());
        createDto.setApplyType(applyType);
        createDto.setStatus(status);
        createDto.setApplyPsnNo(apply.getMakeEmployeeNo());
        createDto.setApplyPsn(apply.getMakeEmployeeName());
        createDto.setSalesPsn(apply.getSaleEmployeeNo());
        createDto.setApplyTime(apply.getApplyDate());
        createDto.setDeptNo(apply.getDeptNo());
        if (!"S0000".equals(apply.getCustomerNo())) {
            createDto.setCustomerNo(apply.getCustomerNo());
        }
        createDto.setStockType(inventoryType);
        createDto.setWarehouseCode(apply.getWarehouseId()); // 申请补库仓库
        createDto.setGroupCustomerNo(apply.getCustomerBaseNo());
        createDto.setReplType(replType);
        createDto.setReason(apply.getRemark());
        createDto.setVip(apply.getVipProjectFlag());
        //createDto.setRemark(apply.getRemark());

        // bug 14101
        if(apply.getApproveTime() == null) {
            createDto.setApproveTime(new Date());
        } else {
            createDto.setApproveTime(apply.getApproveTime());
        }

        // 先行在库申请项信息
        List<PreStockDetailDTO> details = new ArrayList<>(apply.getItems().size());
        PreStockDetailDTO detail;
        String expType = "0"; // 希望备库方式 0-系统自动
        String detailStatus = "2"; // 申请项处理状态 2-处理中

        for (ChinaRegionWarehouseSupplyApplyItem item : apply.getItems()) {
            detail = new PreStockDetailDTO();
            detail.setItemNo(item.getItemId());
            detail.setApplyModelNo(item.getModelNo());
            detail.setModelNo(item.getModelNo());
            detail.setQuantity(item.getAuditQuantity());
            detail.setExpType(expType);
            detail.setStatus(detailStatus);
            detail.setOrderNo(item.getOrderNo());
            detail.setDlvDate(item.getHopeDeliveryDate());
            detail.setRohs10(Optional.ofNullable(item.getRohs10()).orElse(Boolean.FALSE));
            detail.setSpecMark(this.getDetailSpecMark(item.getAssemble()));
            detail.setTransType(this.getDetailTransType(item.getTransType()));
            detail.setEprice(item.getEPrice());
            detail.setPplNo(item.getBomNo());
            detail.setProjectNo(item.getProjectCode());
            detail.setGroupCustomerNo(createDto.getGroupCustomerNo());
            detail.setStockType(createDto.getStockType());
            if (StringUtils.isNotBlank(detail.getPplNo())) {
                detail.setStockType(InventoryTypeEnum.GK_PPL.getCode());
            }
            if (StringUtils.isNotBlank(detail.getProjectNo())) {
                detail.setStockType(InventoryTypeEnum.GK_PJ.getCode());
            }
            if (StringUtils.isNotBlank(detail.getGroupCustomerNo())) {
                detail.setStockType(InventoryTypeEnum.ZL_JT.getCode());
            }
            details.add(detail);
        }
        createDto.setDetails(details);

        // 保存申请
        ResultVo<String> handleResult = preStockFeignApi.handleSMSPreStockApply(createDto);
        log.info("门户分库备库申请处理结果 {} => {}", apply.getId(), handleResult);
        if (!handleResult.isSuccess()) {
            throw new BusinessException(handleResult.getMessage());
        }

        return getAdapterResult(apply, handleResult);
    }

    @Override
    public AdapterResult handleCustomersInStockCreateMQ(BinDataApply apply) {
        AdapterResult result = new AdapterResult();
        result.setNo(apply.getId());
        result.setDataType("0");
        Map<Integer, String> temp = new HashMap<>(apply.getItems().size());
        apply.getItems().forEach(item -> temp.put(item.getItemId(), item.getModelNo()));
        result.setItemModelList(temp);

        String applyType = "1"; // 专备
        String replType = "1"; // SMC提案
        String status = "3"; // 申请状态 3-待处理
        String inventoryType = InventoryTypeEnum.GK_TY.getCode();
        if (StringUtils.isNotBlank(apply.getCustomerBaseNo())) {
            inventoryType = InventoryTypeEnum.ZL_JT.getCode();
        }
        if (apply.getAutoSupply()) {
            replType = "3"; // 自动周转申请
        }

        String warehouseCode = null;
        if (StringUtils.isNotBlank(apply.getCargoAddress()) && apply.getCargoAddress().contains("】")) {
            // 从【货物所在地】获取备库仓库
            warehouseCode = apply.getCargoAddress().split("】")[0];
            warehouseCode = warehouseCode.replace("【", "");
        }
        if (StringUtils.isBlank(warehouseCode)) {
            ResultVo<CustomerVO> customerInfoResult = commonServiceFeignApi.findCustomerByCustomerNo(apply.getCustomerNo());
            if (!customerInfoResult.isSuccess() || customerInfoResult.getData() == null) {
                log.error("获取备库客户信息失败: {}", customerInfoResult);
                result.setResult(false);
                result.setMessage("获取备库客户信息失败");
                return result;
            }
            warehouseCode = this.getDeptPriorityCentralWarehouse(customerInfoResult.getData().getHRUnitId());
        }

        // 先行在库申请信息
        PreStockApplyDetailDTO createDto = new PreStockApplyDetailDTO();
        createDto.setTransFlag(!apply.getTransFlag());
        createDto.setApplyNo(apply.getId());
        createDto.setApplyType(applyType);
        createDto.setStatus(status);
        createDto.setSalesPsn(apply.getSaleEmployeeNo());
        createDto.setApplyPsnNo(apply.getMakeEmployeeNo());
        createDto.setApplyPsn(apply.getMakeEmployeeName());
        createDto.setApplyTime(apply.getApplyDate());
        createDto.setDeptNo(apply.getDeptNo());
        createDto.setWarehouseCode(warehouseCode);
        createDto.setStockType(inventoryType);
        createDto.setCustomerNo(apply.getCustomerNo());
        createDto.setGroupCustomerNo(apply.getCustomerBaseNo());
        createDto.setReplType(replType);
        // add by A78027 bug 12284 20231017
        createDto.setVip(apply.getVipProjectFlag());
        createDto.setReason(StringUtils.isBlank(apply.getRemark()) ? "" : apply.getRemark().trim());
        // bug 14101
        if(apply.getApproveTime() == null) {
            createDto.setApproveTime(new Date());
        } else {
            createDto.setApproveTime(apply.getApproveTime());
        }


        // 先行在库申请项信息
        List<PreStockDetailDTO> details = new ArrayList<>(apply.getItems().size());
        PreStockDetailDTO detail;
        String expType = "0"; // 希望备库方式 0-系统自动
        String detailStatus = "2"; // 申请项处理状态 2-处理中

        for (BinDataApplyItem item : apply.getItems()) {
            if (item.getHopeDeliveryDate() == null) {
                result.setResult(false);
                result.setMessage("期望交货期不能为空");
                return result;
            }
            boolean specialModel = opsCommonService.isSpecialModel(item.getModelNo());
            if (specialModel) {
                if (!"KLS".equalsIgnoreCase(warehouseCode)) {
                    result.setResult(false);
                    result.setMessage("型号是特殊型号，仓库只能选择KLS");
                    return result;
                }
            }

            detail = new PreStockDetailDTO();
            detail.setItemNo(item.getItemId());
            detail.setApplyModelNo(item.getModelNo());
            detail.setModelNo(item.getModelNo());
            detail.setQuantity(item.getAuditQuantity());
            detail.setDlvDate(item.getHopeDeliveryDate());
            detail.setExpType(expType);
            detail.setStatus(detailStatus);
            if ("3".equals(createDto.getReplType())) {
                detail.setQtybin(item.getAuditBinUnitQuantity());
            }
            detail.setRohs10(Optional.ofNullable(item.getRohs10()).orElse(Boolean.FALSE));
            detail.setSpecMark(this.getDetailSpecMark(item.getAssemble()));
            detail.setTransType(this.getDetailTransType(item.getTransType()));
            detail.setEprice(item.getEPrice());

            /** <!--add by WuWeiDong 20240311  bug 13655   -->
             *   明细没有库存类开，集团号等，就用主表的。
             */
            detail.setPplNo(Optional.ofNullable(item.getBomNo()).orElse(createDto.getPplNo()));
            detail.setProjectNo(Optional.ofNullable(item.getProjectCode()).orElse(createDto.getProjectNo()));
            detail.setGroupCustomerNo(createDto.getGroupCustomerNo());
            detail.setStockType(createDto.getStockType());

            if (StringUtils.isNotBlank(detail.getPplNo())) {
                detail.setStockType(InventoryTypeEnum.GK_PPL.getCode());
            }
            if (StringUtils.isNotBlank(detail.getProjectNo())) {
                detail.setStockType(InventoryTypeEnum.GK_PJ.getCode());
            }
            if (StringUtils.isNotBlank(detail.getGroupCustomerNo())) {
                detail.setStockType(InventoryTypeEnum.ZL_JT.getCode());
            }

            details.add(detail);
        }
        createDto.setDetails(details);

        // 处理备库申请
        ResultVo<String> handleResult = preStockFeignApi.handleSMSPreStockApply(createDto);
        log.info("门户客户备库申请处理结果: {} => {}", apply.getId(), handleResult);
        if (!handleResult.isSuccess()) {
            throw new BusinessException(handleResult.getMessage());
        }

        result.setERPno(apply.getId());
        result.setResult(true);
        return result;
    }

    @Override
    public ResultVo<Boolean> sendPreStockDetailStatusMessage(AdapterPreStockDTO dto) {
        log.info("回调门户先行在库处理状态 {} : {}", dto.getNo(), dto);
        dto.setDataType("1");
        AdapterResult result = BeanCopyUtil.copy(dto, AdapterResult.class);

        if (dto.getNo().startsWith("ZK")) {
            RabbitMqMessage rabbitMqMessage = new RabbitMqMessage(JSON.toJSONString(result),
                    Constants.ERP_CUSTOMERINSTOCK_CREATE_RETURN,
                    Constants.CUSTOMERINSTOCK,
                    Constants.SMS);
            sendMessage.opsToSmsYyqb(rabbitMqMessage);
        } else if (dto.getNo().startsWith("FK")) {
            RabbitMqMessage rabbitMqMessage = new RabbitMqMessage(JSON.toJSONString(result),
                    Constants.ERP_SUBTREASURY_CREATE_RETURN,
                    Constants.SUBTREASURY,
                    Constants.SMS);
            sendMessage.opsToSmsYyqb(rabbitMqMessage);
        }
        return ResultVo.success(true);
    }

    @Override
    public List<AdapterResult> handlePreOccupiedInventoryCreateMQ(PreSaleInventory preSaleInventory) {
        if (CollectionUtils.isEmpty(preSaleInventory.getItems())) {
            return null;
        }
        List<AdapterResult> resultList = new ArrayList<>(preSaleInventory.getItems().size());
        AdapterResult result;
        String salesInfoNo;
        InventoryForAdjustDto adjustDto;
        CommonResult<String> adjustResult;

        for (PreSaleInventoryItem item : preSaleInventory.getItems()) {
            salesInfoNo = item.getId() + "-" + item.getItemId();
            adjustDto = new InventoryForAdjustDto();
            adjustDto.setDeptno(preSaleInventory.getDeptNo());
            adjustDto.setCustomerNo(preSaleInventory.getCustomerNo());
            adjustDto.setSalesInfoNo(salesInfoNo);
            adjustDto.setWarehouseCode(item.getStockCode());
            adjustDto.setModelno(item.getModelNo());
            adjustDto.setQty(item.getAuditQuantity());
            adjustDto.setQaStatus(QAStatusEnum.NORMAL.getType());
            adjustDto.setExpDate(item.getExpiryDate());
            adjustDto.setOrderId(salesInfoNo);
            log.info("情报号申请预占库存 salesInfoNo = {}, {}", adjustDto.getSalesInfoNo(), adjustDto.toString());
            adjustResult = opsWmDispatchForOrderFeignApi.salesInfo(adjustDto);
            log.info("情报号申请预占库存结果: salesInfoNo = {}, {}", adjustDto.getSalesInfoNo(), JSON.toJSONString(adjustResult));

            // 设置返回结果
            result = new AdapterResult();
            result.setNo(item.getId());
            result.setItemNo(item.getItemId());
            result.setModelNo(item.getModelNo());
            result.setResult(adjustResult.isSuccess());
            result.setMessage(adjustResult.getMessage());
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public AdapterResult handlePreOccupiedInventoryCancelMQ(PreSaleInventory preSaleInventory) {
        AdapterResult result = new AdapterResult();
        result.setResult(true);
        result.setNo(preSaleInventory.getId());
        result.setOperateType(preSaleInventory.getOperateType());
        Map<Integer, String> modelItem = new HashMap<>(preSaleInventory.getItems().size());
        Map<Integer, String> statusMap = new HashMap<>(preSaleInventory.getItems().size());
        result.setItemModelList(modelItem);
        result.setItemListStatus(statusMap);

        String salesInfoNo;
        CommonResult<String> adjustResult;

        for (PreSaleInventoryItem item : preSaleInventory.getItems()) {
            salesInfoNo = item.getId() + "-" + item.getItemId();
            log.info("取消情报号预占库存 salesInfoNo = {}", salesInfoNo);
            adjustResult = opsWmDispatchForOrderFeignApi.undoSalesInfo(salesInfoNo);
            log.info("取消情报号预占库存结果 salesInfoNo = {}, {}", salesInfoNo, JSON.toJSONString(adjustResult));
            // 构建返回结果
            if (!adjustResult.isSuccess()) {
                if (SalesInfoErrorCodeEnum.NON_UNIQUE_PROPERTY.getDesc().equals(adjustResult.getMessage())) {
                    statusMap.put(item.getItemId(), Boolean.FALSE.toString());
                    result.setResult(false);
                    result.setMessage(salesInfoNo + adjustResult.getMessage() + ";");
                } else {
                    statusMap.put(item.getItemId(), Boolean.TRUE.toString());
                }
            } else {
                statusMap.put(item.getItemId(), Boolean.TRUE.toString());
            }
            modelItem.put(item.getItemId(), item.getModelNo());
        }
        return result;
    }

    // edit by DengDengHui, 2022-10-26 for bug-8395
    @Override
    public List<BinTransferReturn> handleCustomerInStockTransferCreateMQ(BinTransferInfo info) {
        if (CollectionUtils.isEmpty(info.getItems())) {
            return null;
        }

        StockAssemblyApplyDTO dto = new StockAssemblyApplyDTO();
        dto.setApplyNo(info.getNo());
        dto.setDeptNo(info.getDeptNo());
        dto.setStatus("3");  // 已审核确认
        dto.setAssembleType("3"); // 调库
        dto.setApplyType("2"); // 调库
        dto.setApplyDate(DateUtil.getNow());
        dto.setApprovePsn(info.getEmployeeNo());

        List<StockAssemblyItemDTO> outItems = new ArrayList<>(info.getItems().size());
        List<StockAssemblyItemDTO> inItems = new ArrayList<>(info.getItems().size());
        StockAssemblyItemDTO itemDTO;
        double quantity;
        int detailStatus = 2; // 待处理
        int noTransStatus = 7; // 不处理
        String[] noTransferCustomer = new String[]{"95001", "95002"}; // 不调库客户拦截
        boolean isNoTransfer;

        for (BinTransferInfoItem item : info.getItems()) {
            isNoTransfer = Arrays.binarySearch(noTransferCustomer, item.getCustomerNoTrans()) > 0;
            // 调出
            String[] stockInfo = item.getStockTrans().split("-");
            quantity = Double.valueOf(String.valueOf(item.getQuantity()));
            itemDTO = new StockAssemblyItemDTO();
            itemDTO.setModelNo(item.getModelNo());
            itemDTO.setIsTransOut(Boolean.TRUE);
            itemDTO.setQuantity(-quantity);
            itemDTO.setWarehouseCode(stockInfo[1]);
            itemDTO.setCustomerNo(item.getCustomerNoTrans());
            itemDTO.setInventoryType(InventoryTypeEnum.TY.getCode());
            if (StringUtils.isNotBlank(itemDTO.getCustomerNo())) {
                itemDTO.setInventoryType(InventoryTypeEnum.GK_TY.getCode());
            }
            if (StringUtils.isNotBlank(item.getBomNo())) {
                itemDTO.setPplNo(item.getBomNo());
                itemDTO.setInventoryType(InventoryTypeEnum.GK_PPL.getCode());
            }
            if (StringUtils.isNotBlank(item.getProjectCode())) {
                itemDTO.setProjectNo(item.getProjectCode());
                itemDTO.setInventoryType(InventoryTypeEnum.GK_PJ.getCode());
            }
            // Add by DengDengHui 2022-11-03 for bug-8547
            if (isNoTransfer) {
                itemDTO.setOptCode(noTransStatus);
                itemDTO.setRemark("不调库客户库存拦截");
            } else {
                itemDTO.setOptCode(detailStatus);
            }
            // End
            outItems.add(itemDTO);

            // 调入
            itemDTO = new StockAssemblyItemDTO();
            itemDTO.setModelNo(item.getModelNo());
            itemDTO.setIsTransOut(Boolean.FALSE);
            itemDTO.setQuantity(quantity);
            itemDTO.setWarehouseCode(stockInfo[1]); // 业务要求门户端不进行异仓调拨,只进行同仓调库
            if (StringUtils.isBlank(info.getCustomerNo())) {
                itemDTO.setInventoryType(InventoryTypeEnum.TY.getCode()); //TY 通用在库
            } else {
                itemDTO.setCustomerNo(info.getCustomerNo());
                itemDTO.setInventoryType(InventoryTypeEnum.GK_TY.getCode()); // GK-TY 客户通用在库
            }
            // Add by DengDengHui 2022-11-03 for bug-8547
            if (isNoTransfer) {
                itemDTO.setOptCode(noTransStatus);
                itemDTO.setRemark("不调库客户库存拦截");
            } else {
                itemDTO.setOptCode(detailStatus);
            }
            // End
            inItems.add(itemDTO);
        }
        dto.setOutItems(outItems);
        dto.setInItems(inItems);

        // 处理门户调库申请
        List<BinTransferReturn> resultList = new ArrayList<>(info.getItems().size());
        if (CollectionUtils.isNotEmpty(dto.getOutItems())) {
            ResultVo<List<TransferResult>> handleResult = stockAssemblyFeignApi.handleSMSInStockApply(dto);
            log.info("门户客户在库调库申请处理结果 {} => {}", info.getNo(), handleResult);
            if (!handleResult.isSuccess()) {
                throw new BusinessException(handleResult.getMessage());
            }
            BinTransferReturn result;
            for (TransferResult res : handleResult.getData()) {
                result = new BinTransferReturn();
                result.setNo(res.getNo());
                result.setItemId(res.getItemId());
                result.setModelNo(res.getModelNo());
                result.setResult(res.isResult());
                result.setMessage(res.getMessage());
                resultList.add(result);
            }
        }
        return resultList;
    } // end

    private AdapterResult getAdapterResult(ChinaRegionWarehouseSupplyApply apply, ResultVo<String> handleResult) {
        AdapterResult result = new AdapterResult();
        result.setDataType("0");
        result.setNo(apply.getId());
        Map<Integer, String> map = new HashMap<>(apply.getItems().size());
        apply.getItems().forEach(item -> map.put(item.getItemId(), item.getModelNo()));
        result.setItemModelList(map);
        result.setResult(handleResult.isSuccess());
        if (handleResult.isSuccess()) {
            result.setERPno(apply.getId());
            result.setResult(true);
            result.setMessage(handleResult.getData());
        } else {
            result.setResult(false);
            result.setMessage(handleResult.getMessage());
        }
        return result;
    }

    /**
     * 查询营业所最先出库的中心仓库
     *
     * @param deptNo 部门代码
     * @return 仓库代码
     */
    private String getDeptPriorityCentralWarehouse(String deptNo) {
        deptNo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(deptNo).getData();
        return warehouseConfigMapper.getDeptPriorityCentralWarehouse(deptNo);
    }

    /**
     * 获取整单组装标识
     *
     * @param assemble 组装标识
     * @return 整单组装标识
     */
    private String getDetailSpecMark(String assemble) {
        if ("正常".equals(assemble)) {
            return "0";
        }
        if ("板".equals(assemble)) {
            return "1";
        }
        if ("阀".equals(assemble)) {
            return "2";
        }
        return "0";
    }

    /**
     * 获取运输方式代码
     *
     * @param transType 运输方式
     * @return 运输方式代码 (默认-海运)
     */
    private String getDetailTransType(String transType) {
        if (transType == null) {
            return "0";
        }
        String transTypeCode;
        switch (transType) {
            case "海运":
                transTypeCode = "0";
                break;
            case "空运":
                transTypeCode = "1";
                break;
            case "陆运":
                transTypeCode = "3";
                break;
            case "快船":
                transTypeCode = "4";
                break;
            case "铁路":
                transTypeCode = "5";
                break;
            default:
                transTypeCode = "0";
                break;
        }
        return transTypeCode;
    }

}
