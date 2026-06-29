package com.sales.ops.dto.po;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ bugid:17646 20250526 c14717
 * @description：采购拦截自动出库存订单
 * @date ：Created in 2025/5/26 11:15
 */
public class InterceptPoAutoReleaseInvDto implements Serializable {

    private static final long serialVersionUID = 6486121015642572586L;
    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String doId;

    private String pcoId;

    private String modelNo;

    private Integer qty;

    private Integer handleFlag;// 0 整出 订单还原 1 数量拆 2.型号拆分 转定

    // 拆分类型 WHOLE("W","整型号采购"),
    //    ASS("A","拆分型号");
    private String wmtag;

    public InterceptPoAutoReleaseInvDto(){};

    public InterceptPoAutoReleaseInvDto(String orderno,Integer itemno,Integer splititemno,Integer handleFlag,String wmtag,String doId,String pcoId){
        this.orderno = orderno;
        this.itemno = itemno;
        this.splititemno = splititemno;
        this.handleFlag = handleFlag;
        this.wmtag = wmtag;
        this.doId = doId;
        this.pcoId = pcoId;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public Integer getHandleFlag() {
        return handleFlag;
    }

    public void setHandleFlag(Integer handleFlag) {
        this.handleFlag = handleFlag;
    }

    public String getWmtag() {
        return wmtag;
    }

    public void setWmtag(String wmtag) {
        this.wmtag = wmtag;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
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
}
