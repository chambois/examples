package com.example.infrastructure.adapter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domain.Course;

class CourseRepositoryImplTest {

    private CourseRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new CourseRepositoryImpl();
    }

    @Test
    void shouldFindExistingCourseById() {
        Optional<Course> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Java Programming", result.get().getTitle());
    }

    @Test
    void shouldFindAllPreloadedCourses() {
        Optional<Course> javaCourse = repository.findById(1L);
        Optional<Course> springCourse = repository.findById(2L);
        Optional<Course> microservicesCourse = repository.findById(3L);

        assertTrue(javaCourse.isPresent());
        assertEquals("Java Programming", javaCourse.get().getTitle());

        assertTrue(springCourse.isPresent());
        assertEquals("Spring Boot", springCourse.get().getTitle());

        assertTrue(microservicesCourse.isPresent());
        assertEquals("Microservices", microservicesCourse.get().getTitle());
    }

    @Test
    void shouldReturnEmptyOptionalForNonExistentCourse() {
        Optional<Course> result = repository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalForNullId() {
        Optional<Course> result = repository.findById(null);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnSameCourseInstanceForMultipleCalls() {
        Optional<Course> first = repository.findById(1L);
        Optional<Course> second = repository.findById(1L);

        assertTrue(first.isPresent());
        assertTrue(second.isPresent());
        assertSame(first.get(), second.get());
    }

    @Test
    void shouldHavePreloadedCoursesWithCorrectIds() {
        Optional<Course> course1 = repository.findById(1L);
        Optional<Course> course2 = repository.findById(2L);
        Optional<Course> course3 = repository.findById(3L);

        assertTrue(course1.isPresent());
        assertTrue(course2.isPresent());
        assertTrue(course3.isPresent());

        assertEquals(1L, course1.get().getId());
        assertEquals(2L, course2.get().getId());
        assertEquals(3L, course3.get().getId());
    }
}
