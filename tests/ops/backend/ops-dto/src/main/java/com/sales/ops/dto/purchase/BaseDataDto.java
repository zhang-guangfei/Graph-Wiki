package com.sales.ops.dto.purchase;

import java.io.Serializable;

public class BaseDataDto implements Serializable {

	/**
	 * bug11808/bug12472 增加字典实体
	 */
	private static final long serialVersionUID = 8349177769158089006L;

	private String code;

	private String codeName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
