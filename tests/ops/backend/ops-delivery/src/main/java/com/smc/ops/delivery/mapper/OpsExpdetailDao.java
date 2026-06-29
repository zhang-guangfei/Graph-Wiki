package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsMail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：出库数据
 * @date ：Created in 2023/6/7 10:19
 */
@DS("opsdb")
@Mapper
public interface OpsExpdetailDao {


    //根据时间段取ops箱码数据
    @Select("<script> select quantity quantity ,expdetail_id barcode_w,barcode barcode_n," +
            " customer_no customer_no,do_id  from expdetail_barcode where customer_no in " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            " and create_time &gt; #{beginDate} and  create_time &lt; #{endDate} order by create_time </script>")
    public List<ExpdetailBarcodeSend> findListExpDetailBarcodeDate(@Param("list") List<String> list, @Param("beginDate") String begingDate, @Param("endDate") String endDate);


    //根据时间段取ops箱码数据
    @Select("<script> select e.order_fno order_fno,e.model_no model_no," +
            "e.user_no user_no,e.ship_date ship_date,e.express_no express_no,e.express_company express_company," +
            "e.warehouse_code warehouse_code,e.corder_no corder_no from expdetail e " +
            " where  e.delivery_no = #{doId} and e.barcode = #{barW}  </script>")
    public List<ExpdetailBarcodeSend> findListExpdetail(@Param("doId") String doId, @Param("barW") String barW);


    //根据时间段取ops箱码数据
    @Select("<script> select e.order_fno order_fno,e.model_no model_no,b.quantity quantity ,b.expdetail_id barcode_w,b.barcode barcode_n," +
            "e.customer_no customer_no ,e.user_no user_no,e.ship_date ship_date,e.express_no express_no,e.express_company express_company," +
            "e.warehouse_code warehouse_code,e.corder_no corder_no,b.do_id ,oc.name as user_name,e.cmodel_no as cproduct_no," +
            "d.name as dept_name,e.dlv_address  as address,e.ContactPsn as linkman,od.dlv_entire as dlv_entire"+
            " from expdetail_barcode b " +
            " left join expdetail e on b.expdetail_id = e.barcode and e.delivery_no = b.do_id  " +
            " left join ops_customer oc on e.user_no = oc.customer_no and oc.delflag =0"+
            " left join hr_organization d on d.id = e.dept_no and d.Status =0  "+
            " left join ops_do od on  od.do_id  = e.delivery_no and od.delflag =0 "+
            " where b.customer_no in " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            " and b.create_time &gt; #{beginDate} and  b.create_time &lt; #{endDate} order by b.create_time </script>")
    public List<ExpdetailBarcodeSend> findListBarcodeDate(@Param("list") List<String> list, @Param("beginDate") String begingDate, @Param("endDate") String endDate);

    @Insert("<script> insert into dbo.ops_mail  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            " <if test='mailId != null'> mail_id,</if>  <if test='mailFrom != null'>  mail_from,</if> " +
            " <if test='mailTo != null'> mail_to,</if>  <if test='subject != null'> subject, </if> " +
            " <if test='context != null'> context,</if> <if test='sendDate != null'>  send_date,  </if> " +
            " <if test='cc != null'>  cc, </if>         <if test='bcc != null'> bcc,  </if> " +
            " <if test='status != null'>  status, </if> <if test='errorMsg != null'>  error_msg,  </if> " +
            " <if test='fileUrls != null'>file_urls,</if><if test='nickName != null'>  nick_name,  </if> " +
            " </trim> " +
            " <trim prefix='values (' suffix=')' suffixOverrides=','> " +
            " <if test='mailId != null'> #{mailId,jdbcType=BIGINT},  </if> " +
            " <if test='mailFrom != null'>  #{mailFrom,jdbcType=VARCHAR},  </if> " +
            " <if test='mailTo != null'>  #{mailTo,jdbcType=VARCHAR},  </if> " +
            " <if test='subject != null'>  #{subject,jdbcType=VARCHAR},  </if> " +
            " <if test='context != null'>  #{context,jdbcType=VARCHAR}, </if> " +
            " <if test='sendDate != null'>  #{sendDate,jdbcType=TIMESTAMP},  </if> " +
            " <if test='cc != null'>  #{cc,jdbcType=VARCHAR},  </if> " +
            " <if test='bcc != null'>   #{bcc,jdbcType=VARCHAR},  </if> " +
            " <if test='status != null'> #{status,jdbcType=VARCHAR},  </if> " +
            " <if test='errorMsg != null'>  #{errorMsg,jdbcType=VARCHAR},  </if> " +
            " <if test='fileUrls != null'>  #{fileUrls,jdbcType=VARCHAR},  </if> " +
            " <if test='nickName != null'>  #{nickName,jdbcType=VARCHAR},  </if> " +
            " </trim>  </script>")
    public Integer insertMailData(OpsMail record);


    /**
     * 查询expBarcode表和 expdetail表 email有值的数据 同步数据
     * 根据时间段取ops箱码包含email的数据
     * bugid:12391 c14717 2023/10/31
     * @param begingDate
     * @param endDate
     * @return
     */
    @Select("<script> select e.order_fno order_fno,e.model_no model_no,b.quantity quantity ,b.expdetail_id barcode_w,b.barcode barcode_n," +
            " e.customer_no customer_no ,e.user_no user_no,e.ship_date ship_date,e.express_no express_no,e.express_company express_company," +
            " e.warehouse_code warehouse_code,e.corder_no corder_no,b.do_id ,oc.name as user_name,e.cmodel_no as cproduct_no," +
            " d.name as dept_name,e.dlv_address  as address,e.ContactPsn as linkman ,e.email " +
            " from expdetail_barcode b " +
            " left join expdetail e on b.expdetail_id = e.barcode and e.delivery_no = b.do_id  " +
            " left join ops_customer oc on e.user_no = oc.customer_no and oc.delflag =0 " +
            " left join hr_organization d on d.id = e.dept_no and d.Status =0 " +
            " where  b.create_time &gt;= #{beginDate} and  b.create_time &lt;= #{endDate} and e.email is not null order by b.create_time </script>")//
    public List<EmailBarcode> findExpBarAndExpdetailHaveEmail(@Param("beginDate") String begingDate, @Param("endDate") String endDate);


    @Select("<script> select count(b.barcode)" +
            " from expdetail_barcode b " +
            " left join expdetail e on b.expdetail_id = e.barcode and e.delivery_no = b.do_id  " +
            " where  b.create_time &gt;= #{beginDate} and  b.create_time &lt;= #{endDate} and e.email is not null  </script>")
    public Integer countExpBarAndExpdetailHaveEmail(@Param("beginDate") String begingDate, @Param("endDate") String endDate);


    /**
     * email 字段发送箱码信息 -- 解决主子表不同步
     * bugid:12391 c14717 2023/10/31
     * @param doId
     * @param barW
     * @return
     */
    @Select("<script> select e.order_fno order_fno,e.model_no model_no," +
            "e.user_no user_no,e.ship_date ship_date,e.express_no express_no,e.express_company express_company," +
            "e.warehouse_code warehouse_code,e.corder_no corder_no,e.email from expdetail e " +
            " where  e.delivery_no = #{doId} and e.barcode = #{barW}  </script>")
    public List<EmailBarcode> findListEmailExpdetail(@Param("doId") String doId, @Param("barW") String barW);
}
