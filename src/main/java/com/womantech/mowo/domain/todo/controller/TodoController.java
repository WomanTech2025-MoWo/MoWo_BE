package com.womantech.mowo.domain.todo.controller;

import com.womantech.mowo.domain.todo.dto.TodoRequestDTO;
import com.womantech.mowo.domain.todo.dto.TodoResponseDTO;
import com.womantech.mowo.domain.todo.service.TodoService;
import com.womantech.mowo.global.apiPayload.ApiResponse;
import com.womantech.mowo.global.security.handler.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "TODO", description = "TODO 관리 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(
            summary = "TODO 생성 API", 
            description = "새로운 TODO를 생성합니다. 카테고리(WORK, HEALTH, PERSONAL)별로 분류되며, 알림 설정과 고정 여부를 지정할 수 있습니다."
    )
    @PostMapping
    public com.womantech.mowo.global.apiPayload.ApiResponse<TodoResponseDTO.createdTodoResponseDTO> createTodo(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "TODO 생성 요청 데이터") @Valid @RequestBody TodoRequestDTO.CreateTodoDTO request) {
        Long result = todoService.createTodo(memberId,request);
        return ApiResponse.onSuccess(new TodoResponseDTO.createdTodoResponseDTO(result));
    }

    @Operation(
            summary = "TODO 수정 API", 
            description = "기존 TODO의 내용을 수정합니다. 제목, 메모, 날짜, 알림, 카테고리, 고정 여부 등을 변경할 수 있습니다."
    )
    @PatchMapping("/{todoId}")
    public com.womantech.mowo.global.apiPayload.ApiResponse<TodoResponseDTO.createdTodoResponseDTO> patchTodo(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "수정할 TODO의 ID") @PathVariable("todoId") Long todoId,
            @Parameter(description = "TODO 수정 요청 데이터") @Valid @RequestBody TodoRequestDTO.UpdateTodoDTO request) {
        Long result = todoService.updateTodo(memberId, todoId, request);
        return ApiResponse.onSuccess(new TodoResponseDTO.createdTodoResponseDTO(result));
    }

    @Operation(
            summary = "TODO 삭제 API", 
            description = "기존 TODO를 완전히 삭제합니다. 삭제된 TODO는 복구할 수 없습니다."
    )
    @DeleteMapping("/{todoId}")
    public com.womantech.mowo.global.apiPayload.ApiResponse<String> deleteTodo(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "삭제할 TODO의 ID") @PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(memberId, todoId);
        return ApiResponse.onSuccess("삭제가 완료되었습니다.");
    }

    @Operation(
            summary = "TODO 임시 보관함 조회 API", 
            description = "임시 보관함에 저장된 TODO 목록을 조회합니다. 아직 일정에 추가되지 않은 TODO들이 표시됩니다."
    )
    @GetMapping("/draft")
    public com.womantech.mowo.global.apiPayload.ApiResponse<List<TodoResponseDTO.TodoInfoDTO>> getDraftTodos(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId) {
        List<TodoResponseDTO.TodoInfoDTO> result =  todoService.getDraftTodos(memberId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(
            summary = "TODO 임시 보관 API", 
            description = "TODO를 임시 보관함에 저장합니다. 나중에 일정에 추가하거나 수정할 수 있습니다."
    )
    @PostMapping("/draft")
    public com.womantech.mowo.global.apiPayload.ApiResponse<TodoResponseDTO.createdTodoResponseDTO> draftTodo(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "임시 보관할 TODO 데이터") @Valid @RequestBody TodoRequestDTO.CreateTodoDTO request) {
        Long result = todoService.draftTodo(memberId,request);
        return ApiResponse.onSuccess(new TodoResponseDTO.createdTodoResponseDTO(result));
    }

    @Operation(
            summary = "특정 날짜 TODO 목록 조회 API", 
            description = "특정 날짜의 TODO 목록을 카테고리별(WORK, HEALTH, PERSONAL)로 그룹화하여 조회합니다. 각 카테고리별 전체 개수와 완료 개수도 함께 반환됩니다."
    )
    @GetMapping
    public com.womantech.mowo.global.apiPayload.ApiResponse<TodoResponseDTO.TodoListResponseDTO> getMyTodos(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD 형식)", example = "2024-12-25") @RequestParam LocalDate date) {
        TodoResponseDTO.TodoListResponseDTO result = todoService.getMyTodos(memberId, date);
        return ApiResponse.onSuccess(result);
    }

    @Operation(
            summary = "TODO 완료 처리 API", 
            description = "TODO를 완료 상태로 변경합니다. 완료 날짜가 자동으로 기록되며, isDone 상태가 true로 변경됩니다."
    )
    @PatchMapping("/{todoId}/complete")
    public com.womantech.mowo.global.apiPayload.ApiResponse<String> completeTodo(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "완료 처리할 TODO의 ID") @PathVariable("todoId") Long todoId) {
        todoService.completeTodo(memberId, todoId);
        return ApiResponse.onSuccess("완료 처리 되었습니다.");
    }

    @Operation(
            summary = "TODO 검색 API", 
            description = "TODO의 제목을 기반으로 키워드 검색을 수행합니다. 대소문자를 구분하지 않으며, 부분 일치로 검색됩니다. 임시보관함의 TODO는 제외되고 최신순으로 정렬됩니다."
    )
    @GetMapping("/search")
    public com.womantech.mowo.global.apiPayload.ApiResponse<List<TodoResponseDTO.TodoDTO>> searchTodos(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "검색할 키워드 (제목에서 검색)", example = "회의") @RequestParam String keyword) {
        List<TodoResponseDTO.TodoDTO> result = todoService.searchTodos(memberId, keyword);
        return ApiResponse.onSuccess(result);
    }

    @Operation(
            summary = "TODO 상세 조회 API", 
            description = "특정 TODO의 상세 정보를 조회합니다. 제목, 내용, 날짜, 알림, 카테고리, 완료 상태 등 모든 정보가 포함됩니다."
    )
    @GetMapping("/{todoId}")
    public com.womantech.mowo.global.apiPayload.ApiResponse<TodoResponseDTO.TodoDTO> getTodoDetail(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "조회할 TODO의 ID") @PathVariable("todoId") Long todoId) {
        TodoResponseDTO.TodoDTO result = todoService.getTodoDetail(memberId, todoId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(
            summary = "월별 TODO 목록 조회 API", 
            description = "특정 연월의 모든 TODO 목록을 날짜순으로 조회합니다. 임시보관함의 TODO는 제외되며, 해당 월의 모든 날짜의 TODO가 포함됩니다."
    )
    @GetMapping("/monthly")
    public com.womantech.mowo.global.apiPayload.ApiResponse<List<TodoResponseDTO.TodoDTO>> getMonthlyTodos(
            @Parameter(description = "인증된 사용자 ID", hidden = true) @AuthUser Long memberId,
            @Parameter(description = "조회할 연도", example = "2024") @RequestParam int year,
            @Parameter(description = "조회할 월 (1-12)", example = "12") @RequestParam int month) {
        List<TodoResponseDTO.TodoDTO> result = todoService.getMonthlyTodos(memberId, year, month);
        return ApiResponse.onSuccess(result);
    }

    @Operation(
            summary = "오늘의 알림 목록 조회 API", 
            description = "오늘 하루 동안 생성된 TODO 알림 목록을 조회합니다. 스케줄러에 의해 생성된 알림들을 카테고리별로 확인할 수 있습니다."
    )
    @GetMapping("/notifications")
    public com.womantech.mowo.global.apiPayload.ApiResponse<List<TodoResponseDTO.GetNotificationResponseDTO>> getNotificationList(
            @AuthUser Long memberId
    ){
        List<TodoResponseDTO.GetNotificationResponseDTO> result = todoService.getNotificationResponseDTOList(memberId);
        return ApiResponse.onSuccess(result);
    }
}