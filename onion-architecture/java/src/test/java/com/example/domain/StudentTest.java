package com.example.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private Student student;
    private Course javaCourse;
    private Course springCourse;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "John Doe");
        javaCourse = new Course(1L, "Java Programming");
        springCourse = new Course(2L, "Spring Boot");
    }

    @Test
    void shouldCreateStudentWithIdAndName() {
        Long id = 1L;
        String name = "John Doe";

        Student student = new Student(id, name);

        assertEquals(id, student.getId());
        assertTrue(student.getEnrolledCourses().isEmpty());
    }

    @Test
    void shouldEnrollStudentInCourse() {
        student.enrol(javaCourse);

        assertTrue(student.getEnrolledCourses().contains(javaCourse));
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void shouldEnrollStudentInMultipleCourses() {
        student.enrol(javaCourse);
        student.enrol(springCourse);

        assertTrue(student.getEnrolledCourses().contains(javaCourse));
        assertTrue(student.getEnrolledCourses().contains(springCourse));
        assertEquals(2, student.getEnrolledCourses().size());
    }

    @Test
    void shouldThrowExceptionWhenEnrollingInSameCourseAgain() {
        student.enrol(javaCourse);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> student.enrol(javaCourse)
        );

        assertEquals("Already enrolled in course", exception.getMessage());
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void shouldNotEnrollInDuplicateCoursesBasedOnCourseEquals() {
        Course sameCourse = new Course(1L, "Different Title But Same ID");

        student.enrol(javaCourse);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> student.enrol(sameCourse)
        );

        assertEquals("Already enrolled in course", exception.getMessage());
    }

    @Test
    void shouldReturnEmptySetInitially() {
        Student newStudent = new Student(2L, "Jane Doe");

        assertTrue(newStudent.getEnrolledCourses().isEmpty());
    }

    @Test
    void shouldReturnModifiableSetOfEnrolledCourses() {
        student.enrol(javaCourse);

        assertEquals(1, student.getEnrolledCourses().size());
        assertTrue(student.getEnrolledCourses().contains(javaCourse));
    }
}
