package com.example.infrastructure.jpa

import jakarta.persistence.*

@Entity
class JpaCourseEntity {

    @Id
    @GeneratedValue
    var id: Long? = null
    
    var title: String? = null

    // getters/setters handled by Kotlin properties
}