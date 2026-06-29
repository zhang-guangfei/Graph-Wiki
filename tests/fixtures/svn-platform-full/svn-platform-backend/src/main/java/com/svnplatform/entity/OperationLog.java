package com.svnplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 操作日志实体
 */
@Data
@Entity
@Table(name = "operation_log")
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关联仓库ID
     */
    @Column(name = "repo_id", nullable = false)
    private Long repoId;

    /**
     * 操作类型：COMMIT, UPDATE, MERGE, REVERT, CLEANUP, RESOLVE
     */
    @Column(name = "operation_type", nullable = false)
    private String operationType;

    /**
     * 操作详情
     */
    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    /**
     * 操作状态：SUCCESS, FAILED, RUNNING
     */
    @Column(nullable = false)
    private String status;

    /**
     * 错误信息
     */
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
