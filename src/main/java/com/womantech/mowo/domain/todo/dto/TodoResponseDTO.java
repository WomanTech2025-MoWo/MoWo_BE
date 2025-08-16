package com.womantech.mowo.domain.todo.dto;

import com.womantech.mowo.domain.todo.entity.TodoCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TodoResponseDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createdTodoResponseDTO {
        private Long todoId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoInfoDTO {
        private Long id;
        private LocalDate todoDate;
        private LocalDate completeDate;
        private LocalDateTime alarmDate;
        private String title;
        private String memo;
        private Boolean isFixed;
        private TodoCategory category;
        private Boolean isStorage;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoDTO {
        private Long id;
        private LocalDate todoDate;
        private LocalDate completeDate;
        private LocalDateTime alarmDate;
        private String title;
        private String memo;
        private Boolean isFixed;
        private TodoCategory category;
        private Boolean isDone;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoCategoryDTO {
        private List<TodoDTO> todos;
        private Integer totalCount;
        private Integer completedCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoListResponseDTO {
        private TodoCategoryDTO work;
        private TodoCategoryDTO health;
        private TodoCategoryDTO personal;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoListDTO {
        private List<TodoInfoDTO> todos;
        private Integer totalCount;
    }
}