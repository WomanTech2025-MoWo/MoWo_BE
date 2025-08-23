package com.womantech.mowo.domain.policy.converter;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.dto.PolicyRequestDTO;
import com.womantech.mowo.domain.policy.dto.PolicyResponseDTO;
import com.womantech.mowo.domain.policy.dto.PolicyResponseListDTO;
import com.womantech.mowo.domain.policy.entity.Policy;
import com.womantech.mowo.domain.policy.entity.PolicyTodo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PolicyConverter {

    // 생성 시 엔티티 구성
    public Policy toEntity(PolicyRequestDTO request, Members member) {
       Policy policy = Policy.builder()
                .member(member)
                .title(request.getTitle())
                .regionCode(request.getRegionCode())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .content(request.getContent())
                .build();

        List<String> policyTodoList = Optional.ofNullable(request.getPolicyTodos()).orElseGet(ArrayList::new);
        for (String s : policyTodoList) {
            PolicyTodo policyTodo = PolicyTodo.builder()
                    .policy(policy)
                    .suggestion(s)
                    .build();
            policy.getPolicyTodos().add(policyTodo);
        }
        return policy;
    }

    // 수정 시 기존 엔티티에 덮어쓰기 (연관 컬렉션 교체)
    public void apply(Policy target, PolicyRequestDTO request) {
        target.setTitle(request.getTitle());
        target.setRegionCode(request.getRegionCode());
        target.setStartDate(request.getStartDate());
        target.setEndDate(request.getEndDate());
        target.setContent(request.getContent());

        // 기존 제안목록 교체 (orphanRemoval=true 이므로 자동 삭제)
        target.getPolicyTodos().clear();
        List<String> policyTodoList = Optional.ofNullable(request.getPolicyTodos()).orElseGet(ArrayList::new);
        for (String s: policyTodoList) {
            PolicyTodo policyTodo = PolicyTodo.builder()
                    .policy(target)
                    .suggestion(s)
                    .build();
            target.getPolicyTodos().add(policyTodo);
        }
    }

    public PolicyResponseDTO toDTO(Policy policy) {
        List<String> policyTodos = policy.getPolicyTodos()
                .stream().map(PolicyTodo::getSuggestion).toList();

        return PolicyResponseDTO.builder()
                .policyId(policy.getId())
                .title(policy.getTitle())
                .regionCode(policy.getRegionCode())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .content(policy.getContent())
                .policyTodos(policyTodos)
                .build();
    }

    public PolicyResponseListDTO toListDTO(Policy policy) {
        return PolicyResponseListDTO.builder()
                .policyId(policy.getId())
                .title(policy.getTitle())
                .regionCode(policy.getRegionCode())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .content(policy.getContent())
                .build();
    }

}
