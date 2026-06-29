package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RequestpurchaseView implements Serializable {
    private String orderItem;

    private String orderItemSplit;

    private Long id;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String statecode;

    private Boolean mergeflag;

    private String customerno;

    private String userno;

    private String deptno;

    private String applyDeptNo;

    private String inqno;

    private String ordtype;

    private String modelno;

    private Integer quantity;

    private BigDecimal stdprice;

    private String specmark;

    private String shikomianswerno;

    private Integer shikomiremainqty;

    private Date hopedeliverydate;

    private String remark;

    private String salesinfono;

    private Date requesttime;

    private String producttag;

    private String producttagremark;

    private String requestwarehouseid;

    private String purchasetype;

    private Date orderdate;

    private String supplierid;

    private String purchasewarehouseid;

    private String transtype;

    private Date hopeexportdate;

    private String smccode;

    private Boolean islot;

    private String interceptmsg;

    private BigDecimal netweight;

    private Boolean notuseshikomi;

    private String releasereason;

    private Boolean isedited;

    private Integer producttype;

    private String warehousetype;

    private String industrycode;

    private String inventorytypecode;

    private String ppl;

    private String projectcode;

    private String groupcustomerno;

    private String wmtag;

    private String hscode;

    private String supplierpartno;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private Long inventorypropertyid;

    private String serverremark;

    private Integer supplierinventory;

    private String corderno;

    private Boolean iseven;

    private Integer minpackunit;

    private String nonstandardSizedProduct;

    private Integer qtyimport;

    private Date finishtime;

    private Date updatetime;

    private String operator;

    private String deletereason;

    private String poOrderNo;

    private Integer poItemNo;

    private Integer poSplitNo;

    private String sendversion;

    private String supplierAssignType;

    private String endUser;

    private String prepareorderno;

    private static final long serialVersionUID = 1L;

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public String getOrderItemSplit() {
        return orderItemSplit;
    }

    public void setOrderItemSplit(String orderItemSplit) {
        this.orderItemSplit = orderItemSplit == null ? null : orderItemSplit.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
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

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public Boolean getMergeflag() {
        return mergeflag;
    }

    public void setMergeflag(Boolean mergeflag) {
        this.mergeflag = mergeflag;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo == null ? null : applyDeptNo.trim();
    }

    public String getInqno() {
        return inqno;
    }

    public void setInqno(String inqno) {
        this.inqno = inqno == null ? null : inqno.trim();
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getStdprice() {
        return stdprice;
    }

    public void setStdprice(BigDecimal stdprice) {
        this.stdprice = stdprice;
    }

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
    }

    public String getShikomianswerno() {
        return shikomianswerno;
    }

    public void setShikomianswerno(String shikomianswerno) {
        this.shikomianswerno = shikomianswerno == null ? null : shikomianswerno.trim();
    }

    public Integer getShikomiremainqty() {
        return shikomiremainqty;
    }

    public void setShikomiremainqty(Integer shikomiremainqty) {
        this.shikomiremainqty = shikomiremainqty;
    }

    public Date getHopedeliverydate() {
        return hopedeliverydate;
    }

    public void setHopedeliverydate(Date hopedeliverydate) {
        this.hopedeliverydate = hopedeliverydate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public Date getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(Date requesttime) {
        this.requesttime = requesttime;
    }

    public String getProducttag() {
        return producttag;
    }

    public void setProducttag(String producttag) {
        this.producttag = producttag == null ? null : producttag.trim();
    }

    public String getProducttagremark() {
        return producttagremark;
    }

    public void setProducttagremark(String producttagremark) {
        this.producttagremark = producttagremark == null ? null : producttagremark.trim();
    }

    public String getRequestwarehouseid() {
        return requestwarehouseid;
    }

    public void setRequestwarehouseid(String requestwarehouseid) {
        this.requestwarehouseid = requestwarehouseid == null ? null : requestwarehouseid.trim();
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype == null ? null : purchasetype.trim();
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getPurchasewarehouseid() {
        return purchasewarehouseid;
    }

    public void setPurchasewarehouseid(String purchasewarehouseid) {
        this.purchasewarehouseid = purchasewarehouseid == null ? null : purchasewarehouseid.trim();
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public Date getHopeexportdate() {
        return hopeexportdate;
    }

    public void setHopeexportdate(Date hopeexportdate) {
        this.hopeexportdate = hopeexportdate;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public Boolean getIslot() {
        return islot;
    }

    public void setIslot(Boolean islot) {
        this.islot = islot;
    }

    public String getInterceptmsg() {
        return interceptmsg;
    }

    public void setInterceptmsg(String interceptmsg) {
        this.interceptmsg = interceptmsg == null ? null : interceptmsg.trim();
    }

    public BigDecimal getNetweight() {
        return netweight;
    }

    public void setNetweight(BigDecimal netweight) {
        this.netweight = netweight;
    }

    public Boolean getNotuseshikomi() {
        return notuseshikomi;
    }

    public void setNotuseshikomi(Boolean notuseshikomi) {
        this.notuseshikomi = notuseshikomi;
    }

    public String getReleasereason() {
        return releasereason;
    }

    public void setReleasereason(String releasereason) {
        this.releasereason = releasereason == null ? null : releasereason.trim();
    }

    public Boolean getIsedited() {
        return isedited;
    }

    public void setIsedited(Boolean isedited) {
        this.isedited = isedited;
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    public String getWarehousetype() {
        return warehousetype;
    }

    public void setWarehousetype(String warehousetype) {
        this.warehousetype = warehousetype == null ? null : warehousetype.trim();
    }

    public String getIndustrycode() {
        return industrycode;
    }

    public void setIndustrycode(String industrycode) {
        this.industrycode = industrycode == null ? null : industrycode.trim();
    }

    public String getInventorytypecode() {
        return inventorytypecode;
    }

    public void setInventorytypecode(String inventorytypecode) {
        this.inventorytypecode = inventorytypecode == null ? null : inventorytypecode.trim();
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl == null ? null : ppl.trim();
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode == null ? null : projectcode.trim();
    }

    public String getGroupcustomerno() {
        return groupcustomerno;
    }

    public void setGroupcustomerno(String groupcustomerno) {
        this.groupcustomerno = groupcustomerno == null ? null : groupcustomerno.trim();
    }

    public String getWmtag() {
        return wmtag;
    }

    public void setWmtag(String wmtag) {
        this.wmtag = wmtag == null ? null : wmtag.trim();
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode == null ? null : hscode.trim();
    }

    public String getSupplierpartno() {
        return supplierpartno;
    }

    public void setSupplierpartno(String supplierpartno) {
        this.supplierpartno = supplierpartno == null ? null : supplierpartno.trim();
    }

    public BigDecimal getImportfobpriceoriginal() {
        return importfobpriceoriginal;
    }

    public void setImportfobpriceoriginal(BigDecimal importfobpriceoriginal) {
        this.importfobpriceoriginal = importfobpriceoriginal;
    }

    public String getImportcurrencyid() {
        return importcurrencyid;
    }

    public void setImportcurrencyid(String importcurrencyid) {
        this.importcurrencyid = importcurrencyid == null ? null : importcurrencyid.trim();
    }

    public Long getInventorypropertyid() {
        return inventorypropertyid;
    }

    public void setInventorypropertyid(Long inventorypropertyid) {
        this.inventorypropertyid = inventorypropertyid;
    }

    public String getServerremark() {
        return serverremark;
    }

    public void setServerremark(String serverremark) {
        this.serverremark = serverremark == null ? null : serverremark.trim();
    }

    public Integer getSupplierinventory() {
        return supplierinventory;
    }

    public void setSupplierinventory(Integer supplierinventory) {
        this.supplierinventory = supplierinventory;
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno == null ? null : corderno.trim();
    }

    public Boolean getIseven() {
        return iseven;
    }

    public void setIseven(Boolean iseven) {
        this.iseven = iseven;
    }

    public Integer getMinpackunit() {
        return minpackunit;
    }

    public void setMinpackunit(Integer minpackunit) {
        this.minpackunit = minpackunit;
    }

    public String getNonstandardSizedProduct() {
        return nonstandardSizedProduct;
    }

    public void setNonstandardSizedProduct(String nonstandardSizedProduct) {
        this.nonstandardSizedProduct = nonstandardSizedProduct == null ? null : nonstandardSizedProduct.trim();
    }

    public Integer getQtyimport() {
        return qtyimport;
    }

    public void setQtyimport(Integer qtyimport) {
        this.qtyimport = qtyimport;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDeletereason() {
        return deletereason;
    }

    public void setDeletereason(String deletereason) {
        this.deletereason = deletereason == null ? null : deletereason.trim();
    }

    public String getPoOrderNo() {
        return poOrderNo;
    }

    public void setPoOrderNo(String poOrderNo) {
        this.poOrderNo = poOrderNo == null ? null : poOrderNo.trim();
    }

    public Integer getPoItemNo() {
        return poItemNo;
    }

    public void setPoItemNo(Integer poItemNo) {
        this.poItemNo = poItemNo;
    }

    public Integer getPoSplitNo() {
        return poSplitNo;
    }

    public void setPoSplitNo(Integer poSplitNo) {
        this.poSplitNo = poSplitNo;
    }

    public String getSendversion() {
        return sendversion;
    }

    public void setSendversion(String sendversion) {
        this.sendversion = sendversion == null ? null : sendversion.trim();
    }

    public String getSupplierAssignType() {
        return supplierAssignType;
    }

    public void setSupplierAssignType(String supplierAssignType) {
        this.supplierAssignType = supplierAssignType == null ? null : supplierAssignType.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getPrepareorderno() {
        return prepareorderno;
    }

    public void setPrepareorderno(String prepareorderno) {
        this.prepareorderno = prepareorderno == null ? null : prepareorderno.trim();
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
        RequestpurchaseView other = (RequestpurchaseView) that;
        return (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getOrderItemSplit() == null ? other.getOrderItemSplit() == null : this.getOrderItemSplit().equals(other.getOrderItemSplit()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getMergeflag() == null ? other.getMergeflag() == null : this.getMergeflag().equals(other.getMergeflag()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getApplyDeptNo() == null ? other.getApplyDeptNo() == null : this.getApplyDeptNo().equals(other.getApplyDeptNo()))
            && (this.getInqno() == null ? other.getInqno() == null : this.getInqno().equals(other.getInqno()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStdprice() == null ? other.getStdprice() == null : this.getStdprice().equals(other.getStdprice()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getShikomianswerno() == null ? other.getShikomianswerno() == null : this.getShikomianswerno().equals(other.getShikomianswerno()))
            && (this.getShikomiremainqty() == null ? other.getShikomiremainqty() == null : this.getShikomiremainqty().equals(other.getShikomiremainqty()))
            && (this.getHopedeliverydate() == null ? other.getHopedeliverydate() == null : this.getHopedeliverydate().equals(other.getHopedeliverydate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getRequesttime() == null ? other.getRequesttime() == null : this.getRequesttime().equals(other.getRequesttime()))
            && (this.getProducttag() == null ? other.getProducttag() == null : this.getProducttag().equals(other.getProducttag()))
            && (this.getProducttagremark() == null ? other.getProducttagremark() == null : this.getProducttagremark().equals(other.getProducttagremark()))
            && (this.getRequestwarehouseid() == null ? other.getRequestwarehouseid() == null : this.getRequestwarehouseid().equals(other.getRequestwarehouseid()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getOrderdate() == null ? other.getOrderdate() == null : this.getOrderdate().equals(other.getOrderdate()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getPurchasewarehouseid() == null ? other.getPurchasewarehouseid() == null : this.getPurchasewarehouseid().equals(other.getPurchasewarehouseid()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getHopeexportdate() == null ? other.getHopeexportdate() == null : this.getHopeexportdate().equals(other.getHopeexportdate()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getIslot() == null ? other.getIslot() == null : this.getIslot().equals(other.getIslot()))
            && (this.getInterceptmsg() == null ? other.getInterceptmsg() == null : this.getInterceptmsg().equals(other.getInterceptmsg()))
            && (this.getNetweight() == null ? other.getNetweight() == null : this.getNetweight().equals(other.getNetweight()))
            && (this.getNotuseshikomi() == null ? other.getNotuseshikomi() == null : this.getNotuseshikomi().equals(other.getNotuseshikomi()))
            && (this.getReleasereason() == null ? other.getReleasereason() == null : this.getReleasereason().equals(other.getReleasereason()))
            && (this.getIsedited() == null ? other.getIsedited() == null : this.getIsedited().equals(other.getIsedited()))
            && (this.getProducttype() == null ? other.getProducttype() == null : this.getProducttype().equals(other.getProducttype()))
            && (this.getWarehousetype() == null ? other.getWarehousetype() == null : this.getWarehousetype().equals(other.getWarehousetype()))
            && (this.getIndustrycode() == null ? other.getIndustrycode() == null : this.getIndustrycode().equals(other.getIndustrycode()))
            && (this.getInventorytypecode() == null ? other.getInventorytypecode() == null : this.getInventorytypecode().equals(other.getInventorytypecode()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectcode() == null ? other.getProjectcode() == null : this.getProjectcode().equals(other.getProjectcode()))
            && (this.getGroupcustomerno() == null ? other.getGroupcustomerno() == null : this.getGroupcustomerno().equals(other.getGroupcustomerno()))
            && (this.getWmtag() == null ? other.getWmtag() == null : this.getWmtag().equals(other.getWmtag()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()))
            && (this.getInventorypropertyid() == null ? other.getInventorypropertyid() == null : this.getInventorypropertyid().equals(other.getInventorypropertyid()))
            && (this.getServerremark() == null ? other.getServerremark() == null : this.getServerremark().equals(other.getServerremark()))
            && (this.getSupplierinventory() == null ? other.getSupplierinventory() == null : this.getSupplierinventory().equals(other.getSupplierinventory()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()))
            && (this.getIseven() == null ? other.getIseven() == null : this.getIseven().equals(other.getIseven()))
            && (this.getMinpackunit() == null ? other.getMinpackunit() == null : this.getMinpackunit().equals(other.getMinpackunit()))
            && (this.getNonstandardSizedProduct() == null ? other.getNonstandardSizedProduct() == null : this.getNonstandardSizedProduct().equals(other.getNonstandardSizedProduct()))
            && (this.getQtyimport() == null ? other.getQtyimport() == null : this.getQtyimport().equals(other.getQtyimport()))
            && (this.getFinishtime() == null ? other.getFinishtime() == null : this.getFinishtime().equals(other.getFinishtime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getDeletereason() == null ? other.getDeletereason() == null : this.getDeletereason().equals(other.getDeletereason()))
            && (this.getPoOrderNo() == null ? other.getPoOrderNo() == null : this.getPoOrderNo().equals(other.getPoOrderNo()))
            && (this.getPoItemNo() == null ? other.getPoItemNo() == null : this.getPoItemNo().equals(other.getPoItemNo()))
            && (this.getPoSplitNo() == null ? other.getPoSplitNo() == null : this.getPoSplitNo().equals(other.getPoSplitNo()))
            && (this.getSendversion() == null ? other.getSendversion() == null : this.getSendversion().equals(other.getSendversion()))
            && (this.getSupplierAssignType() == null ? other.getSupplierAssignType() == null : this.getSupplierAssignType().equals(other.getSupplierAssignType()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getPrepareorderno() == null ? other.getPrepareorderno() == null : this.getPrepareorderno().equals(other.getPrepareorderno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getOrderItemSplit() == null) ? 0 : getOrderItemSplit().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getMergeflag() == null) ? 0 : getMergeflag().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getApplyDeptNo() == null) ? 0 : getApplyDeptNo().hashCode());
        result = prime * result + ((getInqno() == null) ? 0 : getInqno().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStdprice() == null) ? 0 : getStdprice().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getShikomianswerno() == null) ? 0 : getShikomianswerno().hashCode());
        result = prime * result + ((getShikomiremainqty() == null) ? 0 : getShikomiremainqty().hashCode());
        result = prime * result + ((getHopedeliverydate() == null) ? 0 : getHopedeliverydate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getRequesttime() == null) ? 0 : getRequesttime().hashCode());
        result = prime * result + ((getProducttag() == null) ? 0 : getProducttag().hashCode());
        result = prime * result + ((getProducttagremark() == null) ? 0 : getProducttagremark().hashCode());
        result = prime * result + ((getRequestwarehouseid() == null) ? 0 : getRequestwarehouseid().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getOrderdate() == null) ? 0 : getOrderdate().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getPurchasewarehouseid() == null) ? 0 : getPurchasewarehouseid().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getHopeexportdate() == null) ? 0 : getHopeexportdate().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getIslot() == null) ? 0 : getIslot().hashCode());
        result = prime * result + ((getInterceptmsg() == null) ? 0 : getInterceptmsg().hashCode());
        result = prime * result + ((getNetweight() == null) ? 0 : getNetweight().hashCode());
        result = prime * result + ((getNotuseshikomi() == null) ? 0 : getNotuseshikomi().hashCode());
        result = prime * result + ((getReleasereason() == null) ? 0 : getReleasereason().hashCode());
        result = prime * result + ((getIsedited() == null) ? 0 : getIsedited().hashCode());
        result = prime * result + ((getProducttype() == null) ? 0 : getProducttype().hashCode());
        result = prime * result + ((getWarehousetype() == null) ? 0 : getWarehousetype().hashCode());
        result = prime * result + ((getIndustrycode() == null) ? 0 : getIndustrycode().hashCode());
        result = prime * result + ((getInventorytypecode() == null) ? 0 : getInventorytypecode().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectcode() == null) ? 0 : getProjectcode().hashCode());
        result = prime * result + ((getGroupcustomerno() == null) ? 0 : getGroupcustomerno().hashCode());
        result = prime * result + ((getWmtag() == null) ? 0 : getWmtag().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        result = prime * result + ((getInventorypropertyid() == null) ? 0 : getInventorypropertyid().hashCode());
        result = prime * result + ((getServerremark() == null) ? 0 : getServerremark().hashCode());
        result = prime * result + ((getSupplierinventory() == null) ? 0 : getSupplierinventory().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        result = prime * result + ((getIseven() == null) ? 0 : getIseven().hashCode());
        result = prime * result + ((getMinpackunit() == null) ? 0 : getMinpackunit().hashCode());
        result = prime * result + ((getNonstandardSizedProduct() == null) ? 0 : getNonstandardSizedProduct().hashCode());
        result = prime * result + ((getQtyimport() == null) ? 0 : getQtyimport().hashCode());
        result = prime * result + ((getFinishtime() == null) ? 0 : getFinishtime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getDeletereason() == null) ? 0 : getDeletereason().hashCode());
        result = prime * result + ((getPoOrderNo() == null) ? 0 : getPoOrderNo().hashCode());
        result = prime * result + ((getPoItemNo() == null) ? 0 : getPoItemNo().hashCode());
        result = prime * result + ((getPoSplitNo() == null) ? 0 : getPoSplitNo().hashCode());
        result = prime * result + ((getSendversion() == null) ? 0 : getSendversion().hashCode());
        result = prime * result + ((getSupplierAssignType() == null) ? 0 : getSupplierAssignType().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getPrepareorderno() == null) ? 0 : getPrepareorderno().hashCode());
        return result;
    }
}