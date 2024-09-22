package com.enigma.edunity.service;

import com.enigma.edunity.dto.response.JWTClaims;
import com.enigma.edunity.entity.UserAccount;

public interface JWTService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JWTClaims getClaimsByToken(String token);
}
