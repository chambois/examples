package com.example.domain.service;

import com.example.domain.Course;
import com.example.domain.Student;

public class EnrollmentService {
    public void enrollStudent(Student student, Course course) {
        student.enroll(course);
    }
}
