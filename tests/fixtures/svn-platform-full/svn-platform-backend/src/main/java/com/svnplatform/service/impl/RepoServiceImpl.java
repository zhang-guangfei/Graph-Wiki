package com.svnplatform.service.impl;

import com.svnplatform.dto.RepoImportRequest;
import com.svnplatform.entity.Repository;
import com.svnplatform.repository.RepositoryRepo;
import com.svnplatform.service.RepoService;
import com.svnplatform.util.SvnCommandExecutor;
import com.svnplatform.util.SvnXmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService {

    private final RepositoryRepo repositoryRepo;
    private final SvnCommandExecutor svnExecutor;
    private final SvnXmlParser svnXmlParser;

    @Override
    public Repository importRepo(RepoImportRequest request) {
        String localPath = normalizePath(request.getLocalPath());

        // 检查是否已导入
        if (repositoryRepo.existsByLocalPath(localPath)) {
            throw new RuntimeException("该仓库已存在于管理列表中");
        }

        // 验证路径
        Map<String, String> info = validatePath(localPath);
        if (info.isEmpty()) {
            throw new RuntimeException("该路径不是有效的SVN工作副本");
        }

        // 创建仓库记录
        Repository repo = new Repository();
        repo.setLocalPath(localPath);
        repo.setName(request.getName() != null && !request.getName().isEmpty()
                ? request.getName()
                : new File(localPath).getName());
        repo.setSvnUrl(info.get("url"));
        repo.setRepositoryRoot(info.get("repository-root"));
        repo.setRepositoryUuid(info.get("repository-uuid"));

        String entryRevision = info.get("entry-revision");
        if (entryRevision != null) {
            try {
                repo.setLastRevision(Long.parseLong(entryRevision));
            } catch (NumberFormatException e) {
                repo.setLastRevision(0L);
            }
        }

        return repositoryRepo.save(repo);
    }

    @Override
    public List<Repository> listRepos() {
        return repositoryRepo.findAll();
    }

    @Override
    public Repository getRepo(Long id) {
        return repositoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("仓库不存在，ID: " + id));
    }

    @Override
    public void deleteRepo(Long id) {
        if (!repositoryRepo.existsById(id)) {
            throw new RuntimeException("仓库不存在，ID: " + id);
        }
        repositoryRepo.deleteById(id);
    }

    @Override
    public Repository renameRepo(Long id, String newName) {
        Repository repo = repositoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("仓库不存在，ID: " + id));
        repo.setName(newName);
        return repositoryRepo.save(repo);
    }

    @Override
    public Map<String, String> validatePath(String localPath) {
        String path = normalizePath(localPath);

        // 检查路径是否存在
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("路径不存在: " + path);
        }

        // 检查.svn目录：当前目录或向上查找父目录
        boolean foundSvn = false;
        File checkDir = dir;
        while (checkDir != null) {
            File svnDir = new File(checkDir, ".svn");
            if (svnDir.exists() && svnDir.isDirectory()) {
                foundSvn = true;
                break;
            }
            checkDir = checkDir.getParentFile();
        }
        if (!foundSvn) {
            throw new RuntimeException("该路径不是有效的SVN工作副本（未找到.svn目录）");
        }

        // 执行svn info获取仓库信息（svn info可以在任何子目录中执行）
        SvnCommandExecutor.SvnResult result = svnExecutor.execute(path, "info", "--xml");
        if (!result.isSuccess()) {
            throw new RuntimeException("SVN信息获取失败: " + result.getErrorMessage());
        }

        return svnXmlParser.parseInfo(result.getOutput());
    }

    @Override
    public String browseFolder() {
        try {
            // 使用PowerShell调用Windows原生文件夹选择对话框
            String psScript = "Add-Type -AssemblyName System.Windows.Forms; " +
                    "$dialog = New-Object System.Windows.Forms.FolderBrowserDialog; " +
                    "$dialog.Description = '选择SVN工作副本目录'; " +
                    "$dialog.ShowNewFolderButton = $false; " +
                    "$form = New-Object System.Windows.Forms.Form; " +
                    "$form.TopMost = $true; " +
                    "$result = $dialog.ShowDialog($form); " +
                    "if ($result -eq [System.Windows.Forms.DialogResult]::OK) { " +
                    "  Write-Output $dialog.SelectedPath " +
                    "}";

            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-NoProfile", "-Command", psScript);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream(), "GBK"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();

            String path = output.toString().trim();
            if (path.isEmpty()) {
                return null;
            }
            return path;
        } catch (Exception e) {
            throw new RuntimeException("打开文件夹选择器失败: " + e.getMessage());
        }
    }

    private String normalizePath(String path) {
        if (path == null) return "";
        // 统一路径分隔符，去除末尾分隔符
        return path.replace("/", File.separator)
                .replace("\\", File.separator)
                .replaceAll("[/\\\\]$", "");
    }
}
