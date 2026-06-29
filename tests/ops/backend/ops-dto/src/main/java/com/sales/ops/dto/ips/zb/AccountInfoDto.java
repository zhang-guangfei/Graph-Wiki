package com.sales.ops.dto.ips.zb;



public class AccountInfoDto {

    /**
     * finalAccountModel
     * finalAccountQty
     */
    // 决算型号: 制品备库就是原型号，部品备库是BOM拆分型号
    private String finalAccountModel;
    // 决算数量：若涉及BOM拆分的情况 = 部品备库数量*BOM拆分型号元数
    private Integer finalAccountQty;

    public String getFinalAccountModel() {
        return finalAccountModel;
    }

    public void setFinalAccountModel(String finalAccountModel) {
        this.finalAccountModel = finalAccountModel;
    }

    public Integer getFinalAccountQty() {
        return finalAccountQty;
    }

    public void setFinalAccountQty(Integer finalAccountQty) {
        this.finalAccountQty = finalAccountQty;
    }
}
