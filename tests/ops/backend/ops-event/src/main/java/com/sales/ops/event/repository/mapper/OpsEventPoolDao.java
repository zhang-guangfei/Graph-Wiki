package com.sales.ops.event.repository.mapper;

import com.sales.ops.event.repository.entity.OrderEvent;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/7/2 9:20
 */
@Mapper
public interface OpsEventPoolDao {

    @Insert("<script> " +
            "insert into ops_event_pool <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "        event.bus_id, " +
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
    int insertIntoEventPoolBatch(List<OrderEvent> list);

    @Insert("<script> " +
            "insert into ops_event_pool <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "      <if test='event.busId != null'> " +
            "        event.bus_id, " +
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
    int insertIntoEventPool(@Param("event") OrderEvent eventBus);


    @Select("select top ${num} * from ops_event_pool where deal_flag=0 order by id")
    List<OrderEvent> scanEventsByCount(int num);

    @Update("update ops_event_pool set deal_flag = 1,version = version+1, modify_time=#{date} where id = #{id}  and version=#{version}")
    int updateEventToHandled(Long id, Long version, Date date);

    @Update("update ops_event_pool set deal_flag = 2,remark=#{remark}, modify_time=#{date} where id = #{id}")
    int updateEventToFailure(Long id,String remark, Date date);



}
