package com.enigma.edunity.service;

import com.enigma.edunity.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount findByUsername(String username);
    UserAccount getByUserId(String id);
    UserAccount getByContext();
    void deleteUserAccount(String id);
}
