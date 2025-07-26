"""Tests for the Course domain entity."""

from src.example.domain.entities.course import Course


class TestCourse:
    """Test cases for Course entity."""

    def test_should_create_course_with_id_and_title(self) -> None:
        """Test course creation with ID and title."""
        course_id = 1
        title = "Java Programming"

        course = Course(course_id, title)

        assert course.id == course_id
        assert course.title == title

    def test_should_return_true_for_equal_courses_with_same_id(self) -> None:
        """Test equality based on ID only."""
        course1 = Course(1, "Java Programming")
        course2 = Course(1, "Different Title")

        assert course1 == course2
        assert hash(course1) == hash(course2)

    def test_should_return_false_for_courses_with_different_ids(self) -> None:
        """Test inequality for different IDs."""
        course1 = Course(1, "Java Programming")
        course2 = Course(2, "Java Programming")

        assert course1 != course2
        assert hash(course1) != hash(course2)

    def test_should_return_false_when_comparing_with_none(self) -> None:
        """Test comparison with None."""
        course = Course(1, "Java Programming")

        assert course != None  # noqa: E711

    def test_should_return_false_when_comparing_with_different_type(self) -> None:
        """Test comparison with different type."""
        course = Course(1, "Java Programming")
        not_a_course = "Not a course"

        assert course != not_a_course

    def test_should_return_true_when_comparing_with_itself(self) -> None:
        """Test self-comparison."""
        course = Course(1, "Java Programming")

        assert course == course

    def test_should_have_consistent_hash_code(self) -> None:
        """Test hash consistency."""
        course = Course(1, "Java Programming")
        initial_hash = hash(course)

        assert hash(course) == initial_hash
        assert hash(course) == initial_hash
