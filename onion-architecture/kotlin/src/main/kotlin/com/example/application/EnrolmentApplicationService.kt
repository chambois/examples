package com.example.application

import com.example.application.ports.CourseRepository
import com.example.application.ports.StudentRepository
import com.example.domain.services.EnrolmentService

class EnrolmentApplicationService(
    private val studentRepo: StudentRepository,
    private val courseRepo: CourseRepository,
    private val enrolmentService: EnrolmentService
) {
    fun enrol(studentId: Long, courseId: Long) {
        val student = studentRepo.findById(studentId)
            ?: throw RuntimeException("Student not found")
            
        val course = courseRepo.findById(courseId)
            ?: throw RuntimeException("Course not found")

        enrolmentService.enrolStudent(student, course)
        studentRepo.save(student) // save updated enrolment
    }
}