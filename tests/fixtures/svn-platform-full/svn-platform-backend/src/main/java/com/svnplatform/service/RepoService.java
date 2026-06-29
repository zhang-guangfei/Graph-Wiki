package com.svnplatform.service;

import com.svnplatform.dto.*;
import com.svnplatform.entity.Repository;

import java.util.List;
import java.util.Map;

/**
 * 仓库管理服务接口
 */
public interface RepoService {

    /**
     * 验证并导入本地SVN目录
     */
    Repository importRepo(RepoImportRequest request);

    /**
     * 获取所有已导入仓库列表
     */
    List<Repository> listRepos();

    /**
     * 获取仓库详细信息
     */
    Repository getRepo(Long id);

    /**
     * 删除仓库记录（不删除本地文件）
     */
    void deleteRepo(Long id);

    /**
     * 修改仓库名称
     */
    Repository renameRepo(Long id, String newName);

    /**
     * 验证路径是否为有效SVN工作副本
     */
    Map<String, String> validatePath(String localPath);

    /**
     * 打开系统文件夹选择对话框
     */
    String browseFolder();
}
