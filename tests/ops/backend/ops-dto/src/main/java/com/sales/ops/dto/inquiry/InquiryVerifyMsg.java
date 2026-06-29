package com.sales.ops.dto.inquiry;

import com.sales.ops.enums.OrderTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：B91717
 * 催促校验枚举类
 */
public enum InquiryVerifyMsg {
    ORDERNONULL("0", "订单号不能为空，请补充后重试!"),
    UNPURCHASE("1", "未查到此单采购信息，不可催促!"),
    STATUSERROR("2", "当前采购单状态不能催促，当前采购单状态为:"),

    QTYRECEIVE("3","当前采购单已在运输中，不可问询！"),

    OVERSIZE("4","当前订单的催促次数已经达到6次，不可再进行催促！"),

    MANUOVERSIZE("5","当前订单的供应商为制造，且催促次数已经达到3次，不可再进行催促！"),

    JPOVERSIZE("6","当前订单的供应商为日本，催促级别为HoT-2且已在此级别催促2次，不可再催促！"),

    OVERTIME("7","当前订单的催促间隔不足24小时，不可再进行催促！"),

    INVENTORYTYPEERROR("8","当前订单的供应商为中国制造或广州制造，且出库区分为在库，不可催促！"),

    LISTNULL("9","请求参数为空，请补充后再试！"),

    REASONNULL("10","催促申请原因为空，请补充后再试！"),

    HOPEDATENULL("11","催促期望货期为空，请补充后再试！"),

    BATCHNONULL("12","催促申请批次号为空，请补充后再试！"),

    ZHILINGNULL("13","制造部指示未下发，暂不能催促！"),

    MANUSTATUSERROR("14","制造处理状态为不可催促状态，请联系业务确认当前状态！"),

    MANUNOREPLY("15","还有未回复催促，不可再催促！"),

    GETMANUERROR("16","获取制造催促状态失败，请稍后重试！"),

    JPWORKDAYERROR("17","选择的催促货期为供应商的节假日，不可催促，请重新选择日期！"),

    NOSUPPILYCONFIG("18","当前订单的供应商未配置催促发送信息，不可催促，供应商为："),

    ITEMNONULL("19","门户催促申请子项号为空，请修正后重试！"),

    REASONPARAMNULL("20","当前催促原因需要填写参数信息，请补充后重试！"),
    REASONPARAMUPLENGTH("21","催促原因参数数据超出配置长度，请重新输入！"),
    JPDLVDATEERROR("22","999999、990000、444444 交货期异常订单，不可催促！"),
    OVER1YEAR("23","催促的日期超过一年，请重新选择！"),
    REPLYNONULL("24","当前订单-供应商还未回复手配号，不可催促！"),
    AS400LANGUAGEERROR("25","发送AS400时，催促备注信息不能包含中文字符，请重新输入！"),
    UNORDERINFO("26", "未查到订单信息，不可催促!"),
    ORDERTYPEERROR("27", "当前订单类型不能催促，当前订单类型为:"),
    ORDERASSIGNRESULTNULL("28", "当前订单未找到相关的分配信息，请更换订单号后重试！"),
    STOCKTYPENULL("29", "订单分配接口返回结果出库类别为空！"),
    STOCKTYPENOCG("30", "当前拆分单号分配类别非采购，不可问询！"),
    ITEMNONEINQ("31", "当前订单不存在采购或采购子项，不可问询！"),

    ORDERNODELIVERYNULL("32", "请求订单号或催促期望货期为空，请补充后再试！"),

    CALEXPDATENULL("34", "订单催促计算的催促货期为空，请稍后再试！"),

    CALEXPDATEBEFOR("35", "，小于当前系统日期，不可催促，请更改后再试！"),

    ORDERSTATUSERROR("36", "当前订单状态不能催促，当前订单状态为:"),

    JPTRANSGVN("37", "当前订单为日本转订新加坡或越南的订单，不可催促！"),

    MANUOVERTIME("38","制造接单24小时(工作日)内，不可催促！"),
    JPProducefactory("39", "当前订单出库区分不是 现场且有手配号 订单，不可催促！"),

    AS400REMARKOVERLENGTH("40","发送AS400时，催促备注信息不能超过30个字符，请重新输入！");



    private String code;
    private String message;

    InquiryVerifyMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getType() {
        return code;
    }
    public String getDesc() {
        return message;
    }
    public static InquiryVerifyMsg getType(String type) {
        for (InquiryVerifyMsg typeEnum : values()) {
            if (typeEnum.code.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }


    public static List<String> manuGzJPSuppilyList() {
        List<String> list = new ArrayList<String>();
        list.add("CN");
        list.add("CM");
        list.add("YZ");
        list.add("CT");
        list.add("TZ");
        list.add("GN");
        list.add("GZ");
        list.add("JP");
        return list;
    }

    public static List<String> manuGzSuppilyList() {
        List<String> list = new ArrayList<String>();
        list.add("CN");
        list.add("CM");
        list.add("YZ");
        list.add("CT");
        list.add("TZ");
        list.add("GN");
        list.add("GZ");
        return list;
    }

    public static List<String> manuSuppilyList() {
        List<String> list = new ArrayList<String>();
        list.add("CN");
        list.add("CM");
        list.add("YZ");
        list.add("CT");
        list.add("TZ");
        return list;
    }
    // 补库订单类别清单
    public static List<String> bukuList() {
        List<String> list = new ArrayList<String>();
        list.add(OrderTypeEnum.BIN);
        list.add(OrderTypeEnum.PRE);
        list.add(OrderTypeEnum.DR);
        list.add(OrderTypeEnum.CR);
        list.add(OrderTypeEnum.WT);
        return list;
    }

    public static List<String> jpConvertList() {
        List<String> list = new ArrayList<String>();
        list.add("现场");
//        list.add("在库出荷");
//        list.add("生产");
        return list;
    }

    public static List<String> jpVnSgList() {
        List<String> list = new ArrayList<String>();
        list.add("越南");
        list.add("新加坡");
        return list;
    }


}
