package com.womantech.mowo.domain.knowhow.repository;

import com.womantech.mowo.domain.knowhow.entity.Knowhow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KnowhowRepository extends JpaRepository<Knowhow, Long> {
    @Query("SELECT k FROM Knowhow k LEFT JOIN FETCH k.knowhowTodos WHERE k.id = :id")
    Optional<Knowhow> findByIdWithTodos(@Param("id") Long id);
}
