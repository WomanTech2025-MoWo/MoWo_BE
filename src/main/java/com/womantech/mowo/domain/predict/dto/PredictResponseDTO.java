package com.womantech.mowo.domain.predict.dto;

import java.util.List;

public class PredictResponseDTO {
    private List<String> predictions;

    public PredictResponseDTO() {}
    public PredictResponseDTO(List<String> predictions) {
        this.predictions = predictions;
    }

    public List<String> getPredictions() { return predictions; }
    public void setPredictions(List<String> predictions) { this.predictions = predictions; }
}
