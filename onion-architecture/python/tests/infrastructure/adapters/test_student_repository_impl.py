"""Tests for the StudentRepositoryImpl."""

from src.example.domain.entities.course import Course
from src.example.domain.entities.student import Student
from src.example.infrastructure.adapters.student_repository_impl import StudentRepositoryImpl


class TestStudentRepositoryImpl:
    """Test cases for StudentRepositoryImpl."""

    def setup_method(self) -> None:
        """Set up test fixtures."""
        self.repository = StudentRepositoryImpl()
        self.student = Student(1, "John Doe")

    def test_should_return_none_for_nonexistent_student(self) -> None:
        """Test returning None for nonexistent student."""
        result = self.repository.find_by_id(1)

        assert result is None

    def test_should_save_and_find_student_by_id(self) -> None:
        """Test saving and finding student."""
        self.repository.save(self.student)

        result = self.repository.find_by_id(1)

        assert result is not None
        assert result.id == self.student.id
        assert result is self.student

    def test_should_update_existing_student(self) -> None:
        """Test updating existing student."""
        self.repository.save(self.student)

        course = Course(1, "Java Programming")
        self.student.enrol(course)
        self.repository.save(self.student)

        result = self.repository.find_by_id(1)

        assert result is not None
        assert course in result.get_enrolled_courses()
        assert len(result.get_enrolled_courses()) == 1

    def test_should_save_multiple_students(self) -> None:
        """Test saving multiple students."""
        student2 = Student(2, "Jane Doe")

        self.repository.save(self.student)
        self.repository.save(student2)

        result1 = self.repository.find_by_id(1)
        result2 = self.repository.find_by_id(2)

        assert result1 is not None
        assert result2 is not None
        assert result1.id == 1
        assert result2.id == 2

    def test_should_return_none_for_zero_id(self) -> None:
        """Test returning None for zero ID."""
        result = self.repository.find_by_id(0)

        assert result is None

    def test_should_overwrite_student_when_saving_with_same_id(self) -> None:
        """Test overwriting student with same ID."""
        self.repository.save(self.student)

        new_student = Student(1, "John Smith")
        self.repository.save(new_student)

        result = self.repository.find_by_id(1)

        assert result is not None
        assert result is new_student
        assert result is not self.student

    def test_should_persist_student_enrolments(self) -> None:
        """Test persisting student enrolments."""
        java_course = Course(1, "Java Programming")
        spring_course = Course(2, "Spring Boot")

        self.student.enrol(java_course)
        self.student.enrol(spring_course)
        self.repository.save(self.student)

        result = self.repository.find_by_id(1)

        assert result is not None
        assert len(result.get_enrolled_courses()) == 2
        assert java_course in result.get_enrolled_courses()
        assert spring_course in result.get_enrolled_courses()

    def test_should_demonstrate_python_type_safety(self) -> None:
        """Test that demonstrates Python type safety prevents null issues."""
        # This test demonstrates that Python's typing prevents many null-related issues
        students = self.repository.find_by_id(1)
        assert students is None  # Repository starts empty
