package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.City;
import com.enigma.edunity.constant.Province;
import com.enigma.edunity.dto.request.LocationRequest;
import com.enigma.edunity.entity.Location;
import com.enigma.edunity.entity.Role;
import com.enigma.edunity.repository.LocationRepository;
import com.enigma.edunity.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    @Override
    public Location getOrSave(LocationRequest location) {
        try {
            return locationRepository.findByCity(City.valueOf(location.getCity()))
                    .orElseGet(() -> locationRepository.saveAndFlush(
                            Location.builder()
                                    .city(City.valueOf(location.getCity()))
                                    .province(Province.valueOf(location.getProvince()))
                                    .build()
                    ));
        } catch (Exception e) {
            throw new IllegalArgumentException("Sorry, location not available yet");
        }
    }
}
