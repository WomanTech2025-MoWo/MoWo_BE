package com.womantech.mowo.domain.todo.converter;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.todo.dto.TodoRequestDTO;
import com.womantech.mowo.domain.todo.dto.TodoResponseDTO;
import com.womantech.mowo.domain.todo.entity.Todos;

public class TodoConverter {

    public static Todos toSaveTodos(Members members, TodoRequestDTO.CreateTodoDTO dto){
        return Todos.builder()
                .members(members)
                .todoDate(dto.getTodoDate())
                .alarmDate(dto.getAlarmDate())
                .title(dto.getTitle())
                .memo(dto.getMemo())
                .isFixed(dto.getIsFixed())
                .isStorage(true)
                .category(dto.getTodoCategory())
                .build();
    }

    public static TodoResponseDTO.TodoInfoDTO toTodoInfoDTO(Todos todo){
        return TodoResponseDTO.TodoInfoDTO.builder()
                .id(todo.getId())
                .todoDate(todo.getTodoDate())
                .completeDate(todo.getCompleteDate())
                .alarmDate(todo.getAlarmDate())
                .title(todo.getTitle())
                .memo(todo.getMemo())
                .isFixed(todo.getIsFixed())
                .category(todo.getCategory())
                .isStorage(todo.getIsStorage())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    public static TodoResponseDTO.TodoDTO toTodoDTO(Todos todo){
        return TodoResponseDTO.TodoDTO.builder()
                .id(todo.getId())
                .todoDate(todo.getTodoDate())
                .completeDate(todo.getCompleteDate())
                .alarmDate(todo.getAlarmDate())
                .title(todo.getTitle())
                .memo(todo.getMemo())
                .isFixed(todo.getIsFixed())
                .category(todo.getCategory())
                .isDone(todo.getIsDone())
                .createdAt(todo.getCreatedAt())
                .build();
    }
}