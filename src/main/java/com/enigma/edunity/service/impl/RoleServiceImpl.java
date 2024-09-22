package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.UserRole;
import com.enigma.edunity.entity.Role;
import com.enigma.edunity.repository.RoleRepository;
import com.enigma.edunity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role getOrSave(UserRole role) {
        try {
            return roleRepository.findByRoleName(role)
                    .orElseGet(() -> roleRepository.saveAndFlush(
                            Role.builder()
                                    .roleName(role)
                                    .build()
                    ));
        } catch (Exception e) {
            throw new IllegalArgumentException("Role not found");
        }
    }
}
