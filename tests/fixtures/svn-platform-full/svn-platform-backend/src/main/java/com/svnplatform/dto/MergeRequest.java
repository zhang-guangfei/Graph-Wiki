package com.svnplatform.dto;

import lombok.Data;
import java.util.List;

/**
 * 合并请求DTO
 */
@Data
public class MergeRequest {

    /**
     * 源仓库ID
     */
    private Long sourceRepoId;

    /**
     * 源分支URL
     */
    private String sourceUrl;

    /**
     * 起始版本号
     */
    private Long startRevision;

    /**
     * 结束版本号
     */
    private Long endRevision;

    /**
     * 离散版本号列表（用于勾选多个不连续提交的情况）
     */
    private List<Long> revisionList;

    /**
     * 是否仅预览（dry-run）
     */
    private boolean dryRun = false;

    /**
     * 遇到冲突是否跳过失败版本继续合并（仅适用于离散版本模式）
     */
    private boolean skipFailedRevisions = false;
}
