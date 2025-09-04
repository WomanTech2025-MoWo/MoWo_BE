package com.womantech.mowo.domain.policy.repository;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.entity.PolicyBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PolicyBookmarkRepository extends JpaRepository<PolicyBookmark, Long> {
    
    // 특정 사용자의 특정 정책 북마크 조회
    Optional<PolicyBookmark> findByMemberAndPolicy(Members member, Policy policy);
    
    // 특정 사용자가 특정 정책을 북마크했는지 확인
    boolean existsByMemberAndPolicy(Members member, Policy policy);
    
    // 특정 사용자의 북마크 목록 조회 (최신순, 정책 정보 포함)
    @Query("SELECT pb FROM PolicyBookmark pb " +
           "LEFT JOIN FETCH pb.policy p " +
           "WHERE pb.member = :member " +
           "ORDER BY pb.createdAt DESC")
    Page<PolicyBookmark> findByMemberOrderByCreatedAtDesc(@Param("member") Members member, Pageable pageable);
}