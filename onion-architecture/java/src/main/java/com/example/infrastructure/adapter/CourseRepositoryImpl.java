package com.example.infrastructure.adapter;

import com.example.application.ports.CourseRepository;
import com.example.domain.Course;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final Map<Long, Course> store = new HashMap<>();

    public CourseRepositoryImpl() {
        // Add some sample courses
        store.put(1L, new Course(1L, "Java Programming"));
        store.put(2L, new Course(2L, "Spring Boot"));
        store.put(3L, new Course(3L, "Microservices"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
