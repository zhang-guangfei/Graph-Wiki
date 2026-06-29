package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.model.inventory.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface OpsInventoryLogMapper extends BaseMapper<OpsInventoryLogDO> {

    /**
     * 前端页面分页查询使用
     */
    @Select("<script>" +
            "select inventory_log_id,inventory_id,quantity,voucher_code,voucher_type,order_no,item_no,modelno,invoice_no,warehouse_code," +
            "p.version,p.delflag,l.cre_time,l.creator,p.modify_time,p.modifier,type,property_id,inventory_property_id,inventory_type_code,customer_no," +
            "ppl,project_code,group_customer_no,sales_info_no,exp_date " +
            "from ops_core.dbo.ops_inventory_log l " +
            " with(nolock)  \n" +
            "left join ops_core.dbo.ops_inventory_property p  with(nolock) on p.inventory_property_id=l.property_id" +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " <if test='dto.inventoryId!=null and dto.inventoryId!=\"\" '> inventory_id=#{dto.inventoryId} and </if>" +
            " <if test='dto.orderNo!=null and dto.orderNo!=\"\" '> order_no=#{dto.orderNo} and </if>" +
            " <if test='dto.itemNo!=null and dto.itemNo!=\"\" '> item_no=#{dto.itemNo} and </if>" +
            " <if test='dto.modelNo!=null and dto.modelNo!=\"\" '> modelno=#{dto.modelNo} and </if>" +
            " <if test='dto.invoiceNo!=null and dto.invoiceNo!=\"\" '> invoice_no=#{dto.invoiceNo} and </if>" +
            " <if test='dto.voucherType !=null and dto.voucherType.size() &gt; 0 '> " +
            "  voucher_type in " +
            " <foreach collection = 'dto.voucherType' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "  #{item}" +
            " </foreach>"+
            " and " +
            " </if>" +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '> warehouse_code=#{dto.warehouseCode} and </if>" +
            " <if test='dto.type!=null '> type=#{dto.type} and </if>" +
            " <if test='dto.crtTimeStartStr != null and dto.crtTimeStartStr!= \"\"  '>" +
            "  l.cre_time &gt; #{dto.crtTimeStartStr} and l.cre_time &lt; #{dto.crtTimeEndStr} " +
            " </if>" +
            "</trim>"+
            " order by l.${dto.property} ${dto.sortRule} " +
            "</script>" )
    List<OpsInventoryLogVO> selectInventoryLogList(@Param("dto") InventoryLogRequstDTO dto);

    @Select("<script>" +
            "select * from ops_core.dbo.ops_inventory_log  with(nolock)" +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " <if test='dto.inventoryId!=null and dto.inventoryId!=\"\" '> inventory_id=#{dto.inventoryId} and </if>" +
            " <if test='dto.orderNo!=null and dto.orderNo!=\"\" '> order_no=#{dto.orderNo} and </if>" +
            " <if test='dto.itemNo!=null and dto.itemNo!=\"\" '> item_no=#{dto.itemNo} and </if>" +
            " <if test='dto.modelNo!=null and dto.modelNo!=\"\" '> modelno=#{dto.modelNo} and </if>" +
            " <if test='dto.invoiceNo!=null and dto.invoiceNo!=\"\" '> invoice_no=#{dto.invoiceNo} and </if>" +
            " <if test='dto.voucherType !=null and dto.voucherType.size() &gt; 0 '> " +
            "  voucher_type in " +
            " <foreach collection = 'dto.voucherType' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "  #{item}" +
            " </foreach>"+
            " and " +
            " </if>" +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '> warehouse_code=#{dto.warehouseCode} and </if>" +
            " <if test='dto.type!=null '> type=#{dto.type} and </if>" +
            " <if test='dto.crtTimeStart!=null '>  cre_time &gt; #{dto.crtTimeStart} and  cre_time &lt; #{dto.crtTimeEnd} </if>" +
            "</trim>"+
            " order by  ${dto.property} ${dto.sortRule} " +
            "</script>" )
    List<OpsInventoryLogVO> listInventoryLog(@Param("dto") InventoryLogRequstDTO dto);



    @Select("<script>" +
            "  declare    @begindate datetime  ,  @endDate datetime   " +
            "  set @EndDate=convert(varchar(10),getdate(),121)+' 00:00:00' " +
            "  set @begindate=convert(varchar(10), DATEADD(Month,0-#{inventoryProperty.months}, @EndDate) ,121)+' 00:00:00' "+
            "  select  a.modelno,a.order_no,max(a.cre_time) as maxcre_time,sum(case when [type]=1 then 0-a.quantity else a.quantity end ) as quantity into #tmp  "+
            "  From ops_core.dbo.ops_inventory_log A with(nolock) join ops_inventory b with(nolock)  on a.inventory_id=b.inventory_id "+
            "  join ops_inventory_property c with(nolock)  on b.inventory_property_id=c.inventory_property_id"+
            "  where a.cre_time between @begindate and @endDate  and a.voucher_type in ('JYCK','THRK') and a.delflag=0    " +
            "  <if test = 'inventoryProperty.modelNos!= null and  inventoryProperty.modelNos.size() &gt; 0'>  " +
            "    and  a.modelno in " +
            "    <foreach collection='inventoryProperty.modelNos' item='item' index='index' open='(' separator=',' close=')'> "+
            "      #{item} " +
            "    </foreach> "+
            "  </if> "+
            "  <if test = 'inventoryProperty.isPres!= null and inventoryProperty.isPres'>  " +
            "    and  (c.inventory_type_code like'GK-%' or  c.inventory_type_code like'ZL-%') " +
            "  </if> "+
            "  <if test = 'inventoryProperty.inventoryTypeCode!= null and  inventoryProperty.inventoryTypeCode!=\"\" '>  " +
            "    and  c.inventory_type_code =#{inventoryProperty.inventoryTypeCode} " +
            "  </if> "+
            "  <if test = 'inventoryProperty.customerNo!= null and  inventoryProperty.customerNo!=\"\" '>  " +
            "    and  c.customer_no =#{inventoryProperty.customerNo} " +
            "  </if> "+
            "  <if test = 'inventoryProperty.ppl!= null and  inventoryProperty.ppl!=\"\" '>  " +
            "    and  c.ppl =#{inventoryProperty.ppl} " +
            "  </if> "+
            "  <if test = 'inventoryProperty.projectCode!= null and  inventoryProperty.projectCode!=\"\" '>  " +
            "    and  c.project_code =#{inventoryProperty.projectCode} " +
            "  </if> "+
            "  <if test = 'inventoryProperty.groupCustomerNo!= null and  inventoryProperty.groupCustomerNo!=\"\" '>  " +
            "    and  c.group_customer_no =#{inventoryProperty.groupCustomerNo} " +
            "  </if> "+
            "  <if test = 'inventoryProperty.salesInfoNo!= null and  inventoryProperty.salesInfoNo!=\"\" '>  " +
            "    and  c.sales_info_no =#{inventoryProperty.salesInfoNo} " +
            "  </if> "+
            "  group by a.modelno,a.order_no "+
            "  select modelno,sum(quantity ) as quantity, count(distinct order_no) as ordercount, count(distinct convert(varchar(7),maxcre_time,121)) as freq From #tmp"+
            "  where quantity>0 "+
            "  group by modelno "+
            "</script>" )
    List<ModelOrderExpFreqVO> getSalesQuantityAndFreq(@Param("inventoryProperty") OpsInventoryPropertyRequestDTO inventoryProperty);





}
