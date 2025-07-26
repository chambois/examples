package com.example.infrastructure.jpa

import jakarta.persistence.*

@Entity
class JpaStudentEntity {

    @Id
    @GeneratedValue
    var id: Long? = null
    
    var name: String? = null

    @ManyToMany
    var enrolledCourses: MutableSet<JpaCourseEntity> = mutableSetOf()

    // getters/setters handled by Kotlin properties
}