package com.enigma.edunity.service.impl;

import com.enigma.edunity.constant.City;
import com.enigma.edunity.constant.Province;
import com.enigma.edunity.dto.request.UpdateTutorRequest;
import com.enigma.edunity.dto.response.SubjectResponse;
import com.enigma.edunity.dto.response.TutorResponse;
import com.enigma.edunity.dto.response.UpdateTutorResponse;
import com.enigma.edunity.entity.Subject;
import com.enigma.edunity.entity.Tutor;
import com.enigma.edunity.repository.TutorRepository;
import com.enigma.edunity.service.SubjectService;
import com.enigma.edunity.service.TutorService;
import com.enigma.edunity.service.UserService;
import com.enigma.edunity.specification.TutorSpecification;
import com.enigma.edunity.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final TutorRepository tutorRepository;

    private final UserService userService;
    private final SubjectService subjectService;

    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerTutor(Tutor tutor) {
        tutorRepository.saveAndFlush(tutor);
    }

    @Transactional(readOnly = true)
    @Override
    public Tutor getById(String id) {
        return tutorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Tutor getByUsername() {
        String username = userService.getByContext().getUsername();
        return tutorRepository.findByUserAccountUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor not found")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public TutorResponse getTutorById(String id) {
        List<String> roles = userService.getByContext().getAuthorities().stream()
                .map(Object::toString).toList();
        if (roles.contains("ROLE_ADMIN")) {
            Tutor tutor = getById(id);
            return ResponseBuilder(tutor);
        } else if (roles.contains("ROLE_TUTOR")) {
            Tutor tutor = getByUsername();
            return ResponseBuilder(tutor);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have permission to access this resource");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TutorResponse> getAllTutors(Integer page, Integer size, String name, String subject, String city) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Tutor> specification = Specification.where(null);

        Page<Tutor> tutors;
        if (name != null && subject != null) {
            if (city != null) {
                specification = specification.and(TutorSpecification.hasNameAndSubjectAndCity(name, subject, city));
            } else {
                specification = specification.and(TutorSpecification.hasNameAndSubject(name, subject));
            }
        }
        if (name != null) {
            if (city != null) {
                specification = specification.and(TutorSpecification.hasNameAndCity(name, city));
            } else {
                specification = specification.and(TutorSpecification.hasName(name));
            }
        }
        if (subject != null) {
            if (city != null) {
                specification = specification.and(TutorSpecification.hasSubjectAndCity(subject, city));
            } else {
                specification = specification.and(TutorSpecification.hasSubject(subject));
            }
        }
        if (city != null) {
            specification = specification.and(TutorSpecification.hasCity(city));
        }
        tutors = tutorRepository.findAll(specification, pageable);

        return tutors.map(this::ResponseBuilder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateTutorResponse updateTutor(UpdateTutorRequest request) {
        validationUtil.validate(request);
        Tutor tutor = getByUsername();
        if (request.getUsername() != null) {
            if (userService.findByUsername(tutor.getUserAccount().getUsername()) != null) {
                throw new IllegalArgumentException("Username already exist");
            }
            tutor.getUserAccount().setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            String hashPassword = passwordEncoder.encode(request.getPassword());
            tutor.getUserAccount().setPassword(hashPassword);
        }
        if (request.getName() != null) {
            tutor.setName(request.getName());
        }
        if (request.getEmail() != null) {
            tutor.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            tutor.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getLocation() != null) {
            tutor.getLocation().setCity(City.valueOf(request.getLocation().getCity()));
            tutor.getLocation().setProvince(Province.valueOf(request.getLocation().getProvince()));
        }
        if (request.getSubjectIds() != null) {
            List<Subject> subjects = request.getSubjectIds().stream()
                    .map(subjectService::getById).toList();
            tutor.setSubjects(subjects);
        }
        return UpdateTutorResponse.builder().build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTutor(String id) {
        String userAccountId = getById(id).getUserAccount().getId();
        tutorRepository.deleteById(id);
        userService.deleteUserAccount(userAccountId);
    }

    private TutorResponse ResponseBuilder(Tutor tutor) {
        return TutorResponse.builder()
                .tutorId(tutor.getId())
                .name(tutor.getName())
                .subjects(tutor.getSubjects().stream()
                        .map(subject -> SubjectResponse.builder()
                                .subjectId(subject.getId())
                                .subjectName(subject.getSubjectName())
                                .build())
                        .toList())
                .location(tutor.getLocation())
                .build();
    }
}
