package com.womantech.mowo.domain.policy.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PolicyResponseListDTO {
    private Long policyId;
    private String title;
    private String regionCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
}
