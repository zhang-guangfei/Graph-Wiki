package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.order.OrderLimitDto;
import com.smc.smccloud.model.order.OrderRemindDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/11 13:02
 */
@Mapper
@DS("opsdb")
public interface OrderLimitPromptMapper {



    @Select("<script>SELECT d.rorder_fno ,m.dept_No ,r.order_no ,r.order_item ,d.model_no ,d.quantity ,\n" +
            "m.customer_no ,m.end_user ,d.allot_time ,d.dlv_date ,d.order_type ,\n" +
            "d.exp_dlv_type ,r.remind_type ,r.remind_date ,r.remind_note \n" +
            "from ops_core.dbo.ops_order_remind r\n" +
            "inner join rcvdetail d on r.order_no = d.rorder_no and r.order_item = d.rorder_item \n" +
            "inner join ops_core.dbo.rcvmaster m on d.rorder_no = m.rorder_no"
            + " <where> "
            + " <if test='rorderFno != null and rorderFno != \"\"'> and d.rorder_fno=#{rorderFno} </if>"
            + " <if test='modelNo != null and modelNo != \"\"'> and d.model_no=#{modelNo} </if>"
            + " <if test='customerNo != null and customerNo != \"\"'> and m.customer_no =#{customerNo} </if>"
            + " <if test='endUser != null and endUser != \"\"'> and m.end_user=#{endUser} </if>"
            + " <if test='orderType != null and orderType != \"\"'> and d.order_type=#{orderType} </if>"
            + "<if test = 'deptNoArray != null and  deptNoArray.size() &gt; 0' >  and m.dept_No in "
            +"  <foreach collection='deptNoArray' item='item' index='index' open='(' separator=',' close=')'> "
            +" #{item} "
            +"  </foreach>"
            +"</if>"
            + " <if test='remindType != null and remindType != \"\"'> and r.remind_type=#{remindType} </if>"
            + " <if test='startRemindDate != null'> and r.remind_date <![CDATA[ >= ]]>#{startRemindDate} </if> "
            + " <if test='endRemindDate != null'> and r.remind_date <![CDATA[ <= ]]>#{endRemindDate} </if> "
            + "</where> </script>")
    List<OrderRemindDto> getOrderRemindView(OrderRemindDto obj);

    @Select("SELECT d.rorder_no ,d.rorder_item ,d.model_no ,m.end_user  " +
            "from ops_core.dbo.rcvdetail d left join ops_core.dbo.rcvmaster m " +
            "on d.rorder_no = m.rorder_no  where d.status not in (7,8,9,13) and d.delete_status = 0 ")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 5000)
    Cursor<OrderLimitDto> getOrder();

    @Select("SELECT d.rorder_no ,d.rorder_item ,d.model_no ,m.end_user  " +
            "from ops_core.dbo.rcvdetail d left join ops_core.dbo.rcvmaster m " +
            "on d.rorder_no = m.rorder_no  where d.order_type in (1,9) and d.status not in (7,8,9,13) and d.delete_status = 0 ")
    List<OrderLimitDto> getAllOrder();

    @Select("<script> select  modelNo from product_delivery where orgCountryId  in " +
            "<if test = 'orgCountryIds != null and  orgCountryIds.length &gt; 0' >" +
            "  <foreach collection='orgCountryIds' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            "and supplyId in " +
            "<if test = 'supplyIds != null and  supplyIds.length &gt; 0' >" +
            "  <foreach collection='supplyIds' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            " and is_deleted =0 </script>")
    List<String> getProductDelivery(@Param("orgCountryIds") String[] orgCountryIds, @Param("supplyIds") String[] supplyIds);
    @Select("SELECT modelNo  from product_limit where valid =1")
    List<String> getProductLimit();
    @Select("<script> SELECT b.CustomerNo  from ops_customer_limit l left join " +
            "ops_customer_base b on l.socialCreditCode = b.socialCreditCode " +
            "where b.CustomerNo is not NULL " +
            "and DATEDIFF(day, l.createTime,GETDATE()) >=#{bufferDays}" +
            " and l.limitType in " +
            "<if test = 'limitTypes != null and  limitTypes.length &gt; 0' >" +
            "  <foreach collection='limitTypes' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if></script>")
    List<String> getCustomerLimit(@Param("bufferDays") Integer bufferDays,@Param("limitTypes") String[] limitTypes );


    @Select("<script> " +
            "insert into  ops_order_remind(order_no,order_item,remind_type,remind_note,create_user) VALUES" +
            "<if test = 'data != null and  data.size() &gt; 0' >" +
            "  <foreach collection='data' item='item' index='index'  separator=',' > " +
            " (#{item.rorderNo}, #{item.rorderItem}, #{item.remindType},#{item.remindNode},#{item.createUser})" +
            "  </foreach>" +
            "</if>" +
            "</script>")
    void insertByBatch(@Param("data") List<OrderLimitDto> list);
}
