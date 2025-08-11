package com.womantech.mowo.domain.member.converter;

import com.womantech.mowo.domain.member.dto.UserRequestDTO;
import com.womantech.mowo.domain.member.dto.UserResponseDTO;
import com.womantech.mowo.domain.member.entity.MemberSymptoms;
import com.womantech.mowo.domain.member.entity.Members;

public class UserConverter {

    public static Members toUser(UserRequestDTO.joinDTO request, String password){
        Members user = Members.builder()
                .nickName(request.getNickname())
                .userName(request.getUsername())
                .password(password)
                .build();
        return user;
    }

    public static UserResponseDTO.LoginResultDTO toLoginResultDTO(Long userId, String accessToken){
        return UserResponseDTO.LoginResultDTO.builder()
                .UserId(userId)
                .accessToken(accessToken)
                .build();
    }

    public static MemberSymptoms toMemberSymptoms(Members members, UserRequestDTO.OnboardingRequestDTO request){
        return MemberSymptoms.builder()
                .member(members)
                .pregnantStatus(request.getPregnantStatus())
                .dueDate(request.getDueDate())
                .hasTwins(request.isHasTwins())
                .frequentUrination(request.isFrequentUrination())
                .jointPain(request.isJointPain())
                .heartburn(request.isHeartburn())
                .drowsiness(request.isDrowsiness())
                .abdominalTightness(request.isAbdominalTightness())
                .morningSickness(request.isMorningSickness())
                .constipationOrHemorrhoids(request.isConstipationOrHemorrhoids())
                .swelling(request.isSwelling())
                .dizziness(request.isDizziness())
                .insomniaOrSleepDisorder(request.isInsomniaOrSleepDisorder())
                .build();
    }
}
