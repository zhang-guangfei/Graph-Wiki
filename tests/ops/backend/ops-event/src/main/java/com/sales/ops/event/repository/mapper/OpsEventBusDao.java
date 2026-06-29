package com.sales.ops.event.repository.mapper;

import com.sales.ops.event.repository.entity.OrderEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/7/2 9:20
 */
@Mapper
public interface OpsEventBusDao {

    @Select("select * from ops_event_bus where id=#{busId}")
    OrderEvent scanEventsByBusId(Long busId);

    @Select("select top ${num} * from ops_event_bus where deal_flag=0 order by id")
    List<OrderEvent> scanEventsByCount(int num);

    @Update("update ops_event_bus set deal_flag = 1,version = version+1, modify_time=#{modifyTime}  where id = #{id}  and version=#{version} and deal_flag = 3 ")
    int updateEventToHandled(Long id, Long version, Date modifyTime);

    @Update("update ops_event_bus set deal_flag = 2,version = version+1, remark=#{remark}, modify_time=#{modifyTime} where id = #{id} ")
    int updateEventToFailure(Long id, String remark, Date modifyTime);

    @Update("update ops_event_bus set deal_flag = 0,version = version+1, remark=#{remark}, modify_time=#{modifyTime} where id = #{id} ")
    int updateEventToInit(Long id, String remark, Date modifyTime);

    @Update("update ops_event_bus set deal_flag = 3, modify_time=#{modifyTime}  where id = #{id}  and version=#{version}")
    int updateEventToHandling(Long id, Long version, Date modifyTime);

}
