package com.womantech.mowo.domain.todo.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import com.womantech.mowo.domain.member.entity.Members;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todos extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members members;

    @Column(nullable = false)
    private LocalDate todoDate;

    private LocalDate completeDate;

    private LocalDate alarmDate;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isFixed = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private TodoCategory category;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isStorage = false;
}
