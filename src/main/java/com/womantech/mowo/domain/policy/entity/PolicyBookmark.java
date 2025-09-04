package com.womantech.mowo.domain.policy.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import com.womantech.mowo.domain.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "policy_bookmark", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "policy_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;
}