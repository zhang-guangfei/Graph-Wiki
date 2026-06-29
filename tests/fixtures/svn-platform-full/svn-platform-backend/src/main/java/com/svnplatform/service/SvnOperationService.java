package com.svnplatform.service;

import com.svnplatform.dto.*;
import com.svnplatform.task.AsyncTask;

import java.util.List;
import java.util.Map;

/**
 * SVN操作服务接口
 */
public interface SvnOperationService {

    /**
     * 获取工作副本状态
     */
    List<Map<String, String>> getStatus(Long repoId);

    /**
     * 获取文件diff
     */
    String getDiff(Long repoId, String filePath);

    /**
     * 提交代码（异步）
     */
    AsyncTask commit(Long repoId, CommitRequest request);

    /**
     * 更新代码（异步）
     */
    AsyncTask update(Long repoId, Long revision);

    /**
     * 合并代码（异步）
     */
    AsyncTask merge(Long repoId, MergeRequest request);

    /**
     * 获取提交记录
     */
    List<Map<String, Object>> getLog(Long repoId, int limit, Long startRevision, Long endRevision);

    /**
     * 获取提交记录（增强版，支持更多过滤参数）
     */
    List<Map<String, Object>> getLogWithFilters(Long repoId, int limit, Long startRevision, Long endRevision,
                                                  String author, String startDate, String endDate, String path);

    /**
     * 获取指定提交的变更文件列表
     */
    List<Map<String, String>> getLogChanges(Long repoId, Long revision);

    /**
     * 获取指定文件在指定提交中的diff内容
     */
    String getLogDiff(Long repoId, Long revision, String filePath);

    /**
     * 获取指定文件在两个版本间的内容对比（用于Diff Editor）
     */
    Map<String, String> getFileDiffContent(Long repoId, Long revision, String filePath);

    /**
     * 还原文件
     */
    String revert(Long repoId, RevertRequest request);

    /**
     * 解决冲突
     */
    String resolve(Long repoId, ResolveRequest request);

    /**
     * 清理工作副本
     */
    String cleanup(Long repoId);

    /**
     * 获取仓库信息（实时）
     */
    Map<String, String> getInfo(Long repoId);

    /**
     * 获取任务状态
     */
    AsyncTask getTaskStatus(String taskId);

    /**
     * 将文件添加到changelist
     */
    String addToChangelist(Long repoId, ChangelistRequest request);

    /**
     * 将文件从changelist移除
     */
    String removeFromChangelist(Long repoId, ChangelistRequest request);

    /**
     * 重命名changelist（将所有文件从旧名移至新名）
     */
    String renameChangelist(Long repoId, String oldName, String newName);

    /**
     * 按changelist提交
     */
    AsyncTask commitByChangelist(Long repoId, CommitRequest request);

    /**
     * 获取文件内容（原始版本和当前版本，用于diff对比）
     */
    Map<String, String> getFileContent(Long repoId, String filePath);

    /**
     * 将未版本化文件加入版本控制（svn add）
     */
    String addFiles(Long repoId, List<String> files);

    /**
     * 获取冲突文件的三方内容（BASE/MINE/THEIRS）
     */
    ConflictDetail getConflictDetail(Long repoId, String filePath);

    /**
     * 以指定内容解决冲突（写入合并结果后标记为resolved）
     */
    String resolveWithContent(Long repoId, MergeResolveRequest request);

    /**
     * 列出仓库的分支或标签
     *
     * @param repoId 仓库ID
     * @param pathType "branches" 或 "tags"
     * @return 分支/标签名称列表
     */
    List<String> listBranchesOrTags(Long repoId, String pathType);

    /**
     * 获取分支列表
     */
    List<String> getBranches(Long repoId);

    /**
     * 获取标签列表
     */
    List<String> getTags(Long repoId);

    /**
     * 预览合并冲突
     */
    AsyncTask previewMerge(Long repoId, MergeRequest request);

    /**
     * 执行合并操作
     */
    AsyncTask executeMerge(Long repoId, MergeRequest request);

    /**
     * 获取已合并版本列表
     *
     * @param sourceRepoId 源仓库ID
     * @param targetRepoId 目标仓库ID
     * @return 已合并的版本号列表
     */
    List<Long> getMergedRevisions(Long sourceRepoId, Long targetRepoId);

    /**
     * 创建分支或标签（svn copy）
     *
     * @param repoId 仓库ID
     * @param sourceUrl 源URL（通常是trunk或某分支）
     * @param destName 目标名称
     * @param pathType "branches" 或 "tags"
     * @param message 提交信息
     */
    String createBranchOrTag(Long repoId, String sourceUrl, String destName, String pathType, String message);

    /**
     * 删除分支或标签（svn delete）
     *
     * @param repoId 仓库ID
     * @param name 分支/标签名称
     * @param pathType "branches" 或 "tags"
     * @param message 提交信息
     */
    String deleteBranchOrTag(Long repoId, String name, String pathType, String message);

    /**
     * 获取操作历史记录（分页）
     *
     * @param repoId 仓库ID
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @return 操作记录分页数据
     */
    Map<String, Object> getOperationLogs(Long repoId, int page, int size);

    /**
     * 获取最近的操作历史记录
     *
     * @param repoId 仓库ID
     * @param limit 返回记录数
     * @return 操作记录列表
     */
    List<Map<String, Object>> getRecentOperationLogs(Long repoId, int limit);

    /**
     * 根据操作类型筛选操作历史记录
     *
     * @param repoId 仓库ID
     * @param operationType 操作类型：COMMIT, UPDATE, MERGE, REVERT, CLEANUP, RESOLVE
     * @param page 页码
     * @param size 每页大小
     * @return 操作记录分页数据
     */
    Map<String, Object> getOperationLogsByType(Long repoId, String operationType, int page, int size);

    // === 本地仓库管理 ===

    /**
     * 获取本地仓库状态信息
     *
     * @param localPath 本地仓库路径
     * @return 本地仓库状态
     */
    LocalRepoStatus getLocalRepoStatus(String localPath);

    /**
     * 获取本地仓库文件状态列表
     *
     * @param localPath 本地仓库路径
     * @return 文件状态列表（包含统计信息）
     */
    FileStatusList getFileStatusList(String localPath);

    /**
     * 更新本地仓库到最新版本
     *
     * @param localPath 本地仓库路径
     * @param revision 目标版本号（null表示更新到HEAD）
     * @return 异步任务
     */
    AsyncTask svnUpdate(String localPath, Long revision);

    /**
     * 提交本地仓库变更
     *
     * @param localPath 本地仓库路径
     * @param request 提交请求
     * @return 异步任务
     */
    AsyncTask svnCommit(String localPath, CommitRequest request);

    /**
     * 还原本地仓库文件变更
     *
     * @param localPath 本地仓库路径
     * @param request 还原请求
     * @return 操作结果
     */
    String svnRevert(String localPath, RevertRequest request);

    /**
     * 清理本地仓库
     *
     * @param localPath 本地仓库路径
     * @return 操作结果
     */
    String svnCleanup(String localPath);

    /**
     * 获取冲突文件详情
     *
     * @param localPath 本地仓库路径
     * @param filePath 冲突文件路径
     * @return 冲突详情
     */
    ConflictDetail getConflictDetailByPath(String localPath, String filePath);

    /**
     * 在系统中打开指定文件夹
     *
     * @param folderPath 文件夹路径
     * @return 操作结果
     */
    String openFolder(String folderPath);
}
