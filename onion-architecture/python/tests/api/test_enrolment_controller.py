"""Tests for the EnrolmentController."""

from unittest.mock import Mock

from fastapi.testclient import TestClient

from src.example.api.main import create_app
from src.example.application.enrolment_application_service import EnrolmentApplicationService
from src.example.infrastructure.config.container import Container


class TestEnrolmentController:
    """Test cases for EnrolmentController."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        # Create a test container and mock service
        self.container = Container()
        self.mock_service = Mock(spec=EnrolmentApplicationService)

        # Override the container's service with our mock
        self.container.enrolment_application_service.override(self.mock_service)

        # Wire the container for testing
        self.container.wire(modules=["src.example.api.enrolment_controller"])

        # Create app and client
        self.app = create_app()
        self.client = TestClient(self.app)

    def teardown_method(self) -> None:
        """Clean up after tests."""
        self.container.enrolment_application_service.reset_override()
        self.container.unwire()

    def test_should_enrol_student_in_course_successfully(self) -> None:
        """Test successful student enrolment."""
        student_id = 1
        course_id = 1

        response = self.client.post("/enrolments/", params={"student_id": student_id, "course_id": course_id})

        assert response.status_code == 200
        assert response.json() == {"message": f"Student {student_id} enrolled in course {course_id}"}
        self.mock_service.enrol.assert_called_once_with(student_id, course_id)

    def test_should_accept_valid_student_and_course_ids(self) -> None:
        """Test accepting valid IDs."""
        student_id = 123
        course_id = 456

        response = self.client.post("/enrolments/", params={"student_id": student_id, "course_id": course_id})

        assert response.status_code == 200
        self.mock_service.enrol.assert_called_once_with(student_id, course_id)

    def test_should_return_bad_request_when_student_id_missing(self) -> None:
        """Test bad request when student ID missing."""
        course_id = 1

        response = self.client.post("/enrolments/", params={"course_id": course_id})

        assert response.status_code == 422  # FastAPI validation error
        self.mock_service.enrol.assert_not_called()

    def test_should_return_bad_request_when_course_id_missing(self) -> None:
        """Test bad request when course ID missing."""
        student_id = 1

        response = self.client.post("/enrolments/", params={"student_id": student_id})

        assert response.status_code == 422  # FastAPI validation error
        self.mock_service.enrol.assert_not_called()

    def test_should_return_bad_request_when_both_parameters_missing(self) -> None:
        """Test bad request when both parameters missing."""
        response = self.client.post("/enrolments/")

        assert response.status_code == 422  # FastAPI validation error
        self.mock_service.enrol.assert_not_called()

    def test_should_return_method_not_allowed_for_get_request(self) -> None:
        """Test method not allowed for GET request."""
        response = self.client.get("/enrolments/", params={"student_id": "1", "course_id": "1"})

        assert response.status_code == 405  # Method not allowed
        self.mock_service.enrol.assert_not_called()

    def test_should_handle_runtime_error_from_service(self) -> None:
        """Test handling RuntimeError from service."""
        student_id = 1
        course_id = 1

        self.mock_service.enrol.side_effect = RuntimeError("Student not found")

        response = self.client.post("/enrolments/", params={"student_id": student_id, "course_id": course_id})

        assert response.status_code == 404
        assert "Student not found" in response.json()["detail"]
        self.mock_service.enrol.assert_called_once_with(student_id, course_id)

    def test_should_handle_value_error_from_service(self) -> None:
        """Test handling ValueError from service."""
        student_id = 1
        course_id = 1

        self.mock_service.enrol.side_effect = ValueError("Already enrolled in course")

        response = self.client.post("/enrolments/", params={"student_id": student_id, "course_id": course_id})

        assert response.status_code == 400
        assert "Already enrolled in course" in response.json()["detail"]
        self.mock_service.enrol.assert_called_once_with(student_id, course_id)
