"""Tests for the Student domain entity."""

import pytest

from src.example.domain.entities.course import Course
from src.example.domain.entities.student import Student


class TestStudent:
    """Test cases for Student entity."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        self.student = Student(1, "John Doe")
        self.java_course = Course(1, "Java Programming")
        self.spring_course = Course(2, "Spring Boot")

    def test_should_create_student_with_id_and_name(self) -> None:
        """Test student creation with ID and name."""
        student_id = 1
        name = "John Doe"

        student = Student(student_id, name)

        assert student.id == student_id
        assert student.name == name
        assert len(student.get_enrolled_courses()) == 0

    def test_should_enroll_student_in_course(self) -> None:
        """Test enrolling student in a course."""
        self.student.enrol(self.java_course)

        assert self.java_course in self.student.get_enrolled_courses()
        assert len(self.student.get_enrolled_courses()) == 1

    def test_should_enroll_student_in_multiple_courses(self) -> None:
        """Test enrolling student in multiple courses."""
        self.student.enrol(self.java_course)
        self.student.enrol(self.spring_course)

        assert self.java_course in self.student.get_enrolled_courses()
        assert self.spring_course in self.student.get_enrolled_courses()
        assert len(self.student.get_enrolled_courses()) == 2

    def test_should_raise_exception_when_enrolling_in_same_course_again(self) -> None:
        """Test exception when enrolling in same course twice."""
        self.student.enrol(self.java_course)

        with pytest.raises(ValueError, match="Already enrolled in course"):
            self.student.enrol(self.java_course)

        assert len(self.student.get_enrolled_courses()) == 1

    def test_should_not_enroll_in_duplicate_courses_based_on_course_equals(self) -> None:
        """Test duplicate course prevention based on course equality."""
        same_course = Course(1, "Different Title But Same ID")

        self.student.enrol(self.java_course)

        with pytest.raises(ValueError, match="Already enrolled in course"):
            self.student.enrol(same_course)

    def test_should_return_empty_set_initially(self) -> None:
        """Test initial empty enrolled courses."""
        new_student = Student(2, "Jane Doe")

        assert len(new_student.get_enrolled_courses()) == 0

    def test_should_return_copy_of_enrolled_courses(self) -> None:
        """Test that enrolled courses returns a copy."""
        self.student.enrol(self.java_course)

        courses = self.student.get_enrolled_courses()
        assert len(courses) == 1
        assert self.java_course in courses

        # Modifying returned set should not affect internal state
        courses.add(self.spring_course)
        assert len(self.student.get_enrolled_courses()) == 1
