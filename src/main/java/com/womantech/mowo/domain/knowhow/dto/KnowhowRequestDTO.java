package com.womantech.mowo.domain.knowhow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KnowhowRequestDTO {
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;
    
    @NotBlank(message = "요약은 필수입니다")
    @Size(max = 500, message = "요약은 500자 이하여야 합니다")
    private String summary;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    private List<String> knowhowTodos;
}
