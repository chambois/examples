package com.example.infrastructure.adapter

import com.example.domain.Course
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*

class CourseRepositoryImplTest {

    private lateinit var repository: CourseRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = CourseRepositoryImpl()
    }

    @Test
    fun shouldFindExistingCourseById() {
        val result = repository.findById(1L)
        
        assertNotNull(result)
        assertEquals(1L, result!!.id)
        assertEquals("Java Programming", result.title)
    }

    @Test
    fun shouldFindAllPreloadedCourses() {
        val javaCourse = repository.findById(1L)
        val springCourse = repository.findById(2L)
        val microservicesCourse = repository.findById(3L)
        
        assertNotNull(javaCourse)
        assertEquals("Java Programming", javaCourse!!.title)
        
        assertNotNull(springCourse)
        assertEquals("Spring Boot", springCourse!!.title)
        
        assertNotNull(microservicesCourse)
        assertEquals("Microservices", microservicesCourse!!.title)
    }

    @Test
    fun shouldReturnNullForNonExistentCourse() {
        val result = repository.findById(999L)
        
        assertNull(result)
    }

    @Test
    fun shouldReturnNullForZeroId() {
        val result = repository.findById(0L)
        
        assertNull(result)
    }

    @Test
    fun shouldReturnSameCourseInstanceForMultipleCalls() {
        val first = repository.findById(1L)
        val second = repository.findById(1L)
        
        assertNotNull(first)
        assertNotNull(second)
        assertSame(first, second)
    }

    @Test
    fun shouldHavePreloadedCoursesWithCorrectIds() {
        val course1 = repository.findById(1L)
        val course2 = repository.findById(2L)
        val course3 = repository.findById(3L)
        
        assertNotNull(course1)
        assertNotNull(course2)
        assertNotNull(course3)
        
        assertEquals(1L, course1!!.id)
        assertEquals(2L, course2!!.id)
        assertEquals(3L, course3!!.id)
    }
}