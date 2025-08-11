package com.womantech.mowo.domain.member.controller;

import com.womantech.mowo.domain.member.dto.UserRequestDTO;
import com.womantech.mowo.domain.member.dto.UserResponseDTO;
import com.womantech.mowo.domain.member.service.MemberService;
import com.womantech.mowo.global.apiPayload.ApiResponse;
import com.womantech.mowo.global.security.handler.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "일반 회원가입 API",
            description = "아이디(이메일)와 비밀번호를 입력하여 회원가입을 진행합니다." +
                    "비밀번호 확인란(password2)은 password1과 반드시 일치해야 합니다.")
    @PostMapping("/auth/signup")
    public ApiResponse<String> joinService (@Valid @RequestBody UserRequestDTO.joinDTO request){
        memberService.joinUser(request);
        return ApiResponse.onSuccess("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "일반 로그인 API",
            description = "아이디와 비밀번호를 정확히 입력하면 인증에 성공하며, Access Token이 발급됩니다.")
    @PostMapping("/auth/login")
    public ApiResponse<UserResponseDTO.LoginResultDTO> localLogin (@RequestBody UserRequestDTO.LoginDTO request){
        return ApiResponse.onSuccess(memberService.loginUser(request));
    }

    @Operation(summary = "닉네임 중복확인 API", description = "닉네임 중복확인을 진행하는 API입니다.")
    @GetMapping("/auth/check-nickname")
    public ApiResponse<String> checkNickname(@RequestParam String nickname) {
        String result = memberService.checkNickname(nickname);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "사용자 ID 주입 테스트", description = "@AuthUser을 사용한 현사용자 ID 자동 주입 예시입니다.")
    @GetMapping("test")
    public ApiResponse<String> loginTest(@AuthUser Long userId){
        String result = "userID : " + userId;
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "온보딩 설문하기 API",
            description = "온보딩 설문 내용을 저장합니다.")
    @PostMapping("/onboarding")
    public ApiResponse<String> submitOnboardingSurvey (
            @AuthUser Long userId,
            @RequestBody @Valid UserRequestDTO.OnboardingRequestDTO request){
        memberService.submitOnboardingSurvey(userId, request);
        return ApiResponse.onSuccess("온보딩 설문이 완료되었습니다.");
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 정보를 조회하는 API입니다.")
    @GetMapping
    public ApiResponse<UserResponseDTO.MemberInfoResponseDTO> getUserInfo(
            @AuthUser Long userId) {
        UserResponseDTO.MemberInfoResponseDTO result = memberService.getMemberInfo(userId);
        return ApiResponse.onSuccess(result);
    }
}
