package com.womantech.mowo.domain.knowhow.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "knowhow_todos")
public class KnowhowTodo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowhow_id", nullable = false)
    private Knowhow knowhow;

    @Column(nullable = false)
    private String suggestion;
}
