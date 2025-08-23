package com.womantech.mowo.domain.predict.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.womantech.mowo.domain.predict.dto.PredictRequest;
import com.womantech.mowo.domain.predict.dto.PredictResponse;
import com.womantech.mowo.global.ai.PythonProperties;
import com.womantech.mowo.global.ai.PythonRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictService {
    private final PythonRunner pythonRunner;
    private final PythonProperties props;
    private final ObjectMapper mapper;

    public PredictService(PythonRunner pythonRunner, PythonProperties props, ObjectMapper mapper) {
        this.pythonRunner = pythonRunner;
        this.props = props;
        this.mapper = mapper;
    }

    public PredictResponse predict(PredictRequest req) {
        // 1) 입력 패딩/검증
        List<List<Integer>> padded = padToExpected(req.getFeatures(), props.getExpectedFeatures());

        // 2) Python에 보낼 JSON 그대로 구성
        String jsonForPython;
        try {
            jsonForPython = mapper.writeValueAsString(new FeaturesWrapper(padded));
        } catch (Exception e) {
            throw new IllegalStateException("요청 직렬화 실패: " + e.getMessage(), e);
        }

        // 3) 파이썬 호출
        String stdout = pythonRunner.runWithJsonInput(jsonForPython);

        // 4) 파이썬 출력 파싱
        try {
            JsonNode root = mapper.readTree(stdout);
            JsonNode preds = root.get("predictions");
            List<String> list = new ArrayList<>();
            if (preds != null && preds.isArray()) {
                preds.forEach(n -> list.add(n.asText()));
            }
            return new PredictResponse(list);
        } catch (Exception e) {
            throw new IllegalStateException("파이썬 출력 파싱 실패: " + e.getMessage() + ", raw=" + stdout, e);
        }
    }

    private static List<List<Integer>> padToExpected(List<List<Integer>> input, int expected) {
        List<List<Integer>> out = new ArrayList<>(input.size());
        for (List<Integer> row : input) {
            if (row.size() > expected) {
                throw new IllegalArgumentException("입력 feature 수(" + row.size() + ")가 기대치(" + expected + ")보다 큼");
            }
            List<Integer> copy = new ArrayList<>(row);
            while (copy.size() < expected) copy.add(0); // 뒤를 0으로 패딩
            out.add(copy);
        }
        return out;
    }

    // {"features": [...] } 형태를 유지하기 위한 래퍼
    static class FeaturesWrapper {
        public List<List<Integer>> features;
        public FeaturesWrapper(List<List<Integer>> features) { this.features = features; }
    }
}
