package com.example.infrastructure.config;

import com.example.application.EnrolmentApplicationService;
import com.example.application.ports.CourseRepository;
import com.example.application.ports.StudentRepository;
import com.example.domain.services.EnrolmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    public EnrolmentService enrolmentService() {
        return new EnrolmentService();
    }
    
    @Bean
    public EnrolmentApplicationService enrolmentApplicationService(
        StudentRepository studentRepo,
        CourseRepository courseRepo,
        EnrolmentService enrolmentService) {
        return new EnrolmentApplicationService(studentRepo, courseRepo, enrolmentService);
    }
}