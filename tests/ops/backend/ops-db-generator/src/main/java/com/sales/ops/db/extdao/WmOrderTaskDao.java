package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.ProductBom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/10/28 12:05
 */
public interface WmOrderTaskDao {


    /**
     * bugid: 12401 c14717 2023/10/24 增加算货齐查询 优先级  ORDER BY  priority desc
     * @param orderType
     * @param flag
     * @param limit
     * @return
     */
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderType(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskType(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    //拆分id 1
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and id % 5=0 and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeOne(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and id % 5=0 and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskTypeOne(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);


    //拆分id 2
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and id % 5=1 and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeTwo(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and id % 5=1 and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskTypeTwo(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    //拆分id 3
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and id % 5=2 and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeThree(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and id % 5=2 and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskTypeThree(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);


    //拆分id 4
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and id % 5=3 and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeFour(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and id % 5=3 and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskTypeFour(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);


    //拆分id 5
    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where wm_order_type = #{orderType} and id % 5=4 and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeFive(@Param("orderType") String orderType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Select("select top ${limit} ID,wm_order_id ,wm_order_type,task_type ,flag ,retry_num ,msg ,cre_time ,creator ,modify_time ,modifier from ops_wm_order_task where  wm_order_type = #{orderType} and id % 5=4 and task_type = #{taskType}  and flag = #{flag} and delflag = 0 ORDER BY  priority desc")
    List<OpsWmOrderTask>  getTaskByLimitAndOrderTypeAndTaskTypeFive(@Param("orderType") String orderType,@Param("taskType") String taskType,@Param("flag") Integer flag,@Param("limit") Integer limit);

    @Update("update ops_wm_order_task set flag = 3 where flag = 4 and wm_order_type = 'PCO' and delflag = 0")
    void updateTaskPcoFlagFourToThree();

    @Update("update ops_wm_order_task set flag = 3 where flag = 4 and wm_order_type = 'DO' and delflag = 0")
    void updateTaskDoFlagFourToThree();

    @Update("update rcvdetail set status =1 where status =10 and delete_status =0")
    void updateRcvdetailStatusTenToInit();

    /**
     * bugid:13892 指令下发失败重试信息表
     * @return
     */
    @Select("SELECT key_msg from OPS_ORDER_TASK_REPEAT_POST where delflag = 0")
    List<String> getWMSReturnErrorRepeatMsg();
}
