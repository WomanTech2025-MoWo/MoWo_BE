package com.womantech.mowo.domain.todo.repository;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.todo.entity.TodoCategory;
import com.womantech.mowo.domain.todo.entity.Todos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todos, Long> {
    List<Todos> findByMembers(Members member);
    List<Todos> findByMembersAndTodoDate(Members member, LocalDate todoDate);
    List<Todos> findByMembersAndCategory(Members member, TodoCategory category);
    List<Todos> findByMembersAndIsFixed(Members member, Boolean isFixed);
    Optional<Todos> findByIdAndMembers(Long id, Members member);
    boolean existsByIdAndMembers(Long id, Members member);
    
    @Query("SELECT t FROM Todos t JOIN FETCH t.members WHERE t.members.id = :memberId AND t.isStorage = true")
    List<Todos> findDraftTodosByMemberId(@Param("memberId") Long memberId);
    
    @Query("SELECT t FROM Todos t WHERE t.alarmDate IS NOT NULL AND t.alarmDate <= :currentTime AND t.isDone = false")
    List<Todos> findTodosWithAlarmTimeBefore(@Param("currentTime") LocalDateTime currentTime);
}