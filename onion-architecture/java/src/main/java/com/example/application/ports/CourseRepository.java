package com.example.application.ports;

import com.example.domain.Course;
import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findById(Long id);
}