package com.svnplatform.controller;

import com.svnplatform.dto.*;
import com.svnplatform.exception.BusinessException;
import com.svnplatform.service.SvnOperationService;
import com.svnplatform.task.AsyncTask;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * SVN操作控制器
 * 异常由 GlobalExceptionHandler 统一处理，Controller仅负责参数校验和路由
 */
@RestController
@RequestMapping("/api/svn")
@RequiredArgsConstructor
public class SvnOperationController {

    private final SvnOperationService svnOperationService;

    /**
     * 获取工作副本状态
     */
    @GetMapping("/{repoId}/status")
    public Result<List<Map<String, String>>> getStatus(@PathVariable Long repoId) {
        return Result.success(svnOperationService.getStatus(repoId));
    }

    /**
     * 获取文件diff
     */
    @GetMapping("/{repoId}/diff")
    public Result<String> getDiff(@PathVariable Long repoId,
                                  @RequestParam(required = false) String filePath) {
        return Result.success(svnOperationService.getDiff(repoId, filePath));
    }

    /**
     * 提交代码（异步）
     */
    @PostMapping("/{repoId}/commit")
    public Result<AsyncTask> commit(@PathVariable Long repoId,
                                    @RequestBody CommitRequest request) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new BusinessException("请填写提交说明");
        }
        return Result.success(svnOperationService.commit(repoId, request));
    }

    /**
     * 更新代码（异步）
     */
    @PostMapping("/{repoId}/update")
    public Result<AsyncTask> update(@PathVariable Long repoId,
                                    @RequestParam(required = false) Long revision) {
        return Result.success(svnOperationService.update(repoId, revision));
    }

    /**
     * 合并代码（异步）
     */
    @PostMapping("/{repoId}/merge")
    public Result<AsyncTask> merge(@PathVariable Long repoId,
                                   @RequestBody MergeRequest request) {
        return Result.success(svnOperationService.merge(repoId, request));
    }

    /**
     * 获取提交记录
     */
    @GetMapping("/{repoId}/log")
    public Result<List<Map<String, Object>>> getLog(
            @PathVariable Long repoId,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false) Long startRevision,
            @RequestParam(required = false) Long endRevision) {
        return Result.success(svnOperationService.getLog(repoId, limit, startRevision, endRevision));
    }

    /**
     * 获取提交记录（增强版，支持更多过滤）
     */
    @GetMapping("/{repoId}/log/filter")
    public Result<List<Map<String, Object>>> getLogWithFilters(
            @PathVariable Long repoId,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false) Long startRevision,
            @RequestParam(required = false) Long endRevision,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String path) {
        return Result.success(svnOperationService.getLogWithFilters(repoId, limit, startRevision, endRevision,
                author, startDate, endDate, path));
    }

    /**
     * 获取指定提交的变更文件列表
     */
    @GetMapping("/{repoId}/log/{revision}/changes")
    public Result<List<Map<String, String>>> getLogChanges(
            @PathVariable Long repoId,
            @PathVariable Long revision) {
        return Result.success(svnOperationService.getLogChanges(repoId, revision));
    }

    /**
     * 获取指定文件在指定提交中的diff内容
     */
    @GetMapping("/{repoId}/log/{revision}/diff")
    public Result<String> getLogDiff(
            @PathVariable Long repoId,
            @PathVariable Long revision,
            @RequestParam(required = false) String filePath) {
        return Result.success(svnOperationService.getLogDiff(repoId, revision, filePath));
    }

    /**
     * 获取指定文件在两个版本间的内容对比（用于Diff Editor）
     */
    @GetMapping("/{repoId}/log/{revision}/diff/file")
    public Result<Map<String, String>> getFileDiffContent(
            @PathVariable Long repoId,
            @PathVariable Long revision,
            @RequestParam String filePath) {
        return Result.success(svnOperationService.getFileDiffContent(repoId, revision, filePath));
    }

    /**
     * 还原文件
     */
    @PostMapping("/{repoId}/revert")
    public Result<String> revert(@PathVariable Long repoId,
                                 @RequestBody RevertRequest request) {
        return Result.success(svnOperationService.revert(repoId, request));
    }

    /**
     * 解决冲突
     */
    @PostMapping("/{repoId}/resolve")
    public Result<String> resolve(@PathVariable Long repoId,
                                  @RequestBody ResolveRequest request) {
        return Result.success(svnOperationService.resolve(repoId, request));
    }

    /**
     * 清理工作副本
     */
    @PostMapping("/{repoId}/cleanup")
    public Result<String> cleanup(@PathVariable Long repoId) {
        return Result.success(svnOperationService.cleanup(repoId));
    }

    /**
     * 获取仓库信息（实时）
     */
    @GetMapping("/{repoId}/info")
    public Result<Map<String, String>> getInfo(@PathVariable Long repoId) {
        return Result.success(svnOperationService.getInfo(repoId));
    }

    /**
     * 查询异步任务状态
     */
    @GetMapping("/task/{taskId}")
    public Result<AsyncTask> getTaskStatus(@PathVariable String taskId) {
        return Result.success(svnOperationService.getTaskStatus(taskId));
    }

    /**
     * 将文件添加到changelist
     */
    @PostMapping("/{repoId}/changelist/add")
    public Result<String> addToChangelist(@PathVariable Long repoId,
                                          @RequestBody ChangelistRequest request) {
        return Result.success(svnOperationService.addToChangelist(repoId, request));
    }

    /**
     * 将文件从changelist移除
     */
    @PostMapping("/{repoId}/changelist/remove")
    public Result<String> removeFromChangelist(@PathVariable Long repoId,
                                               @RequestBody ChangelistRequest request) {
        return Result.success(svnOperationService.removeFromChangelist(repoId, request));
    }

    /**
     * 重命名changelist
     */
    @PostMapping("/{repoId}/changelist/rename")
    public Result<String> renameChangelist(@PathVariable Long repoId,
                                           @RequestBody Map<String, String> body) {
        String oldName = body.get("oldName");
        String newName = body.get("newName");
        return Result.success(svnOperationService.renameChangelist(repoId, oldName, newName));
    }

    /**
     * 按changelist提交
     */
    @PostMapping("/{repoId}/commit/changelist")
    public Result<AsyncTask> commitByChangelist(@PathVariable Long repoId,
                                                @RequestBody CommitRequest request) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new BusinessException("请填写提交说明");
        }
        if (request.getChangelist() == null || request.getChangelist().trim().isEmpty()) {
            throw new BusinessException("请指定changelist名称");
        }
        return Result.success(svnOperationService.commitByChangelist(repoId, request));
    }

    /**
     * 获取文件内容（用于diff预览）
     */
    @GetMapping("/{repoId}/cat")
    public Result<Map<String, String>> getFileContent(@PathVariable Long repoId,
                                                      @RequestParam String filePath) {
        return Result.success(svnOperationService.getFileContent(repoId, filePath));
    }

    /**
     * 将未版本化文件加入版本控制（svn add）
     */
    @PostMapping("/{repoId}/add")
    public Result<String> addFiles(@PathVariable Long repoId,
                                   @RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        List<String> files = (List<String>) body.get("files");
        if (files == null || files.isEmpty()) {
            throw new BusinessException("请选择要加入版本控制的文件");
        }
        return Result.success(svnOperationService.addFiles(repoId, files));
    }

    /**
     * 获取冲突文件三方内容（BASE/MINE/THEIRS）
     */
    @GetMapping("/{repoId}/conflict")
    public Result<ConflictDetail> getConflictDetail(@PathVariable Long repoId,
                                                    @RequestParam String filePath) {
        return Result.success(svnOperationService.getConflictDetail(repoId, filePath));
    }

    /**
     * 以合并内容解决冲突
     */
    @PostMapping("/{repoId}/resolve/merge")
    public Result<String> resolveWithContent(@PathVariable Long repoId,
                                             @RequestBody MergeResolveRequest request) {
        return Result.success(svnOperationService.resolveWithContent(repoId, request));
    }

    /**
     * 列出分支或标签
     */
    @GetMapping("/{repoId}/branch-tag/list")
    public Result<List<String>> listBranchesOrTags(@PathVariable Long repoId,
                                                    @RequestParam String pathType) {
        return Result.success(svnOperationService.listBranchesOrTags(repoId, pathType));
    }

    /**
     * 获取分支列表
     */
    @GetMapping("/{repoId}/branches")
    public Result<List<String>> getBranches(@PathVariable Long repoId) {
        return Result.success(svnOperationService.getBranches(repoId));
    }

    /**
     * 获取标签列表
     */
    @GetMapping("/{repoId}/tags")
    public Result<List<String>> getTags(@PathVariable Long repoId) {
        return Result.success(svnOperationService.getTags(repoId));
    }

    /**
     * 预览合并冲突
     */
    @PostMapping("/{repoId}/merge/preview")
    public Result<AsyncTask> previewMerge(@PathVariable Long repoId,
                                          @RequestBody MergeRequest request) {
        return Result.success(svnOperationService.previewMerge(repoId, request));
    }

    /**
     * 执行合并操作
     */
    @PostMapping("/{repoId}/merge/execute")
    public Result<AsyncTask> executeMerge(@PathVariable Long repoId,
                                          @RequestBody MergeRequest request) {
        return Result.success(svnOperationService.executeMerge(repoId, request));
    }

    /**
     * 获取已合并版本列表
     */
    @GetMapping("/merge/merged-revisions")
    public Result<List<Long>> getMergedRevisions(
            @RequestParam Long sourceRepoId,
            @RequestParam Long targetRepoId) {
        return Result.success(svnOperationService.getMergedRevisions(sourceRepoId, targetRepoId));
    }

    /**
     * 创建分支或标签
     */
    @PostMapping("/{repoId}/branch-tag/create")
    public Result<String> createBranchOrTag(@PathVariable Long repoId,
                                            @RequestBody BranchTagRequest request) {
        if (request.getSourceUrl() == null || request.getSourceUrl().trim().isEmpty()) {
            throw new BusinessException("源URL不能为空");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessException("名称不能为空");
        }
        return Result.success(svnOperationService.createBranchOrTag(repoId, request.getSourceUrl(), request.getName(), request.getPathType(), request.getMessage()));
    }

    /**
     * 删除分支或标签
     */
    @PostMapping("/{repoId}/branch-tag/delete")
    public Result<String> deleteBranchOrTag(@PathVariable Long repoId,
                                            @RequestBody BranchTagRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessException("名称不能为空");
        }
        return Result.success(svnOperationService.deleteBranchOrTag(repoId, request.getName(), request.getPathType(), request.getMessage()));
    }

    /**
     * 获取操作历史记录（分页）
     */
    @GetMapping("/{repoId}/operation-logs")
    public Result<Map<String, Object>> getOperationLogs(
            @PathVariable Long repoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(svnOperationService.getOperationLogs(repoId, page, size));
    }

    /**
     * 获取最近的操作历史记录
     */
    @GetMapping("/{repoId}/operation-logs/recent")
    public Result<List<Map<String, Object>>> getRecentOperationLogs(
            @PathVariable Long repoId,
            @RequestParam(defaultValue = "10") int limit) {
        return Result.success(svnOperationService.getRecentOperationLogs(repoId, limit));
    }

    /**
     * 根据操作类型筛选操作历史记录
     */
    @GetMapping("/{repoId}/operation-logs/type/{operationType}")
    public Result<Map<String, Object>> getOperationLogsByType(
            @PathVariable Long repoId,
            @PathVariable String operationType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(svnOperationService.getOperationLogsByType(repoId, operationType, page, size));
    }

    // === 本地仓库管理 ===

    /**
     * 获取本地仓库状态
     */
    @PostMapping("/local-repo/status")
    public Result<LocalRepoStatus> getLocalRepoStatus(@RequestBody Map<String, String> body) {
        String localPath = body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        return Result.success(svnOperationService.getLocalRepoStatus(localPath));
    }

    /**
     * 获取本地仓库文件状态列表
     */
    @PostMapping("/local-repo/file-status")
    public Result<FileStatusList> getFileStatusList(@RequestBody Map<String, String> body) {
        String localPath = body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        return Result.success(svnOperationService.getFileStatusList(localPath));
    }

    /**
     * 更新本地仓库
     */
    @PostMapping("/local-repo/update")
    public Result<AsyncTask> svnUpdate(@RequestBody Map<String, Object> body) {
        String localPath = (String) body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        Long revision = null;
        if (body.get("revision") != null) {
            revision = Long.valueOf(body.get("revision").toString());
        }
        return Result.success(svnOperationService.svnUpdate(localPath, revision));
    }

    /**
     * 提交本地仓库
     */
    @PostMapping("/local-repo/commit")
    public Result<AsyncTask> svnCommit(@RequestBody Map<String, Object> body) {
        String localPath = (String) body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        String message = (String) body.get("message");
        if (message == null || message.trim().isEmpty()) {
            throw new BusinessException("请填写提交说明");
        }

        CommitRequest request = new CommitRequest();
        request.setMessage(message);
        @SuppressWarnings("unchecked")
        List<String> files = (List<String>) body.get("files");
        request.setFiles(files);
        if (body.get("autoAdd") != null) {
            request.setAutoAdd((Boolean) body.get("autoAdd"));
        }

        return Result.success(svnOperationService.svnCommit(localPath, request));
    }

    /**
     * 还原本地仓库文件
     */
    @PostMapping("/local-repo/revert")
    public Result<String> svnRevert(@RequestBody Map<String, Object> body) {
        String localPath = (String) body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        @SuppressWarnings("unchecked")
        List<String> files = (List<String>) body.get("files");
        if (files == null || files.isEmpty()) {
            throw new BusinessException("请选择要还原的文件");
        }

        RevertRequest request = new RevertRequest();
        request.setFiles(files);

        return Result.success(svnOperationService.svnRevert(localPath, request));
    }

    /**
     * 清理本地仓库
     */
    @PostMapping("/local-repo/cleanup")
    public Result<String> svnCleanup(@RequestBody Map<String, String> body) {
        String localPath = body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        return Result.success(svnOperationService.svnCleanup(localPath));
    }

    /**
     * 获取冲突文件详情
     */
    @PostMapping("/local-repo/conflict")
    public Result<ConflictDetail> getConflictDetail(@RequestBody Map<String, String> body) {
        String localPath = body.get("localPath");
        if (localPath == null || localPath.trim().isEmpty()) {
            throw new BusinessException("本地仓库路径不能为空");
        }
        String filePath = body.get("filePath");
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new BusinessException("冲突文件路径不能为空");
        }
        return Result.success(svnOperationService.getConflictDetailByPath(localPath, filePath));
    }

    /**
     * 打开本地文件夹
     */
    @PostMapping("/local-repo/open-folder")
    public Result<String> openFolder(@RequestBody Map<String, String> body) {
        String folderPath = body.get("folderPath");
        if (folderPath == null || folderPath.trim().isEmpty()) {
            throw new BusinessException("文件夹路径不能为空");
        }
        return Result.success(svnOperationService.openFolder(folderPath));
    }
}
