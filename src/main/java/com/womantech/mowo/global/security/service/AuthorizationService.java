package com.womantech.mowo.global.security.service;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.repository.MemberRepository;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final MemberRepository memberRepository;

    public Members getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public Members ensureAdminOrThrow(Long userId) {
        Members member = getMemberOrThrow(userId);
        String role = String.valueOf(member.getRole());
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }
        return member;
    }
}