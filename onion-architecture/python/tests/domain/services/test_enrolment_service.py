"""Tests for the EnrolmentService domain service."""

import pytest

from src.example.domain.entities.course import Course
from src.example.domain.entities.student import Student
from src.example.domain.services.enrolment_service import EnrolmentService


class TestEnrolmentService:
    """Test cases for EnrolmentService."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        self.enrolment_service = EnrolmentService()
        self.student = Student(1, "John Doe")
        self.course = Course(1, "Java Programming")

    def test_should_enrol_student_in_course(self) -> None:
        """Test enrolling student in course."""
        self.enrolment_service.enrol_student(self.student, self.course)

        assert self.course in self.student.get_enrolled_courses()
        assert len(self.student.get_enrolled_courses()) == 1

    def test_should_enrol_student_in_multiple_courses(self) -> None:
        """Test enrolling student in multiple courses."""
        second_course = Course(2, "Spring Boot")

        self.enrolment_service.enrol_student(self.student, self.course)
        self.enrolment_service.enrol_student(self.student, second_course)

        assert self.course in self.student.get_enrolled_courses()
        assert second_course in self.student.get_enrolled_courses()
        assert len(self.student.get_enrolled_courses()) == 2

    def test_should_raise_exception_when_enrolling_student_in_same_course_again(self) -> None:
        """Test exception when enrolling in same course twice."""
        self.enrolment_service.enrol_student(self.student, self.course)

        with pytest.raises(ValueError, match="Already enrolled in course"):
            self.enrolment_service.enrol_student(self.student, self.course)

        assert len(self.student.get_enrolled_courses()) == 1

    def test_should_handle_multiple_students_enrolling_in_same_course(self) -> None:
        """Test multiple students enrolling in same course."""
        second_student = Student(2, "Jane Doe")

        self.enrolment_service.enrol_student(self.student, self.course)
        self.enrolment_service.enrol_student(second_student, self.course)

        assert self.course in self.student.get_enrolled_courses()
        assert self.course in second_student.get_enrolled_courses()

    def test_should_not_affect_other_students_when_enrolment_fails(self) -> None:
        """Test that failed enrolment doesn't affect other students."""
        second_student = Student(2, "Jane Doe")

        self.enrolment_service.enrol_student(self.student, self.course)
        self.enrolment_service.enrol_student(second_student, self.course)

        with pytest.raises(ValueError):
            self.enrolment_service.enrol_student(self.student, self.course)

        assert self.course in second_student.get_enrolled_courses()
        assert len(second_student.get_enrolled_courses()) == 1
