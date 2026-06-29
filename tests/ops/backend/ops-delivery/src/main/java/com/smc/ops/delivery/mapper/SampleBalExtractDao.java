package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.HrOrganization;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.SampleBal;
import com.sales.ops.dto.expdetail.ExpdetailDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ops 样品出库数据抽取到 sample_bal
 */
@DS("opsdb")
@Mapper
public interface SampleBalExtractDao {


    /**
     * 查询未拆分的出库数据
     *
     * @param ordtype
     * @param optcode
     * @return
     */
    @Select("<script> SELECT expdetail.*,rcvmaster.HLCode,rcvmaster.dept_No as HRUnitId FROM\n" +
            " expdetail inner join rcvmaster on expdetail.order_no = rcvmaster.rorder_no inner join rcvdetail on expdetail.order_fno = rcvdetail.rorder_fno \n" +
            " WHERE opt_code = #{optcode} and expdetail.order_type = #{ordtype} and expdetail.create_user = 'wms' \n" +
            "  and rcvdetail.status <![CDATA[ <> ]]> '9' and rcvdetail.prod_flag <![CDATA[ <> ]]>'2'  </script>")
    public List<ExpdetailDto> getUnProdFlagData(@Param("ordtype") String ordtype, @Param("optcode") Integer optcode);


    /**
     * 查询拆分的出库数据,只取得拆分型号，完全出库的
     *
     * @param ordtype
     * @param optcode
     * @return
     */
//    @Select("<script> select order_no,item_no,order_fno,expdetail.order_type,expdetail.customer_no,rcvdetail.quantity,rcvdetail.model_no,rcvdetail.price,MAX(ship_date) as ship_date,MAX(expdetail.stock_code) as stock_code\n" +
//            " FROM expdetail inner join rcvmaster on expdetail.order_no = rcvmaster.rorder_no inner join rcvdetail on expdetail.order_fno = rcvdetail.rorder_fno\n" +
//            " WHERE opt_code = #{optcode} and expdetail.order_type =#{ordtype} and expdetail.create_user = 'wms' and rcvdetail.prod_flag ='2' and rcvdetail.status = '7'  and rcvdetail.status <![CDATA[ <> ]]> '9' \n" +
//            " group by order_no,item_no,order_fno,expdetail.order_type,expdetail.customer_no,rcvdetail.quantity,rcvdetail.model_no,rcvdetail.price </script>")
    @Select("<script> SELECT \n" +
            "    order_no,\n" +
            "    item_no,\n" +
            "    order_fno,\n" +
            "    e1.order_type,\n" +
            "    e1.customer_no,\n" +
            "    e1.user_no,\n" +
            "    rc1.quantity,\n" +
            "    rc1.model_no,\n" +
            "    rc1.price,\n" +
            "    MAX(ship_date) AS ship_date,\n" +
            "    MAX(e1.stock_code) AS stock_code,\n" +
            "    STUFF((\n" +
            "        SELECT ',' + CAST(e2.id AS NVARCHAR(MAX))\n" +
            "        FROM expdetail e2\n" +
            "        INNER JOIN rcvmaster r2 ON e2.order_no = r2.rorder_no\n" +
            "        INNER JOIN rcvdetail rc2 ON e2.order_fno = rc2.rorder_fno\n" +
            "        WHERE \n" +
            "            e2.order_no = e1.order_no\n" +
            "            AND e2.item_no = e1.item_no\n" +
            "            AND e2.order_fno = e1.order_fno\n" +
            "            AND e2.order_type = e1.order_type\n" +
            "            AND e2.customer_no = e1.customer_no\n" +
            "            AND rc2.quantity = rc1.quantity\n" +
            "            AND rc2.model_no = rc1.model_no\n" +
            "            AND rc2.price = rc1.price\n" +
            "            AND e2.opt_code = #{optcode}\n" +
            "            AND e2.order_type = #{ordtype}\n" +
            "            AND e2.create_user = 'wms'\n" +
            "            AND rc2.prod_flag = '2'\n" +
            "            AND rc2.status = '7'\n" +
            "            AND rc2.status<![CDATA[ <> ]]> '9' \n" +
            "        FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '') AS id_string\n" +
            "FROM \n" +
            "    expdetail e1\n" +
            "INNER JOIN \n" +
            "    rcvmaster r1 ON e1.order_no = r1.rorder_no\n" +
            "INNER JOIN \n" +
            "    rcvdetail rc1 ON e1.order_fno = rc1.rorder_fno\n" +
            "WHERE \n" +
            "    e1.opt_code =#{optcode}\n" +
            "    AND e1.order_type = #{ordtype}\n" +
            "    AND e1.create_user = 'wms' \n" +
            "    AND rc1.prod_flag = '2' \n" +
            "    AND rc1.status = '7'  \n" +
            "    AND rc1.status <![CDATA[ <> ]]>  '9'\n" +
            "GROUP BY \n" +
            "    order_no,\n" +
            "    item_no,\n" +
            "    order_fno,\n" +
            "    e1.order_type,\n" +
            "    e1.customer_no,\n" +
            "    e1.user_no,\n" +
            "    rc1.quantity,\n" +
            "    rc1.model_no,\n" +
            "    rc1.price </script>")
    public List<ExpdetailDto> getProdFlagData(@Param("ordtype") String ordtype, @Param("optcode") Integer optcode);

    @Select(" select * from rcvdetail where rorder_fno =#{rorder_fno} ")
    public List<Rcvdetail> getRcvdetails(@Param("rorder_fno") String rorder_fno);

    @Select(" select * from sample_bal where optcode!='9' and ROrderNo =#{rorderno} ")
    public List<SampleBal> getSampleBalList(@Param("rorderno") String rorderno);

    @Update("<script> update expdetail set opt_code =#{opt_code} ,update_time = GETDATE() where  id in  " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            "</script>")
    public int updateExpdetailOptcode(@Param("list") List<Long> list, @Param("opt_code") Integer opt_code);

    @Update("<script> update expdetail set opt_code =#{opt_code} ,update_time = GETDATE() where order_fno = #{order_fno} and opt_code = '1'  " +
            "</script>")
    public int updateExpdetailOptcodeProdflag(@Param("order_fno") String order_fno, @Param("opt_code") Integer opt_code);

    @Select(" select * from hr_organization where  Id = #{id} and CompanyCode = '200000' and   Level in ('课内机构(HL)','驻在所HL') " )
    public List<HrOrganization> getHrOrg(@Param("list") List<String> list, @Param("id") String id);

    @Insert(" <script> insert into sample_bal (CustomerNo,ROrderNo,ModelNo,Quantity,DeptNo,DeptDesc,claim_no,claim_amount,express_company,ApplyCode,AppType,BalType,RcvDeptNo,Price \n" +
            "            ,Price_apply,ProdFlag,OptCode,OptDate,OptTime,ECode,Remark,Expdate,OrdType,Username,CreateTime ,StockCode,expdetail_id) \n" +
            "             values(#{customerno},#{rorderno},#{modelno},#{quantity},#{deptno},#{deptdesc},#{claimNo},#{claimAmount},#{expressCompany},#{applycode},#{apptype},#{baltype},#{rcvdeptno},#{price} \n" +
            "            ,#{priceApply},#{prodflag},#{optcode},#{optdate},#{opttime},#{ecode},#{remark},#{expdate},#{ordtype},#{username},#{createtime} ,#{stockcode},#{expdetailId}) </script>")
    public int insertSampleBal(SampleBal sampleBal);

}
