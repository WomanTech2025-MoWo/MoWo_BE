package com.womantech.mowo.domain.todo.service;

import com.womantech.mowo.domain.member.converter.UserConverter;
import com.womantech.mowo.domain.member.entity.Notifications;
import com.womantech.mowo.domain.member.repository.NotificationRepository;
import com.womantech.mowo.domain.todo.entity.Todos;
import com.womantech.mowo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoScheduler {
    
    private final TodoRepository todoRepository;
    private final NotificationRepository notificationRepository;
    
    @Scheduled(fixedRate = 600000)
    @Transactional
    public void checkTodoAlarmsAndCreateNotifications() {
        log.info("TodoScheduler: 알림 시간이 된 투두들을 확인합니다.");
        
        LocalDateTime currentTime = LocalDateTime.now();
        List<Todos> todosWithAlarm = todoRepository.findTodosWithAlarmTimeBefore(currentTime);
        
        for (Todos todo : todosWithAlarm) {
            try {
                Notifications notification
                        = UserConverter.toNotifications(todo.getMembers(), todo);
                
                notificationRepository.save(notification);
                log.info("알림 생성 완료: 사용자 ID {}, 투두 제목: {}", 
                        todo.getMembers().getId(), todo.getTitle());

                todo.setAlarmDate(null);
                todoRepository.save(todo);
                
            } catch (Exception e) {
                log.error("알림 생성 중 오류 발생: 투두 ID {}, 오류: {}", 
                        todo.getId(), e.getMessage());
            }
        }
        log.info("TodoScheduler: 총 {}개의 알림을 생성했습니다.", todosWithAlarm.size());
    }
}
