//package com.smc.smccloud.core.model.enums;
//
//import org.apache.commons.lang3.StringUtils;
//
///**
// * @Author lyc
// * @Date 2023/10/27 13:22
// * @Descripton TODO
// */
//// 0未受理 1已受理 2已结转 3退回申请
//public enum  SampleBalApplyStatusEnum {
//    dqr("0","未受理"),
//    dzc("1","已受理"),
//    djz("2","已结转"),
//    returnApply("3","退回申请");
//
//    private String code;
//    private String codeName;
//
//    SampleBalApplyStatusEnum(String code, String codeName) {
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
//    public static String getCodeNameByCode(String code) {
//        if (StringUtils.isBlank(code)) {
//            return "";
//        }
//        for (SampleBalApplyStatusEnum item : SampleBalApplyStatusEnum.values()) {
//            if (item.getCode().equals(code)) {
//                return item.getCodeName();
//            }
//        }
//        return code;
//    }
//
//}
