package com.sales.ops.serviceimpl.wmOrder;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.dao.OrderdlvdataMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSWarehouseDao;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.OrderAdjustDto;
import com.sales.ops.dto.order.ResultForOrderAdjustDto;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.dto.zd.*;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.service.ba.BaseWarehouseService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.serviceimpl.utils.HashCodeUtil;
import com.sales.ops.utils.WmOrderNoFactory;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：转定
 * @date ：Created in 2023/4/27 8:48
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ZDServiceImpl implements ZDService{

    @Resource
    private BaseDoService baseDoService;
    @Resource
    private OpsPcoService opsPcoService;
    @Autowired
    private RcvdetailMapper rcvdetailMapper;
    @Autowired
    private OrderdlvdataMapper orderdlvdataMapper;
    @Autowired
    private AllotInvenToryService allotInvenToryService;
    @Autowired
    private WmRouterOrderService wmRouterOrderService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OPSWarehouseDao opsWarehouseDao;
    @Autowired
    private BaseWarehouseService baseWarehouseService;
    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Autowired
    private BaseRoService baseRoService;

    @Autowired
    private BasePoService basePoService;

    @Autowired
    private OpsInventoryDao opsInventoryDao;
    @Autowired
    private WmDispatchService wmDispatchService;

    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;

    /**
     * 是否部分收货 用于调库计划
     * bugid:14473 (40楼) c14717 20240719
     * @return
     */
    @Override
    public boolean getRoPartStatus(PurchaseInfoForCancel param){
        OpsRequestpurchase requestPurchase = basePoService.getRequestPurchase(param.getRequestno(), param.getRequestItemno(), param.getRequestSplitno());
        if(Objects.isNull(requestPurchase) || org.apache.commons.lang3.StringUtils.isBlank(param.getPurchaseno())){
            return false;
        }
        String purchaseno = param.getPurchaseno();
        Integer purchaseItemno = param.getPurchaseItemno();
        Integer purchaseSplitno = param.getPurchaseSplitno();
        if(Objects.isNull(purchaseSplitno)){
            purchaseSplitno = 0;
        }
        int recQty = 0;
        if(WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())){
            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseno, purchaseItemno,
                    purchaseSplitno,null,null, RoTypeEnum.CGRKBK.getType());
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                for(OpsRo opsRo : roList){
                    //存在部分收货
                    if(RoStatusEnum.PART.getStatus().equals(opsRo.getRoStatus())){
                        return true;
                    }
                    //如果ro已完成 累加roItem收货数量
                    if(RoStatusEnum.FINISH.getStatus().equals(opsRo.getRoStatus())){
                        List<OpsRoItem> roItems = baseRoService.findRoItemsByRoId(opsRo.getRoId());
                        if(CollectionUtils.isNotEmpty(roItems)){
                            if(Objects.nonNull(roItems.get(0).getRecQty())){
                                recQty = recQty + roItems.get(0).getRecQty();
                            }
                        }
                    }
                }
            }
        }else {
            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseno, purchaseItemno,null
                    ,purchaseSplitno,null, RoTypeEnum.CGRKBK.getType());
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                for(OpsRo opsRo : roList){
                    //存在部分收货
                    if(RoStatusEnum.PART.getStatus().equals(opsRo.getRoStatus())){
                        return true;
                    }
                    //如果ro已完成 累加roItem收货数量
                    if(RoStatusEnum.FINISH.getStatus().equals(opsRo.getRoStatus())){
                        List<OpsRoItem> roItems = baseRoService.findRoItemsByRoId(opsRo.getRoId());
                        if(CollectionUtils.isNotEmpty(roItems)){
                            if(Objects.nonNull(roItems.get(0).getRecQty())){
                                recQty = recQty + roItems.get(0).getRecQty();
                            }
                        }
                    }
                }
            }
        }
        // 转定时验证调库计划是否可勾选，如果有收货则不可勾选
        if(recQty > 0 && recQty < param.getQuantity()){
          return true;
        }
        return false;
    }


    /**
     * //获取订单可用库存
     * @param orderId
     * @param orderItem
     * @param qty
     * @throws OpsException
     */
    @Override
    public void showInvByItemQty(String orderId, Integer orderItem, Integer qty, String modelNo, List<ZDInventoryInfoDTO> outList) throws OpsException{
        Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(orderId);
        Rcvdetail rcvdetail = null;

        RcvdetailExample example1 = new RcvdetailExample();
        example1.createCriteria().andRorderNoEqualTo(orderId).andRorderItemEqualTo(orderItem);
        List<Rcvdetail> rcvdetailList = rcvdetailMapper.selectByExample(example1);
        rcvdetail = rcvdetailList.get(0);
        OrderdlvdataKey orderdlvdataKey = new OrderdlvdataKey();
        orderdlvdataKey.setOrderno(rcvmaster.getRorderNo());
        orderdlvdataKey.setItemno(rcvdetail.getRorderItem());
        Orderdlvdata orderdlvdata = orderdlvdataMapper.selectByPrimaryKey(orderdlvdataKey);
        if (ObjectUtil.isNull(orderdlvdata)) {
            OrderdlvdataExample example = new OrderdlvdataExample();
            example.createCriteria().andOrdernoEqualTo(rcvmaster.getRorderNo()).andItemnoEqualTo(0);
            List<Orderdlvdata> orderdlvdataList = orderdlvdataMapper.selectByExample(example);
            if (orderdlvdataList.size() > 0) {
                orderdlvdata = orderdlvdataList.get(0);
            }
        }
        if (Objects.isNull(orderdlvdata)) {
            throw Exceptions.OpsException("单号：" + rcvmaster.getRorderNo() + " ; orderdlvdata表无数据");
        }
        InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto(rcvmaster, rcvdetail, orderdlvdata);
        //转定数量
        inputDto.setQuantity(qty);
        inputDto.setModelno(modelNo);
        ZDInventoryCkByOrderOutDto out = new ZDInventoryCkByOrderOutDto();
        allotInvenToryService.getInvByModelNO(inputDto,modelNo);
        allotInvenToryService.getMoveInvByModelNO(inputDto,modelNo);
        allotInvenToryService.getOpsInventoryListByCkToZD(inputDto,out);
        // 去重
        List<ZDInventoryDto> distinctInventoryList = out.getInventoryList().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(inv -> inv.getInventory().getInventoryId()))),
                        ArrayList::new
                ));
        // 去重
        List<ZDInventoryMoveDto> distinctMoveInventoryList = out.getInventoryMoveList().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(inv -> inv.getInventory().getInventoryId()))),
                        ArrayList::new
                ));
        //组装页面报文
        for (ZDInventoryDto inv : distinctInventoryList) {
            ZDInventoryInfoDTO inventoryDTO = new ZDInventoryInfoDTO(inv.getInventory());
            inventoryDTO.setDk("否");
            if(inv.getDk()){
                inventoryDTO.setDk("是");
            }
            // bugid:20641 c14717 20260424 获取库存风险标识
            inventoryDTO.setInvRisk(inv.getRisk());
            outList.add(inventoryDTO);
        }
        for (ZDInventoryMoveDto invMove : distinctMoveInventoryList) {
            ZDInventoryInfoDTO inventoryDTO = new ZDInventoryInfoDTO(invMove.getInventory());
            inventoryDTO.setDk("否");
            if(invMove.getDk()){
                inventoryDTO.setDk("是");
            }
            inventoryDTO.setAssociateNo(invMove.getInventory().getAssociateNo(),invMove.getInventory().getAssociateNoItem(),invMove.getInventory().getAssociateNoSplitno());
            outList.add(inventoryDTO);
        }
        for (ZDInventoryInfoDTO dto : outList) {
            OpsInventoryProperty property = opsInventoryPropertyService.findById(dto.getInventoryPropertyId());
            dto.setProperty(property);
            OpsWarehouse warehouse = opsWarehouseService.findById(dto.getWarehouseCode());
            dto.setWarehouseInfo(warehouse);
        }
    }

    /**
     * 转定转采购
     * 1.db
     * 2.pco
     * 3.do
     * 4.变更物流交货期
     * 5.写事件报文  需要确认报文；
     * 6.持久化日志
     * @param param
     * @throws OpsException
     */
    @Override
    public void handleOrderBindPOZD(ZDHandToPoParamDto param) throws OpsException{
        UserDto userDto = new UserDto(param.getUserName());
        //0 验证
        checkZDToPo(param);
        //1.绑定指令
        //db
        param.setResultLog(new StringBuffer());
        if(param.getDbOrNot()){
            param.getResultLog().append(";db：");
            handleDBToPo(param,userDto);
        }else{
            //pco
            if(param.getAssModleFlag()){
                param.getResultLog().append(";pco：");
                handlePCOToPo(param,userDto);
            }else{//do
                param.getResultLog().append(";do：");
                handleDOToPo(param, userDto);
            }
        }
        //2. 变更物流交货期
        opsDoService.updateDoPcoWlday(param.getOrderId(), param.getOrderItem(), null, true, userDto);
        //3. 状态事件组装报文
        sendZDToPoEvent(param);
    }

    public void sendZDToPoEvent(ZDHandToPoParamDto param)throws OpsException{
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

        } else {// 非db
            if (param.getAssModleFlag()) {
                Long pcoCount = opsPcoService.countPcoItemInvByPcoIdItem(param.getPcoId(), param.getPcoItem());
                if (pcoCount > 0) {
                    params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getPcoItem(), param.getPcoId()));
                }
            } else {
                // 非单组装有库存关联关系的报文
                // 交易单
                Long jyDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getDoId());
                if (jyDoItemInvsCount > 0) {
                    params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getNum(), param.getDoId()));                }

                //新拆分的交易出库单
                if(!StringUtils.isEmpty(param.getAssNewJYDoId())){
                    Long dbDoItemInvsCount = baseDoService.countDoItemInvByDoId(param.getAssNewJYDoId());
                    if (dbDoItemInvsCount > 0) {
                        params.add(new OrderAdjustDto(param.getOrderId(), param.getOrderItem(), param.getAssNewNum(), param.getAssNewJYDoId()));
                    }
                }
            }
        }
        //事件发送：客单转订
        ResultForOrderAdjustDto dto = buildToPurchaseAdjustDto(param);
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ADJUST, param.getOrderId(), Integer.valueOf(param.getOrderItem()), param.getNum(), dto);
    }

    private ResultForOrderAdjustDto buildToPurchaseAdjustDto(ZDHandToPoParamDto param) {
        if (Boolean.TRUE.equals(param.getExceptionOrNot()) &&
                !StringUtils.isEmpty(param.getInvTableType()) && param.getInvTableType().equals(DoWaitTypeEnum.EXCEPTION.getType())) {
            return ResultForOrderAdjustDto.exceptionToPurchase(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty());
        }
        if (InventoryTableTypeEnum.TRANS.getType().equals(param.getInvTableType())) {
            return ResultForOrderAdjustDto.transitToPurchase(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty(), param.getInvId());
        }
        return ResultForOrderAdjustDto.normalToPurchase(param.getOrderId(), param.getOrderItem(), param.getModelNo(), param.getQty());
    }

    /**
     * 转定采购验证
     * @param param
     * @throws OpsException
     */
    public void checkZDToPo(ZDHandToPoParamDto param) throws OpsException{
        param.setSplitNoFlag(true);
        //1.调拨单和交易单验证
        if(param.getDbOrNot() ||( !param.getDbOrNot() && !param.getAssModleFlag())){
            //用于请购单号
            if(param.getNum()==0){
                param.setSplitNoFlag(false);
            }
            String thisDoid = "";
            if(param.getDbOrNot()){
                thisDoid = param.getDbDoId();
            }else {
                thisDoid = param.getDoId();
            }
            OpsDo opsDo = baseDoService.getDoByDoId(thisDoid);
            if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())
                    || DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())){
                throw Exceptions.OpsException("物流已发货");
            }
            param.setGatherWarehouseCode(opsDo.getGatherWarehouseCode());
            //1.1状态及关联关系验证
            if(!param.getExceptionOrNot()){
                OpsDoItemInventory doItemInventory = baseDoService.getDoItemInventoryById(param.getItemInvId());
                if(!param.getExceptionOrNot() && Objects.isNull(doItemInventory)){
                    throw Exceptions.OpsException("关联关系不存在："+param.getItemInvId());
                }
                //验证关联关系
                if(Objects.nonNull(doItemInventory)){
                    if(param.getDbOrNot() && !doItemInventory.getUseQty().equals( param.getQty())){
                        throw Exceptions.OpsException("调拨单多条关联关系"+opsDo.getDoId());
                    }
                    if(InventoryTableTypeEnum.TRANS.getType().equals(doItemInventory.getInventoryTableType())){
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
            if(!opsDoItem.getModelno().equals(param.getModelNo())){
                throw Exceptions.OpsException("转定型号不符");
            }
        }else{
            OpsDo opsDo = baseDoService.getDoByDoId(param.getDoId());
            if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())
                    || DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())){
                throw Exceptions.OpsException("物流已发货");
            }
            param.setGatherWarehouseCode(opsDo.getGatherWarehouseCode());
            //2.pco加工单关联关系验证
            if(param.getAssModleFlag()){
                if(!param.getExceptionOrNot()){
                    OpsPcoItemInventory itemInventory = opsPcoService.findPcoItemInventoryById(param.getItemInvId());
                    if(!param.getExceptionOrNot() && Objects.isNull(itemInventory)){
                        throw Exceptions.OpsException("关联关系不存在："+param.getItemInvId());
                    }
                    if(Objects.nonNull(itemInventory)){
                        if(InventoryTableTypeEnum.TRANS.getType().equals(itemInventory.getInventoryTableType())) {
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
                }
                //2.1 验证数量
                List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByPcoId(param.getPcoId(),param.getPcoItem());
                OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(param.getPcoId(),param.getPcoItem());
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
                if(!opsPcoItem.getSubModelno().equals(param.getModelNo())){
                    throw Exceptions.OpsException("转定型号不符");
                }
                //bomid
                OpsPco pco = opsPcoService.getPcoByPcoId(param.getPcoId());
                param.setBomId(pco.getBomid());
            }
        }
        Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(param.getOrderId());
        param.setRcvmaster(rcvmaster);
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvmaster.getDlvEntire())){
            throw Exceptions.OpsException("通知发货不可转定采购");
        }

    }
    // 删do
    public void delDo(OpsDo opsDo, Long cancelId, DoTypeEnum doTypeEnum, String msg) throws OpsException {
        wmOrderTaskService.delWmOrderTask(opsDo.getDoId(), msg);
        opsDoService.CancelDo(opsDo.getId(), opsDo.getDoId(), opsDo.getOrderId(), opsDo.getOrderItem(), UserDto.ADMIN);
        opsDoService.insertOrderCancelItem(cancelId, opsDo.getDoId(), doTypeEnum.getType(), "取消成功", UserDto.ADMIN);
    }
    // 删ro
    public void delDBRo(OpsDo opsDo, Long cancelId,RoTypeEnum roTypeEnum) throws OpsException {
        List<OpsRo> opsRos = baseRoService.findRoByOrderItemNumExtNUM(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                opsDo.getNum(), opsDo.getAssNum(),opsDo.getExtNum(),roTypeEnum.getType());
        if (org.springframework.util.CollectionUtils.isEmpty(opsRos)) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
        }
        if (opsRos.size() > 1) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
        }
        opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), "采购取消", UserDto.ADMIN);
    }

    /**
     *1.db
     *调用wms删单接口
     *删调拨单
     *变更waitType（pcoItem，do）
     *写请购
     *追加日志
     * @param param
     * @param userDto
     */
    public void handleDBToPo(ZDHandToPoParamDto param,UserDto userDto) throws OpsException{
        //1.验证状态
        OpsDo DBDo =  baseDoService.getDoByDoId(param.getDbDoId());
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(DBDo, null,null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        //3.删调拨单
        CancelForOrderDto inputDto = new CancelForOrderDto(DBDo.getOrderId(),DBDo.getOrderItem(),DBDo.getDoType(),userDto,"转定取消");
        Long cancelId = opsDoService.insertOrderCancel(inputDto);
        delDo(DBDo,cancelId,DoTypeEnum.DBCK,"转定取消");
        delDBRo(DBDo, cancelId, RoTypeEnum.DBRK);
        //4.变更waitType
        if(param.getAssModleFlag()){
            OpsPco opsPco = opsPcoService.getPcoByPcoId(param.getPcoId());
            if(opsPco.getIsWms().equals(1)){
                throw Exceptions.OpsException("调拨单未出库，加工单已下发："+opsPco.getPcoId());
            }
            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(param.getPcoId(), param.getPcoItem());
            opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(),DoWaitTypeEnum.WaitCG.getType(), userDto);
            param.getResultLog().append("删db,pcoItem_wait=CG");
        }else{
            OpsDo jyckDo =  baseDoService.getDoByDoId(param.getDoId());
            if(jyckDo.getIsWms().equals(1)){
                throw Exceptions.OpsException("调拨单未出库，交易单已下发："+jyckDo.getDoId());
            }
            opsDoService.updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, jyckDo.getGatherWarehouseCode(), jyckDo.getGatherWarehouseCode(),
                    DoWaitTypeEnum.WaitCG.getType(), jyckDo.getVersion(), param.getUserName(), 0, null);
            param.getResultLog().append("删db，修改waitType=CG");
        }
        //5.写请购
        initRequestPo(param);
    }

    /**
     * 2.pco
     * 验证   ********统一接口
     * 调用wms删单接口
     * 变更iswms （do，pco，是否创建task）
     * 判断有无关联关系，删关联关系，释放预占库存
     * 是否创建新task
     * 变更waitType pcoItem
     * 写请购
     * 追加日志
     * @param param
     * @param userDto
     */
    public void handlePCOToPo(ZDHandToPoParamDto param,UserDto userDto) throws OpsException{
        //1.转定验证
        OpsDo jyDo =  baseDoService.getDoByDoId(param.getDoId());
        if(!jyDo.getGatherWarehouseCode().equals(jyDo.getWarehouseCode())){
            throw Exceptions.OpsException("数据异常集约仓不等发货仓");
        }
        OpsPco opsPco = opsPcoService.getPcoByPcoId(param.getPcoId());
        if(Objects.isNull(opsPco)){
            throw Exceptions.OpsException("加工指令不存在");
        }
        //越库判断
        if(!StringUtils.isEmpty(opsPco.getRoId())){
            throw Exceptions.OpsException("越库单据无法转定");
        }
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(jyDo, opsPco, null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(param.getPcoId(), param.getPcoItem());
        //3变更iswms （do，pco，是否创建task）
        boolean newTaskFlag = jyDo.getIsWms() == 1;
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
        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(),DoWaitTypeEnum.WaitCG.getType(),userDto);
        //4判断有无关联关系，删关联关系，释放预占库存
        //非二次处理删除关联关系，释放预占库存
        if(!param.getExceptionOrNot()){
            //释放预占
            opsPcoService.deletePcoItemInventoryByPrimaryKeySelective(param.getItemInvId(), userDto);
            baseInventoryService.releasePreQtyInventory(param.getInvId(),  param.getQty(), param.getInvTableType(), userDto);
        }
        //5是否创建新task
        taskPCOHandle(newTaskFlag,param,"转定");
        //6写请购
        initRequestPo(param);
        //7追加日志
        param.getResultLog().append("释放库存删关联关系");
    }

    /**
     * 3.do
     * 验证   ********统一接口
     * 调用wms删单接口
     * 变更iswms （do 是否创建task）
     * 变更 dowaitType OK
     * 不拆分do
     *    删关联关系，释放库存
     *    变更waitType=CG
     *    是否创建新task
     *    写请购追加
     *    日志
     * 拆分do
     *    创建新的的do（waitType=CG），doitem
     *    删除旧的关联关系，释放库存
     *    更改旧的doItem qty
     *    持久化新的do
     *    旧的do是否创建task 或修改flag
     *   写请购
     *   追加日志
     * @param param
     * @param userDto
     */
    public void handleDOToPo(ZDHandToPoParamDto param,UserDto userDto) throws OpsException{
        //1.转定验证
        OpsDo jyDo =  baseDoService.getDoByDoId(param.getDoId());
        // 越库判断
        if(Objects.nonNull(jyDo.getRoCrossType()) && jyDo.getRoCrossType() == 2){
            throw Exceptions.OpsException("越库单据无法转定");
        }
        //2.调用wms删单接口
        CommonResult<List<JSONObject>> wmResult = cancelWMSApi(jyDo, null,null,"转定取消");
        if(!wmResult.isSuccess()){
            throw Exceptions.OpsException("物流已作业");
        }
        //3.变更iswms （do 是否创建task）
        boolean newTaskFlag = jyDo.getIsWms() == 1;
        if(jyDo.getIsWms()==1 || param.getExceptionOrNot()){
            //二次处理状态变更
            jyDo.setIsWms(0);
        }
        jyDo.setWarehouseCode(jyDo.getGatherWarehouseCode());
        OpsDoItem jyDoItem = baseDoService.getDoItemByDoId(param.getDoId());
        if(jyDoItem.getQty() - param.getQty() == 0){//5.不拆分do
            //4.变更 dowaitType CG
            jyDo.setWaitType(DoWaitTypeEnum.WaitCG.getType());
            opsDoService.updateOpsDo(jyDo,userDto);
            //非二次处理状态
            if(!param.getExceptionOrNot()){
                //删除doIteInv 释放预占数量
                opsDoService.deleteDoItemInventoryByPrimaryKeySelective(param.getItemInvId(),  userDto);
                baseInventoryService.releasePreQtyInventory(param.getInvId(),  param.getQty(), param.getInvTableType(), userDto);
                param.getResultLog().append("释放库存删关联关系");
            }
            if(!CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(jyDo.getDlvEntire())){
                taskDoHandle(newTaskFlag,param.getDoId(),"转定");
            }
        }else {//6.拆分do
            jyDo.setWaitType(DoWaitTypeEnum.OK.getType());
            opsDoService.updateOpsDo(jyDo,userDto);
            createAssAndSubOldDoItemQtyZD(param, jyDo, jyDoItem, userDto, newTaskFlag, "拆do:");
        }
        //7.写请购
        initRequestPo(param);
    }

    // 拆分新指令，扣减旧doItem数量，处理task
    public CreDoByInventoryDto createAssAndSubOldDoItemQtyZD(ZDHandToPoParamDto param,OpsDo jyDo,OpsDoItem jyDoItem,UserDto userDto,
                                                             boolean newTaskFlag,String logMsg) throws OpsException{
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(param.getOrderId(), param.getOrderItem(), DoTypeEnum.JYCK);
        CreDoByInventoryDto newDto = createAssQtyDo(OrderIDFormatEnum.DO_ZD_FORMAT.getFormat(),
                opsDoMaxOne.getNum()+1,opsDoMaxOne.getAssNum(), jyDo, jyDoItem, param.getQty(), userDto,
                DoTypeEnum.JYCK,DoWaitTypeEnum.WaitCG.getType(),DoSourceEnum.ASSQTY);
        param.setNum(opsDoMaxOne.getNum()+1);
        param.setSplitNoFlag(true);
        //更改旧的doItem qty
        opsDoService.updateDoItemQtyByDoItemId(jyDoItem.getId(),  jyDoItem.getQty() - param.getQty(),jyDoItem.getVersion());
        //持久化do
        wmOrderByInventoryDto.getDolist().add(newDto);
        opsDoService.CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,null);
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
    public void taskPCOHandle(boolean newTaskFlag,ZDHandToPoParamDto param, String msg){
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
    /**
     * 初始化请购表
     * @param param init{SplitNoFlag，ModelNo,Qty,GatherWarehouseCode,bomId}
     * @throws OpsException
     */
    public void initRequestPo(ZDHandToPoParamDto param) throws OpsException{
        List<OpsRequestpurchase> requestpurchaseList = new ArrayList<>();
        Rcvdetail rcvdetail = wmRouterOrderService.getRcvdetail(param.getOrderId(), param.getOrderItem());
        InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto(param.getRcvmaster(), rcvdetail);
        if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {
            throw Exceptions.OpsException("特殊标识为必须出委托不采购");
        }
        //拆分型号
        if(param.getAssModleFlag()){
            // 组装请购实体
            OpsRequestpurchase opsRequestpurchase = wmDispatchService.initOpsRequestpurchase(param.getSplitNoFlag(), inputDto,
                    WMPurchaseTagEnum.ASS, param.getPcoItem(), param.getModelNo(), param.getQty(), param.getGatherWarehouseCode(), param.getBomId());
            opsRequestpurchase.setShikomianswerno("");
            requestpurchaseList.add(opsRequestpurchase);
        }else {
            OpsRequestpurchase opsRequestpurchase = wmDispatchService.initOpsRequestpurchase(param.getSplitNoFlag(), inputDto,
                    WMPurchaseTagEnum.WHOLE, param.getNum(), param.getModelNo(), param.getQty(), param.getGatherWarehouseCode(), null);
            if (OrderTypeEnum.DR.equals(inputDto.getOrderType()) || OrderTypeEnum.CR.equals(inputDto.getOrderType())) {// DR单直接写供应商JP
                opsRequestpurchase.setSupplierid("JP");
            }
            requestpurchaseList.add(opsRequestpurchase);
        }
        // 持久化请购表
        if (!CollectionUtils.isEmpty(requestpurchaseList)) {
            for (OpsRequestpurchase item : requestpurchaseList) {
                OpsRequestpurchase r = basePoService.getRequestPurchase(item.getOrderno(), item.getItemno(),
                        item.getSplititemno());
                if (Objects.nonNull(r)) {
                    throw Exceptions.OpsException("请购单已存在，请先处理请购单");
                }
                opsRequestpurchaseMapper.insertSelective(item);
            }
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


    @Override
    public CommonResult<Boolean> ZDDelPo(PurchaseInfoForCancel info) throws OpsException{
        if (Objects.nonNull(info) && !info.isMerge()) {
            RequestCancelDto dto = new RequestCancelDto();
            //请购单号
            dto.setOrderno(info.getRequestno());
            dto.setItemno(info.getRequestItemno());
            dto.setSplititemno(info.getRequestSplitno());
            //订单还原取消采购单
            dto.setSourceType(PurchaseCancelSourceEnum.ZD_CUSTOMER_ORDER.getType());
            log.info("取消采购单：{}", JSONUtil.toJsonPrettyStr(dto));
            return requestPurchaseFeignApi.cancelPurchase(dto);
        } else {
            throw Exceptions.OpsException("无采购数据");
        }
    }

    /**
     * 创建pcoItemInventory 预占库存
     * @param pcoId
     * @param pcoItem
     * @param qty
     * @param invTableType
     * @param invId
     * @param userDto
     * @throws OpsException
     */
    @Override
    public void createPcoItemInvAddPre(String orderFno,Boolean toInvRiskFlag,String pcoId, Integer pcoItem, Integer qty, String invTableType, Long invId,
                                       UserDto userDto) throws OpsException{
        OpsPcoItemInventory pcoItemInventory = initOpsPcoItemInventory(pcoId, pcoItem,qty,1,invId, invTableType,userDto.getUserName());
        opsPcoService.insertInventoryItem(pcoItemInventory);

        //bugid: 20641 风险在库
        if(Objects.nonNull(toInvRiskFlag) && toInvRiskFlag){
            baseInventoryService.addPreQtyRiskInv(pcoItemInventory.getInventoryId(), pcoItemInventory.getUseQty(), pcoItemInventory.getInventoryTableType(), userDto,orderFno);
        }
        baseInventoryService.addPreQtyInventory(pcoItemInventory.getInventoryId(), pcoItemInventory.getUseQty(), pcoItemInventory.getInventoryTableType(), userDto);
    }


    /**
     * 生成 pcoItemInventory
     */
    private OpsPcoItemInventory initOpsPcoItemInventory(String pcoId, int pcoItem, Integer useQty, Integer sortnum,
                                                        Long inventoryId, String invTableType, String creator) {
        OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
        pcoItemInventory.setPcoId(pcoId);
        pcoItemInventory.setPcoItem(pcoItem);
        pcoItemInventory.setPcoType(1);
        pcoItemInventory.setUseQty(useQty);
        pcoItemInventory.setInventoryId(inventoryId);
        pcoItemInventory.setInventoryTableType(invTableType);
        pcoItemInventory.setVersion(0L);
        pcoItemInventory.setDelflag(0);
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setCreator(creator);
        pcoItemInventory.setSortnum(sortnum);
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setCreator(creator);
        return pcoItemInventory;
    }

    @Override
    public void createDoItemInvAddPre(String orderFno,Boolean toInvRiskFlag, String doId, Integer qty, String invTableType, Long invId,
                                      UserDto userDto) throws OpsException{
        OpsDoItemInventory  doItemInventory = initOpsDoItemInventory(qty,doId,invId,invTableType,userDto);
        opsDoService.insertDoItemInv(doItemInventory,userDto);
        //bugid:20641 C14717 20260424 风险判断
        if(Objects.nonNull(toInvRiskFlag) && toInvRiskFlag){
            baseInventoryService.addPreQtyRiskInv(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), userDto,orderFno);
        }
        baseInventoryService.addPreQtyInventory(doItemInventory.getInventoryId(), doItemInventory.getUseQty(), doItemInventory.getInventoryTableType(), userDto);
    }
    @Override
    public OpsDoItemInventory initOpsDoItemInventory(Integer useQty, String doid, Long toInvId, String invTableType, UserDto userDto) {
        OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
        opsDoItemInventory.setDoId(doid);
        opsDoItemInventory.setDoItem(1);
        opsDoItemInventory.setInventoryId(toInvId);
        opsDoItemInventory.setInventoryTableType(invTableType);
        opsDoItemInventory.setUseQty(useQty);
        opsDoItemInventory.setVersion(0L);
        opsDoItemInventory.setDelflag(0);
        opsDoItemInventory.setCreTime(new Date());
        opsDoItemInventory.setCreator(userDto.getUserName());
        opsDoItemInventory.setSortnum(1);
        return opsDoItemInventory;
    }
    @Override
    public void persistentLogisticsDoc(Map<Long, Boolean> riskMap,WmOrderByInventoryDto wmOrderByInventoryDto, UserDto userDto) throws OpsException {
        log.info("持久化物流单据");
        if (!wmOrderByInventoryDto.getRoList().isEmpty()) {
            opsRoService.CreateRoForDispatch(wmOrderByInventoryDto.getRoList(), userDto);
        }
        if (!wmOrderByInventoryDto.getDolist().isEmpty()) {
            opsDoService.CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,riskMap);
        }
        if (!wmOrderByInventoryDto.getPcoList().isEmpty()) {
            String dlvEntire = "";
            for (CrePcoByInventoryDto dto : wmOrderByInventoryDto.getPcoList()){
                if (Objects.nonNull(dto.getOpsPco())){
                    Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(dto.getOpsPco().getOrderId());
                    if(Objects.nonNull(rcvmaster)){
                        dlvEntire = rcvmaster.getDlvEntire();
                        break;
                    }
                }
            }
            opsPcoService.CreatePcoForDispatch(wmOrderByInventoryDto.getPcoList(), userDto,dlvEntire,null);
        }
    }
}
