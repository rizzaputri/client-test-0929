package com.enigma.edunity.entity;

import com.enigma.edunity.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRole roleName;
}
