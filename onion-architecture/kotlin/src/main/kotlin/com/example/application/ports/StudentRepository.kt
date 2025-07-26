package com.example.application.ports

import com.example.domain.Student

interface StudentRepository {
    fun findById(id: Long): Student?
    fun save(student: Student)
}