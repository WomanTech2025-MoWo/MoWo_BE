package com.womantech.mowo.domain.policy.service;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.repository.PolicyRepository;
import com.womantech.mowo.global.security.service.AuthorizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final AuthorizationService authorizationService;

    public PolicyService(PolicyRepository policyRepository, AuthorizationService authorizationService) {
        this.policyRepository = policyRepository;
        this.authorizationService = authorizationService;
    }

    public Members ensureAdminOrThrow(Long userId) {
        return authorizationService.ensureAdminOrThrow(userId);
    }

    // 전체 조회, 페이징 없음
    @Transactional(readOnly = true)
    public List<Policy> getAll() {
        return policyRepository.findAll();
    }

    // 전체 조회, 페이징 있음
    @Transactional(readOnly = true)
    public Page<Policy> getAll(Pageable pageable){
        return policyRepository.findAll(pageable);
    }

    // 필터링: 지역구, 진행 상태(현재/예정/지난)
    public Page<Policy> search(String regionCode, String status, Pageable pageable) {
        List<Policy> base = (regionCode == null || regionCode.isBlank())
                ? policyRepository.findAll()
                : policyRepository.findByRegionCode(regionCode);

        if (status != null && !status.isBlank()) {
            LocalDate today = LocalDate.now();
            base = base.stream()
                    .filter(p -> matchesStatus(p, status, today))
                    .collect(Collectors.toList());
    @Transactional(readOnly = true)
        }

        // 수동 페이지네이션
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), base.size());
        List<Policy> content = (start > end) ? Collections.emptyList() : base.subList(start, end);
        return new PageImpl<>(content, pageable, base.size());
    }

    private boolean matchesStatus(Policy p, String status, LocalDate today) {
        LocalDate s = p.getStartDate();
        LocalDate e = p.getEndDate();

        String key = status.trim().toLowerCase(); // "current|upcoming|past" or "현재|예정|지난"
        switch (key) {
            case "current":
            case "현재":
                return (s == null || !s.isAfter(today)) && (e == null || !e.isBefore(today));
            case "upcoming":
            case "예정":
                return s != null && s.isAfter(today);
            case "past":
            case "지난":
                return e != null && e.isBefore(today);
            default:
                return true; // 알 수 없는 상태면 필터 미적용
        }
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public Optional<Policy> getById(Long id) {
        return policyRepository.findByIdWithTodos(id);
    }

    // 등록
    @Transactional
    public Policy create(Policy policy) {
        return policyRepository.save(policy);
    }

    // 수정
    @Transactional
    public Policy update(Policy policy) {
        return policyRepository.save(policy);
    }

    // 삭제
    @Transactional
    public void deleteById(Long id) {
        policyRepository.deleteById(id);
    }

}
