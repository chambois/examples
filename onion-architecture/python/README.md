# Python Onion Architecture Example

This is a Python implementation of the onion architecture pattern, demonstrating
clean architecture principles with dependency injection and proper separation of
concerns.

## Architecture Overview

The project follows the onion architecture pattern with these layers:

- **Domain Layer**: Core business entities and services
- **Application Layer**: Use cases and repository interfaces (ports)
- **Infrastructure Layer**: Repository implementations and dependency injection
- **API Layer**: REST API controllers using FastAPI

## Features

- **Type Safety**: Full type hints with mypy checking
- **Dependency Injection**: Using dependency-injector library
- **Protocol Classes**: Python's structural typing for interfaces
- **Comprehensive Testing**: 46+ unit tests with mocking
- **FastAPI**: Modern async web framework
- **Development Tools**: Pre-configured linting, formatting, and type checking

## Quick Start

### Installation

Note: ensure UV is already installed.

```sh
make install
```

### Running the Application

```sh
make run
```

The API will be available at `http://localhost:8000` with automatic OpenAPI
documentation at `http://localhost:8000/docs`.

### Running Tests

```sh
# Run all tests with coverage
make test
```

### Development

```bash
# Format code
make format

# Run linting
make lint

# Type checking
make type-check

# Run all quality checks
make check
```

## API Endpoints

### Enrol Student in Course

```bash
POST /enrolments/?student_id=1&course_id=1
```

**Example:**

```bash
curl -X POST "http://localhost:8000/enrolments/?student_id=1&course_id=1"
```

## Key Python Patterns Demonstrated

### 1. Protocol Classes for Interfaces

```python
from typing import Protocol, Optional

class StudentRepository(Protocol):
    def find_by_id(self, student_id: int) -> Optional[Student]: ...
    def save(self, student: Student) -> None: ...
```

### 2. Dependency Injection

```python
from dependency_injector import containers, providers

class Container(containers.DeclarativeContainer):
    student_repository = providers.Factory(StudentRepositoryImpl)
    enrolment_service = providers.Factory(
        EnrolmentApplicationService,
        student_repo=student_repository,
        # ...
    )
```

### 3. FastAPI Integration

```python
@router.post("/")
@inject
async def enrol_student(
    student_id: int,
    course_id: int,
    service: EnrolmentApplicationService = Depends(
        Provide[Container.enrolment_application_service]
    ),
) -> dict[str, str]:
    # ...
```

## Development Tools

The project includes pre-configured development tools:

- **mypy**: Static type checking
- **black**: Code formatting
- **isort**: Import sorting
- **ruff**: Fast Python linter
- **pytest**: Testing framework with coverage

## Requirements

- Python 3.11+
- FastAPI
- dependency-injector
- pytest (for testing)
- mypy (for type checking)
