package com.svnplatform.repository;

import com.svnplatform.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 需求数据访问接口
 */
@Repository
public interface RequirementRepo extends JpaRepository<Requirement, String> {

    /**
     * 根据需求ID模糊搜索
     */
    List<Requirement> findByRequirementIdContaining(String requirementId);

    /**
     * 根据需求标题模糊搜索
     */
    List<Requirement> findByTitleContaining(String title);

    /**
     * 根据需求ID精确查找
     */
    Optional<Requirement> findByRequirementId(String requirementId);
}
