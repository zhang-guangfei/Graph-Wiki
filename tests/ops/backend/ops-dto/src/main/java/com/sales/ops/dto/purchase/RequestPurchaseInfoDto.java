package com.sales.ops.dto.purchase;

import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.OpsRequestpurchase;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * bug12284 对外暴露的请购实体，用于新增请购使用
 * 
 * @author SMC892N
 *
 */
@Data
public class RequestPurchaseInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8930677194949360323L;

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

	private String inspectiontype;

	private String shikomirelease;

	private String operator;

	private String information;

	private String sendversion;

	private RequestPurchaseInfo infojson;

	private String endUser; //bug 19576 采购发单给老生管对于客户代码的传值

	private String prepareOrderNo;

	public String getPrepareOrderNo() {
		return prepareOrderNo;
	}

	public void setPrepareOrderNo(String prepareOrderNo) {
		this.prepareOrderNo = prepareOrderNo;
	}

	public OpsRequestpurchase convertToEntity() {
		OpsRequestpurchase rq = new OpsRequestpurchase();
		rq.setApplyDeptNo(this.applyDeptNo);
		rq.setCorderno(this.corderno);
		rq.setCustomerno(this.customerno);
		rq.setDeptno(this.deptno);
		rq.setFinishtime(this.finishtime);
		rq.setGroupcustomerno(this.groupcustomerno);
		rq.setHopedeliverydate(this.hopedeliverydate);
		rq.setHopeexportdate(this.hopeexportdate);
		rq.setHscode(this.hscode);
		rq.setIndustrycode(this.industrycode);
		rq.setInqno(this.inqno);
		rq.setInventorypropertyid(this.inventorypropertyid);
		rq.setItemno(this.itemno);
		rq.setModelno(this.modelno);
		rq.setOrderdate(this.orderdate);
		rq.setOrderno(this.orderno);
		rq.setOrdtype(this.ordtype);
		rq.setPpl(this.ppl);
		rq.setProducttag(this.producttag);
		rq.setProducttagremark(this.producttagremark);
		rq.setProducttype(this.producttype);
		rq.setProjectcode(this.projectcode);
		rq.setPurchasetype(this.purchasetype);
		rq.setPurchasewarehouseid(this.purchasewarehouseid);
		rq.setQuantity(this.quantity);
		rq.setRemark(this.remark);
		rq.setRequesttime(this.requesttime);
		rq.setRequestwarehouseid(this.requestwarehouseid);
		rq.setSalesinfono(this.salesinfono);
		rq.setServerremark(this.serverremark);
		rq.setShikomianswerno(this.shikomianswerno);
		rq.setSpecmark(this.specmark);
		rq.setSplititemno(this.splititemno);
		rq.setStatecode(this.statecode);
		rq.setStdprice(this.stdprice);
		rq.setSupplierid(this.supplierid);
		rq.setTranstype(this.transtype);
		rq.setUserno(this.userno);
		rq.setWarehousetype(this.warehousetype);
		rq.setWmtag(this.wmtag);
		if (this.infojson != null) {
			rq.setInfojson(JSON.toJSONString(this.infojson));
		}
		rq.setInventorytypecode(this.inventorytypecode);
		rq.setEndUser(this.endUser);
		rq.setPrepareorderno(this.prepareOrderNo);
		return rq;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
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
		this.customerno = customerno;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getApplyDeptNo() {
		return applyDeptNo;
	}

	public void setApplyDeptNo(String applyDeptNo) {
		this.applyDeptNo = applyDeptNo;
	}

	public String getInqno() {
		return inqno;
	}

	public void setInqno(String inqno) {
		this.inqno = inqno;
	}

	public String getOrdtype() {
		return ordtype;
	}

	public void setOrdtype(String ordtype) {
		this.ordtype = ordtype;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
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
		this.specmark = specmark;
	}

	public String getShikomianswerno() {
		return shikomianswerno;
	}

	public void setShikomianswerno(String shikomianswerno) {
		this.shikomianswerno = shikomianswerno;
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
		this.remark = remark;
	}

	public String getSalesinfono() {
		return salesinfono;
	}

	public void setSalesinfono(String salesinfono) {
		this.salesinfono = salesinfono;
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
		this.producttag = producttag;
	}

	public String getProducttagremark() {
		return producttagremark;
	}

	public void setProducttagremark(String producttagremark) {
		this.producttagremark = producttagremark;
	}

	public String getRequestwarehouseid() {
		return requestwarehouseid;
	}

	public void setRequestwarehouseid(String requestwarehouseid) {
		this.requestwarehouseid = requestwarehouseid;
	}

	public String getPurchasetype() {
		return purchasetype;
	}

	public void setPurchasetype(String purchasetype) {
		this.purchasetype = purchasetype;
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
		this.supplierid = supplierid;
	}

	public String getPurchasewarehouseid() {
		return purchasewarehouseid;
	}

	public void setPurchasewarehouseid(String purchasewarehouseid) {
		this.purchasewarehouseid = purchasewarehouseid;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
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
		this.smccode = smccode;
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
		this.interceptmsg = interceptmsg;
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
		this.releasereason = releasereason;
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
		this.warehousetype = warehousetype;
	}

	public String getIndustrycode() {
		return industrycode;
	}

	public void setIndustrycode(String industrycode) {
		this.industrycode = industrycode;
	}

	public String getInventorytypecode() {
		return inventorytypecode;
	}

	public void setInventorytypecode(String inventorytypecode) {
		this.inventorytypecode = inventorytypecode;
	}

	public String getPpl() {
		return ppl;
	}

	public void setPpl(String ppl) {
		this.ppl = ppl;
	}

	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}

	public String getGroupcustomerno() {
		return groupcustomerno;
	}

	public void setGroupcustomerno(String groupcustomerno) {
		this.groupcustomerno = groupcustomerno;
	}

	public String getWmtag() {
		return wmtag;
	}

	public void setWmtag(String wmtag) {
		this.wmtag = wmtag;
	}

	public String getHscode() {
		return hscode;
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getSupplierpartno() {
		return supplierpartno;
	}

	public void setSupplierpartno(String supplierpartno) {
		this.supplierpartno = supplierpartno;
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
		this.importcurrencyid = importcurrencyid;
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
		this.serverremark = serverremark;
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
		this.corderno = corderno;
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
		this.nonstandardSizedProduct = nonstandardSizedProduct;
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

	public String getInspectiontype() {
		return inspectiontype;
	}

	public void setInspectiontype(String inspectiontype) {
		this.inspectiontype = inspectiontype;
	}

	public String getShikomirelease() {
		return shikomirelease;
	}

	public void setShikomirelease(String shikomirelease) {
		this.shikomirelease = shikomirelease;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getSendversion() {
		return sendversion;
	}

	public void setSendversion(String sendversion) {
		this.sendversion = sendversion;
	}

	public RequestPurchaseInfo getInfojson() {
		return infojson;
	}

	public void setInfojson(RequestPurchaseInfo infojson) {
		this.infojson = infojson;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
}
