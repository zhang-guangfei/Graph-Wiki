package com.sales.ops.dto.inqb;

/**
 * @author ：B91717
 * 催促校验枚举类
 */
public enum InqbVerifyMsgEnum {
    DELIVERYNULL("0", "当前型号不是供应商可生产的型号，请重试!"),
    ITEMNONULL("1","当前用户型号，存在3日内的申请单，不允许再进行问询!"),
    LISTNULL("2","请求参数为空，请补充后再试！"),
    BATCHNONULL("3","催促申请批次号为空，请补充后再试！"),

    REASONNULL("4","催促申请原因为空，请补充后再试！"),

    SUPPILYCONFIGNULL("5","未配置INQB可用供应商"),

    INVENTORYLESS("6","当前INQB申请信息，存在在库数据，不允许问询！"),

    DELIVERYDATEUPMAX("7","当前INQB申请催促货期不正确(数值范围为1-999天)，请重新核对后再进行提交！"),
    REMARKUPMAX("8","INQB申请备注限制长度不能超过30个字符或15个汉字，请重新核对后再进行提交！"),

    UPQTY("9","INQB申请数量不符合要求，规定范围：0＜申请数量＜9999999！");



    private String code;
    private String message;

    InqbVerifyMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getType() {
        return code;
    }
    public String getDesc() {
        return message;
    }
    public static InqbVerifyMsgEnum getType(String type) {
        for (InqbVerifyMsgEnum typeEnum : values()) {
            if (typeEnum.code.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }


}
