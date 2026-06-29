package com.svnplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * SVN仓库实体
 */
@Data
@Entity
@Table(name = "repository")
public class Repository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 仓库名称（用户自定义别名）
     */
    @Column(nullable = false)
    private String name;

    /**
     * 本地工作副本路径
     */
    @Column(name = "local_path", nullable = false, unique = true)
    private String localPath;

    /**
     * SVN仓库URL
     */
    @Column(name = "svn_url")
    private String svnUrl;

    /**
     * 最后已知版本号
     */
    @Column(name = "last_revision")
    private Long lastRevision;

    /**
     * 仓库根URL
     */
    @Column(name = "repository_root")
    private String repositoryRoot;

    /**
     * 仓库UUID
     */
    @Column(name = "repository_uuid")
    private String repositoryUuid;

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
