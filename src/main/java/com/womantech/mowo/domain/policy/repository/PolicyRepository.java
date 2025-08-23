package com.womantech.mowo.domain.policy.repository;

import com.womantech.mowo.domain.policy.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByRegionCode(String regionCode);
    Page<Policy> findByRegionCode(String regionCode, Pageable pageable);

    @Query("SELECT p FROM Policy p LEFT JOIN FETCH p.policyTodos WHERE p.id = :id")
    Optional<Policy> findByIdWithTodos(@Param("id") Long id);

}
