//package com.smc.smccloud.model.enums;
//
//import com.smc.smccloud.core.utils.DateUtil;
//import com.smc.smccloud.model.order.PoReplyDateDto;
//
//import java.util.Date;
//
///**
// * @Author lyc
// * @Date 2023/3/8 10:03
// * @Descripton TODO  特殊日期翻译枚举类
// */
//public enum  PoReplyDateStrEnum {
//    EIGHT888888("88/88/88","日本制造部正在向日本研发中心要图纸"),
//    TWO222222("22/22/22","型号确认中订单正转交正确部门"),
//    THREE333333("33/33/33","正在日本研发中心组装"),
//    FOUR444444("44/44/44","紧急停止供货"),
//    FIVE555555("55/55/55","部品定向其他工厂,无确切交货期"),
//    SIX666666("66/66/66","部品定向外协,无确切交货期"),
//    SEVEN777777("77/77/77","加工完毕日期不确定,正在确认中"),
//    NINE999999("99/99/99","订单异常,请登录AS400查看异常原因并及时进行处理"),
//    NINE990000("99/00/00","要提供样式书");
//
//    private String code;
//    private String codeName;
//
//    PoReplyDateStrEnum(String code, String codeName) {
//        this.code = code;
//        this.codeName = codeName;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getCodeName() {
//        return codeName;
//    }
//
//    public void setCodeName(String codeName) {
//        this.codeName = codeName;
//    }
//
//    public static PoReplyDateDto getDescByCode(Date date) {
//        PoReplyDateDto poReplyDateDto = new PoReplyDateDto();
//        if (date == null) {
//            return poReplyDateDto;
//        }
//        String dateStr = DateUtil.dateToString(date);
//        switch (dateStr) {
//            case "8888-08-08":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.EIGHT888888.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.EIGHT888888.getCode());
//                break;
//            case "2222-02-02":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.TWO222222.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.TWO222222.getCode());
//                break;
//            case "3333-03-03":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.THREE333333.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.THREE333333.getCode());
//                break;
//            case "4444-04-04":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.FOUR444444.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.FOUR444444.getCode());
//                break;
//            case "5555-05-05":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.FIVE555555.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.FIVE555555.getCode());
//                break;
//            case "6666-06-06":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.SIX666666.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.SIX666666.getCode());
//                break;
//            case "7777-07-07":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.SEVEN777777.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.SEVEN777777.getCode());
//                break;
//            case "9999-09-09":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.NINE999999.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.NINE999999.getCode());
//                break;
//            case "9900-09-09":
//                poReplyDateDto.setCodeName(PoReplyDateStrEnum.NINE990000.getCodeName());
//                poReplyDateDto.setCode(PoReplyDateStrEnum.NINE990000.getCode());
//                break;
//            default:
//                // 9924-02-29
//                if (dateStr.startsWith("99") && !dateStr.equals("9999-12-31")) {
//                    String year = dateStr.substring(0,4);
//                    String month = dateStr.substring(5,7);
//                    String day = dateStr.substring(8,10);
//                    year = year.replace("99","20");
//                    poReplyDateDto.setCode(year+"-"+month+"-"+"99");
//                } else {
//                    poReplyDateDto.setCode(dateStr);
//                }
//                break;
//        }
//        return poReplyDateDto;
//    }
//}
