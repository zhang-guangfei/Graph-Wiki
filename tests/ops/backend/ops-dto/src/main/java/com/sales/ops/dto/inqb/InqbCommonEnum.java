package com.sales.ops.dto.inqb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：B91717
 * 催促校验枚举类
 */
public enum InqbCommonEnum {

    APPLYNOPREFIX("OB", "OPS-INQ-B申请号前缀"),
    DATE_FORMATTER("yyyyMMdd","OPS-INQ-B申请号日期格式"),

    SALES_DATATYPE("sales","催促模块-销售门户接入时data_sources"),

    STOCKTYPECG("CG","出库类别采购"),

    DATEDEADLINE("5", "有效截止日,从回复当日开始可用，有效期为回复时间次日起的5个工作日均有效"),
    APPLYNOREDIS("ops:inqb:applyno", "INQ-B申请号，redis key"),

    GROUPADDPORT("http://10.116.1.13:9091/inqb/handle/addInqbHandle", "集团采购的接口地址"),

    DIFFDAYVALID("1", "INQB申请可提交校验中的催促时间的校验"),

    CODECONFIGKEY("ops:inqb:codeconfig", "INQ-B-code_config，redis key");


    private String code;
    private String message;

    InqbCommonEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getType() {
        return code;
    }
    public String getDesc() {
        return message;
    }
    public static InqbCommonEnum getType(String type) {
        for (InqbCommonEnum typeEnum : values()) {
            if (typeEnum.code.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static List<String> manuSuppilyList() {
        List<String> list = new ArrayList<String>();
        list.add("CN");
        list.add("CM");
        list.add("YZ");
        list.add("CT");
        list.add("TZ");
        list.add("GN");
        return list;
    }

}
