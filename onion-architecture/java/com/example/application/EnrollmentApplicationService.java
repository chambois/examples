package com.example.application;

import com.example.domain.Student;
import com.example.domain.Course;
import com.example.domain.service.EnrollmentService;
import com.example.application.port.StudentRepository;
import com.example.application.port.CourseRepository;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentApplicationService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentService enrollmentService;

    public EnrollmentApplicationService(StudentRepository studentRepo, CourseRepository courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentService = new EnrollmentService();
    }

    public void enroll(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepo.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        enrollmentService.enrollStudent(student, course);
        studentRepo.save(student); // save updated enrollment
    }
}
