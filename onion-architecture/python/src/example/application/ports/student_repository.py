"""Student repository port (interface)."""

from typing import Protocol

from ...domain.entities.student import Student


class StudentRepository(Protocol):
    """Protocol defining the student repository interface."""

    def find_by_id(self, student_id: int) -> Student | None:
        """Find a student by their ID.

        Args:
            student_id: The ID of the student to find

        Returns:
            The student if found, None otherwise
        """
        ...

    def save(self, student: Student) -> None:
        """Save a student.

        Args:
            student: The student to save
        """
        ...
