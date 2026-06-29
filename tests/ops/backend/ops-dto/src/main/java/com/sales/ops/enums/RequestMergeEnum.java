package com.sales.ops.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并采购 枚举类
 */
public enum RequestMergeEnum {

	MIDQTY("8", "单条合并中位数量"),
	MINCOUNT("10", "单条合并最小项数"),
	MAINFOLDSIGN("1", "型号式样书"),
	MAXVIEWSIZE("2000", "单页显示最大数量");

	RequestMergeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	//判断 采购在途和调拨在途
	public static boolean containT(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		} else {
			if (InventoryStatusEnum.CGTRANS.getCode().equals(value) || InventoryStatusEnum.DBTRANS.getCode().equals(value)) {
				return true;
			} else {
				return false;
			}
		}
	}
	// 定义多段价格可以合并的，供应商清单
	public static List<String> lotSuppilyList() {
		List list = new ArrayList<String>();
		list.add("CN");
		list.add("CM");
		list.add("CT");
		return list;
	}

	// 从非BIN中筛选purchaseType为A和B的数据，客户订单和先行在库，进行下一步的合并采购推荐,其余订单不参与合并
	public static List<String> purchasetypeList() {
		List list = new ArrayList<String>();
		list.add(PurchaseTypeEnum.SALE.getTypeCode());
		list.add(PurchaseTypeEnum.PRE.getTypeCode());
		return list;
	}


	public static List<String> orderTypeList() {
		List list = new ArrayList<String>();
		list.add(OrderTypeEnum.BIN);
		list.add(OrderTypeEnum.WT);
		return list;
	}

	public static List<String> specmarkList() {
		List list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		return list;
	}


	public static InventoryStatusEnum getEnumByType(String type) {
		return Arrays.stream(InventoryStatusEnum.values()).filter(Enum -> Enum.getCode().equals(type)).findFirst().orElse(null);
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
