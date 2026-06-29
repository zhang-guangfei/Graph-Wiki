package com.sales.ops.dto.ips.zb;


import java.util.List;


public class OrderResidueInfoDto {

    /**
         { // 订单残信息2
         "backupMethod":"0", // 备库方式：0部品；1制品
         "planRemainingQty":5, // 计划残数量
         "factRemainingQty":5, // 实际残数量
         "bomInfo":[
             {
             "splitModel":"AB", // 拆分型号
             "splitModelMaterialNo":"P00000029031", // 拆分型号物料号
             "splitModelQty":3, // 拆分型号数量
             },
             {
             "splitModel":"AC", // 拆分型号
             "splitModelMaterialNo":"P00000029066", // 拆分型号物料号
             "splitModelQty":4, // 拆分型号数量
             }]
         },
     */

    private String backupMethod;
    private Integer planRemainingQty;
    private Integer factRemainingQty;
    private List<BomInfoDto> bomInfo;

    public String getBackupMethod() {
        return backupMethod;
    }

    public void setBackupMethod(String backupMethod) {
        this.backupMethod = backupMethod;
    }

    public Integer getPlanRemainingQty() {
        return planRemainingQty;
    }

    public void setPlanRemainingQty(Integer planRemainingQty) {
        this.planRemainingQty = planRemainingQty;
    }

    public Integer getFactRemainingQty() {
        return factRemainingQty;
    }

    public void setFactRemainingQty(Integer factRemainingQty) {
        this.factRemainingQty = factRemainingQty;
    }

    public List<BomInfoDto> getBomInfo() {
        return bomInfo;
    }

    public void setBomInfo(List<BomInfoDto> bomInfo) {
        this.bomInfo = bomInfo;
    }
}
