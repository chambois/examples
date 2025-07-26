"""Course repository port (interface)."""

from typing import Protocol

from ...domain.entities.course import Course


class CourseRepository(Protocol):
    """Protocol defining the course repository interface."""

    def find_by_id(self, course_id: int) -> Course | None:
        """Find a course by its ID.

        Args:
            course_id: The ID of the course to find

        Returns:
            The course if found, None otherwise
        """
        ...
