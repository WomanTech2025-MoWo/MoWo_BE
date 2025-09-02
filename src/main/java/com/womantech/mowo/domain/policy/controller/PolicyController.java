package com.womantech.mowo.domain.policy.controller;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.converter.PolicyConverter;
import com.womantech.mowo.domain.policy.dto.PolicyRequestDTO;
import com.womantech.mowo.domain.policy.dto.PolicyResponseDTO;
import com.womantech.mowo.domain.policy.dto.PolicyResponseListDTO;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.service.PolicyService;
import com.womantech.mowo.global.apiPayload.ApiResponse;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.PolicyHandler;
import com.womantech.mowo.global.security.handler.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    private final PolicyConverter policyConverter;

    @Operation(summary = "정책 목록 조회", description = "regionCode와 status로 필터링. 둘 다 옵션. 페이지네이션 지원.")
    @GetMapping
    public ApiResponse<Page<PolicyResponseListDTO>> list(
            @RequestParam(required = false) String regionCode,
            @RequestParam(required = false) String status,
            Pageable pageable
    ) {
        Page<PolicyResponseListDTO> page = policyService.search(regionCode, status, pageable)
                .map(policyConverter::toListDTO);
        return ApiResponse.onSuccess(page);
    }

    @Operation(summary = "정책 상세 조회")
    @GetMapping("/{id}")
    public ApiResponse<PolicyResponseDTO> detail(@PathVariable Long id) {
        Policy policy = policyService.getById(id)
                .orElseThrow(() -> new PolicyHandler(ErrorStatus.POLICY_NOT_FOUND));
        return ApiResponse.onSuccess(policyConverter.toDTO(policy));
    }

    @Operation(summary = "정책 등록", description = "관리자만. @AuthUser로 멤버 ID 주입.")
    @PostMapping
    public ApiResponse<PolicyResponseDTO> create(
            @AuthUser Long userId,
            @Valid @RequestBody PolicyRequestDTO request
    ) {
        Members admin = policyService.ensureAdminOrThrow(userId);
        Policy policy = policyConverter.toEntity(request, admin);
        Policy saved = policyService.create(policy);
        return ApiResponse.onSuccess(policyConverter.toDTO(saved));
    }

    @Operation(summary = "정책 수정", description = "관리자만. 투두 제안 리스트 전체 교체 방식.")
    @PutMapping("/{id}")
    public ApiResponse<PolicyResponseDTO> update(
            @AuthUser Long userId,
            @PathVariable Long id,
            @Valid @RequestBody PolicyRequestDTO request
    ) {
        policyService.ensureAdminOrThrow(userId);

        Policy policy = policyService.getById(id)
                .orElseThrow(() -> new PolicyHandler(ErrorStatus.POLICY_NOT_FOUND));

        policyConverter.apply(policy, request);

        Policy updated = policyService.update(policy);
        return ApiResponse.onSuccess(policyConverter.toDTO(updated));
    }

    @Operation(summary = "정책 삭제", description = "관리자만.")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthUser Long userId, @PathVariable Long id) {
        policyService.ensureAdminOrThrow(userId);
        policyService.getById(id)
                .orElseThrow(() -> new PolicyHandler(ErrorStatus.POLICY_NOT_FOUND));
        policyService.deleteById(id);
        return ApiResponse.onSuccess("정책이 삭제되었습니다.");
    }

}
