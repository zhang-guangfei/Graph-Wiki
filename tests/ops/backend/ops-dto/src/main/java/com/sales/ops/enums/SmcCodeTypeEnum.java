package com.sales.ops.enums;

public enum SmcCodeTypeEnum {

    /**
     * B91717
     * 配置不同smcCode对应订单类型
     * 用于采购-工厂订单发送制造使用
     */
	BJWL("9501200", "北京分公司客户订单"),
	SHWL("9501202", "上海物流补库"),
	BJWUBK("9501201", "北京物流补库"),
	GZWUBK("9501205", "广州物流补库"),
	ZZLK("9501203", "制造六课"),
	GZKH("9501211", "广州分公司客户订单"),
	SFKH("9501282", "上海分公司客户订单"),
	GZZZ("9501212", "SMC自动化广州制造订单"),
	CZWLBK("9501207", "常州物流补库"),
	CZWL("9501208", "常州物流");
        private String code;
        private String accepter;
        
		public String getCode() {
			return code;
		}

		public String getAccepter() {
			return accepter;
		}

		private SmcCodeTypeEnum(String code, String accepter) {
			this.code = code;
			this.accepter = accepter;
		}

		public static String getAccepter(String code) {
		    for(SmcCodeTypeEnum c : SmcCodeTypeEnum.values()) {
                if (c.getCode().equals(code)) {
                    return  c.accepter;
                }
            }
		    return  null;
        }


}
