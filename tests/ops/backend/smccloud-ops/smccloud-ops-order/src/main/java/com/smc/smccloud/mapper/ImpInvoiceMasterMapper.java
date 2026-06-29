package com.smc.smccloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.ImpInvoiceMasterDO;

/**
 * Author: B90034 Date: 2021-12-02 13:31 Description:
 */
@Mapper
public interface ImpInvoiceMasterMapper extends BaseMapper<ImpInvoiceMasterDO> {

	@Select("select top 1 * from imp_invoice_master where invoice_No=#{invoiceNo} and supplier_Code=#{supplierNo} and status<>9 order by ship_date desc")
	ImpInvoiceMasterDO getLastImpInvoiceMaster(@Param("invoiceNo") String invoiceNo,
			@Param("supplierNo") String supplierNo);

	@Select("select top 1 receiveWarehouseId FROM ops_purchaseInvoice where poNo=#{poNo}")
	String getReceiveWarehouseCode(@Param("poNo") String poNo);

	@Select("select top 1 receiveWarehouseId FROM ops_purchaseInvoice where orderNo=#{orderNo} and itemNo=#{itemNo} and isnull(splitItemNo,0)=#{splitItemNo}")
	String getReceiveWarehouseCodeByOrderNo(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo,
			@Param("splitItemNo") Integer splitItemNo);

	// @Select("select distinct id from imp_invoice_master" +
	// " where status=2 and update_time>=#{optdate} and (invoice_type=3 or
	// remark like 'BJ制造发货数据' or remark='GZ制造发货数据')")
	// List<Long> listGPtoconfirmInvoiceId(@Param("optdate") String optdate);

	// bug15253 查询所有发票，不限制单独制造
	@Select("select distinct id from imp_invoice_master where status=2 and update_time>=#{optdate} ")
	List<Long> listGPtoconfirmInvoiceId(@Param("optdate") String optdate);
}
