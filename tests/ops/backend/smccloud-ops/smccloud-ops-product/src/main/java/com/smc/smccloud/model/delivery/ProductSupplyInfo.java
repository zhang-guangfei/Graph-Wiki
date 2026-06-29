package com.smc.smccloud.model.delivery;

import lombok.Data;

@Data
public class ProductSupplyInfo {

	private String modelNo;

	private String supplyId;

	private Integer nstdProductManuDay;

	private Integer stdProductManuDay;

	private Integer shipDeliveryDay;

	private Integer fstDeliveryDay;

	private Integer quantity;

	private String fstTransTypeId;

	private String nonstandard_sized_product;

	private String designTypeId;

	private String warehouseCode;

}
