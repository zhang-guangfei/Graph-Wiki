package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderState implements Serializable {
    private Long id;

    private String orderNo;

    private Integer itemNo;

    private Integer splitNo;

    private String rorderNo;

    private Short orderType;

    private String modelNo;

    private Integer quantity;

    private Short stateCode;

    private String stateDes;

    private Date stateDate;

    private Integer stateType;

    private Date createTime;

    private Date updateTime;

    private Date custDlvDate;

    private Date custShipDate;

    private Date custSignDate;

    private Date deptDlvDate;

    private Date poDlvDate;

    private Date poReplyDate;

    private Date poShipDate;

    private String customerNo;

    private String userNo;

    private String deptNo;

    private String supplierCode;

    private String supplierNo;

    private Date actArrivalDate;

    private Date esArrivalDate;

    private Date esArriveDateReq;

    private String provider;

    private Date shipDate;

    private Date esShipDate;

    private Date expressDlvDate;

    private Integer delayDay;

    private Integer poDelayDay;

    private String corderNo;

    private String cmodelNo;

    private String transType;

    private String supplierOrderno;

    private String shikomiNo;

    private Date supplierRcvtime;

    private String orderPsnNo;

    private String orderAppoverNo;

    private String poInvoiceNo;

    private String poExpType;

    private String poHolon;

    private String poNo;

    private Date orderDate;

    private Date receiveDate;

    private Date poDate;

    private String purchaseType;

    private String optUserNo;

    private String optUserName;

    private Date poRcvTime;

    private String inqa;

    private String inqb;

    private Integer rcvStatus;

    private Date poReplyDatea;

    private Date poReplyDateb;

    private Date poReplyDatec;

    private String poTransType;

    private String warehouseCode;

    private Date portArrivedate;

    private Date customsDate;

    private Date beginProduceDate;

    private BigDecimal poPrice;

    private String orderPsnDept;

    private String endUser;

    private Date poImptime;

    private Boolean intercept;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Short getStateCode() {
        return stateCode;
    }

    public void setStateCode(Short stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateDes() {
        return stateDes;
    }

    public void setStateDes(String stateDes) {
        this.stateDes = stateDes == null ? null : stateDes.trim();
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCustDlvDate() {
        return custDlvDate;
    }

    public void setCustDlvDate(Date custDlvDate) {
        this.custDlvDate = custDlvDate;
    }

    public Date getCustShipDate() {
        return custShipDate;
    }

    public void setCustShipDate(Date custShipDate) {
        this.custShipDate = custShipDate;
    }

    public Date getCustSignDate() {
        return custSignDate;
    }

    public void setCustSignDate(Date custSignDate) {
        this.custSignDate = custSignDate;
    }

    public Date getDeptDlvDate() {
        return deptDlvDate;
    }

    public void setDeptDlvDate(Date deptDlvDate) {
        this.deptDlvDate = deptDlvDate;
    }

    public Date getPoDlvDate() {
        return poDlvDate;
    }

    public void setPoDlvDate(Date poDlvDate) {
        this.poDlvDate = poDlvDate;
    }

    public Date getPoReplyDate() {
        return poReplyDate;
    }

    public void setPoReplyDate(Date poReplyDate) {
        this.poReplyDate = poReplyDate;
    }

    public Date getPoShipDate() {
        return poShipDate;
    }

    public void setPoShipDate(Date poShipDate) {
        this.poShipDate = poShipDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo == null ? null : supplierNo.trim();
    }

    public Date getActArrivalDate() {
        return actArrivalDate;
    }

    public void setActArrivalDate(Date actArrivalDate) {
        this.actArrivalDate = actArrivalDate;
    }

    public Date getEsArrivalDate() {
        return esArrivalDate;
    }

    public void setEsArrivalDate(Date esArrivalDate) {
        this.esArrivalDate = esArrivalDate;
    }

    public Date getEsArriveDateReq() {
        return esArriveDateReq;
    }

    public void setEsArriveDateReq(Date esArriveDateReq) {
        this.esArriveDateReq = esArriveDateReq;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider == null ? null : provider.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getEsShipDate() {
        return esShipDate;
    }

    public void setEsShipDate(Date esShipDate) {
        this.esShipDate = esShipDate;
    }

    public Date getExpressDlvDate() {
        return expressDlvDate;
    }

    public void setExpressDlvDate(Date expressDlvDate) {
        this.expressDlvDate = expressDlvDate;
    }

    public Integer getDelayDay() {
        return delayDay;
    }

    public void setDelayDay(Integer delayDay) {
        this.delayDay = delayDay;
    }

    public Integer getPoDelayDay() {
        return poDelayDay;
    }

    public void setPoDelayDay(Integer poDelayDay) {
        this.poDelayDay = poDelayDay;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public String getCmodelNo() {
        return cmodelNo;
    }

    public void setCmodelNo(String cmodelNo) {
        this.cmodelNo = cmodelNo == null ? null : cmodelNo.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getSupplierOrderno() {
        return supplierOrderno;
    }

    public void setSupplierOrderno(String supplierOrderno) {
        this.supplierOrderno = supplierOrderno == null ? null : supplierOrderno.trim();
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo == null ? null : shikomiNo.trim();
    }

    public Date getSupplierRcvtime() {
        return supplierRcvtime;
    }

    public void setSupplierRcvtime(Date supplierRcvtime) {
        this.supplierRcvtime = supplierRcvtime;
    }

    public String getOrderPsnNo() {
        return orderPsnNo;
    }

    public void setOrderPsnNo(String orderPsnNo) {
        this.orderPsnNo = orderPsnNo == null ? null : orderPsnNo.trim();
    }

    public String getOrderAppoverNo() {
        return orderAppoverNo;
    }

    public void setOrderAppoverNo(String orderAppoverNo) {
        this.orderAppoverNo = orderAppoverNo == null ? null : orderAppoverNo.trim();
    }

    public String getPoInvoiceNo() {
        return poInvoiceNo;
    }

    public void setPoInvoiceNo(String poInvoiceNo) {
        this.poInvoiceNo = poInvoiceNo == null ? null : poInvoiceNo.trim();
    }

    public String getPoExpType() {
        return poExpType;
    }

    public void setPoExpType(String poExpType) {
        this.poExpType = poExpType == null ? null : poExpType.trim();
    }

    public String getPoHolon() {
        return poHolon;
    }

    public void setPoHolon(String poHolon) {
        this.poHolon = poHolon == null ? null : poHolon.trim();
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo == null ? null : poNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType == null ? null : purchaseType.trim();
    }

    public String getOptUserNo() {
        return optUserNo;
    }

    public void setOptUserNo(String optUserNo) {
        this.optUserNo = optUserNo == null ? null : optUserNo.trim();
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName == null ? null : optUserName.trim();
    }

    public Date getPoRcvTime() {
        return poRcvTime;
    }

    public void setPoRcvTime(Date poRcvTime) {
        this.poRcvTime = poRcvTime;
    }

    public String getInqa() {
        return inqa;
    }

    public void setInqa(String inqa) {
        this.inqa = inqa == null ? null : inqa.trim();
    }

    public String getInqb() {
        return inqb;
    }

    public void setInqb(String inqb) {
        this.inqb = inqb == null ? null : inqb.trim();
    }

    public Integer getRcvStatus() {
        return rcvStatus;
    }

    public void setRcvStatus(Integer rcvStatus) {
        this.rcvStatus = rcvStatus;
    }

    public Date getPoReplyDatea() {
        return poReplyDatea;
    }

    public void setPoReplyDatea(Date poReplyDatea) {
        this.poReplyDatea = poReplyDatea;
    }

    public Date getPoReplyDateb() {
        return poReplyDateb;
    }

    public void setPoReplyDateb(Date poReplyDateb) {
        this.poReplyDateb = poReplyDateb;
    }

    public Date getPoReplyDatec() {
        return poReplyDatec;
    }

    public void setPoReplyDatec(Date poReplyDatec) {
        this.poReplyDatec = poReplyDatec;
    }

    public String getPoTransType() {
        return poTransType;
    }

    public void setPoTransType(String poTransType) {
        this.poTransType = poTransType == null ? null : poTransType.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Date getPortArrivedate() {
        return portArrivedate;
    }

    public void setPortArrivedate(Date portArrivedate) {
        this.portArrivedate = portArrivedate;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public Date getBeginProduceDate() {
        return beginProduceDate;
    }

    public void setBeginProduceDate(Date beginProduceDate) {
        this.beginProduceDate = beginProduceDate;
    }

    public BigDecimal getPoPrice() {
        return poPrice;
    }

    public void setPoPrice(BigDecimal poPrice) {
        this.poPrice = poPrice;
    }

    public String getOrderPsnDept() {
        return orderPsnDept;
    }

    public void setOrderPsnDept(String orderPsnDept) {
        this.orderPsnDept = orderPsnDept == null ? null : orderPsnDept.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public Date getPoImptime() {
        return poImptime;
    }

    public void setPoImptime(Date poImptime) {
        this.poImptime = poImptime;
    }

    public Boolean getIntercept() {
        return intercept;
    }

    public void setIntercept(Boolean intercept) {
        this.intercept = intercept;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderState other = (OrderState) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStateCode() == null ? other.getStateCode() == null : this.getStateCode().equals(other.getStateCode()))
            && (this.getStateDes() == null ? other.getStateDes() == null : this.getStateDes().equals(other.getStateDes()))
            && (this.getStateDate() == null ? other.getStateDate() == null : this.getStateDate().equals(other.getStateDate()))
            && (this.getStateType() == null ? other.getStateType() == null : this.getStateType().equals(other.getStateType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCustDlvDate() == null ? other.getCustDlvDate() == null : this.getCustDlvDate().equals(other.getCustDlvDate()))
            && (this.getCustShipDate() == null ? other.getCustShipDate() == null : this.getCustShipDate().equals(other.getCustShipDate()))
            && (this.getCustSignDate() == null ? other.getCustSignDate() == null : this.getCustSignDate().equals(other.getCustSignDate()))
            && (this.getDeptDlvDate() == null ? other.getDeptDlvDate() == null : this.getDeptDlvDate().equals(other.getDeptDlvDate()))
            && (this.getPoDlvDate() == null ? other.getPoDlvDate() == null : this.getPoDlvDate().equals(other.getPoDlvDate()))
            && (this.getPoReplyDate() == null ? other.getPoReplyDate() == null : this.getPoReplyDate().equals(other.getPoReplyDate()))
            && (this.getPoShipDate() == null ? other.getPoShipDate() == null : this.getPoShipDate().equals(other.getPoShipDate()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getSupplierNo() == null ? other.getSupplierNo() == null : this.getSupplierNo().equals(other.getSupplierNo()))
            && (this.getActArrivalDate() == null ? other.getActArrivalDate() == null : this.getActArrivalDate().equals(other.getActArrivalDate()))
            && (this.getEsArrivalDate() == null ? other.getEsArrivalDate() == null : this.getEsArrivalDate().equals(other.getEsArrivalDate()))
            && (this.getEsArriveDateReq() == null ? other.getEsArriveDateReq() == null : this.getEsArriveDateReq().equals(other.getEsArriveDateReq()))
            && (this.getProvider() == null ? other.getProvider() == null : this.getProvider().equals(other.getProvider()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getEsShipDate() == null ? other.getEsShipDate() == null : this.getEsShipDate().equals(other.getEsShipDate()))
            && (this.getExpressDlvDate() == null ? other.getExpressDlvDate() == null : this.getExpressDlvDate().equals(other.getExpressDlvDate()))
            && (this.getDelayDay() == null ? other.getDelayDay() == null : this.getDelayDay().equals(other.getDelayDay()))
            && (this.getPoDelayDay() == null ? other.getPoDelayDay() == null : this.getPoDelayDay().equals(other.getPoDelayDay()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getCmodelNo() == null ? other.getCmodelNo() == null : this.getCmodelNo().equals(other.getCmodelNo()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getSupplierOrderno() == null ? other.getSupplierOrderno() == null : this.getSupplierOrderno().equals(other.getSupplierOrderno()))
            && (this.getShikomiNo() == null ? other.getShikomiNo() == null : this.getShikomiNo().equals(other.getShikomiNo()))
            && (this.getSupplierRcvtime() == null ? other.getSupplierRcvtime() == null : this.getSupplierRcvtime().equals(other.getSupplierRcvtime()))
            && (this.getOrderPsnNo() == null ? other.getOrderPsnNo() == null : this.getOrderPsnNo().equals(other.getOrderPsnNo()))
            && (this.getOrderAppoverNo() == null ? other.getOrderAppoverNo() == null : this.getOrderAppoverNo().equals(other.getOrderAppoverNo()))
            && (this.getPoInvoiceNo() == null ? other.getPoInvoiceNo() == null : this.getPoInvoiceNo().equals(other.getPoInvoiceNo()))
            && (this.getPoExpType() == null ? other.getPoExpType() == null : this.getPoExpType().equals(other.getPoExpType()))
            && (this.getPoHolon() == null ? other.getPoHolon() == null : this.getPoHolon().equals(other.getPoHolon()))
            && (this.getPoNo() == null ? other.getPoNo() == null : this.getPoNo().equals(other.getPoNo()))
            && (this.getOrderDate() == null ? other.getOrderDate() == null : this.getOrderDate().equals(other.getOrderDate()))
            && (this.getReceiveDate() == null ? other.getReceiveDate() == null : this.getReceiveDate().equals(other.getReceiveDate()))
            && (this.getPoDate() == null ? other.getPoDate() == null : this.getPoDate().equals(other.getPoDate()))
            && (this.getPurchaseType() == null ? other.getPurchaseType() == null : this.getPurchaseType().equals(other.getPurchaseType()))
            && (this.getOptUserNo() == null ? other.getOptUserNo() == null : this.getOptUserNo().equals(other.getOptUserNo()))
            && (this.getOptUserName() == null ? other.getOptUserName() == null : this.getOptUserName().equals(other.getOptUserName()))
            && (this.getPoRcvTime() == null ? other.getPoRcvTime() == null : this.getPoRcvTime().equals(other.getPoRcvTime()))
            && (this.getInqa() == null ? other.getInqa() == null : this.getInqa().equals(other.getInqa()))
            && (this.getInqb() == null ? other.getInqb() == null : this.getInqb().equals(other.getInqb()))
            && (this.getRcvStatus() == null ? other.getRcvStatus() == null : this.getRcvStatus().equals(other.getRcvStatus()))
            && (this.getPoReplyDatea() == null ? other.getPoReplyDatea() == null : this.getPoReplyDatea().equals(other.getPoReplyDatea()))
            && (this.getPoReplyDateb() == null ? other.getPoReplyDateb() == null : this.getPoReplyDateb().equals(other.getPoReplyDateb()))
            && (this.getPoReplyDatec() == null ? other.getPoReplyDatec() == null : this.getPoReplyDatec().equals(other.getPoReplyDatec()))
            && (this.getPoTransType() == null ? other.getPoTransType() == null : this.getPoTransType().equals(other.getPoTransType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getPortArrivedate() == null ? other.getPortArrivedate() == null : this.getPortArrivedate().equals(other.getPortArrivedate()))
            && (this.getCustomsDate() == null ? other.getCustomsDate() == null : this.getCustomsDate().equals(other.getCustomsDate()))
            && (this.getBeginProduceDate() == null ? other.getBeginProduceDate() == null : this.getBeginProduceDate().equals(other.getBeginProduceDate()))
            && (this.getPoPrice() == null ? other.getPoPrice() == null : this.getPoPrice().equals(other.getPoPrice()))
            && (this.getOrderPsnDept() == null ? other.getOrderPsnDept() == null : this.getOrderPsnDept().equals(other.getOrderPsnDept()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getPoImptime() == null ? other.getPoImptime() == null : this.getPoImptime().equals(other.getPoImptime()))
            && (this.getIntercept() == null ? other.getIntercept() == null : this.getIntercept().equals(other.getIntercept()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getSplitNo() == null) ? 0 : getSplitNo().hashCode());
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStateCode() == null) ? 0 : getStateCode().hashCode());
        result = prime * result + ((getStateDes() == null) ? 0 : getStateDes().hashCode());
        result = prime * result + ((getStateDate() == null) ? 0 : getStateDate().hashCode());
        result = prime * result + ((getStateType() == null) ? 0 : getStateType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCustDlvDate() == null) ? 0 : getCustDlvDate().hashCode());
        result = prime * result + ((getCustShipDate() == null) ? 0 : getCustShipDate().hashCode());
        result = prime * result + ((getCustSignDate() == null) ? 0 : getCustSignDate().hashCode());
        result = prime * result + ((getDeptDlvDate() == null) ? 0 : getDeptDlvDate().hashCode());
        result = prime * result + ((getPoDlvDate() == null) ? 0 : getPoDlvDate().hashCode());
        result = prime * result + ((getPoReplyDate() == null) ? 0 : getPoReplyDate().hashCode());
        result = prime * result + ((getPoShipDate() == null) ? 0 : getPoShipDate().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getSupplierNo() == null) ? 0 : getSupplierNo().hashCode());
        result = prime * result + ((getActArrivalDate() == null) ? 0 : getActArrivalDate().hashCode());
        result = prime * result + ((getEsArrivalDate() == null) ? 0 : getEsArrivalDate().hashCode());
        result = prime * result + ((getEsArriveDateReq() == null) ? 0 : getEsArriveDateReq().hashCode());
        result = prime * result + ((getProvider() == null) ? 0 : getProvider().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getEsShipDate() == null) ? 0 : getEsShipDate().hashCode());
        result = prime * result + ((getExpressDlvDate() == null) ? 0 : getExpressDlvDate().hashCode());
        result = prime * result + ((getDelayDay() == null) ? 0 : getDelayDay().hashCode());
        result = prime * result + ((getPoDelayDay() == null) ? 0 : getPoDelayDay().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getCmodelNo() == null) ? 0 : getCmodelNo().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getSupplierOrderno() == null) ? 0 : getSupplierOrderno().hashCode());
        result = prime * result + ((getShikomiNo() == null) ? 0 : getShikomiNo().hashCode());
        result = prime * result + ((getSupplierRcvtime() == null) ? 0 : getSupplierRcvtime().hashCode());
        result = prime * result + ((getOrderPsnNo() == null) ? 0 : getOrderPsnNo().hashCode());
        result = prime * result + ((getOrderAppoverNo() == null) ? 0 : getOrderAppoverNo().hashCode());
        result = prime * result + ((getPoInvoiceNo() == null) ? 0 : getPoInvoiceNo().hashCode());
        result = prime * result + ((getPoExpType() == null) ? 0 : getPoExpType().hashCode());
        result = prime * result + ((getPoHolon() == null) ? 0 : getPoHolon().hashCode());
        result = prime * result + ((getPoNo() == null) ? 0 : getPoNo().hashCode());
        result = prime * result + ((getOrderDate() == null) ? 0 : getOrderDate().hashCode());
        result = prime * result + ((getReceiveDate() == null) ? 0 : getReceiveDate().hashCode());
        result = prime * result + ((getPoDate() == null) ? 0 : getPoDate().hashCode());
        result = prime * result + ((getPurchaseType() == null) ? 0 : getPurchaseType().hashCode());
        result = prime * result + ((getOptUserNo() == null) ? 0 : getOptUserNo().hashCode());
        result = prime * result + ((getOptUserName() == null) ? 0 : getOptUserName().hashCode());
        result = prime * result + ((getPoRcvTime() == null) ? 0 : getPoRcvTime().hashCode());
        result = prime * result + ((getInqa() == null) ? 0 : getInqa().hashCode());
        result = prime * result + ((getInqb() == null) ? 0 : getInqb().hashCode());
        result = prime * result + ((getRcvStatus() == null) ? 0 : getRcvStatus().hashCode());
        result = prime * result + ((getPoReplyDatea() == null) ? 0 : getPoReplyDatea().hashCode());
        result = prime * result + ((getPoReplyDateb() == null) ? 0 : getPoReplyDateb().hashCode());
        result = prime * result + ((getPoReplyDatec() == null) ? 0 : getPoReplyDatec().hashCode());
        result = prime * result + ((getPoTransType() == null) ? 0 : getPoTransType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getPortArrivedate() == null) ? 0 : getPortArrivedate().hashCode());
        result = prime * result + ((getCustomsDate() == null) ? 0 : getCustomsDate().hashCode());
        result = prime * result + ((getBeginProduceDate() == null) ? 0 : getBeginProduceDate().hashCode());
        result = prime * result + ((getPoPrice() == null) ? 0 : getPoPrice().hashCode());
        result = prime * result + ((getOrderPsnDept() == null) ? 0 : getOrderPsnDept().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getPoImptime() == null) ? 0 : getPoImptime().hashCode());
        result = prime * result + ((getIntercept() == null) ? 0 : getIntercept().hashCode());
        return result;
    }
}