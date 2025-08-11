package com.womantech.mowo.domain.member.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class MemberSymptoms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Members member;

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
