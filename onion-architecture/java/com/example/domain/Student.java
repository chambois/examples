package com.example.domain;

import java.util.HashSet;
import java.util.Set;

public class Student {
    private Long id;
    private String name;
    private Set<Course> enrolledCourses = new HashSet<>();

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void enroll(Course course) {
        if (enrolledCourses.contains(course)) {
            throw new IllegalStateException("Already enrolled in course");
        }
        enrolledCourses.add(course);
    }

    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Long getId() {
        return id;
    }
}
