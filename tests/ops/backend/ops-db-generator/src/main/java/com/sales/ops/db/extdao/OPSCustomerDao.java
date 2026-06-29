package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.customer.CustomerInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops客户相关redis
 * @date ：Created in 2021/10/18 17:23
 */
public interface OPSCustomerDao {

    /**
     * @author C14717
     * 根据最近一小时新增或更新来删除redis  table
     * @return
     */
    @Select("SELECT customer_no FROM dbo.ops_customer where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT customer_no FROM dbo.ops_customer where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsCustomerData(@Param("time") Long time);

    /**
     * @author C14717
     * 根据最近一小时新增或更新来删除redis  table
     * @return
     */
    @Select("SELECT customer_no FROM dbo.ops_customer_warehouse where DateDiff(mi,cre_time,getDate())<=#{time} UNION ALL SELECT customer_no FROM dbo.ops_customer_warehouse where DateDiff(mi,modify_time,getDate())<=#{time}")
    public List<String> refreshOpsCustomerWarehouseData(@Param("time") Long time);


    @Select("SELECT TOP 10 CUSTOMERNO,USERNO,CSTMNAME " +
            "FROM dbo.CUSTOMER WHERE CUSTOMERNO like CONCAT(#{customerNo},'%') OR CSTMNAME like CONCAT(#{customerNo},'%')")
    List<Customer> queryCustomer(@Param("customerNo") String customerNo);

    @Select("select customer_no as customerNo, cstm.name as customerName, HRUnitId as hrUnitId, tion.Name as hrUnitName, PSNSMCID as userId, AgentNo as agentNo\n" +
            ", case when CustomerType = '0' then '直销' when CustomerType = '1' then '代理' when CustomerType = '2' then '经销' end as customerType" +
            ", SMCGroupId, tradeSubjectId as tradeSubjectId, TaxNo as taxNo, bank as bank, AccountNo as accountNo, InvoiceAddress as invoiceAddress" +
            ", InvoicePhoneNo as invoicePhoneNo, CreditTerm as creditTerm, CreditGrade as creditGrade, PayTerm as payTerm, CreditLimit as creditLimit" +
            ", GraceLimit as graceLimit, AdjustLimit as adjustLimit from ops_customer cstm " +
            "left join ops_pay_term term on cstm.customer_no = term.CustomerNo " +
            "left join hr_organization tion on cstm.HRUnitId = tion.Id " +
            "where customer_no like CONCAT(#{customerNo},'%')")
    List<CustomerInformation> findCustomerInfoByCustomerNo(String customerNo);

    @Select("SELECT TOP 10 customer_no as customerNo, name as customerName FROM ops_customer " +
            "WHERE customer_no like CONCAT(#{customerNoOrName},'%') OR name like CONCAT(#{customerNoOrName},'%')")
    List<Map<String, String>> findCustomerByCustomerNoOrName(String customerNoOrName);
}
