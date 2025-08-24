package com.womantech.mowo.domain.knowhow.converter;

import com.womantech.mowo.domain.knowhow.dto.KnowhowRequestDTO;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseDTO;
import com.womantech.mowo.domain.knowhow.dto.KnowhowResponseListDTO;
import com.womantech.mowo.domain.knowhow.entity.Knowhow;
import com.womantech.mowo.domain.knowhow.entity.KnowhowTodo;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.PolicyTodo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class KnowhowConverter {

    public Knowhow toEntity(KnowhowRequestDTO request, Members member) {
        Knowhow knowhow = Knowhow.builder()
                .member(member)
                .title(request.getTitle())
                .summary(request.getSummary())
                .content(request.getContent())
                .build();

        List<String> knowhowTodoList = Optional.ofNullable(request.getKnowhowTodos()).orElseGet(ArrayList::new);
        for (String s : knowhowTodoList) {
            KnowhowTodo knowhowTodo = KnowhowTodo.builder()
                    .knowhow(knowhow)
                    .suggestion(s)
                    .build();
            knowhow.getKnowhowTodos().add(knowhowTodo);
        }
        return knowhow;
    }

    public void apply(Knowhow target, KnowhowRequestDTO request) {
        target.setTitle(request.getTitle());
        target.setSummary(request.getSummary());
        target.setContent(request.getContent());

        target.getKnowhowTodos().clear();
        List<String> knowhowTodoList = Optional.ofNullable(request.getKnowhowTodos()).orElseGet(ArrayList::new);
        for(String s: knowhowTodoList) {
            KnowhowTodo knowhowTodo = KnowhowTodo.builder()
                    .knowhow(target)
                    .suggestion(s)
                    .build();
            target.getKnowhowTodos().add(knowhowTodo);
        }
    }

    public KnowhowResponseDTO toDTO(Knowhow knowhow) {
        List<String> knowhowTodos = knowhow.getKnowhowTodos()
                .stream().map(KnowhowTodo::getSuggestion).toList();

        return KnowhowResponseDTO.builder()
                .knowhowId(knowhow.getId())
                .title(knowhow.getTitle())
                .summary(knowhow.getSummary())
                .content(knowhow.getContent())
                .knowhowTodos(knowhowTodos)
                .build();
    }

    public KnowhowResponseListDTO toListDTO(Knowhow knowhow) {
        return KnowhowResponseListDTO.builder()
                .knowhowId(knowhow.getId())
                .title(knowhow.getTitle())
                .summary(knowhow.getSummary())
                .content(knowhow.getContent())
                .build();
    }
}
