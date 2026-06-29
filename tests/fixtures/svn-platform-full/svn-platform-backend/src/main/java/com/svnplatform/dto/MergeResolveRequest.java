package com.svnplatform.dto;

import lombok.Data;

/**
 * 合并解决请求 DTO
 */
@Data
public class MergeResolveRequest {

    /**
     * 冲突文件路径
     */
    private String filePath;

    /**
     * 合并后的文件内容
     */
    private String content;
}
