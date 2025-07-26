package com.example.domain

class Student(
    val id: Long,
    private val name: String
) {
    private val enrolledCourses: MutableSet<Course> = mutableSetOf()

    fun enrol(course: Course) {
        if (enrolledCourses.contains(course)) {
            throw IllegalStateException("Already enrolled in course")
        }
        enrolledCourses.add(course)
    }

    fun getEnrolledCourses(): Set<Course> = enrolledCourses.toSet()
}