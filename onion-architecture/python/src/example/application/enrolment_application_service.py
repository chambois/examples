"""Application service for handling enrolment operations."""

from ..domain.services.enrolment_service import EnrolmentService
from .ports.course_repository import CourseRepository
from .ports.student_repository import StudentRepository


class EnrolmentApplicationService:
    """Application service coordinating enrolment operations."""

    def __init__(
        self,
        student_repo: StudentRepository,
        course_repo: CourseRepository,
        enrolment_service: EnrolmentService,
    ) -> None:
        """Initialize the application service.

        Args:
            student_repo: Repository for student operations
            course_repo: Repository for course operations
            enrolment_service: Domain service for enrolment logic
        """
        self._student_repo = student_repo
        self._course_repo = course_repo
        self._enrolment_service = enrolment_service

    def enrol(self, student_id: int, course_id: int) -> None:
        """Enroll a student in a course.

        Args:
            student_id: ID of the student to enroll
            course_id: ID of the course to enroll in

        Raises:
            RuntimeError: If student or course not found
        """
        student = self._student_repo.find_by_id(student_id)
        if student is None:
            raise RuntimeError("Student not found")

        course = self._course_repo.find_by_id(course_id)
        if course is None:
            raise RuntimeError("Course not found")

        self._enrolment_service.enrol_student(student, course)
        self._student_repo.save(student)  # Save updated enrolment
