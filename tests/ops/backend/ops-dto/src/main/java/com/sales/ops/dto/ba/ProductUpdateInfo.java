package com.sales.ops.dto.ba;

import java.io.Serializable;

public class ProductUpdateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -993762839245000693L;

	private int id;

	private String modelno;

	private boolean isdelete;

	private String supplier;

	private int activity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelno() {
		return modelno;
	}

	public void setModelno(String modelno) {
		this.modelno = modelno;
	}

	public boolean isIsdelete() {
		return isdelete;
	}

	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

}
