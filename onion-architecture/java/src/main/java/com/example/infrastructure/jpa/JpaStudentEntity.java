package com.example.infrastructure.jpa;

import java.util.*;

@Entity
public class JpaStudentEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany
    private Set<JpaCourseEntity> enrolledCourses = new HashSet<>();

    // getters/setters
}
