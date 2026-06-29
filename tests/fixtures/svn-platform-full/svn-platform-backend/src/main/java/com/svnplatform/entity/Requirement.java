package com.svnplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 需求管理实体
 */
@Data
@Entity
@Table(name = "requirement")
public class Requirement {

    @Id
    @Column(name = "requirement_id", nullable = false, unique = true)
    private String requirementId;

    /**
     * 需求标题
     */
    @Column(nullable = false)
    private String title;

    /**
     * 需求描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 需求类型（功能开发、Bug修复、优化、重构、配置调整、代码清理）
     */
    @Column(length = 50)
    private String type;

    /**
     * 模板格式字符串（如：【{type}】#{requirementId} {title}）
     */
    @Column(length = 500)
    private String format;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
