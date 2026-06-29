package com.svnplatform.controller;

import com.svnplatform.dto.Result;
import com.svnplatform.entity.Requirement;
import com.svnplatform.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 需求管理控制器
 */
@RestController
@RequestMapping("/api/requirement")
@RequiredArgsConstructor
public class RequirementController {

    private final RequirementService requirementService;

    /**
     * 获取所有需求列表
     */
    @GetMapping("/list")
    public Result<List<Requirement>> listAll() {
        return Result.success(requirementService.listAll());
    }

    /**
     * 根据关键词搜索（支持需求ID和标题）
     */
    @GetMapping("/search")
    public Result<List<Requirement>> search(@RequestParam(required = false) String keyword) {
        List<Requirement> byId = requirementService.searchById(keyword);
        List<Requirement> byTitle = requirementService.searchByTitle(keyword);
        // 合并去重
        java.util.Set<Requirement> combined = new java.util.LinkedHashSet<>(byId);
        combined.addAll(byTitle);
        return Result.success(combined.stream().collect(java.util.stream.Collectors.toList()));
    }

    /**
     * 根据需求ID精确查找
     */
    @GetMapping("/{requirementId}")
    public Result<Requirement> getById(@PathVariable String requirementId) {
        return Result.success(requirementService.getById(requirementId));
    }

    /**
     * 创建需求
     */
    @PostMapping
    public Result<Requirement> create(@RequestBody Requirement requirement) {
        return Result.success(requirementService.createRequirement(requirement));
    }

    /**
     * 更新需求
     */
    @PutMapping("/{requirementId}")
    public Result<Requirement> update(@PathVariable String requirementId,
                                      @RequestBody Requirement requirement) {
        requirement.setRequirementId(requirementId);
        return Result.success(requirementService.updateRequirement(requirement));
    }

    /**
     * 创建或更新需求（如果需求号已存在则更新）
     */
    @PostMapping("/createOrUpdate")
    public Result<Requirement> createOrUpdate(@RequestBody Requirement requirement) {
        return Result.success(requirementService.createOrUpdateRequirement(requirement));
    }

    /**
     * 批量创建或更新需求
     */
    @PostMapping("/batch")
    public Result<List<Requirement>> batchCreateOrUpdate(@RequestBody List<Requirement> requirements) {
        return Result.success(requirementService.batchCreateOrUpdateRequirements(requirements));
    }

    /**
     * 删除需求
     */
    @DeleteMapping("/{requirementId}")
    public Result<Void> delete(@PathVariable String requirementId) {
        requirementService.deleteRequirement(requirementId);
        return Result.success();
    }
}
