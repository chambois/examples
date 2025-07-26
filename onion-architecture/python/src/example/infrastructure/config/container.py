"""Dependency injection container configuration."""

from dependency_injector import containers, providers

from ...application.enrolment_application_service import EnrolmentApplicationService
from ...domain.services.enrolment_service import EnrolmentService
from ..adapters.course_repository_impl import CourseRepositoryImpl
from ..adapters.student_repository_impl import StudentRepositoryImpl


class Container(containers.DeclarativeContainer):
    """IoC container for dependency injection."""

    # Configuration
    config = providers.Configuration()

    # Repositories
    course_repository = providers.Factory(CourseRepositoryImpl)
    student_repository = providers.Factory(StudentRepositoryImpl)

    # Domain services
    enrolment_service = providers.Factory(EnrolmentService)

    # Application services
    enrolment_application_service = providers.Factory(
        EnrolmentApplicationService,
        student_repo=student_repository,
        course_repo=course_repository,
        enrolment_service=enrolment_service,
    )
