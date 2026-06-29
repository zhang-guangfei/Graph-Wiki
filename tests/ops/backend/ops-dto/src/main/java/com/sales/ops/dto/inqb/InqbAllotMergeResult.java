package com.sales.ops.dto.inqb;

import java.util.List;

/**
 * @author B91717
 * INQB-订单分配模块的分配结果主子表组合
 */
public class InqbAllotMergeResult extends OpsInqbOrderAllotRequest{

    private String prodFlag;

    private Boolean isCG; // 子表中是否有出库类别采购，用于订单状态的判断

    private int status; // 主表的问询状态，是否可以问询

    private List<OpsInqbOrderAllotResult> detailList; // 订单分配的子项清单

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(String prodFlag) {
        this.prodFlag = prodFlag;
    }

    public Boolean getCG() {
        return isCG;
    }

    public void setCG(Boolean CG) {
        isCG = CG;
    }

    public List<OpsInqbOrderAllotResult> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OpsInqbOrderAllotResult> detailList) {
        this.detailList = detailList;
    }
}
