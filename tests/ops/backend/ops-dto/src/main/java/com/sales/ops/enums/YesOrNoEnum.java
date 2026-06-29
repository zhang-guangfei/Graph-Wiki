package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/11/17 18:10
 * @description：后台使用，不用字典
 */
public enum YesOrNoEnum {

    NO(Boolean.FALSE, "否"),
    YES(Boolean.TRUE, "是");

    private boolean status;
    private String name;

    YesOrNoEnum(boolean status, String name) {
        this.status = status;
        this.name = name;
    }
}
