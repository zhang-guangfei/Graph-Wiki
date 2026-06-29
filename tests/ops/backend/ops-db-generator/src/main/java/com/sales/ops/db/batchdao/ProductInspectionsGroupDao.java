package com.sales.ops.db.batchdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sales.ops.dto.product.ProductInspectionsGroupInfo;
import com.sales.ops.dto.product.ProductRecordRefModelToSales;

public interface ProductInspectionsGroupDao {

	List<ProductInspectionsGroupInfo> selectInfos(List<String> modelno);

	// 获取3C及冷干机清单
	List<ProductRecordRefModelToSales> selectAll();

	ProductRecordRefModelToSales selectByModelNo(@Param("modelNo") String modelNo);
}
