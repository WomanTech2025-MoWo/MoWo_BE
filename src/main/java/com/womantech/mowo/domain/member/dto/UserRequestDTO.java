package com.womantech.mowo.domain.member.dto;

import com.womantech.mowo.domain.member.entity.PregnantStatus;
import com.womantech.mowo.domain.member.entity.Role;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class UserRequestDTO {

    @Getter
    public static class joinDTO{

        @NotBlank
        String username;

        @NotBlank
        String nickname;

        @NotBlank
        String password1;

        @NotBlank
        String password2;

        @NotNull
        Role role;
    }

    @Getter
    @Setter
    public static class LoginDTO{
        @NotBlank(message = "아이디는 필수입니다.")
        private String username;

        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class OnboardingRequestDTO {

        @NotNull(message = "임신 여부는 필수입니다.")
        private PregnantStatus pregnantStatus;     // 임신여부

        private boolean hasTwins;                  // 쌍둥이 여부

        @FutureOrPresent(message = "출산 예정일은 과거일 수 없습니다.")
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfoPatchRequestDTO{
        String nickName;
        String userName;
        LocalDate birthday;

        String password1;
        String password2;

        private PregnantStatus pregnantStatus;     // 임신여부
        private Boolean  hasTwins;                  // 쌍둥이 여부
        private LocalDate dueDate;                 // 출산예정일
        private Boolean  frequentUrination;        // 이뇨감
        private Boolean  jointPain;                 // 관절통증
        private Boolean  heartburn;                 // 속쓰림
        private Boolean  abdominalTightness;       // 배 뭉침
        private Boolean  drowsiness;                // 졸림
        private Boolean  morningSickness;           // 입덧
        private Boolean  constipationOrHemorrhoids; // 변비/치질
        private Boolean  swelling;                  // 부종
        private Boolean  dizziness;                 // 어지럼증
        private Boolean  insomniaOrSleepDisorder;  // 불면/수면장애
    }
}