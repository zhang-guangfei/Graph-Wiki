package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.EmailBarcode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * bugid:12391 c14717
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/10/31 11:18
 */
@DS("reportdb")
@Mapper
public interface EmailBarcodeDao {

    //批量插入barcode
    @Insert("<script> INSERT INTO ops_report.dbo.email_barcode (quantity,barcode_w,barcode_n,customer_no ,do_id," +
            "create_time,create_user) values  " +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.quantity},#{item.barcodeW},#{item.barcodeN},#{item.customerNo},#{item.doId}," +
            "#{item.createTime},#{item.createUser}) " +
            " </foreach> </script> ")
    public Integer batchInsertNoUpTime(@Param("list") List<EmailBarcode> list);


    //更新发送表
    @Update("UPDATE ops_report.dbo.email_barcode SET order_fno = #{orderFno},model_no=#{modelNo},user_no=#{userNo}" +
            ",ship_date=#{shipDate},express_no=#{expressNo} ,express_company=#{expressCompany},warehouse_code=#{warehouseCode}" +
            ",corder_no=#{corderNo},update_time=#{updateTime},update_user=#{updateUser},email = #{email} WHERE barcode_n = #{barcodeN}")
    Integer updateExpdetailBarcodeSend(EmailBarcode barcodeSend);

    //批量插入数据
    @Insert("<script> INSERT INTO ops_report.dbo.email_barcode (order_fno,model_no,quantity,barcode_w,barcode_n,customer_no ," +
            "user_no ,ship_date ,express_no , express_company,warehouse_code,corder_no,create_time,create_user,update_time,update_user,do_id," +
            "user_name,cproduct_no,dept_name,address,linkman,email) values  " +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.orderFno},#{item.modelNo},#{item.quantity},#{item.barcodeW},#{item.barcodeN},#{item.customerNo}," +
            "#{item.userNo},#{item.shipDate},#{item.expressNo},#{item.expressCompany},#{item.warehouseCode},#{item.corderNo}," +
            "#{item.createTime},#{item.createUser},#{item.updateTime},#{item.updateUser},#{item.doId}," +
            "#{item.userName},#{item.cproductNo},#{item.deptName},#{item.address},#{item.linkman},#{item.email}) " +
            " </foreach> </script> ")
    public Integer batchInsert(@Param("list") List<EmailBarcode> list);


    @Select("select count(1) from ops_report.dbo.email_barcode where barcode_w=#{barcodeW} and barcode_n=#{barcodeN}")
    public Integer checkBarcodeRep(@Param("barcodeW") String barcodeW,@Param("barcodeN") String barcodeN);


    //分组email
    @Select("<script> select email from ops_report.dbo.email_barcode where update_time &gt;= #{beginDate} and  update_time &lt; #{endDate} " +
            " group by email  " +
            "</script>")
    public List<String> findBarcodeListGroup( @Param("beginDate") String begingDate, @Param("endDate") String endDate);


    @Select("SELECT id,barcode_w,barcode_n,do_id  from ops_report.dbo.email_barcode where update_time is null")
    public List<EmailBarcode> findEmailBarcodeByUpdateTimeIsNull();


    /**
     * 通过email查询待发送时数据
     * @param begingDate
     * @param endDate
     * @param email
     * @return
     */
    @Select("<script> select * from ops_report.dbo.email_barcode where update_time &gt;= #{beginDate} and  update_time &lt; #{endDate} " +
            " and email = #{email}  " +
            "</script>")
    public List<EmailBarcode> findListBarcodeDateByEmail( @Param("beginDate") String begingDate, @Param("endDate") String endDate,@Param("email") String email);

}
