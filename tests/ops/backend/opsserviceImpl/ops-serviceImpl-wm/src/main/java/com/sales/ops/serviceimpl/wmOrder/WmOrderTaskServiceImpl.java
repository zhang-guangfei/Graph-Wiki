package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.batchdao.WmOrderTaskBatchDao;
import com.sales.ops.db.dao.OpsWmOrderTaskMapper;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.OpsWmOrderTaskExample;
import com.sales.ops.db.extdao.WmOrderTaskDao;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsPcoService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import com.sales.ops.utils.WmTaskFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交wms要上传的数据
 * @date ：Created in 2021/10/27 17:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class WmOrderTaskServiceImpl implements WmOrderTaskService {

    @Autowired
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;

    @Autowired
    private WmOrderTaskDao wmOrderTaskDao;

    @Autowired
    private WmOrderTaskBatchDao wmOrderTaskBatchDao;

    @Autowired
    private BaseDoService baseDoService;


    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    /**
     * bugid:12401 c14717 2023/10/20
     * 根据订单号 4改3 优先级设置为8
     * 可以传7位和10位单号
     */
    @Override
    public void changeTaskFlagToTreeByOrder(String orderId, String orderItem) throws OpsException{
        //1.查询指令
        List<OpsDo> doList = baseDoService.findAllJYCKByOrder(orderId, orderItem);
        List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(orderId, orderItem);
        //2.收集指令
        List<String> wmOrderIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(doList)){
            for(OpsDo opsDo : doList){
                //没下发指令
                if(opsDo.getIsWms() ==0 && !DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())){
                    wmOrderIds.add(opsDo.getDoId());
                }
            }
        }
        if(CollectionUtils.isNotEmpty(pcoList)){
            for(OpsPco opsPco : pcoList){
                //没下发指令
                if(opsPco.getIsWms() ==0 ){
                    wmOrderIds.add(opsPco.getPcoId());
                }
            }
        }
        //3.批量改指令
        int CAL_SIZE = 200;
        for (int i = 0; i < wmOrderIds.size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<String> temp = new ArrayList<String>();
                if (i + CAL_SIZE < wmOrderIds.size()) {
                    temp = wmOrderIds.subList(i, i + CAL_SIZE);
                } else {
                    temp = wmOrderIds.subList(i, wmOrderIds.size());
                }
                //批量更新
                updateBatchChangeTaskFlagToTree(temp);
            }
        }
    }

    /**
     * bugid:12401 c14717 2023/10/20
     * 根据订单号 4改3 优先级设置为9
     * 根据doId 和 pcoId
     */
    @Override
    public void changeTaskFlagToTreeByDOIdOrPCOId(String wmOrderId,WmOrderTaskPriorityEnum priorityEnum) throws OpsException{
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andFlagEqualTo(4);//挂起修改
        cri.andDelflagEqualTo(0);
        cri.andWmOrderIdEqualTo(wmOrderId);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(3);
        record.setModifyTime(DateUtil.getNow());
        record.setModifier("ro收货");
        record.setMsg(priorityEnum.getDesc());
        record.setPriority(priorityEnum.getType());//最高优先级9
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }


    /**
     * 更新上传wms状态
     */
    @Override
    public void updateOpsWmOrderTaskFlagByCondition(OpsWmOrderTaskCondition condition) throws OpsException {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(condition.getWmOrderId());
        //cri.andFlagEqualTo(0);//失败不传
        cri.andDelflagEqualTo(0);
        if (StringUtils.isNotEmpty(condition.getWmOrderType())) {
            cri.andWmOrderTypeEqualTo(condition.getWmOrderType());
        }
        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            cri.andTaskTypeEqualTo(condition.getTaskType());
        }

        OpsWmOrderTask record = new OpsWmOrderTask();
        if (Objects.nonNull(condition.getFlag())) {
            record.setDelflag(condition.getFlag());
        }
        record.setModifyTime(new Date());
        record.setModifier("wms");
        if (Objects.nonNull(condition.getDelFlag())) {
            record.setDelflag(condition.getDelFlag());
        }
        if (StringUtils.isNotBlank(condition.getMsg())) {
            record.setMsg(condition.getMsg());
        }
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }

    @Override
    public void delWmOrderTask(String wmOrderId, String msg) throws OpsException {
        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setWmOrderId(wmOrderId);
        condition.setDelFlag(1);
        condition.setMsg(msg);
        updateOpsWmOrderTaskFlagByCondition(condition);

    }

    @Override
    public void updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(String wmOrderId, String msg, Integer flag) {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(wmOrderId);
        cri.andDelflagEqualTo(0);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(flag);
        record.setModifyTime(new Date());
        record.setModifier("wms");
        record.setMsg(msg);
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }

    /**
     * //bugid:12294 c14717 20231019
     * @param wmOrderId
     * @param orderType
     * @param taskType
     * @param flag
     * @param createInfo
     * @param sendSuccessTime
     * @param delflag
     * @param msg
     * @return
     */
    @Override
    public OpsWmOrderTask createWmTaskPo(String wmOrderId, WmOrderTaskEnum orderType, WmOrderTaskEnum taskType,
                                         SendStatusEnum flag, CreateInfoDto createInfo, Date sendSuccessTime, Integer delflag,
                                         String msg) {
        OpsWmOrderTask task = new OpsWmOrderTask();
        task.setWmOrderId(wmOrderId);
        task.setWmOrderType(orderType.getType());
        task.setTaskType(taskType.getType());
        task.setFlag(Integer.valueOf(flag.getType()));
        task.setCreator(createInfo.getCreateUser());
        task.setCreTime(createInfo.getCreateTime());
        task.setSentSuccessTime(sendSuccessTime);
        task.setDelflag(delflag);
        task.setMsg(msg);
        return task;
    }



    /**
     * 单条更新task
     */
    @Override
    public void addOpsWmOrderTask(OpsWmOrderTask opsWmOrderTask) {
        opsWmOrderTaskMapper.insertSelective(opsWmOrderTask);
    }

    /**
     * bugid:12465 c14717
     * 批量插入 task任务
     */
    @Override
    public void addBatchOpsWmOrderTask(List<OpsWmOrderTask> list) {
        for (OpsWmOrderTask opsWmOrderTask : list) {
            if(Objects.isNull(opsWmOrderTask.getDelflag())){
                opsWmOrderTask.setDelflag(0);
            }
        }
        int CAL_SIZE = 150;
        for (int i = 0; i < list.size(); i++) {
            if (i % CAL_SIZE == 0) {
                List<OpsWmOrderTask> temp = new ArrayList<OpsWmOrderTask>();
                if (i + CAL_SIZE < list.size()) {
                    temp = list.subList(i, i + CAL_SIZE);
                } else {
                    temp = list.subList(i, list.size());
                }
                wmOrderTaskBatchDao.wmOrderTaskbatchInsert(temp);
            }
        }
       /* Map<Integer, List<OpsWmOrderTask>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, OpsWmOrderTask.class);
        for (Map.Entry<Integer, List<OpsWmOrderTask>> entry : mapBarcode.entrySet()) {
           wmOrderTaskBatchDao.wmOrderTaskbatchInsert(entry.getValue());
        }*/
    }

    @Override
    public void addRoTask(String roId) {
        OpsWmOrderTask task = WmTaskFactory.RoTask(roId, SendStatusEnum.SUSPENDED, new CreateInfoDto(UserDto.WMS));
        addOpsWmOrderTask(task);
    }


    /**
     * bugid:12401 c14717 2023/10/20
     * 批量 4改3
     * @param wmOrderIds
     * @throws OpsException
     */
    @Override
    public void updateBatchChangeTaskFlagToTree(List<String> wmOrderIds) throws OpsException {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andFlagEqualTo(4);//挂起修改
        cri.andDelflagEqualTo(0);
        cri.andWmOrderIdIn(wmOrderIds);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(3);
        record.setModifyTime(DateUtil.getNow());
        record.setModifier("RO入库");
        record.setMsg("ro入库");
        record.setPriority(8);
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }




    @Override
    public void updateWmOrderTaskFByConditionBetweenDetail(String wmOrderId, WmOrderTaskEnum taskEnum, Integer flag, String msg) throws OpsException {
        //更新order_task barcode状态
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(wmOrderId);
        cri.andWmOrderTypeEqualTo(taskEnum.getType());
        cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
        cri.andFlagBetween(3, 4);//将挂起状态设置为 0初始状态
        cri.andDelflagEqualTo(0);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(flag);
        record.setModifyTime(new Date());
        record.setMsg(msg);
        record.setModifier("xxlJob");
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }

    @Override
    public void updateWmsOrderTaskFailureById(Long Id, String msg) {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andIdEqualTo(Id);
        cri.andDelflagEqualTo(0);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(2);//失败
        record.setModifyTime(new Date());
        record.setMsg(msg);
        //record.setModifier(msg);
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }

    @Override
    public void delWmsOrderTaskByWmOrderId(String wmOrderId, String wmOrderType, String taskType) throws OpsException {
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(wmOrderId);
        cri.andWmOrderTypeEqualTo(wmOrderType);
        cri.andTaskTypeEqualTo(taskType);
        cri.andDelflagEqualTo(0);
        opsWmOrderTaskMapper.deleteByExample(exa);
    }

    @Override
    public void updateWmsOrderTaskFlagWaitByWmOrderId(String wmOrderId, String wmOrderType, String taskType) throws OpsException {
        //更新order_task barcode状态
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(wmOrderId);
        cri.andWmOrderTypeEqualTo(wmOrderType);
        cri.andTaskTypeEqualTo(taskType);
        cri.andDelflagEqualTo(0);
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setFlag(4);//挂起
        record.setModifyTime(new Date());
        record.setMsg("调拨挂起，待下发");
        record.setModifier("wms");
        opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
    }

    /**
     * 更新已读状态重新计算
     */
    @Override
    public void updateTaskFlagToThree() {
        wmOrderTaskDao.updateTaskDoFlagFourToThree();
        wmOrderTaskDao.updateTaskPcoFlagFourToThree();
    }

    /**
     * 更新rcvdetail异常状态为初始状态
     */
    @Override
    public void updateRcvdetailStatusTenToInit() {
        wmOrderTaskDao.updateRcvdetailStatusTenToInit();
    }
    /**
     * 批量取消wms 接口 测试用
     * @param havePco true pco和do 打包删除 false 只删除do
     */
    @Override
    public boolean getMidDoIdTableCancelWmsDoAndPco(String doId, boolean havePco) {
        List<CancelDocOrderDto> cancelOrder = new ArrayList<>();// 交易出库wms报文 包括do和pco db
        try {
            OpsDo opsDo = baseDoService.getDoByDoId(doId);
            if (havePco) {
                getCancelOrder(opsDo, "异常数据,批量操作", cancelOrder);
            } else {
                getCancelOrderNoPco(opsDo, "异常数据,批量操作", cancelOrder);
            }

            if (!CollectionUtils.isEmpty(cancelOrder)) {// 有下发成功的交易出库单
                CommonResult wmJYCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelOrder);// 查看是否可删除
                // 物流已经作业删除失败
                return wmJYCKResult.isSuccess();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public Integer getTaskFlag(String wmId) throws OpsException {
        OpsWmOrderTaskExample example = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = example.createCriteria();
        cri.andWmOrderIdEqualTo(wmId);
        cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
        cri.andDelflagEqualTo(0);
        List<OpsWmOrderTask> tasks = opsWmOrderTaskMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }
        if (tasks.size() > 1) {
            throw Exceptions.OpsException("task重复：" + wmId);
        }
        return tasks.get(0).getFlag();
    }

    private void getCancelOrder(OpsDo opsDo, String reason, List<CancelDocOrderDto> doAndPcoList) throws OpsException {
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            if (opsDo.getDoStatus() > DoStatusEnum.WAIT.getStatus()) {
                throw Exceptions.OpsException("发货中和发货完成不可取消" + opsDo.getDoId());
            }
            CancelDocOrderDto doParam = new CancelDocOrderDto();
            doParam.setWmId(opsDo.getId());
            doParam.setCustomerId("SMC");// 固定不变
            doParam.setDocNo(opsDo.getDoId());// do_id 或pco_id
            if (org.springframework.util.StringUtils.isEmpty(reason)) {
                //throw Exceptions.OpsException("wms需要取消订单原因", opsDo.getDoId());
                //bugid : 8811 c14717 20221130
                reason = "OPS取消指令";
            }
            doParam.setErpCancelReason(reason);// 取消原因
            doParam.setOrderType("CM"); // 如果是pco 固定kt; 如果是do CM参加DoTypeEnum枚举对应关系的wltype
            doParam.setWarehouseId(opsDo.getWarehouseCode());
            doAndPcoList.add(doParam);
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())
                    && !DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                if (!CollectionUtils.isEmpty(pcoList)) {
                    for (OpsPco opsPco : pcoList) {
                        if (opsPco.getPcoStatus() > DoStatusEnum.WAIT.getStatus()) {
                            throw Exceptions.OpsException("发货中和发货完成不可取消" + opsPco.getPcoId());
                        }
                        CancelDocOrderDto param = new CancelDocOrderDto();
                        param.setWmId(opsPco.getId());
                        param.setCustomerId("SMC");// 固定不变
                        param.setDocNo(opsPco.getPcoId());// do_id 或pco_id
                        param.setErpCancelReason(reason);// 取消原因
                        param.setOrderType("KT"); // 如果是pco 固定kt; 如果是do 参加DoTypeEnum枚举对应关系的wltype
                        param.setWarehouseId(opsPco.getWarehouseCode());
                        doAndPcoList.add(param);
                    }
                }
            }
        } else {// 其他全部是调拨
            CancelDocOrderDto param = new CancelDocOrderDto();
            param.setWmId(opsDo.getId());
            param.setCustomerId("SMC");// 固定不变
            param.setDocNo(opsDo.getDoId());// do_id 或pco_id
            param.setErpCancelReason(reason);// 取消原因
            param.setWarehouseId(opsDo.getWarehouseCode());
            if (StringUtils.isNotBlank(opsDo.getDoType()) && DoTypeEnum.getType(opsDo.getDoType()) != null) {
                param.setOrderType(DoTypeEnum.getType(opsDo.getDoType()).getWltype()); // 如果是pco 固定kt; 如果是do
                // CM参加DoTypeEnum枚举对应关系的wltype
            } else {
                throw Exceptions.OpsException("doType 为空");
            }
            param.setWarehouseId(opsDo.getWarehouseCode());
            doAndPcoList.add(param);
        }
    }

    private void getCancelOrderNoPco(OpsDo opsDo, String reason, List<CancelDocOrderDto> doAndPcoList) throws OpsException {
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            if (opsDo.getDoStatus() > DoStatusEnum.WAIT.getStatus()) {
                throw Exceptions.OpsException("发货中和发货完成不可取消" + opsDo.getDoId());
            }
            CancelDocOrderDto doParam = new CancelDocOrderDto();
            doParam.setWmId(opsDo.getId());
            doParam.setCustomerId("SMC");// 固定不变
            doParam.setDocNo(opsDo.getDoId());// do_id 或pco_id
            if (org.springframework.util.StringUtils.isEmpty(reason)) {
                //throw Exceptions.OpsException("wms需要取消订单原因", opsDo.getDoId());
                //bugid : 8811 c14717 20221130
                reason = "OPS取消指令";
            }
            doParam.setErpCancelReason(reason);// 取消原因
            doParam.setOrderType("CM"); // 如果是pco 固定kt; 如果是do CM参加DoTypeEnum枚举对应关系的wltype
            doParam.setWarehouseId(opsDo.getWarehouseCode());
            doAndPcoList.add(doParam);
        } else {// 其他全部是调拨
            CancelDocOrderDto param = new CancelDocOrderDto();
            param.setWmId(opsDo.getId());
            param.setCustomerId("SMC");// 固定不变
            param.setDocNo(opsDo.getDoId());// do_id 或pco_id
            param.setErpCancelReason(reason);// 取消原因
            param.setWarehouseId(opsDo.getWarehouseCode());
            if (StringUtils.isNotBlank(opsDo.getDoType()) && DoTypeEnum.getType(opsDo.getDoType()) != null) {
                param.setOrderType(DoTypeEnum.getType(opsDo.getDoType()).getWltype()); // 如果是pco 固定kt; 如果是do
                // CM参加DoTypeEnum枚举对应关系的wltype
            } else {
                throw Exceptions.OpsException("doType 为空");
            }
            param.setWarehouseId(opsDo.getWarehouseCode());
            doAndPcoList.add(param);
        }
    }


}
