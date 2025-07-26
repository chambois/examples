package com.example.domain

data class Course(
    val id: Long,
    val title: String
) {
    // Custom equals/hashCode based on ID only (like Java version)
    override fun equals(other: Any?): Boolean {
        return other is Course && other.id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}