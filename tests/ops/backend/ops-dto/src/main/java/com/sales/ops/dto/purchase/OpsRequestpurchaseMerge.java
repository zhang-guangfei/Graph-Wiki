package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class OpsRequestpurchaseMerge implements Serializable {
	
	private String uuid;
	
    private String orderno;

    private String customerno;

    private String userno;

    private String deptno;

    private String inqno;

    private String ordtype;

    private String modelno;

    private Integer quantity;

    private BigDecimal stdprice;

    private String specmark;

    private String shikomianswerno;
    
    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopedeliverydate;

    private String statecode;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requesttime;

    private String producttag;

    private String producttagremark;

    private String requestwarehouseid;

    private String purchasewarehouseid;

    private String transtype;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeexportdate;

    private String supplierid;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderdate;

    private Boolean islot;

    private String interceptmsg;
    
    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeExportDateMerge;
    
    private String transTypeMerge;

    private String requestWarehouseIdMerge;
    
    private int  QuantitySum;
    // 是否为BIN品
    private boolean isBin;
    //bin数量
    private int binCount;
    //bin频率
    private int binFrequency;

    public boolean isBin() {
        return isBin;
    }

    public void setBin(boolean bin) {
        isBin = bin;
    }

    public int getBinCount() {
        return binCount;
    }

    public void setBinCount(int binCount) {
        this.binCount = binCount;
    }

    public int getBinFrequency() {
        return binFrequency;
    }

    public void setBinFrequency(int binFrequency) {
        this.binFrequency = binFrequency;
    }

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getHopeExportDateMerge() {
		return hopeExportDateMerge;
	}

	public void setHopeExportDateMerge(Date hopeExportDateMerge) {
		this.hopeExportDateMerge = hopeExportDateMerge;
	}

	public String getTransTypeMerge() {
		return transTypeMerge;
	}

	public void setTransTypeMerge(String transTypeMerge) {
		this.transTypeMerge = transTypeMerge;
	}

	public String getRequestWarehouseIdMerge() {
		return requestWarehouseIdMerge;
	}

	public void setRequestWarehouseIdMerge(String requestWarehouseIdMerge) {
		this.requestWarehouseIdMerge = requestWarehouseIdMerge;
	}

	public int getQuantitySum() {
		return QuantitySum;
	}

	public void setQuantitySum(int quantitySum) {
		QuantitySum = quantitySum;
	}

	private static final long serialVersionUID = 1L;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
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

    public Date getHopedeliverydate() {
        return hopedeliverydate;
    }

    public void setHopedeliverydate(Date hopedeliverydate) {
        this.hopedeliverydate = hopedeliverydate;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
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
}