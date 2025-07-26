package com.example.api

import com.example.application.EnrolmentApplicationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
    private val service: EnrolmentApplicationService
) {

    @PostMapping
    fun enrol(@RequestParam studentId: Long, @RequestParam courseId: Long) {
        service.enrol(studentId, courseId)
    }
}