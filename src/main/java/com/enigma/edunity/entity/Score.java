package com.enigma.edunity.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;
}
