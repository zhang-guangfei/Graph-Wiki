package com.svnplatform.service.impl;

import com.svnplatform.dto.RevertRequest;
import com.svnplatform.dto.CommitRequest;
import com.svnplatform.entity.OperationLog;
import com.svnplatform.entity.Repository;
import com.svnplatform.exception.BusinessException;
import com.svnplatform.repository.OperationLogRepo;
import com.svnplatform.repository.RepositoryRepo;
import com.svnplatform.task.AsyncTask;
import com.svnplatform.task.TaskManager;
import com.svnplatform.util.SvnCommandExecutor;
import com.svnplatform.util.SvnXmlParser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SvnOperationServiceImplLocalRepoLogTest {

    private final RepositoryRepo repositoryRepo = mock(RepositoryRepo.class);
    private final OperationLogRepo operationLogRepo = mock(OperationLogRepo.class);
    private final SvnCommandExecutor svnExecutor = mock(SvnCommandExecutor.class);
    private final SvnXmlParser svnXmlParser = mock(SvnXmlParser.class);
    private final TaskManager taskManager = new TaskManager();

    private final SvnOperationServiceImpl service = new SvnOperationServiceImpl(
            repositoryRepo,
            operationLogRepo,
            svnExecutor,
            svnXmlParser,
            taskManager
    );

    @Test
    void localCleanupWritesOperationLogWhenPathIsRegistered() {
        String localPath = "D:/work/repo";
        Repository repo = new Repository();
        repo.setId(42L);
        repo.setLocalPath(localPath);

        when(repositoryRepo.findByLocalPath(localPath)).thenReturn(Optional.of(repo));
        when(svnExecutor.execute(eq(localPath), eq("cleanup")))
                .thenReturn(SvnCommandExecutor.SvnResult.success("cleaned"));

        service.svnCleanup(localPath);

        ArgumentCaptor<OperationLog> logCaptor = ArgumentCaptor.forClass(OperationLog.class);
        verify(operationLogRepo).save(logCaptor.capture());

        OperationLog log = logCaptor.getValue();
        assertEquals(42L, log.getRepoId());
        assertEquals("CLEANUP", log.getOperationType());
        assertEquals("SUCCESS", log.getStatus());
    }

    @Test
    void localUpdateCreatesRepoScopedTaskAndWritesOperationLogWhenPathIsRegistered() {
        String localPath = "D:/work/repo";
        Repository repo = new Repository();
        repo.setId(44L);
        repo.setLocalPath(localPath);

        when(repositoryRepo.findByLocalPath(localPath)).thenReturn(Optional.of(repo));
        when(svnExecutor.executeLong(eq(localPath), eq("update")))
                .thenReturn(SvnCommandExecutor.SvnResult.success("updated"));

        AsyncTask task = service.svnUpdate(localPath, null);

        ArgumentCaptor<OperationLog> logCaptor = ArgumentCaptor.forClass(OperationLog.class);
        verify(operationLogRepo).save(logCaptor.capture());

        assertEquals(44L, task.getRepoId());
        assertEquals("COMPLETED", task.getStatus());
        OperationLog log = logCaptor.getValue();
        assertEquals(44L, log.getRepoId());
        assertEquals("UPDATE", log.getOperationType());
        assertEquals("更新到HEAD", log.getDetail());
        assertEquals("SUCCESS", log.getStatus());
    }

    @Test
    void localCommitCreatesRepoScopedTaskAndWritesOperationLogWhenPathIsRegistered() {
        String localPath = "D:/work/repo";
        Repository repo = new Repository();
        repo.setId(45L);
        repo.setLocalPath(localPath);

        CommitRequest request = new CommitRequest();
        request.setMessage("commit message");
        request.setFiles(Collections.singletonList("src/App.java"));
        request.setAutoAdd(false);

        when(repositoryRepo.findByLocalPath(localPath)).thenReturn(Optional.of(repo));
        when(svnExecutor.executeLong(eq(localPath), eq("commit"), eq("-m"), eq("commit message"), eq("src/App.java")))
                .thenReturn(SvnCommandExecutor.SvnResult.success("committed"));

        AsyncTask task = service.svnCommit(localPath, request);

        ArgumentCaptor<OperationLog> logCaptor = ArgumentCaptor.forClass(OperationLog.class);
        verify(operationLogRepo).save(logCaptor.capture());

        assertEquals(45L, task.getRepoId());
        assertEquals("COMPLETED", task.getStatus());
        OperationLog log = logCaptor.getValue();
        assertEquals(45L, log.getRepoId());
        assertEquals("COMMIT", log.getOperationType());
        assertEquals("commit message", log.getDetail());
        assertEquals("SUCCESS", log.getStatus());
    }

    @Test
    void localRevertWritesOperationLogWhenPathIsRegistered() {
        String localPath = "D:/work/repo";
        Repository repo = new Repository();
        repo.setId(43L);
        repo.setLocalPath(localPath);

        RevertRequest request = new RevertRequest();
        request.setFiles(Collections.singletonList("src/App.java"));

        when(repositoryRepo.findByLocalPath(localPath)).thenReturn(Optional.of(repo));
        when(svnExecutor.execute(eq(localPath), eq("revert"), eq("src/App.java")))
                .thenReturn(SvnCommandExecutor.SvnResult.success("reverted"));

        service.svnRevert(localPath, request);

        ArgumentCaptor<OperationLog> logCaptor = ArgumentCaptor.forClass(OperationLog.class);
        verify(operationLogRepo).save(logCaptor.capture());

        OperationLog log = logCaptor.getValue();
        assertEquals(43L, log.getRepoId());
        assertEquals("REVERT", log.getOperationType());
        assertEquals("还原文件: src/App.java", log.getDetail());
        assertEquals("SUCCESS", log.getStatus());
    }

    @Test
    void getTaskStatusReturnsBusinessErrorWhenTaskDoesNotExist() {
        BusinessException error = assertThrows(
                BusinessException.class,
                () -> service.getTaskStatus("missing-task-id")
        );

        assertEquals(404, error.getCode());
        assertEquals("任务不存在: missing-task-id", error.getMessage());
    }
}
