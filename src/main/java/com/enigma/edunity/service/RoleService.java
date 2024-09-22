package com.enigma.edunity.service;

import com.enigma.edunity.constant.UserRole;
import com.enigma.edunity.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
