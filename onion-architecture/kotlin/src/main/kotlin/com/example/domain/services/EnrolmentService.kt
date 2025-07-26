package com.example.domain.services

import com.example.domain.Course
import com.example.domain.Student

class EnrolmentService {
    fun enrolStudent(student: Student, course: Course) {
        student.enrol(course)
    }
}