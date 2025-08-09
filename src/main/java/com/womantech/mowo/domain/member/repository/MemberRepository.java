package com.womantech.mowo.domain.member.repository;

import com.womantech.mowo.domain.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByNickName(String nickname);
}
