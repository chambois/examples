package com.example.application.ports;

import com.example.domain.Student;
import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(Long id);
    void save(Student student);
}
