package com.sales.ops.serviceimpl.wmOrder;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.conf.OpsCommonConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.batchdao.AddNotifyShipmentBatchDao;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.db.extdao.NotifyShipmentPlanDao;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.db.extdao.OpsDoPostDao;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.replacement.*;
import com.sales.ops.dto.util.*;
import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.log.OpsInventoryLogService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.ModifyCustomerOrderService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.TransferEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.utils.WmOrderNoFactory;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.service.CommonServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author ：C14717
 * @version: $ v1
 * @description： 发货方式为分配发货的替换
 * @date ：Created in 2024/11/6 8:36
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DoPcoLogicCenterServiceImpl implements DoPcoLogicCenterService {

    @Autowired
    private BaseWMOrderService baseWMOrderService;
    @Autowired
    private OpsDoPostDao opsDoPostDao;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsInventoryLogService opsInventoryLogService;
    @Autowired
    private OpsPcoMapper opsPcoMapper;
    @Autowired
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;
    @Autowired
    private WmCommonService wmCommonService;
    @Autowired
    private WmDoService wmDoService;
    @Autowired
    private ModifyCustomerOrderService modifyCustomerOrderService;
    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;
    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
    @Autowired
    private BaseRoService baseRoService;
    @Autowired
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private OpsInventoryMoveMapper opsInventoryMoveMapper;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;

    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private ZDService zdService;
    @Autowired
    private OPSWarehouseDao opsWarehouseDao;
    @Autowired
    private OpsWarehouseService opsWarehouseService;

    @Autowired
    private OpsCommonConfig opsCommonConfig;
    @Autowired
    OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;

    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;

    @Autowired
    private NotifyShipmentPlanDao notifyShipmentPlanDao;

    @Autowired
    private AddNotifyShipmentBatchDao addNotifyShipmentBatchDao;

    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private TransferEventPublisher transferEventPublisher;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;

    /**
     * 验证是否是通知发货
     * @return
     */
    @Override
    public Boolean checkDlvEntireAssShipment(String orderNo){
        Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(orderNo);
        if(Objects.isNull(rcvMaster)){
            return false;
        }
        return CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire());
    }


    /**
     * 一、出库确认回传 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * 更新内容：结构优化-拆分pco适配
     * start
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public String doConfirm(WmDoConfirmDto param) throws OpsException {
        DoConfirmDto doConfirmDto = new DoConfirmDto();
        //1.数据验证
        checkDoConfirmParam(param, doConfirmDto);
        //初始化数据
        OpsDo opsDo = doConfirmDto.getOpsDo();
        OpsDoItem opsDoItem = doConfirmDto.getOpsDoItem();
        WmDoItemConfirmDto wmDoItemConfirmDto = doConfirmDto.getWmDoItemConfirmDto();
        //2.写入出库数据
        opsDoService.doComfirmCommon(false,wmDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmDoItemConfirmDto.getBarCode(),
                wmDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                wmDoItemConfirmDto.getExpressCode(), wmDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), param.getShipTime(), param.getExpressCodeChild());
        //3.组换单仅传回执;
        if(DoTypeEnum.ZHCKOW.getType().equals(opsDo.getDoType())){
            //bugid:12889 c14717 20240112 组换单创建ro
            opsRoService.createROForPorduceV2(opsDo.getOrderId(),opsDo.getWarehouseCode(),new UserDto("doConfirmApi"),true);
            return "成功";
        }
        //4.扣减库存、写入事件
        subInvDoConfirm(param,opsDo,opsDoItem,wmDoItemConfirmDto);
        //5.组换单创建ro
        if(DoTypeEnum.ZHCK.getType().equals(opsDo.getDoType())){
            //bugid:12889 c14717 20240112 组换单创建ro
            opsRoService.createROForPorduceV2(opsDo.getOrderId(),opsDo.getWarehouseCode(),new UserDto("doConfirmApi"),false);
        }
        return "成功";
    }
    //1.数据验证
    public void checkDoConfirmParam(WmDoConfirmDto param, DoConfirmDto doConfirmDto) throws OpsException {
        if (Objects.isNull(param)) {
            throw Exceptions.OpsException("参数解析失败--WmDoConfirmDto", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        if (Objects.isNull(param.getUserDto())) {
            UserDto userDto = new UserDto("doconfirm");
            param.setUserDto(userDto);
        }
        WmDoItemConfirmDto wmDoItemConfirmDto = param.getItems();//doItem
        if (wmDoItemConfirmDto == null) {
            throw Exceptions.OpsException("参数解析失败--wmDoItemConfirmDto或为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        String doId = param.getDeliveryOrderCode();
        if (StringUtils.isEmpty(doId)) {
            throw Exceptions.OpsException("出库指令为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }

        OpsDo opsDo = baseWMOrderService.getDo(doId);
        if(Objects.isNull(opsDo)){
            throw Exceptions.OpsException("无此发货单", DoConfirmErrorCodeEnum.NONEXISTENT_DO.getCode());
        }
        if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
            throw Exceptions.OpsException("此发货单已完成，不允许继续过账处理", DoConfirmErrorCodeEnum.COMPLETED.getCode());
        }
        OpsDoItem opsDoItem = baseWMOrderService.getDoItem(doId);
        opsDoItem.setOutQty(opsDoItem.getOutQty() + wmDoItemConfirmDto.getQty());

        if (opsDoItem.getQty() < opsDoItem.getOutQty()) {
            throw Exceptions.OpsException("此发货单数量大于应发数量，不允许继续", DoConfirmErrorCodeEnum.ABNORMAL_ISSUE_QUANTITY.getCode());
        }
        if (StringUtils.isBlank(param.getExpressCodeChild())) {
            throw Exceptions.OpsException("出库确认回传失败，子运单号为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        List<String> doPostList = opsDoPostDao.getOpsDoPostExpressChildNoByDoId(opsDo.getDoId());
        if (!CollectionUtils.isEmpty(doPostList)) {
            boolean repeat = doPostList.contains(param.getExpressCodeChild());
            if (repeat) {
                throw Exceptions.OpsException("出库确认回传失败，重复调用，子运单号：" + param.getExpressCodeChild(), DoConfirmErrorCodeEnum.REPEAT_CALL.getCode());
            }
        }
        if (opsDoItem.getQty().equals(opsDoItem.getOutQty())){//变更状态发货完成
            opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
        }else {
            opsDo.setDoStatus(DoStatusEnum.PART.getStatus());
        }
        opsDo.setCarried(wmDoItemConfirmDto.getLogisticsCode());//成承运商
        opsDo.setExpressCode(param.getExpressCodeChild());

        doConfirmDto.setOpsDo(opsDo);
        doConfirmDto.setOpsDoItem(opsDoItem);
        doConfirmDto.setWmDoItemConfirmDto(wmDoItemConfirmDto);
    }
    //4.扣减库存，写事件
    public void subInvDoConfirm(WmDoConfirmDto param, OpsDo opsDo, OpsDoItem opsDoItem, WmDoItemConfirmDto wmDoItemConfirmDto) throws OpsException {
        String doId = param.getDeliveryOrderCode();
        // 拆分型号do pco
        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()))){
            List<OpsPco> pcoList = baseWMOrderService.getPcos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
            if (CollectionUtils.isEmpty(pcoList)) {
                throw Exceptions.OpsException("无此加工单", DoConfirmErrorCodeEnum.NONEXISTENT_PCO.getCode());
            }
            OpsPco opsPco = pcoList.get(0);
            List<OpsPcoItemInventory> pcoItemInvs = baseWMOrderService.getPcoItemInv(opsPco.getPcoId(), null);
            List<OpsPcoItem> pcoItems = baseWMOrderService.getPcoItem(opsPco.getPcoId(), null);
            HashMap<String, Integer> pcoItemMap = new HashMap<String, Integer>();
            //计算子型号和整型号数量的倍数关系，计算出子型号出库数量
            for (OpsPcoItem pcoItem : pcoItems) {
                int pcoItemQty = pcoItem.getSubQty();
                int doQty = opsDoItem.getQty();
                int multipleQty = pcoItemQty / doQty;
                pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), multipleQty * wmDoItemConfirmDto.getQty());
            }
            if(CollectionUtils.isEmpty(pcoItemInvs)){
                opsDoService.saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                        param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                        param.getDeliveryOrderCode(), opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", pcoList.get(0).getPcoId(), "");
            }else {
                for (OpsPcoItemInventory item : pcoItemInvs){
                    //当前子型号库存关系和数量 备注：正常出库存拆分型号不拆数量，可能存在两条相同子型号的记录的场景为分纳入库导致；
                    if (Objects.isNull(item.getOutQty())) {//避免空指针
                        item.setOutQty(0);
                    }
                    if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                        continue;
                    }
                    int pcoSuboutQty = pcoItemMap.get(item.getPcoId() + "-" + item.getPcoItem());
                    if (pcoSuboutQty == 0) {
                        continue;
                    }
                    //子型号出库数量 - （该条库存使用数量 - 该条库存已经出库数量）
                    if (pcoSuboutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                        pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                        subPcoInvDoConfirm(param,opsDo,opsDoItem,item,item.getUseQty() - item.getOutQty());
                    }else {
                        if ((pcoSuboutQty - (item.getUseQty() - item.getOutQty())) < 0) {
                            pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), 0);
                        } else {
                            pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                        }
                        subPcoInvDoConfirm(param,opsDo,opsDoItem,item,pcoSuboutQty);
                    }
                }
            }
        // 非拆分型号do
        }else {
            List<OpsDoItemInventory> doItemInvs = baseWMOrderService.getDoItemInv(doId);
            if(CollectionUtils.isEmpty(doItemInvs)){
                //记录日志继续进行
                opsDoService.saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                        param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                        doId, opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", "", "");
            }else {
                //直接扣减库存  加工单整型号不扣减库存
                Integer wmOutQty = wmDoItemConfirmDto.getQty();
                for (OpsDoItemInventory item : doItemInvs){
                    //物流出库数量大于 当前item 继续循环
                    if (Objects.isNull(item.getOutQty())) {
                        item.setOutQty(0);
                    }
                    if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                        continue;
                    }
                    if (wmOutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                        wmOutQty = wmOutQty - (item.getUseQty() - item.getOutQty());
                        subDoInvDoConfirm(param,opsDo,opsDoItem,item,item.getUseQty() - item.getOutQty());
                    }else {//物流出库数量小于等于当前Item  结束循环
                        if (wmOutQty == 0) {
                            break;
                        }
                        subDoInvDoConfirm(param,opsDo,opsDoItem,item,wmOutQty);
                        break;
                    }
                }
            }
        }

        //写入状态变更事件表-调拨\交易出库完成
        if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmDoItemConfirmDto);
        } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            TmsMessageDto msg = new TmsMessageDto();
            msg.setCarrierid(opsDo.getCarried());
            msg.setExpressno(wmDoItemConfirmDto.getExpressCode());
            msg.setHandoverTime(param.getShipTime());
            msg.setQuantity(wmDoItemConfirmDto.getQty());
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, param);
        } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
            transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
        }
    }
    //扣减do库存
    public void subDoInvDoConfirm(WmDoConfirmDto param,OpsDo opsDo, OpsDoItem opsDoItem, OpsDoItemInventory item , int wmOutQty) {
        try {
            if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), param.getUserDto());
                opsDo.setDoId(param.getDeliveryOrderCode());
                opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), wmOutQty, new UserDto());
                item.setOutQty(item.getOutQty() + wmOutQty);
                item.setModifyTime(DateUtil.getNow());
                opsDoItemInventoryMapper.updateByPrimaryKey(item);
            } else {
                throw Exceptions.OpsException("在途库存只记录，不扣库存");
            }
        } catch (OpsException e) {
            //记录日志继续进行
            opsDoService.saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                    param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                    param.getDeliveryOrderCode(), opsDoItem.getModelno(), wmOutQty, param.getWarehouseCode(),
                    item.getInventoryId() + "", item.getInventoryTableType(), "", "");
        }
    }
    //扣减pco库存
    public void subPcoInvDoConfirm(WmDoConfirmDto param,OpsDo opsDo, OpsDoItem opsDoItem, OpsPcoItemInventory item , int pcoSubOutQty){
        try {
            if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), pcoSubOutQty, pcoSubOutQty,
                        item.getInventoryTableType(), param.getUserDto());
                opsDo.setDoId(param.getDeliveryOrderCode());
                opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), pcoSubOutQty, new UserDto());
                item.setOutQty(item.getOutQty() + pcoSubOutQty);
                item.setModifyTime(DateUtil.getNow());
                pcoItemInventoryMapper.updateByPrimaryKey(item);
            } else {
                throw Exceptions.OpsException("在途库存只记录，不扣库存");
            }
        } catch (OpsException e) {
            //记录日志继续进行
            opsDoService.saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                    param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                    param.getDeliveryOrderCode(), opsDoItem.getModelno(), pcoSubOutQty, param.getWarehouseCode(),
                    item.getInventoryId() + "", item.getInventoryTableType(), item.getPcoId(), item.getPcoItem() + "");
        }
    }


    /**
     *  二、更新物流交货期和客户期望交货期
     * @param orderId
     * @param orderItem
     * @param dlvDateParam
     * @param updateRcvFlag
     * @param userDto
     * @return
     * @throws OpsException
     * 1.通知发货 不修改货期
     * 2.货齐 追条重新计算
     * 3.其他仅计算当前
     */
    @Override
    public  Map<String, String> updateDoPcoWlday(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto)throws OpsException{

        if(updateRcvFlag){
            Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(orderId);
            Map<String, String> result = new HashMap<String, String>();
            //1.通知发货 不修改货期
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire())){
                result.put(orderId+"-"+orderItem, "通知发货不需修改，仅按计划日期发货");
                //通知发货不修改货期
                return result;
            }
            // 2.货齐 追条重新计算
            if(Objects.isNull(dlvDateParam)) {
                if(CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(rcvMaster.getDlvEntire())
                        || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(rcvMaster.getDlvEntire())){
                    List<Rcvdetail> rcvList = baseWMOrderService.findRcvDetail(orderId);
                    for(Rcvdetail rcv : rcvList) {
                        Map<String, String> stringStringMap = modifyDeliveryDay(rcv.getRorderNo(), rcv.getRorderItem().toString(), dlvDateParam, updateRcvFlag, userDto);
                        result.putAll(stringStringMap);
                    }
                    return result;
                }
            }
        }
        // 3.其他仅计算当前
        return modifyDeliveryDay(orderId,orderItem,dlvDateParam,updateRcvFlag,userDto);
    }

    /**
     * 二、更新物流交货期和客户期望交货期 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * 更新内容：方法名（原方法名：updateDoPcoWlday） 结构 适配拆分db，拆分pco
     * @param orderId
     * @param orderItem
     * @param dlvDateParam 客户交货期  如果不为空是订单修改 物流交货期
     * @throws OpsException
     * 1.获取 十位交易单集合
     * 2.计算 物流货期
     * 3.修改 pco
     * 4.修改 do
     * 5.修改 db
     * 6.修改 rcv
     */
    @Override
    public Map<String, String> modifyDeliveryDay(String orderId, String orderItem, Date dlvDateParam,
                                                 Boolean updateRcvFlag, UserDto userDto) throws OpsException{
        // 返回结果
        Map<String, String> result = new HashMap<String, String>();
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem);
        List<OpsDo> jyDos = baseWMOrderService.getDos(dos,DoTypeEnum.JYCK);
        //非订单分配和修改货期 计算倒挂
        boolean wmsDateIsSystemTime = false;
        //获取物流货期集合
        HashSet<Date> rcvWmsDlvDateSet = new HashSet<Date>();
        if(CollectionUtils.isNotEmpty(jyDos)){
            for(OpsDo jyckDo : jyDos){
                UpdateForOrderDto dto = new UpdateForOrderDto();
                // 2.计算 物流货期
                wmsDateIsSystemTime = calcDeliveryTime(dto, dlvDateParam, jyckDo);
                if(DoSourceEnum.ASSModelNo.getType().equals(jyckDo.getDoSource())){
                    // 3. 修改 pco
                    modifyPCODeliveryTime(dto, jyckDo, rcvWmsDlvDateSet, result);
                }else {
                    // 4. 修改 do
                    modifyDoDeliveryTime(dto, jyckDo, rcvWmsDlvDateSet, result);
                }
                // 5.修改 db 一交易单对应的所有调拨单
                modifyDBDeliveryTime(dto, jyckDo, dos , wmsDateIsSystemTime, result);
            }
        }
        // 6.修改rcv
        if(updateRcvFlag){
            if(!rcvWmsDlvDateSet.isEmpty()){
                modifyRcvDeliveryTime(Collections.max(rcvWmsDlvDateSet), dlvDateParam, orderId, orderItem, userDto);
            }
        }
        result.put("maxWmDlvDate", DateUtil.dateToString(Collections.max(rcvWmsDlvDateSet)));
        return result;
    }

    // 2.计算 物流货期
    public boolean calcDeliveryTime(UpdateForOrderDto dto, Date dlvDateParam, OpsDo jyckDo) throws OpsException{
        boolean wmsDateIsSystemTime = false;
        //是否更新客户交货期
        if(Objects.nonNull(dlvDateParam)){
            dto.setDlvDate(dlvDateParam);//客户交货期
        }else {
            dto.setDlvDate(jyckDo.getHopeDate());
        }
        //2.计算交易单的物流期望交货期
        CommonResult<Integer> warehouseToSalesDept = wmCommonService.getWarehouseSalesbranchConfigByCode(jyckDo.getDeptNo(),jyckDo.getGatherWarehouseCode());
        if(warehouseToSalesDept.isSuccess()){
            /**
             * 1 客户订单物流发货日=客户交货期-国内运输时间
             * 2 客户订单物流发货日<系统日期时，客户订单物流发货日=系统日期
             * 3 客户订单物流发货日>=系统日期时,客户订单物流发货日=1中计算的客户订单物流发货日
             */
            Date countDate = DateUtil.addDay(dto.getDlvDate(),-warehouseToSalesDept.getData());
            Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
            if(diffDay < 0){
                dto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                wmsDateIsSystemTime = true;//无需计算调拨单的物流交货期
            }else {
                dto.setWmsDlvDate(countDate);//物流交货期
            }
        }else {
            throw Exceptions.OpsException(jyckDo.getDoId(), warehouseToSalesDept.getMessage());
        }
        return wmsDateIsSystemTime;
    }
    // 3. 修改 pco
    public void modifyPCODeliveryTime(UpdateForOrderDto dto, OpsDo jyckDo, HashSet<Date> rcvWmsDlvDateSet, Map<String, String> result) throws OpsException{
        //获取加工单
        List<OpsPco> pcos = baseWMOrderService.getPcos(jyckDo.getOrderId(), jyckDo.getOrderItem(), jyckDo.getNum());
        if(CollectionUtils.isEmpty(pcos)){
            throw Exceptions.OpsException(jyckDo.getDoId(), "数据异常缺少pco指令");
        }
        OpsPco opsPco = pcos.get(0);
        //是否下发wms
        if (1 == jyckDo.getIsWms()) {//拆分型号
            //调用WMS变更接口
            if (opsPco.getIsWms() != 1) {
                throw Exceptions.OpsException(jyckDo.getDoId(), "数据异常pco和do下发不一致");
            }
            OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
            pcoAndDoMidIDDto.setDoId(jyckDo.getDoId());
            pcoAndDoMidIDDto.setPcoId(opsPco.getPcoId());
            pcoAndDoMidIDDto.setUpdateOpsDo(opsDoService.fillInUpdateInfo(jyckDo, dto));
            pcoAndDoMidIDDto.setUdateOpsPco(opsDoService.fillInUpdatePcoInfo(opsPco, dto));
            CommonResult<String> resWms = wmDoService.postWmsDoAndPcoNew(pcoAndDoMidIDDto);
            if (resWms.isSuccess()) {
                opsDoService.updateDoDeliveryDay(jyckDo, dto);
                opsDoService.updatePcoDeliveryDay(opsPco, dto);
                //获取交易出库单的物流发货期
                rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                result.put(jyckDo.getDoId(), "1");
            } else {
                result.put(jyckDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
            }
        } else {
            opsDoService.updatePcoDeliveryDay(opsPco, dto);
            opsDoService.updateDoDeliveryDay(jyckDo, dto);
            //获取交易出库单的物流发货期
            rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
            result.put(jyckDo.getDoId(), "1");
        }
    }
    // 4. 修改 do
    public void modifyDoDeliveryTime(UpdateForOrderDto dto, OpsDo jyckDo, HashSet<Date> rcvWmsDlvDateSet, Map<String, String> result) throws OpsException{
        //是否下发wms
        if (1 == jyckDo.getIsWms()) {//拆分型号
            //调用WMS变更接口
            //非拆分型号
            OpsDoAndItemDto doDto = opsDoService.findDoToWms(jyckDo.getDoId());
            doDto.setOpsDo(opsDoService.fillInUpdateInfo(jyckDo, dto));
            CommonResult<String> resWms = wmDoService.postWmsDoNew(doDto);
            if (resWms.isSuccess()) {
                opsDoService.updateDoDeliveryDay(jyckDo, dto);
                //获取交易出库单的物流发货期
                rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                result.put(jyckDo.getDoId(), "1");
            } else {
                result.put(jyckDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
            }
        } else {
            opsDoService.updateDoDeliveryDay(jyckDo, dto);
            //获取交易出库单的物流发货期
            rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
            result.put(jyckDo.getDoId(), "1");
        }
    }
    // 5.修改 db 一交易单对应的所有调拨单
    public void modifyDBDeliveryTime(UpdateForOrderDto dto, OpsDo jyckDo, List<OpsDo> dos, boolean wmsDateIsSystemTime, Map<String, String> result) throws OpsException{
        // 一个交易单，对应的所有调拨单
        List<OpsDo> DBDos = baseWMOrderService.getDos(dos,DoTypeEnum.DBCK, jyckDo.getNum());
        if(CollectionUtils.isNotEmpty(DBDos)){
            for(OpsDo dbDo : DBDos){
                UpdateForOrderDto dbDto = new UpdateForOrderDto();
                //5.计算非拆分调拨单物流期望交货期 bugid:17714 c14717 20250910
                if(wmsDateIsSystemTime || CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(dbDo.getDlvEntire())){
                    dbDto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                }else {
                    CommonResult<Integer> warehouseDelivery =  wmCommonService.getWarehouseDeliveryDayByCode(dbDo.getWarehouseCode(),dbDo.getGatherWarehouseCode());
                    if(warehouseDelivery.isSuccess()){
                        /**
                         * 当客户订单存在调拨单时
                         * 1. 调拨单物流发货日 = 客户订单物流发货日 - 仓间调拨时间
                         * 2  调拨单物流发货日<系统日期时，调拨单物流发货日=系统日期
                         * 3  调拨单物流发货日>=系统日期时, 调拨单物流发货日=1中计算的调拨单物流发货日
                         */
                        Date countDate = DateUtil.addDay(dto.getWmsDlvDate(),-warehouseDelivery.getData());
                        Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
                        if(diffDay < 0){
                            dbDto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                        }else {
                            dbDto.setWmsDlvDate(countDate);//物流交货期
                        }
                    }else {
                        throw Exceptions.OpsException(jyckDo.getDoId(), warehouseDelivery.getMessage());
                    }
                }
                dbDto.setDlvDate(dto.getDlvDate());
                //6.非拆分型号需要修改调拨do以及wms; 是否下发wms
                if (1 == dbDo.getIsWms()) {//拆分型号
                    //调用WMS变更接口
                    OpsDoAndItemDto doDto = opsDoService.findDoToWms(dbDo.getDoId());
                    doDto.setOpsDo(opsDoService.fillInUpdateInfo(dbDo, dbDto));
                    CommonResult<String> resWms = wmDoService.postWmsDoNew(doDto);
                    if (resWms.isSuccess()) {
                        opsDoService.updateDoDeliveryDay(dbDo, dbDto);
                        result.put(dbDo.getDoId(), "1");
                    } else {
                        result.put(dbDo.getDoId(), "调拨单物流已作业");
                    }
                } else {
                    opsDoService.updateDoDeliveryDay(dbDo, dbDto);
                    result.put(dbDo.getDoId(), "1");
                }
            }
        }
    }
    // 6.修改rcv
    public void modifyRcvDeliveryTime(Date maxDate,Date dlvDate,
                                      String orderId, String orderItem,UserDto userDto
                                      ) throws OpsException{
        //变更订单表
        Rcvdetail detail = new Rcvdetail();
        detail.setRorderNo(orderId);
        detail.setRorderItem(Integer.parseInt(orderItem));
        if(Objects.nonNull(dlvDate)){
            detail.setDlvDate(dlvDate);
        }
        //获取最大物流交货期
        detail.setWmsDlvDate(maxDate);
        detail.setUpdateTime(DateUtil.getNow());
        modifyCustomerOrderService.modifyRcvDetail(detail, userDto);
    }


    /**
     * 三、订单修改 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * 修改结构 原方法名：UpdateDoForOrder
     * @param dto
     * @return
     * @throws OpsException
     * 1.修改子项特发
     * 2.修改其他内容
     */
    @Override
    public Map<String, String> modifyDoPcoForOrder(UpdateForOrderDto dto) throws OpsException{
        String orderItem = null;
        if (Objects.nonNull(dto.getOrderItem())) {
            orderItem = dto.getOrderItem().toString();
        }
        List<OpsDo> doList = baseWMOrderService.getDos(dto.getOrderId(), orderItem);
        //查询指令
        if (CollectionUtils.isEmpty(doList)) {
            throw Exceptions.OpsException("物流指令变更失败,改订单没有查到对应物流指令：" + dto.getOrderId() + "-" + dto.getOrderItem());
        }
        Map<String, String> result = new HashMap<String, String>();
        //修改客户交货期走单独的方法
        dto.setDlvDate(null);
        dto.setWmsDlvDate(null);
        if (dto.getAssModelChangeDo()) {
            //1.修改子项特发
            modifySubItemSpecial(doList, result, dto);
        } else {
            List<OpsDo> jydos = baseWMOrderService.getDos(doList, DoTypeEnum.JYCK);
            for (OpsDo opsDo : jydos) {
                //2.修改其他内容
                modifyOther(opsDo, result, dto);
            }
        }
        return result;
    }

    /**
     * 1.修改子项特发
     * @param doList
     * @param result
     * @param dto
     * @throws OpsException
     * 1.验证数据
     * 2.已下发wms
     *   2.1 组装wms删除报文pco和do
     *   2.2 wms删单接口
     *   2.3 生成新的物流单据
     *      2.3.1 修改调拨单编号
     *      2.3.2.创建新单据
     *      2.3.3.拆分新单据
     *          2.3.3.1.删调拨单
     *          2.3.3.2.拆N
     *          2.3.3.3.拆W
     *          2.3.3.4.拆X(虚拟库存) doItem的数据大于doItemInv的数据 且doItemInv的数量大于0 继续拆分指令
     *          2.3.3.5.更新doItem数量 持久化do
     *          2.3.3.6.传事件，重算货期
     *   2.4 删除指令
     * 3.没下发wms
     *   3.1 生成新的物流单据
     *      3.3.1 修改调拨单编号
     *            3.3.2.创建新单据
     *            3.3.3.拆分新单据
     *                3.3.3.1.删调拨单
     *                3.3.3.2.拆N
     *                3.3.3.3.拆W
     *                3.3.3.4.拆X(虚拟库存) doItem的数据大于doItemInv的数据 且doItemInv的数量大于0 继续拆分指令
     *                3.3.3.5.更新doItem数量 持久化do
     *                3.3.3.6.传事件，重算货期
     *
     *   3.2 删除指令
     */
    public void modifySubItemSpecial(List<OpsDo> doList, Map<String, String> result, UpdateForOrderDto dto) throws OpsException{
        //1.验证数据
        List<OpsDo> jydos = baseWMOrderService.getDos(doList, DoTypeEnum.JYCK);
        List<OpsDo> dbdos = baseWMOrderService.getDos(doList, DoTypeEnum.DBCK);
        if(jydos.size() == 0){
            throw Exceptions.OpsException("物流指令变更失败,改订单没有查到对应物流指令：" + dto.getOrderId() + "-" + dto.getOrderItem());
        }
        OpsDo opsDo = jydos.get(0);
        if(jydos.size() > 1){
            result.put(opsDo.getDoId(), "已拆分单据无法修改子项特发");
            return ;
        }
        List<OpsDo> shippedDos = baseWMOrderService.getShippedDos(jydos);
        if (shippedDos.size() > 0){
            result.put(opsDo.getDoId(), "物流已经发货不能修改");
            return ;
        }
        if(!DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())){
            result.put(opsDo.getDoId(), "非型号拆分单无法修改子项特发");
            return ;
        }
        //通知发货存在计划不修改子项特发
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            int i = notifyShipmentPlanDao.countNotifyShipmentPlanList(opsDo.getOrderId(), opsDo.getOrderItem());
            if( i>0 ){
                result.put(opsDo.getDoId(), "存在通知发货计划，无法修改子项特发");
                return ;
            }
        }

        String oldDoId = opsDo.getDoId();
        OpsPcoAndItemAndItemInventoryDto opsPcoAndItemDto = baseWMOrderService.getPcoDto(opsDo.getOrderId(),opsDo.getOrderItem(),opsDo.getNum());
        if(Objects.isNull(opsPcoAndItemDto)){
            throw Exceptions.OpsException("物流指令变更失败,改订单没有查到对应物流指令：" + dto.getOrderId() + "-" + dto.getOrderItem());
        }
        if(StringUtils.isNotEmpty(opsPcoAndItemDto.getOpsPco().getRoId())){
            result.put(opsDo.getDoId(), "此单已越库，无法变更子项拆分特发");
            return ;
        }

        String oldPcoId = opsPcoAndItemDto.getOpsPco().getPcoId();
        OpsPco opsPco = opsPcoAndItemDto.getOpsPco();
        //2.已下发下发wms
        if (1 == opsDo.getIsWms()) {//已经下发wms 不存在调拨 看是否能删除
            //2.1 组装wms删除报文pco和do
            CommonResult<List<JSONObject>> wmJYCKResult = cancelWMSApi(opsDo, opsPco, null, dto.getRemark1());
            if (wmJYCKResult.isSuccess()) {
                //2.3生成新的物流单据
                createNewWmOrder(opsPcoAndItemDto, dbdos, opsDo, dto);
                //2.4删除指令
                CancelForOrderDto inputDto = new CancelForOrderDto(opsDo.getOrderId(),opsDo.getOrderItem(),"1",dto.getUserDto(),dto.getRemark1());
                Long cancelId = opsDoService.insertOrderCancel(inputDto);
                opsDoService.delOpsWmOrder(cancelId, oldPcoId, oldDoId, dto);
                //返回结果
                result.put(opsDo.getDoId(), "1");
            } else {//物流已经作业删除失败
                result.put(opsDo.getDoId(), "物流已经作业不能修改");
            }
        } else {//没有下发wms 说明货不齐 判断是否有调拨 是否是调拨的在途
            // 3.没发现wms
            //3.1生成新的物流单据
            createNewWmOrder(opsPcoAndItemDto, dbdos, opsDo, dto);
            //3.2删除指令
            CancelForOrderDto inputDto = new CancelForOrderDto(opsDo.getOrderId(),opsDo.getOrderItem(),"1",dto.getUserDto(),dto.getRemark1());;
            long cancelId = opsDoService.insertOrderCancel(inputDto);
            opsDoService.delOpsWmOrder(cancelId, oldPcoId, oldDoId, dto);
            //返回结果
            result.put(opsDo.getDoId(), "1");
        }
    }


    /**
     * 生成新的物流单据
     * 1.修改调拨单编号
     * 2.创建新单据
     * 3.拆分新单据 拆分型号子项特发增加拆分do
     */
    public void createNewWmOrder(OpsPcoAndItemAndItemInventoryDto opsPcoAddItemDto,List<OpsDo> dbList, OpsDo opsDo, UpdateForOrderDto dto) throws OpsException{
        //1.修改调拨单编号  改变调拨单的num号 ass_num号
        for(OpsDo pcoDbckDo : dbList){
            List<OpsRo> opsRos = baseRoService.findDBRoByDBDo(pcoDbckDo);
            if(CollectionUtils.isNotEmpty(opsRos)){
                //修改do num
                pcoDbckDo.setNum(pcoDbckDo.getAssNum());
                pcoDbckDo.setAssNum(0);
                pcoDbckDo.setDoSource(DoSourceEnum.ASSQTY.getType());
                opsDoService.updateDoByDo(pcoDbckDo);
                //修改ro num
                opsRos.get(0).setNum(pcoDbckDo.getAssNum());
                opsRos.get(0).setAssNum(0);
                baseRoService.updateRoById(opsRos.get(0));
            }
        }

        //2.创建新的单据；
        OpsDoItem doItem = baseWMOrderService.getDoItem(opsDo.getDoId());
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (OpsPcoItem opsPcoItem : opsPcoAddItemDto.getItemList()) {
            String doid = String.format(OrderIDFormatEnum.DO_PCO_CHANGE_DO_FORMAT.getFormat(), opsDo.getOrderId(),
                    String.format("%03d", Integer.parseInt(opsDo.getOrderItem())),
                    String.format("%03d", opsPcoItem.getPcoItem()), String.format("%03d", opsDo.getAssNum()));
            //修改采购wmTag
            if (DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())) {
                OpsRequestpurchase request = new OpsRequestpurchase();
                request.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
                request.setUpdatetime(DateUtil.getNow());
                OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
                example.createCriteria().andOrdernoEqualTo(opsDo.getOrderId()).andItemnoEqualTo(Integer.parseInt(opsDo.getOrderItem())).andSplititemnoEqualTo(opsPcoItem.getPcoItem());
                opsRequestpurchaseMapper.updateByExampleSelective(request, example);
            }

            OpsDo opsUpdateDo = opsDoService.fillInUpdateInfo(opsDo, dto);
            opsUpdateDo.setId(null);
            opsUpdateDo.setDoId(doid);
            opsUpdateDo.setWaitType(opsPcoItem.getWaitType());
            opsUpdateDo.setDoSource(DoSourceEnum.ASSQTY.getType());//num 不为0默认按照拆分数量搞
            opsUpdateDo.setNum(opsPcoItem.getPcoItem());//num 不为0默认按照拆分数量搞 不然入库找不到对应的do
            opsUpdateDo.setAssNum(0);
            //创建doitem
            OpsDoItem opsDoItem = new OpsDoItem();
            // bugid:20854 C14717 20260509
            opsDoItem.setCproductNo(doItem.getCproductNo());
            opsDoItem.setContractNo(doItem.getContractNo());
            opsDoItem.setProductName(doItem.getProductName());

            opsDoItem.setDoId(doid);
            opsDoItem.setOutQty(0);
            opsDoItem.setVersion(0);
            opsDoItem.setQty(opsPcoItem.getSubQty());
            opsDoItem.setDoItem(1);
            opsDoItem.setModelno(opsPcoItem.getSubModelno());
            opsDoItem.setDelflag(0);
            opsDoItem.setCreator(dto.getUserDto().getUserName());
            opsDoItem.setCreTime(DateUtil.getNow());
            List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoAddItemDto.getItemInventoryList();
            List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
            for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList) {
                if (opsPcoItemInventory.getPcoId().equals(opsPcoItem.getPcoId()) && opsPcoItemInventory.getPcoItem().equals(opsPcoItem.getPcoItem())) {
                    //创建doItemInventory
                    OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
                    opsDoItemInventory.setDoId(doid);
                    opsDoItemInventory.setDoItem(1);
                    opsDoItemInventory.setInventoryId(opsPcoItemInventory.getInventoryId());
                    opsDoItemInventory.setInventoryTableType(opsPcoItemInventory.getInventoryTableType());
                    opsDoItemInventory.setSrcInventoryId(opsPcoItemInventory.getSrcInventoryId());
                    opsDoItemInventory.setSrcInventoryTableType(opsPcoItemInventory.getSrcInventoryTableType());
                    opsDoItemInventory.setUseQty(opsPcoItemInventory.getUseQty());
                    opsDoItemInventory.setVersion(0L);
                    opsDoItemInventory.setDelflag(0);
                    opsDoItemInventory.setCreTime(DateUtil.getNow());
                    opsDoItemInventory.setCreator(dto.getUserDto().getUserName());
                    opsDoItemInventory.setSortnum(opsPcoItemInventory.getSortnum());
                    doItemInventoryList.add(opsDoItemInventory);
                }
            }
            //bugid:9965 20230315 c14717
            //越库和上预约都带上此字段
            opsUpdateDo.setRelocation("");
            if (Objects.nonNull(opsPcoItem.getIsCross()) && opsPcoItem.getIsCross()) {
                opsUpdateDo.setRelocation(opsPcoItem.getPcoId());
            }
            opsDoService.createDo(opsUpdateDo, opsDoItem, doItemInventoryList, dto.getUserDto());
            //生成Ordertask
            if(!CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
                OpsWmOrderTask wmTaskPo = wmOrderTaskService.createWmTaskPo(opsUpdateDo.getDoId(), WmOrderTaskEnum.DO,
                        WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto(dto.getUserDto().getUserName()), DateUtil.getNow(),0,"");
                taskList.add(wmTaskPo);
            }
        }
        //拆分型号子项特发增加拆分do
        if(CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            List<OpsDo> doList = baseWMOrderService.getDos(dto.getOrderId(), dto.getOrderItem().toString());
            List<OpsDo> jydos = baseWMOrderService.getDos(doList, DoTypeEnum.JYCK);
            if(CollectionUtils.isNotEmpty(jydos)){
                for(OpsDo newDo : jydos){
                    createAssDo(newDo, 0, dto.getUserDto(), DoTypeEnum.JYCK, CKTYPEEnum.ITEM_UNLIMIT.getCode());
                }
            }
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
    }

    /**
     * 拆分新单据
     * 1. 删调拨单,更换关联，修改仓库号
     * 2.拆N
     * 3.拆W
     * 4.拆X(虚拟库存) doItem的数据大于doItemInv的数据 且doItemInv的数量大于0 继续拆分指令
     * 5.更新doItem数量 持久化do
     * 6.传事件，重算货期
     */
    @Override
    public void createAssDo(OpsDo oldOpsDo, int qty, UserDto userDto, DoTypeEnum doTypeEnum, String dlvEntire) throws OpsException {
        if(Objects.isNull(oldOpsDo)){
            return;
        }
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource())) {
            //1.删调拨单,更换关联，修改仓库号
            OpsDo delDBDo = null;
            if(DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())){
                delDBDo = updateDoDlvEntryDelDB(oldOpsDo);
            }

            List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(oldOpsDo.getDoId());
            for (OpsDoItemInventory obj : doItemInventorieList) {
                if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                    qty += obj.getUseQty();
                }
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(oldOpsDo.getDoId());
            if (opsDoItem.getQty().equals(qty)) {
                if (Objects.nonNull(delDBDo)) {//添加上架类型
                    if (StringUtils.isNotEmpty(delDBDo.getRoId())) {
                        opsDoService.updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                    }
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                    //bugid: 10345 20230410 c14717 重新计算物流交货期
                    updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
                }
                return ;
            }

            OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(oldOpsDo.getOrderId(), oldOpsDo.getOrderItem(), doTypeEnum);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(dlvEntire)){
                oldOpsDo.setDlvEntire(dlvEntire);//随发分批
            }
            WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
            //2.拆N opsDoItem.qty > do_item_inventory.qty N的情况
            if (qty != 0) {
                opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
                String formatDoId = "";
                if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                    formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
                }else {
                    formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
                }
                // 2.1创建新do
                CreDoByInventoryDto creDoByInventoryDto = createAssQtyDo(formatDoId,opsDoMaxOne.getNum(),opsDoMaxOne.getAssNum(),
                        oldOpsDo, opsDoItem, qty, userDto, doTypeEnum,DoWaitTypeEnum.OK.getType(),DoSourceEnum.ASSQTY);
                // 2.2绑定上架类型
                if (Objects.nonNull(delDBDo)) {//添加上架类型
                    if (StringUtils.isNotEmpty(delDBDo.getRoId())) {
                        creDoByInventoryDto.getOpsDo().setRoId(delDBDo.getRoId());
                        creDoByInventoryDto.getOpsDo().setRoCrossType(delDBDo.getRoCrossType());
                        creDoByInventoryDto.getOpsDo().setRelocation(delDBDo.getDoId());
                    }
                } else if (StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                    creDoByInventoryDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                    creDoByInventoryDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                    opsDoService.updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                    creDoByInventoryDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
                } else if(StringUtils.isNotEmpty(oldOpsDo.getRelocation())){
                    opsDoService.updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                    creDoByInventoryDto.getOpsDo().setRelocation(oldOpsDo.getRelocation());
                }
                wmOrderByInventoryDto.getDolist().add(creDoByInventoryDto);
                //n的绑定一条
                for (OpsDoItemInventory obj : doItemInventorieList) {
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                        opsDoService.updateOpsDoItemInventoryDoId(obj.getId(), obj.getVersion(), creDoByInventoryDto.getOpsDo().getDoId(), userDto);
                    }
                }
            }

            // 3. 拆W
            List<OpsDoItemInventory> oldDoItemInventorieList = baseDoService.getDoItemInventoryByDoIdOrderByInventoryId(oldOpsDo.getDoId());
            if (oldDoItemInventorieList.size() > 1) {
                int addMaxNum = opsDoMaxOne.getNum();
                for (OpsDoItemInventory obj : oldDoItemInventorieList) {
                    //判断是否下预约
                    boolean relocationFlag = false;
                    if(InventoryTableTypeEnum.TRANS.getType().equals(obj.getInventoryTableType())){
                        OpsInventoryMove inventoryMove = opsInventoryMoveMapper.selectByPrimaryKey(obj.getInventoryId());
                        if(Objects.nonNull(inventoryMove)){
                            //查询move opt_code=5 需要下预约
                            if(InventoryStatusEnum.W.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())
                                    && OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())){
                                relocationFlag = true;
                            }
                        }
                    }
                    //旧do
                    if (opsDoMaxOne.getNum() == addMaxNum) {
                        if(relocationFlag){
                            if (Objects.nonNull(delDBDo)) {//添加上架类型
                                if (StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    opsDoService.updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                                }
                            }
                        }
                    }else {//产生新do
                        qty += obj.getUseQty();
                        String formatDoId = "";
                        if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                            formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
                        }else {
                            formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
                        }
                        // 3.1创建新do
                        CreDoByInventoryDto newDto = createAssQtyDo(formatDoId,opsDoMaxOne.getNum(),opsDoMaxOne.getAssNum(),
                                oldOpsDo, opsDoItem, obj.getUseQty(), userDto, doTypeEnum,DoWaitTypeEnum.OK.getType(),DoSourceEnum.ASSQTY);
                        if (relocationFlag) {
                            // 3.2添加上架类型
                            if (Objects.nonNull(delDBDo)) {
                                if (StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    newDto.getOpsDo().setRoId(delDBDo.getRoId());
                                    newDto.getOpsDo().setRoCrossType(delDBDo.getRoCrossType());
                                    //bugid:9965 c14717 20230315
                                    newDto.getOpsDo().setRelocation(delDBDo.getDoId());
                                }
                            } else if (StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                                newDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                                newDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                                opsDoService.updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                                //bugid:9965 c14717 20230315
                                newDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
                            }else if(StringUtils.isNotEmpty(oldOpsDo.getRelocation())){
                                opsDoService.updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                                newDto.getOpsDo().setRelocation(oldOpsDo.getRelocation());
                            }
                        }
                        wmOrderByInventoryDto.getDolist().add(newDto);
                        opsDoService.updateOpsDoItemInventoryDoId(obj.getId(), obj.getVersion(), newDto.getOpsDo().getDoId(), userDto);
                    }
                    opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
                }
            }else if(oldDoItemInventorieList.size() == 1){//调拨单上预约
                if(InventoryTableTypeEnum.TRANS.getType().equals(oldDoItemInventorieList.get(0).getInventoryTableType())){
                    OpsInventoryMove inventoryMove = opsInventoryMoveMapper.selectByPrimaryKey(oldDoItemInventorieList.get(0).getInventoryId());
                    if(Objects.nonNull(inventoryMove)){
                        //查询move opt_code=5 需要下预约
                        if(InventoryStatusEnum.W.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())
                                && OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())){
                            if (Objects.nonNull(delDBDo)) {//添加上架类型
                                if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    opsDoService.updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                                }
                            }
                        }
                    }
                }
            }
            //4.拆X(虚拟库存) 需虚拟一条原来的调拨在途库存然后拆分 oldDOItemInvSumQty>0 && opsDoItemQty>oldDOItemInvSumQty 拆分指令 bugid:11568
            int assQty =  opsDoService.assDoByQtySumItemInv(opsDoMaxOne.getNum() + 1,opsDoItem.getDoId(),opsDoItem.getQty() - qty,
                    opsDoItem,userDto,doTypeEnum,wmOrderByInventoryDto);
            qty = qty + assQty;
            //5.更新doItem数量 持久化do
            if (qty != 0) {
                //如果是all 需要更改 assQty
                if (DoSourceEnum.ALL.getType().equals(oldOpsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(oldOpsDo.getDoSource())) {
                    opsDoService.updateDoWaitTypeAndWareHouseCodeForId(oldOpsDo.getId(), DoSourceEnum.ASSQTY.getType(), null,
                            null, null, oldOpsDo.getVersion(), userDto.getUserName(), 0, null);
                }
                //更改旧的doItem qty
                opsDoService.updateDoItemQtyByDoItemId(opsDoItem.getId(),  opsDoItem.getQty() - qty,opsDoItem.getVersion());
                ////持久化do
                opsDoService.CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,null);
                //记录事件
                if (DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())) {
                    for (CreDoByInventoryDto doDto : wmOrderByInventoryDto.getDolist()) {
                        List<OpsDoItemInventory> doItemInventoryByDoId = baseDoService.getDoItemInventoryByDoId(doDto.getOpsDo().getDoId());
                        doDto.setItemInventorys(doItemInventoryByDoId);
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_DELIVERY_TYPE, oldOpsDo, doDto);
                    }
                }
            }
            //6.传事件，重算货期
            if (Objects.nonNull(delDBDo)){
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                // 重算交货期
                updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
            }
        }
        return ;
    }

    /**
     * todo do交易对应多个db 如何下预约 需要拆分
     * 删调拨，修改仓
     * @param oldOpsDo
     * @return
     * @throws OpsException
     * 1.组装wms删单报文
     * 2.调用wms删单接口
     * 3.变更仓库号
     * 4.删db指令
     *
     */
    public OpsDo updateDoDlvEntryDelDB(OpsDo oldOpsDo) throws OpsException {
        //非拆分型号，未下发do,等待类型为等待调拨
        if (DoWaitTypeEnum.WaitDB.getType().equals(oldOpsDo.getWaitType())) {
            List<OpsDo> dos = baseWMOrderService.getDos(oldOpsDo.getOrderId(), oldOpsDo.getOrderItem(), oldOpsDo.getNum(), oldOpsDo.getAssNum());
            List<OpsDo> DBdos = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK);
            if (CollectionUtils.isEmpty(DBdos)) {
                return null;
            }
            List<OpsDo> shippedDos = baseWMOrderService.getShippedDos(DBdos);
            if (shippedDos.size() > 0) {
                return null;
            }

            //1.组装删单报文
            CommonResult<List<JSONObject>> wmDBCKResult = cancelWMSApi(null, null,DBdos,"随发分批取消db");
            if (wmDBCKResult.isSuccess()) {
                OpsDo db = DBdos.get(0);
                //3.变更仓库号
                opsDoService.updateDoWaitTypeAndWareHouseCodeForId(oldOpsDo.getId(), null, db.getWarehouseCode(), db.getWarehouseCode(),
                        DoWaitTypeEnum.OK.getType(), oldOpsDo.getVersion(), "admin", 0, null);
                //变更集约仓事件
                oldOpsDo.setWarehouseCode(db.getWarehouseCode());
                oldOpsDo.setGatherWarehouseCode(db.getWarehouseCode());
                for(OpsDo dbDo : DBdos){
                    //4.删除db单
                    CancelForOrderDto inputDto = new CancelForOrderDto(dbDo.getOrderId(), dbDo.getOrderItem(), "1", UserDto.ADMIN, "随发分批变更");
                    long cancelId = opsDoService.insertOrderCancel(inputDto);
                    //删除调拨do 关联doItemInventory 把调拨单的do_item_inventory更新为交易出库的doid
                    opsDoService.delDoChangeItemInventory(dbDo, cancelId, DoTypeEnum.DBCK, null, oldOpsDo);
                    //删除调拨ro
                    opsDoService.delDBRo(dbDo, cancelId, RoTypeEnum.DBRK.getType());
                }

                return db;
            }
        }
        return null;
    }
    /**
     * 2.修改其他内容
     * @param opsDo
     * @param result
     * @param dto
     * @throws OpsException
     * 1.已下发wms
     *  1.1型号拆分
     *      1.1.1 收集变更指令
     *      1.1.2 调用wms接口
     *      1.1.3 持久化
     *  1.2非型号拆分
     *      1.2.1 收集指令变更指令
     *      1.2.2 调用wms接口
     *      1.2.3 持久化接口
     * 2.没下发wms
     *  2.1 型号拆分
     *      2.1.1修改pco
     *      2.1.2 重新计算task flag=3
     *  2.2 非型号拆分
     *      2.2.1 拆分指令
     *      2.2.2 重算集约仓，修改task
     *  2.3 修改do
     */
    public void modifyOther(OpsDo opsDo, Map<String, String> result, UpdateForOrderDto dto) throws OpsException{
        if(DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())){
            result.put(opsDo.getDoId(), "物流已经发货不能修改");
            return;
        }
        //1.已下发wms
        if (1 == opsDo.getIsWms()) {//拆分型号
            //1.1型号拆分
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                List<OpsPco> pcos = baseWMOrderService.getPcos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
                if(CollectionUtils.isEmpty(pcos)){
                    result.put(opsDo.getDoId(), "数据异常无pco");
                    return;
                }
                OpsPco opsPco = pcos.get(0);
                if (opsPco.getIsWms() != 1) {
                    result.put(opsDo.getDoId(), "数据异常pco下发不一致");
                    return;
                }
                //1.1.1 收集指令
                OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
                pcoAndDoMidIDDto.setDoId(opsDo.getDoId());
                pcoAndDoMidIDDto.setPcoId(opsPco.getPcoId());
                pcoAndDoMidIDDto.setUpdateOpsDo(opsDoService.fillInUpdateInfo(opsDo, dto));
                pcoAndDoMidIDDto.setUdateOpsPco(opsDoService.fillInUpdatePcoInfo(opsPco, dto));
                //bugid:11447 c14717 20230809
                if(StringUtils.isNotBlank(dto.getCproductNo())){
                    pcoAndDoMidIDDto.setCproductNo(dto.getCproductNo());
                }
                //1.1.2 调用wms接口
                CommonResult<String> resWm = wmDoService.postWmsDoAndPcoNew(pcoAndDoMidIDDto);
                if (resWm.isSuccess()) {
                    //1.1.3 变更指令
                    opsDoService.updateDo(opsDo, dto);
                    opsDoService.updatePco(opsPco, dto);
                    result.put(opsDo.getDoId(), "1");
                } else {
                    result.put(opsDo.getDoId(), "WMS已经处理，不允许变更");
                }
            } else {
                //1.2非拆分型号
                //1.2.1 收集指令变更指令
                OpsDoAndItemDto doDto = opsDoService.findDoToWms(opsDo.getDoId());
                doDto.setOpsDo(opsDoService.fillInUpdateInfo(opsDo, dto));
                //bugid:11447 c14717 20230809
                if(StringUtils.isNotBlank(dto.getCproductNo())){
                    doDto.getList().get(0).setCproductNo(dto.getCproductNo());
                }
                //1.2.2 调用wms接口
                CommonResult<String> resWm = wmDoService.postWmsDoNew(doDto);
                if (resWm.isSuccess()) {
                    // 1.2.3 持久化接口
                    opsDoService.updateDo(opsDo, dto);
                    result.put(opsDo.getDoId(), "1");
                } else {
                    result.put(opsDo.getDoId(), "WMS已经处理，不允许变更");
                }
            }
        } else {
            //2.没下发wms
            //2.1 型号拆分
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                List<OpsPco> pcos = baseWMOrderService.getPcos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
                if(CollectionUtils.isEmpty(pcos)){
                    result.put(opsDo.getDoId(), "数据异常无pco");
                    return;
                }
                OpsPco opsPco = pcos.get(0);
                //2.1.1修改pco
                opsDoService.updatePco(opsPco, dto);
                //2.1.2 重新计算task flag=3
                if (Objects.nonNull(dto.getDlvEntire())
                        && !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                        && !CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(dto.getDlvEntire())
                ) {
                    if (!opsDo.getDlvEntire().equals(dto.getDlvEntire())) {
                        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsPco.getPcoId(), "修改发货方式", 3);
                    }
                }
            } else {
                //2.2 非型号拆分
                if (Objects.nonNull(dto.getDlvEntire())
                        && !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                        && !CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(dto.getDlvEntire())
                ) {
                    if (!opsDo.getDlvEntire().equals(dto.getDlvEntire())) {
                        //2.2.1 拆分指令
                        if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(dto.getDlvEntire())) {
                            createAssDo(opsDo, 0, dto.getUserDto(), DoTypeEnum.JYCK, dto.getDlvEntire());
                        }
                        //2.2.2 重算集约仓，修改task
                        if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                                && DoWaitTypeEnum.WaitDB.getType().equals(opsDo.getWaitType())
                                && (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource()))) {
                            //计算集约仓
                            updateGatherWarehouse(opsDo);
                        }
                        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsDo.getDoId(), "修改发货方式", 3);
                    }
                }
            }
            //2.3 修改do
            opsDoService.updateDo(opsDo, dto);
            result.put(opsDo.getDoId(), "1");
        }
    }

    /**
     * 2.2.2 重算集约仓
     * @param oldOpsDo
     * @throws OpsException
     * 1.删调拨单
     * 2.添加上架类型
     * 3.计算货齐数
     * 4.发送事件
     * 5.重算货期
     */
    public void updateGatherWarehouse(OpsDo oldOpsDo) throws OpsException{
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())){
            // 1.删调拨单
            OpsDo delDBDo = updateDoDlvEntryDelDB(oldOpsDo);
            if (Objects.nonNull(delDBDo)) {
                //2.添加上架类型
                if (StringUtils.isNotEmpty(delDBDo.getRoId())) {
                    opsDoService.updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                }
                //3.计算货齐数
                List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(oldOpsDo.getDoId());
                int qty = 0;
                for (OpsDoItemInventory obj : doItemInventorieList) {
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                        qty += obj.getUseQty();
                    }
                }
                // 4.发送事件
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                //5.重算货期
                updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
            }
        }
    }

    //四、 转定 获取现状关联关系
    @Override
    public void showItemInvZD(String orderId, String orderItem, Integer num, Integer assNum, Boolean assModelFlag, List<ZDPageShowOrderBindInvDto> outPutZD) throws OpsException {
        //1.获取调拨单库存关联关系，调拨单不存在需要二次处理订单
        showDBCKItemInvZD(orderId, orderItem, num, assNum, assModelFlag, outPutZD);
        if(assModelFlag){
            //2.获取加工单库存关联关系和二次处理状态虚拟返回实体
            showAssModelItemInvZD(orderId, orderItem, num, assNum, outPutZD);
        }else {
            //3.获取交易单库存关联关系和二次处理状态虚拟返回实体
            showNotAssModelItemInvZD(orderId, orderItem, num, assNum, outPutZD);
        }
    }

    //1.获取调拨单库存关联关系，调拨单不存在需要二次处理订单todo num 和 ass num 如何应对
    public void showDBCKItemInvZD(String orderId, String orderItem, Integer num, Integer assNum, Boolean assModelFlag, List<ZDPageShowOrderBindInvDto> outPutZD)throws OpsException{
        //1.验证指令
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem, num, assNum);
        List<OpsDo> opsDBCKDoList = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK);
        if(CollectionUtils.isEmpty(opsDBCKDoList)){
            return;
        }
        List<OpsDo> dojys = baseWMOrderService.getDos(orderId, orderItem, num);
        List<OpsDo> opsJYCKDoList = baseWMOrderService.getDos(dojys, DoTypeEnum.JYCK);

        if(opsJYCKDoList.size() != 1){
            throw Exceptions.OpsException("交易出库数据异常"+opsJYCKDoList.size());
        }
        for(OpsDo db : opsDBCKDoList){
            if(DoStatusEnum.PART.getStatus().equals(db.getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(db.getDoStatus())){
                continue;
            }
            Integer itemInvQty = 0;
            String dbDoId = db.getDoId();
            //2.查询调拨doItem
            OpsDoItem dbDoItem = baseDoService.getDoItemByDoId(dbDoId);
            Integer itemQty = dbDoItem.getQty();
            //3.查询调拨itemInv
            List<OpsDoItemInventory> DBdoItemInvList = baseDoService.getDoItemInventoryByDoId(dbDoId);
            int itemSize = DBdoItemInvList.size();
            String jyckDoId = opsJYCKDoList.get(0).getDoId();
            String pcoId = "";

            if(assModelFlag){
                //5.查询pcoId
                List<OpsPco> pcos = baseWMOrderService.getPcos(orderId, orderItem, num);
                if(CollectionUtils.isEmpty(pcos)){
                    throw Exceptions.OpsException("加工指令不存在");
                }
                pcoId = pcos.get(0).getPcoId();
            }
            //5.获取关联列表，及可转库存
            for(OpsDoItemInventory DBitemInv : DBdoItemInvList){
                itemInvQty = itemInvQty + DBitemInv.getUseQty();
                ZDPageShowOrderBindInvDto param =
                        new ZDPageShowOrderBindInvDto(DBitemInv.getInventoryId(),DBitemInv.getInventoryTableType(),DBitemInv.getUseQty(),
                                false,assModelFlag, DBitemInv.getId(), true,DBitemInv.getDoId(),orderId,orderItem,num,
                                jyckDoId,pcoId,assNum,dbDoItem.getModelno(),"","","",false,"在库不可转定库存");
                param.setAssNum(assNum);
                if(InventoryTableTypeEnum.TRANS.getType().equals(DBitemInv.getInventoryTableType())){
                    // 构造返回数据
                    putZDPageDtoByMoveInv(param,DBitemInv.getInventoryId());
                    if(itemSize != 1){
                        param.setActionFlag(false);
                        param.setActionMsg("调拨单多条关联库存不可转定，可改随发分批");
                    }
                }else {
                    // 构造返回数据
                    putZDPageDtoByInv(param,DBitemInv.getInventoryId());
                }
                outPutZD.add(param);
            }
            //验证数量不可转定
            if(!itemQty.equals(itemInvQty)){
                throw Exceptions.OpsException("数据数量异常："+dbDoItem.getDoId());
            }
        }


    }
    //2.获取加工单库存关联关系和二次处理状态虚拟返回实体
    public void showAssModelItemInvZD(String orderId, String orderItem, Integer num, Integer assNum, List<ZDPageShowOrderBindInvDto> outPutZD)throws OpsException{
        //1.查询、验证指令
        List<OpsPco> pcos = baseWMOrderService.getPcos(orderId, orderItem, num);
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem, num);
        List<OpsDo> opsJYCKDoList = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(pcos)){
            throw Exceptions.OpsException("加工指令不存在");
        }
        if(opsJYCKDoList.size() != 1){
            throw Exceptions.OpsException("交易出库数据异常，存在多条");
        }

        String jyckDoId = opsJYCKDoList.get(0).getDoId();
        Integer itemQty = 0;
        Integer itemInvQty = 0;
        String pcoId = pcos.get(0).getPcoId();
        //2.查询pcoItem
        List<OpsPcoItem> pcoItems = baseWMOrderService.getPcoItem(pcoId, assNum);
        if(CollectionUtils.isEmpty(pcoItems)){
            throw Exceptions.OpsException("加工指令item不存在");
        }
        OpsPcoItem opsPcoItem = pcoItems.get(0);
        itemQty = opsPcoItem.getSubQty();
        //3.查询itemInv
        List<OpsPcoItemInventory> itemInvList = baseWMOrderService.getPcoItemInv(pcoId, assNum);
        int pcoItemInvSize = itemInvList.size();

        //4.获取关联列表，及可转库存
        for(OpsPcoItemInventory itemInv : itemInvList){
            itemInvQty = itemInvQty + itemInv.getUseQty();
            ZDPageShowOrderBindInvDto  param =
                    new ZDPageShowOrderBindInvDto(itemInv.getInventoryId(),itemInv.getInventoryTableType(),itemInv.getUseQty(),
                            false,true, itemInv.getId(), false,null,orderId,orderItem,num,
                            jyckDoId,itemInv.getPcoId(),itemInv.getPcoItem(),opsPcoItem.getSubModelno(),"",
                            "","",false,"在库不可转定库存");
            param.setAssNum(assNum);
            if(InventoryTableTypeEnum.TRANS.getType().equals(itemInv.getInventoryTableType())){
                // 构造返回数据
                putZDPageDtoByMoveInv(param,itemInv.getInventoryId());
                if(pcoItemInvSize != 1){
                    param.setActionFlag(false);
                    param.setActionMsg("加工单子型号关联多条库存，可先改子项特发");
                }
            }else {
                // 构造返回数据
                putZDPageDtoByInv(param,itemInv.getInventoryId());
            }
            outPutZD.add(param);
        }
        //5.删除调拨关联关系
        if(Objects.equals(itemQty, itemInvQty)){
            outPutZD.removeIf(ZDPageShowOrderBindInvDto::getDbOrNot);
        }
        //6.组装二次处理状态和无关联关系需要虚拟关联关系
        if(itemQty > itemInvQty){
            if(DoWaitTypeEnum.EXCEPTION.getType().equals(opsPcoItem.getWaitType())){
                ZDPageShowOrderBindInvDto  param =
                        new ZDPageShowOrderBindInvDto(null,DoWaitTypeEnum.EXCEPTION.getType(),itemQty - itemInvQty,
                                true,true, null, false,null,orderId,orderItem,num,
                                jyckDoId,opsPcoItem.getPcoId(),opsPcoItem.getPcoItem(),opsPcoItem.getSubModelno(),"","","",true,"可转定");
                param.setAssNum(assNum);
                outPutZD.add(param);
            }else{
                ZDPageShowOrderBindInvDto  param =
                        new ZDPageShowOrderBindInvDto(null,null,itemQty - itemInvQty,
                                true,true, null, false,null,orderId,orderItem,num,
                                jyckDoId,opsPcoItem.getPcoId(),opsPcoItem.getPcoItem(),opsPcoItem.getSubModelno(),"",
                                "","",false,"无关联关系不可转定");
                param.setAssNum(assNum);
                if(DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())){
                    param.setActionFlag(true);
                    param.setActionMsg("采购未接单可转定");
                    outPutZD.add(param);
                }
            }
        }
    }

    ///获取交易单库存关联关系和二次处理状态虚拟返回实体
    public void showNotAssModelItemInvZD(String orderId, String orderItem, Integer num, Integer assNum, List<ZDPageShowOrderBindInvDto> outPutZD)throws OpsException{
        //1.查询
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem, num);
        List<OpsDo> opsJYCKDoList = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(opsJYCKDoList)){
            return;
        }
        if(opsJYCKDoList.size() != 1){
            throw Exceptions.OpsException("交易单数据异常，存在多条");
        }
        Integer itemQty = 0;
        Integer itemInvQty = 0;
        String doId = opsJYCKDoList.get(0).getDoId();
        //2.查询交易doItem
        OpsDoItem jyDoItem = baseDoService.getDoItemByDoId(doId);
        itemQty = jyDoItem.getQty();
        //3.获取关联列表，及可转库存
        List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByDoId(doId);
        for(OpsDoItemInventory itemInv : doItemInventoryList){
            itemInvQty = itemInvQty + itemInv.getUseQty();
            ZDPageShowOrderBindInvDto param =
                    new ZDPageShowOrderBindInvDto(itemInv.getInventoryId(),itemInv.getInventoryTableType(),itemInv.getUseQty(),
                            false,false, itemInv.getId(), false,null,
                            orderId,orderItem,num,jyDoItem.getDoId(),null,null,jyDoItem.getModelno(),
                            "","","",false,"在库不可转定库存");
            param.setAssNum(assNum);
            if(InventoryTableTypeEnum.TRANS.getType().equals(itemInv.getInventoryTableType())){
                // 构造返回数据
                putZDPageDtoByMoveInv(param,itemInv.getInventoryId());
            }else {
                // 构造返回数据
                putZDPageDtoByInv(param,itemInv.getInventoryId());
            }
            outPutZD.add(param);
        }
        //4.删除调拨关联关系
        if(Objects.equals(itemQty, itemInvQty)){
            outPutZD.removeIf(ZDPageShowOrderBindInvDto::getDbOrNot);
        }
        //5.组装二次处理状态和无关联关系需要虚拟关联关系
        if(itemQty > itemInvQty ){
            if(DoWaitTypeEnum.EXCEPTION.getType().equals(opsJYCKDoList.get(0).getWaitType())){
                ZDPageShowOrderBindInvDto  param =
                        new ZDPageShowOrderBindInvDto(null,DoWaitTypeEnum.EXCEPTION.getType(),itemQty - itemInvQty,
                                true,false, null, false,null,orderId,orderItem,num,
                                jyDoItem.getDoId(),null,null,jyDoItem.getModelno(),"",
                                "","",true,"可转定");
                param.setAssNum(assNum);
                outPutZD.add(param);
            }else{
                ZDPageShowOrderBindInvDto  param =
                        new ZDPageShowOrderBindInvDto(null,null,itemQty - itemInvQty,
                                true,false, null, false,null,orderId,orderItem,num,
                                jyDoItem.getDoId(),null,null,jyDoItem.getModelno(),"",
                                "","",false,"无关联关系不可转定");
                param.setAssNum(assNum);
                if(DoWaitTypeEnum.WaitCG.getType().equals(opsJYCKDoList.get(0).getWaitType())){
                    param.setActionFlag(true);
                    param.setActionMsg("采购未接单可转定");
                    outPutZD.add(param);
                }
            }
        }
    }
    // 根据库存构造返回数据
    public void putZDPageDtoByMoveInv(ZDPageShowOrderBindInvDto param,Long moveInvId){
        OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(moveInvId);
        OpsInventoryProperty property = opsInventoryPropertyService.findById(invMove.getInventoryPropertyId());
        param.setWarehouseCode(invMove.getWarehouseCode());
        param.setInventoryTypeCode(property.getInventoryTypeCode());
        param.setPageShowInvTableType(invMove.getInventoryStatus());
        param.setActionFlag(true);
        param.setActionMsg("可转定");
        param.setInvoiceno(invMove.getInvoiceno());
        param.setAssociateNo(invMove.getAssociateNo());
        param.setAssociateNoItem(invMove.getAssociateNoItem());
        param.setAssociateNoSplitno(invMove.getAssociateNoSplitno());
        param.setSupplierId(invMove.getSupplierid());
        // W5不可转定
        if (InventoryStatusEnum.W.getCode().equals(invMove.getInventoryStatus())){
            if(Objects.nonNull(invMove.getOptStatus()) && invMove.getOptStatus().equals(5)){
                param.setActionFlag(false);
                param.setActionMsg("到货未入库不可转定");
            }
        }
        //调拨在途不可转定
        if (InventoryStatusEnum.DBTRANS.getCode().equals(invMove.getInventoryStatus())){
            param.setActionFlag(false);
            param.setActionMsg("调拨在途不可转定");
        }
    }
    // 根据库存构造返回数据
    public void putZDPageDtoByInv(ZDPageShowOrderBindInvDto param,Long invId){
        OpsInventory inv = baseInventoryService.getInventoryById(invId);
        OpsInventoryProperty property = opsInventoryPropertyService.findById(inv.getInventoryPropertyId());
        param.setInventoryTypeCode(property.getInventoryTypeCode());
        param.setWarehouseCode(inv.getWarehouseCode());
        param.setPageShowInvTableType("N");
    }

    /**
     * 五、转定 绑定新的关联关系
     * @param param
     * @throws OpsException
     */
    @Override
    public void handleOrderBindInvZD(ZDPageShowOrderBindInvDto param) throws OpsException {
        UserDto userDto = new UserDto(param.getUserName());
        //1.绑定指令
        //db
        param.setResultLog(new StringBuffer());
        if (param.getDbOrNot()) {
            param.getResultLog().append(";db：");
            handleDB(param, userDto);
        } else {
            //pco
            if (param.getAssModleFlag()) {
                param.getResultLog().append(";pco：");
                handlePCO(param, userDto);
            } else {//do
                param.getResultLog().append(";do：");
                handleDO(param, userDto);
            }
        }
        //2. 变更物流交货期
        updateDoPcoWlday(param.getOrderId(), param.getOrderItem(), null, true, userDto);

        //3. 状态事件组装报文
        List<OrderAdjustDto> params = new ArrayList<>();
        if (param.getDbOrNot()) {
            // 调拨单组装有库存关联关系的报文
            Long dbDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getDbDoId());
            Long jyDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getDoId());
            if (dbDoItemInvsCount > 0) {
                params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getDbDoId()));
            }
            if (jyDoItemInvsCount > 0) {
                params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getDoId()));
            }
            // bugid:12534 c14717 20231103  子项转订后，订单状态异常
            if (param.getAssModleFlag()) {
                Long pcoCount = opsPcoService.countPcoItemInvByPcoIdItem(param.getPcoId(), param.getPcoItem());
                if (pcoCount > 0) {
                    params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getPcoItem(), param.getPcoId()));
                }
            }
            //事件发送：客单转订（在途转在途）
            ResultForOrderAdjustDto dto = buildBindInvAdjustDto(param);
            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ADJUST, param.getOrderId(), Integer.valueOf(param.getOrderItem()), param.getNum(), dto);

        } else {// 非db
            if (param.getAssModleFlag()) {
                // 加工单组装有库存关联关系的报文
                if (StringUtils.isNotBlank(param.getNewDBDoId())) {
                    Long dbDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getNewDBDoId());
                    if (dbDoItemInvsCount > 0) {
                        params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getNewDBDoId()));
                    }
                }
                Long pcoCount = opsPcoService.countPcoItemInvByPcoIdItem(param.getPcoId(), param.getPcoItem());
                if (pcoCount > 0) {
                    params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getPcoItem(), param.getPcoId()));
                }
            } else {
                // 非单组装有库存关联关系的报文
                // 交易单
                Long jyDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getDoId());
                if (jyDoItemInvsCount > 0) {
                    params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getDoId()));
                }
                // 新产生调拨单
                if (StringUtils.isNotBlank(param.getNewDBDoId())) {
                    Long dbDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getNewDBDoId());
                    if (dbDoItemInvsCount > 0) {
                        if (Objects.nonNull(param.getAssNewNum()) && param.getAssNewNum() != 0) {
                            params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getAssNewNum(), param.getNewDBDoId()));
                        } else {
                            params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getNewDBDoId()));
                        }
                    }
                } else {
                    //新拆分的交易出库单
                    if (StringUtils.isNotBlank(param.getAssNewJYDoId())) {
                        Long dbDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getAssNewJYDoId());
                        if (dbDoItemInvsCount > 0) {
                            params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getAssNewNum(), param.getAssNewJYDoId()));
                        }
                    }
                }
            }
            //事件发送：客单转订，按当前 V3 from/to 矩阵组装分配结果调整 DTO。
            ResultForOrderAdjustDto dto = buildBindInvAdjustDto(param);
            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ADJUST, param.getOrderId(), Integer.valueOf(param.getOrderItem()), param.getNum(), dto);
        }
    }

    private ResultForOrderAdjustDto buildBindInvAdjustDto(ZDPageShowOrderBindInvDto param) throws OpsException {
        ResultForOrderAdjustDto dto;
        if (InventoryTableTypeEnum.NORMAL.getType().equals(param.getToInvTableType())) {
            dto = buildToNormalAdjustDto(param);
            dto.setRiskFlag(param.getToInvRiskFlag());
            return dto;
        }
        if (InventoryTableTypeEnum.TRANS.getType().equals(param.getToInvTableType())) {
            return buildToTransitAdjustDto(param);
        }
        throw Exceptions.OpsException("不支持的转订目标库存类型:" + param.getToInvTableType());
    }

    private ResultForOrderAdjustDto buildToNormalAdjustDto(ZDPageShowOrderBindInvDto param) throws OpsException {
        if (isExceptionSource(param)) {
            return ResultForOrderAdjustDto.exceptionToNormal(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    param.getInvId(), param.getToInvId(), param.getToInvTableType());
        }
        if (isPurchaseSource(param)) {
            return ResultForOrderAdjustDto.purchaseToNormal(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    null, param.getToInvId(), param.getToInvTableType());
        }
        if (isTransitSource(param)) {
            return ResultForOrderAdjustDto.transitToNormal(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    param.getInvId(), param.getToInvId(), param.getToInvTableType());
        }
        throw Exceptions.OpsException("不支持的转在库来源库存类型:" + param.getInvTableType());
    }

    private ResultForOrderAdjustDto buildToTransitAdjustDto(ZDPageShowOrderBindInvDto param) throws OpsException {
        if (isExceptionSource(param)) {
            return ResultForOrderAdjustDto.exceptionToTransit(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    param.getToInvId(), param.getToInvTableType());
        }
        if (isPurchaseSource(param)) {
            return ResultForOrderAdjustDto.purchaseToTransit(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    param.getToInvId(), param.getToInvTableType());
        }
        if (isTransitSource(param)) {
            return ResultForOrderAdjustDto.transitToTransit(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(),
                    param.getInvId(), param.getToInvId(), param.getToInvTableType());
        }
        throw Exceptions.OpsException("不支持的转在途来源库存类型:" + param.getInvTableType());
    }

    private boolean isExceptionSource(ZDPageShowOrderBindInvDto param) {
        return Boolean.TRUE.equals(param.getExceptionOrNot()) && DoWaitTypeEnum.EXCEPTION.getType().equals(param.getInvTableType());
    }

    private boolean isPurchaseSource(ZDPageShowOrderBindInvDto param) {
        return Boolean.TRUE.equals(param.getExceptionOrNot()) && StringUtils.isBlank(param.getInvTableType());
    }

    private boolean isTransitSource(ZDPageShowOrderBindInvDto param) {
        return InventoryTableTypeEnum.TRANS.getType().equals(param.getInvTableType());
    }

    // 转定验证
    public void checkZD(ZDPageShowOrderBindInvDto param) throws OpsException{
        //1.调拨单和交易单验证
        if(param.getDbOrNot() ||( !param.getDbOrNot() && !param.getAssModleFlag())){
            String thisDoid = "";
            if(param.getDbOrNot()){
                thisDoid = param.getDbDoId();
            }else {
                thisDoid = param.getDoId();
            }
            //1.1状态及关联关系验证
            if(!param.getExceptionOrNot()){
                OpsDoItemInventory doItemInventory = baseDoService.getDoItemInventoryById(param.getItemInvId());
                OpsDo opsDo =  baseDoService.getDoByDoId(thisDoid);
                if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())
                        || DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())){
                    throw Exceptions.OpsException("物流已发货");
                }
                if(!param.getExceptionOrNot() && Objects.isNull(doItemInventory)){
                    throw Exceptions.OpsException("关联关系不存在："+param.getItemInvId());
                }
                //验证关联关系
                if(Objects.nonNull(doItemInventory)){
                    if(param.getDbOrNot() && !doItemInventory.getUseQty().equals( param.getQty())){
                        throw Exceptions.OpsException("调拨单多条关联关系"+opsDo.getDoId());
                    }
                    if(!InventoryTableTypeEnum.TRANS.getType().equals(doItemInventory.getInventoryTableType())){
                        throw Exceptions.OpsException("关联关系状态不对N");
                    }
                    OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(doItemInventory.getInventoryId());
                    // W 5 不可转定
                    if (InventoryStatusEnum.W.getCode().equals(invMove.getInventoryStatus())){
                        if(Objects.nonNull(invMove.getOptStatus()) && invMove.getOptStatus().equals(5)){
                            throw Exceptions.OpsException("关联关系状态不对：W5");
                        }
                    }
                    //调拨在途不可转定
                    if (InventoryStatusEnum.DBTRANS.getCode().equals(invMove.getInventoryStatus())){
                        throw Exceptions.OpsException("关联关系状态不对："+invMove.getInventoryStatus());
                    }
                }
            }
            //1.2 验证数量是否符合
            List<OpsDoItemInventory> opsDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(thisDoid);
            if(param.getDbOrNot()){
                if(opsDoItemInventoryList.size()>1){
                    throw Exceptions.OpsException("调拨单多条关联库存不可转定，可改随发分批");
                }
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(thisDoid);
            if(CollectionUtils.isNotEmpty(opsDoItemInventoryList)){
                int itemInvQty = param.getQty();
                for(OpsDoItemInventory opsDoItemInventory : opsDoItemInventoryList){
                    if(Objects.nonNull(param.getItemInvId()) && opsDoItemInventory.getId().equals(param.getItemInvId())){
                        continue;
                    }
                    itemInvQty = itemInvQty + opsDoItemInventory.getUseQty();
                }
                if(itemInvQty - opsDoItem.getQty() != 0){
                    throw Exceptions.OpsException("转定数量不符合");
                }
            }else{
                if(param.getQty() - opsDoItem.getQty() != 0){
                    throw Exceptions.OpsException("转定数量不符合");
                }
            }
            //bugid:17162 c14717 20250320
            if(!opsDoItem.getModelno().equals(param.getModelNo())){
                throw Exceptions.OpsException("转定型号不符");
            }
        }else{
            //2.pco加工单关联关系验证
            if(param.getAssModleFlag()){
                if(!param.getExceptionOrNot()){
                    OpsPcoItemInventory itemInventory = opsPcoService.findPcoItemInventoryById(param.getItemInvId());
                    if(!param.getExceptionOrNot() && Objects.isNull(itemInventory)){
                        throw Exceptions.OpsException("关联关系不存在："+param.getItemInvId());
                    }
                    if(Objects.nonNull(itemInventory)){
                        if(!InventoryTableTypeEnum.TRANS.getType().equals(itemInventory.getInventoryTableType())){
                            throw Exceptions.OpsException("关联关系状态不对："+itemInventory.getInventoryTableType());
                        }
                        OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(itemInventory.getInventoryId());
                        // W 5 不可转定
                        if (InventoryStatusEnum.W.getCode().equals(invMove.getInventoryStatus())){
                            if(Objects.nonNull(invMove.getOptStatus()) && invMove.getOptStatus().equals(5)){
                                throw Exceptions.OpsException("关联关系状态不对：W5");
                            }
                        }
                        //调拨在途不可转定
                        if (InventoryStatusEnum.DBTRANS.getCode().equals(invMove.getInventoryStatus())){
                            throw Exceptions.OpsException("关联关系状态不对："+invMove.getInventoryStatus());
                        }
                    }
                }
                //2.1 验证数量
                List<OpsPcoItemInventory> opsPcoItemInventoryList =  opsPcoService.getOpsPcoItemInventoryByPcoId(param.getPcoId(),param.getPcoItem());
                OpsPcoItem opsPcoItem =  opsPcoService.getPcoItemByPcoId(param.getPcoId(),param.getPcoItem());
                if(CollectionUtils.isNotEmpty(opsPcoItemInventoryList)){
                    if(opsPcoItemInventoryList.size()>1){
                        throw Exceptions.OpsException("加工单子型号关联多条库存，可先改子项特发");
                    }
                    int itemInvQty = param.getQty();
                    for(OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList){
                        if(Objects.nonNull(param.getItemInvId()) && opsPcoItemInventory.getId().equals(param.getItemInvId())){
                            continue;
                        }
                        itemInvQty = itemInvQty + opsPcoItemInventory.getUseQty();
                    }
                    if(itemInvQty - opsPcoItem.getSubQty() != 0){
                        throw Exceptions.OpsException("转定数量不符合");
                    }
                }else{
                    if(param.getQty() - opsPcoItem.getSubQty() != 0){
                        throw Exceptions.OpsException("转定数量不符合");
                    }
                }
                //bugid:17162 c14717 20250320
                if(!opsPcoItem.getSubModelno().equals(param.getModelNo())){
                    throw Exceptions.OpsException("转定型号不符");
                }
            }
        }
        //3.库存验证
        if(InventoryTableTypeEnum.TRANS.getType().equals(param.getToInvTableType())){
            OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(param.getToInvId());
            if(Objects.isNull(invMove)){
                throw Exceptions.OpsException("库存不存在："+param.getToInvId()+":"+param.getToInvTableType());
            }
            if((invMove.getQuantity() - invMove.getPrepareQuantity() - param.getQty()) < 0){
                throw Exceptions.OpsException("可用库存数量不足："+param.getToInvId()+":"+param.getToInvTableType());
            }
            //3.1组装报文，型号及转定目的仓
            param.setModelNo(invMove.getModelno());
            //bugid:13544 c14717 20240305 在途取库存取 签收仓为库存仓库
            param.setToWareHouseCode( invMove.getSignWarehouseCode());
        }else {
            OpsInventory opsInv = baseInventoryService.getInventoryById(param.getToInvId());
            if(Objects.isNull(opsInv)){
                throw Exceptions.OpsException("库存不存在："+param.getToInvId()+":"+param.getToInvTableType());
            }
            if((opsInv.getQuantity() - opsInv.getPrepareQuantity() - param.getQty()) < 0){
                throw Exceptions.OpsException("可用库存数量不足："+param.getToInvId()+":"+param.getToInvTableType());
            }
            //3.1组装报文，型号及转定目的仓
            param.setModelNo(opsInv.getModelno());
            param.setToWareHouseCode( opsInv.getWarehouseCode());
        }

    }

    /**
     * 如果单据已经存在，不删除调拨单
     * @param param
     * @param userDto
     * @throws OpsException
     * 1.转定验证
     * 2.调用wms删单接口
     * 3.发货仓=转定仓 绑定关联关系
     * 4.发货仓 != 转定仓
     *  4.1删除调拨单 绑定关联关系（发货仓与集约仓一致）
     *  4.2绑定关联关系（发货仓与集约仓不一致）
     */
    public void handleDB(ZDPageShowOrderBindInvDto param,UserDto userDto) throws OpsException{
        //1.转定验证
        checkZD(param);
        OpsDo DBDo =  baseDoService.getDoByDoId(param.getDbDoId());
        //bugid:14056 c14717 2024-04-25 删除调拨单判断状态
        if (DoStatusEnum.PART.getStatus().equals(DBDo.getDoStatus())
                || DoStatusEnum.FINISH.getStatus().equals(DBDo.getDoStatus())){
            throw Exceptions.OpsException("物流已作业");
        }
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(DBDo, null,null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        //3.集约仓=转定仓
        if(DBDo.getWarehouseCode().equals(param.getToWareHouseCode())){
            //bugid:12294 c14717 20231019
            boolean newTaskFlag = false;
            if(DBDo.getIsWms() == 1){
                newTaskFlag = true;
                DBDo.setIsWms(0);
                opsDoService.updateOpsDo(DBDo,userDto);
            }
            // 释放预占数量 关联新库存，预占库存数量
            updateDoItemInvSubAndAddInvPre(param,userDto);
            //bugid:12294 c14717 20231019
            // 转定重新计算
            taskDoHandle(newTaskFlag,DBDo.getDoId(),"转定");
            param.getResultLog().append("变更关联系");
        }
        //4.发货仓 != 转定仓
        if(!DBDo.getWarehouseCode().equals(param.getToWareHouseCode())){
            //4.1 发货仓与集约仓一致 删除调拨单
            if(DBDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
                CancelForOrderDto inputDto = new CancelForOrderDto(DBDo.getOrderId(),DBDo.getOrderItem(),DBDo.getDoType(),userDto,"转定取消");
                Long cancelId = opsDoService.insertOrderCancel(inputDto);
                delDo(DBDo,cancelId,DoTypeEnum.DBCK,"转定取消");
                delDBRo(DBDo, cancelId, RoTypeEnum.DBRK);
                //创建交易出库的关联关系 ,重新计算下发
                String orderFno = param.getOrderId()+"-"+param.getOrderItem();
                if(param.getAssModleFlag()){
                    OpsPco opsPco = opsPcoService.getPcoByPcoId(param.getPcoId());
                    if(opsPco.getIsWms().equals(1)){
                        throw Exceptions.OpsException("调拨单未出库，加工单已下发："+opsPco.getPcoId());
                    }
                    zdService.createPcoItemInvAddPre(orderFno,param.getToInvRiskFlag(),param.getPcoId(), param.getAssNum(),param.getQty(), param.getToInvTableType(), param.getToInvId(), userDto);
                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(param.getPcoId(), "转定", 3);
                    OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(param.getPcoId(), param.getPcoItem());
                    opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(),DoWaitTypeEnum.OK.getType(), userDto);
                    param.getResultLog().append("删db,pcoItem_wait=ok,绑定pcoItemInv");
                }else{
                    zdService.createDoItemInvAddPre(orderFno,param.getToInvRiskFlag(),param.getDoId(), param.getQty(), param.getToInvTableType(), param.getToInvId(), userDto);
                    OpsDo jyckDo =  baseDoService.getDoByDoId(param.getDoId());
                    if(jyckDo.getIsWms().equals(1)){
                        throw Exceptions.OpsException("调拨单未出库，交易单已下发："+jyckDo.getDoId());
                    }
                    opsDoService.updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, jyckDo.getGatherWarehouseCode(), jyckDo.getGatherWarehouseCode(),
                            DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), param.getUserName(), 0, null);
                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(param.getDoId(), "转定", 3);
                    param.getResultLog().append("删db，变更发货仓，修改waitType=ok，绑定doItemInv");
                }
            }
            // 4.2 发货仓与集约仓一致 修改发货仓
            if(!DBDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
                //bugid:12294 c14717 20231019
                boolean newTaskFlag = false;
                if(DBDo.getIsWms() == 1){
                    newTaskFlag = true;
                }
                DBDo.setIsWms(0);
                DBDo.setWarehouseCode(param.getToWareHouseCode());
                opsDoService.updateOpsDo(DBDo,userDto);
                OpsDo jyckDo =  baseDoService.getDoByDoId(param.getDoId());
                if(jyckDo.getIsWms().equals(1)){
                    throw Exceptions.OpsException("调拨单未出库，交易单已下发："+jyckDo.getDoId());
                }
                //bugid:15503 c14717 20241023 do_source ='ASSModelNo' 不改发货仓
                if(!DoSourceEnum.ASSModelNo.getType().equals(jyckDo.getDoSource())){
                    opsDoService.updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null,null ,param.getToWareHouseCode(),
                            null, jyckDo.getVersion(), param.getUserName(), 0, null);
                }
                updateDoItemInvSubAndAddInvPre(param,userDto);
                taskDoHandle(newTaskFlag,DBDo.getDoId(),"转定");
                param.getResultLog().append("变更关联系,修改发货仓");
            }
        }
    }

    /**
     * 转定处理do
     * @param param
     * @param userDto
     * @throws OpsException
     * 1.转定验证
     * 2.调用wms删单接口
     * 3.集约仓=转定仓
     *  3.1 绑定新关联关系
     *  3.2 拆分新指令
     * 4.集约仓 != 转定仓
     *  4.1不拆分do
     *      4.1.1 创建调拨
     *      4.1.2 绑定关联关系
     *  4.2拆分do
     *      4.2.1拆分新指令
     *      4.2.2拆分新指令且创建调拨
     *
     */
    public void handleDO(ZDPageShowOrderBindInvDto param,UserDto userDto) throws OpsException{
        //1.转定验证
        checkZD(param);
        OpsDo jyDo =  baseDoService.getDoByDoId(param.getDoId());
        //bugid:14056 c14717 2024-04-25 删除调拨单判断状态
        if (DoStatusEnum.PART.getStatus().equals(jyDo.getDoStatus())
                || DoStatusEnum.FINISH.getStatus().equals(jyDo.getDoStatus())){
            throw Exceptions.OpsException("物流已作业");
        }
        if(!jyDo.getGatherWarehouseCode().equals(jyDo.getWarehouseCode())){
            throw Exceptions.OpsException("数据异常集约仓不等发货仓");
        }
        OpsDoItem jyDoItem = baseDoService.getDoItemByDoId(param.getDoId());
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(jyDo, null,null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        //bugid:12294 c14717 20231019
        boolean newTaskFlag = false;
        if(jyDo.getIsWms()==1){
            newTaskFlag = true;
        }
        if(jyDo.getIsWms()==1 || param.getExceptionOrNot()){
            //二次处理状态变更
            jyDo.setIsWms(0);
        }
        jyDo.setWaitType("OK");
        opsDoService.updateOpsDo(jyDo,userDto);
        jyDo =  baseDoService.getDoByDoId(param.getDoId());
        //3.集约仓=转定仓
        if(jyDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
            //3.1 绑定新关联关系
            if(jyDoItem.getQty() - param.getQty() == 0){
                //3.1.1异常处理状态
                if(param.getExceptionOrNot()){
                    //创建关联关系、task表flag = 3
                    String orderFno = param.getOrderId()+"-"+param.getOrderItem();
                    zdService.createDoItemInvAddPre(orderFno,param.getToInvRiskFlag(),param.getDoId(), param.getQty(), param.getToInvTableType(), param.getToInvId(), userDto);
                }else {//3.1.2非异常处理状态
                    //加减预占，变更关联关系
                    updateDoItemInvSubAndAddInvPre(param,userDto);}
                //bugid:12294 c14717 20231019
                taskDoHandle(newTaskFlag,param.getDoId(),"转定");
                param.getResultLog().append("集约=转定仓，不拆do，添加或变更关联关系;");
            }else{
                //3.2 拆分新指令
                createAssAndSubOldDoItemQtyZD(param, jyDo, jyDoItem, userDto, newTaskFlag, true,"集约=转定仓，拆do:");
            }
        }
        //4.集约仓 != 转定仓
        if(!jyDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
            //4.1不拆分do
            if(jyDoItem.getQty() - param.getQty() == 0){
                //货齐单仓 需要调拨 非随发分批且订单数量-do_item_qty !=0 需要调拨
                boolean creDBFlag = true;
                if(CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(jyDo.getDlvEntire()) || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(jyDo.getDlvEntire())){
                    Rcvdetail rcvdetail = baseWMOrderService.findRcvDetail(param.getOrderId(), Integer.parseInt(param.getOrderItem()));
                    if(rcvdetail.getQuantity() - param.getQty() == 0){
                        creDBFlag = false;
                    }
                }else if(CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(jyDo.getDlvEntire())){
                    creDBFlag = false;
                }
                //4.1.1 创建调拨
                if (creDBFlag){
                    jyDo.setWarehouseCode(param.getToWareHouseCode());
                    jyDo.setWaitType(DoWaitTypeEnum.WaitDB.getType());
                    opsDoService.updateOpsDo(jyDo,userDto);
                    //创建调拨单
                    String newDbId = createDBOrderZD(param.getToInvRiskFlag(),param.getNum(),param.getAssNum(), param.getModelNo(),param.getQty(),
                            jyDo.getDoSource(),jyDo,jyDoItem,param.getToInvId(),param.getToInvTableType(),userDto);
                    //非二次处理状态
                    if(!param.getExceptionOrNot()){
                        //删除doIteInv 释放预占数量
                        opsDoService.deleteDoItemInventoryByPrimaryKeySelective(param.getItemInvId(),  userDto);
                        baseInventoryService.releasePreQtyInventory(param.getInvId(),  param.getQty(), param.getInvTableType(), userDto);
                    }
                    param.setNewDBDoId(newDbId);
                    param.getResultLog().append("集约!=转定仓，不拆do,creDB:");
                    param.getResultLog().append(newDbId);
                }else{//4.1.2 绑定关联关系
                    jyDo.setGatherWarehouseCode(param.getToWareHouseCode());
                    jyDo.setWarehouseCode(param.getToWareHouseCode());
                    opsDoService.updateOpsDo(jyDo,userDto);
                    String orderFno = param.getOrderId()+"-"+param.getOrderItem();
                    //二次处理状态
                    if(param.getExceptionOrNot()){
                        //创建关联关系、task表flag = 3
                        zdService.createDoItemInvAddPre(orderFno,param.getToInvRiskFlag(),param.getDoId(), param.getQty(), param.getToInvTableType(), param.getToInvId(), userDto);
                    }else{//非二次处理状态
                        //加减预占，变更关联关系
                        updateDoItemInvSubAndAddInvPre(param,userDto);
                    }
                    //bugid:12294 c14717 20231019
                    taskDoHandle(newTaskFlag,param.getDoId(),"转定");
                    param.getResultLog().append("集约!=转定仓，不拆do,修改集约仓，添加或变更关联关系;");
                }
            }
            //4.2拆分do
            if(jyDoItem.getQty() - param.getQty() != 0){
                if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(jyDo.getDlvEntire())){
                    //4.2.1拆分新指令
                    jyDo.setWarehouseCode(param.getToWareHouseCode());
                    jyDo.setGatherWarehouseCode(param.getToWareHouseCode());
                    createAssAndSubOldDoItemQtyZD(param, jyDo, jyDoItem, userDto, newTaskFlag, true,"集约!=转定仓，拆do：");
                }else {
                    //4.2.2拆分新指令且创建调拨
                    jyDo.setWarehouseCode(param.getToWareHouseCode());
                    CreDoByInventoryDto newDto = createAssAndSubOldDoItemQtyZD(param, jyDo, jyDoItem, userDto, newTaskFlag, false,"集约!=转定仓，拆do,creDB,doId:");
                    //创建调拨单
                    String newDBid = createDBOrderZD(param.getToInvRiskFlag(),newDto.getOpsDo().getNum(),param.getAssNum(), param.getModelNo(),param.getQty(),
                            newDto.getOpsDo().getDoSource(),newDto.getOpsDo(),newDto.getOpsDoItem(),param.getToInvId(),param.getToInvTableType(),userDto);
                    param.setNewDBDoId(newDBid);
                    param.getResultLog().append(";dbdoId:");
                    param.getResultLog().append(newDBid);
                }
            }
        }
    }

    /**
     * 转定处理pco
     * @param param
     * @param userDto
     * @throws OpsException
     * 1.转定验证
     * 2.调用wms删单接口
     * 3.集约仓=转定仓 绑定新关联关系
     * 4.集约仓 != 转定仓 创建调拨单
     */
    public void handlePCO(ZDPageShowOrderBindInvDto param,UserDto userDto) throws OpsException{
        //1.转定验证
        checkZD(param);
        OpsDo jyDo =  baseDoService.getDoByDoId(param.getDoId());
        if (DoStatusEnum.PART.getStatus().equals(jyDo.getDoStatus())
                || DoStatusEnum.FINISH.getStatus().equals(jyDo.getDoStatus())){
            throw Exceptions.OpsException("物流已作业");
        }
        if(!jyDo.getGatherWarehouseCode().equals(jyDo.getWarehouseCode())){
            throw Exceptions.OpsException("数据异常集约仓不等发货仓");
        }
        OpsPco opsPco = opsPcoService.getPcoByPcoId(param.getPcoId());
        if(Objects.isNull(opsPco)){
            throw Exceptions.OpsException("加工指令不存在");
        }
        OpsDoItem jyDoItem = baseDoService.getDoItemByDoId(param.getDoId());
        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(param.getPcoId(), param.getPcoItem());
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(jyDo, opsPco, null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        boolean newTaskFlag = false;
        if(jyDo.getIsWms()==1){
            newTaskFlag = true;
        }
        if(jyDo.getIsWms()==1){
            //二次处理状态变更
            jyDo.setIsWms(0);
            opsDoService.updateOpsDo(jyDo,userDto);
        }
        if(opsPco.getIsWms()==1){
            //二次处理状态变更
            opsPcoService.updatetOpsPco(opsPco.getId(),0,userDto);
        }
        //变更二次处理状态
        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(),DoWaitTypeEnum.OK.getType(),userDto);

        //3.集约仓=转定仓 绑定新关联关系
        if(jyDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
            //二次处理状态
            String orderFno = param.getOrderId()+"-"+param.getOrderItem();
            if(param.getExceptionOrNot()){
                //创建关联关系
                zdService.createPcoItemInvAddPre(orderFno,param.getToInvRiskFlag(), param.getPcoId(), param.getAssNum(),param.getQty(), param.getToInvTableType(), param.getToInvId(), userDto);
                //bugid:12294 c14717 20231019
                taskPCOHandle(newTaskFlag,param,"转定");
            }else {//非二次处理状态
                OpsPcoItemInventory opsPcoItemInventory = opsPcoService.findPcoItemInventoryById(param.getItemInvId());
                baseInventoryService.releasePreQtyInventory(opsPcoItemInventory.getInventoryId(),  opsPcoItemInventory.getUseQty(), opsPcoItemInventory.getInventoryTableType(), userDto);
                //关联新库存，预占库存数量
                opsPcoService.updateOpsPcoItemInv(opsPcoItemInventory.getId(),opsPcoItemInventory.getVersion(),param.getQty(),param.getToInvId(),param.getToInvTableType(),userDto);
                //budid:20641 C14717 20260424 风险判断
                if(Objects.nonNull(param.getToInvRiskFlag()) && param.getToInvRiskFlag()){
                    baseInventoryService.addPreQtyRiskInv(param.getToInvId(), param.getQty(), param.getToInvTableType(), userDto,orderFno);
                }
                baseInventoryService.addPreQtyInventory(param.getToInvId(), param.getQty(), param.getToInvTableType(), userDto);
                //bugid:12294 c14717 20231019
                taskPCOHandle(newTaskFlag,param,"转定");
            }
            param.getResultLog().append("变更关联系");
        }
        //4.集约仓 != 转定仓 创建调拨单
        if(!jyDo.getGatherWarehouseCode().equals(param.getToWareHouseCode())){
            //创建调拨单
            jyDo.setWarehouseCode(param.getToWareHouseCode());
            String newDBid= createDBOrderZD( param.getToInvRiskFlag(),param.getNum(),param.getAssNum(), param.getModelNo(),param.getQty(),
                    jyDo.getDoSource(),jyDo,jyDoItem,param.getToInvId(),param.getToInvTableType(),userDto);
            //修改wati_type = DB
            opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(),DoWaitTypeEnum.WaitDB.getType(), userDto);
            //非二次处理删除关联关系，释放预占库存
            if(!param.getExceptionOrNot()){
                //释放预占
                opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(param.getItemInvId(), userDto);
                baseInventoryService.releasePreQtyInventory(param.getInvId(),  param.getQty(), param.getInvTableType(), userDto);
            }
            param.setNewDBDoId(newDBid);
            param.getResultLog().append("创建db:");
            param.getResultLog().append(newDBid);
        }
    }

    // wms删单接口
    public CommonResult<List<JSONObject>> cancelWMSApi(OpsDo opsDo, OpsPco opsPco,List<OpsDo> doList, String msg) throws OpsException{

        List<CancelDocOrderDto> canList = new ArrayList<>();// 调拨出库报文
        if(Objects.nonNull(opsDo)){
            String type= Objects.requireNonNull(DoTypeEnum.getType(opsDo.getDoType())).getWltype();
            CancelDocOrderDto canDoDto = new CancelDocOrderDto(opsDo.getId(),opsDo.getWarehouseCode(),opsDo.getDoId(),type,msg);
            canList.add(canDoDto);
        }
        if(Objects.nonNull(opsPco)){
            CancelDocOrderDto canPcoDto = new CancelDocOrderDto(opsPco.getId(),opsDo.getWarehouseCode(),opsPco.getPcoId(),"KT",msg);
            canList.add(canPcoDto);
        }
        if(CollectionUtils.isNotEmpty(doList)){
            for(OpsDo oDo : doList){
                String type= Objects.requireNonNull(DoTypeEnum.getType(oDo.getDoType())).getWltype();
                CancelDocOrderDto canDoDto = new CancelDocOrderDto(oDo.getId(),oDo.getWarehouseCode(),oDo.getDoId(),type,msg);
                canList.add(canDoDto);
            }
        }
        return opsCallWmsFeignApi.cancelDocOrder(canList);// 查看是否可删除
    }

    // 拆分新指令，扣减旧doItem数量，处理task
    public CreDoByInventoryDto createAssAndSubOldDoItemQtyZD(ZDPageShowOrderBindInvDto param,OpsDo jyDo,OpsDoItem jyDoItem,UserDto userDto,
                               boolean newTaskFlag,boolean newItemInvFlag,String logMsg) throws OpsException{
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(param.getOrderId(), param.getOrderItem(), DoTypeEnum.JYCK);
        CreDoByInventoryDto newDto = createAssQtyDo(OrderIDFormatEnum.DO_ZD_FORMAT.getFormat(),
                opsDoMaxOne.getNum()+1,opsDoMaxOne.getAssNum(), jyDo, jyDoItem, param.getQty(), userDto,
                DoTypeEnum.JYCK,DoWaitTypeEnum.OK.getType(),DoSourceEnum.ASSQTY);
        //更改旧的doItem qty
        opsDoService.updateDoItemQtyByDoItemId(jyDoItem.getId(),  jyDoItem.getQty() - param.getQty(),jyDoItem.getVersion());
        if(newItemInvFlag){
            OpsDoItemInventory  doItemInventory = zdService.initOpsDoItemInventory(param.getQty(),newDto.getOpsDo().getDoId(),
                    param.getToInvId(),param.getToInvTableType(),userDto);
            List<OpsDoItemInventory> itemInventorys = new ArrayList<>();
            itemInventorys.add(doItemInventory);
            newDto.setItemInventorys(itemInventorys);
        }
        //持久化do
        wmOrderByInventoryDto.getDolist().add(newDto);
        // 风险在库
        Map<Long, Boolean> riskMap = null;
        if(Objects.nonNull(param.getToInvRiskFlag()) && param.getToInvRiskFlag()){
            riskMap = new HashMap<>();
            riskMap.put(param.getToInvId(), true);
        }
        opsDoService.CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,riskMap);
        //bugid:12294 c14717 20231019
        if(!CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(jyDo.getDlvEntire())){
            taskDoHandle(newTaskFlag,param.getDoId(),"转定");
        }
        //非二次处理状态
        if(!param.getExceptionOrNot()){
            //删除doIteInv 释放预占数量
            opsDoService.deleteDoItemInventoryByPrimaryKeySelective(param.getItemInvId(),  userDto);
            baseInventoryService.releasePreQtyInventory(param.getInvId(),  param.getQty(), param.getInvTableType(), userDto);
        }
        param.setAssNewJYDoId(newDto.getOpsDo().getDoId());
        param.setAssNewNum(newDto.getOpsDo().getNum());
        param.getResultLog().append(logMsg);
        param.getResultLog().append(newDto.getOpsDo().getDoId());
        return newDto;
    }


    // 加减预占变更关联关系
    public void updateDoItemInvSubAndAddInvPre(ZDPageShowOrderBindInvDto param, UserDto userDto ) throws OpsException{
        //修改发货仓和关联关系
        OpsDoItemInventory doItemInventory = baseDoService.getDoItemInventoryById(param.getItemInvId());
        //释放预占数量
        baseInventoryService.releasePreQtyInventory(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), userDto);
        //关联新库存，预占库存数量
        doItemInventory.setInventoryId(param.getToInvId());
        doItemInventory.setInventoryTableType(param.getToInvTableType());
        opsDoService.updateOpsDoItemInventory(doItemInventory,userDto);
        //bugid:20641 C14717 20260424 风险判断
        if(Objects.nonNull(param.getToInvRiskFlag()) && param.getToInvRiskFlag()){
            String orderFno = param.getOrderId()+"-"+param.getOrderItem();
            baseInventoryService.addPreQtyRiskInv(param.getToInvId(), param.getQty(), param.getToInvTableType(), userDto,orderFno);
        }
        baseInventoryService.addPreQtyInventory(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), userDto);

    }

    // 处理task 修改或创建Rotask
    public void taskRoHandle(String roId,String msg){
        OpsWmOrderTask wmTaskPo = wmOrderTaskService.createWmTaskPo(roId, WmOrderTaskEnum.RO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.READ,new CreateInfoDto(msg), DateUtil.getNow(),0,msg);
        wmOrderTaskService.addOpsWmOrderTask(wmTaskPo);

    }
    // 处理task 修改或创建dotask
    public void taskDoHandle(boolean newTaskFlag,String doId,String msg){
        if(newTaskFlag){
            // 转定重新计算
            OpsWmOrderTask wmTaskPo = wmOrderTaskService.createWmTaskPo(doId, WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto(msg), DateUtil.getNow(),0,msg);
            wmOrderTaskService.addOpsWmOrderTask(wmTaskPo);
        }else {
            wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(doId, msg, 3);
        }
    }

    // 处理task 修改或创建pcotask
    public void taskPCOHandle(boolean newTaskFlag,ZDPageShowOrderBindInvDto param, String msg){
        if(newTaskFlag){
            // 转定重新计算
            List<OpsWmOrderTask> taskList = new ArrayList<>();
            OpsWmOrderTask wmTaskPco = wmOrderTaskService.createWmTaskPo(param.getPcoId(), WmOrderTaskEnum.PCO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto(msg), DateUtil.getNow(),0,msg);
            OpsWmOrderTask wmTaskDo = wmOrderTaskService.createWmTaskPo(param.getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.READ,new CreateInfoDto(msg), DateUtil.getNow(),0,msg);
            taskList.add(wmTaskDo);
            taskList.add(wmTaskPco);
            wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        }else {
            wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(param.getPcoId(), "转定", 3);
        }
    }


    // 转定构造持久化db
    public String createDBOrderZD(Boolean toRiskInvFlag,Integer num,Integer assNum, String modelNo, Integer qty, String doSourceEnum,
                                 OpsDo opsDo, OpsDoItem opsDoItem,
                                 Long toInvId,String invTableType , UserDto userDto) throws OpsException {

        List<OpsDo> dos = baseWMOrderService.getDos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum(), opsDo.getAssNum());
        List<OpsDo> dbList = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK);
        //排序 降序
        int newExtNum = 0;
        if(CollectionUtils.isNotEmpty(dbList)){
            dbList.sort(Comparator.comparing(OpsDo::getExtNum,Comparator.reverseOrder()));
            newExtNum = dbList.get(0).getExtNum() + 1;
        }
        String dbckid = String.format(OrderIDFormatEnum.DBC_FN1_FORMAT.getFormat(), opsDo.getOrderId(),
                String.format("%03d", (Integer.parseInt(opsDo.getOrderItem()))), String.format("%03d", num), String.format("%03d", assNum), String.format("%03d", newExtNum));
        String dbrkid = String.format(OrderIDFormatEnum.DBR_FN1_FORMAT.getFormat(), opsDo.getOrderId(),
                String.format("%03d", (Integer.parseInt(opsDo.getOrderItem()))), String.format("%03d", num), String.format("%03d", assNum), String.format("%03d", newExtNum));
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        //生成doItem
        OpsDoItem copyDoItem = BeanCopyUtil.copy(opsDoItem, OpsDoItem.class);
        copyDoItem.setId(null);
        copyDoItem.setDoId(dbckid);
        copyDoItem.setModelno(modelNo);
        copyDoItem.setQty(qty);
        List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
        OpsDoItemInventory doItemInventory = zdService.initOpsDoItemInventory( qty, dbckid,toInvId,invTableType , userDto);
        doItemInventoryList.add(doItemInventory);
        OpsDo copyDo = BeanCopyUtil.copy(opsDo, OpsDo.class);
        copyDo.setId(null);
        copyDo.setDoId(dbckid);
        copyDo.setNum(num);
        copyDo.setAssNum(assNum);
        copyDo.setExtNum(newExtNum);
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
            copyDo.setWlDate(DateUtil.getNow());
        }
        copyDo.setDoSource(doSourceEnum);
        copyDo.setDoType(DoTypeEnum.DBCK.getType());
        copyDo.setCarried("");// 指定承运商
        copyDo.setWaitType(DoWaitTypeEnum.OK.getType());
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(opsDo.getGatherWarehouseCode());
        if (Objects.isNull(opsWarehouse)) {
            throw Exceptions.OpsException("不存在此仓库号：" + opsDo.getGatherWarehouseCode());
        }
        //bugid:11445 c14717 2023/07/26 仓库多地址
        opsWarehouseService.getMultAdressSetAddress(opsWarehouse,copyDo.getDoType());
        copyDo.setProvince(opsWarehouse.getProvince());
        copyDo.setCity(opsWarehouse.getCity());
        copyDo.setDistrict(opsWarehouse.getDistrict());
        copyDo.setStreet(opsWarehouse.getAdress());
        copyDo.setAddress(opsWarehouse.getAdress());
        copyDo.setLinkman(opsWarehouse.getLinkman());
        if (StringUtils.isNotBlank(opsWarehouse.getLinkMobile())) {//手机号
            if (StringPhoneUtil.isMobil(opsWarehouse.getLinkMobile())) {
                copyDo.setMobile(opsWarehouse.getLinkMobile());
            }
        }
        if (StringUtils.isNotBlank(opsWarehouse.getLinkPhone())) {
            //是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(opsWarehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(opsWarehouse.getLinkPhone())) {
                copyDo.setPhone(opsWarehouse.getLinkPhone());
            }
        }
        copyDo.setPostcode(opsWarehouse.getPostCode());
        wmOrderByInventoryDto.getDolist().add(new CreDoByInventoryDto(copyDo, copyDoItem, doItemInventoryList));
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId(dbrkid);
        opsRo.setRoSource("");
        opsRo.setOrderId(opsDo.getOrderId());
        opsRo.setOrderItem(opsDo.getOrderItem());
        opsRo.setNum(num);
        opsRo.setAssNum(assNum);
        opsRo.setExtNum(newExtNum);
        opsRo.setRoType(RoTypeEnum.DBRK.getType());
        opsRo.setWarehouseCode(opsDo.getGatherWarehouseCode());
        opsRo.setRoStatus(0);// 初始
        opsRo.setTransType("");// 运输方式
        opsRo.setCarried("");// 到货承运商
        opsRo.setExpressCode("");// 到货承运商
        opsRo.setCustomerNo(opsDo.getCustomerNo());
        opsRo.setInvoiceNo("");// 到货发票号
        opsRo.setSupplierId("");// 到货供应商
        opsRo.setDelflag(0);
        opsRo.setCreator(userDto.getUserName());
        opsRo.setCreTime(DateUtil.getNow());
        List<OpsRoItem> roItemList = new ArrayList<>();
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(dbrkid);
        roItem.setRoItem(1);
        roItem.setQty(qty);
        roItem.setModelno(modelNo);
        roItem.setFromInventoryId(toInvId);
        roItem.setFromInventoryTableType(invTableType);
        roItem.setVersion(0);
        roItem.setDelflag(0);
        roItem.setCreator(userDto.getUserName());
        roItem.setCreTime(DateUtil.getNow());
        roItem.setGreenCode(opsDoItem.getGreenCode());
        roItemList.add(roItem);
        wmOrderByInventoryDto.getRoList().add(new CreRoByInventoryDto(opsRo, roItemList, null));
        Map<Long, Boolean> riskMap = null ;
        if(Objects.nonNull(toRiskInvFlag) && toRiskInvFlag){
            riskMap = new HashMap<>();
            riskMap.put(toInvId, true);
        }
        zdService.persistentLogisticsDoc(riskMap,wmOrderByInventoryDto, userDto);//持久化物流单据
        return dbckid;
    }

    //六、拆分do 复制do 和doItem 返回
    @Override
    public CreDoByInventoryDto createAssQtyDo(String formatDoId, Integer num, Integer assNum, OpsDo oldOpsDo,
                                              OpsDoItem oldOpsDoItem, int qty, UserDto userDto,
                                              DoTypeEnum doTypeEnum, String waitType, DoSourceEnum doSourceEnum) throws OpsException {
        String newDoId = String.format(formatDoId, oldOpsDo.getOrderId(),
                String.format("%03d", Integer.parseInt(oldOpsDo.getOrderItem())),
                String.format("%03d", num), String.format("%03d", assNum));
        // 调库出库TKCK的分纳，OrderItem是四位
        if (DoTypeEnum.TKCK.getType().equalsIgnoreCase(doTypeEnum.getType())) {
            newDoId = WmOrderNoFactory.TKCK_FN(oldOpsDo.getOrderId(), Integer.parseInt(oldOpsDo.getOrderItem()), num, assNum);
        }
        OpsDoItem copyOpsDoItem = BeanCopyUtil.copy(oldOpsDoItem, OpsDoItem.class);
        copyOpsDoItem.setDoId(newDoId);
        copyOpsDoItem.setQty(qty);
        copyOpsDoItem.setId(null);

        OpsDo copyDo = BeanCopyUtil.copy(oldOpsDo, OpsDo.class);
        copyDo.setId(null);
        copyDo.setDoId(newDoId);
        copyDo.setNum(num);
        copyDo.setDoSource(doSourceEnum.getType());
        copyDo.setDoType(doTypeEnum.getType());
        copyDo.setWaitType(waitType);
        return new CreDoByInventoryDto(copyDo, copyOpsDoItem, null);
    }


    /**
     * 七、删采购后调用删采购相关指令 ； 于旧方法同名
     * @param orderNo
     * @param itemNo
     * @param splitItemNo
     * @param requestpurchaseList
     * @param cancelSourceEnum
     * @return
     * @throws OpsException
     * 1.无move
     *  1.1.请购状态 根据单号删指令
     *  1.2.非请购状态记录异常
     * 2.move表关联关系删除指令
     *
     * 3.删除p状态move
     */
    @Override
    public List<OrderInventoryQueryDto> delPoSoDelDoNew(String orderNo, Integer itemNo, Integer splitItemNo,
                                                        List<OpsRequestpurchase> requestpurchaseList,
                                                        PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException{
        List<OrderInventoryQueryDto> resutlList = new ArrayList<>();
        //0.查move表
        List<OpsInventoryMove> opsInventoryMoveList = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitItemNo, null);
        //1.无move
        if(CollectionUtils.isEmpty(opsInventoryMoveList)){
            //bugid:12991 c14717 20240112
            //1.1.请购状态 根据单号删指令 删po无move且来源为请购单删单页面删除对应指令操作
            if(PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE.getType().equals(cancelSourceEnum.getType()) ){
                if(requestpurchaseList.size() ==1 ){
                    delPoSoDelDoNotItemInv(requestpurchaseList.get(0), resutlList);
                    return resutlList;
                }
                //记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_ReqPO_SIZE_ONE.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_ReqPO_SIZE_ONE.getDesc(),""
                        , "", 0,"",""
                        , "", "", "");
                return resutlList;
            }else {
                //1.2.非请购状态记录异常 todo 后续去掉异常记录 记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO_NOT_MOVE.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_PO_NOT_MOVE.getDesc(),""
                        , "", 0,"",""
                        , "", "", "");
                return resutlList;
            }
        }
        //2.move表关联关系删除指令
        for (OpsInventoryMove invMove : opsInventoryMoveList) {
            //2.1 move表数据验证
            if (!"0".equals(invMove.getSourceType())){
                continue;
            }
            if(!InventoryStatusEnum.PRODUCE.getCode().equals(invMove.getInventoryStatus())){
                // 记录异常
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO_HAVE_NOT_P.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", orderNo, itemNo.toString() ) , DoConfirmErrorCodeEnum.DEL_PO_HAVE_NOT_P.getDesc(),""
                        , "", 0,"",invMove.getInventoryId().toString()
                        , invMove.getInventoryStatus(), "", "");
                continue;
            }
            //2.2 处理do
            List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
                OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
                if (Objects.nonNull(opsDo)) {
                    if(opsDo.getIsWms() == 1) {
                        // 增加下发状态保护记录异常表 记录异常表
                        opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                opsDo.getDoId(), "", 0, opsDo.getWarehouseCode(),
                                doItemInventory.getInventoryId().toString(), "", "", "");
                        continue;
                    }
                    OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
                    initOrderInventoryQueryDto(dto, orderNo, itemNo, splitItemNo, opsDo.getOrderId(),
                            opsDo.getOrderItem(), opsDo.getNum(), "");
                    if (!DoWaitTypeEnum.WaitCG.getType().equals(opsDo.getWaitType())) {
                        dto.setSortNo(doItemInventory.getSortnum());
                    }
                    //是否拆分 整单 还原重跑用
                    dto.setDelDo(false);
                    CancelForOrderDto inputDto = new CancelForOrderDto(opsDo.getOrderId(),opsDo.getOrderItem(),opsDo.getDoType(), UserDto.ADMIN,"采购取消");
                    Long cancelId = opsDoService.insertOrderCancel(inputDto);
                    // 处理db
                    if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {//调拨出库
                        List<OpsDo> dos = baseWMOrderService.getDos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
                        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource())) {//全数量出
                            //删jydo
                            List<OpsDo> jydos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK, opsDo.getNum());
                            if(CollectionUtils.isNotEmpty(jydos)){
                                OpsDo opsDoJYCK = jydos.get(0);
                                //已下发指令不删
                                if(opsDoJYCK.getIsWms() == 1) {
                                    // 增加下发状态保护记录异常表 记录异常表
                                    opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                            "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                            opsDoJYCK.getDoId(), "", 0, opsDoJYCK.getWarehouseCode(),
                                            doItemInventory.getInventoryId().toString(), "", "", "");
                                    continue;
                                }
                                //删除交易出库
                                delDo(opsDoJYCK, cancelId, DoTypeEnum.JYCK,"采购取消");
                                dto.setDelDo(true);
                            }

                        } else if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {//调拨单为拆分型号
                            //pco变更waitType=Exception
                            List<OpsPco> pcos = baseWMOrderService.getPcos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
                            if(CollectionUtils.isEmpty(pcos)){
                                throw Exceptions.OpsException("无pco数据");
                            }
                            OpsPco opsPco = pcos.get(0);
                            List<OpsPcoItem> pcoItems = baseWMOrderService.getPcoItem(opsPco.getPcoId(), opsDo.getAssNum());
                            if(CollectionUtils.isEmpty(pcoItems)){
                                throw Exceptions.OpsException("无pcoItem数据");
                            }
                            OpsPcoItem opsPcoItem = pcoItems.get(0);
                            dto.setModelno(opsPcoItem.getSubModelno());
                            //已下发加工单状态不改
                            if(opsPco.getIsWms() == 1) {
                                // 增加下发状态保护记录异常表 记录异常表
                                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                        "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                        "", "", 0, opsPco.getWarehouseCode(),
                                        doItemInventory.getInventoryId().toString(), "", opsPco.getPcoId(), opsPcoItem.getPcoItem().toString());
                                continue;
                            }
                            opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                        } else {
                            //交易出库单 do变更waitType=Exception
                            List<OpsDo> jydos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK, opsDo.getNum());
                            if(CollectionUtils.isNotEmpty(jydos)){
                                OpsDo opsDoJYCK = jydos.get(0);
                                //已下发指令状态不改
                                if(opsDoJYCK.getIsWms() == 1) {
                                    // 增加下发状态保护记录异常表 记录异常表
                                    opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                            "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                            opsDoJYCK.getDoId(), "", 0, opsDoJYCK.getWarehouseCode(),
                                            doItemInventory.getInventoryId().toString(), "", "", "");
                                    continue;
                                }
                                //1.创建新的do
                                opsDoService.updateDoToException(opsDoJYCK.getId());
                            }
                        }
                        //删除调拨do
                        delDo(opsDo, cancelId, DoTypeEnum.DBCK,"采购取消");
                        //删除调拨ro
                        delDBRo(opsDo, cancelId,RoTypeEnum.DBRK);
                        resutlList.add(dto);
                    } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {//交易出库
                        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {//全数量出
                            //删除交易出库
                            dto.setDelDo(true);
                            delDo(opsDo, cancelId, DoTypeEnum.JYCK,"采购取消");
                        } else {//非全数量出 do的waitType = exception
                            //1.创建新的do
                            opsDoService.updateDoToException(opsDo.getId());
                            opsDoService.deleteDoItemInventoryByPrimaryKeySelective(doItemInventory.getId(),UserDto.ADMIN);
                            baseInventoryService.releasePreQtyInventory(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), UserDto.ADMIN);
                        }
                        resutlList.add(dto);
                    }else if(DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())){
                        //删除调拨do
                        delDo(opsDo, cancelId, DoTypeEnum.CGDBCK,"采购取消");
                        //删除调拨ro
                        delDBRo(opsDo, cancelId,RoTypeEnum.CGDBRK);
                    }
                }
            }
            //处理pco 变更waitType = Exception
            List<OpsPcoItemInventory> pcoInvList = opsPcoService.getOpsPcoItemInventoryByInventoryId(invMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
            for (OpsPcoItemInventory item : pcoInvList) {
                OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(item.getPcoId(), item.getPcoItem());
                OpsPco opsPco = opsPcoService.selectPcoBypcoId(opsPcoItem.getPcoId());
                if (Objects.nonNull(opsPco)) {
                    if(opsPco.getIsWms() == 1) {
                        // 增加下发状态保护记录异常表 记录异常表
                        opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_PO.getCode(), 0,
                                "", "", DoConfirmErrorCodeEnum.DEL_PO.getDesc(),
                                "", "", 0, "",
                                item.getInventoryId().toString(), "", item.getPcoId(), item.getPcoItem().toString());
                        continue;
                    }
                    OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
                    initOrderInventoryQueryDto(dto, orderNo, itemNo, splitItemNo, opsPco.getOrderId(),
                            opsPco.getOrderItem(), opsPcoItem.getPcoItem(), "");
                    if (!DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())) {
                        dto.setSortNo(item.getSortnum());
                    }
                    dto.setDelDo(false);
                    dto.setModelno(opsPcoItem.getSubModelno());
                    resutlList.add(dto);
                    opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                    opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(item.getId(),UserDto.ADMIN);
                    baseInventoryService.releasePreQtyInventory(item.getInventoryId(), item.getUseQty(), item.getInventoryTableType(), new UserDto());
                }
            }
        }
        //3.删除剩余在途库存 bugid 6393 c14717 20221122
        int res = delInvMoveByPo(orderNo, itemNo, splitItemNo, InventoryStatusEnum.PRODUCE.getCode());
        if (res == 2) {
            throw Exceptions.OpsException("delete库存更新异常ID");
        }
        return resutlList;

    }

    /**
     * 1.1.请购状态 根据单号删指令
     * @param requestPurchase
     * @param resutlList
     * @throws OpsException
     */
    public void delPoSoDelDoNotItemInv(OpsRequestpurchase requestPurchase, List<OrderInventoryQueryDto> resutlList) throws OpsException{
        OrderInventoryQueryDto dto = new OrderInventoryQueryDto();
        dto.setDelDo(false);
        dto.setModelno(requestPurchase.getModelno());
        // 验证是否有关联关系
        boolean invFlag = false;
        // 根据单号获取do指令
        List<OpsDo> dos = baseWMOrderService.getDos(requestPurchase.getOrderno(), requestPurchase.getItemno().toString());
        if(CollectionUtils.isNotEmpty(dos)){
            int num = 0;
            if(Objects.nonNull(requestPurchase.getSplititemno())){
                num = requestPurchase.getSplititemno();
            }
            // 初始化返回值
            initOrderInventoryQueryDto(dto, requestPurchase.getOrderno(), requestPurchase.getItemno(), requestPurchase.getSplititemno(),
                    requestPurchase.getOrderno(), requestPurchase.getItemno().toString(), num, "");
            //1.1.1.整型号采购 非拆分型号
            if(WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())){
                //1.1.1.1.处理交易do
                List<OpsDo> jydos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK, num);
                if(CollectionUtils.isNotEmpty(jydos)){
                    OpsDo opsDo = jydos.get(0);
                    List<OpsDoItemInventory> doItemInv = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
                    if(CollectionUtils.isEmpty(doItemInv)){
                        // 验证无关联关系才处理do
                        List<OpsDo> dbs = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK, num);
                        if(CollectionUtils.isEmpty(dbs)){
                            //处理do
                            dto.setSplitNo(opsDo.getNum());
                            if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {//全数量出
                                //删除交易出库
                                dto.setDelDo(true);
                                CancelForOrderDto inputDto = new CancelForOrderDto(opsDo.getOrderId(),opsDo.getOrderItem(),opsDo.getDoType(), UserDto.ADMIN,"采购取消");
                                Long cancelId = opsDoService.insertOrderCancel(inputDto);
                                delDo(opsDo, cancelId, DoTypeEnum.JYCK,"采购取消");
                            } else {//非全数量出
                                //更新状态为异常状态
                                opsDoService.updateDoToException(opsDo.getId());
                            }
                            resutlList.add(dto);
                        }
                        //存在调拨单
                        if(CollectionUtils.isNotEmpty(dbs)){
                            invFlag = true;
                        }
                    }else { // 有关联关系
                        invFlag = true;
                    }
                }
                //1.1.1.2.处理采购调拨
                List<OpsDo> cgDBdos = baseWMOrderService.getDos(dos, DoTypeEnum.CGDBCK, 0);
                if(CollectionUtils.isNotEmpty(cgDBdos)){
                    OpsDo cgdo = cgDBdos.get(0);
                    List<OpsDoItemInventory> doDBItemInv = baseDoService.getDoItemInventoryByDoId(cgdo.getDoId());
                    // 验证无关联关系才处理do
                    if(CollectionUtils.isEmpty(doDBItemInv)){
                        //删除调拨do
                        CancelForOrderDto inputDto = new CancelForOrderDto(cgdo.getOrderId(),cgdo.getOrderItem(),cgdo.getDoType(), UserDto.ADMIN,"采购取消");
                        Long cancelId = opsDoService.insertOrderCancel(inputDto);
                        delDo(cgdo, cancelId, DoTypeEnum.CGDBCK,"采购取消");
                        //删除调拨ro
                        delDBRo(cgdo, cancelId,RoTypeEnum.CGDBRK);
                    }else {
                        invFlag = true;
                    }
                }
            } else if(WMPurchaseTagEnum.ASS.getType().equals(requestPurchase.getWmtag())){
                //1.1.2.拆分型号采购
                // 查看pcoItem 关键点：num=1 默认不拆的那个指令 不拆p
                List<OpsPco> pcos = baseWMOrderService.getPcos(requestPurchase.getOrderno(), requestPurchase.getItemno().toString(), 1);
                if(CollectionUtils.isNotEmpty(pcos)){
                    OpsPco opsPco = pcos.get(0);
                    List<OpsPcoItem> pcoItems = baseWMOrderService.getPcoItem(opsPco.getPcoId(), requestPurchase.getSplititemno());
                    if(CollectionUtils.isEmpty(pcoItems)){
                        throw Exceptions.OpsException("无pcoItem数据");
                    }
                    OpsPcoItem opsPcoItem = pcoItems.get(0);
                    List<OpsPcoItemInventory> pcoItemInvs = baseWMOrderService.getPcoItemInv(opsPco.getPcoId(), requestPurchase.getSplititemno());
                    // 验证无关联关系才处理do
                    if(CollectionUtils.isEmpty(pcoItemInvs)){
                        List<OpsDo> dbs = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK, opsPco.getNum(),opsPcoItem.getPcoItem());
                        if(CollectionUtils.isEmpty(dbs)){
                            opsPcoService.updatePcoItemToExcepiton(opsPcoItem.getId());
                            resutlList.add(dto);
                        }else {// 存在调拨单
                            invFlag = true;
                        }
                    }else {
                        invFlag = true;
                    }
                }
            }
            //有关联关系要记录异常
            if(invFlag) {
                opsDoService.saveDOopsExceptionHandle("delPoSoDelDo",DoConfirmErrorCodeEnum.DEL_ReqPO_HAVE_ITEM_INV.getCode(), 0,
                        "",String.format("orderno: %s itemNo %s", requestPurchase.getOrderno(), requestPurchase.getItemno().toString() ) ,
                        DoConfirmErrorCodeEnum.DEL_ReqPO_HAVE_ITEM_INV.getDesc(),""
                        , "", 0,"",""
                        , "", "", "");
            }
        }
    }
    private void initOrderInventoryQueryDto(OrderInventoryQueryDto dto, String orderNo, Integer itemNo, Integer splitItemNo,
                                            String orderId, String orderItem, Integer SplitNo, String OrderFullNo) {
        dto.setPoNo(orderNo);
        dto.setPoItemNo(itemNo);
        dto.setPoSplitNo(splitItemNo);
        dto.setOrderId(orderId);
        dto.setOrderItem(orderItem);
        dto.setSplitNo(SplitNo);
        dto.setOrderFullNo(OrderFullNo);
    }

    // 考虑ext_num
    public void delDBRo(OpsDo opsDo, Long cancelId,RoTypeEnum roTypeEnum) throws OpsException {
        List<OpsRo> opsRos = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                opsDo.getNum(), opsDo.getAssNum(),opsDo.getExtNum(),roTypeEnum.getType());
        if (org.springframework.util.CollectionUtils.isEmpty(opsRos)) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
        }
        if (opsRos.size() > 1) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
        }
        opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), "采购取消", UserDto.ADMIN);
    }

    public void delDo(OpsDo opsDo, Long cancelId, DoTypeEnum doTypeEnum, String msg) throws OpsException {
        wmOrderTaskService.delWmOrderTask(opsDo.getDoId(), msg);
        opsDoService.CancelDo(opsDo.getId(), opsDo.getDoId(), opsDo.getOrderId(), opsDo.getOrderItem(), UserDto.ADMIN);
        opsDoService.insertOrderCancelItem(cancelId, opsDo.getDoId(), doTypeEnum.getType(), "取消成功", UserDto.ADMIN);
    }

    //并发异常3次处理
    public int delInvMoveByPo(String orderNo, Integer itemNo, Integer splitItemNo,
                              String inventoryStatus) throws OpsException {
        //重试次数读取配置文件3次
        for (int i = 0; i < opsCommonConfig.getCoInvRetryFrequency(); i++) {
            //删除剩余在途库存
            try {
                List<OpsInventoryMove> lastOpsInventoryMoveList = baseInventoryService.findInventoryMoveByPo(orderNo, itemNo, splitItemNo, InventoryStatusEnum.PRODUCE);
                for (OpsInventoryMove invMove : lastOpsInventoryMoveList) {
                    baseInventoryService.deleteInventoryMoveNotPreQty(invMove.getInventoryId(), invMove.getVersion(), UserDto.ADMIN);
                }
                //处理成功
                return 1;
            } catch (OpsException e) {
                log.error("采购删单,取消物流指令", e.getMessage());
            }
        }
        //处理失败抛出异常
        return 2;
    }


    /**
     * 八、采购异仓收货 原方法 ：handROChangeDoWarehouseCodeByCGDo
     * @param cgDo
     * @param pcoId
     * @param pcoItem
     * @throws OpsException
     * 1.写日志表
     * 2.处理tkck
     * 3.处理cgdbck
     * 4.处理非7位单仓集约
     * 5.处理7位单仓集约
     * 6.修改货期
     */
    @Override
    public void cgDiffWarehouseModifyDO(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException{
        //1.写日志表
        writeLog(cgDo.getOrderId(),cgDo.getOrderItem(),"变更交易出库单" + cgDo.getDoId() + " " + cgDo.getWarehouseCode(),cgDo.getDoId());
        //2.处理tkck bugid：12563 调库单修改仓库代码 2023-12-06 C12961
        if (DoTypeEnum.TKCK.getType().equals(cgDo.getDoType())){
            cgDiffWaTKCK(cgDo);
            return;
        }
        //3.处理cgdbck
        if (DoTypeEnum.CGDBCK.getType().equals(cgDo.getDoType())) {//采购补库调拨直接修改仓库号 此处必须有do
            cgDiffWaCGDBCK(cgDo);
            return;
        }
        //4.处理非7位单仓集约
        if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())//随到随发
                || CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(cgDo.getDlvEntire())) {//随发分批
            cgDiffWaMultIntensive(cgDo,pcoId,pcoItem);
        } else {//整单单仓货齐 必须整单集约
            //5.处理7位单仓集约
            cgDiffWaSingleWaIntensive(cgDo,pcoId,pcoItem);
        }
        //6.修改货期
        updateDoPcoWlday(cgDo.getOrderId(),cgDo.getOrderItem(),null,true,UserDto.ADMIN);
    }

    //1.写日志表
    @Override
    public void writeLog(String orderId, String orderItem, String result, String params){
        OpsOrderUpdateLog updateLog = new OpsOrderUpdateLog();
        updateLog.setOrderid(orderId);
        updateLog.setOrderItem(Integer.parseInt(orderItem));
        updateLog.setCreateTime(DateUtil.getNow());
        updateLog.setParams(params);
        updateLog.setResult(result);
        opsOrderUpdateLogMapper.insertSelective(updateLog);
    }

    /**
     * 2.处理tkck
     * @param cgDo
     * @throws OpsException
     * 2.1 修改do仓库号
     * 2.2 修改ro仓库号
     * 2.3 修改transOrder仓库号
     * 2.4 异仓创建task
     */
    public void cgDiffWaTKCK(OpsDo cgDo) throws OpsException{
        //更新do的warehouse为签收仓
        OpsDo updateDo = new OpsDo();
        updateDo.setId(cgDo.getId());
        updateDo.setWarehouseCode(cgDo.getWarehouseCode());
        //判断transFlag==0,则更新do的gather_warehouse为签收仓
        TransOrder transOrder = transOrderService.getTransOrderByTransNo(cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()));
        if(transOrder==null){
            throw Exceptions.OpsException("查询不到transOrder==>{}-{}", cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()));
        }
        Boolean transFlag = transOrder.getTransFlag();
        if (transFlag != null && !transFlag) {
            updateDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
            cgDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
            // 修改ro的仓库号
            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()),
                    cgDo.getNum(), cgDo.getAssNum(),cgDo.getExtNum(), RoTypeEnum.TKRK.getType());
            if (CollectionUtils.isNotEmpty(roList)) {
                OpsRo ro = roList.get(0);
                //2.2 修改ro仓库号
                baseRoService.updateOpsRoWarehouse(ro.getRoId(), ro.getVersion(), cgDo.getGatherWarehouseCode(), "异仓调库改入库仓");
            }
        }
        updateDo.setModifyTime(DateUtil.getNow());
        //2.1 修改do仓库号
        opsDoService.updateDoByDo(updateDo);
        // 2.3 修改transOrder仓库号
        transOrderService.updateWarehouse(transOrder.getId(), cgDo.getWarehouseCode(), cgDo.getGatherWarehouseCode());

        // 2.4 异仓创建task
        if (!cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
            // 如果是易仓调拨，则生成task下发
            // 创建task前先查询是否已经存在，如果存在则不创建
            OpsWmOrderTask task = wmOrderTaskService.createWmTaskPo(cgDo.getDoId(), WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER, SendStatusEnum.SUSPENDED, new CreateInfoDto("异仓调库"), com.sales.ops.common.until.DateUtil.getNow(), 0, "");
            Integer taskFlag = wmOrderTaskService.getTaskFlag(task.getWmOrderId());
            if (taskFlag == null) {
                wmOrderTaskService.addOpsWmOrderTask(task);
            }
        }
    }

    /**
     * 3.处理cgdbck
     * @param cgDo
     * @throws OpsException
     * 3.1 删db
     * 3.2 修改等待类型
     */
    public void cgDiffWaCGDBCK(OpsDo cgDo) throws OpsException{
        //bugid:9708 c14717 20230222 常州仓升仓位物流中心 ，可以直接接收采购收货 调拨单的ro_id 和 上架类型标记不用考虑
        if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
            //3.1 删db
            //删除表
            CancelForOrderDto inputDto = new CancelForOrderDto(cgDo.getOrderId(),cgDo.getOrderItem(),cgDo.getDoType(), UserDto.ADMIN,"收货仓变更");
            Long cancelId = opsDoService.insertOrderCancel(inputDto);
            //删除调拨入
            delDBRo(cgDo, cancelId, RoTypeEnum.CGDBRK);
            //删除调拨出
            delDo(cgDo,cancelId,DoTypeEnum.CGDBCK,"收货仓变更");
        } else {
            //3.2 修改等待类型
            udpateDOWarehouse(cgDo,false);
        }
    }

    /**
     * 4.处理非7位单仓集约 多仓集约
     * @param cgDo
     * @param pcoId
     * @param pcoItem
     * @throws OpsException
     * 4.1 pco删db,变更关联
     * 4.2 do删db,变更关联
     * 4.3 变更发货仓
     * 4.4 变更发货仓
     * 4.5 do删db,变更关联
     * 4.6 do删db,变更关联
     * 4.7 pco创建db
     * 4.8 do创建db
     * 4.9 修改集约仓和发货仓
     * 4.10 修改集约仓和发货仓
     */
    public void cgDiffWaMultIntensive(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException{
        if (DoTypeEnum.DBCK.getType().equals(cgDo.getDoType())) {
            if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
                if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                    //4.1 pco删db,变更关联
                    pcoDelDBChangeInv(cgDo,pcoId,pcoItem);
                } else {
                    //4.2 do删db,变更关联
                    doDelDBChangeInv(cgDo);
                }
            } else {//仓库号不一致需要修改仓库号
                //1.加工单继续调拨 修改仓库号
                if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {//拆分继续调拨
                    //4.3 变更发货仓
                    udpateDOWarehouse(cgDo,false);
                } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {//拆分继续调拨
                    if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                            || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())) {
                        //4.4 变更发货仓
                        udpateDOWarehouse(cgDo,false);
                    } else {
                        //4.5 do删db,变更关联
                        doDelDBChangeInv(cgDo);
                    }
                } else {//2.其他直接修改交易单出库仓库号
                    //4.6 do删db,变更关联
                    doDelDBChangeInv(cgDo);
                }
            }
        } else if (DoTypeEnum.JYCK.getType().equals(cgDo.getDoType())) {
            if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
                throw Exceptions.OpsException("异常收货do表仓库号一致，不属于异仓收货,请联系it人员；warehouseCode:" + cgDo.getWarehouseCode() + "-doid:" + cgDo.getDoId());
            }
            //修改等待类型
            if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {//拆分生成调拨单
                //4.7 pco创建db
                pcoCreateDB(cgDo,pcoId,pcoItem);
            } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {//拆分生成调拨单
                if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                        || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())) {
                    //4.8 do创建db
                    doCreateDB(cgDo);
                } else {//随发分批 修改集约仓和发货仓
                    //4.9 修改集约仓和发货仓
                    udpateDOWarehouse(cgDo,true);
                }
            } else {//全数量采购 多仓 随发 随发分批 直接发客户
                //4.10 修改集约仓和发货仓
                udpateDOWarehouse(cgDo,true);
            }
        }
    }
    //单仓集约

    /**
     * 5.处理7位单仓集约
     * @param cgDo
     * @param pcoId
     * @param pcoItem
     * @throws OpsException
     * 5.1 pco删db,变更关联
     * 5.2 do删db,变更关联
     * 5.3 变更发货仓
     * 5.4 pco创建db
     * 5.5 do创建db
     */
    public void cgDiffWaSingleWaIntensive(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException{
        //是否是调拨单
        if (DoTypeEnum.DBCK.getType().equals(cgDo.getDoType())) {
            if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
                if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                    //5.1 pco删db,变更关联
                    pcoDelDBChangeInv(cgDo,pcoId,pcoItem);
                } else {
                    //5.2 do删db,变更关联
                    doDelDBChangeInv(cgDo);
                }
            } else {//仓库号不一致需要修改仓库号
                //5.3 变更发货仓
                udpateDOWarehouse(cgDo,false);
            }
        } else if (DoTypeEnum.JYCK.getType().equals(cgDo.getDoType())) {
            if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
                throw Exceptions.OpsException("异常收货do表仓库号一致，不属于异仓收货,请联系it人员；warehouseCode:" + cgDo.getWarehouseCode() + "-doid:" + cgDo.getDoId());
            }
            //修改等待类型
            if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                //5.4 pco创建db
                pcoCreateDB(cgDo,pcoId,pcoItem);
            } else {
                //5.5 do创建db
                doCreateDB(cgDo);
            }
        }
    }
    // pco删db,变更关联
    public void pcoDelDBChangeInv(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException{
        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.OK.getType(), new UserDto("ADMIN", ""));
        //2.删除db单
        CancelForOrderDto inputDto = new CancelForOrderDto(cgDo.getOrderId(),cgDo.getOrderItem(),cgDo.getDoType(), UserDto.ADMIN,"收货仓变更");
        Long cancelId = opsDoService.insertOrderCancel(inputDto);
        //删除调拨do 关联交易出库单或加工单doItemInventory
        opsDoService.delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, opsPcoItem, null);
        //删除调拨ro
        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK);
    }
    // do删db,变更关联
    public void doDelDBChangeInv(OpsDo cgDo) throws OpsException{
        List<OpsDo> dos = baseWMOrderService.getDos(cgDo.getOrderId(), cgDo.getOrderItem(), cgDo.getNum());
        List<OpsDo> jyckDos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(jyckDos)){
            throw Exceptions.OpsException("无交易单");
        }
        OpsDo jyckDo = jyckDos.get(0);
        opsDoService.updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, cgDo.getWarehouseCode(), cgDo.getWarehouseCode(),
                DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), "admin", 0, null);
        //2.删除db单
        CancelForOrderDto inputDto = new CancelForOrderDto(cgDo.getOrderId(),cgDo.getOrderItem(),cgDo.getDoType(), UserDto.ADMIN,"收货仓变更");
        Long cancelId = opsDoService.insertOrderCancel(inputDto);
        //删除调拨do 关联doItemInventory
        opsDoService.delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, null, jyckDo);
        //删除调拨ro
        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK);
    }
    // pco创建db
    public void pcoCreateDB(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException{
        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.WaitDB.getType(), new UserDto("ADMIN", ""));
        //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
        createDBOrderDiff(cgDo, opsPcoItem, new UserDto("admin"));
    }
    // do创建db
    public void doCreateDB(OpsDo cgDo) throws OpsException{
        opsDoService.updateDoWaitTypeAndWareHouseCodeForId(cgDo.getId(), null, cgDo.getGatherWarehouseCode(), cgDo.getWarehouseCode(),
                DoWaitTypeEnum.WaitDB.getType(), cgDo.getVersion(), "admin", 0, null);
        //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
        createDBOrderDiff(cgDo, null,new UserDto("admin"));
    }
    // 变更发货仓
    public void udpateDOWarehouse(OpsDo cgDo,boolean gatherWarehouseCode){
        OpsDo updateDo = new OpsDo();
        updateDo.setId(cgDo.getId());
        updateDo.setWarehouseCode(cgDo.getWarehouseCode());
        updateDo.setModifyTime(DateUtil.getNow());
        if(gatherWarehouseCode){
            updateDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
            updateDo.setDoId(cgDo.getDoId());
            updateDo.setOrderId(cgDo.getOrderId());
            updateDo.setOrderItem(cgDo.getOrderItem());
            updateDo.setNum(cgDo.getNum());
            updateDo.setDoSource(cgDo.getDoSource());
        }
        opsDoService.updateDoByDo(updateDo);
        //bugid:9805 c14717 20230228
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, cgDo);
    }
    // 创建db
    public void createDBOrderDiff(OpsDo cgDo, OpsPcoItem opsPcoItem,UserDto userDto) throws OpsException {
        log.info("创建调拨单");
        String dbckid = "";
        String dbrkid = "";

        if (Objects.isNull(opsPcoItem)) {
            dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", cgDo.getAssNum()));
            dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", cgDo.getAssNum()));

        } else {
            dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", opsPcoItem.getPcoItem()));
            dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", opsPcoItem.getPcoItem()));
        }
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(cgDo.getDoId());
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        //生成调拨单
        opsDoService.initOpsDBDoAndRo(dbckid, dbrkid, cgDo, opsDoItem, opsPcoItem, wmOrderByInventoryDto);
        opsDoService.createDo(wmOrderByInventoryDto.getDolist().get(0).getOpsDo(), wmOrderByInventoryDto.getDolist().get(0).getOpsDoItem(), null, new UserDto("采购仓库变更", "0.0.0.0"));
        opsRoService.insertRo(wmOrderByInventoryDto.getRoList().get(0).getOpsRo(), wmOrderByInventoryDto.getRoList().get(0).getOpsRoItemList().get(0), null, new UserDto("采购仓库变更", "0.0.0.0"));

        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        OpsWmOrderTask opsWmOrderTaskDO = new OpsWmOrderTask();
        opsWmOrderTaskDO.setWmOrderId(dbckid);
        opsWmOrderTaskDO.setWmOrderType(WmOrderTaskEnum.DO.getType());
        opsWmOrderTaskDO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskDO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
        opsWmOrderTaskDO.setCreator(userDto.getUserName());
        opsWmOrderTaskDO.setCreTime(DateUtil.getNow());
        taskList.add(opsWmOrderTaskDO);
        OpsWmOrderTask opsWmOrderTaskRO = new OpsWmOrderTask();
        opsWmOrderTaskRO.setWmOrderId(dbrkid);
        opsWmOrderTaskRO.setWmOrderType(WmOrderTaskEnum.RO.getType());
        opsWmOrderTaskRO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskRO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
        opsWmOrderTaskRO.setCreator(userDto.getUserName());
        opsWmOrderTaskRO.setCreTime(DateUtil.getNow());
        taskList.add(opsWmOrderTaskRO);
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
    }

    /**
     * 通知发货-页面获取list;
     */
    @Override
    public  PageInfo<NotifyShipmentPlanPageDto> findNotifyShipmentPlanPage(PageModel<NotifyShipmentPlanPageDto> pageModel){
        // 页面获取列表
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(), pageModel.getOrderBy());
        List<NotifyShipmentPlanPageDto> notifyShipmentPlanList = notifyShipmentPlanDao.findNSForPage(pageModel.getCondition());
        return new PageInfo<>(notifyShipmentPlanList);
    }

    @Override
    public  List<NotifyShipmentPlanPageDto> findNotifyShipmentPlanListExport(NotifyShipmentPlanExportPageDto param){
        // 导出获取列表
        return notifyShipmentPlanDao.findNotifyShipmentPlanListExport(param);
    }

    @Override
    public  List<NotifyShipmentPlanPageDto> findNotifyShipment(NotifyShipmentFindParam param){
        // 导出获取列表
        return notifyShipmentPlanDao.findNS(param);
    }
    @Override
    public List<NotifyShipmentPlanPageDto> findNotifyShipment(String wmOrderId){
        return notifyShipmentPlanDao.findNotifyShipmentPlanByWmOrderId(wmOrderId);
    }

    @Override
    public List<NotifyShipmentPlanDto> findNotifyShipmentPlanList(String orderId, String orderItem) {
        return notifyShipmentPlanDao.findNotifyShipmentPlanList(orderId, orderItem);
    }

    /**
     * 订单还原重置计划
     * @param orderId
     * @param orderItem
     */
    @Override
    public void releaseNotify(String orderId, String orderItem, String msg){
        List<NotifyShipmentPlanDto> noList = findNotifyShipmentPlanList(orderId, orderItem);
        for(NotifyShipmentPlanDto obj : noList){
            String planNo = obj.getPlanNo();
            notifyShipmentPlanDao.updateNotifyShipmentRealse(planNo,msg);
            notifyShipmentPlanDao.updateNotifyShipmentItemDel(planNo,msg);
        }
    }

    /**
     * 通知发货-子表
     * @param planNo
     * @return
     */
    @Override
    public List<NotifyShipmentPlanItemPageDto> findNotifyShipmentPlanItemItem(String planNo){
        return notifyShipmentPlanDao.findNotifyShipentPlanItemPageList(planNo);
    }

    /**
     * 通知发货-页面根据单号号，客户获取生成 物流指定货期
     * @param hopeDate
     * @param orderId
     * @param orderItem
     * @throws OpsException
     */
    @Override
    public Date getNotifyShipmentWlDate(Date hopeDate, String orderId, String orderItem) throws OpsException{
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem);
        List<OpsDo> doJYList = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(doJYList)){
            throw Exceptions.OpsException("无单据，无法生成物流货期");
        }
        OpsDo jyckDo = doJYList.get(0);
        UpdateForOrderDto dto = new UpdateForOrderDto();
        dto.setDlvDate(hopeDate);
        // 获取物流指定货期 仓库到营业所的配置时间或当前时间
        calcDeliveryTime(dto, hopeDate, jyckDo);
        return dto.getWmsDlvDate();
    }

    /**
     * 页面新增验证
     * @param orderFno
     * @return
     * @throws OpsException
     */
    @Override
    public NotifyShipmentVerifyDto getNotifyShipmentVerifyForPage(String orderFno) throws OpsException{
        //1.获取订单数据 用于验证订单型号和数量
        NotifyShipmentPlanDto param = new NotifyShipmentPlanDto();
        param.setOrderFno(orderFno);
        NotifyShipmentVerifyDto notifyShipmentVerifyDto = getNotifyShipmentVerifyDto(param);
        //2.获取计划
        NotifyShipmentCondition condition = new NotifyShipmentCondition(param.getOrderId(),param.getOrderItem(),"");
        NotifyShipmentVerifyAssDto planInfo = getNotifyShipmentPlan(condition);
        //3.获取在库 可绑定指令
        List<NotifyShipmentAllowSplitDto> allowSplitOrderList = getAllowBindOrder(condition, planInfo);
        //4.获取就绪数量
        if(notifyShipmentVerifyDto.getAssChildFlag()){
            // 子型号发货
            for(NotifyShipmentVerifyItemDto obj : notifyShipmentVerifyDto.getItems()){
                for(NotifyShipmentAllowSplitDto allObj : allowSplitOrderList){
                    if(obj.getModelNo().equals(allObj.getModelNo())){
                        obj.setReadyQty(allObj.getAllowSplitQty());
                    }
                }
            }
        }else {
            //非子型号发货
            int sum = allowSplitOrderList.stream().mapToInt(NotifyShipmentAllowSplitDto::getAllowSplitQty).sum();
            for(NotifyShipmentVerifyItemDto obj : notifyShipmentVerifyDto.getItems()){
                obj.setReadyQty(sum);
            }
        }




        return notifyShipmentVerifyDto;
    }

    /**
     * 通知发货-获取订单数据 用于验证
     * 1.获取子型号和子型号数量及标识
     * 2.获取订单数量及订单型号
     * 3.获取订单已计划数量
     * 4.获取子型号已计划数量
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public NotifyShipmentVerifyDto getNotifyShipmentVerifyDto(NotifyShipmentPlanDto param) throws OpsException{

        NotifyShipmentVerifyDto resultDto = new NotifyShipmentVerifyDto();
        //查rcv和do
        Rcvdetail rcvdetail = baseWMOrderService.findRcvDetailByFno(param.getOrderFno());
        param.setOrderId(rcvdetail.getRorderNo());
        param.setOrderItem(rcvdetail.getRorderItem().toString());
        //验证是否是通知发货
        Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(rcvdetail.getRorderNo());
        if(!CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire())){
            throw Exceptions.OpsException("发货方式需通知发货");
        }
        if(rcvdetail.getStatus() > RcvOrderStatusEnum.CKING.getType() || RcvOrderStatusEnum.INIT.getType() == rcvdetail.getStatus()){
            throw Exceptions.OpsException("当前状态不可计划");
        }
        List<OpsDo> dos = baseWMOrderService.getDos(rcvdetail.getRorderNo(), rcvdetail.getRorderItem().toString());
        List<OpsDo> doJYList = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(doJYList)){
            throw Exceptions.OpsException("无单据");
        }
        // 1.获取子型号和子型号数量
        //返回型号列表
        List<NotifyShipmentVerifyItemDto> doItems = new ArrayList<>();
        if(OrderSpecExpType.include(rcvdetail.getExpDlvType(), OrderSpecExpType.AssChildToExport)){
            //子型号发货标识
            resultDto.setAssChildFlag(true);
            //modelno ,qty,
            HashMap<String, Integer> assModelMap = new HashMap<>();
            for(OpsDo opsDo : doJYList){
                OpsDoItem doItem = baseWMOrderService.getDoItem(opsDo.getDoId());
                if(assModelMap.containsKey(doItem.getModelno())){
                    Integer qty = assModelMap.get(doItem.getModelno());
                    assModelMap.put(doItem.getModelno(),doItem.getQty() + qty);
                }else {
                    assModelMap.put(doItem.getModelno(),doItem.getQty());
                }
            }
            if(!assModelMap.isEmpty()){
                for (String modelNo : assModelMap.keySet()) {
                    NotifyShipmentVerifyItemDto item = new NotifyShipmentVerifyItemDto(modelNo, assModelMap.get(modelNo));
                    doItems.add(item);
                }
            }
        }else {
            NotifyShipmentVerifyItemDto item = new NotifyShipmentVerifyItemDto(rcvdetail.getModelNo(), rcvdetail.getQuantity());
            doItems.add(item);
        }
        // 获取doItem实体列表
        resultDto.setItems(doItems);
        //2.获取订单数量及订单型号
        resultDto.setOrderQty(rcvdetail.getQuantity());
        resultDto.setModelNo(rcvdetail.getModelNo());

        //3.获取订单，型号 已计划数量（包含子型号发货）
        for(NotifyShipmentVerifyItemDto itemDto :resultDto.getItems()){
            Integer planQty = notifyShipmentPlanDao.sumNotifyShipmentPlanQty(rcvdetail.getRorderNo(),rcvdetail.getRorderItem().toString(),itemDto.getModelNo());
            if (Objects.isNull(planQty)){
                planQty = 0;
            }
            itemDto.setPlanQty(planQty);
            if(!resultDto.getAssChildFlag()){
                resultDto.setPlanQty(planQty);
            }
        }
        return resultDto;
    }

    /**
     * 通知发货-验证数据，用于页面创建计划
     * 1.验证已修改子项特发，请刷新页面重试
     * 2.计划无效，数量已超出订单数量
     * @param param
     * @throws OpsException
     */
    @Override
    public void checkNotifyShipment(NotifyShipmentPlanDto param) throws OpsException{
        //验证参数
        if(StringUtils.isEmpty(param.getOrderFno())){
            throw Exceptions.OpsException("订单号空");
        }
        if(StringUtils.isEmpty(param.getModelNo())){
            throw Exceptions.OpsException("型号为空");
        }
        if(Objects.isNull(param.getPlanQty()) || param.getPlanQty() == 0){
            throw Exceptions.OpsException("计划数量应大于0");
        }
        if(Objects.isNull(param.getHopeDate())){
            throw Exceptions.OpsException("期望货期空");
        }
        Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),param.getHopeDate());//计算时间-系统时间
        if(diffDay < 0){
            throw Exceptions.OpsException("期望货期应大于等于当前时间");
        }


        //验证do；
        NotifyShipmentVerifyDto verifyDto = getNotifyShipmentVerifyDto(param);
        // 子型号发货
        if(verifyDto.getAssChildFlag()){
            for(NotifyShipmentVerifyItemDto itemDto : verifyDto.getItems()){
                if (param.getModelNo().equals(itemDto.getModelNo())){
                    if(param.getPlanQty() +  itemDto.getPlanQty() > itemDto.getOrderAssChildQty()){
                        throw Exceptions.OpsException("累加计划已超出订单数量");
                    }
                }
            }
            // 验证型号
            boolean checkModelNo = false;
            for(NotifyShipmentVerifyItemDto itemDto : verifyDto.getItems()){
                if (param.getModelNo().equals(itemDto.getModelNo())){
                    checkModelNo = true;
                    break;
                }
            }
            if(!checkModelNo){
                throw Exceptions.OpsException("订单型号不符");
            }
        }
        // 非子型号发货 //验证数量不超
        if(!verifyDto.getAssChildFlag()){
            if(!param.getModelNo().equals(verifyDto.getModelNo())){
                throw Exceptions.OpsException("订单型号不符");
            }
            if(param.getPlanQty() +  verifyDto.getPlanQty() > verifyDto.getOrderQty()){
                throw Exceptions.OpsException("累加计划已超出订单数量");
            }
        }
    }


    /**
     * 通知发货-验证并持久化通知发货主表
     * @param dto
     * @throws OpsException
     * 1.验证
     * 2.获取通知发货计划单号
     * 3.计算物流指定货期
     * 4.持久化主表
     */
    @Override
    public void createNotifyShipmentPlan(NotifyShipmentPlanDto dto) throws OpsException{

        if(StringUtils.isNotEmpty(dto.getPlanNo())){
            Integer i = notifyShipmentPlanDao.countNotifyShipmentPlan(dto.getPlanNo());
            if(i > 0){
                throw Exceptions.OpsException("计划号已存在");
            }
        }
        //1.验证
        checkNotifyShipment(dto);
        //2.获取通知发货计划单号
        if(StringUtils.isEmpty(dto.getPlanNo())){
            ResultVo<String> resultVo = commonServiceFeignApi.generatorBillNo("42");
            if(Objects.isNull(resultVo) || !resultVo.isSuccess()){
                throw Exceptions.OpsException("生成单号失败");
            }
            dto.setPlanNo(resultVo.getData());
        }
        //3.计算物流指定货期
        Date wlDate = getNotifyShipmentWlDate(dto.getHopeDate(), dto.getOrderId(), dto.getOrderItem());
        dto.setWlDate(wlDate);
        dto.setVersion(0);
        dto.setDelflag(0);
        dto.setCreateTime(DateUtil.getNow());
        //4.持久化主表
        addNotifyShipmentBatchDao.insert(dto);
        //5.拆分指令
        notifyShipmentPlanMatchOrder(new NotifyShipmentCondition(dto.getOrderId(),dto.getOrderItem(),"创建计划"));
    }


    // 通知发货-持久化主表
    public void insertNotifyShipmentPlan(List<NotifyShipmentPlanDto> list){
        Map<Integer, List<NotifyShipmentPlanDto>> map = SplitBatchUtils.getInsertBatchBySqlserver(list, NotifyShipmentPlanDto.class);
        for (Map.Entry<Integer, List<NotifyShipmentPlanDto>> entry : map.entrySet()) {
            addNotifyShipmentBatchDao.batchInsert(list);
        }
    }

    /**
     * 外部调用拆分指令方法
     * 拆分类型
     * 1.通知发货
     * 2.随发分配
     * 3.外部到货 CGDBCK TKCK
     */
    @Override
    public void splitOrderByDlvEntry(NotifyShipmentCondition condition)throws OpsException{
        Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(condition.getOrderId());
        if(Objects.nonNull(rcvMaster)){
            //通知发货
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire())){
                notifyShipmentPlanMatchOrder(condition);
            }
            //随发分配
            if(CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire())){
                createAssDo(condition.getParamDo(),0,new UserDto(condition.getReason()),DoTypeEnum.JYCK, null);
            }
        }else {
            //3.外部到货 CGDBCK TKCK
            if(Objects.nonNull(condition.getParamDo())){
                if(DoTypeEnum.CGDBCK.getType().equalsIgnoreCase(condition.getParamDo().getDoType())){
                    createAssDo(condition.getParamDo(),0,new UserDto(condition.getReason()),DoTypeEnum.CGDBCK, null);
                }
                if(DoTypeEnum.TKCK.getType().equalsIgnoreCase(condition.getParamDo().getDoType())){
                    createAssDo(condition.getParamDo(),0,new UserDto(condition.getReason()),DoTypeEnum.TKCK, null);
                }
            }
        }
    }


    /**
     * 通知发货-1 订单匹配计划 外部调用
     * @param condition 条件
     * @throws OpsException
     * 1.单独拆分在库部分指令 仅拆N的部分
     * 2.拆所有满足计划的指令 包含T（W,T3,T1）和N的部分
     * 3.拆分调拨单
     */
    @Override
    public void notifyShipmentPlanMatchOrder(NotifyShipmentCondition condition) throws OpsException{
        //验证是否是通知发货
        Boolean notifyFlag = checkDlvEntireAssShipment(condition.getOrderId());
        if(!notifyFlag){
            return;
        }
        // 日志
        StringBuilder param = new StringBuilder();
        List<String> beginDoIdList = opsDoPostDao.getDoIdList(condition.getOrderId(), condition.getOrderItem());
        param.append(condition.getReason());
        param.append("@开始订单指令:").append(JSONObject.toJSONString(beginDoIdList));

        //1.单独拆分在库部分指令 仅拆N的部分
        getPlanCountOrderAndBingPlan(condition);
        //2.拆所有满足计划的指令 包含T（W,T3,T1）和N的部分
        condition.setContainMoveFlag(true);
        getPlanCountOrderAndBingPlan(condition);
        //3.拆分调拨单
        notifyShipmentSplitDBOrder(condition.getOrderId(),condition.getOrderItem());
        //日志
        StringBuilder result = new StringBuilder();
        List<String> endDoIdList = opsDoPostDao.getDoIdList(condition.getOrderId(), condition.getOrderItem());
        result.append("结束订单指令:").append(JSONObject.toJSONString(endDoIdList));
        //4.写日志 开始do指令集合,结束指令集合
        writeLog(condition.getOrderId(),condition.getOrderItem(),result.toString(),param.toString());
        //5.写事件
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_NOTIFY_SHIPMENT_PLAN,condition.getOrderId(),Integer.valueOf(condition.getOrderItem()));
    }

    /**
     * 通知发货-2在库部分指令 仅拆N的部分
     * @param condition 基础信息
     *  1.获取可执行计划
     *  2.获取可拆指令集合
     *  3.计划匹配指令
     *  4.指令绑定计划
     *  5.持久化通知发货子计划
     *  6.创建task
     *  7.更新主表状态
     *
     */
    @Override
    public void getPlanCountOrderAndBingPlan(NotifyShipmentCondition condition) throws OpsException{
        //1.获取可执行计划
        NotifyShipmentVerifyAssDto planInfo = getNotifyShipmentPlan(condition);
        //不存在未完成计划
        if(!planInfo.getExistPlan()){
            return;
        }
        //2.获取绑定计划指令集合
        List<NotifyShipmentAllowSplitDto> allowSplitOrderList = getAllowBindOrder(condition, planInfo);
        //3.计划匹配指令
        List<NotifyShipmentPlanMatchOrderDto> matchOrderDtos = planMatchOrder(planInfo, allowSplitOrderList);
        //4.指令绑定计划
        List<NotifyShipmentPlanItemDto> list = new ArrayList<>();
        for(NotifyShipmentPlanMatchOrderDto matchPlan : matchOrderDtos){
            if(CollectionUtils.isNotEmpty(matchPlan.getList())){
                for(NotifyShipmentAllowSplitDto allowSplitDto : matchPlan.getList()){
                    NotifyShipmentPlanItemDto planItemDto = orderBindPlan(matchPlan.getPlanNo(), allowSplitDto, matchPlan.getHopeDate(), matchPlan.getWlDate());
                    list.add(planItemDto);
                }
            }
        }
        //5.持久化通知发货子计划
        insertNotifyShipmentPlanItem(list);
        //6.创建task
        createTask(list);
        //7.更新计划主表状态
        NotifyShipmentVerifyAssDto udpateInfo = getNotifyShipmentPlan(condition);
        if(udpateInfo.getExistPlan()){
            for(NotifyShipmentPlanDto obj : udpateInfo.getPlanList()){
                if(!Objects.equals(NotifyShipmentStatusEnum.FINISH.getCode(), obj.getStatus())){
                    if(obj.getMatchQty() > 0){
                        obj.setStatus(NotifyShipmentStatusEnum.PART.getCode());
                        if(obj.getMatchFinish()){
                            obj.setStatus(NotifyShipmentStatusEnum.FINISH.getCode());
                        }
                        // 更新语句
                        notifyShipmentPlanDao.updateNotifyShipmentStatus(obj.getPlanNo(), obj.getStatus());
                    }
                }
            }
        }
    }
    /**
     * 通知发货-获取计划
     * todo 修改子项特发时验证是否有计划 也可用
     * @param condition
     * @return
     * 1.获取主计划
     * 2.获取子计划
     * 3.获取计划已匹配doId
     * 4.获取计划已匹配数量
     *
     *
     */
    @Override
    public NotifyShipmentVerifyAssDto getNotifyShipmentPlan(NotifyShipmentCondition condition) throws OpsException{
        NotifyShipmentVerifyAssDto result = new NotifyShipmentVerifyAssDto();
        //1.获取主计划
        List<NotifyShipmentPlanDto> notifyList = notifyShipmentPlanDao.findNotifyShipmentPlanList(condition.getOrderId(), condition.getOrderItem());
        if(CollectionUtils.isEmpty(notifyList)){
            //无计划
            return result;
        }
        //有计划
        result.setExistPlan(true);
        //计划主表
        result.setPlanList(notifyList);
        //2.获取子计划
        // <doId,totalPlanQty>
        for(NotifyShipmentPlanDto planDto : notifyList){
            List<NotifyShipmentPlanItemDto> planItemList = notifyShipmentPlanDao.findNotifyShipentPlanItemList(planDto.getPlanNo());
            //计划子表
            result.setPlanItemList(planItemList);
            if(CollectionUtils.isNotEmpty(planItemList)){
                result.setExistPlanItem(true);

                HashMap<String,Integer> planItemMap = new HashMap<>();
                for(NotifyShipmentPlanItemDto itemDto : planItemList){
                    //未分配数量计算 planNo
                    if(planItemMap.containsKey(itemDto.getPlanNo())){
                        Integer qty = planItemMap.get(itemDto.getPlanNo());
                        planItemMap.put(itemDto.getPlanNo(),itemDto.getQty() + qty);
                    }else {
                        planItemMap.put(itemDto.getPlanNo(),itemDto.getQty());
                    }

                    //3.获取计划已匹配doId
                    if(result.getPlanItemMap().containsKey(itemDto.getDoId())){
                        Integer qty = result.getPlanItemMap().get(itemDto.getDoId());
                        result.getPlanItemMap().put(itemDto.getDoId(),itemDto.getQty() + qty);
                    }else {
                        result.getPlanItemMap().put(itemDto.getDoId(),itemDto.getQty());
                    }
                }
                //4.获取计划已匹配数量
                planDto.setMatchQty(planItemMap.get(planDto.getPlanNo()));
            }
        }
        return result;
    }

    /**
     * 通知发货-6.创建task
     * @param list
     * @throws OpsException
     */
    public void createTask(List<NotifyShipmentPlanItemDto> list) throws OpsException{
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        List<OpsWmOrderTask> wmTaskPoList = new ArrayList<>();
        for(NotifyShipmentPlanItemDto dto : list){
            if(StringUtils.isEmpty(dto.getPcoId())){
                //初始化do task flag=3
                OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(dto.getDoId()  , WmOrderTaskEnum.DO,
                        WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto("通知发货"), null,0,"");
                wmTaskPoList.add(wmTaskdo);
            }else {
                OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(dto.getDoId(), WmOrderTaskEnum.DO,
                        WmOrderTaskEnum.ORDER,SendStatusEnum.READ,new CreateInfoDto("通知发货"), null,0,"");
                //初始化Pco task flag=3
                OpsWmOrderTask wmTaskPco = wmOrderTaskService.createWmTaskPo(dto.getPcoId(), WmOrderTaskEnum.PCO,
                        WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto("通知发货"), null,0,"");
                wmTaskPoList.add(wmTaskdo);
                wmTaskPoList.add(wmTaskPco);
            }
        }
        //持久化task表
        wmOrderTaskService.addBatchOpsWmOrderTask(wmTaskPoList);

    }
    //5.持久化通知发货子计划
    public void insertNotifyShipmentPlanItem(List<NotifyShipmentPlanItemDto> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        Map<Integer, List<NotifyShipmentPlanItemDto>> map = SplitBatchUtils.getInsertBatchBySqlserver(list, NotifyShipmentPlanItemDto.class);
        for (Map.Entry<Integer, List<NotifyShipmentPlanItemDto>> entry : map.entrySet()) {
            addNotifyShipmentBatchDao.batchInsertItem(list);
        }
    }

    /**
     * 4.指令绑定计划
     * @param planNo
     * @param allowSplitDto
     * @param hopeDate
     * @param wlDate
     * @return 返回子计划实体
     * @throws OpsException
     * 1.不拆do
     * 2.拆do
     *
     */
    public NotifyShipmentPlanItemDto orderBindPlan(String planNo, NotifyShipmentAllowSplitDto allowSplitDto,Date hopeDate, Date wlDate ) throws OpsException{
        String doId = allowSplitDto.getDoId();
        Integer planMatchQty = allowSplitDto.getPlanMatchQty();//do拆分数量
        //bugid:18019 c14717 20250623
        OpsDoItem doItem = baseWMOrderService.getDoItem(doId);
        Integer doItemQty = doItem.getQty();
        NotifyShipmentPlanItemDto planItemDto = new NotifyShipmentPlanItemDto();
        planItemDto.setPlanNo(planNo);
        //1.不拆do，直接绑定计划
        if(doItemQty.equals(planMatchQty)){
            // 修改货期
            UpdateForOrderDto dto = new UpdateForOrderDto();
            dto.setDlvDate(hopeDate);
            dto.setWmsDlvDate(wlDate);
            //通知发货计划子表实体
            planItemDto.build(doId,planMatchQty);
            OpsDo aDo = baseWMOrderService.getDo(doId);
            if(DoSourceEnum.ASSModelNo.getType().equals(aDo.getDoSource()) ){
                List<OpsPco> pcos = baseWMOrderService.getPcos(aDo.getOrderId(), aDo.getOrderItem(), aDo.getNum());
                OpsPco opsPco = pcos.get(0);
                //通知发货计划子表实体
                planItemDto.setPcoId(opsPco.getPcoId());
                //修改pco货期
                opsDoService.updatePcoDeliveryDay(opsPco,dto);
            }
            //修改do货期
            opsDoService.updateDoDeliveryDay(aDo, dto);
        }
        //2.拆do绑定计划
        if(!doItemQty.equals(planMatchQty)){
            splitOrder(doId, planMatchQty, hopeDate, wlDate, planItemDto);
        }
        //3.绑定计划
        return planItemDto;
    }


    /**
     * 通知发货--拆分订单
     * 2.拆do
     * @param doId
     * @param planMatchQty
     * @param hopeDate
     * @param wlDate
     * @return
     * @throws OpsException
     */
    public void splitOrder(String doId, Integer planMatchQty,Date hopeDate, Date wlDate,NotifyShipmentPlanItemDto planItem) throws OpsException{
        //指令集合
        OpsDo aDo = baseWMOrderService.getDo(doId);
        OpsDoItem doItem = baseWMOrderService.getDoItem(doId);
        List<OpsDoItemInventory> doItemInv = baseWMOrderService.getDoItemInv(doId);
        OpsPcoAndItemAndItemInventoryDto pcoDto = baseWMOrderService.getPcoDto(aDo.getOrderId(), aDo.getOrderItem(), aDo.getNum());

        //1.拆分do a.newDoId b.newNum c.newAssNum d.newRelation |
        OpsDo newDo = splitDo(aDo, hopeDate, wlDate);
        //2.拆分doItem a.newDoId b.newQty  | 修改旧doItem qty
        OpsDoItem newDoItem = splitDoItem(doItem, newDo.getDoId(), planMatchQty);
        //3.拆分doItemInv a.绑定新doItemInvId; | 修改旧
        List<OpsDoItemInventory> doItemInventoryList = splitDoItemInv(doItemInv, newDo.getDoId(), planMatchQty);
        // 持久化指令
        opsDoService.createDo(newDo,newDoItem,doItemInventoryList, UserDto.AUTO);
        if(Objects.nonNull(pcoDto)){
            //4.拆分pco a.newPcoId b.newNum c.newAssNum d.newRelation
            OpsPco newPco = splitPco(pcoDto.getOpsPco(), newDo, hopeDate, wlDate);
            if(Objects.nonNull(planItem)){
                planItem.setPcoId(newPco.getPcoId());
            }
            //5.拆分pcoItem a.newPcoId newPcoItem newQty
            List<OpsPcoItem> newPcoItems = splitPcoItem(pcoDto.getItemList(), newPco.getPcoId(), planMatchQty,doItem.getQty());
            //6.拆分pcoItemInv a.绑定新pcoItemInvId;
            List<OpsPcoItemInventory> opsPcoItemInventories = splitPcoItemInv(pcoDto.getItemInventoryList(), newPco.getPcoId(), newPcoItems);
            //持久化指令
            opsPcoService.crePco(newPco,newPcoItems,opsPcoItemInventories,UserDto.AUTO);
        }
        if(Objects.nonNull(planItem)){
            planItem.build(newDo.getDoId(),planMatchQty);
        }

    }


    /**
     * doItemInv 排序
     * @param list
     */
    @Override
    public List<OpsDoItemInventory> sortDoItemInvListV1(List<OpsDoItemInventory> list){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        //减少数据库交互
        HashMap<Long,OpsInventoryMove> map = new HashMap<>();
        for(OpsDoItemInventory obj : list){
            if(InventoryTableTypeEnum.TRANS.getType().equals(obj.getInventoryTableType())){
                OpsInventoryMove move = opsInventoryMoveMapper.selectByPrimaryKey(obj.getInventoryId());
                if(Objects.nonNull(move)){
                    map.put(obj.getInventoryId(),move);
                }
            }
        }
        list.sort(Comparator.comparing(OpsDoItemInventory::getInventoryTableType)//比较 N和T
                .thenComparing((o1,o2) -> {//比较 W5 W4 T3 T1 P
                    if(InventoryTableTypeEnum.TRANS.getType().equals(o2.getInventoryTableType())){
                        if(!map.containsKey(o1.getInventoryId()) && !map.containsKey(o2.getInventoryId())){
                            return 0;
                        }else if(!map.containsKey(o1.getInventoryId()) ){
                            return -1;
                        }
                        OpsInventoryMove move1 = map.get(o1.getInventoryId());
                        OpsInventoryMove move2 = map.get(o2.getInventoryId());
                        InvIndexStatusEnum enumByType1 = InvIndexStatusEnum.getEnumByType(move1.getInventoryStatus(),move1.getOptStatus());
                        InvIndexStatusEnum enumByType2 = InvIndexStatusEnum.getEnumByType(move2.getInventoryStatus(),move2.getOptStatus());
                        // 排序 W5 W4 T3 T1 P
                        return enumByType2.getSort().compareTo(enumByType1.getSort());
                    }
                    return 0;
                })//比较预到货日期
                .thenComparing((o1,o2) -> {
                    if(InventoryTableTypeEnum.TRANS.getType().equals(o2.getInventoryTableType())){
                        if(!map.containsKey(o1.getInventoryId()) && !map.containsKey(o2.getInventoryId())){
                            return 0;
                        }else if(!map.containsKey(o1.getInventoryId()) ){
                            return -1;
                        }
                        if(InvIndexStatusEnum.CGTRANS.getCode().equals(map.get(o1.getInventoryId()).getInventoryStatus())){
                            OpsInventoryMove move1 = map.get(o1.getInventoryId());
                            OpsInventoryMove move2 = map.get(o2.getInventoryId());
                            if(move1.getPrereceivedate() == null || move2.getPrereceivedate() == null){
                                if(move1.getPrereceivedate() == null && move2.getPrereceivedate() == null){
                                    return 0;
                                } else if(move1.getPrereceivedate() == null){
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }else {
                                return move1.getPrereceivedate().compareTo(move2.getPrereceivedate());
                            }
                        }
                    }
                    return 0;
                })//比较数量
                .thenComparing(OpsDoItemInventory::getUseQty,Comparator.reverseOrder()));
        return list;
    }

    @Override
    public List<OpsPcoItemInventory>  sortPcoItemInvListV1(List<OpsPcoItemInventory> list){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        //减少库存交货
        HashMap<Long,OpsInventoryMove> map = new HashMap<>();
        for(OpsPcoItemInventory obj : list){
            if(InventoryTableTypeEnum.TRANS.getType().equals(obj.getInventoryTableType())){
                OpsInventoryMove move = opsInventoryMoveMapper.selectByPrimaryKey(obj.getInventoryId());
                if(Objects.nonNull(move)){
                    map.put(obj.getInventoryId(),move);
                }
            }
        }
        list.sort(Comparator.comparing((OpsPcoItemInventory::getPcoItem))//pcoItem
                .thenComparing(OpsPcoItemInventory::getInventoryTableType)//N T
                .thenComparing((o1,o2) -> {//比较 W5 W4 T3 T1 P
                    if(InventoryTableTypeEnum.TRANS.getType().equals(o2.getInventoryTableType())){
                        if(!map.containsKey(o1.getInventoryId()) && !map.containsKey(o2.getInventoryId())){
                            return 0;
                        }else if(!map.containsKey(o1.getInventoryId()) ){
                            return -1;
                        }
                        OpsInventoryMove move1 = map.get(o1.getInventoryId());
                        OpsInventoryMove move2 = map.get(o2.getInventoryId());
                        InvIndexStatusEnum enumByType1 = InvIndexStatusEnum.getEnumByType(move1.getInventoryStatus(),move1.getOptStatus());
                        InvIndexStatusEnum enumByType2 = InvIndexStatusEnum.getEnumByType(move2.getInventoryStatus(),move2.getOptStatus());
                        // 排序W5 W4 T3 T1 P
                        return enumByType2.getSort().compareTo(enumByType1.getSort());
                    }
                    return 0;
                })//比较预到货日期
                .thenComparing((o1,o2) -> {
                    if(InventoryTableTypeEnum.TRANS.getType().equals(o2.getInventoryTableType())){
                        if(!map.containsKey(o1.getInventoryId()) && !map.containsKey(o2.getInventoryId())){
                            return 0;
                        }else if(!map.containsKey(o1.getInventoryId()) ){
                            return -1;
                        }
                        if(InvIndexStatusEnum.CGTRANS.getCode().equals(map.get(o1.getInventoryId()).getInventoryStatus())){
                            OpsInventoryMove move1 = map.get(o1.getInventoryId());
                            OpsInventoryMove move2 = map.get(o2.getInventoryId());
                            if(move1.getPrereceivedate() == null || move2.getPrereceivedate() == null){
                                if(move1.getPrereceivedate() == null && move2.getPrereceivedate() == null){
                                    return 0;
                                } else if(move1.getPrereceivedate() == null){
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }else {
                                return move1.getPrereceivedate().compareTo(move2.getPrereceivedate());
                            }
                        }
                    }
                    return 0;
                })//比较数量
                .thenComparing(OpsPcoItemInventory::getUseQty,Comparator.reverseOrder()));
        return list;
    }

    //1.拆分do a.newDoId b.newNum c.newAssNum d.newRelation e.hopeDate f. | 旧指令不变更
    public OpsDo splitDo(OpsDo opsDo,Date hopeDate, Date wlDate){
        List<OpsDo> dos = baseWMOrderService.getDos(opsDo.getOrderId(), opsDo.getOrderItem());
        List<OpsDo> jydos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        Integer maxNum = jydos.stream().max(Comparator.comparing(OpsDo::getNum)).get().getNum();
        Integer newNum = maxNum + 1;
        OpsDo copy = BeanCopyUtil.copy(opsDo, OpsDo.class);
        if(!DoWaitTypeEnum.WaitJG.getType().equals(copy.getWaitType())){
            copy.setWaitType(DoWaitTypeEnum.OK.getType());
        }
        //如果是整出或采购 num=0 ,doid对应是1 那么 跳过1，直接用2；
        if(newNum == 1){
            newNum = 2;
        }
        String newDoId = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), opsDo.getOrderId(),
                String.format("%03d", Integer.parseInt(opsDo.getOrderItem())),
                String.format("%03d", newNum), String.format("%03d", opsDo.getAssNum()));
        copy.setDoId(newDoId);
        copy.setNum(newNum);
        if(StringUtils.isNotEmpty(opsDo.getRoId())){
            copy.setRelocation(opsDo.getDoId());
        }
        if( StringUtils.isNotEmpty(opsDo.getRelocation())) {
            copy.setRelocation(opsDo.getRelocation());
        }
        if(Objects.nonNull(hopeDate)){
            copy.setHopeDate(hopeDate);
        }
        if(Objects.nonNull(wlDate)){
            copy.setWlDate(wlDate);
        }
        copy.setId(null);
        return copy;


    }
    //2.拆分doItem a.newDoId b.newQty  | 修改旧doItem qty
    public OpsDoItem splitDoItem(OpsDoItem doItem, String newDoId, Integer planMatchQty) throws OpsException{
        OpsDoItem copy = BeanCopyUtil.copy(doItem, OpsDoItem.class);
        copy.setDoId(newDoId);
        copy.setQty(planMatchQty);
        copy.setId(null);
        //修改旧doItem qty
        opsDoService.updateDoItemQtyByDoItemId(doItem.getId(),doItem.getQty() - planMatchQty, doItem.getVersion());
        return copy;

    }
    //3.拆分doItemInv a.绑定新doItemInvId; | 修改旧
    public List<OpsDoItemInventory> splitDoItemInv(List<OpsDoItemInventory> doItemInvList, String newDoId, Integer planMatchQty) throws OpsException{
        if(CollectionUtils.isEmpty(doItemInvList)){
            return null;
        }
        //1.doItemInv排序
        List<OpsDoItemInventory> doItemInvSortList = sortDoItemInvListV1(doItemInvList);
        List<OpsDoItemInventory> list = new ArrayList<>();
        //计算匹配数量
        int planQty = planMatchQty;
        HashMap<Long,Integer> map = new HashMap<>();
        for(OpsDoItemInventory obj : doItemInvSortList){
            if(planQty == 0){
                break;
            }
            if(planQty - obj.getUseQty() > 0){
                map.put(obj.getId(),obj.getUseQty());
                planQty = planQty - obj.getUseQty();
            }else {
                map.put(obj.getId(),planQty);
                planQty = 0;
            }

        }
        //初始化do 修改旧do
        for(OpsDoItemInventory obj : doItemInvSortList){
            if(map.containsKey(obj.getId())){
                Integer matchQty = map.get(obj.getId());
                //初始化新
                OpsDoItemInventory copy = BeanCopyUtil.copy(obj, OpsDoItemInventory.class);
                copy.setId(null);
                copy.setDoId(newDoId);
                copy.setUseQty(matchQty);
                list.add(copy);
                if(matchQty.equals(obj.getUseQty())){
                    //软删旧
                    opsDoService.deleteDoItemInventoryByPrimaryKeySelective(obj.getId(), UserDto.AUTO);
                }else {
                    //修改旧数量
                    opsDoService.updateDoItemInvQtyById(obj.getId(),obj.getUseQty() - matchQty,obj.getVersion());
                }
            }
        }
        return list;
    }
    //4.拆分pco a.newPcoId b.newNum c.newAssNum ,hopeDate, wlDate
    public OpsPco splitPco(OpsPco opsPco, OpsDo newDo,Date hopeDate, Date wlDate){
        String newPcoId = String.format(OrderIDFormatEnum.PCO_FORMAT.getFormat(), opsPco.getOrderId(),
                String.format("%03d", Integer.parseInt(opsPco.getOrderItem())),
                String.format("%03d", newDo.getNum()), String.format("%03d", newDo.getAssNum()));
        OpsPco copy = BeanCopyUtil.copy(opsPco, OpsPco.class);
        copy.setId(null);
        copy.setPcoId(newPcoId);
        copy.setNum(newDo.getNum());
        copy.setAssNum(newDo.getAssNum());
        if(Objects.nonNull(hopeDate)){
            copy.setHopeDate(hopeDate);
        }
        if(Objects.nonNull(wlDate)){
            copy.setWlDate(wlDate);
        }
        return copy;
    }
    //5.拆分pcoItem a.newPcoId newPcoItem newQty d.newRelation
    public List<OpsPcoItem> splitPcoItem(List<OpsPcoItem> pcoItems, String newPcoId, Integer planMatchQty, Integer oldDoItemQty) throws OpsException{
        List<OpsPcoItem> pcoItemList = new ArrayList<>();
        HashMap<String, Integer> pcoItemMap = new HashMap<String, Integer>();
        //计算子型号和整型号数量的倍数关系，计算出子型号出库数量
        for (OpsPcoItem obj : pcoItems) {
            int pcoItemQty = obj.getSubQty();
            int multipleQty = pcoItemQty / oldDoItemQty;
            pcoItemMap.put(obj.getPcoId() + "-" + obj.getPcoItem(), multipleQty * planMatchQty);
        }

        for(OpsPcoItem obj : pcoItems) {
            OpsPcoItem copy = BeanCopyUtil.copy(obj, OpsPcoItem.class);
            copy.setWaitType(DoWaitTypeEnum.OK.getType());
            copy.setId(null);
            copy.setPcoId(newPcoId);
            if (Objects.nonNull(obj.getIsCross()) && obj.getIsCross()) {
                copy.setRelocation(obj.getPcoId());
            }
            if( StringUtils.isNotEmpty(obj.getRelocation())) {
                copy.setRelocation(obj.getRelocation());
            }
            Integer matchPcoItemQty = pcoItemMap.get(obj.getPcoId() + "-" + obj.getPcoItem());
            copy.setSubQty(matchPcoItemQty);
            pcoItemList.add(copy);
            opsPcoService.updatePcoItemQty(obj.getId(),obj.getSubQty()-matchPcoItemQty,UserDto.AUTO);
        }
        return pcoItemList;
    }
    //6.拆分pcoItemInv a.绑定新pcoItemInvId;
    public List<OpsPcoItemInventory> splitPcoItemInv(List<OpsPcoItemInventory> itemInvList, String newPcoId, List<OpsPcoItem> newPcoItems ) throws OpsException{
        List<OpsPcoItemInventory> list = new ArrayList<>();
        HashMap<Integer, Integer> pcoItemMap = new HashMap<Integer, Integer>();
        //计算子型号和整型号数量的倍数关系，计算出子型号出库数量
        for (OpsPcoItem obj : newPcoItems) {
            pcoItemMap.put(obj.getPcoItem(),obj.getSubQty());
        }
        //排序
        List<OpsPcoItemInventory> pcoItemInvSosrtList = sortPcoItemInvListV1(itemInvList);
        //计算匹配数量
        HashMap<Long,Integer> map = new HashMap<>();
        for(OpsPcoItemInventory obj : pcoItemInvSosrtList) {
            Integer planQty = pcoItemMap.get(obj.getPcoItem());
            if(Objects.isNull(planQty) || planQty == 0) {
                continue;
            }
            if(planQty - obj.getUseQty() > 0){
                map.put(obj.getId(),obj.getUseQty());
                planQty = planQty - obj.getUseQty();
                pcoItemMap.put(obj.getPcoItem(),planQty);
            }else {
                map.put(obj.getId(),planQty);
                pcoItemMap.put(obj.getPcoItem(),0);
            }
        }
        //初始化pcoItemInv 修改旧pcoItemInv
        for(OpsPcoItemInventory obj : pcoItemInvSosrtList){
            if(map.containsKey(obj.getId())){
                Integer matchQty = map.get(obj.getId());
                //初始化新
                OpsPcoItemInventory copy = BeanCopyUtil.copy(obj, OpsPcoItemInventory.class);
                copy.setId(null);
                copy.setPcoId(newPcoId);
                copy.setUseQty(matchQty);
                list.add(copy);
                if(matchQty.equals(obj.getUseQty())){
                    //软删旧
                    opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(obj.getId(), UserDto.AUTO);
                }else {
                    //修改旧数量
                    opsPcoService.updatePcoItemInvQty(obj.getId(),obj.getUseQty() - matchQty,UserDto.AUTO);
                }
            }
        }
        //计算waitType 拆出必有关联，都是N则设置为ok
        for (OpsPcoItem obj : newPcoItems) {// DoWaitTypeEnum.WaitDB.getType().
            if(!Objects.equals(DoWaitTypeEnum.OK.getType(),obj.getWaitType())){
                String oldWaitType = obj.getWaitType();
                for(OpsPcoItemInventory invObj : list){
                    obj.setWaitType(DoWaitTypeEnum.OK.getType());
                    if(!Objects.equals(InventoryTableTypeEnum.NORMAL.getType(),invObj.getInventoryTableType())){
                        obj.setWaitType(oldWaitType);
                    }
                }
            }
            pcoItemMap.put(obj.getPcoItem(),obj.getSubQty());
        }

        return list;
    }


    /**
     * 通知发货- 订单匹配计划  计划匹配do planNo 返回doId 匹配数量
     * @param planInfo
     * @param allowSplitOrderList
     * @return
     * 1.循环计划实体
     * 2.循环指令可匹配数量
     * 3.计划匹配订单
     * 4.计划匹配数量（计划匹配指令实体）
     */
    public List<NotifyShipmentPlanMatchOrderDto> planMatchOrder(NotifyShipmentVerifyAssDto planInfo, List<NotifyShipmentAllowSplitDto> allowSplitOrderList){
        if (allowSplitOrderList.isEmpty()){
            return Collections.emptyList();
        }
        // 由大到小排序
        allowSplitOrderList.sort(Comparator.comparing( NotifyShipmentAllowSplitDto::getAllowSplitQty,Comparator.reverseOrder()));
        List<NotifyShipmentPlanMatchOrderDto> list = new ArrayList<>();
        //1.循环计划实体
        for(NotifyShipmentPlanDto plan : planInfo.getPlanList()){
            //计划匹配数量
            Integer planMatchQty = plan.getPlanQty() - plan.getMatchQty();
            //计划匹配指令实体
            NotifyShipmentPlanMatchOrderDto matchOrderDto = new NotifyShipmentPlanMatchOrderDto(plan.getPlanNo(),plan.getHopeDate(),plan.getWlDate());
            //匹配计划数量不等于可分配数量
            //2.循环指令可匹配数量
            for(NotifyShipmentAllowSplitDto obj : allowSplitOrderList){
                //计划数量等于0
                if(planMatchQty <= 0){//避免超约负数
                    break;
                }
                // 子型号特发
                if(!plan.getModelNo().equals(obj.getModelNo())){
                    continue;
                }
                //指令匹配数量等于0
                if(obj.getAllowSplitQty() == 0){
                    continue;
                }
                //3.计划匹配订单
                //计划数量大于允许分配数量
                if(planMatchQty - obj.getAllowSplitQty() > 0){
                    //4.计划匹配数量
                    obj.setPlanMatchQty(obj.getAllowSplitQty());
                    planMatchQty = planMatchQty - obj.getAllowSplitQty();
                    //允许拆分数量清零
                    obj.setAllowSplitQty(0);
                    NotifyShipmentAllowSplitDto newObj = BeanCopyUtil.copy(obj,NotifyShipmentAllowSplitDto.class);
                    matchOrderDto.addList(newObj);

                }else if(planMatchQty - obj.getAllowSplitQty() <= 0){
                    //计划数量小于允许数量
                    //4.计划匹配数量
                    obj.setPlanMatchQty(planMatchQty);
                    //允许拆分数量等于自己减计划数量
                    obj.setAllowSplitQty(obj.getAllowSplitQty() - planMatchQty);
                    NotifyShipmentAllowSplitDto newObj = BeanCopyUtil.copy(obj,NotifyShipmentAllowSplitDto.class);
                    matchOrderDto.addList(newObj);
                    planMatchQty = 0;
                }
            }
            list.add(matchOrderDto);
        }
        return list;
    }

    /**
     * 通知发货-2.获取可绑定指令集合
     * @param condition
     * @param planInfo
     * @return
     * @throws OpsException
     * 1.获取do指令
     * 2.获取pco在库数量
     * 3.获取do在库数量
     * 4.初始化指令可匹配数量
     */
    public List<NotifyShipmentAllowSplitDto> getAllowBindOrder(NotifyShipmentCondition condition, NotifyShipmentVerifyAssDto planInfo) throws OpsException{
        //1.获取do指令
        List<NotifyShipmentAllowSplitDto> list = new ArrayList<>();
        HashMap<String, Integer> planItemMap = planInfo.getPlanItemMap();
        List<OpsDo> dos = baseWMOrderService.getDos(condition.getOrderId(), condition.getOrderItem());
        List<OpsDo> doJYList = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        if(CollectionUtils.isEmpty(doJYList)){
            throw Exceptions.OpsException("无指令单据");
        }
        //获取do可拆数量 <doId,可拆数量>
        for(OpsDo JYDo : doJYList){
            //计划没有匹配的do
            if(planItemMap.containsKey(JYDo.getDoId())){
                continue;
            }
            //已发货，已下发 不可拆
            if(JYDo.getIsWms() == 1
                    || DoStatusEnum.FINISH.getStatus().equals(JYDo.getDoStatus())
                    || DoStatusEnum.PART.getStatus().equals(JYDo.getDoStatus())){
                continue;
            }
            //获取可拆分指令
            Integer allowSplitQty = null;
            if(DoSourceEnum.ASSModelNo.getType().equals(JYDo.getDoSource())){
                //拆分型号标识
                condition.setPcoFlag(true);
                //2.获取pco数量
                allowSplitQty = getAllowSplitPcoItemInv(JYDo, condition.getContainMoveFlag());
            }else {
                //3.获取do数量
                allowSplitQty = getAllowSplitDoItemInv(JYDo, condition.getContainMoveFlag());
            }
            //4.初始化指令可匹配数量
            if(Objects.nonNull(allowSplitQty)){
                OpsDoItem doItem = baseWMOrderService.getDoItem(JYDo.getDoId());
                NotifyShipmentAllowSplitDto allowSplitDto = new NotifyShipmentAllowSplitDto(JYDo.getDoId(),
                        allowSplitQty,doItem.getModelno());
                list.add(allowSplitDto);
            }
        }
        return list;
    }

    /**
     * 通知发货-获取pco可拆分数量
     * @param JYDo 交易单
     * @throws OpsException
     */
    public Integer getAllowSplitPcoItemInv(OpsDo JYDo,Boolean containMoveFlag) throws OpsException{
        //1.拆分型号        
        OpsPcoAndItemAndItemInventoryDto pcoDto = baseWMOrderService.getPcoDto(JYDo.getOrderId(), JYDo.getOrderItem(), JYDo.getNum());
        if(Objects.isNull(pcoDto)){
            throw Exceptions.OpsException("无指令单据");
        }
        HashMap<String,Integer> pcoItemInvMap = new HashMap<String,Integer>();
        if(CollectionUtils.isNotEmpty(pcoDto.getItemInventoryList())){
            for(OpsPcoItemInventory obj : pcoDto.getItemInventoryList()){
                String key = obj.getPcoId()+ "-" +obj.getPcoItem();
                getItemInvAllowQty(key, obj.getInventoryId(), obj.getUseQty(),pcoItemInvMap,obj.getInventoryTableType(),containMoveFlag);
            }
        }
        // itemInv不为空 且 集合数量相等
        if(!pcoItemInvMap.isEmpty() && pcoItemInvMap.size() == pcoDto.getItemList().size()){
            OpsDoItem doItem = baseWMOrderService.getDoItem(JYDo.getDoId());
            int minDoPlanQty = 0;
            //获取最小拆分do的数量
            for (OpsPcoItem obj : pcoDto.getItemList()) {
                int itemQty = obj.getSubQty();
                int doQty = doItem.getQty();
                //计算子型号和整型号数量的倍数关系
                int multipleQty =  itemQty / doQty ;
                String key = obj.getPcoId()+ "-" +obj.getPcoItem();
                Integer itemInvQty = pcoItemInvMap.get(key);
                //计算此pcoItem满足doItem的数量有几个
                int planDoQty = itemInvQty / multipleQty;
                if(minDoPlanQty == 0){
                    minDoPlanQty = planDoQty;
                }
                if(minDoPlanQty !=0 && minDoPlanQty>planDoQty){
                    minDoPlanQty = planDoQty;
                }
            }
            if(minDoPlanQty != 0){                
                return minDoPlanQty;
            }
        }
        return null;
    }
    

    /**
     * 通知发货-获取pco可拆分数量 非拆分型号
     * @param JYDo
     * @throws OpsException
     */
    public Integer getAllowSplitDoItemInv(OpsDo JYDo,Boolean containMoveFlag) throws OpsException{
        List<OpsDoItemInventory> doItemInvs = baseWMOrderService.getDoItemInv(JYDo.getDoId());
        HashMap<String,Integer> doItemMap = new HashMap<>();
        for(OpsDoItemInventory obj : doItemInvs){
            //获取itemInv 可用数量
            getItemInvAllowQty(obj.getDoId(), obj.getInventoryId(), obj.getUseQty(),doItemMap,obj.getInventoryTableType(),containMoveFlag);
        }
        if(doItemMap.containsKey(JYDo.getDoId())){
            return doItemMap.get(JYDo.getDoId());
        }
        return null;
    }

    /**
     * 通知发货-获取itemInv可拆分数量
     * @param key
     * @param invId
     * @param useQty
     * @param map
     * @param invTableType
     * @param containMoveFlag
     */
    public void getItemInvAllowQty(String key, Long invId, Integer useQty, HashMap<String,Integer> map, String invTableType, Boolean containMoveFlag){
        if (InventoryTableTypeEnum.NORMAL.getType().equals(invTableType)) {
            if(map.containsKey(key)){
                Integer qty = map.get(key);
                map.put(key,useQty + qty);
            }else {
                map.put(key,useQty);
            }
        }
        if(containMoveFlag && InventoryTableTypeEnum.TRANS.getType().equals(invTableType)){
            OpsInventoryMove inventoryMove = opsInventoryMoveMapper.selectByPrimaryKey(invId);
            if(Objects.nonNull(inventoryMove)){
                // W5不拆 20240219 c14717 开会确认  20250429 确认拆W5
                /*if(InventoryStatusEnum.W.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())
                        && OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())){
                   return;
                }*/
                if(!InventoryStatusEnum.PRODUCE.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())){
                    if(map.containsKey(key)){
                        Integer qty = map.get(key);
                        map.put(key,useQty + qty);
                    }else {
                        map.put(key,useQty);
                    }
                }
            }
        }
    }

    /**
     * 通知发货- 3 收集拆分调拨单List 后调用splitDBOrder拆分
     * doItemInv.size>1 且 包含一条非N
     * @param orderId
     * @param orderItem
     * @throws OpsException
     */
    @Override
    public void notifyShipmentSplitDBOrder(String orderId, String orderItem)  throws OpsException{
        //1.查指令
        List<OpsDo> dos = baseWMOrderService.getDos(orderId, orderItem);
        List<OpsDo> jydos = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
        List<OpsDo> dbdos = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK);
        List<OpsPco> pcos = baseWMOrderService.getPcos(orderId, orderItem);
        List<OpsDo> splitDbDos = new ArrayList<>();
        //2 waitType = DB
        if(CollectionUtils.isEmpty(pcos)){
            for (OpsDo jydo : jydos){
                if(DoWaitTypeEnum.WaitDB.getType().equals(jydo.getWaitType())){
                    List<OpsDo> dos1 = baseWMOrderService.getDos(dbdos, DoTypeEnum.DBCK, jydo.getNum());
                    splitDbDos.addAll(dos1);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(pcos)){
            for(OpsPco pco : pcos){
                List<OpsPcoItem> pcoItems = baseWMOrderService.getPcoItem(pco.getPcoId(), null);
                for(OpsPcoItem pcoItem : pcoItems){
                    if(DoWaitTypeEnum.WaitDB.getType().equals(pcoItem.getWaitType())){
                        List<OpsDo> dos1 = baseWMOrderService.getDos(dbdos, DoTypeEnum.DBCK, pco.getNum(),pcoItem.getPcoItem());
                        splitDbDos.addAll(dos1);
                    }
                }
            }
        }
        //3.拆分指令
        for(OpsDo db : splitDbDos){
            if(db.getIsWms() == 1
                    || DoStatusEnum.FINISH.getStatus().equals(db.getDoStatus())
                    || DoStatusEnum.PART.getStatus().equals(db.getDoStatus())){
                continue;
            }
            splitDBOrder(db);
        }
    }

    /**
     * 拆分调拨单 （到多少拆多少）
     * 单号不变 变更doId extNum
     * @param opsDo
     * @throws OpsException
     */
    @Override
    public void splitDBOrder(OpsDo opsDo) throws OpsException{
        List<OpsDoItemInventory> doItemInvs = baseWMOrderService.getDoItemInv(opsDo.getDoId());
        int size = doItemInvs.size();
        if(size <=1 ){
            return;
        }
        //0 如果全是N也不用拆
        long normalSize = doItemInvs.stream().filter(o-> StringUtils.equals(InventoryTableTypeEnum.NORMAL.getType(), o.getInventoryTableType())).count();
        if(normalSize == size){
            return;
        }
        //1.排序 顺序是 N W T1 p
        List<OpsDoItemInventory> doItemInventoryList = sortDoItemInvListV1(doItemInvs);
        //2.删除掉最后一个不拆分
        doItemInventoryList.remove(size - 1);
        //3.拆分指令
        for(OpsDoItemInventory itemInv : doItemInventoryList){
            OpsDoItem doItem = baseWMOrderService.getDoItem(opsDo.getDoId());
            //3.1 拆do
            OpsDo newDB = splitDBDo(opsDo);
            //3.2 拆doItemInv
            List<OpsDoItemInventory> newDoItemInvList = splitDBDoItemInv(itemInv, newDB.getDoId());
            //3.3 拆doItem
            OpsDoItem newDoItem = splitDBDoItem(doItem, newDB.getDoId(), itemInv.getUseQty());
            //3.4 持久化指令
            opsDoService.createDo(newDB,newDoItem,newDoItemInvList, UserDto.AUTO);
            //3.5 创建task
            taskDoHandle(true,newDB.getDoId(),"dbAss");

            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                    opsDo.getNum(), opsDo.getAssNum(),opsDo.getExtNum(), RoTypeEnum.DBRK.getType());
            if (CollectionUtils.isNotEmpty(roList)) {
                OpsRo ro = roList.get(0);
                OpsRoItem roItem = baseRoService.findRoItemByRoId(ro.getRoId());
                //3.6 拆分ro
                OpsRo opsNewRo = splieDBRo(ro, newDB.getExtNum());
                //3.7 拆分roItem
                OpsRoItem opsNewRoItem = splitDBRoItem(roItem, opsNewRo.getRoId(), itemInv.getUseQty());
                //3.8 持久化
                opsRoService.insertRo(opsNewRo, opsNewRoItem, null, new UserDto("通知发货"));
                //3.9 创建task
                taskRoHandle(opsNewRo.getRoId(),"dbAss");
            }
        }
    }

    //3.6 拆分ro
    public OpsRo splieDBRo(OpsRo opsRo, Integer newExtNum) throws OpsException{
        OpsRo copy = BeanCopyUtil.copy(opsRo,OpsRo.class);
        String newRoId = String.format(OrderIDFormatEnum.DBR_FN1_FORMAT.getFormat(), opsRo.getOrderId(),
                String.format("%03d", Integer.parseInt(opsRo.getOrderItem())),
                String.format("%03d", opsRo.getNum()), String.format("%03d", opsRo.getAssNum()), String.format("%03d", newExtNum));
        copy.setRoId(newRoId);
        copy.setExtNum(newExtNum);
        copy.setId(null);
        return copy;
    }

    //3.7 拆分roItem
    public OpsRoItem splitDBRoItem(OpsRoItem item,String newRoId, Integer planMatchQty) throws OpsException{
        OpsRoItem copy = BeanCopyUtil.copy(item, OpsRoItem.class);
        copy.setRoId(newRoId);
        copy.setQty(planMatchQty);
        copy.setId(null);
        //修改旧RoItem qty
        opsRoService.updateRoItemQtyByDoItemId(item.getId(),item.getQty() - planMatchQty, item.getVersion());
        return copy;
    }

    //1.拆分调拨do
    public OpsDo splitDBDo(OpsDo opsDo) throws OpsException{
        if (!DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())){
            throw Exceptions.OpsException("无调拨单");
        }
        List<OpsDo> dos = baseWMOrderService.getDos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum(), opsDo.getAssNum());
        List<OpsDo> dbList = baseWMOrderService.getDos(dos, DoTypeEnum.DBCK);
        //排序 降序
        dbList.sort(Comparator.comparing(OpsDo::getExtNum,Comparator.reverseOrder()));
        int newExtNum = dbList.get(0).getExtNum() + 1; //todo
        OpsDo copy = BeanCopyUtil.copy(opsDo, OpsDo.class);
        String newDoId = String.format(OrderIDFormatEnum.DBC_FN1_FORMAT.getFormat(), opsDo.getOrderId(),
                String.format("%03d", Integer.parseInt(opsDo.getOrderItem())),
                String.format("%03d", opsDo.getNum()), String.format("%03d", opsDo.getAssNum()), String.format("%03d", newExtNum));
        copy.setDoId(newDoId);
        copy.setExtNum(newExtNum);
        if(StringUtils.isNotEmpty(opsDo.getRoId())){
            copy.setRelocation(opsDo.getDoId());
        }
        if(StringUtils.isNotEmpty(opsDo.getRelocation())){
            copy.setRelocation(opsDo.getRelocation());
        }
        copy.setId(null);
        return copy;
    }
    //2.拆分调拨doItem a.newDoId b.newQty  | 修改旧doItem qty
    public OpsDoItem splitDBDoItem(OpsDoItem doItem, String newDoId, Integer planMatchQty) throws OpsException{
        OpsDoItem copy = BeanCopyUtil.copy(doItem, OpsDoItem.class);
        copy.setDoId(newDoId);
        copy.setQty(planMatchQty);
        copy.setId(null);
        //修改旧doItem qty
        opsDoService.updateDoItemQtyByDoItemId(doItem.getId(),doItem.getQty() - planMatchQty, doItem.getVersion());
        return copy;

    }
    //3.拆分doItemInv a.绑定新doItemInvId; | 修改旧
    public List<OpsDoItemInventory> splitDBDoItemInv(OpsDoItemInventory obj,String newDoId) throws OpsException{
        List<OpsDoItemInventory> list = new ArrayList<>();
        OpsDoItemInventory copy = BeanCopyUtil.copy(obj, OpsDoItemInventory.class);
        copy.setId(null);
        copy.setDoId(newDoId);
        copy.setUseQty(obj.getUseQty());
        copy.setInventoryId(obj.getInventoryId());
        opsDoService.deleteDoItemInventoryByPrimaryKeySelective(obj.getId(), UserDto.AUTO);
        list.add(copy);
        return list;
    }
}
