.PHONY: install test lint format type-check run clean help

# Install dependencies
install:
	@if [ ! -d ".venv" ]; then \
		echo "Creating virtual environment and installing dependencies..."; \
		uv sync; \
	else \
		uv sync; \
	fi

# Run tests with coverage
test:
	. .venv/bin/activate
	pytest --cov=src --cov-report=term-missing --cov-report=html

# Run linting
lint:
	ruff check src tests

# Format code
format:
	black src tests
	isort src tests

# Type checking
type-check:
	mypy src

# Run all quality checks
check: format lint type-check test

# Run the application
run:
	python -m src.example.api.main

# Clean up generated files
clean:
	rm -rf htmlcov/
	rm -rf .coverage
	rm -rf .pytest_cache/
	rm -rf .mypy_cache/
	rm -rf .ruff_cache/
	find . -type d -name __pycache__ -delete
	find . -type f -name "*.pyc" -delete

# Show help
help:
	@echo "Available commands:"
	@echo "  install     Install dependencies"
	@echo "  test        Run tests with coverage"
	@echo "  lint        Run linting"
	@echo "  format      Format code with black and isort"
	@echo "  type-check  Run type checking with mypy"
	@echo "  check       Run all quality checks"
	@echo "  run         Run the application"
	@echo "  clean       Clean up generated files"
