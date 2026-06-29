package com.sales.ops.enums;

/**
 * TMS路由节点
 * @author C12961
 * @date 2022/6/15 15:32
 */
public enum TmsTrackStatus {
    T00("T00","待执行"),
    T10("T10","已提货"),
    T20("T20","入中转站"),
    T30("T30","已入库"),
    T40("T40","已装车"),
    T50("T50","已发运"),
    T60("T60","已配送"),
    T70("T70","已到达"),
    T80("T80","配送完成"),
    T85("T85","回场"),
    T90("T90","取消"),
    T99("T99","已关闭"),
    ;

    private String code;
    private String desc;


    TmsTrackStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
