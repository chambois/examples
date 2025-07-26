"""Tests for the CourseRepositoryImpl."""

from src.example.infrastructure.adapters.course_repository_impl import CourseRepositoryImpl


class TestCourseRepositoryImpl:
    """Test cases for CourseRepositoryImpl."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        self.repository = CourseRepositoryImpl()

    def test_should_find_existing_course_by_id(self) -> None:
        """Test finding an existing course by ID."""
        result = self.repository.find_by_id(1)

        assert result is not None
        assert result.id == 1
        assert result.title == "Java Programming"

    def test_should_find_all_preloaded_courses(self) -> None:
        """Test finding all preloaded courses."""
        java_course = self.repository.find_by_id(1)
        spring_course = self.repository.find_by_id(2)
        microservices_course = self.repository.find_by_id(3)

        assert java_course is not None
        assert java_course.title == "Java Programming"

        assert spring_course is not None
        assert spring_course.title == "Spring Boot"

        assert microservices_course is not None
        assert microservices_course.title == "Microservices"

    def test_should_return_none_for_nonexistent_course(self) -> None:
        """Test returning None for nonexistent course."""
        result = self.repository.find_by_id(999)

        assert result is None

    def test_should_return_none_for_zero_id(self) -> None:
        """Test returning None for zero ID."""
        result = self.repository.find_by_id(0)

        assert result is None

    def test_should_return_same_course_instance_for_multiple_calls(self) -> None:
        """Test returning same instance for multiple calls."""
        first = self.repository.find_by_id(1)
        second = self.repository.find_by_id(1)

        assert first is not None
        assert second is not None
        assert first is second

    def test_should_have_preloaded_courses_with_correct_ids(self) -> None:
        """Test preloaded courses have correct IDs."""
        course1 = self.repository.find_by_id(1)
        course2 = self.repository.find_by_id(2)
        course3 = self.repository.find_by_id(3)

        assert course1 is not None
        assert course2 is not None
        assert course3 is not None

        assert course1.id == 1
        assert course2.id == 2
        assert course3.id == 3
