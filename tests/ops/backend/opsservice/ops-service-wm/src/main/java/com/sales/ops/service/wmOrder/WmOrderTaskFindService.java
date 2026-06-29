package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.enums.WmOrderTaskEnum;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交wms要上传的数据 查询中间表
 * @date ：Created in 2021/10/27 16:58
 */
public interface WmOrderTaskFindService {

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailOne(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailTwo(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailThree(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailFour(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetail(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailFive(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByCondition(OpsWmOrderTaskCondition condition) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionOne(OpsWmOrderTaskCondition condition) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionTwo(OpsWmOrderTaskCondition condition) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionThree(OpsWmOrderTaskCondition condition) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionFour(OpsWmOrderTaskCondition condition) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionFive(OpsWmOrderTaskCondition condition) throws OpsException;

    /* 查询需要上传wms的数据
    *
            * @param id
    * @return
            * @throws OpsException
    */
    OpsWmOrderTask searchOpsWmOrderTaskById(Long id) throws OpsException;

    List<OpsWmOrderTask> searchOpsWmOrderTaskDOandPco(OpsWmOrderTaskCondition condition) throws OpsException;

    OpsWmOrderTask findWmsOrderTaskByWmOrderId(String wmOrderId, WmOrderTaskEnum taskEnum);


}
