package com.sales.ops.dto.eta;


import com.sales.ops.db.entity.OpsDeliveryEstimateResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/12/6 15:33
 */
public class ETAInfoListPageDto implements Serializable {
    private static final long serialVersionUID = -7827116176464785823L;

    // 海运优先标记
    private Boolean priorityShipment;
    private Integer index;

    private String modelNo;

    private String deptNo;

    private String customerNo;

    private String endUser;

    private Integer qty;

    private Integer qtyLevel;

    // 采购运输方式最快 计算参考货期
    private Integer deliveryDays;

    // 采购运输方式最慢 计算参考货期
    private Integer slowDeliveryDays;

    private Integer instock;

    //出库区分 采购【JP】 在库【KBJ】
    private String isStockInfo;

    private List<ETAInfoListPageDetailDto> detail;


    private String gatherWarehouseCode;

    //拆分类型 型号拆分 数量拆分
    private String doSource;

    //文字描述
    private String msg;

    //采购  数据来源 取detail 采购生产周期最大的 显示 数据来源
    private String calDesc;

    public ETAInfoListPageDto(){}

    public ETAInfoListPageDto(OpsDeliveryEstimateResult result){
        this.modelNo = result.getModelNo();
        this.deptNo = result.getDeptNo();
        this.customerNo = result.getCustomerNo();
        this.endUser = result.getEndUser();
        this.qty = result.getQty();
        this.gatherWarehouseCode = result.getGatherWarehouseCode();
        this.instock = result.getInstock();
    }

    public Boolean getPriorityShipment() {
        return priorityShipment;
    }

    public void setPriorityShipment(Boolean priorityShipment) {
        this.priorityShipment = priorityShipment;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public Integer getInstock() {
        return instock;
    }

    public void setInstock(Integer instock) {
        this.instock = instock;
    }

    public List<ETAInfoListPageDetailDto> getDetail() {
        return detail;
    }

    public void setDetail(List<ETAInfoListPageDetailDto> detail) {
        this.detail = detail;
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource;
    }

    public String getIsStockInfo() {
        return isStockInfo;
    }

    public void setIsStockInfo(String isStockInfo) {
        this.isStockInfo = isStockInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getQtyLevel() {
        return qtyLevel;
    }

    public void setQtyLevel(Integer qtyLevel) {
        this.qtyLevel = qtyLevel;
    }

    public Integer getSlowDeliveryDays() {
        return slowDeliveryDays;
    }

    public void setSlowDeliveryDays(Integer slowDeliveryDays) {
        this.slowDeliveryDays = slowDeliveryDays;
    }

    public String getCalDesc() {
        return calDesc;
    }

    public void setCalDesc(String calDesc) {
        this.calDesc = calDesc;
    }
}
