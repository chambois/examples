"""In-memory implementation of the course repository."""

from ...application.ports.course_repository import CourseRepository
from ...domain.entities.course import Course


class CourseRepositoryImpl(CourseRepository):
    """In-memory implementation of the course repository."""

    def __init__(self) -> None:
        """Initialize the repository with sample data."""
        self._store: dict[int, Course] = {
            1: Course(1, "Java Programming"),
            2: Course(2, "Spring Boot"),
            3: Course(3, "Microservices"),
        }

    def find_by_id(self, course_id: int) -> Course | None:
        """Find a course by its ID.

        Args:
            course_id: The ID of the course to find

        Returns:
            The course if found, None otherwise
        """
        return self._store.get(course_id)
