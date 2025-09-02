package com.womantech.mowo.domain.policy.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import com.womantech.mowo.domain.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "policy", indexes = {
    @Index(name = "idx_policy_region_code", columnList = "regionCode"),
    @Index(name = "idx_policy_start_date", columnList = "startDate"),
    @Index(name = "idx_policy_end_date", columnList = "endDate"),
    @Index(name = "idx_policy_region_start", columnList = "regionCode, startDate"),
    @Index(name = "idx_policy_region_end", columnList = "regionCode, endDate")
})
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegionCode regionCode;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PolicyTodo> policyTodos = new ArrayList<>();
}
