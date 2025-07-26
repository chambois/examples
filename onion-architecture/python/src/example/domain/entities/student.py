"""Student domain entity."""

from .course import Course


class Student:
    """A student who can enroll in courses."""

    def __init__(self, student_id: int, name: str) -> None:
        """Initialize a new student."""
        self.id = student_id
        self.name = name
        self._enrolled_courses: set[Course] = set()

    def enrol(self, course: Course) -> None:
        """Enroll the student in a course.

        Args:
            course: The course to enroll in

        Raises:
            ValueError: If already enrolled in the course
        """
        if course in self._enrolled_courses:
            raise ValueError("Already enrolled in course")
        self._enrolled_courses.add(course)

    def get_enrolled_courses(self) -> set[Course]:
        """Get a copy of enrolled courses."""
        return self._enrolled_courses.copy()
