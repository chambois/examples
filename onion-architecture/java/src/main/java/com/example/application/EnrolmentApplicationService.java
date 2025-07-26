package com.example.application;

import com.example.application.ports.CourseRepository;
import com.example.application.ports.StudentRepository;
import com.example.domain.Course;
import com.example.domain.Student;
import com.example.domain.services.EnrolmentService;

public class EnrolmentApplicationService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrolmentService enrolmentService;

    public EnrolmentApplicationService(
            StudentRepository studentRepo,
            CourseRepository courseRepo,
            EnrolmentService enrolmentService
    ) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrolmentService = enrolmentService;
    }

    public void enrol(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        enrolmentService.enrolStudent(student, course);
        studentRepo.save(student); // save updated enrolment
    }
}
