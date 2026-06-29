package com.svnplatform.repository;

import com.svnplatform.entity.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRepo extends JpaRepository<Repository, Long> {

    Optional<Repository> findByLocalPath(String localPath);

    boolean existsByLocalPath(String localPath);
}
