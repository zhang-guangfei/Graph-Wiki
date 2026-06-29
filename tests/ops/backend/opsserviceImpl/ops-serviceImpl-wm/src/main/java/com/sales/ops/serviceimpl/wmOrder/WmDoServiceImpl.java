package com.sales.ops.serviceimpl.wmOrder;

import cn.hutool.core.collection.CollectionUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.OpsDoAndItemDto;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidDto;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidIDDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.sales.ops.event.publisher.enums.EventSourceEnum.WAREHOUSE_RECEIVE_CONFIRM;

/**
 * @author C02483
 * @version 1.0
 * @description: TODO
 * @date 2021/9/21 21:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class WmDoServiceImpl implements WmDoService {

    @Autowired
    private WmOrderTaskService wmOrderTaskService;//下发富勒表

    @Autowired
    private WmOrderTaskFindService findWmsOrderTaskService;

    @Autowired
    private WmRouterOrderService wmRouterOrderService;

    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private BaseDoService baseDoService;

    @Autowired
    private OpsPcoService opsPcoService;
    @Resource
    private WmDispatchService wmDispatchService;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    @Autowired
    private OpsWmFeignApi opsWmFeignApi;
    @Resource
    private BaseInventoryService baseInventoryService;
    @Resource
    private WmOrderTaskFindService wmOrderTaskFindService;

    @Autowired
    private CustomerEventPublisher customerEventPublisher;


    /**
     * 货齐判断 检查do（pco）指令和关联仓库是否一致
     * 如果不一致记录异常表，不下发该指令
     */
    private void checkInstructionsWarehouseCode(List<String> wmDoIdList, List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId) throws OpsException {
        boolean endFlag = false;
        String inventoryId = "";
        if (CollectionUtil.isNotEmpty(wmDoIdList)) {// do
            // 对比do的仓库号和ops_inventory的仓库号是否一致
            for (String wmOrderId : wmDoIdList) {
                OpsDo opsDo = baseDoService.getDoByDoId(wmOrderId);
                List<OpsDoItemInventory>  doItemInv = baseDoService.getDoItemInventoryByDoId(wmOrderId);
                for (OpsDoItemInventory obj : doItemInv){
                    //bugid:12401 c14717 2023/10/24 交易单判断越库 包含在途数据
                    String invWarehouseCode = "";
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())){
                        OpsInventory inv = baseInventoryService.getInventoryById(obj.getInventoryId());
                        invWarehouseCode = inv.getWarehouseCode();
                    }else {
                        OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(obj.getInventoryId());
                        invWarehouseCode = invMove.getSignWarehouseCode();//20231103 异仓收货 不改warehouseCode 所以用签收仓
                    }
                    if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
                        if(!invWarehouseCode.equals(opsDo.getGatherWarehouseCode())){
                            inventoryId = obj.getInventoryId()+"";
                            endFlag = true;
                            break;
                        }
                    }else {
                        if(!invWarehouseCode.equals(opsDo.getWarehouseCode())){
                            inventoryId = obj.getInventoryId()+"";
                            endFlag = true;
                            break;
                        }
                    }
                }
                if(endFlag){
                    // 记录异常表
                    opsDoService.saveDOopsExceptionHandle("checkIns",DoConfirmErrorCodeEnum.WAREHOUSE_DIFF.getCode(), 0,
                            "", "", "出库库存不一致",
                            wmOrderId, "", 0, opsDo.getWarehouseCode(), inventoryId, "", "", "");
                    //更新order_task状态
                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(wmOrderId,"出库库存不一致",2);
                    wmDoIdList.clear();
                    break;
                }
            }
        }
        if(CollectionUtil.isNotEmpty(doIdAndPcoId)){//pco
            for (OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto : doIdAndPcoId){
                //对比pco的仓库号和ops_inventory的仓库号是否一致
                OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoAndDoMidIDDto.getPcoId());
                List<OpsPcoItemInventory> pcoItemInv = opsPcoService.getPcoItemInventoryByPcoId(pcoAndDoMidIDDto.getPcoId());
                for (OpsPcoItemInventory obj : pcoItemInv){
                    //bugid:12401 c14717 2023/10/24 交易单判断越库 包含在途数据
                    String invWarehouseCode = "";
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())){
                        OpsInventory inv = baseInventoryService.getInventoryById(obj.getInventoryId());
                        invWarehouseCode = inv.getWarehouseCode();
                    }else {
                        OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(obj.getInventoryId());
                        invWarehouseCode = invMove.getSignWarehouseCode();//20231103 异仓收货 不改warehouseCode 所以用签收仓
                    }
                    if(!invWarehouseCode.equals(opsPco.getWarehouseCode())){
                        inventoryId = obj.getInventoryId()+"";
                        endFlag = true;
                        break;
                    }
                }
                if(endFlag){
                    // 记录异常表
                    opsDoService.saveDOopsExceptionHandle("checkIns",DoConfirmErrorCodeEnum.WAREHOUSE_DIFF.getCode(), 0,
                            "", "", "出库库存不一致",
                            pcoAndDoMidIDDto.getDoId(), "", 0, opsPco.getWarehouseCode(), inventoryId, "", opsPco.getPcoId(), "");
                    //更新order_task状态
                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(pcoAndDoMidIDDto.getPcoId(),"出库库存不一致",2);
                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(pcoAndDoMidIDDto.getDoId(),"出库库存不一致",2);
                    doIdAndPcoId.clear();
                    break;
                }
            }
        }
    }

    /**
     * bugid:16290
     * 货齐判断 检查do（pco）
     * 特殊订单，特殊型号，出指定仓库校验
     */
    private void checkSpeModelNo(List<String> wmDoIdList, List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId) throws OpsException {
        boolean endFlag = false;
        if (CollectionUtil.isNotEmpty(wmDoIdList)) {// do
            // 对比do的仓库号和ops_inventory的仓库号是否一致
            for (String wmOrderId : wmDoIdList) {
                OpsDoItem doItem = baseDoService.getDoItemByDoId(wmOrderId);
                Boolean speFlag = baseDoService.checkSpeModelNoSpeWarehouse(doItem.getModelno());
                if(speFlag) {
                    OpsDo opsDo = baseDoService.getDoByDoId(wmOrderId);
                    if(opsDo.getWarehouseCode().equals(opsDo.getGatherWarehouseCode())){
                        List<OpsDoItemInventory>  doItemInv = baseDoService.getDoItemInventoryByDoId(wmOrderId);
                        for (OpsDoItemInventory obj : doItemInv){
                            //bugid:12401 c14717 2023/10/24 交易单判断越库 包含在途数据
                            String invWarehouseCode = "";
                            if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())){
                                OpsInventory inv = baseInventoryService.getInventoryById(obj.getInventoryId());
                                invWarehouseCode = inv.getWarehouseCode();
                            }else {
                                OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(obj.getInventoryId());
                                invWarehouseCode = invMove.getSignWarehouseCode();//20231103 异仓收货 不改warehouseCode 所以用签收仓
                            }
                            if(!invWarehouseCode.equals("KLS")){
                                endFlag = true;
                                break;
                            }
                        }
                    }else {
                        //特殊型号无调拨单
                        endFlag = true;
                    }

                    if(endFlag){
                        // 记录异常表
                        opsDoService.saveDOopsExceptionHandle("checkSpeciModelNo",DoConfirmErrorCodeEnum.SPECAIL_MODELNO.getCode(), 0,
                                "", "", "特殊型号需固定仓",
                                wmOrderId, "", 0, "", "", "", "", "");
                        //更新order_task状态
                        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(wmOrderId,"特殊型号需固定仓",2);
                        wmDoIdList.clear();
                        break;
                    }
                }

            }
        }
        if(CollectionUtil.isNotEmpty(doIdAndPcoId)){//pco
            for (OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto : doIdAndPcoId){
                //对比pco的仓库号和ops_inventory的仓库号是否一致
                OpsDoItem doItem = baseDoService.getDoItemByDoId(pcoAndDoMidIDDto.getDoId());
                String modelNo = doItem.getModelno();
                Boolean speFlag = baseDoService.checkSpeModelNoSpeWarehouse(modelNo);
                if(speFlag){
                    List<OpsPcoItemInventory> pcoItemInv = opsPcoService.getPcoItemInventoryByPcoId(pcoAndDoMidIDDto.getPcoId());
                    for (OpsPcoItemInventory obj : pcoItemInv){
                        //bugid:12401 c14717 2023/10/24 交易单判断越库 包含在途数据
                        String invWarehouseCode = "";
                        if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())){
                            OpsInventory inv = baseInventoryService.getInventoryById(obj.getInventoryId());
                            invWarehouseCode = inv.getWarehouseCode();
                        }else {
                            OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(obj.getInventoryId());
                            invWarehouseCode = invMove.getSignWarehouseCode();//20231103 异仓收货 不改warehouseCode 所以用签收仓
                        }
                        if(!invWarehouseCode.equals("KLS")){
                            endFlag = true;
                            break;
                        }
                    }
                    if(endFlag){
                        // 记录异常表
                        opsDoService.saveDOopsExceptionHandle("checkSpeciModelNo",DoConfirmErrorCodeEnum.SPECAIL_MODELNO.getCode(), 0,
                                "", "", "特殊型号需固定仓",
                                pcoAndDoMidIDDto.getDoId(), "", 0, "", "", "", "", "");
                        //更新order_task状态
                        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(pcoAndDoMidIDDto.getPcoId(),"特殊型号需固定仓",2);
                        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(pcoAndDoMidIDDto.getDoId(),"特殊型号需固定仓",2);
                        doIdAndPcoId.clear();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 出库下发改变wmordertask状态为ready do单
     */
    @Override
    public Integer sendDoToWMSChangeStatus(Integer limit,OpsWmOrderTask opsWmOrderTask) throws OpsException {
        //变更的 wm_order_id list
        List<String> wmOrderIdList = new ArrayList<String>(limit);
        //货齐判断，收集货齐指令
        collectDo(wmOrderIdList,opsWmOrderTask);
        //bugid:9441 20230201 c14717
        // 判断do仓库号和关联库存仓库号是否一致 根据发货方式不同，如果存在多条指令，只有其中一条失败，那么都不下发
        checkInstructionsWarehouseCode(wmOrderIdList,null);
        //判断特殊型号
        checkSpeModelNo(wmOrderIdList,null);
        //下发改0
        updateDoToWms(wmOrderIdList);
        return wmOrderIdList.size();

    }


    /**
     * 出库下发改变wmordertask状态为ready 加工单
     * @param
     */
    @Override
    public Integer sendPcoToWMSChangeStatus(Integer limit,OpsWmOrderTask opsWmOrderTask) throws OpsException {
        //变更的 wm_order_id list
        List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId = new ArrayList<OpsSendPcoAndDoMidIDDto>(limit);
        //货齐判断收集货齐指令
        collectPco(opsWmOrderTask,doIdAndPcoId);
        //bugid:9441 20230201 c14717
        // 判断pco仓库号和关联库存仓库号是否一致，如果存在多条指令，只有其中一条失败，那么都不下发
        checkInstructionsWarehouseCode(null,doIdAndPcoId);

        //判断特殊型号
        checkSpeModelNo(null,doIdAndPcoId);
        //下发doIdAndPcoId
        for(OpsSendPcoAndDoMidIDDto obj : doIdAndPcoId){
            updateWMSPcoAddDoTwo(obj);//货齐判断 pco和do同时下发
        }
        return doIdAndPcoId.size();
    }

    /**
     * bugid:12401 c14717 2023/10/24
     * 交易单判断越库 直接下发
     * @return
     */
    public boolean roCrossTypeDo(List<String> wmOrderIdList,OpsDo opsDo){
        //越库
        if (Objects.nonNull(opsDo.getRoCrossType()) && opsDo.getRoCrossType() == 2) {
            wmOrderIdList.add(opsDo.getDoId());
            return true;
        }
        return false;
    }

    /**
     * bugid:12401 c14717 2023/10/24
     * 加工单判断越库 直接下发
     * @return
     */
    public boolean roCrossTypePCO(List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId,OpsPco opsPco) throws OpsException{
        //越库 判断roid 字段是否为空
        if (org.apache.commons.lang3.StringUtils.isNotBlank(opsPco.getRoId())) {
            List<OpsDo> opsDos = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
            OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto(opsDos.get(0).getDoId(),opsPco.getPcoId(),
                    opsDos.get(0).getOrderId()+"-"+opsDos.get(0).getOrderItem(),"在库货齐判断下发");
            doIdAndPcoId.add(obj);
            return true;
        }
        return false;
    }

    private void updateDoToWms(List<String> wmOrderIdList) throws OpsException{
        //判断货齐下发富勒
        for (String wmOrderId : wmOrderIdList) {
            //更新order_task状态
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(wmOrderId, WmOrderTaskEnum.DO, 0, "货齐");
            //产生交易出库货齐状态事件
            OpsDo opsDo = baseDoService.getDoByDoId(wmOrderId);
            if (opsDo != null && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_GOODS_READY,
                        opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
            }
        }
    }

    /**
     * 计算do是否货齐
     * @param wmOrderIdList
     * @param opsWmOrderTask
     * @throws OpsException
     */
    @Override
    public void collectDo(List<String> wmOrderIdList, OpsWmOrderTask opsWmOrderTask) throws OpsException{
        OpsDoAndItemDto opsDoAndItemDto = opsDoService.findDoToWms(opsWmOrderTask.getWmOrderId());
        if (null == opsDoAndItemDto) {
            log.info("opsDoAddItemDto为空 ：" + opsWmOrderTask.getWmOrderId());
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(),WmOrderTaskEnum.DO,2,"该单已删除");
            return;
        }
        OpsDo opsDo = opsDoAndItemDto.getOpsDo();
        OpsDoItem opsDoItem = opsDoAndItemDto.getList().get(0);
        if (opsDo == null || opsDoItem == null) {
            log.info("do为空 ：" + opsWmOrderTask.getWmOrderId());
            return;
        }
        //20231106 c14717 加工单的交易单不判断货齐
        if(DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()) && DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())){
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(),WmOrderTaskEnum.DO,4,"拆分型号");
            return;
        }
        //bugid:12401 c14717 2023/10/24 交易单判断越库 直接下发
        boolean crossType =  roCrossTypeDo(wmOrderIdList,opsDo);
        if(crossType){
            log.info("do为越库单 ：" + opsWmOrderTask.getWmOrderId());
            return;
        }
        if(!DoWaitTypeEnum.OK.getType().equals(opsDo.getWaitType())){
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(),WmOrderTaskEnum.DO,4,"货不齐");
            return;
        }
        boolean isEnough = true;
        //调拨出
        if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())
                || DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())
                || DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())){
            // 验证doItemInv在库数量是否齐
            isEnough = checkDoItemInvQtyEnough(opsDo.getDoId(),opsDoItem.getQty());
            if (isEnough) {
                wmOrderIdList.add(opsWmOrderTask.getWmOrderId());
            }
            //采购调拨出库
        } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(opsDo.getOrderId());
            if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
                || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
            ) {//整单单仓货齐 或 货齐多仓
                //判断所有订单是否处理完毕
                List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null, DoTypeEnum.JYCK);
                // 判断7位订单是否分配完成 bugid:14095 c14717 2024-04-30 货齐计算优化 分配完成 返回true
                boolean allocationFinishFlag = checkInitStatus(opsDo.getOrderId());
                if(!allocationFinishFlag){
                    log.info("7位订单未分配完成" + opsWmOrderTask.getWmOrderId());
                    return ;
                }
                // 判断waitType和在库数量
                isEnough = checkWaitTypeAndQty(opsDo.getOrderId(),doList);
                if (isEnough) {//满足出库条件
                    wmOrderIdList.add(opsWmOrderTask.getWmOrderId());
                }
            }else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
                    || CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode().equals(rcvmaster.getDlvEntire())) {//单项单仓货齐发货
                List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
                // 判断waitType和在库数量
                isEnough = checkWaitTypeAndQty(null,doList);
                if (isEnough) {//满足出库条件
                    wmOrderIdList.add(opsWmOrderTask.getWmOrderId());
                }
            } else if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire()) //随发分批
                    || CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())) {//通知发货
                // 判断在库数量
                isEnough = checkDoItemInvQtyEnough(opsDo.getDoId(),opsDoItem.getQty());
                if (isEnough) {
                    wmOrderIdList.add(opsWmOrderTask.getWmOrderId());
                }
            }
        }
        if (!isEnough) {//等待入库
            //更新order_task 状态
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(),WmOrderTaskEnum.DO,4,"货不齐");
        }
    }

    /**
     * 验证加工单的doList中isWms不等于1的ItemInv数量是否一致
     * @param doList
     * @return
     * @throws OpsException
     */
    @Override
    public boolean checkPCOAndDoList(List<OpsDo> doList) throws OpsException{
        for (OpsDo opsDoSub : doList) {
            if(opsDoSub.getIsWms() != 1){
                if (DoWaitTypeEnum.OK.getType().equals(opsDoSub.getWaitType())) {
                    OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDoSub.getDoId());
                    boolean isEnough = checkDoItemInvQtyEnough(opsDoSub.getDoId(),opsDoItem.getQty());
                    if(!isEnough){
                        return false;
                    }
                } else if (DoWaitTypeEnum.WaitJG.getType().equals(opsDoSub.getWaitType())) {//加工单货齐判断
                    List<OpsPco> opsPcoList = opsPcoService.GetPcolistByOrder(opsDoSub.getOrderId(), opsDoSub.getOrderItem());
                    for (OpsPco opsPcoSub : opsPcoList) {
                        //看等待类型OK
                        List<OpsPcoItem> opsPcoSubList = opsPcoService.selectItemBypcoId(opsPcoSub.getPcoId());
                        boolean isEnough = checkPcoItemInvQtyEnough(opsPcoSubList);
                        if (!isEnough) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证doItemInv在库数量是否齐
     * @param doId
     * @param doItemQty
     * @return
     */
    @Override
    public boolean checkDoItemInvQtyEnough(String doId, Integer doItemQty){
        List<OpsDoItemInventory> dtolist = baseDoService.getDoItemInventoryByDoId(doId,InventoryStatusEnum.NORMAL.getCode() ,0);
        if (CollectionUtils.isEmpty(dtolist)) {
            return false;
        }
        int inventoryQty = 0;
        for (OpsDoItemInventory opsDoItemInventory : dtolist) {
            inventoryQty = inventoryQty + opsDoItemInventory.getUseQty();
        }
        if (inventoryQty != doItemQty) { //数量不够
            return false;
        }
        return true;
    }

    /**
     * 验证pcoItemInv在库数量是否齐
     * @param pcoItems
     * @return
     */
    @Override
    public boolean checkPcoItemInvQtyEnough(List<OpsPcoItem> pcoItems){
        for (OpsPcoItem opsPcoItem : pcoItems) {
            if (!DoWaitTypeEnum.OK.getType().equals(opsPcoItem.getWaitType())) {
                return false;
            }
        }
        for (OpsPcoItem opsPcoItem : pcoItems) {
            List<OpsPcoItemInventory> subPcoItemsInventoryList = opsPcoService.selectItemInventoryBypcoId(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem(), InventoryTableTypeEnum.NORMAL);
            int inventoryQty = 0;
            for (OpsPcoItemInventory opsPcoItemInventory : subPcoItemsInventoryList) {
                inventoryQty = inventoryQty + opsPcoItemInventory.getUseQty();
            }
            if (inventoryQty != opsPcoItem.getSubQty()) { //数量不够
                return false;
            }
        }
        return true;
    }

    /**
     * bugid:14095 c14717 2024-04-30 货齐计算优化
     * 货齐单仓和货齐多仓是，7位订单全部分配完成 返回true
     * @param orderId
     * @return
     */
    @Override
    public boolean checkInitStatus(String orderId){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(orderId)){
            List<Rcvdetail> rcvDetailList = wmRouterOrderService.getRcvdetailListByOrder(orderId);
            for (Rcvdetail rcvdetail : rcvDetailList) {
                if (RcvOrderStatusEnum.INIT.getType() == rcvdetail.getStatus()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证等待类型是否OK
     * @param orderId
     * @param opsDos
     * @return
     */
    @Override
    public boolean checkWaitTypeAndQty(String orderId, List<OpsDo> opsDos) throws OpsException{

        for (OpsDo opsDoSub : opsDos) { //判断子项是否都是ok
            if(opsDoSub.getIsWms() != 1){
                //等待调拨、等待采购、异常状态
                if (DoWaitTypeEnum.WaitDB.getType().equals(opsDoSub.getWaitType())
                        || DoWaitTypeEnum.WaitCG.getType().equals(opsDoSub.getWaitType())
                        || DoWaitTypeEnum.EXCEPTION.getType().equals(opsDoSub.getWaitType())) {
                    return false;
                } else if (DoWaitTypeEnum.WaitJG.getType().equals(opsDoSub.getWaitType())) {//等待加工，看子型号
                    List<OpsPco> opsPcos = opsPcoService.GetPcolistByOrder(opsDoSub.getOrderId(), opsDoSub.getOrderItem());
                    for(OpsPco opsPco : opsPcos){
                        List<OpsPcoItem> opsPcoItems = opsPcoService.findPcoItemByPcoId(opsPco.getPcoId());
                        for (OpsPcoItem opsPcoItemSub : opsPcoItems) {
                            if (!DoWaitTypeEnum.OK.getType().equals(opsPcoItemSub.getWaitType())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        //判断在库数量
        return checkPCOAndDoList(opsDos);
    }

    @Override
    public CommonResult<String> updateWMSPcoAddDoTwo(OpsSendPcoAndDoMidIDDto obj) throws OpsException{
        //收集数据
        OpsDoAndItemDto doDto =  opsDoService.findDoToWms(obj.getDoId());
        OpsPcoAddItemDto pcoDto =  opsPcoService.findPcoToWms(obj.getPcoId());
        if(Objects.isNull(doDto)){
            return CommonResult.failure("do表无数据或已删除 doId:"+obj.getDoId());
        }
        if(Objects.isNull(pcoDto)){
            return CommonResult.failure("pco表无数据或已删除 pcoId:"+obj.getPcoId());
        }
        // 发送货齐就绪ready事件
        if (DoTypeEnum.JYCK.getType().equals(doDto.getOpsDo().getDoType())) {
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_GOODS_READY,
                    doDto.getOpsDo().getOrderId(), Integer.valueOf(doDto.getOpsDo().getOrderItem()));
        }
        if(!StringUtils.isEmpty(obj.getYuKuRoId())){
            pcoDto.setYuKuRoId(obj.getYuKuRoId());
            doDto.setYuKuRoId(obj.getYuKuRoId());
        }
        if(!CollectionUtils.isEmpty(obj.getYuKuMap())){
            pcoDto.setYuKuMap(obj.getYuKuMap());
        }
        //用于订单修改
        if(Objects.nonNull(obj.getUpdateOpsDo())){
            doDto.setOpsDo(obj.getUpdateOpsDo());
        }
        //用于订单修改
        if(Objects.nonNull(obj.getUdateOpsPco())){
            pcoDto.setOpsPco(obj.getUdateOpsPco());
        }
        //bugid:11447 c14717 20230809 用于订单修改
        if(org.apache.commons.lang3.StringUtils.isNotBlank(obj.getCproductNo())){
            doDto.getList().get(0).setCproductNo(obj.getCproductNo());
        }
        //传入taskId
        OpsWmOrderTask opsWmOrderTaskDo = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(obj.getDoId(),WmOrderTaskEnum.DO);
        if(Objects.isNull(opsWmOrderTaskDo)){
            return CommonResult.failure("task表无do doId:"+obj.getDoId());
        }
        doDto.setWmOrderTaskId(opsWmOrderTaskDo.getId());
        OpsWmOrderTask opsWmOrderTaskPco = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(obj.getPcoId(),WmOrderTaskEnum.PCO);
        if(Objects.isNull(opsWmOrderTaskPco)){
            return CommonResult.failure("task表无pco pcoID:"+obj.getPcoId());
        }
        pcoDto.setWmOrderTaskId(opsWmOrderTaskPco.getId());
        OpsSendPcoAndDoMidDto param = new OpsSendPcoAndDoMidDto(doDto,pcoDto,obj.getForderNo(),obj.getXfName());
        //调用下发接口
        CommonResult<String> result =  opsCallWmsFeignApi.updateDoPcoToWmsOrToWT(param);
        return result;
    }

    /**
     * 计算pco是否货齐
     * @param opsWmOrderTask
     * @param doIdAndPcoId
     * @throws OpsException
     */
    @Override
    public void collectPco(OpsWmOrderTask opsWmOrderTask,List<OpsSendPcoAndDoMidIDDto> doIdAndPcoId) throws OpsException{
        OpsPcoAddItemDto opsPcoAddItemDto = opsPcoService.findPcoToWms(opsWmOrderTask.getWmOrderId());
        if (null == opsPcoAddItemDto) {
            log.debug("opsPcoAddItemDto ：" + opsWmOrderTask.getWmOrderId());
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(),WmOrderTaskEnum.PCO,2,"该单已删除");
            return;
        }
        //调拨出
        OpsPco opsPco = opsPcoAddItemDto.getOpsPco();
        List<OpsPcoItem> opsPcoItems = opsPcoAddItemDto.getList();
        if (opsPco == null || opsPcoItems == null) {
            log.info("pco为空");
            return;
        }
        //bugid:12401 c14717 2023/10/24 加工单判断越库 直接下发
        boolean crossType = roCrossTypePCO(doIdAndPcoId,opsPco);
        if(crossType){
            log.info("pco为子项整单越库:" +opsPco.getPcoId());
            return ;
        }
        boolean isEnough = true;//是否货齐
        Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(opsPco.getOrderId());
        if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
            || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
        ) {//整单单仓货齐 货齐多仓
            List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(), null, null, DoTypeEnum.JYCK);
            // 判断7位订单是否分配完成  bugid:14095 c14717 2024-04-30 货齐计算优化 分配完成 返回true
            boolean allocationFinishFlag = checkInitStatus(opsPco.getOrderId());
            if(!allocationFinishFlag){
                log.info("7位订单未分配完成" +opsPco.getPcoId());
                return ;
            }
            // 判断waitType和在库数量
            isEnough = checkWaitTypeAndQty(opsPco.getOrderId(),doList);
            if (isEnough) {//满足出库条件
                List<OpsDo> opsDos = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
                if (CollectionUtils.isEmpty(opsDos)) {
                    throw Exceptions.OpsException("加工单货齐判断 do为空" + opsPco.getOrderId() + "-" + opsPco.getOrderItem(),opsPco.getPcoId());
                }
                OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto(opsDos.get(0).getDoId(),opsWmOrderTask.getWmOrderId(),
                        opsDos.get(0).getOrderId()+"-"+opsDos.get(0).getOrderItem(),"在库货齐判断下发");
                doIdAndPcoId.add(obj);
            }
        } else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
                || CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode().equals(rcvmaster.getDlvEntire())) {//单项单仓货齐发货
            List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
            if (CollectionUtils.isEmpty(doList)) {
                throw Exceptions.OpsException("加工单货齐判断 do为空" + opsPco.getOrderId() + "-" + opsPco.getOrderItem(),opsPco.getPcoId());
            }
            // 判断waitType和在库数量
            isEnough = checkWaitTypeAndQty(null,doList);
            if (isEnough) {//满足出库条件
                OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto(doList.get(0).getDoId(),opsWmOrderTask.getWmOrderId(),
                        doList.get(0).getOrderId()+"-"+doList.get(0).getOrderItem(),"在库货齐判断下发");
                doIdAndPcoId.add(obj);
            }
        } else if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())) {//单项分批随到发货
            //判断在库数量
            isEnough = checkPcoItemInvQtyEnough(opsPcoItems);
            if (isEnough) {//满足出库条件
                List<OpsDo> opsDos = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
                if (CollectionUtils.isEmpty(opsDos)) {
                    throw Exceptions.OpsException("加工单货齐判断 do为空" + opsPco.getOrderId() + "-" + opsPco.getOrderItem(),opsPco.getPcoId());
                }
                OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto(opsDos.get(0).getDoId(),opsWmOrderTask.getWmOrderId(),
                        opsDos.get(0).getOrderId()+"-"+opsDos.get(0).getOrderItem(),"在库货齐判断下发");
                doIdAndPcoId.add(obj);
            }
        } else if( CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){// 通知发货
            //判断在库数量
            isEnough = checkPcoItemInvQtyEnough(opsPcoItems);
            if (isEnough) {//满足出库条件
                List<OpsDo> opsDos = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), opsPco.getNum(), DoTypeEnum.JYCK);
                if (CollectionUtils.isEmpty(opsDos)) {
                    throw Exceptions.OpsException("加工单货齐判断 do为空" + opsPco.getOrderId() + "-" + opsPco.getOrderItem(),opsPco.getPcoId());
                }
                OpsSendPcoAndDoMidIDDto obj = new OpsSendPcoAndDoMidIDDto(opsDos.get(0).getDoId(),opsWmOrderTask.getWmOrderId(),
                        opsDos.get(0).getOrderId()+"-"+opsDos.get(0).getOrderItem(),"在库货齐判断下发");
                doIdAndPcoId.add(obj);
            }
        }
        if (!isEnough) {
            // 更新order_task barcode状态
            wmOrderTaskService.updateWmOrderTaskFByConditionBetweenDetail(opsWmOrderTask.getWmOrderId(), WmOrderTaskEnum.PCO, 4, "货不齐");
        }
    }


    @Override
    public void sendToWmsForGoodsConfirm(Map<String, String> doSendMap, Map<String, OpsSendPcoAndDoMidIDDto> pcoSendMap) throws OpsException {
        // 下发do bug:11242 C12961 在do序列化后调用下发接口
        if (doSendMap != null && doSendMap.size() > 0) {
            for (Map.Entry<String, String> entry : doSendMap.entrySet()) {
                wmOrderTaskService.changeTaskFlagToTreeByDOIdOrPCOId(entry.getKey(), WmOrderTaskPriorityEnum.NINE);
            }
        }
        // 下发pco bug:11242 C12961 在do序列化后调用下发接口
        if (pcoSendMap != null && pcoSendMap.size() > 0) {
            for (Map.Entry<String, OpsSendPcoAndDoMidIDDto> entry : pcoSendMap.entrySet()) {
                // updateWMSPcoAddDoTwo(entry.getValue());
                wmOrderTaskService.changeTaskFlagToTreeByDOIdOrPCOId(entry.getKey(), WmOrderTaskPriorityEnum.NINE);
            }
        }
    }

    /**
     * DO指令下发wms或wt  代替 opsCallWmsFeignApi.updateDoToWms
     * 用于二次下发
     * bugids:12187 c14717 20230921
     */
    @Override
    public CommonResult<String> postWmsDoNew(OpsDoAndItemDto dto) throws OpsException{
        CommonResult<String> wmJYCKResult = opsCallWmsFeignApi.updateDoToWmsNew(dto);//查看是否可删除
        //bugid:12294 c14717 20231019
        OpsWmOrderTask wmTaskPo = null;
        if(wmJYCKResult.isSuccess()){
            opsDoService.updateDOisWMSbyID(dto.getOpsDo().getId());
            //wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(dto.getOpsDo().getDoId(),"下发成功",1);
            wmTaskPo = wmOrderTaskService.createWmTaskPo(dto.getOpsDo().getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUCCESS,new CreateInfoDto("二次下发"), DateUtil.getNow(),1,"下发成功");
            //变更下发状态
            opsDoService.prepareOperation(dto.getOpsDo().getDoId());
        }else {
            //wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(dto.getOpsDo().getDoId(),wmJYCKResult.getMessage(),2);
            wmTaskPo = wmOrderTaskService.createWmTaskPo(dto.getOpsDo().getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.FAIR,new CreateInfoDto("二次下发"), null,1,wmJYCKResult.getMessage());
        }
        wmOrderTaskService.addOpsWmOrderTask(wmTaskPo);
        return wmJYCKResult;
    }

    /**
     *  bugid:12187 c14717 20230921 代替 updateWMSPcoAddDoTwo
     *  用于二次下发
     * @param obj
     * @return
     * @throws OpsException
     */
    @Override
    public CommonResult<String> postWmsDoAndPcoNew(OpsSendPcoAndDoMidIDDto obj) throws OpsException{

        //收集数据
        OpsDoAndItemDto doDto =  opsDoService.findDoToWms(obj.getDoId());
        OpsPcoAddItemDto pcoDto =  opsPcoService.findPcoToWms(obj.getPcoId());

        if(Objects.isNull(doDto)){
            return CommonResult.failure("do表无数据或已删除 doId:"+obj.getDoId());
        }
        if(Objects.isNull(pcoDto)){
            return CommonResult.failure("pco表无数据或已删除 pcoId:"+obj.getPcoId());
        }
        if(!StringUtils.isEmpty(obj.getYuKuRoId())){
            pcoDto.setYuKuRoId(obj.getYuKuRoId());
            doDto.setYuKuRoId(obj.getYuKuRoId());
        }
        if(!CollectionUtils.isEmpty(obj.getYuKuMap())){
            pcoDto.setYuKuMap(obj.getYuKuMap());
        }
        //用于订单修改
        if(Objects.nonNull(obj.getUpdateOpsDo())){
            doDto.setOpsDo(obj.getUpdateOpsDo());
        }
        //用于订单修改
        if(Objects.nonNull(obj.getUdateOpsPco())){
            pcoDto.setOpsPco(obj.getUdateOpsPco());
        }
        //bugid:11447 c14717 20230809 用于订单修改
        if(org.apache.commons.lang3.StringUtils.isNotBlank(obj.getCproductNo())){
            doDto.getList().get(0).setCproductNo(obj.getCproductNo());
        }
        OpsSendPcoAndDoMidDto param = new OpsSendPcoAndDoMidDto(doDto,pcoDto,obj.getForderNo(), obj.getXfName());
        //调用下发接口
        CommonResult<String> result =  opsCallWmsFeignApi.postWmsDoAndPcoNew(param);
        //bugid:12294 c14717 20231019
        List<OpsWmOrderTask> wmTaskPoList = new ArrayList<>();
        //传输状态；
        if(result.isSuccess()){
            //下发状态变更
            if (DoTypeEnum.JYCK.getType().equals(doDto.getOpsDo().getDoType())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_GOODS_READY,
                        doDto.getOpsDo().getOrderId(), Integer.valueOf(doDto.getOpsDo().getOrderItem()));
            }
            opsDoService.updateDOisWMSbyID(doDto.getOpsDo().getId());
            opsPcoService.updatetOpsPco(pcoDto.getOpsPco().getId(),1,UserDto.WMS);
            OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(doDto.getOpsDo().getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUCCESS,new CreateInfoDto("二次下发"), DateUtil.getNow(),1,"下发成功");
            OpsWmOrderTask wmTaskPco = wmOrderTaskService.createWmTaskPo(pcoDto.getOpsPco().getPcoId(), WmOrderTaskEnum.PCO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUCCESS,new CreateInfoDto("二次下发"), DateUtil.getNow(),1,"下发成功");
            wmTaskPoList.add(wmTaskdo);
            wmTaskPoList.add(wmTaskPco);
            //变更下发状态
            opsDoService.prepareOperation(doDto.getOpsDo().getDoId());
        }else {
            OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(doDto.getOpsDo().getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.FAIR,new CreateInfoDto("二次下发"), null,1,result.getMessage());
            OpsWmOrderTask wmTaskPco = wmOrderTaskService.createWmTaskPo(pcoDto.getOpsPco().getPcoId(), WmOrderTaskEnum.PCO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.FAIR,new CreateInfoDto("二次下发"), null,1,result.getMessage());
            wmTaskPoList.add(wmTaskdo);
            wmTaskPoList.add(wmTaskPco);
        }
        wmOrderTaskService.addBatchOpsWmOrderTask(wmTaskPoList);
        return result;
    }

}
