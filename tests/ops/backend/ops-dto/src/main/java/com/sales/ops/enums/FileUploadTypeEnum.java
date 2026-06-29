package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/10/11 11:43
 * @Descripton TODO
 */
public enum FileUploadTypeEnum {

    MINPRICE("minPrice","order","最低售价"),
    SAMPLEORDERBAL("sampleOrderBal","sampleBalApply","样品结转申请");

    private String fileType;
    private String businessType;
    private String fileTypeName;

    FileUploadTypeEnum(String fileType, String businessType, String fileTypeName) {
        this.fileType = fileType;
        this.businessType = businessType;
        this.fileTypeName = fileTypeName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

//    public static String getBusinessTypeByFileType(String fileType) {
//        if (StringUtils.isBlank(fileType)) {
//            return "";
//        }
//        for (FileUploadTypeEnum item : FileUploadTypeEnum.values()) {
//            if (fileType.equals(item.getFileType())) {
//                return item.getBusinessType();
//            }
//        }
//        return "";
//    }
}
