package com.womantech.mowo.global.security.userDetails;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.repository.MemberRepository;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Members members = memberRepository.findByUserName(username)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return new LocalCustomUserDetails(members);
    }
}