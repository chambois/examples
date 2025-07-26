package com.example.infrastructure.adapter

import com.example.application.ports.CourseRepository
import com.example.domain.Course
import org.springframework.stereotype.Repository

@Repository
class CourseRepositoryImpl : CourseRepository {

    private val store: MutableMap<Long, Course> = mutableMapOf()

    init {
        // Add some sample courses
        store[1L] = Course(1L, "Java Programming")
        store[2L] = Course(2L, "Spring Boot")
        store[3L] = Course(3L, "Microservices")
    }

    override fun findById(id: Long): Course? {
        return store[id]
    }
}