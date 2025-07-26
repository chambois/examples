package com.example.application.ports

import com.example.domain.Course

interface CourseRepository {
    fun findById(id: Long): Course?
}