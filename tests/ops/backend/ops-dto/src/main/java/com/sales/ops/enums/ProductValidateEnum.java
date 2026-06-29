package com.sales.ops.enums;

/**
 * 产品校验状态枚举类
 * ClassName: ProductValidateEnum <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2021年1月15日 上午10:58:19 <br/>  
 *  
 * @author B74718  
 * @version   
 * @since JDK 1.8
 */
public enum ProductValidateEnum {

	/**
	 * 正常型号
	 */
	NORMAL(20, "正常型号"),
	/**
	 * 收敛
	 */
	EOS(21, "此型号为收敛型号，请谨慎选择"),
	/**
	 * 贩卖限制
	 */
	RESTRICT(22, "此型号为贩卖限制型号，如要继续选择，请先解除贩卖限制"),
	/**
	 *  未知型号
	 */
	UNKNOWN(23, "未知型号，请先E价格问询"),
	/**
	 * 错误型号
	 */
	ERROR(24, "此型号为错误型号，请重新输入");

	private int code;

	private String description;

	private ProductValidateEnum(int code, String description) {
		this.code = code;
		this.setDescription(description);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
