package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ErrorMsgNoticeConfig;
import com.sales.ops.db.entity.OpsDeliveryEstimateList;
import com.sales.ops.db.entity.OpsDeliveryEstimateResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/11/9 9:11
 */
public interface DeliveryEstimateListDao {

    @Select("select top ${limit} * from ${tableName} where status = #{status} order by id")
    List<OpsDeliveryEstimateList> getListTable( @Param("tableName") String tableName,@Param("limit") Long limit, @Param("status") String status);


    @Select("select top ${limit} * from ops_delivery_estimate_list where status = #{status} order by id")
    List<OpsDeliveryEstimateList> getList( @Param("limit") Long limit, @Param("status") String status);

    @Select("select top ${limit} * from ops_delivery_estimate_list where status = 0 and partation = #{partation} order by id")
    List<OpsDeliveryEstimateList> getListByRemain( @Param("limit") Long limit, @Param("partation") String partation);


    @Update("update ${tableName} set status= 1 where status = 0 and id  BETWEEN #{beginId} and #{endId} ")
    Integer upStatusTable(@Param("tableName") String tableName,@Param("beginId") Long beginId, @Param("endId") Long endId);

    @Select("SELECT top 1 * from ops_delivery_estimate_result where association_key = #{associationKey} order by create_time desc")
    OpsDeliveryEstimateResult getResultByKey(@Param("associationKey") String associationKey);

    // 查询可执行job
    @Select("select job_status  from ops_sync_delivery_manage WHERE job_name  = #{jobName}  AND del_flag =0")
    Integer getBeginJobBySyncDeliveryManageStatus(@Param("jobName") String jobName);

    // 更新job为完成状态
    @Update("update ops_sync_delivery_manage set job_status = #{jobStatus} , update_time = GETDATE()  WHERE job_name  = #{jobName} AND job_status = 1 AND del_flag =0")
    Integer upFinishSyncDeliveryManageStatus(@Param("jobName") String jobName, @Param("jobStatus") Integer jobStatus);

    // 更新job为完成状态
    @Update("update ops_sync_delivery_manage set job_status = #{jobStatus} , update_time = GETDATE()  WHERE group_name  = #{groupName} AND job_status = 0 AND del_flag =0")
    Integer upStartSyncDeliveryManageStatus(@Param("groupName") String groupName, @Param("jobStatus") Integer jobStatus);


    @Select("SELECT key_msg,sub_flag,sub_close,sub_open from error_msg_notice_config where delflag = 0")
    List<ErrorMsgNoticeConfig> getErrorMsgs();






}
