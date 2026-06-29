
package com.smc.smccloud.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilterPostion {
	
	/**
	 * 需要过滤岗位的部门
 	 */
	private List<String> departPrefixs = new ArrayList<>();

	public List<String> getDepartPrefixs() {
		return departPrefixs;
	}

	public void setDepartPrefixs(List<String> departPrefixs) {
		this.departPrefixs = departPrefixs;
	}

}
