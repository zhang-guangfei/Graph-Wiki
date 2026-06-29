package com.svnplatform.service.impl;

import com.svnplatform.entity.Requirement;
import com.svnplatform.repository.RequirementRepo;
import com.svnplatform.service.RequirementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepo requirementRepo;

    @Override
    public Requirement createRequirement(Requirement requirement) {
        if (requirement.getRequirementId() == null || requirement.getRequirementId().trim().isEmpty()) {
            throw new RuntimeException("需求ID不能为空");
        }
        requirement.setRequirementId(requirement.getRequirementId().trim());
        if (requirementRepo.findByRequirementId(requirement.getRequirementId()).isPresent()) {
            throw new RuntimeException("需求ID已存在: " + requirement.getRequirementId());
        }
        requirement.setTitle(requirement.getTitle() != null ? requirement.getTitle().trim() : "");
        requirement.setDescription(requirement.getDescription() != null ? requirement.getDescription().trim() : "");
        requirement.setType(requirement.getType() != null ? requirement.getType().trim() : null);
        requirement.setFormat(requirement.getFormat() != null ? requirement.getFormat().trim() : null);
        return requirementRepo.save(requirement);
    }

    @Override
    public Requirement updateRequirement(Requirement requirement) {
        if (requirement.getRequirementId() == null || requirement.getRequirementId().trim().isEmpty()) {
            throw new RuntimeException("需求ID不能为空");
        }
        Requirement existing = requirementRepo.findByRequirementId(requirement.getRequirementId())
                .orElseThrow(() -> new RuntimeException("需求不存在: " + requirement.getRequirementId()));
        existing.setTitle(requirement.getTitle() != null ? requirement.getTitle().trim() : "");
        existing.setDescription(requirement.getDescription() != null ? requirement.getDescription().trim() : "");
        existing.setType(requirement.getType() != null ? requirement.getType().trim() : null);
        existing.setFormat(requirement.getFormat() != null ? requirement.getFormat().trim() : null);
        return requirementRepo.save(existing);
    }

    @Override
    public void deleteRequirement(String requirementId) {
        if (!requirementRepo.existsById(requirementId)) {
            throw new RuntimeException("需求不存在: " + requirementId);
        }
        requirementRepo.deleteById(requirementId);
    }

    @Override
    public List<Requirement> listAll() {
        return requirementRepo.findAll();
    }

    @Override
    public List<Requirement> searchById(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listAll();
        }
        return requirementRepo.findByRequirementIdContaining(keyword.trim());
    }

    @Override
    public List<Requirement> searchByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listAll();
        }
        return requirementRepo.findByTitleContaining(keyword.trim());
    }

    @Override
    public Requirement getById(String requirementId) {
        return requirementRepo.findByRequirementId(requirementId)
                .orElseThrow(() -> new RuntimeException("需求不存在: " + requirementId));
    }

    @Override
    public Requirement createOrUpdateRequirement(Requirement requirement) {
        if (requirement.getRequirementId() == null || requirement.getRequirementId().trim().isEmpty()) {
            throw new RuntimeException("需求ID不能为空");
        }
        requirement.setRequirementId(requirement.getRequirementId().trim());
        requirement.setTitle(requirement.getTitle() != null ? requirement.getTitle().trim() : "");
        requirement.setDescription(requirement.getDescription() != null ? requirement.getDescription().trim() : "");
        requirement.setType(requirement.getType() != null ? requirement.getType().trim() : null);
        requirement.setFormat(requirement.getFormat() != null ? requirement.getFormat().trim() : null);

        Requirement existing = requirementRepo.findByRequirementId(requirement.getRequirementId()).orElse(null);
        if (existing != null) {
            existing.setTitle(requirement.getTitle());
            existing.setDescription(requirement.getDescription());
            existing.setType(requirement.getType());
            existing.setFormat(requirement.getFormat());
            return requirementRepo.save(existing);
        }
        return requirementRepo.save(requirement);
    }

    @Override
    public List<Requirement> batchCreateOrUpdateRequirements(List<Requirement> requirements) {
        return requirements.stream()
                .map(this::createOrUpdateRequirement)
                .collect(Collectors.toList());
    }
}
