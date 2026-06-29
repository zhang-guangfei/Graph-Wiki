package com.svnplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * 提交请求DTO
 */
@Data
public class CommitRequest {

    /**
     * 提交信息
     */
    private String message;

    /**
     * 要提交的文件路径列表（相对路径）
     */
    private List<String> files;

    /**
     * 是否自动add未版本控制的文件
     */
    private boolean autoAdd = true;

    /**
     * 按changelist提交时指定的changelist名称
     */
    private String changelist;
}
