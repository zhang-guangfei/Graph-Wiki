package com.svnplatform.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 异步任务信息
 */
@Data
public class AsyncTask {

    private String taskId;
    private Long repoId;
    private String type; // UPDATE, MERGE, COMMIT
    private String status; // RUNNING, COMPLETED, FAILED
    private String result;
    private String errorMessage;
    private int progress; // 0-100
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static AsyncTask create(Long repoId, String type) {
        AsyncTask task = new AsyncTask();
        task.setTaskId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        task.setRepoId(repoId);
        task.setType(type);
        task.setStatus("RUNNING");
        task.setProgress(0);
        task.setStartTime(LocalDateTime.now());
        return task;
    }

    public void complete(String result) {
        this.status = "COMPLETED";
        this.result = result;
        this.progress = 100;
        this.endTime = LocalDateTime.now();
    }

    public void fail(String errorMessage) {
        this.status = "FAILED";
        this.errorMessage = errorMessage;
        this.endTime = LocalDateTime.now();
    }
}
