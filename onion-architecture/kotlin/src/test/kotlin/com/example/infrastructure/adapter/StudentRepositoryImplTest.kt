package com.example.infrastructure.adapter

import com.example.domain.Course
import com.example.domain.Student
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*

class StudentRepositoryImplTest {

    private lateinit var repository: StudentRepositoryImpl
    private lateinit var student: Student

    @BeforeEach
    fun setUp() {
        repository = StudentRepositoryImpl()
        student = Student(1L, "John Doe")
    }

    @Test
    fun shouldReturnNullForNonExistentStudent() {
        val result = repository.findById(1L)

        assertNull(result)
    }

    @Test
    fun shouldSaveAndFindStudentById() {
        repository.save(student)

        val result = repository.findById(1L)

        assertNotNull(result)
        assertEquals(student.id, result!!.id)
        assertSame(student, result)
    }

    @Test
    fun shouldUpdateExistingStudent() {
        repository.save(student)

        val course = Course(1L, "Java Programming")
        student.enrol(course)
        repository.save(student)

        val result = repository.findById(1L)

        assertNotNull(result)
        assertTrue(result!!.getEnrolledCourses().contains(course))
        assertEquals(1, result.getEnrolledCourses().size)
    }

    @Test
    fun shouldSaveMultipleStudents() {
        val student2 = Student(2L, "Jane Doe")

        repository.save(student)
        repository.save(student2)

        val result1 = repository.findById(1L)
        val result2 = repository.findById(2L)

        assertNotNull(result1)
        assertNotNull(result2)
        assertEquals(1L, result1!!.id)
        assertEquals(2L, result2!!.id)
    }

    @Test
    fun shouldReturnNullForZeroId() {
        val result = repository.findById(0L)

        assertNull(result)
    }

    @Test
    fun shouldOverwriteStudentWhenSavingWithSameId() {
        repository.save(student)

        val newStudent = Student(1L, "John Smith")
        repository.save(newStudent)

        val result = repository.findById(1L)

        assertNotNull(result)
        assertSame(newStudent, result)
        assertNotSame(student, result)
    }

    @Test
    fun shouldPersistStudentEnrolments() {
        val javaCourse = Course(1L, "Java Programming")
        val springCourse = Course(2L, "Spring Boot")

        student.enrol(javaCourse)
        student.enrol(springCourse)
        repository.save(student)

        val result = repository.findById(1L)

        assertNotNull(result)
        assertEquals(2, result!!.getEnrolledCourses().size)
        assertTrue(result.getEnrolledCourses().contains(javaCourse))
        assertTrue(result.getEnrolledCourses().contains(springCourse))
    }
}
