package com.sales.ops.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 调库计划查询条件
 * @date 2023/10/11 11:53
 * @auther c14717
 */
public class OpsStockTransferPlanQO implements Serializable {

    private static final long serialVersionUID = -71520790364793933L;
    private String planNo;//计划号

    private String poNo;// 采购单号

    private String modelno; //型号

    private Integer status; // 状态 0 1 2

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
