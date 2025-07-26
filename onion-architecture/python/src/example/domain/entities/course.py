"""Course domain entity."""

from dataclasses import dataclass


@dataclass(frozen=True)
class Course:
    """A course that students can enroll in."""

    id: int
    title: str

    def __eq__(self, other: object) -> bool:
        """Check equality based on ID only (like Java/Kotlin versions)."""
        if not isinstance(other, Course):
            return False
        return self.id == other.id

    def __hash__(self) -> int:
        """Generate hash based on ID only."""
        return hash(self.id)
