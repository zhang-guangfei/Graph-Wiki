package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.OpsSyncDeliveryManageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2025/6/26 13:11
 * @Descripton TODO
 */
@Mapper
@DS("delivery")
public interface KettleCommonMapper {

    @Select("select exec_time from ops_sync_delivery_manage where job_name = #{jobName} and del_flag = 0 ")
    OpsSyncDeliveryManageDO getOpsSyncDeliveryManageDO(@Param("jobName")String jobName);

    @Select("update ops_sync_delivery_manage set exec_time = #{execTime} where job_name = #{jobName} and del_flag = 0 ")
    void updateOpsSyncDeliveryManageDO(@Param("jobName")String jobName,@Param("execTime") Date execTime);

}
