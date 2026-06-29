package com.sales.ops.dto.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author C12961
 * @date 2022/6/27 16:51
 */
public class RemarkBuilder {


    public static final String BEGIN_PRODUCE_DATE="开始生产日期";
    public static final String ESTIMATED_DELIVERY_DATE="预计交货日期";
    public static final String Reason="变更交货期原因";
    public static final String PORT_ARRIVE_DATE="到港日期";
    public static final String CUSTOMS_DATE="报关日期";
    public static final String INVOICE_NO="发票号";
    public static final String ALLOCAT_QUANTITY ="分配数量";
    public static final String INVOICE_QUANTITY ="发票数量";
    public static final String PRERECEIVE_DATE="预计到货日期";

    public static final String SIGN_IN_QUANTITY = "签收数量";
    public static final String SIGN_IN_DATE = "签收日期";
    public static final String CONFIRM_QUANTITY = "确认数量";
    public static final String CONFIRM_DATE = "确认日期";


    private HashMap<String, String> map;

    public RemarkBuilder() {
        this.map = new LinkedHashMap<>();
    }

    public static RemarkBuilder create() {
        return new RemarkBuilder();
    }

    public RemarkBuilder set(String key, String value) {
        if (value != null) {
            this.map.put(key, value);
        }
        return this;
    }

    public RemarkBuilder(String remark) {
        this();
        if (StringUtils.isNotBlank(remark)) {
            String[] split = remark.split(",");
            for (String s : split) {
                String[] arr = s.split(":");
                if(arr.length==2){
                    this.map.put(arr[0], arr[1]);
                }
            }
        }
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey() + ":" + entry.getValue() + ",");
        }
        String remark = builder.toString();
        return StringUtils.removeEnd(remark, ",");
    }

    public void clear(){
        this.map.clear();
    }
}
