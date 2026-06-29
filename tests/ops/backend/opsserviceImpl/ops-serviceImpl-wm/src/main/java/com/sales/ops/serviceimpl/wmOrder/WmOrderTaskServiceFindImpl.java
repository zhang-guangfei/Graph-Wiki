package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.batchdao.WmOrderTaskBatchDao;
import com.sales.ops.db.dao.OpsWmOrderTaskMapper;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.OpsWmOrderTaskExample;
import com.sales.ops.db.extdao.WmOrderTaskDao;
import com.sales.ops.dto.order.OpsWmOrderTaskCondition;
import com.sales.ops.enums.WmOrderTaskEnum;
import com.sales.ops.service.wmOrder.WmOrderTaskFindService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交wms要上传的数据 查询服务
 * @date ：Created in 2021/10/27 17:14
 */
@Service
public class WmOrderTaskServiceFindImpl implements WmOrderTaskFindService {

    @Autowired
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;

    @Autowired
    private WmOrderTaskDao wmOrderTaskDao;

    @Autowired
    private WmOrderTaskBatchDao wmOrderTaskBatchDao;

    /**
     * 查询需要上传wms的数据
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetail(String limit, WmOrderTaskEnum taskEnum,Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByCondition(condition);
    }

    /**
     * 查询需要上传wms的数据 拆id 1
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailOne(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByConditionOne(condition);
    }

    /**
     * 查询需要上传wms的数据 拆id 2
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailTwo(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByConditionTwo(condition);
    }

    /**
     * 查询需要上传wms的数据 拆id 3
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailThree(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByConditionThree(condition);
    }

    /**
     * 查询需要上传wms的数据 拆id 4
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailFour(String limit, WmOrderTaskEnum taskEnum, Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByConditionFour(condition);
    }

    /**
     * 查询需要上传wms的数据 拆id 5
     *
     * @param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionDetailFive(String limit, WmOrderTaskEnum taskEnum,Integer flag) throws OpsException {

        OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
        condition.setLimit(limit);
        condition.setWmOrderType(taskEnum.getType());
        condition.setTaskType(WmOrderTaskEnum.ORDER.getType());
        condition.setFlag(flag);//挂起状态为3
        return searchOpsWmOrderTaskByConditionFive(condition);
    }

    /**
     * 查询需要上传wms的数据
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByCondition(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskType(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderType(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }

    /**
     * 查询需要上传wms的数据拆分Id 1
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionOne(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskTypeOne(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeOne(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }


    /**
     * 查询需要上传wms的数据拆分Id 2
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionTwo(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskTypeTwo(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeTwo(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }

    /**
     * 查询需要上传wms的数据拆分Id 3
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionThree(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskTypeThree(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeThree(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }


    /**
     * 查询需要上传wms的数据拆分Id 4
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionFour(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskTypeFour(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeFour(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }

    /**
     * 查询需要上传wms的数据拆分Id 5
     *
     * @param condition
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskByConditionFive(OpsWmOrderTaskCondition condition) throws OpsException {

        if (StringUtils.isNotEmpty(condition.getTaskType())) {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeAndTaskTypeFive(condition.getWmOrderType(), condition.getTaskType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        } else {
            return wmOrderTaskDao.getTaskByLimitAndOrderTypeFive(condition.getWmOrderType(), condition.getFlag(), Integer.parseInt(condition.getLimit()));
        }
    }


     /* 查询需要上传wms的数据
     *
             * @param condition
     * @return
             * @throws OpsException
     */
    @Override
    public OpsWmOrderTask searchOpsWmOrderTaskById(Long id) throws OpsException {
        return opsWmOrderTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OpsWmOrderTask> searchOpsWmOrderTaskDOandPco(OpsWmOrderTaskCondition condition) throws OpsException{
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andFlagEqualTo(0);//失败不传
        cri.andWmOrderIdIn(condition.getWmOrderIdList());
        cri.andFlagEqualTo(condition.getFlag());
        cri.andDelflagEqualTo(0);
        return opsWmOrderTaskMapper.selectByExample(exa);
    }

    @Override
    public OpsWmOrderTask findWmsOrderTaskByWmOrderId(String wmOrderId, WmOrderTaskEnum taskEnum){
        OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
        OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
        cri.andWmOrderIdEqualTo(wmOrderId);
        cri.andWmOrderTypeEqualTo(taskEnum.getType());
        cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
        cri.andDelflagEqualTo(0);
        List<OpsWmOrderTask>  list =  opsWmOrderTaskMapper.selectByExample(exa);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }
    }


}
