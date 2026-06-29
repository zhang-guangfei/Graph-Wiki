package com.sales.ops.dto.ips;

import java.math.BigDecimal;

/**
 * @description:
 * @author: B91717
 * @time: 2024/11/4 18:32
 */
public class IpsProductInfoDto {
    // {“普通品标识：是否”，，"3C产品标识"，"化学品标识","法检品标识"，"冷干机、温控器、干燥机"},
    private String rohscode; // greenMark，绿标 环保标识：H;G;P（非空且不在标识清单内时，退单）

    private String managementCode;  //  阀板标识，二次电池&&同捆：B；二次电池:A；【同捆：P不再使用】；非管理:" "  （新增）

    private String hscode; // hscode

    private String eac;

    private String  drawingNo;

    private String  drawingfilesPath; // 图纸地址

    private String  is3C; //  3C品标识：填写内容为空或者3C

    // 2025-10-20 海外发单改造，通过PSI发送，增加字段
    private BigDecimal importfobprice;

    public BigDecimal getImportfobprice() {
        return importfobprice;
    }

    public void setImportfobprice(BigDecimal importfobprice) {
        this.importfobprice = importfobprice;
    }

    public String getRohscode() {
        return rohscode;
    }

    public void setRohscode(String rohscode) {
        this.rohscode = rohscode;
    }

    public String getManagementCode() {
        return managementCode;
    }

    public void setManagementCode(String managementCode) {
        this.managementCode = managementCode;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public String getEac() {
        return eac;
    }

    public void setEac(String eac) {
        this.eac = eac;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getDrawingfilesPath() {
        return drawingfilesPath;
    }

    public void setDrawingfilesPath(String drawingfilesPath) {
        this.drawingfilesPath = drawingfilesPath;
    }

    public String getIs3C() {
        return is3C;
    }

    public void setIs3C(String is3C) {
        this.is3C = is3C;
    }
//    private String ordinaryProductFlag;
//    private String cCProductFlag;
//    private String chemicalProductFlag;
//    private String lawInspectionProductFlag;
//    private String specialEquipment;
}
