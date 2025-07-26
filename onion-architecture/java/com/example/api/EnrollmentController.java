package com.example.api;

import com.example.application.EnrollmentApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentApplicationService service;

    public EnrollmentController(EnrollmentApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public void enroll(@RequestParam Long studentId, @RequestParam Long courseId) {
        service.enroll(studentId, courseId);
    }
}
