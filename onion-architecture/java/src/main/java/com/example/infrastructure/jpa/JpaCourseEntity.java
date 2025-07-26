package com.example.infrastructure.jpa;

@Entity
public class JpaCourseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    // getters/setters
}
