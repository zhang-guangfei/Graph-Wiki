package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.pd.OpsAsPdCompensateDataDO;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdCompensateDataMapper extends BaseMapper<OpsAsPdCompensateDataDO> {

    @Select("select * from ops_sharedb.dbo.ops_as_pd_compensate_data where pd_data_type = '4' and pd_batch_no = #{batchNo} order by id asc")
    List<OpsAsPdCompensateDataDO> selWmsBcData(@Param("batchNo") String batchNo);

    @Select("select rorder_no=orderNo,item_no=itemNo,order_no='',model_no=modelno,\n" +
            "warehouse_code=sign_warehouse_code,qty=quantity,pd_data_type='6',pd_data_source='2' from ${dataSource}.ops_inventory_move \n" +
            "where delflag=0 and source_type=2 and quantity<>0 and inventory_status='T4'")
    List<OpsAsPdCompensateDataDO> selOpsReturnData(@Param("dataSource") String dataSource);


    @Select("SELECT \n" +
            "       [pd_batch_no]\n" +
            "      ,[warehouse_code]\n" +
            "      ,[pd_bill_no]\n" +
            "      ,[pd_data_type]\n" +
            "      ,[pd_data_source]\n" +
            "      ,[order_no]\n" +
            "      ,[rorder_no]\n" +
            "      ,[item_no]\n" +
            "      ,[split_item_no]\n" +
            "      ,[do_id]\n" +
            "      ,[model_no]\n" +
            "      ,[qty]\n" +
            "      ,[create_user]\n" +
            "      ,[create_time]\n" +
            "      ,[update_user]\n" +
            "      ,[update_time]\n" +
            "  FROM [ops_sharedb].[dbo].[ops_as_pd_compensate_data] " +
            " where pd_data_type = '6' and pd_data_source = '1' and pd_batch_no = #{batchNo} ")
    List<OpsAsPdCompensateDataDO> selWmsReturnData(@Param("batchNo")String batchNo);

    @Delete("delete from ops_report.dbo.ops_as_pd_compensate_data where pd_batch_no = #{pdBatchNo} and pd_data_type = #{dataType} and pd_data_source = #{dataSource} ")
    void clearOpsCompensateData(@Param("pdBatchNo")String pdBatchNo,@Param("dataType") String dataType,@Param("dataSource") String dataSource);

    // 样品数据写入到补偿表
//    @Select("SELECT  'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
//            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
//            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
//            " model_no=r.modelno,qty=(r.quantity * e.expdetailQty)/e.rcvQty -- 拆分型号及数量(根据拆分比例进行分配)\n" +
//            " ,pd_data_type='1',pd_data_source='2'\n" +
//            " from (\n" +
//            " select a.order_fno,a.order_no,a.item_no,a.warehouse_code,SUM(a.quantity) expdetailQty,b.quantity rcvQty\n" +
//            "from ops_core.dbo.expdetail a inner join rcvdetail b on a.order_fno  = b.rorder_fno and a.model_no = b.model_no\n" +
//            "where a.opt_code ='1' and  b.order_type = '9' and b.prod_flag = '2' \n" +
//            "group by a.order_fno,a.order_no,a.item_no,a.warehouse_code,b.quantity  ) e\n" +
//            "inner join ops_order_assign_result r on e.order_no = r.order_no and e.item_no = r.order_item\n" +
//            "where r.delflag = '0'\n" +
//            "-- 1.2 非子项特发：expdetail中记录的就是拆分型号，直接取值即可\n" +
//            "union all\n" +
//            " select \n" +
//            " 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
//            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
//            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
//            " model_no=e.model_no,qty= SUM(e.quantity) -- 拆分型号及数量\n" +
//            " ,pd_data_type='1',pd_data_source='2'\n" +
//            "from ops_core.dbo.expdetail e inner join rcvdetail b on e.order_fno  = b.rorder_fno and e.model_no <> b.model_no\n" +
//            "where e.opt_code ='1' and  b.order_type = '9' and b.prod_flag = '2' \n" +
//            "group by e.order_fno,e.order_no,e.item_no,e.warehouse_code,e.model_no\n" +
//            "-- 2. 非型号拆分,直接取expdetail中相关信息即可\n" +
//            "union all\n" +
//            " select \n" +
//            " 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
//            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
//            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
//            " model_no=e.model_no,qty= e.quantity -- 拆分型号及数量\n" +
//            " ,pd_data_type='1',pd_data_source='2'\n" +
//            "from ops_core.dbo.expdetail e inner join rcvdetail b on e.order_fno  = b.rorder_fno\n" +
//            "where e.opt_code ='1' and  b.order_type = '9' and b.prod_flag <> '2' \n" +
//            "union all \n" +
//            "select 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号固定值\n" +
//            "warehouse_code=o.warehouse_code, -- 仓库代码，取order_status中字段即可\n" +
//            "rorder_no=rorder_no,item_no=rorder_item,order_no=a.ROrderNo,--  订单号、项号、完整订单号\n" +
//            "model_no=c.modelno,qty=(c.quantity * a.quantity)/b.quantity,  -- 拆分型号及数量\n" +
//            "pd_data_type='1',pd_data_source='2'\n" +
//            " FROM (\n" +
//            " SELECT ROrderNo,ModelNo,OptCode,sum(Quantity) quantity\n" +
//            " FROM ops_core.dbo.sample_Bal group by ROrderNo,ModelNo,OptCode) a\n" +
//            " INNER JOIN ops_core.dbo.rcvdetail b ON a.ROrderNo=b.rorder_fno\n" +
//            " LEFT JOIN ops_core.dbo.ops_order_assign_result c ON b.rorder_no=c.order_no and b.rorder_item=c.order_item and c.delflag=0\n" +
//            " left join ( select order_id,order_item,MAX(warehouse_code) warehouse_code from ops_core.dbo.order_status group by order_id,order_item) o on b.rorder_no = o.order_id and b.rorder_item = o.order_item\n" +
//            " WHERE OptCode in (1,3) and b.Prod_Flag='2' and b.ship_time>='2022-8-15' and b.ship_time< #{execDate} \n" +
//            " UNION all\n" +
//            "  SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号固定值\n" +
//            "  warehouse_code=o.warehouse_code, -- 仓库代码，取order_status中字段即可\n" +
//            "  rorder_no=rorder_no,item_no=rorder_item,order_no=a.ROrderNo,  --  订单号、项号、完整订单号\n" +
//            "  model_no=a.ModelNo,qty=a.quantity, -- 型号及数量\n" +
//            "  pd_data_type='1',pd_data_source='2'\n" +
//            " FROM (\n" +
//            " SELECT ROrderNo,ModelNo,OptCode,sum(Quantity) quantity\n" +
//            " FROM ops_core.dbo.sample_Bal group by ROrderNo,ModelNo,OptCode) a\n" +
//            " LEFT JOIN ops_core.dbo.rcvdetail b\n" +
//            " ON a.ROrderNo=b.rorder_fno\n" +
//            " LEFT JOIN ( select order_id,order_item,MAX(warehouse_code) warehouse_code from ops_core.dbo.order_status group by order_id,order_item) o on b.rorder_no = o.order_id and b.rorder_item = o.order_item\n" +
//            " WHERE OptCode in (1,3) and b.Prod_Flag<>'2' and b.ship_time>='2022-8-15' and b.ship_time< #{execDate} \n" +
//            " UNION all\n" +
//            " SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, warehouse_code='K'+isnull(d.Region,'AU'), rorder_no=b.rorder_no,item_no=rorder_item, order_no=a.ROrderNo ,model_no=P.modelno,qty=P.quantity,pd_data_type='1',pd_data_source='2'\n" +
//            " FROM ops_core.dbo.sample_Bal a\n" +
//            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
//            " ON a.ROrderNo=b.rorder_fno\n" +
//            " LEFT JOIN (select rorder_no,delivery_dept_no=isnull(delivery_dept_no,dept_no)\n" +
//            " FROM ops_core.dbo.rcvmaster) c\n" +
//            " ON c.rorder_no=b.rorder_no\n" +
//            " LEFT JOIN ops_core.dbo.ops_order_assign_result P\n" +
//            " ON b.rorder_no=p.order_no and b.rorder_item=P.order_item\n" +
//            " LEFT JOIN (SELECT Id=Code,Name=DeptName,Region=case when FullName like '%北京分公司%' then 'BJ' when FullName like '%上海分公司%' then 'SH' when FullName like '%广州分公司%' then 'GZ' when code like '23%' then 'AU'else 'CN'end ,Status=case when statusID=0 then 0 else 1 end\n" +
//            " FROM ops_core.dbo.KD_Org_Unit\n" +
//            " WHERE (FullName like '%北京分公司%' or FullName like '%上海分公司%'or FullName like '%广州分公司%' or FullName like '%自动化有限公司%' or FullName like '%制造有限公司%' or FullName like '%SMC（中国）有限公司%' or FullName like '%监察室%' or FullName like '%公共关系部%' or FullName like '%企画室%' or FullName like '%财务部%' or FullName='SMC投资管理有限公司') )d\n" +
//            " ON c.delivery_dept_no=d.id\n" +
//            " WHERE OptCode in (1,3) and b.Prod_Flag='2' and a.Expdate>='2021-4-1' and a.Expdate<'2022-8-15'  \n" +
//            " UNION all\n" +
//            " SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, warehouse_code='K'+isnull(d.Region,'AU'), rorder_no=b.rorder_no,item_no=rorder_item, order_no=a.ROrderNo ,model_no=a.ModelNo,qty=a.quantity,pd_data_type='1',pd_data_source='2'\n" +
//            " FROM ops_core.dbo.sample_Bal a\n" +
//            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
//            " ON a.ROrderNo=b.rorder_fno\n" +
//            " LEFT JOIN (select rorder_no,delivery_dept_no=isnull(delivery_dept_no,dept_no)\n" +
//            " FROM ops_core.dbo.rcvmaster) c\n" +
//            " ON c.rorder_no=b.rorder_no\n" +
//            " LEFT JOIN (SELECT Id=Code,Name=DeptName,Region=case when FullName like '%北京分公司%' then 'BJ' when FullName like '%上海分公司%' then 'SH' when FullName like '%广州分公司%' then 'GZ' when code like '23%' then 'AU'else 'CN'end ,Status=case when statusID=0 then 0 else 1 end\n" +
//            " FROM ops_core.dbo.KD_Org_Unit\n" +
//            " WHERE (FullName like '%北京分公司%' or FullName like '%上海分公司%'or FullName like '%广州分公司%' or FullName like '%自动化有限公司%' or FullName like '%制造有限公司%' or FullName like '%SMC（中国）有限公司%' or FullName like '%监察室%' or FullName like '%公共关系部%' or FullName like '%企画室%' or FullName like '%财务部%' or FullName='SMC投资管理有限公司') )d\n" +
//            " ON c.delivery_dept_no=d.id\n" +
//            " WHERE OptCode in (1,3) and b.Prod_Flag<>'2' and a.Expdate>='2021-4-1' and a.Expdate<'2022-8-15' ")
    @Select("SELECT  'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
            " model_no=r.modelno,qty=(r.quantity * e.expdetailQty)/e.rcvQty -- 拆分型号及数量(根据拆分比例进行分配)\n" +
            " ,pd_data_type='1',pd_data_source='2'\n" +
            " from (\n" +
            " select a.order_fno,a.order_no,a.item_no,a.warehouse_code,SUM(a.quantity) expdetailQty,b.quantity rcvQty\n" +
            "from ops_core.dbo.expdetail a inner join ops_core.dbo.rcvdetail b on a.order_fno  = b.rorder_fno and a.model_no = b.model_no\n" +
            "where a.opt_code ='1' and  b.order_type = '9' and b.prod_flag = '2' \n" +
            "group by a.order_fno,a.order_no,a.item_no,a.warehouse_code,b.quantity  ) e\n" +
            "inner join ops_core.dbo.ops_order_assign_result r on e.order_no = r.order_no and e.item_no = r.order_item\n" +
            "where r.delflag = '0'\n" +
            "-- 1.2 非子项特发：expdetail中记录的就是拆分型号，直接取值即可\n" +
            "union all\n" +
            " select \n" +
            " 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
            " model_no=e.model_no,qty= SUM(e.quantity) -- 拆分型号及数量\n" +
            " ,pd_data_type='1',pd_data_source='2'\n" +
            "from ops_core.dbo.expdetail e inner join ops_core.dbo.rcvdetail b on e.order_fno  = b.rorder_fno and e.model_no <> b.model_no\n" +
            "where e.opt_code ='1' and  b.order_type = '9' and b.prod_flag = '2' \n" +
            "group by e.order_fno,e.order_no,e.item_no,e.warehouse_code,e.model_no\n" +
            "-- 2. 非型号拆分,直接取expdetail中相关信息即可\n" +
            "union all\n" +
            " select \n" +
            " 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号\n" +
            "warehouse_code=e.warehouse_code, -- 仓库代码，取自expdetail表\n" +
            "rorder_no=e.order_no,item_no=e.item_no,order_no=e.order_fno, -- 订单号、项号、完整订单号\n" +
            " model_no=e.model_no,qty= e.quantity -- 拆分型号及数量\n" +
            " ,pd_data_type='1',pd_data_source='2'\n" +
            "from ops_core.dbo.expdetail e inner join ops_core.dbo.rcvdetail b on e.order_fno  = b.rorder_fno\n" +
            "where e.opt_code ='1' and  b.order_type = '9' and b.prod_flag <> '2' \n" +
            "union all \n" +
            "select 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号固定值\n" +
            "warehouse_code=o.warehouse_code, -- 仓库代码，取order_status中字段即可\n" +
            "rorder_no=rorder_no,item_no=rorder_item,order_no=a.ROrderNo,--  订单号、项号、完整订单号\n" +
            "model_no=c.modelno,qty=(c.quantity * a.quantity)/b.quantity,  -- 拆分型号及数量\n" +
            "pd_data_type='1',pd_data_source='2'\n" +
            " FROM (\n" +
            " SELECT ROrderNo,ModelNo,OptCode,sum(Quantity) quantity\n" +
            " FROM ops_core.dbo.sample_Bal group by ROrderNo,ModelNo,OptCode) a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b ON a.ROrderNo=b.rorder_fno\n" +
            " LEFT JOIN ops_core.dbo.ops_order_assign_result c ON b.rorder_no=c.order_no and b.rorder_item=c.order_item and c.delflag=0\n" +
            " left join ( select order_id,order_item,MAX(warehouse_code) warehouse_code from ops_core.dbo.order_status group by order_id,order_item) o on b.rorder_no = o.order_id and b.rorder_item = o.order_item\n" +
            " WHERE OptCode in (1,3) and b.Prod_Flag='2' and b.ship_time>='2022-8-15' and b.ship_time< #{execDate} \n" +
            " UNION all\n" +
            "  SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, -- 盘点批次号固定值\n" +
            "  warehouse_code=o.warehouse_code, -- 仓库代码，取order_status中字段即可\n" +
            "  rorder_no=rorder_no,item_no=rorder_item,order_no=a.ROrderNo,  --  订单号、项号、完整订单号\n" +
            "  model_no=a.ModelNo,qty=a.quantity, -- 型号及数量\n" +
            "  pd_data_type='1',pd_data_source='2'\n" +
            " FROM (\n" +
            " SELECT ROrderNo,ModelNo,OptCode,sum(Quantity) quantity\n" +
            " FROM ops_core.dbo.sample_Bal group by ROrderNo,ModelNo,OptCode) a\n" +
            " LEFT JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.ROrderNo=b.rorder_fno\n" +
            " LEFT JOIN ( select order_id,order_item,MAX(warehouse_code) warehouse_code from ops_core.dbo.order_status group by order_id,order_item) o on b.rorder_no = o.order_id and b.rorder_item = o.order_item\n" +
            " WHERE OptCode in (1,3) and b.Prod_Flag<>'2' and b.ship_time>='2022-8-15' and b.ship_time< #{execDate} \n" +
            " UNION all\n" +
            " SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, warehouse_code='K'+isnull(d.Region,'AU'), rorder_no=b.rorder_no,item_no=rorder_item, order_no=a.ROrderNo ,model_no=P.modelno,qty=P.quantity,pd_data_type='1',pd_data_source='2'\n" +
            " FROM ops_core.dbo.sample_Bal a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.ROrderNo=b.rorder_fno\n" +
            " LEFT JOIN (select rorder_no,delivery_dept_no=isnull(delivery_dept_no,dept_no)\n" +
            " FROM ops_core.dbo.rcvmaster) c\n" +
            " ON c.rorder_no=b.rorder_no\n" +
            " LEFT JOIN ops_core.dbo.ops_order_assign_result P\n" +
            " ON b.rorder_no=p.order_no and b.rorder_item=P.order_item\n" +
            " LEFT JOIN (SELECT Id=Code,Name=DeptName,Region=case when FullName like '%北京分公司%' then 'BJ' when FullName like '%上海分公司%' then 'SH' when FullName like '%广州分公司%' then 'GZ' when code like '23%' then 'AU'else 'CN'end ,Status=case when statusID=0 then 0 else 1 end\n" +
            " FROM ops_core.dbo.KD_Org_Unit\n" +
            " WHERE (FullName like '%北京分公司%' or FullName like '%上海分公司%'or FullName like '%广州分公司%' or FullName like '%自动化有限公司%' or FullName like '%制造有限公司%' or FullName like '%SMC（中国）有限公司%' or FullName like '%监察室%' or FullName like '%公共关系部%' or FullName like '%企画室%' or FullName like '%财务部%' or FullName='SMC投资管理有限公司') )d\n" +
            " ON c.delivery_dept_no=d.id\n" +
            " WHERE OptCode in (1,3) and b.Prod_Flag='2' and a.Expdate>='2021-4-1' and a.Expdate<'2022-8-15'  \n" +
            " UNION all\n" +
            " SELECT 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no, warehouse_code='K'+isnull(d.Region,'AU'), rorder_no=b.rorder_no,item_no=rorder_item, order_no=a.ROrderNo ,model_no=a.ModelNo,qty=a.quantity,pd_data_type='1',pd_data_source='2'\n" +
            " FROM ops_core.dbo.sample_Bal a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.ROrderNo=b.rorder_fno\n" +
            " LEFT JOIN (select rorder_no,delivery_dept_no=isnull(delivery_dept_no,dept_no)\n" +
            " FROM ops_core.dbo.rcvmaster) c\n" +
            " ON c.rorder_no=b.rorder_no\n" +
            " LEFT JOIN (SELECT Id=Code,Name=DeptName,Region=case when FullName like '%北京分公司%' then 'BJ' when FullName like '%上海分公司%' then 'SH' when FullName like '%广州分公司%' then 'GZ' when code like '23%' then 'AU'else 'CN'end ,Status=case when statusID=0 then 0 else 1 end\n" +
            " FROM ops_core.dbo.KD_Org_Unit\n" +
            " WHERE (FullName like '%北京分公司%' or FullName like '%上海分公司%'or FullName like '%广州分公司%' or FullName like '%自动化有限公司%' or FullName like '%制造有限公司%' or FullName like '%SMC（中国）有限公司%' or FullName like '%监察室%' or FullName like '%公共关系部%' or FullName like '%企画室%' or FullName like '%财务部%' or FullName='SMC投资管理有限公司') )d\n" +
            " ON c.delivery_dept_no=d.id\n" +
            " WHERE OptCode in (1,3) and b.Prod_Flag<>'2' and a.Expdate>='2021-4-1' and a.Expdate<'2022-8-15' ")
    List<OpsAsPdCompensateDataDO> sampleOrderImpCompensateData(@Param("execDate") String execDate, @Param("dataSource") String dataSource);


//    @Select("select 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no,rorder_no=order_no,item_no,order_no=order_fno,model_no=P.modelNo,warehouse_code,qty=quantity,pd_data_type='2',pd_data_source='2' \n" +
//            "from (\n" +
//            "select order_no,item_no,order_fno,modelNo=c.sub_modelNo,a.warehouse_code,quantity=c.sub_qty from \n" +
//            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
//            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
//            "inner join ${dataSource}.ops_pco b on a.order_no=b.order_id and a.item_no=b.order_item and b.delflag=0\n" +
//            "inner join ${dataSource}.ops_pco_item c on b.pco_id=c.pco_id and c.delflag=0\n" +
//            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
//            "where invoice_flag=0 and a.create_user='wms' and a.order_type<>9 and d.prod_flag=2\n" +
//            "union all\n" +
//            "select order_no,item_no,order_fno,modelNo=a.model_no,a.warehouse_code,quantity=a.quantity from \n" +
//            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
//            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
//            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
//            "where invoice_flag=0 and a.create_user='wms' and a.order_type<>9 and d.prod_flag=2 \n" +
//            "and d.exp_dlv_type in ('8192','4096','5120','8256','9216','9280','10240','12288','13312')\n" +
//            "union all\n" +
//            "select order_no,item_no,order_fno,modelno=a.model_No,a.warehouse_code,quantity=a.quantity from \n" +
//            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
//            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
//            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
//            "where invoice_flag=0 and a.create_user='wms'  and a.order_type<>9 and d.prod_flag<>2) P\n")
    @Select("select 'PD' + convert(varchar(6), getdate(), 112) as pd_batch_no,rorder_no=order_no,item_no,order_no=order_fno,model_no=P.modelNo,warehouse_code,qty=quantity,pd_data_type='2',pd_data_source='2' \n" +
            "from (\n" +
            "select order_no,item_no,order_fno,modelNo=c.sub_modelNo,a.warehouse_code,quantity=c.sub_qty from \n" +
            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
            "inner join ${dataSource}.ops_pco b on a.order_no=b.order_id and a.item_no=b.order_item and b.delflag=0\n" +
            "inner join ${dataSource}.ops_pco_item c on b.pco_id=c.pco_id and c.delflag=0\n" +
            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
            "where invoice_flag=0 and a.create_user='wms' and a.order_type<>9 and d.prod_flag=2\n" +
            "union all\n" +
            "select order_no,item_no,order_fno,modelNo=a.model_no,a.warehouse_code,quantity=a.quantity from \n" +
            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
            "where invoice_flag=0 and a.create_user='wms' and a.order_type<>9 and d.prod_flag=2 \n" +
            "and d.model_no<>a.model_no\n" +
            "union all\n" +
            "select order_no,item_no,order_fno,modelno=a.model_No,a.warehouse_code,quantity=a.quantity from \n" +
            "(select warehouse_code, order_no,item_no, order_fno,model_no,sum(quantity) quantity,invoice_flag,create_user,order_type from  ${dataSource}.expdetail \n" +
            "group by warehouse_code, order_no,item_no, order_fno,model_no,invoice_flag,create_user,order_type ) a\n" +
            "left join ${dataSource}.rcvdetail d on d.rorder_no=a.order_no and d.rorder_item=a.item_no\n" +
            "where invoice_flag=0 and a.create_user='wms'  and a.order_type<>9 and d.prod_flag<>2) P")
      List<OpsAsPdCompensateDataDO> yckNotPushCw(@Param("dataSource") String dataSource);

    // 财务补偿数据 -- 推给财务未确认销售
    @Select("select P.modelNo,warehouse_code,qty=sum(quantity) from (\n" +
            "SELECT ModelNo=d.sub_ModelNo,a.warehouse_code,quantity=d.sub_qty FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
            "inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
            "inner join ${dataSource}.ops_pco c on b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
            "inner join ${dataSource}.ops_pco_item d on c.pco_id=d.pco_id and d.delflag=0\n" +
            "where year(InsertTime)=#{year} and month(InsertTime)='1' and ackdate>=#{execDate} and Invflag!='B'\n" +
            "union all\n" +
            "SELECT a.ModelNo,a.warehouse_code,a.quantity FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
            "inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
            "left join ${dataSource}.ops_pco c on b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
            "left join ${dataSource}.ops_pco_item d on c.pco_id=d.pco_id and d.delflag=0\n" +
            "where year(InsertTime)=#{year} and month(InsertTime)='1' and ackdate>=#{execDate} and Invflag!='B' and c.order_id is null) P\n" +
            "group by P.modelNo,warehouse_code")
    List<OpsAsPdCompensateDataDO> cwDataNotSureSales(@Param("execDate") String execDate, @Param("dataSource") String dataSource);


    // 财务补偿数据
//    @Select("SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=c.modelno,a.warehouse_code,qty=c.quantity,pd_data_type='3',pd_data_source='3' \n" +
//            "                         FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
//            "                        inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
//            "                        inner join ${dataSource}.ops_order_assign_result c on b.rorder_no=c.order_no and b.rorder_item=c.order_item and c.delflag=0\n" +
//            "                        where b.prod_flag=2 \n" +
//            "            and Invflag<>'B'and Invflag<>'D' and Invflag<>'3'and a.modelno<>'手续费'and Invflag1='0'and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate})\n" +
//            "                        \n" +
//            "                        union all\n" +
//            "                        \n" +
//            "                        SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=a.ModelNo,a.warehouse_code,qty=a.quantity ,pd_data_type='3',pd_data_source='3' \n" +
//            "                        FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
//            "                        inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
//            "                        where b.prod_flag<>2 and Invflag<>'B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0'and a.modelno<>'手续费'\n" +
//            "            and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate}) \n" +
//            "                        \n" +
//            "                        union all\n" +
//            "                        \n" +
//            "                        SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=d.sub_ModelNo,c.warehouse_code,qty=d.sub_qty ,pd_data_type='3',pd_data_source='3' \n" +
//            "                        FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
//            "                        inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
//            "                        inner join ${dataSource}.ops_pco c on b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
//            "                        inner join ${dataSource}.ops_pco_item d on c.pco_id=d.pco_id and d.delflag=0\n" +
//            "                        where InvFlag='B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0' and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate})\n" +
//            "                        \n" +
//            "                        union all\n" +
//            "                        \n" +
//            "                                SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=a.ModelNo,warehouse_code=case when e.warehouse_code is null then b.stock_code else e.warehouse_code end,qty=a.quantity ,pd_data_type='3',pd_data_source='3' \n" +
//            "                        FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
//            "                        inner join ${dataSource}.rcvdetail b on a.RorderNo=b.rorder_fno\n" +
//            "                        left join ${dataSource}.ops_do e on b.rorder_no=e.order_id and b.rorder_item=e.order_item and e.do_type='JYCK' and e.delflag=0\n" +
//            "                        left join ${dataSource}.ops_pco c on b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
//            "                        left join ${dataSource}.ops_pco_item d on c.pco_id=d.pco_id and d.delflag=0\n" +
//            "                        where InvFlag='B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0' and c.order_id is null  and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate})")
    @Select("WITH a1 AS (\n" +
            "    SELECT *\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "            a.*,\n" +
            "            ROW_NUMBER() OVER (\n" +
            "                PARTITION BY a.RorderNo\n" +
            "                ORDER BY a.InsertTime DESC\n" +
            "            ) AS rn\n" +
            "        FROM  [10.116.32.17].[smcdb30].[dbo].SalesInvoice a\n" +
            "        WHERE\n" +
            "            a.Invflag NOT IN ('B','D','3')\n" +
            "            AND a.modelno <> '手续费'\n" +
            "            AND a.Invflag1 = '0'\n" +
            "            AND a.InvoiceNo IS NULL\n" +
            "            AND (a.accountingDate IS NULL OR a.accountingDate >= #{execDate})\n" +
            "    ) t\n" +
            "    WHERE rn = 1\n" +
            ")\n" +
            "SELECT\n" +
            "    a1.InsertTime,\n" +
            "    b.rorder_fno   AS order_no,\n" +
            "    b.rorder_no,\n" +
            "    b.rorder_item  AS item_no,\n" +
            "    c.modelno      AS model_no,\n" +
            "    a1.warehouse_code,\n" +
            "    c.quantity     AS qty,\n" +
            "    '3' AS pd_data_type,\n" +
            "    '3' AS pd_data_source\n" +
            "FROM a1\n" +
            "JOIN ops_core.dbo.rcvdetail b\n" +
            "    ON a1.RorderNo = b.rorder_fno\n" +
            "JOIN ops_core.dbo.ops_order_assign_result c\n" +
            "    ON b.rorder_no   = c.order_no\n" +
            "   AND b.rorder_item = c.order_item\n" +
            "   AND c.delflag = 0\n" +
            "WHERE\n" +
            "    b.prod_flag = 2\n" +
            " UNION all\n" +
            " SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=a.ModelNo,a.warehouse_code,qty=a.quantity ,pd_data_type='3',pd_data_source='3'\n" +
            " FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.RorderNo=b.rorder_fno\n" +
            " WHERE b.prod_flag<>2 and Invflag<>'B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0'and a.modelno<>'手续费' and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate})\n" +
            " UNION all\n" +
            " SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=d.sub_ModelNo,c.warehouse_code,qty=d.sub_qty ,pd_data_type='3',pd_data_source='3'\n" +
            " FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.RorderNo=b.rorder_fno\n" +
            " INNER JOIN ops_core.dbo.ops_pco c\n" +
            " ON b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
            " INNER JOIN ops_core.dbo.ops_pco_item d\n" +
            " ON c.pco_id=d.pco_id and d.delflag=0\n" +
            " WHERE InvFlag='B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0' and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate})\n" +
            " UNION all\n" +
            " SELECT InsertTime,order_no=b.rorder_fno ,rorder_no=rorder_no,item_no=rorder_item,model_no=a.ModelNo,warehouse_code=case when e.warehouse_code is null then b.stock_code else e.warehouse_code end,qty=a.quantity ,pd_data_type='3',pd_data_source='3'\n" +
            " FROM [10.116.32.17].[smcdb30].[dbo].[SalesInvoice] a\n" +
            " INNER JOIN ops_core.dbo.rcvdetail b\n" +
            " ON a.RorderNo=b.rorder_fno\n" +
            " LEFT JOIN ops_core.dbo.ops_do e\n" +
            " ON b.rorder_no=e.order_id and b.rorder_item=e.order_item and e.do_type='JYCK' and e.delflag=0\n" +
            " LEFT JOIN ops_core.dbo.ops_pco c\n" +
            " ON b.rorder_no=c.order_id and b.rorder_item=c.order_item and c.delflag=0\n" +
            " LEFT JOIN ops_core.dbo.ops_pco_item d\n" +
            " ON c.pco_id=d.pco_id and d.delflag=0\n" +
            " WHERE InvFlag='B' and Invflag<>'D'and Invflag<>'3'and Invflag1='0' and c.order_id is null and InvoiceNo is null and (accountingDate is null or accountingDate>=#{execDate});")
    List<OpsAsPdCompensateDataDO> cwData(@Param("execDate") String execDate,@Param("year") String year,
                                         @Param("month") String month,@Param("dataSource") String dataSource);


    // 调拨在途 -- ops
//    @Select("select rorder_no=orderNo,item_no=itemNo,model_no=modelno,split_item_no=splitItemNo,warehouse_code=sign_warehouse_code,qty=quantity,PD_data_type='4',pd_data_source = '2' from ${dataSource}.ops_inventory_move\n" +
//            "where delflag=0 and source_type=1 and quantity != 0 and inventory_status='T3'")
    @Select("select rorder_no=orderNo,item_no=itemNo,model_no=modelno,split_item_no=splitItemNo,warehouse_code=sign_warehouse_code,qty=quantity,PD_data_type='4',pd_data_source = '2' \n" +
            "from ${dataSource}.ops_inventory_move left join ${dataSource}.ops_warehouse on sign_warehouse_code = ops_warehouse.warehouse_code\n" +
            " where ops_inventory_move.delflag=0 and source_type=1 and quantity != 0 and inventory_status='T3' and ops_warehouse.delflag = '0'")
    List<OpsAsPdCompensateDataDO> dbztWithOps(@Param("dataSource") String dataSource);


//    @Select("SELECT rorder_no=orderNo,item_no=itemNo,doi.modelno,doi.qty,split_item_no=splitItemNo,warehouse_code=sign_warehouse_code,PD_data_type='7',pd_data_source = '2'   \n" +
//            " FROM ${dataSource}.[ops_inventory_move] ops_inventory_move \n" +
//            " left join ${dataSource}.ops_warehouse ops_warehouse on sign_warehouse_code = ops_warehouse.warehouse_code  \n" +
//            " left join ${dataSource}.ops_do do on orderNo = do.order_id and itemno = do.order_item \n" +
//            " left join ${dataSource}.ops_do_item doi on do.do_id = doi.do_id\n" +
//            "WHERE [inventory_status] = 'X' AND [source_type] = '3' and ops_inventory_move.delflag=0  and quantity != 0 and ops_warehouse.delflag = '0' ")
    @Select("SELECT rorder_no = temp.order_id, item_no = temp.order_item,modelno = temp.modelno,qty = temp.out_qty,split_item_no = temp.num,warehouse_code = temp.warehouseCode, PD_data_type = '7',pd_data_source = '2'\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT \n" +
            "        a.ApplyNo,\n" +
            "        c.order_id,          \n" +
            "        c.order_item,        \n" +
            "        c.num,               \n" +
            "        b.warehouseCode,    \n" +
            "        d.modelno,           \n" +
            "        d.out_qty,           \n" +
            "        b.OptCode,\n" +
            "        e.inventory_status,\n" +
            "        c.do_id,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY c.order_id, c.order_item ORDER BY a.ApplyNo DESC) AS rn \n" +
            "    FROM\n" +
            "        ops_sharedb.dbo.stock_assembly a\n" +
            "    INNER JOIN ops_core.dbo.ops_do c \n" +
            "        ON a.ApplyNo = c.order_id \n" +
            "        AND c.do_status = '3' \n" +
            "        AND c.delflag = '0'\n" +
            "    INNER JOIN ops_core.dbo.ops_inventory_move e \n" +
            "        ON a.ApplyNo = e.orderNo \n" +
            "        AND e.delflag = '0'\n" +
            "    INNER JOIN ops_sharedb.dbo.stock_assembly_detail b \n" +
            "        ON a.id = b.applyid \n" +
            "        AND b.IsTransOut = '1' \n" +
            "        AND b.OptCode <> '9'\n" +
            "    INNER JOIN ops_core.dbo.ops_do_item d \n" +
            "        ON c.do_id = d.do_id \n" +
            "        AND d.delflag = '0' \n" +
            "    WHERE\n" +
            "        a.Status = '5' \n" +
            "        AND a.AssembleType IN ('1', '6')\n" +
            ") AS temp  \n" +
            "WHERE temp.rn = 1; ")
    List<OpsAsPdCompensateDataDO> opszhzt(@Param("dataSource") String dataSource);

    // 制造在途 -- ops
    @Select("select rorder_no=orderNo,item_no=itemNo,model_no=modelno,split_item_no=splitItemNo,warehouse_code=sign_warehouse_code,qty=quantity,PD_data_type='5',pd_data_source = '2' from ${dataSource}.ops_inventory_move a\n" +
            "inner join (select invoice_no,supplier_code=case when supplier_code in ('CM','CN','CT','GZ','TZ','YZ') then 'CN' else 'EX' end from ${dataSource}.ops_po_invoice group by invoice_no,case when supplier_code in ('CM','CN','CT','GZ','TZ','YZ') then 'CN' else 'EX' end) b on a.invoiceNo=b.invoice_no\n" +
            "where delflag=0 and source_type=0 and inventory_status='T1' and a.quantity<>0  and b.supplier_code='CN'")
    List<OpsAsPdCompensateDataDO> zzztWithOps(@Param("dataSource") String dataSource);



    @Select(" select * from ${dataSource}.ops_as_pd_compensate_data where pd_data_type = '5' and pd_data_source = '1' and pd_batch_no = #{batchNo} ")
    List<OpsAsPdCompensateDataDO> zzztWithWms(@Param("dataSource") String dataSource,
                                              @Param("batchNo") String batchNo);

    /**
     * wms组换在途
     * @param dataSource
     * @return
     */
    @Select(" select * from ${dataSource}.ops_as_pd_compensate_data where pd_data_type = '7' and pd_data_source = '1' and pd_batch_no = #{batchNo}  ")
    List<OpsAsPdCompensateDataDO> wmszhztData(@Param("dataSource") String dataSource, @Param("batchNo") String batchNo);

    // WMS补偿数据抽取
    @Insert("<script>" +
            " insert into [ops_report].[dbo].[ops_as_pd_compensate_data] (" +
            "  [pd_batch_no] " +
            " ,[warehouse_code] " +
            " ,[pd_bill_no] " +
            " ,[pd_data_type] " +
            " ,[pd_data_source] " +
            " ,[order_no] " +
            " ,[rorder_no] " +
            " ,[item_no] " +
            " ,[split_item_no] " +
            " ,[do_id] " +
            " ,[model_no] " +
            " ,[qty] " +
            " ,[create_user] " +
            " ,[create_time] " +
            " ,[update_user] " +
            " ,[update_time]" +
            " ) values " +
            " <foreach collection='list' item='item' index='index'  separator=',' > " +
            "(" +
            "  #{item.pdBatchNo}, " +
            "  #{item.warehouseCode}, " +
            "  #{item.pdBillNo}, " +
            "  #{item.pdDataType}, " +
            "  #{item.pdDataSource}, " +
            "  #{item.orderNo}, " +
            "  #{item.rorderNo}, " +
            "  #{item.itemNo}, " +
            "  #{item.splitItemNo}, " +
            "  #{item.doId}, " +
            "  #{item.modelNo}, " +
            "  #{item.qty}, " +
            "  #{item.createUser}, " +
            "  #{item.createTime}, " +
            "  #{item.updateUser}, " +
            "  #{item.updateTime} " +
            ")" +
            " </foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int batchInsertCompensateData(@Param("list") List<OpsAsPdCompensateDataDO> list,@Param("batchNo") String batchNo);

    @Delete("delete from ops_report.dbo.ops_as_pd_compensate_data where pd_batch_no = #{pdBatchNo} and pd_data_source = #{dataSource} ")
    void clearOpsCompensateDataWithWMS(@Param("pdBatchNo")String pdBatchNo,@Param("dataSource") String dataSource);

    @Delete("delete from ops_report.dbo.ops_as_pd_compensate_data " +
            " where pd_batch_no = #{pdBatchNo} and pd_data_source = #{dataSource} and pd_data_type = #{dataType} ")
    void clearOpsReturnCompensateData(@Param("pdBatchNo")String pdBatchNo,
                                      @Param("dataSource") String dataSource,
                                      @Param("dataType") String dataType);
}
