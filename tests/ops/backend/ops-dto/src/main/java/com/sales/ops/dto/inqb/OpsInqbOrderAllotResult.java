package com.sales.ops.dto.inqb;

/**
 * @author B91717
 * INQB-订单分配模块的分配结果信息
 */
public class OpsInqbOrderAllotResult {

    private Integer seqno; // 分配结果序号

    private String prodFlag; // 拆分标识(0:不拆分 1:数量拆分 2:型号拆分)

    private String stockType; // 出库区分：N:在库；P:生产在途； T:采购在途；CG：采购

    private Long bomid; // 分配结果型号拆分bomId

    private String modelNo; // 分配结果型号

    private Integer quantity; // 分配结果数量

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public String getProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(String prodFlag) {
        this.prodFlag = prodFlag;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public Long getBomid() {
        return bomid;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
