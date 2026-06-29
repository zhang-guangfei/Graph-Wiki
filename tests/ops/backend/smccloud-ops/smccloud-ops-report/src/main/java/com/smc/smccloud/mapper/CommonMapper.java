package com.smc.smccloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author lyc
 * @Date 2025/8/18 10:05
 * @Descripton TODO
 */
@Mapper
public interface CommonMapper {

    @Insert("insert into ops_log.dbo.ops_sys_log(title,createid,createtime,params) values(#{title},#{optUser},getDate(),#{methodParams})")
    void insertSysLog(@Param("title") String title, @Param("optUser") String optUser, @Param("methodParams") String methodParams);


    @Select("select count(1) from ops_sharedb.dbo.ops_as_wms_task_notice where job_name= #{jobName} and job_status=1 and pd_batch_no = #{batchNo}")
    int selCountExecYdOk(@Param("jobName") String jobName, @Param("batchNo") String batchNo);
}
