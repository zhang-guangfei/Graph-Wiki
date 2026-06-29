package com.sales.ops.dto.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 到货确认状态
 * 看下发是否信用拦截，是否纳期客户，是否货齐，订单类型
 *
 * @author B28029
 * @version 1.0
 * @date 2022/8/13 13:09
 */
public class OpsRoConfirmStatusDto {
    private String roId;
    private String doId;
    private String pcoIds;
    private Boolean credit;//信用拦截 1拦截 0不拦截
    private Boolean dlvCusotmer;//纳期客户
    private Boolean dlvDate;//交货期是否到
    private String dlvEntire;//货齐类型
    private boolean entire;//货齐状态 1齐全 0不齐
    private Integer isWms;//是否已经下发物流 0待处理 1已下发成功 2失败
    private String poNo;//PO号 9930A93-146-0
    private String invoiceNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date wlDate;//物流时间

    private String creditDes;//信用拦截 1拦截 0不拦截
    private String dlvCusotmerDes;//纳期客户
    private String dlvDateDes;//交货期是否到
    private String dlvEntireDes;//货齐类型
    private String entireDes;//货齐状态 1齐全 0不齐
    private String isWmsDes;//是否已经下发物流 0待处理 1已下发成功 2失败

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getPcoIds() {
        return pcoIds;
    }

    public void setPcoIds(String pcoIds) {
        this.pcoIds = pcoIds;
    }

    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    public Boolean getDlvCusotmer() {
        return dlvCusotmer;
    }

    public void setDlvCusotmer(Boolean dlvCusotmer) {
        this.dlvCusotmer = dlvCusotmer;
    }

    public Boolean getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Boolean dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }

    public boolean isEntire() {
        return entire;
    }

    public void setEntire(boolean entire) {
        this.entire = entire;
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public String getDlvCusotmerDes() {
        if (dlvCusotmer) {
            return "是";
        }
        return "否";
    }

    public String getDlvDateDes() {
        if (dlvDate) {
            return "是";
        }
        return "否";
    }

    public String getDlvEntireDes() {
        //出货方式
        //0 整单单仓货齐发货
        //1 整单多仓货齐发货
        //2 单项单仓货齐发货
        //3 单项分批随到发货
        switch (dlvEntire) {
            case "0":
                return "(10位)单项单仓货齐发货";
            case "1":
                return "(7位)整单单仓货齐发货";
            case "2":
                return "单项分批随到发货";
            case "3":
                return "(7位)整单多仓货齐发货";
            default:
                return "空";
        }
    }


    public String getEntireDes() {
        if (dlvDate) {
            return "是";
        }
        return "否";
    }


    public String getIsWmsDes() {
        switch (isWms) {
            case 0:
                return "待下发";
            case 1:
                return "已成功";
            case 2:
                return "失败";
            default:
                return "其他";
        }
    }
    public String getCreditDes() {
        if (credit) {
            return "是";
        }
        return "否";
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }
}
