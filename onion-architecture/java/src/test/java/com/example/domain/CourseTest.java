package com.example.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void shouldCreateCourseWithIdAndTitle() {
        Long id = 1L;
        String title = "Java Programming";

        Course course = new Course(id, title);

        assertEquals(id, course.getId());
        assertEquals(title, course.getTitle());
    }

    @Test
    void shouldReturnTrueForEqualCoursesWithSameId() {
        Course course1 = new Course(1L, "Java Programming");
        Course course2 = new Course(1L, "Different Title");

        assertTrue(course1.equals(course2));
        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    void shouldReturnFalseForCoursesWithDifferentIds() {
        Course course1 = new Course(1L, "Java Programming");
        Course course2 = new Course(2L, "Java Programming");

        assertFalse(course1.equals(course2));
        assertNotEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    void shouldReturnFalseWhenComparingWithNull() {
        Course course = new Course(1L, "Java Programming");

        assertFalse(course.equals(null));
    }

    @Test
    void shouldReturnFalseWhenComparingWithDifferentType() {
        Course course = new Course(1L, "Java Programming");
        String notACourse = "Not a course";

        assertFalse(course.equals(notACourse));
    }

    @Test
    void shouldReturnTrueWhenComparingWithItself() {
        Course course = new Course(1L, "Java Programming");

        assertTrue(course.equals(course));
    }

    @Test
    void shouldHaveConsistentHashCode() {
        Course course = new Course(1L, "Java Programming");
        int initialHashCode = course.hashCode();

        assertEquals(initialHashCode, course.hashCode());
        assertEquals(initialHashCode, course.hashCode());
    }
}
