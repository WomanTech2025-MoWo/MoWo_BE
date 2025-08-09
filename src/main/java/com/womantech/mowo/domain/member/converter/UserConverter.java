package com.womantech.mowo.domain.member.converter;

import com.womantech.mowo.domain.member.dto.UserRequestDTO;
import com.womantech.mowo.domain.member.dto.UserResponseDTO;
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
}
