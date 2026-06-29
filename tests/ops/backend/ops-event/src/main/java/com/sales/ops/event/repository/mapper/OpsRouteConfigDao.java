package com.sales.ops.event.repository.mapper;

import com.sales.ops.event.repository.entity.OrderEvent;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OpsRouteConfigDao {


    @Select("select distinct queue_name from ops_event_config_binding where event_code = #{eventCode} and status=1 ")
    List<String> selectByEventCode(String eventCode);
    @Insert("<script> " +
            "insert into ${queue} <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "        event.bus_id, " +
            "        event.id, " +
            "        event.order_id, " +
            "        event.order_item, " +
            "        event.split_no, " +
            "        event.event_code, " +
            "        event.deal_flag, " +
            "        event.cre_time, " +
            "        event.creator, " +
            "        event.modify_time, " +
            "        event.modifier, " +
            "        event.remark, " +
            "        event.version, " +
            "        event.params, " +
            "    </trim> " +
            "values " +
            "<foreach collection='list' item='event' separator=','>" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "        #{event.busId,jdbcType=BIGINT}, " +
            "        #{event.id,jdbcType=BIGINT}, " +
            "        #{event.orderId,jdbcType=VARCHAR}, " +
            "        #{event.orderItem,jdbcType=VARCHAR}, " +
            "        #{event.splitNo,jdbcType=INTEGER}, " +
            "        #{event.eventCode,jdbcType=VARCHAR}, " +
            "        #{event.dealFlag,jdbcType=INTEGER}, " +
            "        #{event.creTime,jdbcType=TIMESTAMP}, " +
            "        #{event.creator,jdbcType=VARCHAR}, " +
            "        #{event.modifyTime,jdbcType=TIMESTAMP}, " +
            "        #{event.modifier,jdbcType=VARCHAR}, " +
            "        #{event.remark,jdbcType=NVARCHAR}, " +
            "        #{event.version,jdbcType=BIGINT}, " +
            "        #{event.params,jdbcType=LONGNVARCHAR}, " +
            "    </trim> " +
            "</foreach>" +
            "</script>")
    int insertIntoEventQueueBatch(List<OrderEvent> list, @Param("queue") String queue);

    @Insert("<script> " +
            "insert into ${queue} <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='event.busId != null'> " +
            "        event.bus_id, " +
            "      </if> " +
            "      <if test='event.id != null'> " +
            "        event.id, " +
            "      </if> " +
            "      <if test='event.orderId != null'> " +
            "        event.order_id, " +
            "      </if> " +
            "      <if test='event.orderItem != null'> " +
            "        event.order_item, " +
            "      </if> " +
            "      <if test='event.splitNo != null'> " +
            "        event.split_no, " +
            "      </if> " +
            "      <if test='event.eventCode != null'> " +
            "        event.event_code, " +
            "      </if> " +
            "      <if test='event.dealFlag != null'> " +
            "        event.deal_flag, " +
            "      </if> " +
            "      <if test='event.creTime != null'> " +
            "        event.cre_time, " +
            "      </if> " +
            "      <if test='event.creator != null'> " +
            "        event.creator, " +
            "      </if> " +
            "      <if test='event.modifyTime != null'> " +
            "        event.modify_time, " +
            "      </if> " +
            "      <if test='event.modifier != null'> " +
            "        event.modifier, " +
            "      </if> " +
            "      <if test='event.remark != null'> " +
            "        event.remark, " +
            "      </if> " +
            "      <if test='event.version != null'> " +
            "        event.version, " +
            "      </if> " +
            "      <if test='event.params != null'> " +
            "        event.params, " +
            "      </if> " +
            "    </trim> " +
            "values " +
            "    <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='event.busId != null'> " +
            "        #{event.busId,jdbcType=BIGINT}, " +
            "      </if> " +
            "      <if test='event.id != null'> " +
            "        #{event.id,jdbcType=BIGINT}, " +
            "      </if> " +
            "      <if test='event.orderId != null'> " +
            "        #{event.orderId,jdbcType=VARCHAR}, " +
            "      </if> " +
            "      <if test='event.orderItem != null'> " +
            "        #{event.orderItem,jdbcType=VARCHAR}, " +
            "      </if> " +
            "      <if test='event.splitNo != null'> " +
            "        #{event.splitNo,jdbcType=INTEGER}, " +
            "      </if> " +
            "      <if test='event.eventCode != null'> " +
            "        #{event.eventCode,jdbcType=VARCHAR}, " +
            "      </if> " +
            "      <if test='event.dealFlag != null'> " +
            "        #{event.dealFlag,jdbcType=INTEGER}, " +
            "      </if> " +
            "      <if test='event.creTime != null'> " +
            "        #{event.creTime,jdbcType=TIMESTAMP}, " +
            "      </if> " +
            "      <if test='event.creator != null'> " +
            "        #{event.creator,jdbcType=VARCHAR}, " +
            "      </if> " +
            "      <if test='event.modifyTime != null'> " +
            "        #{event.modifyTime,jdbcType=TIMESTAMP}, " +
            "      </if> " +
            "      <if test='event.modifier != null'> " +
            "        #{event.modifier,jdbcType=VARCHAR}, " +
            "      </if> " +
            "      <if test='event.remark != null'> " +
            "        #{event.remark,jdbcType=NVARCHAR}, " +
            "      </if> " +
            "      <if test='event.version != null'> " +
            "        #{event.version,jdbcType=BIGINT}, " +
            "      </if> " +
            "      <if test='event.params != null'> " +
            "        #{event.params,jdbcType=LONGNVARCHAR}, " +
            "      </if> " +
            "    </trim> " +
            "</script>")
    int insertIntoEventQueue(@Param("event") OrderEvent eventBus, @Param("queue") String queue);

    @Select(
            "select top ${num} e.* from ${queueName} e\n" +
            "left join ops_event_config_binding c on e.event_code=c.event_code and c.queue_name= #{queueName} \n" +
            "where deal_flag=0 and c.group_id = #{groupId}  order by id")
    List<OrderEvent> scanEventsByCount(String queueName, int num,  Integer groupId);

    @Update("update ${queueName} set deal_flag = 1, version = version+1, modify_time=#{date}  where id = #{id}  and version=#{version}")
    int updateEventToHandled(String queueName, Long id, Long version, Date date);

    @Update("update ${queueName} set deal_flag = #{dealFlag}, version = version+1, remark=#{remark}, modify_time=#{date} where id = #{id} ")
    int updateEventDealFlag(String queueName, Long id, String remark, Date date, Integer dealFlag);


    @Select("select * from ${queueName} e\n" +
            "where id=#{id}")
    OrderEvent scanEventId(String queueName, long id);

}
