package com.womantech.mowo.domain.knowhow.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KnowhowResponseDTO {
    private Long knowhowId;
    private String title;
    private String summary;
    private String content;
    private List<String> knowhowTodos;
}
