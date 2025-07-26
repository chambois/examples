package com.example.application;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.ports.CourseRepository;
import com.example.application.ports.StudentRepository;
import com.example.domain.Course;
import com.example.domain.Student;
import com.example.domain.services.EnrolmentService;

@ExtendWith(MockitoExtension.class)
class EnrolmentApplicationServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrolmentService enrolmentService;

    private EnrolmentApplicationService applicationService;
    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        applicationService = new EnrolmentApplicationService(
                studentRepository,
                courseRepository,
                enrolmentService
        );
        student = new Student(1L, "John Doe");
        course = new Course(1L, "Java Programming");
    }

    @Test
    void shouldEnrolStudentInCourseSuccessfully() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        applicationService.enrol(studentId, courseId);

        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(enrolmentService).enrolStudent(student, course);
        verify(studentRepository).save(student);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> applicationService.enrol(studentId, courseId)
        );

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(courseRepository, never()).findById(courseId);
        verify(enrolmentService, never()).enrolStudent(any(), any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> applicationService.enrol(studentId, courseId)
        );

        assertEquals("Course not found", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(enrolmentService, never()).enrolStudent(any(), any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenBothStudentAndCourseNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> applicationService.enrol(studentId, courseId)
        );

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(courseRepository, never()).findById(courseId);
        verify(enrolmentService, never()).enrolStudent(any(), any());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldPropagateEnrolmentServiceException() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        doThrow(new IllegalStateException("Already enrolled in course"))
                .when(enrolmentService).enrolStudent(student, course);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> applicationService.enrol(studentId, courseId)
        );

        assertEquals("Already enrolled in course", exception.getMessage());
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(enrolmentService).enrolStudent(student, course);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldCallSaveAfterSuccessfulEnrolment() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        applicationService.enrol(studentId, courseId);

        verify(enrolmentService).enrolStudent(student, course);
        verify(studentRepository).save(student);
    }
}
