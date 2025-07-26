package com.example.infrastructure.jpa;

import jakarta.persistence.*;

@Entity
public class JpaCourseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    // getters/setters
}
