"""REST controller for enrolment operations."""

from dependency_injector.wiring import Provide, inject
from fastapi import APIRouter, Depends, HTTPException

from ..application.enrolment_application_service import EnrolmentApplicationService
from ..infrastructure.config.container import Container

router = APIRouter(prefix="/enrolments", tags=["enrolments"])


@router.post("/")
@inject
async def enrol_student(
    student_id: int,
    course_id: int,
    service: EnrolmentApplicationService = Depends(Provide[Container.enrolment_application_service]),
) -> dict[str, str]:
    """Enroll a student in a course.

    Args:
        student_id: ID of the student to enroll
        course_id: ID of the course to enroll in
        service: Injected enrolment application service

    Returns:
        Success message

    Raises:
        HTTPException: If student or course not found, or enrolment fails
    """
    try:
        service.enrol(student_id, course_id)
        return {"message": f"Student {student_id} enrolled in course {course_id}"}
    except RuntimeError as e:
        raise HTTPException(status_code=404, detail=str(e)) from e
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e)) from e
