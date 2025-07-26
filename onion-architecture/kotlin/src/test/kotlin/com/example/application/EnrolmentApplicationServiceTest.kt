package com.example.application

import com.example.application.ports.CourseRepository
import com.example.application.ports.StudentRepository
import com.example.domain.Course
import com.example.domain.Student
import com.example.domain.services.EnrolmentService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.*
import org.mockito.junit.jupiter.MockitoExtension

class EnrolmentApplicationServiceTest {

    private val studentRepository = mockk<StudentRepository>()
    private val courseRepository = mockk<CourseRepository>()
    private val enrolmentService = mockk<EnrolmentService>()
    
    private lateinit var applicationService: EnrolmentApplicationService
    private lateinit var student: Student
    private lateinit var course: Course

    @BeforeEach
    fun setUp() {
        applicationService = EnrolmentApplicationService(
            studentRepository, 
            courseRepository, 
            enrolmentService
        )
        student = Student(1L, "John Doe")
        course = Course(1L, "Java Programming")
    }

    @Test
    fun shouldEnrolStudentInCourseSuccessfully() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns student
        every { courseRepository.findById(courseId) } returns course
        every { enrolmentService.enrolStudent(student, course) } just Runs
        every { studentRepository.save(student) } just Runs
        
        applicationService.enrol(studentId, courseId)
        
        verify { studentRepository.findById(studentId) }
        verify { courseRepository.findById(courseId) }
        verify { enrolmentService.enrolStudent(student, course) }
        verify { studentRepository.save(student) }
    }

    @Test
    fun shouldThrowExceptionWhenStudentNotFound() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns null
        
        val exception = assertThrows(RuntimeException::class.java) {
            applicationService.enrol(studentId, courseId)
        }
        
        assertEquals("Student not found", exception.message)
        verify { studentRepository.findById(studentId) }
        verify(exactly = 0) { courseRepository.findById(any()) }
        verify(exactly = 0) { enrolmentService.enrolStudent(any(), any()) }
        verify(exactly = 0) { studentRepository.save(any()) }
    }

    @Test
    fun shouldThrowExceptionWhenCourseNotFound() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns student
        every { courseRepository.findById(courseId) } returns null
        
        val exception = assertThrows(RuntimeException::class.java) {
            applicationService.enrol(studentId, courseId)
        }
        
        assertEquals("Course not found", exception.message)
        verify { studentRepository.findById(studentId) }
        verify { courseRepository.findById(courseId) }
        verify(exactly = 0) { enrolmentService.enrolStudent(any(), any()) }
        verify(exactly = 0) { studentRepository.save(any()) }
    }

    @Test
    fun shouldThrowExceptionWhenBothStudentAndCourseNotFound() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns null
        
        val exception = assertThrows(RuntimeException::class.java) {
            applicationService.enrol(studentId, courseId)
        }
        
        assertEquals("Student not found", exception.message)
        verify { studentRepository.findById(studentId) }
        verify(exactly = 0) { courseRepository.findById(any()) }
        verify(exactly = 0) { enrolmentService.enrolStudent(any(), any()) }
        verify(exactly = 0) { studentRepository.save(any()) }
    }

    @Test
    fun shouldPropagateEnrolmentServiceException() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns student
        every { courseRepository.findById(courseId) } returns course
        every { enrolmentService.enrolStudent(student, course) } throws IllegalStateException("Already enrolled in course")
        
        val exception = assertThrows(IllegalStateException::class.java) {
            applicationService.enrol(studentId, courseId)
        }
        
        assertEquals("Already enrolled in course", exception.message)
        verify { studentRepository.findById(studentId) }
        verify { courseRepository.findById(courseId) }
        verify { enrolmentService.enrolStudent(student, course) }
        verify(exactly = 0) { studentRepository.save(any()) }
    }

    @Test
    fun shouldCallSaveAfterSuccessfulEnrolment() {
        val studentId = 1L
        val courseId = 1L
        
        every { studentRepository.findById(studentId) } returns student
        every { courseRepository.findById(courseId) } returns course
        every { enrolmentService.enrolStudent(student, course) } just Runs
        every { studentRepository.save(student) } just Runs
        
        applicationService.enrol(studentId, courseId)
        
        verify { enrolmentService.enrolStudent(student, course) }
        verify { studentRepository.save(student) }
    }
}