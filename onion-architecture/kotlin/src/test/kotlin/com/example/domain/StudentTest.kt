package com.example.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*

class StudentTest {

    private lateinit var student: Student
    private lateinit var javaCourse: Course
    private lateinit var springCourse: Course

    @BeforeEach
    fun setUp() {
        student = Student(1L, "John Doe")
        javaCourse = Course(1L, "Java Programming")
        springCourse = Course(2L, "Spring Boot")
    }

    @Test
    fun shouldCreateStudentWithIdAndName() {
        val id = 1L
        val name = "John Doe"
        
        val student = Student(id, name)
        
        assertEquals(id, student.id)
        assertTrue(student.getEnrolledCourses().isEmpty())
    }

    @Test
    fun shouldEnrollStudentInCourse() {
        student.enrol(javaCourse)
        
        assertTrue(student.getEnrolledCourses().contains(javaCourse))
        assertEquals(1, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldEnrollStudentInMultipleCourses() {
        student.enrol(javaCourse)
        student.enrol(springCourse)
        
        assertTrue(student.getEnrolledCourses().contains(javaCourse))
        assertTrue(student.getEnrolledCourses().contains(springCourse))
        assertEquals(2, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldThrowExceptionWhenEnrollingInSameCourseAgain() {
        student.enrol(javaCourse)
        
        val exception = assertThrows(IllegalStateException::class.java) {
            student.enrol(javaCourse)
        }
        
        assertEquals("Already enrolled in course", exception.message)
        assertEquals(1, student.getEnrolledCourses().size)
    }

    @Test
    fun shouldNotEnrollInDuplicateCoursesBasedOnCourseEquals() {
        val sameCourse = Course(1L, "Different Title But Same ID")
        
        student.enrol(javaCourse)
        
        val exception = assertThrows(IllegalStateException::class.java) {
            student.enrol(sameCourse)
        }
        
        assertEquals("Already enrolled in course", exception.message)
    }

    @Test
    fun shouldReturnEmptySetInitially() {
        val newStudent = Student(2L, "Jane Doe")
        
        assertTrue(newStudent.getEnrolledCourses().isEmpty())
    }

    @Test
    fun shouldReturnModifiableSetOfEnrolledCourses() {
        student.enrol(javaCourse)
        
        assertEquals(1, student.getEnrolledCourses().size)
        assertTrue(student.getEnrolledCourses().contains(javaCourse))
    }
}