package com.womantech.mowo.domain.policy.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PolicyResponseDTO {
    private Long policyId;
    private String title;
    private String regionCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private List<String> policyTodos;
}
