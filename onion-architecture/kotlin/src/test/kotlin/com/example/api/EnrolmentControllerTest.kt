package com.example.api

import com.example.application.EnrolmentApplicationService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class EnrolmentControllerTest {

    private val enrolmentApplicationService = mockk<EnrolmentApplicationService>()
    
    private lateinit var controller: EnrolmentController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        controller = EnrolmentController(enrolmentApplicationService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun shouldEnrolStudentInCourseSuccessfully() {
        val studentId = 1L
        val courseId = 1L
        
        every { enrolmentApplicationService.enrol(studentId, courseId) } just Runs
        
        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()))
                .andExpect(status().isOk)
        
        verify { enrolmentApplicationService.enrol(studentId, courseId) }
    }

    @Test
    fun shouldAcceptValidStudentAndCourseIds() {
        val studentId = 123L
        val courseId = 456L
        
        every { enrolmentApplicationService.enrol(studentId, courseId) } just Runs
        
        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()))
                .andExpect(status().isOk)
        
        verify { enrolmentApplicationService.enrol(studentId, courseId) }
    }

    @Test
    fun shouldReturnBadRequestWhenStudentIdMissing() {
        val courseId = 1L
        
        mockMvc.perform(post("/enrolments")
                .param("courseId", courseId.toString()))
                .andExpect(status().isBadRequest)
        
        verify(exactly = 0) { enrolmentApplicationService.enrol(any(), any()) }
    }

    @Test
    fun shouldReturnBadRequestWhenCourseIdMissing() {
        val studentId = 1L
        
        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString()))
                .andExpect(status().isBadRequest)
        
        verify(exactly = 0) { enrolmentApplicationService.enrol(any(), any()) }
    }

    @Test
    fun shouldReturnBadRequestWhenBothParametersMissing() {
        mockMvc.perform(post("/enrolments"))
                .andExpect(status().isBadRequest)
        
        verify(exactly = 0) { enrolmentApplicationService.enrol(any(), any()) }
    }

    @Test
    fun shouldReturnMethodNotAllowedForGetRequest() {
        mockMvc.perform(get("/enrolments")
                .param("studentId", "1")
                .param("courseId", "1"))
                .andExpect(status().isMethodNotAllowed)
        
        verify(exactly = 0) { enrolmentApplicationService.enrol(any(), any()) }
    }

    @Test
    fun shouldCallServiceWithExactParameters() {
        val expectedStudentId = 42L
        val expectedCourseId = 99L
        
        every { enrolmentApplicationService.enrol(expectedStudentId, expectedCourseId) } just Runs
        
        mockMvc.perform(post("/enrolments")
                .param("studentId", expectedStudentId.toString())
                .param("courseId", expectedCourseId.toString()))
                .andExpect(status().isOk)
        
        verify { enrolmentApplicationService.enrol(expectedStudentId, expectedCourseId) }
        verify(exactly = 1) { enrolmentApplicationService.enrol(any(), any()) }
    }
}