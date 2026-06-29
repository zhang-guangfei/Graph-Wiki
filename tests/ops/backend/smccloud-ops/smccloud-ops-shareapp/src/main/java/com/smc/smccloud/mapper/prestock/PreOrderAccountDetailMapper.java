package com.smc.smccloud.mapper.prestock;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreOrderAccountDO;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailDO;
import com.smc.smccloud.model.prestock.PreOrderAccountDetailDTO;
import com.smc.smccloud.model.prestock.PreOrderAccountRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/7 09:54
 * @description
 */
@DS("sharedb")
@Mapper
public interface PreOrderAccountDetailMapper extends BaseMapper<PreOrderAccountDetailDO> {
    /**
     * 18个参数，2100/18=116
     * @param list
     * @return
     */
    @Select("<script> " +
            " insert into  preorder_account_detail (inventory_id,order_no,ro_date,model_no,ro_qty,ava_qty,requirement_date,count_date,status,push_qty " +
            ",eprice,eamount,is_bin,charger,create_time,creator,modify_time,modifier) VALUES" +
            " <if test='list !=null and list.size() &gt; 0'>" +
            "  <foreach collection='list' item='item' index='index'  separator=',' > " +
            "   (#{item.inventoryId},#{item.orderNo}, #{item.roDate}, #{item.modelNo},#{item.roQty},#{item.avaQty},#{item.requirementDate}, #{item.countDate}," +
            "   #{item.status},#{item.pushQty}, #{item.ePrice}, #{item.eAmount},#{item.isBin},#{item.charger}, #{item.createTime}, #{item.creator}," +
            "   #{item.modifyTime},#{item.modifier}) " +
            "  </foreach>" +
            " </if>" +
            "</script>")
    Integer insertByBatch(@Param("list") List<PreOrderAccountDetailDO> list);


    @Select("<script>" +
            "select a.warehouse_code,a.inventory_type_code,a.customer_no,a.ppl,a.project_code,a.group_customer_no," +
            " b.* From preorder_account a join preorder_account_detail b on a.inventory_id=b.inventory_id " +
            " <trim prefix ='WHERE' suffixOverrides='AND|OR'> "+
            " b.delflag = '0' and  " +
            " <if test='request.modelNo!=null and request.modelNo!=\"\" '> b.model_no=#{request.modelNo} and </if>" +
            " <if test='request.applyNo!=null and request.applyNo!=\"\" '> b.apply_no like #{request.applyNo} and </if>" +
            " <if test='request.orderNo!=null and request.orderNo!=\"\" '> b.order_no=#{request.orderNo} and </if>" +
            " <if test='request.status!=null and request.status.size() &gt; 0'> "+
            " b.status in " +
            "  <foreach collection='request.status' item='item' index='index'  open='(' separator=',' close=')' > " +
            "   #{item} "+
            "  </foreach> and" +
            "  </if>" +
            " <if test='request.charger!=null and request.charger!=\"\" '> b.charger=#{request.charger} and </if>" +
            " <if test='request.beginDateStr!=null and request.beginDateStr!=\"\"  and request.endDateStr!=null and request.endDateStr!=\"\" '>" +
            "   b.count_date between #{request.beginDateStr} and #{request.endDateStr}   and </if>" +
            " <if test='request.deptNos!=null and request.deptNos.size() &gt; 0'> "+
            " b.dept_no in " +
            "  <foreach collection='request.deptNos' item='item' index='index'  open='(' separator=',' close=')' > " +
            "   #{item} "+
            "  </foreach> and" +
            "  </if>" +
            " <if test='request.warehouseCodes!=null and request.warehouseCodes.size() &gt; 0'>   " +
            " a.warehouse_code in " +
           "  <foreach collection='request.warehouseCodes' item='item' index='index' open='(' separator=',' close=')'>  " +
             "   #{item}  " +
             "  </foreach>  and" +
            "  </if>" +
            " <if test='request.inventoryTypeCodes!=null and request.inventoryTypeCodes.size() &gt; 0'>   " +
            " a.inventory_type_code in " +
            "  <foreach collection='request.inventoryTypeCodes' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            "  </foreach>  and" +
            "  </if>" +
            " <if test='request.transNo!=null and request.transNo!=\"\" '> b.trans_no=#{request.transNo} and </if>" +
            " <if test='request.customerNo!=null and request.customerNo!=\"\" '> a.customer_no=#{request.customerNo} and </if>" +
            " <if test='request.ppl!=null and request.ppl!=\"\" '> a.ppl=#{request.ppl} and </if>" +
            " <if test='request.projectCode!=null and request.projectCode!=\"\" '> a.project_code=#{request.projectCode} and </if>" +
            " <if test='request.groupCustomerNo!=null and request.groupCustomerNo!=\"\" '> a.group_customer_no=#{request.groupCustomerNo} and </if>" +
            " <if test='request.salesInfoNo!=null and request.salesInfoNo!=\"\" '> a.sales_info_no=#{request.salesInfoNo} and </if>" +
            " </trim>"+
            "</script>")
   List<PreOrderAccountDetailDTO> listPreOrderDetail(@Param("request") PreOrderAccountRequest request);

    @Select("select max(count_date) as lastCountDate From preorder_account_detail")
    Date getLastCountDate();


    @Select("<script>" +
            "select a.warehouse_code,a.inventory_type_code,a.customer_no,a.ppl,a.project_code,a.group_customer_no," +
            " b.* From preorder_account a join preorder_account_apply_detail b on a.inventory_id=b.inventory_id " +
            " <trim prefix ='WHERE' suffixOverrides='AND|OR'> "+
            " b.delflag = '0' and  " +
            " <if test='request.modelNo!=null and request.modelNo!=\"\" '> b.model_no=#{request.modelNo} and </if>" +
            " <if test='request.applyNo!=null and request.applyNo!=\"\" '> b.apply_no=#{request.applyNo} and </if>" +
            " <if test='request.accountApplyNo!=null and request.accountApplyNo!=\"\" '> b.account_apply_no=#{request.accountApplyNo} and </if>" +
            " <if test='request.temp == 1 '> b.trans_qty > 0 and </if>" +
            " <if test='request.transNo!=null and request.transNo!=\"\" '> b.trans_no=#{request.transNo} and </if>" +
            " <if test='request.orderNo!=null and request.orderNo!=\"\" '> b.order_no=#{request.orderNo} and </if>" +
            " <if test='request.status!=null and request.status.size() &gt; 0'> "+
            " b.status in " +
            "  <foreach collection='request.status' item='item' index='index'  open='(' separator=',' close=')' > " +
            "   #{item} "+
            "  </foreach> and" +
            "  </if>" +
            " <if test='request.charger!=null and request.charger!=\"\" '> b.charger=#{request.charger} and </if>" +
            " <if test='request.beginDateStr!=null and request.beginDateStr!=\"\"  and request.endDateStr!=null and request.endDateStr!=\"\" '>" +
            "   b.count_date between #{request.beginDateStr} and #{request.endDateStr}   and </if>" +
            " <if test='request.deptNos!=null and request.deptNos.size() &gt; 0'> "+
            " b.dept_no in " +
            "  <foreach collection='request.deptNos' item='item' index='index'  open='(' separator=',' close=')' > " +
            "   #{item} "+
            "  </foreach> and" +
            "  </if>" +
            " <if test='request.warehouseCodes!=null and request.warehouseCodes.size() &gt; 0'>   " +
            " a.warehouse_code in " +
            "  <foreach collection='request.warehouseCodes' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            "  </foreach>  and" +
            "  </if>" +
            " <if test='request.inventoryTypeCodes!=null and request.inventoryTypeCodes.size() &gt; 0'>   " +
            " a.inventory_type_code in " +
            "  <foreach collection='request.inventoryTypeCodes' item='item' index='index' open='(' separator=',' close=')'>  " +
            "   #{item}  " +
            "  </foreach>  and" +
            "  </if>" +
            " <if test='request.customerNo!=null and request.customerNo!=\"\" '> a.customer_no=#{request.customerNo} and </if>" +
            " <if test='request.ppl!=null and request.ppl!=\"\" '> a.ppl=#{request.ppl} and </if>" +
            " <if test='request.projectCode!=null and request.projectCode!=\"\" '> a.project_code=#{request.projectCode} and </if>" +
            " <if test='request.groupCustomerNo!=null and request.groupCustomerNo!=\"\" '> a.group_customer_no=#{request.groupCustomerNo} and </if>" +
            " <if test='request.salesInfoNo!=null and request.salesInfoNo!=\"\" '> a.sales_info_no=#{request.salesInfoNo} and </if>" +
            " </trim>"+
            "</script>")
    List<PreOrderAccountDetailDTO> listPreOrderApplyDetail(@Param("request") PreOrderAccountRequest request);


}
