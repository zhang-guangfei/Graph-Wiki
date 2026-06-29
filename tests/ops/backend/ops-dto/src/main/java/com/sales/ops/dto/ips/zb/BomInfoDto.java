package com.sales.ops.dto.ips.zb;




public class BomInfoDto {
    /**
     *             "bomInfo":[
     *               {
     *                 "splitModel":"AB", // 拆分型号
     *                 "splitModelMaterialNo":"P00000029031", // 拆分型号物料号
     *                 "splitModelQty":3, // 拆分型号数量
     *               },
     */
    private String splitModel; // 拆分型号
    private String splitModelMaterialNo; // 拆分型号物料号
    private int splitModelQty; // 拆分型号数量

    public String getSplitModel() {
        return splitModel;
    }

    public void setSplitModel(String splitModel) {
        this.splitModel = splitModel;
    }

    public String getSplitModelMaterialNo() {
        return splitModelMaterialNo;
    }

    public void setSplitModelMaterialNo(String splitModelMaterialNo) {
        this.splitModelMaterialNo = splitModelMaterialNo;
    }

    public int getSplitModelQty() {
        return splitModelQty;
    }

    public void setSplitModelQty(int splitModelQty) {
        this.splitModelQty = splitModelQty;
    }
}
