package com.sales.ops.dto.attachedfile;

import com.smc.smccloud.model.fileupload.FileUpload;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/10/26 12:07
 * @Descripton TODO
 */
@Data
public class FileUploadInsertVO  extends FileUpload {

    // 业务单号
    private String businessKeyValue;

    // 附件
    private List<FileUpload> fileList;

    // 操作人
    private String  createUser;

    // 文件类型
    private  String fileType;

    // 业务类型
    private String businessType;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessKeyValue() {
        return businessKeyValue;
    }

    public void setBusinessKeyValue(String businessKeyValue) {
        this.businessKeyValue = businessKeyValue;
    }

    public List<FileUpload> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileUpload> fileList) {
        this.fileList = fileList;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
