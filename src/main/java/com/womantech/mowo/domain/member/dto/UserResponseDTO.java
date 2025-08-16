package com.womantech.mowo.domain.member.dto;

import com.womantech.mowo.domain.member.entity.PregnantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PregnancyWeekResponseDTO {
        Long userId;
        int pregnantWeek;
        Integer dDayToBirth;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        Long UserId;
        String accessToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoResponseDTO{
        Long userId;
        String nickName;
        String userName;
        LocalDate birthday;

        private PregnantStatus pregnantStatus;     // 임신여부
        private boolean hasTwins;                  // 쌍둥이 여부
        private LocalDate dueDate;                 // 출산예정일
        private boolean frequentUrination;        // 이뇨감
        private boolean jointPain;                 // 관절통증
        private boolean heartburn;                 // 속쓰림
        private boolean abdominalTightness;       // 배 뭉침
        private boolean drowsiness;                // 졸림
        private boolean morningSickness;           // 입덧
        private boolean constipationOrHemorrhoids; // 변비/치질
        private boolean swelling;                  // 부종
        private boolean dizziness;                 // 어지럼증
        private boolean insomniaOrSleepDisorder;  // 불면/수면장애
    }
}
