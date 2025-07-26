package com.example.domain.services

import com.example.domain.Course
import com.example.domain.Student
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*

class EnrolmentServiceTest {

    private lateinit var enrolmentService: EnrolmentService
    private lateinit var student: Student
    private lateinit var course: Course

    @BeforeEach
    fun setUp() {
        enrolmentService = EnrolmentService()
        student = Student(1L, "John Doe")
        course = Course(1L, "Java Programming")
    }

    @Test
    fun shouldEnrolStudentInCourse() {
        enrolmentService.enrolStudent(student, course)
        
        assertTrue(student.getEnrolledCourses().contains(course))
        assertEquals(1, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldEnrolStudentInMultipleCourses() {
        val secondCourse = Course(2L, "Spring Boot")
        
        enrolmentService.enrolStudent(student, course)
        enrolmentService.enrolStudent(student, secondCourse)
        
        assertTrue(student.getEnrolledCourses().contains(course))
        assertTrue(student.getEnrolledCourses().contains(secondCourse))
        assertEquals(2, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldThrowExceptionWhenEnrollingStudentInSameCourseAgain() {
        enrolmentService.enrolStudent(student, course)
        
        val exception = assertThrows(IllegalStateException::class.java) {
            enrolmentService.enrolStudent(student, course)
        }
        
        assertEquals("Already enrolled in course", exception.message)
        assertEquals(1, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldHandleMultipleStudentsEnrollingInSameCourse() {
        val secondStudent = Student(2L, "Jane Doe")
        
        enrolmentService.enrolStudent(student, course)
        enrolmentService.enrolStudent(secondStudent, course)
        
        assertTrue(student.getEnrolledCourses().contains(course))
        assertTrue(secondStudent.getEnrolledCourses().contains(course))
    }

    @Test
    fun shouldNotAffectOtherStudentsWhenEnrolmentFails() {
        val secondStudent = Student(2L, "Jane Doe")
        
        enrolmentService.enrolStudent(student, course)
        enrolmentService.enrolStudent(secondStudent, course)
        
        assertThrows(IllegalStateException::class.java) {
            enrolmentService.enrolStudent(student, course)
        }
        
        assertTrue(secondStudent.getEnrolledCourses().contains(course))
        assertEquals(1, secondStudent.getEnrolledCourses().size)
    }
}