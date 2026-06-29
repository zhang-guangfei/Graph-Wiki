package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-10-13 09:54
 * Description:
 */
public enum StockAssemblyDetailStatusEnum {
    //    <!--add by WuWeiDong 20240308  bug 13535 组换明细状态枚举与字典不一致 -->
    editing("0", "编辑中"),
    approving("1", "审核中"),
    processing("2", "待处理"),
  //  processing("3", "待处理"),
    uncheck("4", "退回"),
    confirm("5", "组装中"),
    finished("6", "已完成"),
    noHandle("7", "不能处理"),
    cost("8", "已计入成本"),
    cancel("9", "取消");

    private String code;
    private String codeName;

    StockAssemblyDetailStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getNameByCode(String code) {
        for (StockAssemblyDetailStatusEnum item : StockAssemblyDetailStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
