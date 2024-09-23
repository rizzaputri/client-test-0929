package com.enigma.edunity.specification;

import com.enigma.edunity.entity.Application;
import org.springframework.data.jpa.domain.Specification;

public class ApplicationSpecification {
    public static Specification<Application> hasStudentAndSubjectAndDayAndTime(
            String student, String subject, String day, String time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, subjectPredicate, dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubjectAndDay(
            String student, String subject, String day
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(studentPredicate, subjectPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubjectAndTime(
            String student, String subject, String time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, subjectPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndDayAndTime(
            String student, String day, String time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, dayPredicate, timePredicate, timePredicate);
        };
    }

    public static Specification<Application> hasSubjectAndDayAndTime(
            String subject, String day, String time
    ) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(subjectPredicate, dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubject(String student, String subject) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            return cb.and(studentPredicate, subjectPredicate);
        };
    }

    public static Specification<Application> hasStudentAndDay(String student, String day) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(studentPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasStudentAndTime(
            String student, String time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(studentJoin.get("name"), "%" + student + "%");

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasSubjectAndDay(String subject, String day) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(subjectPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasSubjectAndTime(String subject, String time) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.equal(subjectJoin.get("subjectName"), subject);

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(subjectPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasDayAndTime(String day, String time) {
        return (root, query, cb) -> {
            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudent(String student) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            return cb.like(studentJoin.get("name"), "%" + student + "%");
        };
    }

    public static Specification<Application> hasSubject(String subject) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            return cb.equal(subjectJoin.get("subjectName"), subject);
        };
    }

    public static Specification<Application> hasDay(String day) {
        return (root, query, cb) -> cb.equal(root.get("day"), day);
    }

    public static Specification<Application> hasTime(String time) {
        return (root, query, cb) -> cb.equal(root.get("time"), time);
    }
}
