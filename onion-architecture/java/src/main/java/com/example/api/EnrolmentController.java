package com.example.api;

import com.example.application.EnrolmentApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrolments")
public class EnrolmentController {

    private final EnrolmentApplicationService service;

    public EnrolmentController(EnrolmentApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public void enrol(@RequestParam Long studentId, @RequestParam Long courseId) {
        service.enrol(studentId, courseId);
    }
}
