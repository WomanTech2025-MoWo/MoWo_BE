package com.womantech.mowo.domain.predict.controller;

import com.womantech.mowo.domain.predict.dto.PredictRequest;
import com.womantech.mowo.domain.predict.dto.PredictResponse;
import com.womantech.mowo.domain.predict.service.PredictService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predict")
public class PredictController {
    private final PredictService service;

    public PredictController(PredictService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PredictResponse> predict(@Valid @RequestBody PredictRequest req) {
        return ResponseEntity.ok(service.predict(req));
    }
}
