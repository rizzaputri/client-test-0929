package com.enigma.edunity.entity;

import com.enigma.edunity.constant.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Day day;

    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH-mm")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
