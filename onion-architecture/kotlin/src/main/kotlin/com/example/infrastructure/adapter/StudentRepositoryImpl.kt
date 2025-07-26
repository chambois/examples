package com.example.infrastructure.adapter

import com.example.application.ports.StudentRepository
import com.example.domain.Student
import org.springframework.stereotype.Repository

@Repository
class StudentRepositoryImpl : StudentRepository {

    private val store: MutableMap<Long, Student> = mutableMapOf()

    override fun findById(id: Long): Student? {
        return store[id]
    }

    override fun save(student: Student) {
        store[student.id] = student
    }
}