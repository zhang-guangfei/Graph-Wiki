package com.sales.ops.dto.inventory;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/22 16:20
 */
public class InventoryReportExcel implements Serializable {

    @ExcelProperty("仓库代码")
    private String warehouseCode;

    @ExcelProperty("仓库名称")
    private String warehouseName;

    @ExcelProperty("仓库类别")
    private String warehouseType;

    @ExcelProperty("型号")
    private String modelno;

    @ExcelIgnore
    private String inventoryTypeCode;

    @ExcelIgnore
    private Long inventoryPropertyId;
    @ExcelProperty("客户代码")
    private String customerNo;
    @ExcelProperty("客户群代码")
    private String groupCustomerNo;

    @ExcelProperty("PPL")
    private String ppl;

    @ExcelProperty("PJ")
    private String projectCode;

    @ExcelProperty("情报号")
    private String salesInfoNo;

    @ExcelProperty("营业所")
    private String deptcode;
    @ExcelProperty("风险在库(0否/1是)")
    private Integer rflag;
    @ExcelProperty("风险在库数量")
    private Integer rqtyAvailable;

    @ExcelProperty("正在在库有效")
    private Integer qtyNAvailable;
    @ExcelProperty("正在在库实物")
    private Integer qtyn;

    @ExcelProperty("正在在库预占")
    private Integer pqtyn;

    @ExcelProperty("到货未入库实物")
    private Integer qtyw;

    @ExcelProperty("到货未入库预占")
    private Integer pqtyw;


    @ExcelProperty("调拨在途实物")
    private Integer qtyd;
    @ExcelProperty("调拨在途预占")
    private Integer pqtyd;
    @ExcelProperty("采购在途实物")
    private Integer qtyc;
    @ExcelProperty("采购在途预占")
    private Integer pqtyc;
    @ExcelProperty("生产在途实物")
    private Integer qtyp;
    @ExcelProperty("生产在途预占")
    private Integer pqtyp;
    @ExcelProperty("退货在途实物")
    private Integer qtyr;
    @ExcelProperty("退货在途预占")
    private Integer pqtyr;
    @ExcelIgnore
    private Integer qtyAvailable;

    private static final long serialVersionUID = 1L;

    public Integer getQtyNAvailable() {
        return this.qtyn - this.pqtyn;
    }

    public void setQtyNAvailable(Integer qtyNAvailable) {
        this.qtyNAvailable = qtyNAvailable;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    public Integer getQtyn() {
        return qtyn;
    }

    public void setQtyn(Integer qtyn) {
        this.qtyn = qtyn;
    }

    public Integer getPqtyn() {
        return pqtyn;
    }

    public void setPqtyn(Integer pqtyn) {
        this.pqtyn = pqtyn;
    }

    public Integer getQtyw() {
        return qtyw;
    }

    public void setQtyw(Integer qtyw) {
        this.qtyw = qtyw;
    }

    public Integer getPqtyw() {
        return pqtyw;
    }

    public void setPqtyw(Integer pqtyw) {
        this.pqtyw = pqtyw;
    }

    public Integer getQtyd() {
        return qtyd;
    }

    public void setQtyd(Integer qtyd) {
        this.qtyd = qtyd;
    }

    public Integer getPqtyd() {
        return pqtyd;
    }

    public void setPqtyd(Integer pqtyd) {
        this.pqtyd = pqtyd;
    }

    public Integer getQtyc() {
        return qtyc;
    }

    public void setQtyc(Integer qtyc) {
        this.qtyc = qtyc;
    }

    public Integer getPqtyc() {
        return pqtyc;
    }

    public void setPqtyc(Integer pqtyc) {
        this.pqtyc = pqtyc;
    }

    public Integer getQtyp() {
        return qtyp;
    }

    public void setQtyp(Integer qtyp) {
        this.qtyp = qtyp;
    }

    public Integer getPqtyp() {
        return pqtyp;
    }

    public void setPqtyp(Integer pqtyp) {
        this.pqtyp = pqtyp;
    }

    public Integer getQtyr() {
        return qtyr;
    }

    public void setQtyr(Integer qtyr) {
        this.qtyr = qtyr;
    }

    public Integer getPqtyr() {
        return pqtyr;
    }

    public void setPqtyr(Integer pqtyr) {
        this.pqtyr = pqtyr;
    }

    public Integer getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(Integer qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public Integer getRqtyAvailable() {
        return rqtyAvailable;
    }

    public void setRqtyAvailable(Integer rqtyAvailable) {
        this.rqtyAvailable = rqtyAvailable;
    }

    public Integer getRflag() {
        return rflag;
    }

    public void setRflag(Integer rflag) {
        this.rflag = rflag;
    }
}
