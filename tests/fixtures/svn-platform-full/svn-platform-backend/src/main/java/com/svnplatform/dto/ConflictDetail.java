package com.svnplatform.dto;

import lombok.Data;

/**
 * 冲突文件详情 DTO
 * 包含三方内容：BASE（共同祖先）、MINE（本地修改）、THEIRS（远端版本）
 */
@Data
public class ConflictDetail {

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * BASE版本内容（共同祖先）
     */
    private String base;

    /**
     * MINE版本内容（本地修改）
     */
    private String mine;

    /**
     * THEIRS版本内容（远端版本）
     */
    private String theirs;

    /**
     * 当前工作副本内容（包含冲突标记）
     */
    private String merged;
}
