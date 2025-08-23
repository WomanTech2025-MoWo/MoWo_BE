package com.womantech.mowo.domain.policy.repository;

import com.womantech.mowo.domain.policy.entity.PolicyTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyTodoRepository extends JpaRepository<PolicyTodo, Long> {
    List<PolicyTodo> findByPolicyId(Long policyId);
}
