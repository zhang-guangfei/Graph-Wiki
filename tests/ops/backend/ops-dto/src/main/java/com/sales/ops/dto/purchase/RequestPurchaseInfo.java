package com.sales.ops.dto.purchase;

import java.io.Serializable;
import java.util.List;

import com.sales.ops.db.entity.OpsInqbDetail;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;

/**
 * bug12284 保存外部设置的数据信息传入请购保存
 * 
 * @author SMC892N
 *
 */

public class RequestPurchaseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3669043434092312393L;

	// 是否为重点项目，是否使用VIPCODE备注
	private boolean vip;

	// 是否为业务已指定出荷日
	private boolean operateExportDate;

	// 是否最低售价
	private boolean minPrice;

	// 最低售价附件列表
	private List<OpsAttachedFileManageVO> fileList;

	// bug 13517 增加客户型号及客户订单号
	private String cproductno;

	private String corderno;

	private OpsInqbDetail inqbDetail;

	// bug14940 备案客户指定空运标识
	private Boolean airCustomer;

	// 采购拦截配置表id
	private List<Integer> interceptConfigIdList;

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public boolean isOperateExportDate() {
		return operateExportDate;
	}

	public void setOperateExportDate(boolean operateExportDate) {
		this.operateExportDate = operateExportDate;
	}

	public boolean isMinPrice() {
		return minPrice;
	}

	public void setMinPrice(boolean minPrice) {
		this.minPrice = minPrice;
	}

	public List<OpsAttachedFileManageVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<OpsAttachedFileManageVO> fileList) {
		this.fileList = fileList;
	}

	public String getCproductno() {
		return cproductno;
	}

	public void setCproductno(String cproductno) {
		this.cproductno = cproductno;
	}

	public String getCorderno() {
		return corderno;
	}

	public void setCorderno(String corderno) {
		this.corderno = corderno;
	}

	public OpsInqbDetail getInqbDetail() {
		return inqbDetail;
	}

	public void setInqbDetail(OpsInqbDetail inqbDetail) {
		this.inqbDetail = inqbDetail;
	}

	public Boolean getAirCustomer() {
		return airCustomer;
	}

	public void setAirCustomer(Boolean airCustomer) {
		this.airCustomer = airCustomer;
	}

	public List<Integer> getInterceptConfigIdList() {
		return interceptConfigIdList;
	}

	public void setInterceptConfigIdList(List<Integer> interceptConfigIdList) {
		this.interceptConfigIdList = interceptConfigIdList;
	}
}
