package com.womantech.mowo.domain.member.service;

import com.womantech.mowo.domain.member.converter.UserConverter;
import com.womantech.mowo.domain.member.dto.UserRequestDTO;
import com.womantech.mowo.domain.member.dto.UserResponseDTO;
import com.womantech.mowo.domain.member.entity.MemberSymptoms;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.repository.MemberRepository;
import com.womantech.mowo.domain.member.repository.MemberSymptomsRepository;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import com.womantech.mowo.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static com.womantech.mowo.domain.member.converter.UserConverter.toMemberSymptoms;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberSymptomsRepository memberSymptomsRepository;

    @Transactional
    public Members joinUser(UserRequestDTO.joinDTO joinDTO) {

        if (!Objects.equals(joinDTO.getPassword1(), joinDTO.getPassword2())) {
            throw new MemberHandler(ErrorStatus.PASSWORD_MISMATCH);
        }

        if (memberRepository.existsByUserName(joinDTO.getUsername())) {
            throw new MemberHandler(ErrorStatus.DUPLICATE_USERNAME);
        }

        boolean exists = memberRepository.existsByNickName(joinDTO.getNickname());
        if (exists){
            throw new MemberHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(joinDTO.getPassword1());

        Members joinUser = UserConverter.toUser(joinDTO, encodedPassword);

        return memberRepository.save(joinUser);
    }

    public UserResponseDTO.LoginResultDTO loginUser(UserRequestDTO.LoginDTO loginDTO) {

        Members member = memberRepository.findByUserName(loginDTO.getUsername())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())){
            throw new MemberHandler(ErrorStatus.PASSWORD_MISMATCH);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getUserName(),
                null,
                Collections.emptyList());

        String accessToken = jwtTokenProvider.generateToken(authentication);

        return UserConverter.toLoginResultDTO(
                member.getId(),
                accessToken
        );
    }

    public void submitOnboardingSurvey(Long memberId,  UserRequestDTO.OnboardingRequestDTO request){
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Optional<MemberSymptoms> memberSymptoms = memberSymptomsRepository.findByMember(member);

        if(memberSymptoms.isPresent()){
            throw new MemberHandler(ErrorStatus.ONBOARDING_DUPLICATE);
        }
        memberSymptomsRepository.save(toMemberSymptoms(member,request));
    }

    public String checkNickname(String nickname) {
        boolean exists = memberRepository.existsByNickName(nickname);
        if (!exists) {
            return "사용 가능한 닉네임입니다.";
        } else {
            throw new MemberHandler(ErrorStatus.DUPLICATE_NICKNAME);
        }
    }
}
