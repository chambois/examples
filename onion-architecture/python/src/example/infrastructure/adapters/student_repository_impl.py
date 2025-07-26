"""In-memory implementation of the student repository."""

from ...application.ports.student_repository import StudentRepository
from ...domain.entities.student import Student


class StudentRepositoryImpl(StudentRepository):
    """In-memory implementation of the student repository."""

    def __init__(self) -> None:
        """Initialize the repository with empty storage."""
        self._store: dict[int, Student] = {}

    def find_by_id(self, student_id: int) -> Student | None:
        """Find a student by their ID.

        Args:
            student_id: The ID of the student to find

        Returns:
            The student if found, None otherwise
        """
        return self._store.get(student_id)

    def save(self, student: Student) -> None:
        """Save a student.

        Args:
            student: The student to save
        """
        self._store[student.id] = student
