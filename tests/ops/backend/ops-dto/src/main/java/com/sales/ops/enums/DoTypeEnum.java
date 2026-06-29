package com.sales.ops.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/2 13:36
 * @description：物流出库类别
 */
public enum DoTypeEnum {

    JYCK("JYCK", "CM", "客户交易出库单"),

    DBCK("DBCK", "TT", "调拨出库"),
    //ZDDBCK("ZDDBCK","TT","主动调拨出库"),
    CGDBCK("CGDBCK", "TT", "采购调拨出库"),

    TZCK("TZCK", "ADJ", "调账出库"),
    TKCK("TKCK", "TK", "调库出库"),

    ZHCK("ZHCK", "ZH", "组换出库调整ops和wms"),
    //only wms
    ZHCKOW("ZHCKOW","ZH","组换出库仅调wms库存"),

    PKCK("PKCK", "ADJ","盘亏出库收货"),

    QBCK("QBCK","","情报占用出库"),
    QBQX("QBQX","","情报占用撤销"),

    /**
     * 临时使用
     * 针对转定取消使用（2022-09-05）
     */
    ZDQX("ZDQX","ZD","转定取消"),

    ;


    private String type;
    private String wltype;
    private String desc;

    DoTypeEnum(String type, String wltype, String desc) {
        this.type = type;
        this.wltype = wltype;
        this.desc = desc;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/17 13:24
     * @description：按照单据类型 查询
     */
    public static DoTypeEnum getType(String type) {
        for (DoTypeEnum typeEnum : values()) {
            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getWltype() {
        return wltype;
    }

    public String getDesc() {
        return desc;
    }


    public static boolean isDB(String type) {
        DoTypeEnum typeEnum = DoTypeEnum.getType(type);
        List<DoTypeEnum> dbList = Arrays.asList(DBCK, CGDBCK, TKCK, ZDQX);
        return dbList.contains(typeEnum);
    }

    public RoTypeEnum getRoTypeEnum(){
        if(this==DBCK){
            return RoTypeEnum.DBRK;
        } else if(this==CGDBCK){
            return RoTypeEnum.CGDBRK;
        } else if(this==TKCK){
            return RoTypeEnum.TKRK;
        }else if(this==TZCK){
            return RoTypeEnum.TZRK;
        }else if(this==ZHCK){
            return RoTypeEnum.ZHRK;
        }else if(this==ZHCKOW){
            return RoTypeEnum.ZHRKOW;
        }else if(this==QBCK){
            return RoTypeEnum.QBRK;
        }else if(this==QBQX){
            return RoTypeEnum.QBQX;
        }else if(this==PKCK){
            return RoTypeEnum.PYRK;
        }
        return null;
    }

}
