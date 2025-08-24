package com.womantech.mowo.domain.knowhow.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KnowhowRequestDTO {
    private String title;
    private String summary;
    private String content;
    private List<String> knowhowTodos;
}
