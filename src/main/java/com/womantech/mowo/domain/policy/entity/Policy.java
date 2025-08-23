package com.womantech.mowo.domain.policy.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import com.womantech.mowo.domain.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // 작성자 (ADMIN)
    private Members member;

    @Column(nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String regionCode;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PolicyTodo> policyTodos = new ArrayList<>();
}
