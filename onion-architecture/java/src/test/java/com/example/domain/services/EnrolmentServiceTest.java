package com.example.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domain.Course;
import com.example.domain.Student;

class EnrolmentServiceTest {

    private EnrolmentService enrolmentService;
    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        enrolmentService = new EnrolmentService();
        student = new Student(1L, "John Doe");
        course = new Course(1L, "Java Programming");
    }

    @Test
    void shouldEnrolStudentInCourse() {
        enrolmentService.enrolStudent(student, course);

        assertTrue(student.getEnrolledCourses().contains(course));
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void shouldEnrolStudentInMultipleCourses() {
        Course secondCourse = new Course(2L, "Spring Boot");

        enrolmentService.enrolStudent(student, course);
        enrolmentService.enrolStudent(student, secondCourse);

        assertTrue(student.getEnrolledCourses().contains(course));
        assertTrue(student.getEnrolledCourses().contains(secondCourse));
        assertEquals(2, student.getEnrolledCourses().size());
    }

    @Test
    void shouldThrowExceptionWhenEnrollingStudentInSameCourseAgain() {
        enrolmentService.enrolStudent(student, course);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> enrolmentService.enrolStudent(student, course)
        );

        assertEquals("Already enrolled in course", exception.getMessage());
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void shouldHandleMultipleStudentsEnrollingInSameCourse() {
        Student secondStudent = new Student(2L, "Jane Doe");

        enrolmentService.enrolStudent(student, course);
        enrolmentService.enrolStudent(secondStudent, course);

        assertTrue(student.getEnrolledCourses().contains(course));
        assertTrue(secondStudent.getEnrolledCourses().contains(course));
    }

    @Test
    void shouldNotAffectOtherStudentsWhenEnrolmentFails() {
        Student secondStudent = new Student(2L, "Jane Doe");

        enrolmentService.enrolStudent(student, course);
        enrolmentService.enrolStudent(secondStudent, course);

        assertThrows(
                IllegalStateException.class,
                () -> enrolmentService.enrolStudent(student, course)
        );

        assertTrue(secondStudent.getEnrolledCourses().contains(course));
        assertEquals(1, secondStudent.getEnrolledCourses().size());
    }
}
