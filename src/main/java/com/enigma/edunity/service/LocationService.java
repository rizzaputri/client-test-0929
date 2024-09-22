package com.enigma.edunity.service;

import com.enigma.edunity.dto.request.LocationRequest;
import com.enigma.edunity.entity.Location;

public interface LocationService {
    Location getOrSave(LocationRequest location);
}
