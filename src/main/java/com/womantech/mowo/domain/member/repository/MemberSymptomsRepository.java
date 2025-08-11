package com.womantech.mowo.domain.member.repository;

import com.womantech.mowo.domain.member.entity.MemberSymptoms;
import com.womantech.mowo.domain.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSymptomsRepository extends JpaRepository<MemberSymptoms,Long> {
     Optional<MemberSymptoms> findByMember (Members member);
}
