package com.smc.smccloud.mapper.kettle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.kettle.KettleJobManageDO;
import com.smc.smccloud.model.kettle.KettleNoticeTaskDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lyc
 * @Date 2023/4/21 15:29
 * @Descripton TODO
 */
@DS("kettledb")
@Mapper
public interface KettleNoticeTaskMapper extends BaseMapper<KettleNoticeTaskDO>  {

    @Insert("insert into ops_sharedb.dbo.kettle_notice_task (" +
            " mdm_table_name," +
            " job_name," +
            " error_msg," +
            " create_time," +
            " update_time," +
            " opt_status," +
            " mdm_batch_no," +
            " last_update_time) " +
            " values(" +
            "#{item.mdmTableName}," +
            "#{item.jobName}," +
            "#{item.errorMsg}," +
            "getDate()," +
            "getDate()," +
            "#{item.optStatus}," +
            "#{item.mdmBatchNo}," +
            "#{item.lastUpdateTime}" +
            ") ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTask(@Param("item") KettleNoticeTaskDO item);
}
