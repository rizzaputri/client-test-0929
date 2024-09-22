package com.enigma.edunity.entity;

import com.enigma.edunity.constant.City;
import com.enigma.edunity.constant.Province;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private City city;

    private Province province;
}
