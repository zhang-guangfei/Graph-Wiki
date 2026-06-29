package com.sales.ops.dto.inqb;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public enum InqbDeptProvinceEnum {
    BJProvince("CN0-B", "北方订单"),
    SHProvince("CN0-S", "南方订单"),
    GZProvince("CN0-G", "南方订单"),
    JSProvince("CN0-J", "南方订单"),
    AUProvince("CNO", "北方订单"),
    OTHERProvince("OTHER", "北方订单"),

    // 以下按照部门所属的分公司进行配置
    DeptBJProvince("北京分公司", "北方订单"),
    DeptSHProvince("上海分公司", "南方订单"),
    DeptGZProvince("广州分公司", "南方订单"),
    DeptJSProvince("江苏分公司", "南方订单"),
    DeptAUProvince("SMC自动化有限公司", "北方订单");

    private String type;
    private String desc;

    InqbDeptProvinceEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InqbDeptProvinceEnum parse(String type) {
        return Arrays.stream(InqbDeptProvinceEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }

    public static List<String> northList() {
        List<String> list = new ArrayList<String>();
        list.add(BJProvince.type);
        list.add(AUProvince.type);
        return list;
    }


    public static List<String> southList() {
        List<String> list = new ArrayList<String>();
        list.add(SHProvince.type);
        list.add(GZProvince.type);
        list.add(JSProvince.type);
        return list;
    }


    public static List<String> deptNorthList() {
        List<String> list = new ArrayList<String>();
        list.add(DeptBJProvince.type);
        list.add(DeptAUProvince.type);
        return list;
    }


    public static List<String> deptSouthList() {
        List<String> list = new ArrayList<String>();
        list.add(DeptSHProvince.type);
        list.add(DeptGZProvince.type);
        list.add(DeptJSProvince.type);
        return list;
    }
}
