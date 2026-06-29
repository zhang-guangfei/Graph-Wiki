package com.smc.smccloud.model.fileupload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class FileUpload implements Serializable {

	/**
	 * serialVersionUID:TODO:(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 7628931558136855089L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();

	/**
	 * 随机文件名称
	 */
	private String randomFileName;

	/**
	 * 真实文件名称
	 */
	private String realFileName;

	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件名称关键字 （如：特价号）
	 */
	private String businessKeyValue;


    /**
	 * 创建人ID
	 */
	private String createId;

	/**
	 * 创建人姓名
	 */
	private String createName;
	/**
	 * 备注
	 */
	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRandomFileName() {
		return randomFileName;
	}

	public void setRandomFileName(String randomFileName) {
		this.randomFileName = randomFileName;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBusinessKeyValue() {
		return businessKeyValue;
	}

	public void setBusinessKeyValue(String businessKeyValue) {
		this.businessKeyValue = businessKeyValue;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
