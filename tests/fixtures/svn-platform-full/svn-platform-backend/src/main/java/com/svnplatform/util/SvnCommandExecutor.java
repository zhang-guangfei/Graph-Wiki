package com.svnplatform.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * SVN命令执行工具类
 * 通过ProcessBuilder调用本地svn命令行
 * 支持可配置超时、流式输出读取、分级超时策略
 */
@Slf4j
@Component
public class SvnCommandExecutor {

    @Value("${svn.executable:svn}")
    private String svnExecutable;

    @Value("${svn.timeout:60000}")
    private long defaultTimeout;

    @Value("${svn.timeout.long:300000}")
    private long longTimeout;

    @Value("${svn.encoding:UTF-8}")
    private String encoding;

    /**
     * 执行SVN命令并返回结果（使用默认超时）
     *
     * @param workingDir 工作目录
     * @param args       SVN命令参数
     * @return 命令执行结果
     */
    public SvnResult execute(String workingDir, String... args) {
        return execute(workingDir, defaultTimeout, args);
    }

    /**
     * 执行SVN命令并返回结果（使用长超时，适用于commit/update/merge等）
     */
    public SvnResult executeLong(String workingDir, String... args) {
        return execute(workingDir, longTimeout, args);
    }

    /**
     * 执行SVN命令并返回结果（自定义超时）
     *
     * @param workingDir    工作目录
     * @param timeoutMillis 超时时间（毫秒）
     * @param args          SVN命令参数
     * @return 命令执行结果
     */
    public SvnResult execute(String workingDir, long timeoutMillis, String... args) {
        List<String> command = new ArrayList<>();
        command.add(svnExecutable);
        for (String arg : args) {
            command.add(arg);
        }

        String cmdStr = String.join(" ", command);
        log.debug("Executing SVN command: {} in directory: {} (timeout: {}ms)", cmdStr, workingDir, timeoutMillis);

        ProcessBuilder pb = new ProcessBuilder(command);
        if (workingDir != null) {
            pb.directory(new File(workingDir));
        }
        pb.redirectErrorStream(false);

        Process process = null;
        try {
            process = pb.start();

            // 使用独立线程读取stdout/stderr防止大输出导致的管道死锁
            Future<String> stdoutFuture = readStreamAsync(process.getInputStream());
            Future<String> stderrFuture = readStreamAsync(process.getErrorStream());

            boolean finished = process.waitFor(timeoutMillis, TimeUnit.MILLISECONDS);
            if (!finished) {
                process.destroyForcibly();
                process.waitFor(5, TimeUnit.SECONDS); // 等待进程彻底退出
                log.warn("SVN command timed out after {}ms: {}", timeoutMillis, cmdStr);
                return SvnResult.timeout(cmdStr, timeoutMillis);
            }

            String stdout = getStreamResult(stdoutFuture);
            String stderr = getStreamResult(stderrFuture);

            int exitCode = process.exitValue();
            if (exitCode == 0) {
                return SvnResult.success(stdout);
            } else {
                log.warn("SVN command failed with exit code {}: {}", exitCode, stderr);
                return SvnResult.error(stderr.isEmpty() ? "命令执行失败，退出码：" + exitCode : stderr);
            }
        } catch (IOException e) {
            log.error("Failed to execute SVN command: {}", cmdStr, e);
            return SvnResult.error("无法执行SVN命令，请确认SVN已正确安装：" + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (process != null) process.destroyForcibly();
            return SvnResult.error("命令执行被中断");
        }
    }

    /**
     * 执行SVN命令并返回XML格式结果
     */
    public SvnResult executeXml(String workingDir, String... args) {
        String[] xmlArgs = new String[args.length + 1];
        xmlArgs[0] = args[0]; // svn子命令
        xmlArgs[1] = "--xml";
        System.arraycopy(args, 1, xmlArgs, 2, args.length - 1);
        return execute(workingDir, xmlArgs);
    }

    /**
     * 异步读取流内容（防止管道缓冲区满导致死锁）
     */
    private Future<String> readStreamAsync(InputStream inputStream) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            try {
                return readStream(inputStream);
            } finally {
                executor.shutdown();
            }
        });
    }

    private String getStreamResult(Future<String> future) {
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn("Failed to read stream result", e);
            return "";
        }
    }

    private String readStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName(encoding)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString().trim();
    }

    /**
     * SVN命令执行结果
     */
    public static class SvnResult {
        private boolean success;
        private boolean timedOut;
        private String output;
        private String errorMessage;

        public static SvnResult success(String output) {
            SvnResult result = new SvnResult();
            result.success = true;
            result.output = output;
            return result;
        }

        public static SvnResult error(String errorMessage) {
            SvnResult result = new SvnResult();
            result.success = false;
            result.errorMessage = errorMessage;
            return result;
        }

        public static SvnResult timeout(String command, long timeoutMs) {
            SvnResult result = new SvnResult();
            result.success = false;
            result.timedOut = true;
            result.errorMessage = String.format("命令执行超时（%d秒），请检查网络连接或仓库状态。命令: %s",
                    timeoutMs / 1000, command);
            return result;
        }

        public boolean isSuccess() {
            return success;
        }

        public boolean isTimedOut() {
            return timedOut;
        }

        public String getOutput() {
            return output;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
