package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;


@Mapper
@DS("opsdb")
public interface CommonMapper {

    //16383 发票入库修改成本v1
    @Select("SELECT count(id)  from ops_core.dbo.ops_ro where  sign_time > '2025-01-01' and invoice_id = #{invoiceId} and delflag =0  ")
    int getOpsRoSignTimeGT(@Param("invoiceId") Long invoiceId);


    //16383 发票入库修改成本 v2
    @Select("SELECT count(id)  from ops_core.dbo.imp_invoice_master where  confirm_date > '2025-01-01' and id = #{invoiceId} ")
    int getImpInvoiceMasterConfirmDateGT(@Param("invoiceId") Long invoiceId);

    @Select("select Top(1) id from supplier where name like #{name} order by len(name) asc")
    String getSupplierCodeByName(@Param("name") String name);

    @Select("SELECT id FROM currency where AbbrName=#{currency}")
    String getCurrencyIdByName(@Param("currency") String currency);

    @Select("select name from supplier where id=#{id}")
    String getSupplierNameByid(@Param("id") String id);

    @Select("SELECT companyId FROM supplier WHERE id=#{id}")
    String getCompanyIdById(@Param("id") String id);


    @Update("update ops_core.dbo.shikomi_total set PlanUseDate = #{planUseDate}, UpdateTime = getDate() where ShikomiNo = #{shikomiNo} ")
    void updateShikomiTotal(@Param("shikomiNo")String shikomiNo,@Param("planUseDate")String planUseDate);


    @Select("update ops_ui.dbo.tbl_datatype set ext_note1 = #{maxId} where class_code = '9002' and code = '25'")
    void updateUiTblDateType(@Param("maxId")String maxId);

    @Select("{ call ops_core.dbo.cal_monthly_inventory_summary }")
    @Options(statementType = StatementType.CALLABLE)
    void execCalMonthlyInventorySummary();

    @Select("select count(id) from ops_sharedb.dbo.preorder_account_detail " +
            " where inventory_id = #{id} and inventory_id_item = #{item} and batch_no = #{batchNo} and status in ('3','4','5') ")
    int checkPreAccountDetail(@Param("id") Long id,
                              @Param("item") int item,
                              @Param("batchNo") String batchNo);
}
