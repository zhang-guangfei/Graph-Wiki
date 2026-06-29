package com.svnplatform.controller;

import com.svnplatform.dto.RepoImportRequest;
import com.svnplatform.dto.Result;
import com.svnplatform.exception.BusinessException;
import com.svnplatform.entity.Repository;
import com.svnplatform.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 仓库管理控制器
 * 异常由 GlobalExceptionHandler 统一处理
 */
@RestController
@RequestMapping("/api/repo")
@RequiredArgsConstructor
public class RepoController {

    private final RepoService repoService;

    /**
     * 导入本地SVN仓库
     */
    @PostMapping("/import")
    public Result<Repository> importRepo(@RequestBody RepoImportRequest request) {
        Repository repo = repoService.importRepo(request);
        return Result.success(repo);
    }

    /**
     * 获取所有仓库列表
     */
    @GetMapping("/list")
    public Result<List<Repository>> listRepos() {
        return Result.success(repoService.listRepos());
    }

    /**
     * 获取仓库详情
     */
    @GetMapping("/{id}")
    public Result<Repository> getRepo(@PathVariable Long id) {
        return Result.success(repoService.getRepo(id));
    }

    /**
     * 删除仓库记录
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRepo(@PathVariable Long id) {
        repoService.deleteRepo(id);
        return Result.success();
    }

    /**
     * 修改仓库名称
     */
    @PutMapping("/{id}/rename")
    public Result<Repository> renameRepo(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        if (newName == null || newName.trim().isEmpty()) {
            throw new BusinessException("仓库名称不能为空");
        }
        Repository repo = repoService.renameRepo(id, newName.trim());
        return Result.success(repo);
    }

    /**
     * 验证路径是否为有效SVN工作副本
     */
    @GetMapping("/validate")
    public Result<Map<String, String>> validatePath(@RequestParam String path) {
        Map<String, String> info = repoService.validatePath(path);
        return Result.success(info);
    }

    /**
     * 打开系统文件夹选择对话框
     */
    @PostMapping("/browse")
    public Result<String> browseFolder() {
        String path = repoService.browseFolder();
        if (path == null || path.isEmpty()) {
            throw new BusinessException("未选择文件夹");
        }
        return Result.success(path);
    }
}
