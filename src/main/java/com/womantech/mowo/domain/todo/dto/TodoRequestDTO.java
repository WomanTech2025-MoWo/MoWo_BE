package com.womantech.mowo.domain.todo.dto;

import com.womantech.mowo.domain.todo.entity.TodoCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoRequestDTO {

    @Getter
    @Builder
    public static class CreateTodoDTO {
        private LocalDate todoDate;
        private LocalDateTime alarmDate;
        private String title;
        private String memo;
        private TodoCategory todoCategory;
        private Boolean isFixed;
    }

    @Getter
    public static class UpdateTodoDTO {
        private LocalDate todoDate;
        private LocalDate alarmDate;
        private String title;
        private String memo;
        private Boolean isFixed;
        private TodoCategory category;
    }
}