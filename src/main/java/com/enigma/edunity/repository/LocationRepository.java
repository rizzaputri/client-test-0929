package com.enigma.edunity.repository;

import com.enigma.edunity.constant.City;
import com.enigma.edunity.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByCity(City city);
}
