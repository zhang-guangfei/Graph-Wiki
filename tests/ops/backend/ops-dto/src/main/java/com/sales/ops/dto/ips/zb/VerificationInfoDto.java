package com.sales.ops.dto.ips.zb;


public class VerificationInfoDto {
    /**
     *       "verificationInfo" : [ // 核销信息
     *                            {
     * 	            "verificationOrderNo" : "",  // 消耗准备订单的采购单单号
     * 	            "verificationOrderItem" : "", // 消耗准备订单的采购单项号
     * 	            "usedQty" :1, // 使用数量
     * 	            "usedDate" :"2025-08-12", // 准备订单的使用时间
     *              },{
     * 	            "verificationOrderNo" : "",  // 消耗准备订单的采购单单号
     * 	            "verificationOrderItem" : "", // 消耗准备订单的采购单项号
     * 	            "usedQty" :1, // 使用数量
     * 	            "usedDate" :"", // 准备订单的使用时间
     *              }
     *       ],
     */
    private String verificationOrderNo;
    private String verificationOrderItem;
    private Integer usedQty;
    private String usedDate;
    private String verificationOrderEndUser;

    public String getVerificationOrderNo() {
        return verificationOrderNo;
    }

    public void setVerificationOrderNo(String verificationOrderNo) {
        this.verificationOrderNo = verificationOrderNo;
    }

    public String getVerificationOrderItem() {
        return verificationOrderItem;
    }

    public void setVerificationOrderItem(String verificationOrderItem) {
        this.verificationOrderItem = verificationOrderItem;
    }

    public String getVerificationOrderEndUser() {
        return verificationOrderEndUser;
    }

    public void setVerificationOrderEndUser(String verificationOrderEndUser) {
        this.verificationOrderEndUser = verificationOrderEndUser;
    }

    public Integer getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(Integer usedQty) {
        this.usedQty = usedQty;
    }

    public String getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(String usedDate) {
        this.usedDate = usedDate;
    }
}
