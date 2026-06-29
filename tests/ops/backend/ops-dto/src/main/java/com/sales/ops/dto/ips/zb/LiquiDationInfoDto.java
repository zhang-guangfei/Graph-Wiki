package com.sales.ops.dto.ips.zb;




public class LiquiDationInfoDto {
    /**
     *       "liquidationInfo" : [ // 决算信息
     *                            {
     * 	            "liquidationBatchNo" : "", // 决算批次号
     * 	            "liquidationModel" : "", // 决算型号
     * 	            "liquidationQty" : 1, // 决算数量
     *              },{
     * 	            "liquidationBatchNo" : "", // 决算批次号
     * 	            "liquidationModel" : "", // 决算型号
     * 	            "liquidationQty" : 1, // 决算数量
     *              }
     *       ],
     */
    private String liquidationBatchNo;
    private String liquidationModel;
    private Integer liquidationQty;
    private String handleWay;

    public String getHandleWay() {
        return handleWay;
    }

    public void setHandleWay(String handleWay) {
        this.handleWay = handleWay;
    }

    public String getLiquidationBatchNo() {
        return liquidationBatchNo;
    }

    public void setLiquidationBatchNo(String liquidationBatchNo) {
        this.liquidationBatchNo = liquidationBatchNo;
    }

    public String getLiquidationModel() {
        return liquidationModel;
    }

    public void setLiquidationModel(String liquidationModel) {
        this.liquidationModel = liquidationModel;
    }

    public Integer getLiquidationQty() {
        return liquidationQty;
    }

    public void setLiquidationQty(Integer liquidationQty) {
        this.liquidationQty = liquidationQty;
    }
}
