"""FastAPI application entry point."""

from collections.abc import AsyncGenerator
from contextlib import asynccontextmanager

from fastapi import FastAPI

from ..infrastructure.config.container import Container
from .enrolment_controller import router as enrolment_router


@asynccontextmanager
async def lifespan(app: FastAPI) -> AsyncGenerator[None, None]:
    """Application lifespan manager."""
    # Setup
    container = Container()
    container.wire(modules=["example.api.enrolment_controller"])
    app.container = container  # type: ignore

    yield

    # Cleanup
    container.unwire()


def create_app() -> FastAPI:
    """Create and configure the FastAPI application."""
    app = FastAPI(
        title="Onion Architecture Example",
        description="Python implementation of onion architecture pattern",
        version="1.0.0",
        lifespan=lifespan,
    )

    # Include routers
    app.include_router(enrolment_router)

    return app


# Create the application instance
app = create_app()


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
