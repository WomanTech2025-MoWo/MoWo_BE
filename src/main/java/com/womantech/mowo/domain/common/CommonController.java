package com.womantech.mowo.domain.common;

import com.womantech.mowo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class CommonController {

    @Operation(
            summary = "테스트",
            description = "스웨거 사용 예시입니다")
    @GetMapping("/api-test")
    public ApiResponse<String> testOKAPI(){
        return ApiResponse.onSuccess("테스트");
    }

}