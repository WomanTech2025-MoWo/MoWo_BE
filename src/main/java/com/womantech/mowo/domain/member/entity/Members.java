package com.womantech.mowo.domain.member.entity;

import com.womantech.mowo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Members extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String userName;
    private String password;
    private String nickName;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role role;
}
