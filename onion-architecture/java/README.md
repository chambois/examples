# Onion architecture - Java example

## Setup

- Install gradle
- Run `gradle build`

## Notes

- Domain is isolated: No dependency on Spring or JPA.
- Application layer coordinates use cases and interacts via interfaces.
- Infrastructure provides implementations (in-memory here, could be JPA).
- Controller/UI only calls into the application layer.

### Layers

- Domain Model: Student, Course
- Domain Services: EnrolmentService
- Application Services: EnrolmentApplicationService
- Infrastructure: JPA Repositories
- UI Layer: Spring REST controllers

### Q&A

- Why does the EnrolmentService live as part of the domain and not the
  application?
  - The EnrolmentService lives in the Domain Layer because enrolment itself is a
    core domain concept, not just a coordination of infrastructure or a specific
    application workflow.
  - The EnrolmentService isn’t responsible for fetching students, saving data,
    or logging. Instead, it:
    - Enforces a core rule: “A student cannot enrol in the same course twice.”
    - Mutates domain state (i.e., adding the course to the student’s enrolment
      list).
    - Knows nothing about persistence, HTTP, or frameworks
  - Domain services often encapsulate domain rules that don’t naturally belong
    inside a single entity (like Student or Course)
  - If you can’t express a rule within an entity cleanly (e.g., it involves
    multiple aggregates), you often extract it into a domain service
  - It operates only on domain objects (Student, Course)
