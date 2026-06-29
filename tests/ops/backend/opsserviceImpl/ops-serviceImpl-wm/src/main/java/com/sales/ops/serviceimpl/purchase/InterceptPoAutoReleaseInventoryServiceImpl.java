package com.sales.ops.serviceimpl.purchase;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsOrderUpdateLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.RequestPurchaseDao;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryCkByOrderOutDto;
import com.sales.ops.dto.order.OrderChangeToInitStatusDto;
import com.sales.ops.dto.po.InterceptPoAutoReleaseInvDto;
import com.sales.ops.dto.po.PoOrderInfoDto;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.zd.ZDInventoryInfoDTO;
import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.enums.WMPurchaseTagEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.purchase.InterceptPoAutoReleaseInventoryService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：C14717
 * @version: $
 * @description：采购拦截订单自动出库存，整单还原，拆分转定；
 * @date ：Created in 2025/5/26 9:20
 */
@Service
public class InterceptPoAutoReleaseInventoryServiceImpl implements InterceptPoAutoReleaseInventoryService {
    private static Logger log = LoggerFactory.getLogger(InterceptPoAutoReleaseInventoryServiceImpl.class);
    @Autowired
    private RequestPurchaseDao requestPurchaseDao;
    @Autowired
    private BaseDoService baseDoService;

    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private AllotInvenToryService allotInvenToryService;

    @Autowired
    private WmRouterOrderService wmRouterOrderService;

    @Autowired
    private OrderDlvDataService orderDlvDataService;

    @Autowired
    private ZDService zdService;

    @Autowired
    private OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;

    @Autowired
    private OpsChangeOrderService opsChangeOrderService;

    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;

    @Autowired
    private CustomerEventPublisher customerEventPublisher;

    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Autowired
    private WmDispatchService wmDispatchService;

    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;


    /**
     * bugid:17646 20250526 c14717
     * 1. a.订单处理或订单转定
     * 2.符合条件，删采购
     * 3.删指令，及分配表
     * 4.持久化订单分配指令、请购信息为拦截状态，或持久化转定指令，失败则记录异常订单
     * 5.传状态
     *    a.分配状态：
     *    b.转定状态
     * 6.记录日志
     */
    @Override
    public int handleDataDetail(InterceptPoAutoReleaseInvDto obj) throws OpsException{
        if(obj.getHandleFlag() == 0){//整出
            //验证库存是否满足
            Boolean aBoolean = checkReleaseOrderInv(obj.getOrderno(), obj.getItemno() + "");
            if(aBoolean){
                // 构造还原参数
                OrderChangeToInitStatusDto orderChangeToInitStatusDto = constructReleaseParam(obj);
                //执行还原
                orderChangeToInitStatus(orderChangeToInitStatusDto);
                return 1;
            }
        }else {//拆分
            //构造转定参数
            ZDInventoryInfoDTO zdDTO = checkZDOrderInv(obj);
            if(Objects.nonNull(zdDTO)){
                ZDPageShowOrderBindInvDto pageShowOrderBindInvDto = constructZDParam(obj, zdDTO);
                //执行转定
                handleZD(pageShowOrderBindInvDto);
                return 1;
            }
        }
        return 0;
    }

    /**
     * 0.获取采购拦截状态能自动出库存的客单
     * bugid:17646 20250526 c14717
     */
    @Override
    public List<PoOrderInfoDto> getBasePoList(){
        //1.查询采购拦截状态的客单列表 where stateCode =4 and purchaseType ='A'
        List<PoOrderInfoDto> interceptPoList = requestPurchaseDao.getInterceptPoList();
        if(CollectionUtils.isEmpty(interceptPoList)){
            return interceptPoList;
        }
        // 验重集合 <拦截配置表id,是否自动出库存>
        Map<Integer,Boolean> map = new HashMap<Integer,Boolean>();
        // 遍历请购表
        for(PoOrderInfoDto obj: interceptPoList){
            obj.setAutoInventory(false);
            if(StringUtils.isBlank(obj.getInfojson())){
                continue;
            }
            //2.获取请购拦截配置表的ids
            RequestPurchaseInfo info = JSONObject.parseObject(obj.getInfojson(),
                    RequestPurchaseInfo.class);
            if(CollectionUtils.isEmpty(info.getInterceptConfigIdList())){
                continue;
            }
            // 遍历配置表
            for(Integer id : info.getInterceptConfigIdList() ){
                if(map.containsKey(id)){
                    //自动出库存结束循环
                    if(map.get(id)){
                        // 自动出库存 ，结束循环
                        obj.setAutoInventory( map.get(id));
                        break;
                    }
                    continue;
                }
                //3.查询配置表是否是自动出库 where auto_stock_out =1 and id = #{Id}
                String interceptConfig = requestPurchaseDao.getInterceptConfig(id);
                if(StringUtils.isBlank(interceptConfig)){
                    map.put(id,false);
                    continue;
                }
                // 自动出库存 ，结束循环
                map.put(id,true);
                obj.setAutoInventory(true);
                break;
            }
        }
        return interceptPoList;
    }

    /**
     1.验证采购和do的关联关系
     */
    @Override
    public List<InterceptPoAutoReleaseInvDto> checkPoAndDoRelation(List<PoOrderInfoDto> poOrderInfoDtoList) throws OpsException {
        List<InterceptPoAutoReleaseInvDto> handleDtoList = new ArrayList<>();
        StringBuffer log = new StringBuffer();
        if (CollectionUtils.isEmpty(poOrderInfoDtoList)){
            return handleDtoList;
        }
        for(PoOrderInfoDto obj : poOrderInfoDtoList ){
            // 非自动出库存
            if(!obj.getAutoInventory()){
                continue;
            }
            // 整型号采购且拆分型号为空
            if(WMPurchaseTagEnum.WHOLE.getType().equals(obj.getWmtag()) && Objects.isNull(obj.getSplititemno())){
                //查询10位单号 查do指令 包含JYCK DBCK
                List<OpsDo> doList = baseDoService.findDoListByOrder(obj.getOrderno(), obj.getItemno());
                checkPoAndDoRelationDetail(doList, obj,handleDtoList,0,"");
                // 拆分数量
            }else if (WMPurchaseTagEnum.WHOLE.getType().equals(obj.getWmtag()) && Objects.nonNull(obj.getSplititemno())){
                //查询10位单号+拆分号 查do指令 包含JYCK DBCK
                List<OpsDo> doList = baseDoService.findDoListByOrder(obj.getOrderno(), obj.getItemno() + "", obj.getSplititemno(), null, DoTypeEnum.JYCK, DoTypeEnum.DBCK);
                checkPoAndDoRelationDetail(doList, obj,handleDtoList,1,"");
                // 拆分型号
            }else if(WMPurchaseTagEnum.ASS.getType().equals(obj.getWmtag())&& Objects.nonNull(obj.getSplititemno())){
                List<OpsDo> doList = baseDoService.findDoListByOrder(obj.getOrderno(), obj.getItemno() + "", null, null, DoTypeEnum.JYCK);
                List<OpsDo> dbList = baseDoService.findDoListByOrder(obj.getOrderno(), obj.getItemno() + "", null, obj.getSplititemno(), DoTypeEnum.DBCK);
                if(CollectionUtils.isNotEmpty(dbList)){
                    // 有调拨单
                    continue;
                }
                OpsPco opsPco = opsPcoService.findPcoByOrder(obj.getOrderno(), obj.getItemno()+"");
                if(Objects.isNull(opsPco)){
                    // 无pco
                    continue;
                }
                List<OpsPcoItemInventory> opsPcoItemInve = opsPcoService.getOpsPcoItemInventoryByPcoId(opsPco.getPcoId(), obj.getSplititemno());
                if(CollectionUtils.isNotEmpty(opsPcoItemInve)){
                    // pco存在关联关系
                    continue;
                }
                checkPoAndDoRelationDetail(doList,obj,handleDtoList,2,opsPco.getPcoId());
            }
        }
        return handleDtoList;
    }

    /**
     * 验证采购和do的关联关系公共方法明细
     * @param doList
     * @param obj
     * @param handleDtoList
     * @param handleFlag
     * @throws OpsException
     */
    public void checkPoAndDoRelationDetail(List<OpsDo> doList,PoOrderInfoDto obj, List<InterceptPoAutoReleaseInvDto> handleDtoList,Integer handleFlag,String pcoId) throws OpsException{
        //1条交易指令 2.非拆分型号，等待类型不是调拨单，且 无关联关系
        if(CollectionUtils.isEmpty(doList)){
            // 无交易单
            return;
        }
        if(doList.size()!=1){
            // 包含其他单据 调拨单不可
            return;
        }
        OpsDo opsDo = doList.get(0);
        if(!DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
            // 非交易单
            return;
        }
        if(DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && handleFlag != 2){
            // 拆分型号单
            return;
        }
        List<OpsDoItemInventory> doItemInvs = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
        if(CollectionUtils.isNotEmpty(doItemInvs)){
            // 存在关联关系
            return;
        }
        //保存可转定
        handleDtoList.add(new InterceptPoAutoReleaseInvDto(obj.getOrderno(),obj.getItemno(),obj.getSplititemno(),handleFlag,obj.getWmtag(),opsDo.getDoId(),pcoId));
    }

    /**
     * 订单重新分配-验证库存是否满足条件
     * 1.如分配结果为全部出库存，则执行还原处理，删除原采购单
     * 2.如分配结果数量拆分，部分出库存，部分出采购，则执行还原出库，删除原采购单，发行新采购且保持拦截状态。
     * 3.如分配结果型号拆分，全部出库存，则执行还原出库，删除原采购单。
     * 4.如分配结果型号拆分，子型号采购，则不执行还原出库。
     * 5.如分配结果全部采购，则不执行还原处理
     */
    public Boolean checkReleaseOrderInv(String orderId , String orderItem) throws OpsException{
        Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(orderId);
        Rcvdetail rcvdetail = wmRouterOrderService.getRcvdetail(orderId, orderItem);
        Orderdlvdata orderdlvdata = orderDlvDataService.getOrderDlvDataByOrder(orderId,0);
        if(Objects.isNull(orderdlvdata)){
            orderdlvdata = orderDlvDataService.getOrderDlvDataByOrder(orderId,Integer.parseInt(orderItem));
        }
        if (Objects.isNull(orderdlvdata)) {
            throw Exceptions.OpsException("单号：" + rcvmaster.getRorderNo() + " ; orderdlvdata表无数据");
        }
        InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto(rcvmaster, rcvdetail, orderdlvdata);
        InventoryCkByOrderOutDto outDto = allotInvenToryService.getOpsInventoryListByCk(inputDto);
        if (inputDto.isAllotStatus()){
            // 进行订单还原
            return true;
        }else {
            if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())){
                return false;
            }else if(DoSourceEnum.ASSQTY.equals(outDto.getDoSourceEnum()) && inputDto.getAllotQuantity() > 0){
                // bugid:20641 验证最小包装
                allotInvenToryService.minPack(inputDto.getModelno(), inputDto, outDto);
                if(inputDto.getAllotQuantity() > 0){
                    // 进行订单还原
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 订单转定验证库存是否满足
     */
    public ZDInventoryInfoDTO checkZDOrderInv(InterceptPoAutoReleaseInvDto obj) throws OpsException{
        Integer qty = 0;
        String modelNo = "";
        List<ZDInventoryInfoDTO> out = new ArrayList<ZDInventoryInfoDTO>();
        if(obj.getHandleFlag() == 2){
            OpsPcoItem pcoItem = opsPcoService.getPcoItemByPcoId(obj.getPcoId(), obj.getSplititemno());
            qty = pcoItem.getSubQty();
            modelNo = pcoItem.getSubModelno();
        }else {
            OpsDoItem doItem = baseDoService.getDoItemByDoId(obj.getDoId());
            qty = doItem.getQty();
            modelNo = doItem.getModelno();
        }
        obj.setModelNo(modelNo);
        obj.setQty(qty);
        zdService.showInvByItemQty(obj.getOrderno(),obj.getItemno(),qty,modelNo,out);
        if(CollectionUtils.isNotEmpty(out)){
            return out.get(0);
        }else {
            return null;
        }
    }

    /**
     * 构造还原参数
     */
    public OrderChangeToInitStatusDto constructReleaseParam(InterceptPoAutoReleaseInvDto obj){
        OrderChangeToInitStatusDto inputDto = new OrderChangeToInitStatusDto();
        inputDto.setDelPo(true);
        inputDto.setOrderId(obj.getOrderno());
        inputDto.setOrderItem(obj.getItemno()+"");
        inputDto.setUserName("job");
        List<PurchaseInfoForCancel> listPo = new ArrayList<>();
        PurchaseInfoForCancel poInfo = new PurchaseInfoForCancel();
        poInfo.setMerge(false);
        poInfo.setRequestno(obj.getOrderno());
        poInfo.setRequestItemno(obj.getItemno());
        poInfo.setRequestSplitno(obj.getSplititemno());
        listPo.add(poInfo);
        inputDto.setPo(listPo);
        return inputDto;
    }

    /**
     * 构造还原参数
     */
    public ZDPageShowOrderBindInvDto constructZDParam(InterceptPoAutoReleaseInvDto obj , ZDInventoryInfoDTO out){
        ZDPageShowOrderBindInvDto param = new ZDPageShowOrderBindInvDto();
        //无关联关系 订单信息
        param.setExceptionOrNot(true);
        param.setUserName("job");
        param.setOrderId(obj.getOrderno());
        param.setOrderItem(obj.getItemno()+"");
        param.setDbOrNot(false);
        param.setDoId(obj.getDoId());
        param.setQty(obj.getQty());
        if(obj.getHandleFlag() == 2){
            param.setAssModleFlag(true);//拆分类型 2 拆分型号
            param.setAssNum(obj.getSplititemno());
            param.setNum(obj.getSplititemno());
            param.setPcoId(obj.getPcoId());
            param.setPcoItem(obj.getSplititemno());
        }else {
            param.setAssModleFlag(false);//拆分类型 2 拆分型号
            param.setNum(obj.getSplititemno());
        }
        param.setToInvId(out.getInventoryId());
        // bugid:20641 C14717 风险在库
        param.setToInvRiskFlag(out.getInvRisk());
        param.setToInvTableType(out.getInventoryTableType());
        param.setModelNo(obj.getModelNo());
        // 采购信息
        param.setDelPoFlag(true);
        PurchaseInfoForCancel poInfo = new PurchaseInfoForCancel();
        poInfo.setMerge(false);
        poInfo.setRequestno(obj.getOrderno());
        poInfo.setRequestItemno(obj.getItemno());
        poInfo.setRequestSplitno(obj.getSplititemno());
        param.setPoInfo(poInfo);
        return param;
    }


    /**
     * 操作转定
     * @param param
     */
    public void handleZD(ZDPageShowOrderBindInvDto param) {
        // 持久化日志初始化
        StringBuilder updateLog = new StringBuilder();
        updateLog.append("转定：po_auto:");
        updateLog.append(param.getUserName());
        updateLog.append(";");
        //1.转定处理
        try {
            // 锁
            // 转定处理
            //zdService.handleOrderBindInvZD(param);
            doPcoLogicCenterService.handleOrderBindInvZD(param);
            // 持久化日志
            updateLog.append("成功;");
            updateLog.append(param.getResultLog());
        }catch (OpsException e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败;");
            updateLog.append(e.getMessage());
            updateLog.append(param.getResultLog());
        }
        catch (Exception e) {
            log.error(param.getOrderId(),e);
            // 持久化日志
            updateLog.append("失败-系统异常;");
            updateLog.append(param.getResultLog());
        }
        //2.删除采购单 返回是否成功不影响接单处理
        if(Objects.nonNull(param.getDelPoFlag()) && param.getDelPoFlag()){
            String poFlag = "失败;";
            // 持久化信息
            updateLog.append("采购取消：");
            try {
                //采购单取消
                CommonResult<Boolean> poResult = zdService.ZDDelPo(param.getPoInfo());
                if(poResult.isSuccess()){
                    poFlag = "成功;";
                }
                log.info("转定取消采购单:{}",poResult.isSuccess());
            } catch (Exception e) {
                log.error("转定取消采购单失败:", e);
            }
            // 持久化信息
            updateLog.append(poFlag);
        }
        //bugid:14883 c14717 20240730
        if(Objects.isNull(param.getDelPoFlag())){
            updateLog.append(";采购标识为空");
        }
        //4.记录日志 持久化日志信息
        OpsOrderUpdateLog log = new OpsOrderUpdateLog();
        log.setOrderid(param.getOrderId());
        log.setOrderItem(Integer.parseInt(param.getOrderItem()));
        log.setParams(JSONUtil.toJsonStr(param));
        log.setResult(updateLog.toString());
        log.setCreateTime(DateUtil.getNow());
        opsOrderUpdateLogMapper.insertSelective(log);
    }


    /**
     * 操作还原
     * @param inputDto
     */
    public void orderChangeToInitStatus(OrderChangeToInitStatusDto inputDto) {
        // 1.物流指令取消
        boolean delDoflag = false;
        // 记录日志
        StringBuffer resultLog = new StringBuffer();
        resultLog.append("订单还原:po_auto:");
        resultLog.append(inputDto.getUserName());
        Rcvdetail rcvdetail = null;
        boolean lock = false;
        try {
            log.info("订单还原：{}", JSONUtil.toJsonPrettyStr(inputDto));
            rcvdetail = wmRouterOrderService.getRcvdetail(inputDto.getOrderId(), inputDto.getOrderItem());
            if (Objects.nonNull(rcvdetail)) {
                // 物理指令取消；备注:存在多余调拨单，handConfirm会关联新的指令，存在bug，因此删单时需要同时取消调拨单；
                // 调拨单和交易出库单同时删除成功，才返回成功；
                delDoflag = opsChangeOrderService.orderChangeToInitStatusDelDo(inputDto.getOrderId(),
                        inputDto.getOrderItem(), inputDto.getUserName());
            } else {
                resultLog.append(";无rcvdetail;");
            }
            log.info("订单还原删单标识：{}", delDoflag);
        } catch (OpsException ex) {
            resultLog.append(";物流指令取消：").append(ex.getMessage());
            log.error("订单还原删单", ex);
        } catch (Exception ex) {
            log.error("订单还原删单", ex);
        }
        // 记录日志
        resultLog.append(";物流指令取消：").append(delDoflag);
        // 2.物流指令取消成功
        if (delDoflag) {
            // 3.删除采购单 返回是否成功不影响接单处理
            if (inputDto.isDelPo()) {
                resultLog.append(";采购取消：");
                boolean poFlag = false;
                try {
                    // 采购单取消
                    CommonResult<Boolean> poResult = opsChangeOrderService
                            .orderChangeToInitStatusDelPo(inputDto.getPo().get(0));
                    if (poResult.isSuccess()) {
                        poFlag = true;
                    }
                    log.info("订单还原取消采购单:{}", poResult.isSuccess());
                } catch (Exception e) {
                    log.error("订单还原取消采购单失败:", e);
                }
                resultLog.append(poFlag);
            }
            // 3.1清空状态
            try {
                baseOrderAssignResultService.deleteResultOrder(inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
            } catch (OpsException e) {
                log.error("订单还原清空状态失败:", e);
            }
            // bugid:10570 20230424 C14717
            RcvdetailExample exaSttatus = new RcvdetailExample();
            exaSttatus.createCriteria().andRorderNoEqualTo(inputDto.getOrderId())
                    .andRorderItemEqualTo(Integer.parseInt(inputDto.getOrderItem()))
                    .andStatusEqualTo(RcvOrderStatusEnum.CKING.getType());
            Rcvdetail upStatus = new Rcvdetail();
            upStatus.setStatus(RcvOrderStatusEnum.WAITCK.getType());
            wmRouterOrderService.updateRcvdetail(upStatus, exaSttatus);

            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(inputDto.getOrderId());
            // 5.接单处理，生成新的物流指令，
            try {
                // bugid:10691 20230509 c14717
                retryCreateOrder(rcvmaster, rcvdetail);
                resultLog.append(";订单分配：true");
            } catch (OpsException ex) {
                String msg = "创建订单失败";
                if(Objects.nonNull(ex.getMessage())){
                    if(ex.getMessage().contains("请购单已存在")){
                        msg = "请购单已存在，请先处理请购单";
                    }
                }
                resultLog.append(";订单分配：").append(ex.getMessage());
                // 异常处理 改状态
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-"+msg);
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
            } catch (Exception ex) {
                // 异常处理 改状态
                RcvdetailExample example = new RcvdetailExample();
                example.createCriteria().andRorderNoEqualTo(rcvdetail.getRorderNo())
                        .andRorderItemEqualTo(rcvdetail.getRorderItem());
                Rcvdetail up = new Rcvdetail();
                up.setStatus((short) 10);//
                up.setUpdateTime(DateUtil.getNow());
                up.setExpMsg("订单还原-创建订单失败");
                wmRouterOrderService.updateRcvdetail(up, example);
                log.error(rcvdetail.getRorderFno(), ex);
                resultLog.append(";订单分配：失败");
            }
        }
        // 记录日志 //bugid:10691 20230509 c14717
        OpsOrderUpdateLog log = new OpsOrderUpdateLog();
        log.setOrderid(inputDto.getOrderId());
        log.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
        log.setParams(JSONUtil.toJsonStr(inputDto));
        log.setResult(resultLog.toString());
        log.setCreateTime(DateUtil.getNow());
        opsOrderUpdateLogMapper.insertSelective(log);
        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_REALLOT, inputDto.getOrderId(), Integer.valueOf(inputDto.getOrderItem()));
    }

    /**
     * bugid 10961 订单还原重试次数
     *
     * @param rcvmaster
     * @param rcvdetail
     * @throws OpsException
     */
    public void retryCreateOrder(Rcvmaster rcvmaster, Rcvdetail rcvdetail) throws OpsException {
        ResultVo<DataTypeVO> info = dictDataServiceFeignApi.getDataTypeCodesInfo("1025", "count");
        int countFlag = Integer.parseInt(info.getData().getExtNote1());
        for (int i = 0; i < countFlag; i++) {
            try {
                Date allotStartTime = DateUtil.getNow();
                List<OpsOrderAssignResult> list = wmDispatchService.OrderdispatchForOrderItem(rcvmaster, rcvdetail,
                        true);
                Date allotEndTime = DateUtil.getNow();
                // 接单处理状态
                try {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("allotStartTime", allotStartTime);
                    map.put("allotEndTime", allotEndTime);
                    map.put("assignResult", list);
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_ALLOT, rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), map);
                } catch (Exception ex) {
                    log.error(rcvdetail.getRorderFno(), ex);
                }
                return;
            } catch (OpsException ex) {
                if (StringUtils.isNotBlank(ex.getMessage()) && ex.getMessage().contains("库存更新异常")) {
                    log.info("订单还原，库存更新异常重新跑单");
                } else {
                    throw ex;
                }
            }
        }
        throw Exceptions.OpsException("订单还原库存更新异常");
    }
}
