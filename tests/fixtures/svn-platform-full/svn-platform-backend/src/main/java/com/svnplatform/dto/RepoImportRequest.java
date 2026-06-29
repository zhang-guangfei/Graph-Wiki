package com.svnplatform.dto;

import lombok.Data;

/**
 * 仓库导入请求DTO
 */
@Data
public class RepoImportRequest {

    /**
     * 本地SVN工作副本路径
     */
    private String localPath;

    /**
     * 自定义仓库名称（可选，为空则使用目录名）
     */
    private String name;
}
