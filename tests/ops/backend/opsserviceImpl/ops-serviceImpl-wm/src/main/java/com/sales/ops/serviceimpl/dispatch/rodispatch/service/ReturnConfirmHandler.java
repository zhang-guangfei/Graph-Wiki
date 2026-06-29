package com.sales.ops.serviceimpl.dispatch.rodispatch.service;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.ReturnConfirmContext;
import com.sales.ops.serviceimpl.dispatch.rodispatch.domain.ReturnConfirmContextItem;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.inventory.factory.InventoryFactory;
import com.sales.ops.utils.WmOrderNoFactory;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.CommonServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReturnConfirmHandler {

    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;
    @Autowired
    private WmOrderTaskService wmOrderTaskService;

    // 非组装按退货单+退货项，逐项调用传值,始终list.size=1，组装就按组装子项一次性传如组装3个子项，list.size=3  2022-12-1 旧
    // 按批次一次性传，非组装+组装=1+N（子项拆分），如2个非组装+1个组装（3个子项）， 传非组装2+组装3个子项，list.size=5  2022-12-8 新
    // 一次请求的申请号相同，每个申请号可以分多次回传，每个list有多个客单，如果客单是型号拆分则型号为subModelno
    public void returnConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException {
        // 检查参数
        checkParamsForReturnConfirm(list);
        // 同1退货单号分批传值，用小时区分（上游1小时传一次）
        // 根据申请号，生成发票号和发票id，发票号规则：TH+申请号+MMddHH
        ReturnConfirmContext context = new ReturnConfirmContext(list);
        // 检查发票号是否已经存在，如果发票号存在，测发票id也要换成已存在的（发票号和发票id一一对应）
        // 此接口一小时调用一次，正常来说发票号不会重复，为了保险还是查一遍重
        List<OpsRo> opsRoList = baseRoService.findRoByInvoiceNo(context.getInvoiceNo());
        if (!CollectionUtils.isEmpty(opsRoList)) {
            OpsRo opsRo = opsRoList.get(0);
            Long existInvoiceId = opsRo.getInvoiceId();
            context.setInvoiceId(existInvoiceId);
        }
        //bugid:16988 跳过单号查ro中申请号重复的数据（单号：orderId,orderItem,num和RoType=THRK RoSource=RETURN）
        outerLoop:
        for (CreInvMoveForReturnOrderDto inputDto : context.getDtoList()) {
            // 如果数量等于0，直接跳过
            if (inputDto.getQty() == 0) continue;// 当良品是0时，不继续执行
            // 初始化
            ReturnConfirmContextItem item = new ReturnConfirmContextItem(inputDto, context);

            //bugid:16988 20250304 C14717 ro中包含申请号跳过start------
            List<OpsRo> roList = baseRoService.findRoByOrderNoAndTHType(item.getOrderNo(), String.valueOf(item.getOrderItem()), item.getSplitItemNo());
            if(CollectionUtils.isNotEmpty(roList)){
                for(OpsRo opsRo: roList){
                    if(StringUtils.isNotBlank(opsRo.getInvoiceNo())){
                        String applyNo = opsRo.getInvoiceNo().split("-")[0];
                        if(item.getApplyNo().equals(applyNo)){
                            continue outerLoop;
                        }
                    }
                }
            }
            // end ------------------

            // 创建并写入退货在途库存
            createReturnInventory(item);
            // 创建物流指令
            createReturnRoDto(item);
            // 创建bacode
            createRoBarcode(item);
            // 创建roTask和barcodeTask
            createTasks(item);
            context.addItem(item);
        }
        // 批量插入ro\roItem\roItemInventory
        context.getItems().forEach(item -> baseRoService.insertRo(item.getRoDto()));
        // 批量插入barcode
        opsRoBarcodeService.insertBatchBarcode(context.getBarcodes());
        // 批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(context.getTasks());
    }

    // 创建RoBarcode
    private void createRoBarcode(ReturnConfirmContextItem item) throws OpsException {
        OpsRoBarcode roBarcode = new OpsRoBarcode();
        roBarcode.setInvoiceno(item.getInvoiceNo());
        roBarcode.setInvoiceid(item.getInvoiceId());
        roBarcode.setRoId(item.getRoDto().getRoId());
        roBarcode.setRoItem(1);
        roBarcode.setOrderno(item.getOrderNo());// 申请号
        roBarcode.setItemno(item.getOrderItem());// 申请子项
        roBarcode.setNum(item.getSplitItemNo());// 子项拆分项
        roBarcode.setAssNum(0);
        roBarcode.setQty(item.getQty());
        roBarcode.setDelflag(0);
        roBarcode.setModelno(item.getModelNo());
        roBarcode.setWarehouseCode(item.getWarehouseCode());
        roBarcode.setCreator(item.getPsnNo());
        roBarcode.setCreTime(new Date());
        roBarcode.setBarcode(generateBarcode(item));
        roBarcode.setScan(1);
        item.setBarcode(roBarcode);
    }

    // 生成一个随机箱码
    private String generateBarcode(ReturnConfirmContextItem item) throws OpsException {
        // 生成箱码
        ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("20");
        if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
            throw Exceptions.OpsException("生成barcode异常:" + item.getOrderNo() + "-" + item.getItemNo().toString());
        }
        return stringResultVo.getData();
    }

    // 创建 roTask 和 barcodeTask
    private void createTasks(ReturnConfirmContextItem item) throws OpsException {
        OpsWmOrderTask roTask = new OpsWmOrderTask();
        roTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
        roTask.setCreator(UserDto.WMS.getUserName());
        roTask.setCreTime(new Date());
        roTask.setWmOrderId(item.getRoDto().getRoId());
        roTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
        roTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
        OpsWmOrderTask barcodeTask = new OpsWmOrderTask();
        barcodeTask.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
        barcodeTask.setCreator(UserDto.WMS.getUserName());
        barcodeTask.setCreTime(new Date());
        barcodeTask.setWmOrderId(item.getRoDto().getRoId());
        barcodeTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
        barcodeTask.setTaskType(WmOrderTaskEnum.BARCODE.getType());
        item.setTasks(roTask, barcodeTask);
    }

    public void createReturnRoDto(ReturnConfirmContextItem item) {
        // 退货ROID=RO+[TH+applyNo+mmddhh]+itemNo(3位数)+splitNo(3位数)+数量（4位）
        // String roId = "RO" + item.getInvoiceNo() + String.format("%03d", item.getItemNo()) + String.format("%03d", item.getSplitItemNo()) + String.format("%04d", item.getQty());
        String roId = WmOrderNoFactory.THRK_ID(item.getInvoiceNo(), item.getItemNo(), item.getSplitItemNo(), item.getQty());
        OpsRo ro = new OpsRo();
        ro.setInvoiceNo(item.getInvoiceNo());
        ro.setInvoiceId(item.getInvoiceId());
        ro.setRoId(roId);
        // begin bug:8857 B28029 2022-12-09 退货申请号改成订单号
        ro.setOrderId(item.getOrderNo());
        ro.setOrderItem(String.valueOf(item.getOrderItem()));
        ro.setNum(item.getSplitItemNo());
        // end
        ro.setRoSource(RoSourceEnum.RETURN.getType());
        ro.setRoType(RoTypeEnum.THRK.getType());
        ro.setWarehouseCode(item.getWarehouseCode());
        ro.setRoStatus(RoStatusEnum.WAIT.getStatus());
        ro.setCreator(item.getPsnNo());
        ro.setCreTime(new Date());
        // RO调拨只有一条写入RO-Item
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(roId);
        roItem.setRoItem(1);
        roItem.setModelno(item.getModelNo());
        roItem.setQty(item.getQty());
        roItem.setRecQty(0);
        roItem.setQaStatus(QAStatusEnum.NORMAL.getType());
        roItem.setFromInventoryId(item.getReturnMoveId());
        roItem.setFromInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
        roItem.setRemark("退货处理");
        roItem.setCreator(item.getPsnNo());
        roItem.setCreTime(new Date());
        // 写入ops_ro_item_inventory
        OpsRoItemInventory roItemInventory = new OpsRoItemInventory();
        roItemInventory.setRoId(ro.getRoId());
        roItemInventory.setRoItem(roItem.getRoItem());
        roItemInventory.setRecQty(0);
        roItemInventory.setInventoryId(item.getReturnMoveId());
        roItemInventory.setQty(item.getQty());
        roItemInventory.setDelflag(0);
        roItemInventory.setVersion(0L);
        roItemInventory.setCreator(item.getPsnNo());
        roItemInventory.setCreTime(new Date());
        item.setRoDto(new OpsRoDto(ro, roItem, roItemInventory));
    }


    private void createReturnInventory(ReturnConfirmContextItem item) throws OpsException {
        // 检查退货在途是否已存在，如果存在，则抛异常
        notExistMove(item);
        long propertyId = getProperty(item);
        OpsInventoryMove returnMove = InventoryFactory.returnInventory(item, propertyId);
        // 写入采购在途（T4退货在途）
        Long returnMoveId = baseInventoryService.createInvMoveReturnId(returnMove, UserDto.WMS);
        item.setReturnMoveId(returnMoveId);
    }


    private long getProperty(ReturnConfirmContextItem item) throws OpsException {
        long inventoryPropertyId = 1L;
        // 客户通用库存
        if (item.getToUserStock()) {
            OpsInventoryProperty property = new OpsInventoryProperty();
            property.setInventoryTypeCode(InventoryTypeEnum.GKTY.getType());
            property.setCustomerNo(item.getCustomerNo());
            inventoryPropertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property, new UserDto("handReturn", null));
        }
        return inventoryPropertyId;
    }

    private void notExistMove(ReturnConfirmContextItem item) throws OpsException {
        List<OpsInventoryMove> opsInventoryMoves = baseInventoryService.getOpsInventoryMoveListByAssociateNo(InventoryStatusEnum.THTRANS
                , item.getModelNo(), item.getOrderNo(), item.getOrderItem(), item.getSplitItemNo(), item.getInvoiceNo());
        if (!CollectionUtils.isEmpty(opsInventoryMoves)) {
            throw Exceptions.OpsException("已产生在途库存数据，请勿重复生成." + item.getOrderNo() + "-" + item.getOrderItem() + "-" + item.getSplitItemNo() + "型号：" + item.getModelNo());
        }
    }

    private void checkParamsForReturnConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException {
        if (Objects.isNull(list)) {
            throw Exceptions.OpsException("参数解析失败--List<OpsImpdata>");
        }
        if (CollectionUtils.isEmpty(list)) {
            throw Exceptions.OpsException("无退货数据导入");
        }
        // 数据校验判断
        for (CreInvMoveForReturnOrderDto inputDto : list) {
            if (StringUtils.isEmpty(inputDto.getApplyNo())) {
                throw Exceptions.OpsException("退货单号不可为空");
            }
            if (inputDto.getItemNo() < 1) {
                throw Exceptions.OpsException("退货单项号不可为空");
            }
            if (StringUtils.isEmpty(inputDto.getWarehouseCode())) {
                throw Exceptions.OpsException("仓库代码不可为空");
            }
            if (StringUtils.isEmpty(inputDto.getCustomerNo())) {
                throw Exceptions.OpsException("客户代码不可为空");
            }
            if (StringUtils.isEmpty(inputDto.getModelNo())) {
                throw Exceptions.OpsException("型号不可为空");
            }
            if (inputDto.getQty() < 0) {
                throw Exceptions.OpsException("良品不可为负数");
            }
        }
    }


}
