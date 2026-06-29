package com.sales.ops.dto.util;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * bug12513 新增邮件附件实体类
 * 
 * @author SMC892N
 *
 */
@Data
public class MailFileDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4476918372481642535L;

	// 是否压缩，true，false
	private boolean isZip;

	// 压缩文件名
	private String zipFileName;

	// 是否加密，true，false
	private boolean encryption;

	// 加密密码
	private String password;

	private List<MailFileInfo> fileList;

	public boolean isZip() {
		return isZip;
	}

	public void setZip(boolean zip) {
		isZip = zip;
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public boolean isEncryption() {
		return encryption;
	}

	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<MailFileInfo> getFileList() {
		return fileList;
	}

	public void setFileList(List<MailFileInfo> fileList) {
		this.fileList = fileList;
	}
}
