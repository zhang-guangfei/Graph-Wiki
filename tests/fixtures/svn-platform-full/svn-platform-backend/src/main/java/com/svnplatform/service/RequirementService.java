package com.svnplatform.service;

import com.svnplatform.entity.Requirement;

import java.util.List;

/**
 * 需求管理服务接口
 */
public interface RequirementService {

    /**
     * 创建需求
     */
    Requirement createRequirement(Requirement requirement);

    /**
     * 更新需求
     */
    Requirement updateRequirement(Requirement requirement);

    /**
     * 删除需求
     */
    void deleteRequirement(String requirementId);

    /**
     * 获取所有需求列表
     */
    List<Requirement> listAll();

    /**
     * 根据需求ID搜索
     */
    List<Requirement> searchById(String keyword);

    /**
     * 根据需求标题搜索
     */
    List<Requirement> searchByTitle(String keyword);

    /**
     * 根据需求ID精确查找
     */
    Requirement getById(String requirementId);

    /**
     * 创建或更新需求（如果需求号已存在则更新）
     */
    Requirement createOrUpdateRequirement(Requirement requirement);

    /**
     * 批量创建或更新需求
     */
    List<Requirement> batchCreateOrUpdateRequirements(List<Requirement> requirements);
}
