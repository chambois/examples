package com.example.infrastructure.adapter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domain.Course;
import com.example.domain.Student;

class StudentRepositoryImplTest {

    private StudentRepositoryImpl repository;
    private Student student;

    @BeforeEach
    void setUp() {
        repository = new StudentRepositoryImpl();
        student = new Student(1L, "John Doe");
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistentStudent() {
        Optional<Student> result = repository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldSaveAndFindStudentById() {
        repository.save(student);

        Optional<Student> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(student.getId(), result.get().getId());
        assertSame(student, result.get());
    }

    @Test
    void shouldUpdateExistingStudent() {
        repository.save(student);

        Course course = new Course(1L, "Java Programming");
        student.enrol(course);
        repository.save(student);

        Optional<Student> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().getEnrolledCourses().contains(course));
        assertEquals(1, result.get().getEnrolledCourses().size());
    }

    @Test
    void shouldSaveMultipleStudents() {
        Student student2 = new Student(2L, "Jane Doe");

        repository.save(student);
        repository.save(student2);

        Optional<Student> result1 = repository.findById(1L);
        Optional<Student> result2 = repository.findById(2L);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals(1L, result1.get().getId());
        assertEquals(2L, result2.get().getId());
    }

    @Test
    void shouldReturnEmptyOptionalForNullId() {
        Optional<Student> result = repository.findById(null);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldOverwriteStudentWhenSavingWithSameId() {
        repository.save(student);

        Student newStudent = new Student(1L, "John Smith");
        repository.save(newStudent);

        Optional<Student> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertSame(newStudent, result.get());
        assertNotSame(student, result.get());
    }

    @Test
    void shouldPersistStudentEnrolments() {
        Course javaCourse = new Course(1L, "Java Programming");
        Course springCourse = new Course(2L, "Spring Boot");

        student.enrol(javaCourse);
        student.enrol(springCourse);
        repository.save(student);

        Optional<Student> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getEnrolledCourses().size());
        assertTrue(result.get().getEnrolledCourses().contains(javaCourse));
        assertTrue(result.get().getEnrolledCourses().contains(springCourse));
    }

    @Test
    void shouldHandleNullStudentSaveGracefully() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }
}
