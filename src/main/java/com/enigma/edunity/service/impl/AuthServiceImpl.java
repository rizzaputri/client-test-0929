package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.UserRole;
import com.enigma.edunity.dto.request.LoginRequest;
import com.enigma.edunity.dto.request.RegisterStudentRequest;
import com.enigma.edunity.dto.request.RegisterTutorRequest;
import com.enigma.edunity.dto.response.LoginResponse;
import com.enigma.edunity.dto.response.RegisterStudentResponse;
import com.enigma.edunity.dto.response.RegisterTutorResponse;
import com.enigma.edunity.entity.*;
import com.enigma.edunity.repository.UserAccountRepository;
import com.enigma.edunity.service.*;
import com.enigma.edunity.utility.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final LocationService locationService;
    private final StudentService studentService;
    private final TutorService tutorService;
    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtil validationUtil;

    @Value("${edunity.superadmin.username}")
    private String adminUsername;
    @Value("${edunity.superadmin.password}")
    private String adminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initAdmin() {
        Optional<UserAccount> currentUserAdmin = userAccountRepository.findByUsername(adminUsername);
        if (currentUserAdmin.isPresent()) return;

        Role superAdmin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role tutor = roleService.getOrSave(UserRole.ROLE_TUTOR);
        Role student = roleService.getOrSave(UserRole.ROLE_STUDENT);

        UserAccount account = UserAccount.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(List.of(superAdmin, tutor, student))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterStudentResponse registerStudent(RegisterStudentRequest request) {
        // Validate request
        validationUtil.validate(request);

        // Check username, email, phone number existence
        if (userAccountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exist");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // Get role(s)
        Role studentRole = roleService.getOrSave(UserRole.ROLE_STUDENT);

        // Create and save new User Account
        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(studentRole))
                .build();
        userAccountRepository.saveAndFlush(account);

        // Get location
        Location location = locationService.getOrSave(request.getLocation());

        // Create and save new student
        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .userAccount(account)
                .location(location)
                .build();
        studentService.registerStudent(student);

        // Return response
        return RegisterStudentResponse.builder()
                .studentId(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .location(student.getLocation())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterTutorResponse registerTutor(RegisterTutorRequest request) {
        // Validate request
        validationUtil.validate(request);

        // Check username, email, phone number existence
        if (userAccountRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exist");
        }

        // Encode password
        String hashPassword = passwordEncoder.encode(request.getPassword());

        // Get role(s)
        Role tutorRole = roleService.getOrSave(UserRole.ROLE_TUTOR);

        // Create and save new User Account
        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .isEnable(true)
                .roles(List.of(tutorRole))
                .build();
        userAccountRepository.saveAndFlush(account);

        // Get location
        Location location = locationService.getOrSave(request.getLocation());

        // Create and save new tutor
        Tutor tutor = Tutor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .userAccount(account)
                .location(location)
                .build();
        tutorService.registerTutor(tutor);

        // Return response
        return RegisterTutorResponse.builder()
                .tutorId(tutor.getId())
                .name(tutor.getName())
                .email(tutor.getEmail())
                .phoneNumber(tutor.getPhoneNumber())
                .location(tutor.getLocation())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken (userAccount);

        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .token(token)
                .roles (userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
