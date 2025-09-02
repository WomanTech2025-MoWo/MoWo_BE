package com.womantech.mowo.domain.policy.service;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.entity.RegionCode;
import com.womantech.mowo.domain.policy.repository.PolicyRepository;
import com.womantech.mowo.global.security.service.AuthorizationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // 필터링: 지역구, 진행 상태(현재/예정/지난) - DB 레벨에서 처리
    @Transactional(readOnly = true)
    public Page<Policy> search(String regionCodeStr, String status, Pageable pageable) {
        LocalDate today = LocalDate.now();
        
        boolean hasRegion = regionCodeStr != null && !regionCodeStr.isBlank();
        boolean hasStatus = status != null && !status.isBlank();
        
        // 지역과 상태 둘 다 있는 경우
        if (hasRegion && hasStatus) {
            RegionCode regionCode = RegionCode.fromCode(regionCodeStr);
            return searchByRegionAndStatus(regionCode, status, today, pageable);
        }
        
        // 상태만 있는 경우
        if (hasStatus) {
            return searchByStatus(status, today, pageable);
        }
        
        // 지역만 있는 경우
        if (hasRegion) {
            RegionCode regionCode = RegionCode.fromCode(regionCodeStr);
            return policyRepository.findByRegionCode(regionCode, pageable);
        }
        
        // 필터 없는 경우
        return policyRepository.findAll(pageable);
    }
    
    private Page<Policy> searchByRegionAndStatus(RegionCode regionCode, String status, LocalDate today, Pageable pageable) {
        String key = status.trim().toLowerCase();
        
        switch (key) {
            case "current":
            case "현재":
                return policyRepository.findCurrentPoliciesByRegion(regionCode, today, pageable);
            case "upcoming":
            case "예정":
                return policyRepository.findUpcomingPoliciesByRegion(regionCode, today, pageable);
            case "past":
            case "지난":
                return policyRepository.findPastPoliciesByRegion(regionCode, today, pageable);
            default:
                return policyRepository.findByRegionCode(regionCode, pageable);
        }
    }
    
    private Page<Policy> searchByStatus(String status, LocalDate today, Pageable pageable) {
        String key = status.trim().toLowerCase();
        switch (key) {
            case "current":
            case "현재":
                return policyRepository.findCurrentPolicies(today, pageable);
            case "upcoming":
            case "예정":
                return policyRepository.findUpcomingPolicies(today, pageable);
            case "past":
            case "지난":
                return policyRepository.findPastPolicies(today, pageable);
            default:
                return policyRepository.findAll(pageable);
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
