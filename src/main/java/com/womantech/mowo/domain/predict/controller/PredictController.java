package com.womantech.mowo.domain.predict.controller;

import com.womantech.mowo.domain.predict.dto.PredictRequestDTO;
import com.womantech.mowo.domain.predict.dto.PredictResponseDTO;
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
    public ResponseEntity<PredictResponseDTO> predict(@Valid @RequestBody PredictRequestDTO req) {
        return ResponseEntity.ok(service.predict(req));
    }
}
