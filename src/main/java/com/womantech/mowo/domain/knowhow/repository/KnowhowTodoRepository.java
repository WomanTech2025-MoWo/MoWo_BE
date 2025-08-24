package com.womantech.mowo.domain.knowhow.repository;

import com.womantech.mowo.domain.knowhow.entity.KnowhowTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowhowTodoRepository extends JpaRepository<KnowhowTodo, Long> {
    List<KnowhowTodo> findByKnowhowId(Long knowhowId);
}
