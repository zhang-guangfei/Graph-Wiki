package com.sales.ops.dto.ips;

import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.sales.ops.dto.util.MailFileInfo;

/**
 * @description:
 * @author: B91717
 * @time: 2025/12/4 17:16
 * 发送PSI时，增加详细的附件清单，包含文件路径，真实文件名，随机文件名等等
 */
public class PsiFileInfoDto {


    /**
     * 文件类型(minPrice最低售价)
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 实际文件名
     */
    private String randomFileName;

    /**
     * 真实文件名
     */
    private String realFileName;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public PsiFileInfoDto convert(OpsAttachedFileManageVO vo) {
//        this.setBusinessKeyValue(vo.getBusinessKeyValue());
//        this.setBatchNo(vo.getBatchNo());
//        this.setBusinessType(vo.getBusinessType());
        this.setFilePath(vo.getFilePath());
//        this.setDelFlag(vo.getDelFlag());
        this.setFileType(vo.getFileType());
        this.setRandomFileName(vo.getRandomFileName());
        this.setRealFileName(vo.getRealFileName());
        return this;
    }
}
