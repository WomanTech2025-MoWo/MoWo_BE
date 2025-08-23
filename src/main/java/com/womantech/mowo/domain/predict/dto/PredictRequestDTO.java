package com.womantech.mowo.domain.predict.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PredictRequestDTO {
    @NotNull
    @NotEmpty
    private List<List<Integer>> features; // 0/1 정수로 받기 (boolean 대신)

    public List<List<Integer>> getFeatures() { return features; }
    public void setFeatures(List<List<Integer>> features) { this.features = features; }
}
