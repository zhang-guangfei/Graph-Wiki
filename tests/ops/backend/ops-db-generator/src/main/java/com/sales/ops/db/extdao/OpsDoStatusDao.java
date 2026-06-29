package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface OpsDoStatusDao {

    @Update("update ops_do set wms_status = #{wmsStatus} , wave_time = #{waveTime} , wms_expected_delivery_date = #{expectedDeliveryDate}  ,modify_time=getdate()  where do_id = #{doId} and delflag = 0 ")
    int updateDoStatus(String doId, Integer wmsStatus, Date waveTime, Date expectedDeliveryDate);


    @Update("update ops_do set do_ready_time=#{readyTime},modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoReadyTime(String doId, Date readyTime);

    @Update("update ops_do set db_ready_time=#{readyTime},modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoEntryTime(String doId, Date readyTime);


}
