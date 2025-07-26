package com.example.infrastructure.config

import com.example.application.EnrolmentApplicationService
import com.example.application.ports.CourseRepository
import com.example.application.ports.StudentRepository
import com.example.domain.services.EnrolmentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {

    @Bean
    fun enrolmentService(): EnrolmentService {
        return EnrolmentService()
    }

    @Bean
    fun enrolmentApplicationService(
        studentRepo: StudentRepository,
        courseRepo: CourseRepository,
        enrolmentService: EnrolmentService
    ): EnrolmentApplicationService {
        return EnrolmentApplicationService(studentRepo, courseRepo, enrolmentService)
    }
}