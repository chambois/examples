package com.example.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CourseTest {

    @Test
    fun shouldCreateCourseWithIdAndTitle() {
        val id = 1L
        val title = "Java Programming"
        
        val course = Course(id, title)
        
        assertEquals(id, course.id)
        assertEquals(title, course.title)
    }

    @Test
    fun shouldReturnTrueForEqualCoursesWithSameId() {
        val course1 = Course(1L, "Java Programming")
        val course2 = Course(1L, "Different Title")
        
        assertTrue(course1 == course2)
        assertEquals(course1.hashCode(), course2.hashCode())
    }

    @Test
    fun shouldReturnFalseForCoursesWithDifferentIds() {
        val course1 = Course(1L, "Java Programming")
        val course2 = Course(2L, "Java Programming")
        
        assertFalse(course1 == course2)
        assertNotEquals(course1.hashCode(), course2.hashCode())
    }

    @Test
    fun shouldReturnFalseWhenComparingWithNull() {
        val course = Course(1L, "Java Programming")
        
        assertFalse(course == null)
    }

    @Test
    fun shouldReturnFalseWhenComparingWithDifferentType() {
        val course = Course(1L, "Java Programming")
        val notACourse = "Not a course"
        
        assertFalse(course.equals(notACourse))
    }

    @Test
    fun shouldReturnTrueWhenComparingWithItself() {
        val course = Course(1L, "Java Programming")
        
        assertTrue(course == course)
    }

    @Test
    fun shouldHaveConsistentHashCode() {
        val course = Course(1L, "Java Programming")
        val initialHashCode = course.hashCode()
        
        assertEquals(initialHashCode, course.hashCode())
        assertEquals(initialHashCode, course.hashCode())
    }
}