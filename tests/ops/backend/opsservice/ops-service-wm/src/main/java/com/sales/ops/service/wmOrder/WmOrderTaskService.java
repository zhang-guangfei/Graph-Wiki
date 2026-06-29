package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.enums.WmOrderTaskEnum;
import com.sales.ops.enums.WmOrderTaskPriorityEnum;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交wms要上传的数据 更新中间表
 * @date ：Created in 2021/10/27 16:58
 */
public interface WmOrderTaskService {

    //bugid:12401 c14717 2023/10/24 根据订单号 4改3 优先级设置为8 * 可以传7位和10位单号 用于上预约
    void changeTaskFlagToTreeByOrder(String orderId, String orderItem) throws OpsException;
    //bugid:12401 c14717 2023/10/24 根据订单号 4改3  * 根据指令号（doid或pcoid） 用于越库优先级设置为9 上预约 优先级设置为8
    void changeTaskFlagToTreeByDOIdOrPCOId(String wmOrderId , WmOrderTaskPriorityEnum priorityEnum) throws OpsException;

    void updateOpsWmOrderTaskFlagByCondition(OpsWmOrderTaskCondition condition) throws OpsException;

    void delWmOrderTask(String wmOrderId, String msg) throws OpsException;

    void updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(String wmOrderId, String msg, Integer flag);

    //void addOpsWmOrderTask(OpsWmOrderTask opsWmOrderTask);

    OpsWmOrderTask createWmTaskPo(String wmOrderId, WmOrderTaskEnum orderType, WmOrderTaskEnum taskType,
                                  SendStatusEnum flag, CreateInfoDto createInfo, Date sendSuccessTime, Integer delflag,String msg);

    void addOpsWmOrderTask(OpsWmOrderTask opsWmOrderTask);

    void addBatchOpsWmOrderTask(List<OpsWmOrderTask> list);

    void addRoTask(String roId);

    void updateBatchChangeTaskFlagToTree(List<String> wmOrderIds) throws OpsException;

    void updateWmOrderTaskFByConditionBetweenDetail(String wmOrderId, WmOrderTaskEnum taskEnum, Integer flag, String msg) throws OpsException;

    void updateWmsOrderTaskFailureById(Long Id, String msg);

    /**
     * 物理删除
     * @param wmOrderId
     * @param wmOrderType
     * @param taskType
     */
    void delWmsOrderTaskByWmOrderId(String wmOrderId,String wmOrderType,String taskType)throws OpsException;

    /**
     * 更新状态
     * @param wmOrderId
     * @param wmOrderType
     * @param taskType
     */
    void updateWmsOrderTaskFlagWaitByWmOrderId(String wmOrderId,String wmOrderType,String taskType)throws OpsException;

    void updateTaskFlagToThree();

    void updateRcvdetailStatusTenToInit();

    /**
     * 调用wms接口 根据doId 删除wms指令 ops指令不动
     * @param doId
     * @param havePco true pco和do 打包删除 false 只删除do
     * @return
     */
    boolean getMidDoIdTableCancelWmsDoAndPco(String doId ,boolean havePco);
    
    /**
     * @description 查询DoId或PcoId是否下发
     * @author C12961
     * @date 2023/3/16 16:05
     */
    Integer getTaskFlag(String doId) throws OpsException;
    
}
