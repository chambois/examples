package com.example.infrastructure.adapter;

import com.example.application.ports.StudentRepository;
import com.example.domain.Student;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final Map<Long, Student> store = new HashMap<>();

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }
}
