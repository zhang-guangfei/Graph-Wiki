package com.sales.ops.db.batchdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sales.ops.db.entity.OpsCustomer;

//bug11473自动发单
public interface PoBatchDao {

	public List<String> restrictModel(List<String> modelNos);

	public List<OpsCustomer> getCustomerInfo(List<String> customerNos);

	public void updateRequestById(@Param("list") List<Long> id, @Param("status") String status,
			@Param("version") String version);
}
