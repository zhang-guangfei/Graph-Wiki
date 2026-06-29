package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsBarcodeRuleConfig;
import com.sales.ops.db.entity.RuleCustomerBing;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：箱码
 * @date ：Created in 2023/6/7 9:35
 */

@DS("reportdb")
@Mapper
public interface ExpdetailBarcodeSendDao {

    //批量插入barcode
    @Insert("<script> INSERT INTO ops_report.dbo.expdetail_barcode_send (quantity,barcode_w,barcode_n,customer_no ,do_id," +
            "create_time,create_user) values  " +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.quantity},#{item.barcodeW},#{item.barcodeN},#{item.customerNo},#{item.doId}," +
            "#{item.createTime},#{item.createUser}) " +
            " </foreach> </script> ")
    public Integer batchInsertBarcode(@Param("list") List<ExpdetailBarcodeSend> list);


    //更新发送表
    @Update("UPDATE ops_report.dbo.expdetail_barcode_send SET order_fno = #{orderFno},model_no=#{modelNo},user_no=#{userNo}" +
            ",ship_date=#{shipDate},express_no=#{expressNo} ,express_company=#{expressCompany},warehouse_code=#{warehouseCode}" +
            ",corder_no=#{corderNo},update_time=#{updateTime},update_user=#{updateUser} WHERE barcode_n = #{barcodeN}")
    Integer updateExpdetailBarcodeSend(ExpdetailBarcodeSend barcodeSend);

    //批量插入数据
    @Insert("<script> INSERT INTO ops_report.dbo.expdetail_barcode_send (order_fno,model_no,quantity,barcode_w,barcode_n,customer_no ," +
            "user_no ,ship_date ,express_no , express_company,warehouse_code,corder_no,create_time,create_user,update_time,update_user,do_id," +
            "user_name,cproduct_no,dept_name,address,linkman,dlv_entire) values  " +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.orderFno},#{item.modelNo},#{item.quantity},#{item.barcodeW},#{item.barcodeN},#{item.customerNo}," +
            "#{item.userNo},#{item.shipDate},#{item.expressNo},#{item.expressCompany},#{item.warehouseCode},#{item.corderNo}," +
            "#{item.createTime},#{item.createUser},#{item.updateTime},#{item.updateUser},#{item.doId}," +
            "#{item.userName},#{item.cproductNo},#{item.deptName},#{item.address},#{item.linkman},#{item.dlvEntire}) " +
            " </foreach> </script> ")
    public Integer batchInsert(@Param("list") List<ExpdetailBarcodeSend> list);


    @Select("select count(1) from ops_report.dbo.expdetail_barcode_send where barcode_w=#{barcodeW} and barcode_n=#{barcodeN}")
    public Integer checkBarcodeRep(@Param("barcodeW") String barcodeW,@Param("barcodeN") String barcodeN);


    //取出待发送的数据
    @Select("<script> select order_fno,model_no,quantity,barcode_w,barcode_n,customer_no ,user_no ,user_name,ship_date ,express_no , " +
            "express_company,warehouse_code,corder_no,cproduct_no,dept_name,address,linkman from ops_report.dbo.expdetail_barcode_send where update_time &gt; #{beginDate} and  update_time &lt; #{endDate} " +
            " and customer_no in " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item.customerNo}" +
            "   </foreach>" +
            "</script>")
    public List<ExpdetailBarcodeSend> findListBarcodeDate(@Param("list") List<RuleCustomerBing> list,@Param("beginDate") String begingDate,@Param("endDate") String endDate);


    @Select("SELECT id,barcode_w,barcode_n,do_id  from ops_report.dbo.expdetail_barcode_send where update_time is null")
    public List<ExpdetailBarcodeSend> findListBarcodeByBar();

    @Select("SELECT rule_id ,rule_name ,table_name ,last_sync_time,sync_time ,status  from ops_report.dbo.ops_barcode_rule_config where status = 1")
    public List<OpsBarcodeRuleConfig> findBarRule();

    @Select("SELECT rule_id ,group_id  ,customer_no  ,customer_name  ,consignee ,mail ,status , send_time from ops_report.dbo.rule_customer_bing where status = 1")
    public List<RuleCustomerBing> findCusRule();

    @Select("SELECT customer_no  from ops_report.dbo.rule_customer_bing where status = 1 and rule_id = #{ruleID} ")
    public List<String> findCusRuleCusNos(@Param("ruleID") Integer ruleID);


    //更新规则发送时间
    @Update("UPDATE ops_report.dbo.ops_barcode_rule_config SET last_sync_time = #{lastSyncTime}  WHERE rule_id = #{ruleID}")
    Integer updateBarcodeRule(@Param("lastSyncTime") String lastSyncTime,@Param("ruleID") Integer ruleID);
}
