package com.example.domain.services;

import com.example.domain.Course;
import com.example.domain.Student;

public class EnrolmentService {

    public void enrolStudent(Student student, Course course) {
        student.enrol(course);
    }
}
