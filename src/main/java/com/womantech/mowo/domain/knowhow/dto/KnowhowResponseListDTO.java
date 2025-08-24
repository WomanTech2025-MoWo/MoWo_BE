package com.womantech.mowo.domain.knowhow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KnowhowResponseListDTO {
    private Long knowhowId;
    private String title;
    private String summary;
    private String content;
}
