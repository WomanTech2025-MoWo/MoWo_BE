package com.womantech.mowo.domain.knowhow.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.policy.entity.PolicyTodo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "knowhows")
public class Knowhow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // 작성자 (ADMIN)
    private Members member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "knowhow", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<KnowhowTodo> knowhowTodos = new ArrayList<>();
}
