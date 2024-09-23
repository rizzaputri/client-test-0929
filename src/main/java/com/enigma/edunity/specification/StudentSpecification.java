package com.enigma.edunity.specification;

import com.enigma.edunity.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> hasNameAndCity(
            String name, String city
    ) {
        return (root, query, cb) -> {
            var namePredicate = cb.like(root.get("name"), "%" + name + "%");

            var locationJoin = root.join("location");
            var cityPredicate = cb.like(cb.lower(locationJoin.get("city")), "%" + city.toLowerCase() + "%");
            return cb.and(namePredicate, cityPredicate);
        };
    }

    public static Specification<Student> hasName(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Student> hasCity(String city) {
        return (root, query, cb) -> cb.like(cb.lower(root.join("location").get("city")), "%" + city.toLowerCase() + "%");
    }
}
