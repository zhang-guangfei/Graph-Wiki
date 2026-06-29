package com.smc.smccloud.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/11/20 12:07
 * @Descripton TODO
 */
@Data
public class UploadFileByUIVO {

    private String keyValue;

    private String businessType;

    private String fileType;

    private List<MultipartFile> fileList;

    private String createUser;
}
