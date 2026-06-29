package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2024/7/18 10:50
 * @Descripton TODO
 */
public enum SampleOrderApplyStatusEnum {
    handing(3,"处理中"),
    notpass(4,"不通过"),
    yscdd(5,"已生成订单"),
    cancel(9,"已取消");

    private int code;
    private String codeName;

    SampleOrderApplyStatusEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getCodeNameByCode(int code){
        for(SampleOrderApplyStatusEnum sampleOrderApplyStatusEnum : SampleOrderApplyStatusEnum.values()){
            if(sampleOrderApplyStatusEnum.getCode() == code){
                return sampleOrderApplyStatusEnum.getCodeName();
            }
        }
        return null;
    }
}
