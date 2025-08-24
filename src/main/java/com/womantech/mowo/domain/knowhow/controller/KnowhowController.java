package com.womantech.mowo.domain.knowhow.controller;

import com.womantech.mowo.domain.knowhow.converter.KnowhowConverter;
import com.womantech.mowo.domain.knowhow.dto.KnowhowRequestDTO;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseDTO;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseListDTO;
import com.womantech.mowo.domain.knowhow.entity.Knowhow;
import com.womantech.mowo.domain.knowhow.service.KnowhowService;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.global.apiPayload.ApiResponse;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import com.womantech.mowo.global.security.handler.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/knowhows")
public class KnowhowController {

    private final KnowhowService knowhowService;
    private final KnowhowConverter knowhowConverter;

    @Operation(summary = "노하우 목록 조회")
    @GetMapping
    public ApiResponse<List<KnowhowResponseListDTO>> list() {
        List<KnowhowResponseListDTO> knowhow = knowhowService.getAll();
        return ApiResponse.onSuccess(knowhow);
    }

    @Operation(summary = "노하우 상세 조회")
    @GetMapping("/{id}")
    public ApiResponse<KnowhowResponseDTO> detail(@PathVariable Long id) {
        Knowhow knowhow = knowhowService.getById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.KNOWHOW_NOT_FOUND));
        return ApiResponse.onSuccess(knowhowConverter.toDTO(knowhow));
    }

    @Operation(summary = "노하우 등록", description = "관리자만.")
    @PostMapping
    public ApiResponse<KnowhowResponseDTO> create(@AuthUser Long userId, @RequestBody KnowhowRequestDTO request) {
        Members admin = knowhowService.ensureAdminOrThrow(userId);
        Knowhow knowhow = knowhowConverter.toEntity(request, admin);
        Knowhow saved = knowhowService.create(knowhow);
        return ApiResponse.onSuccess(knowhowConverter.toDTO(saved));
    }

    @Operation(summary = "노하우 수정", description = "관리자만")
    @PutMapping("/{id}")
    public ApiResponse<KnowhowResponseDTO> update(
            @AuthUser Long userId,
            @PathVariable Long id,
            @RequestBody KnowhowRequestDTO request
    ) {
        knowhowService.ensureAdminOrThrow(userId);

        Knowhow knowhow = knowhowService.getById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.KNOWHOW_NOT_FOUND));

        knowhowConverter.apply(knowhow, request);

        Knowhow updated = knowhowService.update(knowhow);
        return ApiResponse.onSuccess(knowhowConverter.toDTO(updated));
    }

    @Operation(summary = "노하우 삭제", description = "관리자만")
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@AuthUser Long userId, @PathVariable Long id) {
        knowhowService.ensureAdminOrThrow(userId);
        knowhowService.getById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.KNOWHOW_NOT_FOUND));
        knowhowService.deleteById(id);
        return ApiResponse.onSuccess("노하우가 삭제되었습니다.");
    }
}
