package com.enigma.edunity.specification;

import com.enigma.edunity.entity.Tutor;
import org.springframework.data.jpa.domain.Specification;

public class TutorSpecification {
    public static Specification<Tutor> hasNameAndSubjectAndCity(
            String name, String subject, String city
    ) {
        return (root, query, cb) -> {
            var namePredicate = cb.like(root.get("name"), "%" + name + "%");

            var subjectJoin = root.join("subjects");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var locationJoin = root.join("location");
            var cityPredicate = cb.like(cb.lower(locationJoin.get("city")), "%" + city.toLowerCase() + "%");
            return cb.and(namePredicate, subjectPredicate, cityPredicate);
        };
    }

    public static Specification<Tutor> hasNameAndSubject(
            String name, String subject
    ) {
        return (root, query, cb) -> {
            var namePredicate = cb.like(root.get("name"), "%" + name + "%");

            var subjectJoin = root.join("subjects");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);
            return cb.and(namePredicate, subjectPredicate);
        };
    }

    public static Specification<Tutor> hasNameAndCity(
            String name, String city
    ) {
        return (root, query, cb) -> {
            var namePredicate = cb.like(root.get("name"), "%" + name + "%");

            var locationJoin = root.join("location");
            var cityPredicate = cb.like(cb.lower(locationJoin.get("city")), "%" + city.toLowerCase() + "%");
            return cb.and(namePredicate, cityPredicate);
        };
    }

    public static Specification<Tutor> hasSubjectAndCity(
            String subject, String city
    ) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subjects");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var locationJoin = root.join("location");
            var cityPredicate = cb.like(cb.lower(locationJoin.get("city")), "%" + city.toLowerCase() + "%");
            return cb.and(subjectPredicate, cityPredicate);
        };
    }

    public static Specification<Tutor> hasName(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Tutor> hasSubject(String subject) {
        return (root, query, cb) -> cb.equal(root.get("subjects").get("subjectName"), subject);
    }

    public static Specification<Tutor> hasCity(String city) {
        return (root, query, cb) -> cb.like(cb.lower(root.join("location").get("city")), "%" + city.toLowerCase() + "%");
    }
}
