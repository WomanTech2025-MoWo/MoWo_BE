package com.womantech.mowo.domain.todo.service;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.entity.Notifications;
import com.womantech.mowo.domain.member.repository.MemberRepository;
import com.womantech.mowo.domain.member.repository.NotificationRepository;
import com.womantech.mowo.domain.todo.converter.TodoConverter;
import com.womantech.mowo.domain.todo.dto.TodoRequestDTO;
import com.womantech.mowo.domain.todo.dto.TodoResponseDTO;
import com.womantech.mowo.domain.todo.entity.TodoCategory;
import com.womantech.mowo.domain.todo.entity.Todos;
import com.womantech.mowo.domain.todo.repository.TodoRepository;
import com.womantech.mowo.global.apiPayload.code.status.ErrorStatus;
import com.womantech.mowo.global.apiPayload.exception.handler.MemberHandler;
import com.womantech.mowo.global.apiPayload.exception.handler.TodoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.womantech.mowo.domain.todo.converter.TodoConverter.toSaveTodos;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public List<TodoResponseDTO.GetNotificationResponseDTO> getNotificationResponseDTOList(Long memberId){
        Members members = findMembersById(memberId);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        
        List<Notifications> notifications = notificationRepository.findByMembersAndCreatedAtBetween(
                members, startOfDay, endOfDay);
        
        return notifications.stream()
                .map(notification -> TodoResponseDTO.GetNotificationResponseDTO.builder()
                        .content(notification.getContent())
                        .todoCategory(notification.getTodoCategory())
                        .build())
                .toList();
    }

    public List<TodoResponseDTO.CountTodo> getTodoCategoryCount(Long memberId) {
        Members member = findMembersById(memberId);
        LocalDate today = LocalDate.now();
        
        return Arrays.stream(TodoCategory.values())
                .map(category -> {
                    Long count = todoRepository.countByMembersAndTodoDateAndCategory(member, today, category);
                    return TodoResponseDTO.CountTodo.builder()
                            .todoCategory(category)
                            .count(count.intValue())
                            .build();
                })
                .toList();
    }

    public TodoResponseDTO.TodoListResponseDTO getMyTodos(Long memberId, LocalDate date){
        Members member = findMembersById(memberId);
        List<Todos> todos = member.getTodosList();

        TodoResponseDTO.TodoCategoryDTO workCategory = getTodoCategoryWithCounts(todos, TodoCategory.WORK, date);
        TodoResponseDTO.TodoCategoryDTO healthCategory = getTodoCategoryWithCounts(todos, TodoCategory.HEALTH, date);
        TodoResponseDTO.TodoCategoryDTO personalCategory = getTodoCategoryWithCounts(todos, TodoCategory.PERSONAL, date);

        return TodoResponseDTO.TodoListResponseDTO.builder()
                .work(workCategory)
                .health(healthCategory)
                .personal(personalCategory)
                .build();
    }

    public Long createTodo(Long memberId, TodoRequestDTO.CreateTodoDTO request){
        Members member = findMembersById(memberId);
        Todos newTodos = toSaveTodos(member, request);
        todoRepository.save(newTodos);
        return newTodos.getId();
    }

    public Long draftTodo(Long memberId, TodoRequestDTO.CreateTodoDTO request){
        Members member = findMembersById(memberId);
        Todos newTodos = toSaveTodos(member, request);;
        todoRepository.save(newTodos);
        return newTodos.getId();
    }

    public List<TodoResponseDTO.TodoInfoDTO> getDraftTodos(Long memberId){
        List<Todos> draftTodos = todoRepository.findDraftTodosByMemberId(memberId);
        return draftTodos.stream()
                .map(TodoConverter::toTodoInfoDTO)
                .toList();
    }

    public void deleteTodo(Long memberId, Long todoId){
        Members member = findMembersById(memberId);
        Todos targetTodo = findTodosById(todoId);
        
        validateTodoOwnership(member, targetTodo);
        todoRepository.delete(targetTodo);
    }

    public Long updateTodo(Long memberId, Long todoId, TodoRequestDTO.UpdateTodoDTO request){
        Members member = findMembersById(memberId);
        Todos targetTodo = findTodosById(todoId);
        
        validateTodoOwnership(member, targetTodo);
        updateTodoFields(targetTodo, request);
        
        todoRepository.save(targetTodo);
        return targetTodo.getId();
    }

    public void completeTodo(Long memberId, Long todoId) {
        Members member = findMembersById(memberId);
        Todos targetTodo = findTodosById(todoId);

        validateTodoOwnership(member, targetTodo);

        targetTodo.setIsDone(true);
        targetTodo.setCompleteDate(LocalDate.now());

        todoRepository.save(targetTodo);
    }

    public List<TodoResponseDTO.TodoDTO> searchTodos(Long memberId, String keyword) {
        Members member = findMembersById(memberId);
        List<Todos> todos = member.getTodosList();

        return todos.stream()
                .filter(todo -> !todo.getIsStorage() &&
                        todo.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .map(TodoConverter::toTodoDTO)
                .toList();
    }

    public TodoResponseDTO.TodoDTO getTodoDetail(Long memberId, Long todoId) {
        Members member = findMembersById(memberId);
        Todos targetTodo = findTodosById(todoId);

        validateTodoOwnership(member, targetTodo);

        return TodoConverter.toTodoDTO(targetTodo);
    }

    public List<TodoResponseDTO.TodoDTO> getMonthlyTodos(Long memberId, int year, int month) {
        Members member = findMembersById(memberId);
        List<Todos> todos = member.getTodosList();

        return todos.stream()
                .filter(todo -> !todo.getIsStorage() &&
                        todo.getTodoDate().getYear() == year &&
                        todo.getTodoDate().getMonthValue() == month)
                .sorted((t1, t2) -> t1.getTodoDate().compareTo(t2.getTodoDate()))
                .map(TodoConverter::toTodoDTO)
                .toList();
    }
    
    private void validateTodoOwnership(Members member, Todos todo) {
        if(!member.equals(todo.getMembers())){
            throw new MemberHandler(ErrorStatus._FORBIDDEN);
        }
    }
    
    private void updateTodoFields(Todos todo, TodoRequestDTO.UpdateTodoDTO request) {
        if(request.getTodoDate() != null) {
            todo.setTodoDate(request.getTodoDate());
        }
        if(request.getAlarmDate() != null) {
            todo.setAlarmDate(LocalDateTime.of(request.getAlarmDate(), java.time.LocalTime.MIDNIGHT));
        }
        if(request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if(request.getMemo() != null) {
            todo.setMemo(request.getMemo());
        }
        if(request.getIsFixed() != null) {
            todo.setIsFixed(request.getIsFixed());
        }
        if(request.getCategory() != null) {
            todo.setCategory(request.getCategory());
        }
    }

    private Members findMembersById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Todos findTodosById(Long id){
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoHandler(ErrorStatus.TODO_NOT_FOUND));
    }

    private TodoResponseDTO.TodoCategoryDTO getTodoCategoryWithCounts(List<Todos> todos, TodoCategory category, LocalDate date) {
        List<Todos> categoryTodos = todos.stream()
                .filter(todo -> todo.getCategory() == category &&
                        !todo.getIsStorage() &&
                        todo.getTodoDate().equals(date))
                .toList();

        List<TodoResponseDTO.TodoDTO> todoDTOs = categoryTodos.stream()
                .map(TodoConverter::toTodoDTO)
                .toList();

        int totalCount = categoryTodos.size();
        int completedCount = (int) categoryTodos.stream()
                .filter(Todos::getIsDone)
                .count();

        return TodoResponseDTO.TodoCategoryDTO.builder()
                .todos(todoDTOs)
                .totalCount(totalCount)
                .completedCount(completedCount)
                .build();
    }
}