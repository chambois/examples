"""Tests for the EnrolmentApplicationService."""

from unittest.mock import Mock

import pytest

from src.example.application.enrolment_application_service import EnrolmentApplicationService
from src.example.application.ports.course_repository import CourseRepository
from src.example.application.ports.student_repository import StudentRepository
from src.example.domain.entities.course import Course
from src.example.domain.entities.student import Student
from src.example.domain.services.enrolment_service import EnrolmentService


class TestEnrolmentApplicationService:
    """Test cases for EnrolmentApplicationService."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        self.student_repository = Mock(spec=StudentRepository)
        self.course_repository = Mock(spec=CourseRepository)
        self.enrolment_service = Mock(spec=EnrolmentService)

        self.application_service = EnrolmentApplicationService(
            self.student_repository,
            self.course_repository,
            self.enrolment_service,
        )

        self.student = Student(1, "John Doe")
        self.course = Course(1, "Java Programming")

    def test_should_enrol_student_in_course_successfully(self) -> None:
        """Test successful student enrolment."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = self.student
        self.course_repository.find_by_id.return_value = self.course

        self.application_service.enrol(student_id, course_id)

        self.student_repository.find_by_id.assert_called_once_with(student_id)
        self.course_repository.find_by_id.assert_called_once_with(course_id)
        self.enrolment_service.enrol_student.assert_called_once_with(self.student, self.course)
        self.student_repository.save.assert_called_once_with(self.student)

    def test_should_raise_exception_when_student_not_found(self) -> None:
        """Test exception when student not found."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = None
        self.course_repository.find_by_id.return_value = self.course

        with pytest.raises(RuntimeError, match="Student not found"):
            self.application_service.enrol(student_id, course_id)

        self.student_repository.find_by_id.assert_called_once_with(student_id)
        self.course_repository.find_by_id.assert_not_called()
        self.enrolment_service.enrol_student.assert_not_called()
        self.student_repository.save.assert_not_called()

    def test_should_raise_exception_when_course_not_found(self) -> None:
        """Test exception when course not found."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = self.student
        self.course_repository.find_by_id.return_value = None

        with pytest.raises(RuntimeError, match="Course not found"):
            self.application_service.enrol(student_id, course_id)

        self.student_repository.find_by_id.assert_called_once_with(student_id)
        self.course_repository.find_by_id.assert_called_once_with(course_id)
        self.enrolment_service.enrol_student.assert_not_called()
        self.student_repository.save.assert_not_called()

    def test_should_raise_exception_when_both_student_and_course_not_found(self) -> None:
        """Test exception when both student and course not found."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = None
        self.course_repository.find_by_id.return_value = None

        with pytest.raises(RuntimeError, match="Student not found"):
            self.application_service.enrol(student_id, course_id)

        self.student_repository.find_by_id.assert_called_once_with(student_id)
        self.course_repository.find_by_id.assert_not_called()
        self.enrolment_service.enrol_student.assert_not_called()
        self.student_repository.save.assert_not_called()

    def test_should_propagate_enrolment_service_exception(self) -> None:
        """Test propagation of enrolment service exceptions."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = self.student
        self.course_repository.find_by_id.return_value = self.course
        self.enrolment_service.enrol_student.side_effect = ValueError("Already enrolled in course")

        with pytest.raises(ValueError, match="Already enrolled in course"):
            self.application_service.enrol(student_id, course_id)

        self.student_repository.find_by_id.assert_called_once_with(student_id)
        self.course_repository.find_by_id.assert_called_once_with(course_id)
        self.enrolment_service.enrol_student.assert_called_once_with(self.student, self.course)
        self.student_repository.save.assert_not_called()

    def test_should_call_save_after_successful_enrolment(self) -> None:
        """Test that save is called after successful enrolment."""
        student_id = 1
        course_id = 1

        self.student_repository.find_by_id.return_value = self.student
        self.course_repository.find_by_id.return_value = self.course

        self.application_service.enrol(student_id, course_id)

        self.enrolment_service.enrol_student.assert_called_once_with(self.student, self.course)
        self.student_repository.save.assert_called_once_with(self.student)
