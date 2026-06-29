package com.svnplatform.task;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步任务管理器
 * 维护任务状态，支持轮询查询
 */
@Component
public class TaskManager {

    /**
     * 存储所有任务，key=taskId
     */
    private final Map<String, AsyncTask> tasks = new ConcurrentHashMap<>();

    /**
     * 注册新任务
     */
    public AsyncTask createTask(Long repoId, String type) {
        AsyncTask task = AsyncTask.create(repoId, type);
        tasks.put(task.getTaskId(), task);
        return task;
    }

    /**
     * 获取任务状态
     */
    public AsyncTask getTask(String taskId) {
        return tasks.get(taskId);
    }

    /**
     * 更新任务进度
     */
    public void updateProgress(String taskId, int progress) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.setProgress(progress);
        }
    }

    /**
     * 标记任务完成
     */
    public void completeTask(String taskId, String result) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.complete(result);
        }
    }

    /**
     * 标记任务失败
     */
    public void failTask(String taskId, String errorMessage) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.fail(errorMessage);
        }
    }

    /**
     * 清理已完成/失败超过1小时的任务
     */
    public void cleanup() {
        tasks.entrySet().removeIf(entry -> {
            AsyncTask task = entry.getValue();
            if (task.getEndTime() != null) {
                return task.getEndTime().plusHours(1).isBefore(java.time.LocalDateTime.now());
            }
            return false;
        });
    }
}
