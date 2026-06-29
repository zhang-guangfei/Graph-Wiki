package com.sales.ops.dto.inqb;

import java.util.Date;

/**
 * @author B91717
 * INQB-订单回更占用时，提供的关联信息实体
 */
public class OpsInqbUsageInfo {

    /**
     * INQB申请单号
     */
    private String inqbApplyNo;

    /**
     * INQB申请子项号
     */
    private Integer itemNo;

    /**
     * 使用数量
     */
    private Integer useQty;

    /**
     * 使用的完整业务订单号
     */
    private String rorderFno;

    private Date userTime;

    public OpsInqbUsageInfo(){}

    public OpsInqbUsageInfo(String inqbApplyNo, Integer itemNo ,Integer useQty, String rorderFno, Date userTime){
        this.inqbApplyNo = inqbApplyNo;
        this.itemNo = itemNo;
        this.useQty = useQty;
        this.rorderFno = rorderFno;
        this.userTime = userTime;
    }

    public String getInqbApplyNo() {
        return inqbApplyNo;
    }

    public void setInqbApplyNo(String inqbApplyNo) {
        this.inqbApplyNo = inqbApplyNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno;
    }

    public Date getUserTime() {
        return userTime;
    }

    public void setUserTime(Date userTime) {
        this.userTime = userTime;
    }
}
