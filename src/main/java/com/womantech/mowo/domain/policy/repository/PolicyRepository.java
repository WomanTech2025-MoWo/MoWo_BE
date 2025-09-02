package com.womantech.mowo.domain.policy.repository;

import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.entity.RegionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByRegionCode(RegionCode regionCode);
    Page<Policy> findByRegionCode(RegionCode regionCode, Pageable pageable);

    @Query("SELECT p FROM Policy p LEFT JOIN FETCH p.policyTodos WHERE p.id = :id")
    Optional<Policy> findByIdWithTodos(@Param("id") Long id);

    // 현재 진행중인 정책 (startDate <= today AND (endDate IS NULL OR endDate >= today))
    @Query("SELECT p FROM Policy p WHERE " +
           "(p.startDate IS NULL OR p.startDate <= :today) AND " +
           "(p.endDate IS NULL OR p.endDate >= :today)")
    Page<Policy> findCurrentPolicies(@Param("today") LocalDate today, Pageable pageable);

    // 예정된 정책 (startDate > today)  
    @Query("SELECT p FROM Policy p WHERE p.startDate > :today")
    Page<Policy> findUpcomingPolicies(@Param("today") LocalDate today, Pageable pageable);

    // 종료된 정책 (endDate < today)
    @Query("SELECT p FROM Policy p WHERE p.endDate < :today")
    Page<Policy> findPastPolicies(@Param("today") LocalDate today, Pageable pageable);

    // 지역별 현재 진행중인 정책
    @Query("SELECT p FROM Policy p WHERE p.regionCode = :regionCode AND " +
           "(p.startDate IS NULL OR p.startDate <= :today) AND " +
           "(p.endDate IS NULL OR p.endDate >= :today)")
    Page<Policy> findCurrentPoliciesByRegion(@Param("regionCode") RegionCode regionCode, @Param("today") LocalDate today, Pageable pageable);

    // 지역별 예정된 정책
    @Query("SELECT p FROM Policy p WHERE p.regionCode = :regionCode AND p.startDate > :today")
    Page<Policy> findUpcomingPoliciesByRegion(@Param("regionCode") RegionCode regionCode, @Param("today") LocalDate today, Pageable pageable);

    // 지역별 종료된 정책
    @Query("SELECT p FROM Policy p WHERE p.regionCode = :regionCode AND p.endDate < :today")
    Page<Policy> findPastPoliciesByRegion(@Param("regionCode") RegionCode regionCode, @Param("today") LocalDate today, Pageable pageable);

}
