package com.enigma.edunity.specification;

import com.enigma.edunity.constant.Day;
import com.enigma.edunity.entity.Application;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;

public class ApplicationSpecification {
    public static Specification<Application> hasStudentAndSubjectAndDayAndTime(
            String student, String subject, Day day, LocalTime time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, subjectPredicate, dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubjectAndDay(
            String student, String subject, Day day
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(studentPredicate, subjectPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubjectAndTime(
            String student, String subject, LocalTime time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, subjectPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndDayAndTime(
            String student, Day day, LocalTime time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, dayPredicate, timePredicate, timePredicate);
        };
    }

    public static Specification<Application> hasSubjectAndDayAndTime(
            String subject, Day day, LocalTime time
    ) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(subjectPredicate, dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasStudentAndSubject(String student, String subject) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            return cb.and(studentPredicate, subjectPredicate);
        };
    }

    public static Specification<Application> hasStudentAndDay(String student, Day day) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(studentPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasStudentAndTime(
            String student, LocalTime time
    ) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            var studentPredicate = cb.like(cb.lower(studentJoin.get("name")),
                    "%" + student.toLowerCase() + "%");

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(studentPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasSubjectAndDay(String subject, Day day) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var dayPredicate = cb.equal(root.get("day"), day);
            return cb.and(subjectPredicate, dayPredicate);
        };
    }

    public static Specification<Application> hasSubjectAndTime(String subject, LocalTime time) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            var subjectPredicate = cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");

            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(subjectPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasDayAndTime(Day day, LocalTime time) {
        return (root, query, cb) -> {
            var dayPredicate = cb.equal(root.get("day"), day);
            var timePredicate = cb.equal(root.get("time"), time);
            return cb.and(dayPredicate, timePredicate);
        };
    }

    public static Specification<Application> hasTutor(String tutor) {
        return ((root, query, cb) -> {
            var tutorJoin = root.join("tutor");
            return cb.equal((tutorJoin.get("id")), tutor);
        });
    }

    public static Specification<Application> hasStudent(String student) {
        return (root, query, cb) -> {
            var studentJoin = root.join("student");
            return cb.like(cb.lower(cb.lower(studentJoin.get("name"))),
                    "%" + student.toLowerCase() + "%");
        };
    }

    public static Specification<Application> hasSubject(String subject) {
        return (root, query, cb) -> {
            var subjectJoin = root.join("subject");
            return cb.like(cb.lower(subjectJoin.get("subjectName")),
                    "%" + subject.toLowerCase() + "%");
        };
    }

    public static Specification<Application> hasDay(Day day) {
        return (root, query, cb) -> cb.equal(root.get("day"), day);
    }

    public static Specification<Application> hasTime(LocalTime time) {
        return (root, query, cb) -> cb.equal(root.get("time"), time);
    }
}
