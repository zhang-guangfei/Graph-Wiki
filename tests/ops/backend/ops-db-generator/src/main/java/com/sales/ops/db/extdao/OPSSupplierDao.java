package com.sales.ops.db.extdao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：oredis
 * @date ：Created in 2021/10/18 17:23
 */
public interface OPSSupplierDao {

	/**
	 * @author C14717 根据最近一小时新增或更新来删除redis table
	 * @return
	 */
	@Select("SELECT id FROM dbo.supplier where DateDiff(mi,updateTime,getDate())<=#{time}")
	public List<String> refreshSupplierData(@Param("time") Long time);

	@Select("select a.id,b.CountryId from supplier a inner join supplier_company b on a.companyId=b.Id")
	List<Map<String, String>> getSupplierCountry();

	@Select("select b.CountryId from supplier a inner join supplier_company b on a.companyId=b.Id where a.id=#{supplier}")
	String getCountryBySupplier(@Param("supplier") String supplier);
}
