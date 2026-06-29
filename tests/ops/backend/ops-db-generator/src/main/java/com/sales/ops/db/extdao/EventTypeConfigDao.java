package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author C12961
 * @date 2022/7/2 9:20
 */
public interface EventTypeConfigDao {

    @Select("select event_code from event_type_config where enable_status=1 ")
    List<String> getStatusEventCode();


    @Select("select event_code from event_type_config where enable_allot=1 ")
    List<String> getAllotEventCode();


    @Select("select event_code from event_type_config where enable_plan=1 ")
    List<String> getPlanEventCode();


}
