package com.sales.ops.enums;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：邮件发送状态
 * @date ：Created in 2021/11/4 14:08
 */
public enum SendStatusEnum {

	INIT("0", "待下发"), SUCCESS("1", "成功"), FAIR("2", "失败"), SUSPENDED("3", "挂起，暂不处理"), READ("4", "已读"), PURCHASEINIT("5",
			"采购发单未发送"), PURCHASESUCCESS("6", "采购发单已发送");
	private String type;
	private String desc;

	SendStatusEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}
}
