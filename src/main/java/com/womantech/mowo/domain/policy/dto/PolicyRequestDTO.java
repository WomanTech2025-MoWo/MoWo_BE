package com.womantech.mowo.domain.policy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PolicyRequestDTO {
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;
    private String regionCode;
    @NotNull(message = "지역 코드는 필수입니다")
    @NotNull(message = "시작 날짜는 필수입니다")
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    private List<String> policyTodos;
}
