"""Domain service for student enrolment."""

from ..entities.course import Course
from ..entities.student import Student


class EnrolmentService:
    """Domain service for handling student enrolment business logic."""

    def enrol_student(self, student: Student, course: Course) -> None:
        """Enroll a student in a course.

        Args:
            student: The student to enroll
            course: The course to enroll in
        """
        student.enrol(course)
