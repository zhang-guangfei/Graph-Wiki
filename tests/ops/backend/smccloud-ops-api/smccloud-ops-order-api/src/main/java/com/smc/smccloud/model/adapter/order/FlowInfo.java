package com.smc.smccloud.model.adapter.order;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(description = "流程实体")
public class FlowInfo implements Serializable {


	/**  
	 * serialVersionUID:TODO:(用一句话描述这个变量表示什么).  
	 * @since JDK 1.8  
	 */
	private static final long serialVersionUID = 2554553892004598188L;

	// 节点顺序
	//@ApiModelProperty(value = "节点顺序")
	private int index;

	// 节点名称
	//@ApiModelProperty(value = "节点名称")
	private String name;

	// 节点详情
	//@ApiModelProperty(value = "节点详情")
	private String detail;

	// 是否为当前节点
	//@ApiModelProperty(value = "是否为当前节点")
	private boolean active;

	//@ApiModelProperty(value = "图标")
	private String icon;
	
	
	
	public FlowInfo() {
		super();
	}

	public FlowInfo(int index, String name, String detail, boolean active, String icon) {
		super();
		this.index = index;
		this.name = name;
		this.detail = detail;
		this.active = active;
		this.icon = icon;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
