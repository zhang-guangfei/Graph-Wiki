package com.sales.ops.serviceimpl.config;

import java.util.HashMap;
import java.util.Map;

public class SpeicalTransConfig {

	// 不可使用快船smccode列表
	public static final Map<String, Boolean> NOTQUICKSHIPSMCCODE = new HashMap<String, Boolean>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6144909248180538486L;

		{
			put("9501250", true);
			put("9501252", true);
		}
	};
}
