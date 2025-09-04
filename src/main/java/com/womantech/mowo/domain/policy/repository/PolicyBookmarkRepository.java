package com.womantech.mowo.domain.policy.repository;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.entity.PolicyBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PolicyBookmarkRepository extends JpaRepository<PolicyBookmark, Long> {
    
    // 특정 사용자의 특정 정책 북마크 조회
    Optional<PolicyBookmark> findByMemberAndPolicy(Members member, Policy policy);
    
    // 특정 사용자가 특정 정책을 북마크했는지 확인 (ID 기반)
    boolean existsByMemberAndPolicy(Members member, Policy policy);
    
    // 특정 사용자가 특정 정책을 북마크했는지 확인 (ID 기반, 최적화)
    @Query("SELECT COUNT(pb) > 0 FROM PolicyBookmark pb WHERE pb.member = :member AND pb.policy.id = :policyId")
    boolean existsByMemberAndPolicyId(@Param("member") Members member, @Param("policyId") Long policyId);
    
    // 특정 사용자의 특정 정책 북마크 조회 (ID 기반, 최적화)
    @Query("SELECT pb FROM PolicyBookmark pb WHERE pb.member = :member AND pb.policy.id = :policyId")
    Optional<PolicyBookmark> findByMemberAndPolicyId(@Param("member") Members member, @Param("policyId") Long policyId);
    
    // 특정 사용자의 북마크 목록 조회 (최신순, 정책 정보 포함)
    @Query("SELECT pb FROM PolicyBookmark pb " +
           "LEFT JOIN FETCH pb.policy p " +
           "WHERE pb.member = :member " +
           "ORDER BY pb.createdAt DESC")
    Page<PolicyBookmark> findByMemberOrderByCreatedAtDesc(@Param("member") Members member, Pageable pageable);
    
    // 특정 사용자가 북마크한 정책 ID 목록 조회 (배치 처리용)
    @Query("SELECT pb.policy.id FROM PolicyBookmark pb WHERE pb.member = :member AND pb.policy.id IN :policyIds")
    List<Long> findBookmarkedPolicyIds(@Param("member") Members member, @Param("policyIds") List<Long> policyIds);
}