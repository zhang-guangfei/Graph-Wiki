package com.sales.ops.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/2 13:45
 * @description：收货指令类型
 */
public enum RoTypeEnum {
    THRK("THRK", "TY", "退货入库收货"),
    CGRKGK("CGRKGK", "PR", "顾客采购入库收货"),//废弃
    CGRKBK("CGRKBK", "PR", "补库采购入库收货"),
    DBRK("DBRK", "TT","调拨入库收货-仓库间调拨"),
    //bugid :8741  c14717 20221201
    CGDBRK("CGDBRK", "TT","采购调拨入库"),
    //ZDDBRK("ZDDBRK", "TT","主动调拨入库收货"),

    TZRK("TZRK","ADJ","调账入库"),//调账不下发wms
    TKRK("TKRK","TK","调库入库"),

    ZHRK("ZHRK","ZH","组换入库"),
    //only wms
    ZHRKOW("ZHRKOW","ZH","组换入库仅调wms库存"),

    PYRK("PYRK", "ADJ","盘盈入库收货"),
    QBRK("QBRK", "","情报占用入库"),//情报不下发wms
    QBQX("QBQX", "","情报占用取消"),//情报不下发wms
    ;


    private String type;
    private String wltype;
    private String desc;

    RoTypeEnum(String type, String wltype, String desc) {
        this.type = type;
        this.wltype = wltype;
        this.desc = desc;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/17 13:24
     * @description：按照单据类型 查询
     */
    public static RoTypeEnum getType(String type) {
        for (RoTypeEnum typeEnum : values()) {
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


    public static List<RoTypeEnum> DBType() {
        return Arrays.asList(RoTypeEnum.DBRK, RoTypeEnum.CGDBRK, RoTypeEnum.TKRK);
    }

    public static boolean isDB(String type) {
        List<String> list = RoTypeEnum.DBType().stream().map(RoTypeEnum::getType).collect(Collectors.toList());
        return list.contains(type);
    }

    public DoTypeEnum getDoTypeEnum(){
        if(this==DBRK){
            return DoTypeEnum.DBCK;
        } else if(this==CGDBRK){
            return DoTypeEnum.CGDBCK;
        } else if(this==TKRK){
            return DoTypeEnum.TKCK;
        }else if(this==TZRK){
            return DoTypeEnum.TZCK;
        }else if(this==ZHRK){
            return DoTypeEnum.ZHCK;
        }else if(this==ZHRKOW){
            return DoTypeEnum.ZHCKOW;
        }else if(this==QBRK){
            return DoTypeEnum.QBCK;
        }else if(this==QBQX){
            return DoTypeEnum.QBQX;
        }else if(this==PYRK){
            return DoTypeEnum.PKCK;
        }
        return null;
    }

}
