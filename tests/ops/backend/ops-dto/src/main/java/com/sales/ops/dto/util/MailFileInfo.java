package com.sales.ops.dto.util;

import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;

public class MailFileInfo extends OpsAttachedFileManageVO {

	// 附件文件名（可选）
	private String attachedFileName;

	public String getAttachedFileName() {
		return attachedFileName;
	}

	public void setAttachedFileName(String attachedFileName) {
		this.attachedFileName = attachedFileName;
	}

	public MailFileInfo convert(OpsAttachedFileManageVO vo) {
		this.setBusinessKeyValue(vo.getBusinessKeyValue());
		this.setBatchNo(vo.getBatchNo());
		this.setBusinessType(vo.getBusinessType());
		this.setFilePath(vo.getFilePath());
		this.setDelFlag(vo.getDelFlag());
		this.setFileType(vo.getFileType());
		this.setRandomFileName(vo.getRandomFileName());
		this.setRealFileName(vo.getRealFileName());
		return this;
	}
}
