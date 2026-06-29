package com.svnplatform.service.impl;

import com.svnplatform.dto.*;
import com.svnplatform.entity.OperationLog;
import com.svnplatform.entity.Repository;
import com.svnplatform.exception.BusinessException;
import com.svnplatform.repository.OperationLogRepo;
import com.svnplatform.repository.RepositoryRepo;
import com.svnplatform.service.SvnOperationService;
import com.svnplatform.task.AsyncTask;
import com.svnplatform.task.TaskManager;
import com.svnplatform.util.ConflictArtifactLocator;
import com.svnplatform.util.SvnCommandExecutor;
import com.svnplatform.util.SvnCommandExecutor.SvnResult;
import com.svnplatform.util.SvnXmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SvnOperationServiceImpl implements SvnOperationService {

    private final RepositoryRepo repositoryRepo;
    private final OperationLogRepo operationLogRepo;
    private final SvnCommandExecutor svnExecutor;
    private final SvnXmlParser svnXmlParser;
    private final TaskManager taskManager;

    @Override
    public List<Map<String, String>> getStatus(Long repoId) {
        Repository repo = getRepoById(repoId);
        SvnResult result = svnExecutor.execute(repo.getLocalPath(), "status", "--xml");
        if (!result.isSuccess()) {
            throw new RuntimeException("获取状态失败: " + result.getErrorMessage());
        }
        return svnXmlParser.parseStatus(result.getOutput());
    }

    @Override
    public String getDiff(Long repoId, String filePath) {
        Repository repo = getRepoById(repoId);
        SvnResult result;
        if (filePath != null && !filePath.isEmpty()) {
            result = svnExecutor.execute(repo.getLocalPath(), "diff", filePath);
        } else {
            result = svnExecutor.execute(repo.getLocalPath(), "diff");
        }
        if (!result.isSuccess()) {
            throw new RuntimeException("获取diff失败: " + result.getErrorMessage());
        }
        return result.getOutput();
    }

    @Override
    public AsyncTask commit(Long repoId, CommitRequest request) {
        Repository repo = getRepoById(repoId);
        AsyncTask task = taskManager.createTask(repoId, "COMMIT");

        // 异步执行提交
        doCommitAsync(task.getTaskId(), repo, request);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doCommitAsync(String taskId, Repository repo, CommitRequest request) {
        try {
            String workingDir = repo.getLocalPath();

            // 如果需要自动add未版本控制的文件
            if (request.isAutoAdd() && request.getFiles() != null) {
                // 先获取状态，找出未版本控制的文件
                SvnResult statusResult = svnExecutor.execute(workingDir, "status", "--xml");
                if (statusResult.isSuccess()) {
                    List<Map<String, String>> statusList = svnXmlParser.parseStatus(statusResult.getOutput());
                    List<String> unversionedFiles = statusList.stream()
                            .filter(s -> "unversioned".equals(s.get("status")))
                            .map(s -> s.get("path"))
                            .filter(p -> request.getFiles().contains(p))
                            .collect(Collectors.toList());

                    // 执行svn add
                    for (String file : unversionedFiles) {
                        svnExecutor.execute(workingDir, "add", file);
                    }
                }
            }

            taskManager.updateProgress(taskId, 30);

            // 构建commit命令
            List<String> args = new ArrayList<>();
            args.add("commit");
            args.add("-m");
            args.add(request.getMessage());

            if (request.getFiles() != null && !request.getFiles().isEmpty()) {
                args.addAll(request.getFiles());
            }

            SvnResult result = svnExecutor.executeLong(workingDir, args.toArray(new String[0]));

            taskManager.updateProgress(taskId, 90);

            if (result.isSuccess()) {
                taskManager.completeTask(taskId, result.getOutput());
                saveLog(repo.getId(), "COMMIT", request.getMessage(), "SUCCESS", null);
            } else {
                taskManager.failTask(taskId, result.getErrorMessage());
                saveLog(repo.getId(), "COMMIT", request.getMessage(), "FAILED", result.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Commit failed", e);
            taskManager.failTask(taskId, e.getMessage());
            saveLog(repo.getId(), "COMMIT", "", "FAILED", e.getMessage());
        }
    }

    @Override
    public AsyncTask update(Long repoId, Long revision) {
        Repository repo = getRepoById(repoId);
        AsyncTask task = taskManager.createTask(repoId, "UPDATE");

        doUpdateAsync(task.getTaskId(), repo, revision);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doUpdateAsync(String taskId, Repository repo, Long revision) {
        try {
            String workingDir = repo.getLocalPath();
            taskManager.updateProgress(taskId, 10);

            List<String> args = new ArrayList<>();
            args.add("update");
            if (revision != null && revision > 0) {
                args.add("-r");
                args.add(String.valueOf(revision));
            }

            SvnResult result = svnExecutor.executeLong(workingDir, args.toArray(new String[0]));

            taskManager.updateProgress(taskId, 90);

            if (result.isSuccess()) {
                taskManager.completeTask(taskId, result.getOutput());
                saveLog(repo.getId(), "UPDATE", "更新到" + (revision != null ? "r" + revision : "HEAD"), "SUCCESS", null);

                // 更新仓库的最后版本号
                updateRepoRevision(repo);
            } else {
                taskManager.failTask(taskId, result.getErrorMessage());
                saveLog(repo.getId(), "UPDATE", "", "FAILED", result.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Update failed", e);
            taskManager.failTask(taskId, e.getMessage());
            saveLog(repo.getId(), "UPDATE", "", "FAILED", e.getMessage());
        }
    }

    @Override
    public AsyncTask merge(Long repoId, MergeRequest request) {
        Repository repo = getRepoById(repoId);
        AsyncTask task = taskManager.createTask(repoId, "MERGE");

        doMergeAsync(task.getTaskId(), repo, request);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doMergeAsync(String taskId, Repository repo, MergeRequest request) {
        try {
            String workingDir = repo.getLocalPath();
            taskManager.updateProgress(taskId, 10);

            // 优先使用 sourceRepoId 获取源仓库 URL
            String sourceUrl = request.getSourceUrl();
            if (request.getSourceRepoId() != null) {
                Repository sourceRepo = getRepoById(request.getSourceRepoId());
                sourceUrl = sourceRepo.getSvnUrl() != null && !sourceRepo.getSvnUrl().isEmpty()
                        ? sourceRepo.getSvnUrl()
                        : sourceRepo.getRepositoryRoot();
                log.info("合并操作 - 源仓库ID: {}, 源仓库URL: {}", request.getSourceRepoId(), sourceUrl);
            } else if (sourceUrl == null || sourceUrl.isEmpty()) {
                taskManager.failTask(taskId, "源仓库URL不能为空");
                return;
            }

            // 判断使用离散版本列表还是版本范围
            if (request.getRevisionList() != null && !request.getRevisionList().isEmpty()) {
                // 离散版本合并模式
                List<Long> revisions = new ArrayList<>(request.getRevisionList());
                // 按升序排序，确保合并顺序正确以避免冲突
                Collections.sort(revisions);
                log.info("使用离散版本合并模式，版本列表（已排序）: {}", revisions);
                
                // 先执行 cleanup 清理可能存在的树冲突
                log.info("合并前执行 svn cleanup 清理工作副本");
                svnExecutor.execute(workingDir, "cleanup");
                
                int totalRevisions = revisions.size();
                int completedRevisions = 0;
                StringBuilder totalOutput = new StringBuilder();

                for (Long revision : revisions) {
                    List<String> args = new ArrayList<>();
                    args.add("merge");
                    if (request.isDryRun()) {
                        args.add("--dry-run");
                    }
                    args.add("-c");
                    args.add(String.valueOf(revision));
                    args.add(sourceUrl);

                    log.info("合并命令: svn merge {} -c {} {}", 
                            request.isDryRun() ? "--dry-run" : "", revision, sourceUrl);

                    SvnResult result = svnExecutor.executeLong(workingDir, args.toArray(new String[0]));
                    
                    if (result.isSuccess()) {
                        if (result.getOutput() != null && !result.getOutput().isEmpty()) {
                            totalOutput.append("r").append(revision).append(":\n").append(result.getOutput()).append("\n");
                        }
                    } else {
                        String errorMsg = result.getErrorMessage();
                        if (request.isSkipFailedRevisions()) {
                            // 跳过失败版本，记录警告信息，继续合并后续版本
                            String skipMsg = "[跳过 r" + revision + "] " + (errorMsg != null ? errorMsg : "未知错误");
                            log.warn("跳过失败版本 r{}: {}", revision, errorMsg);
                            totalOutput.append(skipMsg).append("\n\n");
                        } else {
                            // 不跳过，直接失败返回
                            if (errorMsg != null && errorMsg.contains("tree conflict")) {
                                errorMsg = "合并 r" + revision + " 时遇到树冲突（tree conflict）。请先解决工作副本中的冲突（执行 svn cleanup 或 revert），然后再尝试合并。";
                            }
                            taskManager.failTask(taskId, "合并 r" + revision + " 失败: " + errorMsg);
                            saveLog(repo.getId(), "MERGE", "合并 r" + revision + " 失败", "FAILED", errorMsg);
                            return;
                        }
                    }

                    completedRevisions++;
                    // 更新进度：10% + (已完成版本数/总版本数) * 75%
                    int progress = 10 + (int) ((completedRevisions * 75.0) / totalRevisions);
                    taskManager.updateProgress(taskId, Math.min(progress, 85));
                }

                // 所有版本合并完成（可能有跳过的）
                String mergeOutput = totalOutput.toString().trim();
                if (mergeOutput.isEmpty()) {
                    mergeOutput = buildEmptyMergeResultMessage(revisions, request.isDryRun());
                }
                taskManager.completeTask(taskId, mergeOutput);
                if (!request.isDryRun()) {
                    saveLog(repo.getId(), "MERGE",
                            "从仓库[" + request.getSourceRepoId() + "](" + sourceUrl + ") 合并离散版本: " + revisions,
                            "SUCCESS", null);
                }
            } else if (request.getStartRevision() != null && request.getEndRevision() != null) {
                // 版本范围合并模式（向后兼容）
                List<String> args = new ArrayList<>();
                args.add("merge");

                if (request.isDryRun()) {
                    args.add("--dry-run");
                }

                args.add("-r");
                args.add(request.getStartRevision() + ":" + request.getEndRevision());
                args.add(sourceUrl);

                log.info("合并命令: svn merge {} -r {}:{} {}", request.isDryRun() ? "--dry-run" : "", 
                        request.getStartRevision(), request.getEndRevision(), sourceUrl);

                SvnResult result = svnExecutor.executeLong(workingDir, args.toArray(new String[0]));

                taskManager.updateProgress(taskId, 90);

                if (result.isSuccess()) {
                    String mergeOutput = result.getOutput();
                    if (mergeOutput == null || mergeOutput.trim().isEmpty()) {
                        mergeOutput = buildRangeMergeResultMessage(request.getStartRevision(), request.getEndRevision(), request.isDryRun());
                    }
                    taskManager.completeTask(taskId, mergeOutput);
                    if (!request.isDryRun()) {
                        saveLog(repo.getId(), "MERGE",
                                "从仓库[" + request.getSourceRepoId() + "](" + sourceUrl + ") 合并 r" + request.getStartRevision() + ":" + request.getEndRevision(),
                                "SUCCESS", null);
                    }
                } else {
                    taskManager.failTask(taskId, result.getErrorMessage());
                    saveLog(repo.getId(), "MERGE", "", "FAILED", result.getErrorMessage());
                }
            } else {
                taskManager.failTask(taskId, "合并参数错误：请提供版本范围或版本列表");
            }
        } catch (Exception e) {
            log.error("Merge failed", e);
            taskManager.failTask(taskId, e.getMessage());
            saveLog(repo.getId(), "MERGE", "", "FAILED", e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getLog(Long repoId, int limit, Long startRevision, Long endRevision) {
        Repository repo = getRepoById(repoId);

        List<String> args = new ArrayList<>();
        args.add("log");
        args.add("--xml");
        args.add("-v"); // verbose, include changed paths

        if (startRevision != null && endRevision != null) {
            args.add("-r");
            args.add(startRevision + ":" + endRevision);
        }

        args.add("-l");
        args.add(String.valueOf(limit > 0 ? limit : 50));

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("获取提交记录失败: " + result.getErrorMessage());
        }
        return svnXmlParser.parseLog(result.getOutput());
    }

    @Override
    public List<Map<String, Object>> getLogWithFilters(Long repoId, int limit, Long startRevision, Long endRevision,
                                                        String author, String startDate, String endDate, String path) {
        Repository repo = getRepoById(repoId);

        List<String> args = new ArrayList<>();
        args.add("log");
        args.add("--xml");
        args.add("-v");

        // 版本范围
        if (startRevision != null && endRevision != null) {
            args.add("-r");
            args.add(startRevision + ":" + endRevision);
        }

        // 作者过滤
        if (author != null && !author.isEmpty()) {
            args.add("--search");
            args.add(author);
        }

        // 路径过滤
        if (path != null && !path.isEmpty()) {
            args.add(path);
        }

        args.add("-l");
        args.add(String.valueOf(limit > 0 ? limit : 50));

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("获取提交记录失败: " + result.getErrorMessage());
        }

        List<Map<String, Object>> logList = svnXmlParser.parseLog(result.getOutput());

        // 客户端过滤：日期范围
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            logList = logList.stream()
                    .filter(log -> {
                        String dateStr = (String) log.get("date");
                        if (dateStr == null) return false;
                        try {
                            java.time.Instant date = java.time.Instant.parse(dateStr);
                            java.time.Instant start = java.time.Instant.parse(startDate);
                            java.time.Instant end = java.time.Instant.parse(endDate);
                            return !date.isBefore(start) && !date.isAfter(end);
                        } catch (Exception e) {
                            return true;
                        }
                    })
                    .collect(Collectors.toList());
        }

        return logList;
    }

    @Override
    public List<Map<String, String>> getLogChanges(Long repoId, Long revision) {
        Repository repo = getRepoById(repoId);

        List<String> args = new ArrayList<>();
        args.add("log");
        args.add("--xml");
        args.add("-v");
        args.add("-r");
        args.add(String.valueOf(revision));

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("获取提交变更失败: " + result.getErrorMessage());
        }

        List<Map<String, Object>> logList = svnXmlParser.parseLog(result.getOutput());
        if (logList.isEmpty()) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<Map<String, String>> paths = (List<Map<String, String>>) logList.get(0).get("paths");
        return paths != null ? paths : Collections.emptyList();
    }

    @Override
    public String getLogDiff(Long repoId, Long revision, String filePath) {
        Repository repo = getRepoById(repoId);

        // 如果没有指定文件路径，使用 svn log -v -c 获取该版本的变更摘要
        // 因为对整个仓库做diff数据量太大
        if (filePath == null || filePath.isEmpty()) {
            List<String> logArgs = new ArrayList<>();
            logArgs.add("log");
            logArgs.add("--xml");
            logArgs.add("-v");
            logArgs.add("-c");
            logArgs.add(String.valueOf(revision));

            SvnResult logResult = svnExecutor.execute(repo.getLocalPath(), logArgs.toArray(new String[0]));
            if (!logResult.isSuccess()) {
                throw new RuntimeException("获取diff失败: " + logResult.getErrorMessage());
            }

            // 解析XML获取变更文件列表
            List<Map<String, Object>> logs = svnXmlParser.parseLog(logResult.getOutput());
            if (logs.isEmpty()) {
                return "该版本无变更";
            }

            Map<String, Object> logEntry = logs.get(0);
            StringBuilder sb = new StringBuilder();
            sb.append("版本: ").append(revision).append("\n");
            sb.append("作者: ").append(logEntry.get("author")).append("\n");
            sb.append("时间: ").append(logEntry.get("date")).append("\n");
            sb.append("信息: ").append(logEntry.get("message")).append("\n");
            sb.append("\n变更文件:\n");

            @SuppressWarnings("unchecked")
            List<Map<String, String>> paths = (List<Map<String, String>>) logEntry.get("paths");
            if (paths != null) {
                for (Map<String, String> path : paths) {
                    String action = path.get("action");
                    String kind = path.get("kind");
                    String p = path.get("path");
                    String actionStr = "M".equals(action) ? "修改" : "A".equals(action) ? "新增" : "D".equals(action) ? "删除" : action;
                    sb.append("  [").append(actionStr).append("] ").append(kind).append(" ").append(p).append("\n");
                }
            }

            return sb.toString();
        }

        // 获取仓库根URL
        String repoRoot = repo.getRepositoryRoot();
        if (repoRoot == null || repoRoot.isEmpty()) {
            // 如果没缓存，从svn info获取
            Map<String, String> info = getInfo(repoId);
            repoRoot = info.get("repository-root");
        }

        // 对指定文件使用 svn diff -c REV URL
        long prevRevision = revision - 1;
        List<String> args = new ArrayList<>();
        args.add("diff");
        args.add("-r");
        args.add(prevRevision + ":" + revision);
        args.add(repoRoot + "/" + filePath);

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        // svn diff 返回退出码1表示有差异，这是正常现象
        if (!result.isSuccess() && !result.getOutput().isEmpty()) {
            return result.getOutput();
        }
        if (!result.isSuccess()) {
            throw new RuntimeException("获取diff失败: " + result.getErrorMessage());
        }
        String output = result.getOutput();
        return output != null && !output.isEmpty() ? output : "该文件在此版本无变更";
    }

    @Override
    public Map<String, String> getFileDiffContent(Long repoId, Long revision, String filePath) {
        Repository repo = getRepoById(repoId);
        Map<String, String> result = new HashMap<>();
        result.put("filePath", filePath);

        long prevRevision = revision - 1;

        // 使用 SVN URL 方式获取文件内容（因为文件可能不在本地工作副本中）
        String repoRoot = repo.getRepositoryRoot();
        if (repoRoot == null || repoRoot.isEmpty()) {
            Map<String, String> info = getInfo(repoId);
            repoRoot = info.get("repository-root");
            // 缓存回写
            if (repoRoot != null) {
                repo.setRepositoryRoot(repoRoot);
                repositoryRepo.save(repo);
            }
        }
        String fileUrl = repoRoot + "/" + (filePath.startsWith("/") ? filePath : "/" + filePath);

        SvnResult beforeResult = svnExecutor.execute(repo.getLocalPath(),
                "cat", "-r", String.valueOf(prevRevision), fileUrl);
        if (beforeResult.isSuccess()) {
            result.put("original", beforeResult.getOutput() != null ? beforeResult.getOutput() : "");
        } else {
            result.put("original", "");
        }

        SvnResult afterResult = svnExecutor.execute(repo.getLocalPath(),
                "cat", "-r", String.valueOf(revision), fileUrl);
        if (afterResult.isSuccess()) {
            result.put("modified", afterResult.getOutput() != null ? afterResult.getOutput() : "");
        } else {
            result.put("modified", "");
        }

        return result;
    }

    @Override
    public String revert(Long repoId, RevertRequest request) {
        Repository repo = getRepoById(repoId);

        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new RuntimeException("请选择要还原的文件");
        }

        List<String> args = new ArrayList<>();
        args.add("revert");
        args.addAll(request.getFiles());

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("还原失败: " + result.getErrorMessage());
        }

        saveLog(repo.getId(), "REVERT", "还原文件: " + String.join(", ", request.getFiles()), "SUCCESS", null);
        return result.getOutput();
    }

    @Override
    public String resolve(Long repoId, ResolveRequest request) {
        Repository repo = getRepoById(repoId);

        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new RuntimeException("请选择要解决冲突的文件");
        }

        List<String> args = new ArrayList<>();
        args.add("resolve");
        args.add("--accept");
        args.add(request.getAcceptStrategy());
        args.addAll(request.getFiles());

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("解决冲突失败: " + result.getErrorMessage());
        }

        saveLog(repo.getId(), "RESOLVE",
                "解决冲突(" + request.getAcceptStrategy() + "): " + String.join(", ", request.getFiles()),
                "SUCCESS", null);
        return result.getOutput();
    }

    @Override
    public String cleanup(Long repoId) {
        Repository repo = getRepoById(repoId);

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), "cleanup");
        if (!result.isSuccess()) {
            throw new RuntimeException("清理失败: " + result.getErrorMessage());
        }

        saveLog(repo.getId(), "CLEANUP", "执行cleanup", "SUCCESS", null);
        return "清理完成";
    }

    @Override
    public Map<String, String> getInfo(Long repoId) {
        Repository repo = getRepoById(repoId);

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), "info", "--xml");
        if (!result.isSuccess()) {
            throw new RuntimeException("获取信息失败: " + result.getErrorMessage());
        }
        return svnXmlParser.parseInfo(result.getOutput());
    }

    @Override
    public AsyncTask getTaskStatus(String taskId) {
        AsyncTask task = taskManager.getTask(taskId);
        if (task == null) {
            throw new BusinessException(404, "任务不存在: " + taskId);
        }
        return task;
    }

    // === Changelist操作 ===

    @Override
    public String addToChangelist(Long repoId, ChangelistRequest request) {
        Repository repo = getRepoById(repoId);

        if (request.getChangelist() == null || request.getChangelist().trim().isEmpty()) {
            throw new RuntimeException("请输入changelist名称");
        }
        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new RuntimeException("请选择要添加的文件");
        }

        List<String> args = new ArrayList<>();
        args.add("changelist");
        args.add(request.getChangelist());
        args.addAll(request.getFiles());

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("添加到changelist失败: " + result.getErrorMessage());
        }
        return result.getOutput();
    }

    @Override
    public String removeFromChangelist(Long repoId, ChangelistRequest request) {
        Repository repo = getRepoById(repoId);

        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new RuntimeException("请选择要移除的文件");
        }

        List<String> args = new ArrayList<>();
        args.add("changelist");
        args.add("--remove");
        args.addAll(request.getFiles());

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("从changelist移除失败: " + result.getErrorMessage());
        }
        return result.getOutput();
    }

    @Override
    public String renameChangelist(Long repoId, String oldName, String newName) {
        Repository repo = getRepoById(repoId);

        if (oldName == null || oldName.trim().isEmpty()) {
            throw new RuntimeException("原changelist名称不能为空");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new RuntimeException("新changelist名称不能为空");
        }

        // 先获取当前状态，找出属于该changelist的所有文件
        SvnResult statusResult = svnExecutor.execute(repo.getLocalPath(), "status", "--xml");
        if (!statusResult.isSuccess()) {
            throw new RuntimeException("获取状态失败: " + statusResult.getErrorMessage());
        }

        List<Map<String, String>> allFiles = svnXmlParser.parseStatus(statusResult.getOutput());
        List<String> changelistFiles = allFiles.stream()
                .filter(f -> oldName.equals(f.get("changelist")))
                .map(f -> f.get("path"))
                .collect(Collectors.toList());

        if (changelistFiles.isEmpty()) {
            throw new RuntimeException("changelist \"" + oldName + "\" 中没有文件");
        }

        // 将这些文件添加到新名称的changelist（SVN会自动从旧changelist移除）
        List<String> args = new ArrayList<>();
        args.add("changelist");
        args.add(newName.trim());
        args.addAll(changelistFiles);

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("重命名changelist失败: " + result.getErrorMessage());
        }
        return result.getOutput();
    }

    @Override
    public AsyncTask commitByChangelist(Long repoId, CommitRequest request) {
        Repository repo = getRepoById(repoId);
        AsyncTask task = taskManager.createTask(repoId, "COMMIT");

        doCommitByChangelistAsync(task.getTaskId(), repo, request);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doCommitByChangelistAsync(String taskId, Repository repo, CommitRequest request) {
        try {
            String workingDir = repo.getLocalPath();
            taskManager.updateProgress(taskId, 20);

            List<String> args = new ArrayList<>();
            args.add("commit");
            args.add("-m");
            args.add(request.getMessage());
            args.add("--changelist");
            args.add(request.getChangelist());

            SvnResult result = svnExecutor.executeLong(workingDir, args.toArray(new String[0]));

            taskManager.updateProgress(taskId, 90);

            if (result.isSuccess()) {
                taskManager.completeTask(taskId, result.getOutput());
                saveLog(repo.getId(), "COMMIT", "按changelist提交: " + request.getChangelist(), "SUCCESS", null);
            } else {
                taskManager.failTask(taskId, result.getErrorMessage());
                saveLog(repo.getId(), "COMMIT", "", "FAILED", result.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Commit by changelist failed", e);
            taskManager.failTask(taskId, e.getMessage());
        }
    }

    @Override
    public Map<String, String> getFileContent(Long repoId, String filePath) {
        Repository repo = getRepoById(repoId);
        Map<String, String> content = new HashMap<>();

        // 获取BASE版本内容（仓库中的原始版本）
        SvnResult baseResult = svnExecutor.execute(repo.getLocalPath(), "cat", "-r", "BASE", filePath);
        content.put("original", baseResult.isSuccess() ? baseResult.getOutput() : "");

        // 获取当前工作副本内容（通过读取文件）
        try {
            java.io.File file = new java.io.File(repo.getLocalPath(), filePath);
            if (file.exists()) {
                content.put("modified", new String(java.nio.file.Files.readAllBytes(file.toPath()), "UTF-8"));
            } else {
                content.put("modified", "");
            }
        } catch (Exception e) {
            content.put("modified", "");
            log.warn("Failed to read file content: {}", filePath, e);
        }

        content.put("filePath", filePath);
        return content;
    }

    @Override
    public String addFiles(Long repoId, List<String> files) {
        Repository repo = getRepoById(repoId);

        List<String> args = new ArrayList<>();
        args.add("add");
        args.addAll(files);

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), args.toArray(new String[0]));
        if (!result.isSuccess()) {
            throw new RuntimeException("加入版本控制失败: " + result.getErrorMessage());
        }
        return result.getOutput();
    }

    // === Private helpers ===

    @Override
    public ConflictDetail getConflictDetail(Long repoId, String filePath) {
        Repository repo = getRepoById(repoId);
        return loadConflictDetail(repo.getLocalPath(), filePath, true);
    }

    @Override
    public String resolveWithContent(Long repoId, MergeResolveRequest request) {
        Repository repo = getRepoById(repoId);
        String workingDir = repo.getLocalPath();

        if (request.getFilePath() == null || request.getFilePath().trim().isEmpty()) {
            throw new RuntimeException("请指定冲突文件路径");
        }
        if (request.getContent() == null) {
            throw new RuntimeException("请提供合并后的文件内容");
        }

        // 将合并结果写入工作副本文件
        java.io.File targetFile = new java.io.File(workingDir, request.getFilePath());
        try {
            java.nio.file.Files.write(targetFile.toPath(),
                    request.getContent().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (java.io.IOException e) {
            throw new RuntimeException("写入合并结果失败: " + e.getMessage());
        }

        // 执行 svn resolve --accept working
        SvnResult result = svnExecutor.execute(workingDir, "resolve", "--accept", "working", request.getFilePath());
        if (!result.isSuccess()) {
            throw new RuntimeException("标记解决失败: " + result.getErrorMessage());
        }

        saveLog(repo.getId(), "RESOLVE", "合并解决: " + request.getFilePath(), "SUCCESS", null);
        return result.getOutput();
    }

    /**
     * 从XML中提取指定标签的值（简单正则方式）
     */
    private String extractXmlValue(String xml, String tagName) {
        String pattern = "<" + tagName + ">([^<]*)</" + tagName + ">";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(xml);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    /**
     * 读取文件内容（UTF-8）
     */
    private String readFileContent(java.io.File file) {
        try {
            if (file.exists()) {
                return new String(java.nio.file.Files.readAllBytes(file.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            }
        } catch (java.io.IOException e) {
            log.warn("Failed to read file: {}", file.getAbsolutePath(), e);
        }
        return "";
    }

    private Repository getRepoById(Long repoId) {
        return repositoryRepo.findById(repoId)
                .orElseThrow(() -> new RuntimeException("仓库不存在，ID: " + repoId));
    }

    private Optional<Repository> findRepoByLocalPath(String localPath) {
        if (localPath == null || localPath.trim().isEmpty()) {
            return Optional.empty();
        }
        return repositoryRepo.findByLocalPath(localPath);
    }

    private void saveLocalPathLog(Repository repo, String type, String detail, String status, String errorMessage) {
        if (repo == null || repo.getId() == null) {
            return;
        }
        saveLog(repo.getId(), type, detail, status, errorMessage);
    }

    private void saveLog(Long repoId, String type, String detail, String status, String errorMessage) {
        try {
            OperationLog log = new OperationLog();
            log.setRepoId(repoId);
            log.setOperationType(type);
            log.setDetail(detail);
            log.setStatus(status);
            log.setErrorMessage(errorMessage);
            operationLogRepo.save(log);
        } catch (Exception e) {
            SvnOperationServiceImpl.log.error("Failed to save operation log", e);
        }
    }

    private void updateRepoRevision(Repository repo) {
        try {
            SvnResult infoResult = svnExecutor.execute(repo.getLocalPath(), "info", "--xml");
            if (infoResult.isSuccess()) {
                Map<String, String> info = svnXmlParser.parseInfo(infoResult.getOutput());
                String revision = info.get("entry-revision");
                if (revision != null) {
                    repo.setLastRevision(Long.parseLong(revision));
                    repositoryRepo.save(repo);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to update repo revision", e);
        }
    }

    @Override
    public List<String> listBranchesOrTags(Long repoId, String pathType) {
        Repository repo = getRepoById(repoId);
        if (!"branches".equals(pathType) && !"tags".equals(pathType)) {
            throw new RuntimeException("类型参数必须是 branches 或 tags");
        }

        // 从 svn info 获取仓库根 URL
        Map<String, String> info = getInfo(repoId);
        String repoRoot = info.get("repository-root");
        if (repoRoot == null || repoRoot.isEmpty()) {
            throw new RuntimeException("无法获取仓库根URL");
        }

        String listUrl = repoRoot.endsWith("/") ? repoRoot + pathType : repoRoot + "/" + pathType;

        SvnResult result = svnExecutor.execute(repo.getLocalPath(), "list", "--xml", listUrl);
        if (!result.isSuccess()) {
            // 如果 branches/tags 目录不存在，返回空列表
            if (result.getErrorMessage() != null && result.getErrorMessage().contains("not found")) {
                return Collections.emptyList();
            }
            throw new RuntimeException("列出" + pathType + "失败: " + result.getErrorMessage());
        }

        return parseSvnListOutput(result.getOutput());
    }

    @Override
    public List<String> getBranches(Long repoId) {
        return listBranchesOrTags(repoId, "branches");
    }

    @Override
    public List<String> getTags(Long repoId) {
        return listBranchesOrTags(repoId, "tags");
    }

    @Override
    public AsyncTask previewMerge(Long repoId, MergeRequest request) {
        request.setDryRun(true);
        return merge(repoId, request);
    }

    @Override
    public AsyncTask executeMerge(Long repoId, MergeRequest request) {
        request.setDryRun(false);
        return merge(repoId, request);
    }

    private String buildEmptyMergeResultMessage(List<Long> revisions, boolean dryRun) {
        String revisionSummary = revisions.stream()
                .map(revision -> "r" + revision)
                .collect(Collectors.joining(", "));

        if (dryRun) {
            return "预览完成：SVN 未返回文件级变更明细。\n"
                    + "本次检查版本：" + revisionSummary + "\n"
                    + "这通常表示所选版本没有可直接展示的文件改动，或当前 dry-run 未输出路径级结果。";
        }

        return "合并完成：SVN 未返回额外输出。\n"
                + "本次处理版本：" + revisionSummary;
    }

    private String buildRangeMergeResultMessage(Long startRevision, Long endRevision, boolean dryRun) {
        String range = "r" + startRevision + " 到 r" + endRevision;
        if (dryRun) {
            return "预览完成：SVN 未返回文件级变更明细。\n"
                    + "本次检查范围：" + range + "\n"
                    + "这通常表示所选范围没有可直接展示的文件改动，或当前 dry-run 未输出路径级结果。";
        }

        return "合并完成：SVN 未返回额外输出。\n"
                + "本次处理范围：" + range;
    }

    @Override
    public List<Long> getMergedRevisions(Long sourceRepoId, Long targetRepoId) {
        Repository sourceRepo = getRepoById(sourceRepoId);
        Repository targetRepo = getRepoById(targetRepoId);

        // 使用源仓库的svnUrl，如果为空则使用repositoryRoot
        String sourceUrl = sourceRepo.getSvnUrl();
        if (sourceUrl == null || sourceUrl.isEmpty()) {
            sourceUrl = sourceRepo.getRepositoryRoot();
        }
        String targetPath = targetRepo.getLocalPath();

        log.info("获取已合并版本 - 源仓库ID: {}, 源URL: {}, 目标路径: {}", sourceRepoId, sourceUrl, targetPath);
        SvnResult result = svnExecutor.execute(targetPath, "mergeinfo", "--show-revs=merged", sourceUrl);

        if (!result.isSuccess()) {
            log.warn("获取已合并版本失败: {}", result.getErrorMessage());
            return new ArrayList<>();
        }

        List<Long> revisions = new ArrayList<>();
        String output = result.getOutput();
        if (output != null && !output.trim().isEmpty()) {
            for (String line : output.split("\n")) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("r")) {
                    line = line.substring(1);
                }

                if (line.contains("-")) {
                    String[] parts = line.split("-");
                    if (parts.length == 2) {
                        try {
                            long start = Long.parseLong(parts[0].trim());
                            long end = Long.parseLong(parts[1].trim());
                            for (long r = start; r <= end; r++) {
                                revisions.add(r);
                            }
                        } catch (NumberFormatException e) {
                            log.warn("解析版本范围失败: {}", line);
                        }
                    }
                } else {
                    try {
                        revisions.add(Long.parseLong(line));
                    } catch (NumberFormatException e) {
                        log.warn("解析版本号失败: {}", line);
                    }
                }
            }
        }
        Collections.sort(revisions, Collections.reverseOrder());
        return revisions;
    }

    @Override
    public String createBranchOrTag(Long repoId, String sourceUrl, String destName, String pathType, String message) {
        Repository repo = getRepoById(repoId);
        if (!"branches".equals(pathType) && !"tags".equals(pathType)) {
            throw new RuntimeException("类型参数必须是 branches 或 tags");
        }

        if (destName == null || destName.trim().isEmpty()) {
            throw new RuntimeException("名称不能为空");
        }

        Map<String, String> info = getInfo(repoId);
        String repoRoot = info.get("repository-root");
        if (repoRoot == null || repoRoot.isEmpty()) {
            throw new RuntimeException("无法获取仓库根URL");
        }

        String destUrl = repoRoot.endsWith("/") ? repoRoot + pathType + "/" + destName : repoRoot + "/" + pathType + "/" + destName;

        SvnResult copyResult = svnExecutor.execute(repo.getLocalPath(), "copy", sourceUrl, destUrl, "-m", message != null ? message : "Create " + pathType + ": " + destName);
        if (!copyResult.isSuccess()) {
            throw new RuntimeException("创建" + pathType + "失败: " + copyResult.getErrorMessage());
        }

        saveLog(repo.getId(), "BRANCH_TAG", "创建" + pathType + ": " + destName + " (源: " + sourceUrl + ")", "SUCCESS", null);
        return "创建成功，目标: " + destUrl;
    }

    @Override
    public String deleteBranchOrTag(Long repoId, String name, String pathType, String message) {
        Repository repo = getRepoById(repoId);
        if (!"branches".equals(pathType) && !"tags".equals(pathType)) {
            throw new RuntimeException("类型参数必须是 branches 或 tags");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("名称不能为空");
        }

        Map<String, String> info = getInfo(repoId);
        String repoRoot = info.get("repository-root");
        if (repoRoot == null || repoRoot.isEmpty()) {
            throw new RuntimeException("无法获取仓库根URL");
        }

        String targetUrl = repoRoot.endsWith("/") ? repoRoot + pathType + "/" + name : repoRoot + "/" + pathType + "/" + name;

        SvnResult deleteResult = svnExecutor.execute(repo.getLocalPath(), "delete", targetUrl, "-m", message != null ? message : "Delete " + pathType + ": " + name);
        if (!deleteResult.isSuccess()) {
            throw new RuntimeException("删除" + pathType + "失败: " + deleteResult.getErrorMessage());
        }

        saveLog(repo.getId(), "BRANCH_TAG", "删除" + pathType + ": " + name, "SUCCESS", null);
        return "删除成功，目标: " + targetUrl;
    }

    /**
     * 解析 svn list --xml 输出，返回目录名称列表
     */
    private List<String> parseSvnListOutput(String xml) {
        List<String> names = new ArrayList<>();
        // 提取 <name>xxx</name> 之间的内容
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<name>([^<]*)</name>");
        java.util.regex.Matcher matcher = pattern.matcher(xml);
        while (matcher.find()) {
            String name = matcher.group(1).trim();
            if (!name.isEmpty()) {
                // 如果是目录路径，去掉末尾的 /
                if (name.endsWith("/")) {
                    name = name.substring(0, name.length() - 1);
                }
                names.add(name);
            }
        }
        return names;
    }

    @Override
    public Map<String, Object> getOperationLogs(Long repoId, int page, int size) {
        getRepoById(repoId);

        org.springframework.data.domain.Page<OperationLog> logPage =
                operationLogRepo.findByRepoIdOrderByCreatedAtDesc(repoId,
                        org.springframework.data.domain.PageRequest.of(page, size));

        List<Map<String, Object>> records = logPage.getContent().stream()
                .map(this::convertOperationLogToMap)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", logPage.getTotalElements());
        result.put("page", logPage.getNumber());
        result.put("size", logPage.getSize());
        result.put("pages", logPage.getTotalPages());

        return result;
    }

    @Override
    public List<Map<String, Object>> getRecentOperationLogs(Long repoId, int limit) {
        getRepoById(repoId);

        List<OperationLog> logs = operationLogRepo.findTop10ByRepoIdOrderByCreatedAtDesc(repoId);

        return logs.stream()
                .limit(limit)
                .map(this::convertOperationLogToMap)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getOperationLogsByType(Long repoId, String operationType, int page, int size) {
        getRepoById(repoId);

        org.springframework.data.domain.Page<OperationLog> logPage =
                operationLogRepo.findByRepoIdAndOperationTypeOrderByCreatedAtDesc(repoId, operationType,
                        org.springframework.data.domain.PageRequest.of(page, size));

        List<Map<String, Object>> records = logPage.getContent().stream()
                .map(this::convertOperationLogToMap)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("total", logPage.getTotalElements());
        result.put("page", logPage.getNumber());
        result.put("size", logPage.getSize());
        result.put("pages", logPage.getTotalPages());

        return result;
    }

    private Map<String, Object> convertOperationLogToMap(OperationLog log) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", log.getId());
        map.put("repoId", log.getRepoId());
        map.put("operationType", log.getOperationType());
        map.put("detail", log.getDetail());
        map.put("status", log.getStatus());
        map.put("errorMessage", log.getErrorMessage());
        map.put("createdAt", log.getCreatedAt() != null ? log.getCreatedAt().toString() : null);
        return map;
    }

    @Override
    public LocalRepoStatus getLocalRepoStatus(String localPath) {
        SvnResult result = svnExecutor.execute(localPath, "info", "--xml");
        if (!result.isSuccess()) {
            throw new RuntimeException("获取本地仓库状态失败: " + result.getErrorMessage());
        }

        Map<String, String> info = svnXmlParser.parseInfo(result.getOutput());

        LocalRepoStatus status = new LocalRepoStatus();
        status.setLocalPath(localPath);
        status.setUrl(info.get("url"));
        status.setRelativeUrl(info.get("relative-url"));
        status.setRepositoryRoot(info.get("repository-root"));
        status.setRepositoryUuid(info.get("repository-uuid"));
        status.setLastAuthor(info.get("last-author"));
        status.setLastDate(info.get("last-date"));

        // 解析分支信息
        String relativeUrl = info.get("relative-url");
        if (relativeUrl != null && !relativeUrl.isEmpty()) {
            if (relativeUrl.equals("^/trunk") || relativeUrl.endsWith("/trunk")) {
                status.setBranch("trunk");
            } else if (relativeUrl.contains("branches/")) {
                int idx = relativeUrl.indexOf("branches/");
                String afterBranches = relativeUrl.substring(idx + 9);
                int slashIdx = afterBranches.indexOf('/');
                status.setBranch(slashIdx > 0 ? afterBranches.substring(0, slashIdx) : afterBranches);
            } else if (relativeUrl.contains("tags/")) {
                int idx = relativeUrl.indexOf("tags/");
                String afterTags = relativeUrl.substring(idx + 5);
                int slashIdx = afterTags.indexOf('/');
                status.setBranch("tag:" + (slashIdx > 0 ? afterTags.substring(0, slashIdx) : afterTags));
            }
        }

        // 获取远程最新版本号
        SvnResult remoteResult = svnExecutor.execute(localPath, "info", "--show-item", "last-changed-revision");
        if (remoteResult.isSuccess() && remoteResult.getOutput() != null && !remoteResult.getOutput().trim().isEmpty()) {
            try {
                status.setLatestRemoteRevision(Long.parseLong(remoteResult.getOutput().trim()));
            } catch (NumberFormatException e) {
                log.warn("解析远程版本号失败: {}", remoteResult.getOutput());
            }
        }

        if (info.get("entry-revision") != null) {
            try {
                status.setRevision(Long.parseLong(info.get("entry-revision")));
            } catch (NumberFormatException e) {
                log.warn("解析版本号失败: {}", info.get("entry-revision"));
            }
        }

        // 获取文件状态以判断整体状态
        FileStatusList fileList = getFileStatusList(localPath);
        if (fileList.getConflictedCount() > 0) {
            status.setOverallStatus("conflict");
        } else if (fileList.getModifiedCount() > 0 || fileList.getAddedCount() > 0 || fileList.getDeletedCount() > 0) {
            status.setOverallStatus("modified");
        } else if (status.getLatestRemoteRevision() != null && status.getRevision() != null
                && status.getLatestRemoteRevision() > status.getRevision()) {
            status.setOverallStatus("outdated");
        } else {
            status.setOverallStatus("clean");
        }

        return status;
    }

    @Override
    public FileStatusList getFileStatusList(String localPath) {
        SvnResult result = svnExecutor.execute(localPath, "status", "--xml");
        if (!result.isSuccess()) {
            throw new RuntimeException("获取文件状态失败: " + result.getErrorMessage());
        }

        List<Map<String, String>> rawStatusList = svnXmlParser.parseStatus(result.getOutput());

        FileStatusList statusList = new FileStatusList();
        List<FileStatus> files = new ArrayList<>();

        int modifiedCount = 0;
        int addedCount = 0;
        int deletedCount = 0;
        int conflictedCount = 0;
        int unversionedCount = 0;

        for (Map<String, String> item : rawStatusList) {
            FileStatus fileStatus = new FileStatus();
            fileStatus.setPath(item.get("path"));
            fileStatus.setStatus(item.get("status"));
            fileStatus.setProps(item.get("props"));
            fileStatus.setRevision(item.get("revision"));
            fileStatus.setChangelist(item.get("changelist"));

            files.add(fileStatus);

            String status = item.get("status");
            if ("modified".equals(status)) {
                modifiedCount++;
            } else if ("added".equals(status)) {
                addedCount++;
            } else if ("deleted".equals(status)) {
                deletedCount++;
            } else if ("conflicted".equals(status) || (item.get("props") != null && item.get("props").contains("conflicted"))) {
                conflictedCount++;
            } else if ("unversioned".equals(status)) {
                unversionedCount++;
            }
        }

        statusList.setFiles(files);
        statusList.setTotalFiles(files.size());
        statusList.setModifiedCount(modifiedCount);
        statusList.setAddedCount(addedCount);
        statusList.setDeletedCount(deletedCount);
        statusList.setConflictedCount(conflictedCount);
        statusList.setUnversionedCount(unversionedCount);

        return statusList;
    }

    @Override
    public AsyncTask svnUpdate(String localPath, Long revision) {
        Repository repo = findRepoByLocalPath(localPath).orElse(null);
        AsyncTask task = taskManager.createTask(repo != null ? repo.getId() : null, "UPDATE");
        doLocalUpdateAsync(task.getTaskId(), repo, localPath, revision);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doLocalUpdateAsync(String taskId, Repository repo, String localPath, Long revision) {
        try {
            taskManager.updateProgress(taskId, 10);

            List<String> args = new ArrayList<>();
            args.add("update");
            if (revision != null && revision > 0) {
                args.add("-r");
                args.add(String.valueOf(revision));
            }

            SvnResult result = svnExecutor.executeLong(localPath, args.toArray(new String[0]));

            taskManager.updateProgress(taskId, 90);

            if (result.isSuccess()) {
                taskManager.completeTask(taskId, result.getOutput());
                saveLocalPathLog(repo, "UPDATE", "更新到" + (revision != null ? "r" + revision : "HEAD"), "SUCCESS", null);
            } else {
                taskManager.failTask(taskId, result.getErrorMessage());
                saveLocalPathLog(repo, "UPDATE", "", "FAILED", result.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Local update failed", e);
            taskManager.failTask(taskId, e.getMessage());
            saveLocalPathLog(repo, "UPDATE", "", "FAILED", e.getMessage());
        }
    }

    @Override
    public AsyncTask svnCommit(String localPath, CommitRequest request) {
        Repository repo = findRepoByLocalPath(localPath).orElse(null);
        AsyncTask task = taskManager.createTask(repo != null ? repo.getId() : null, "COMMIT");
        doLocalCommitAsync(task.getTaskId(), repo, localPath, request);
        return task;
    }

    @Async("svnTaskExecutor")
    public void doLocalCommitAsync(String taskId, Repository repo, String localPath, CommitRequest request) {
        try {
            if (request.isAutoAdd() && request.getFiles() != null) {
                SvnResult statusResult = svnExecutor.execute(localPath, "status", "--xml");
                if (statusResult.isSuccess()) {
                    List<Map<String, String>> statusList = svnXmlParser.parseStatus(statusResult.getOutput());
                    List<String> unversionedFiles = statusList.stream()
                            .filter(s -> "unversioned".equals(s.get("status")))
                            .map(s -> s.get("path"))
                            .filter(p -> request.getFiles().contains(p))
                            .collect(Collectors.toList());

                    for (String file : unversionedFiles) {
                        svnExecutor.execute(localPath, "add", file);
                    }
                }
            }

            taskManager.updateProgress(taskId, 30);

            List<String> args = new ArrayList<>();
            args.add("commit");
            args.add("-m");
            args.add(request.getMessage());

            if (request.getFiles() != null && !request.getFiles().isEmpty()) {
                args.addAll(request.getFiles());
            }

            SvnResult result = svnExecutor.executeLong(localPath, args.toArray(new String[0]));

            taskManager.updateProgress(taskId, 90);

            if (result.isSuccess()) {
                taskManager.completeTask(taskId, result.getOutput());
                saveLocalPathLog(repo, "COMMIT", request.getMessage(), "SUCCESS", null);
            } else {
                taskManager.failTask(taskId, result.getErrorMessage());
                saveLocalPathLog(repo, "COMMIT", request.getMessage(), "FAILED", result.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("Local commit failed", e);
            taskManager.failTask(taskId, e.getMessage());
            saveLocalPathLog(repo, "COMMIT", request != null ? request.getMessage() : "", "FAILED", e.getMessage());
        }
    }

    @Override
    public String svnRevert(String localPath, RevertRequest request) {
        if (request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new RuntimeException("请选择要还原的文件");
        }

        List<String> args = new ArrayList<>();
        args.add("revert");
        args.addAll(request.getFiles());

        Repository repo = findRepoByLocalPath(localPath).orElse(null);
        SvnResult result = svnExecutor.execute(localPath, args.toArray(new String[0]));
        if (!result.isSuccess()) {
            saveLocalPathLog(repo, "REVERT", "还原文件: " + String.join(", ", request.getFiles()), "FAILED", result.getErrorMessage());
            throw new RuntimeException("还原失败: " + result.getErrorMessage());
        }

        saveLocalPathLog(repo, "REVERT", "还原文件: " + String.join(", ", request.getFiles()), "SUCCESS", null);
        return result.getOutput();
    }

    @Override
    public String svnCleanup(String localPath) {
        Repository repo = findRepoByLocalPath(localPath).orElse(null);
        SvnResult result = svnExecutor.execute(localPath, "cleanup");
        if (!result.isSuccess()) {
            saveLocalPathLog(repo, "CLEANUP", "执行cleanup", "FAILED", result.getErrorMessage());
            throw new RuntimeException("清理失败: " + result.getErrorMessage());
        }

        saveLocalPathLog(repo, "CLEANUP", "执行cleanup", "SUCCESS", null);
        return "清理完成";
    }

    @Override
    public ConflictDetail getConflictDetailByPath(String localPath, String filePath) {
        return loadConflictDetail(localPath, filePath, false);
    }

    private ConflictDetail loadConflictDetail(String localPath, String filePath, boolean failWhenInfoUnavailable) {
        ConflictDetail detail = new ConflictDetail();
        detail.setFilePath(filePath);

        SvnResult infoResult = svnExecutor.execute(localPath, "info", "--xml", filePath);
        java.io.File baseDir = new java.io.File(localPath);
        String mineFile = null;
        String baseFile = null;
        String theirsFile = null;

        if (infoResult.isSuccess()) {
            String infoXml = infoResult.getOutput();
            mineFile = resolveConflictArtifactPath(infoXml, "prev-wc-file");
            baseFile = resolveConflictArtifactPath(infoXml, "prev-base-file");
            theirsFile = resolveConflictArtifactPath(infoXml, "cur-base-file");
        } else if (failWhenInfoUnavailable) {
            throw new RuntimeException("获取文件信息失败: " + infoResult.getErrorMessage());
        }

        java.io.File mineArtifact = ConflictArtifactLocator.resolveMineFile(baseDir, filePath, mineFile);
        if (mineArtifact != null) {
            detail.setMine(readFileContent(mineArtifact));
        } else {
            SvnResult catResult = svnExecutor.execute(localPath, "cat", "-r", "BASE", filePath);
            detail.setMine(catResult.isSuccess() ? catResult.getOutput() : "");
        }

        java.io.File baseArtifact = ConflictArtifactLocator.resolveBaseFile(baseDir, filePath, baseFile);
        if (baseArtifact != null) {
            detail.setBase(readFileContent(baseArtifact));
        } else {
            SvnResult catResult = svnExecutor.execute(localPath, "cat", "-r", "BASE", filePath);
            detail.setBase(catResult.isSuccess() ? catResult.getOutput() : "");
        }

        java.io.File theirsArtifact = ConflictArtifactLocator.resolveTheirsFile(baseDir, filePath, theirsFile);
        detail.setTheirs(theirsArtifact != null ? readFileContent(theirsArtifact) : "");

        java.io.File mergedFile = ConflictArtifactLocator.resolveMergedFile(baseDir, filePath);
        detail.setMerged(mergedFile != null ? readFileContent(mergedFile) : "");

        return detail;
    }

    private String resolveConflictArtifactPath(String infoXml, String tagName) {
        String explicitValue = extractXmlValue(infoXml, tagName);
        if (explicitValue != null && !explicitValue.isEmpty()) {
            return explicitValue;
        }
        return extractConflictFilePath(infoXml, tagName);
    }

    /**
     * 从XML中提取冲突文件路径（支持属性格式）
     * SVN info XML中冲突文件可能是：<prev-wc-file path="..."/> 或 <prev-wc-file>...</prev-wc-file>
     */
    private String extractConflictFilePath(String xml, String tagName) {
        // 尝试匹配 <tagName path="..."/>
        java.util.regex.Pattern attrPattern = java.util.regex.Pattern.compile(
            "<" + tagName + "\\s+path=\"([^\"]+)\"");
        java.util.regex.Matcher attrMatcher = attrPattern.matcher(xml);
        if (attrMatcher.find()) {
            return attrMatcher.group(1).trim();
        }
        
        // 尝试匹配 <tagName>...</tagName>
        java.util.regex.Pattern tagPattern = java.util.regex.Pattern.compile(
            "<" + tagName + ">([^<]*)</" + tagName + ">");
        java.util.regex.Matcher tagMatcher = tagPattern.matcher(xml);
        return tagMatcher.find() ? tagMatcher.group(1).trim() : null;
    }

    @Override
    public String openFolder(String folderPath) {
        if (folderPath == null || folderPath.isEmpty()) {
            throw new RuntimeException("文件夹路径不能为空");
        }

        java.io.File folder = new java.io.File(folderPath);
        if (!folder.exists()) {
            throw new RuntimeException("文件夹不存在: " + folderPath);
        }

        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Windows: 使用 explorer 打开
                Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start", "", "\"" + folderPath + "\""});
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // macOS: 使用 open 打开
                Runtime.getRuntime().exec(new String[]{"open", folderPath});
            } else {
                // Linux: 使用 xdg-open 打开
                Runtime.getRuntime().exec(new String[]{"xdg-open", folderPath});
            }
            return "已打开文件夹: " + folderPath;
        } catch (Exception e) {
            throw new RuntimeException("打开文件夹失败: " + e.getMessage());
        }
    }
}
