package com.sales.ops.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sales.ops.enums.ProductValidateEnum;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 产品校验状态信息
 * ClassName: ProductValidateResult <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2021年1月15日 上午10:59:07 <br/>  
 *  
 * @author B74718  
 * @version   
 * @since JDK 1.8
 * 产品验证信息类
 */

@JsonInclude(Include.NON_EMPTY)
public class ProductValidateResult {
	
	/**
	 * 状态码 验证编码
	 */
	private int code;
	
	/**
	 * 验证状态信息  产品描述信息
	 */
	private String description;
	
	
	

	public ProductValidateResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductValidateResult(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public ProductValidateResult(ProductValidateEnum productValidateEnum) {
		this.code = productValidateEnum.getCode();
		this.description = productValidateEnum.getDescription();
	}
	
	public static boolean isContain(List<ProductValidateResult> validateList, ProductValidateEnum productValidateEnum) {
		if (CollectionUtils.isEmpty(validateList)) {
			return false;
		}
		for (ProductValidateResult productValidateResult : validateList) {
			
			if (productValidateResult.getCode() == productValidateEnum.getCode() ) {
				return true;
			}
		}
		
		return false;
		
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
