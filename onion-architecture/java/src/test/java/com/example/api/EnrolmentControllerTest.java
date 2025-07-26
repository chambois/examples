package com.example.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.application.EnrolmentApplicationService;

@ExtendWith(MockitoExtension.class)
class EnrolmentControllerTest {

    @Mock
    private EnrolmentApplicationService enrolmentApplicationService;

    private EnrolmentController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new EnrolmentController(enrolmentApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldEnrolStudentInCourseSuccessfully() throws Exception {
        Long studentId = 1L;
        Long courseId = 1L;

        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()))
                .andExpect(status().isOk());

        verify(enrolmentApplicationService).enrol(studentId, courseId);
    }

    @Test
    void shouldAcceptValidStudentAndCourseIds() throws Exception {
        Long studentId = 123L;
        Long courseId = 456L;

        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()))
                .andExpect(status().isOk());

        verify(enrolmentApplicationService).enrol(studentId, courseId);
    }

    @Test
    void shouldReturnBadRequestWhenStudentIdMissing() throws Exception {
        Long courseId = 1L;

        mockMvc.perform(post("/enrolments")
                .param("courseId", courseId.toString()))
                .andExpect(status().isBadRequest());

        verify(enrolmentApplicationService, never()).enrol(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenCourseIdMissing() throws Exception {
        Long studentId = 1L;

        mockMvc.perform(post("/enrolments")
                .param("studentId", studentId.toString()))
                .andExpect(status().isBadRequest());

        verify(enrolmentApplicationService, never()).enrol(any(), any());
    }

    @Test
    void shouldReturnBadRequestWhenBothParametersMissing() throws Exception {
        mockMvc.perform(post("/enrolments"))
                .andExpect(status().isBadRequest());

        verify(enrolmentApplicationService, never()).enrol(any(), any());
    }

    @Test
    void shouldReturnMethodNotAllowedForGetRequest() throws Exception {
        mockMvc.perform(get("/enrolments")
                .param("studentId", "1")
                .param("courseId", "1"))
                .andExpect(status().isMethodNotAllowed());

        verify(enrolmentApplicationService, never()).enrol(any(), any());
    }

    @Test
    void shouldCallServiceWithExactParameters() throws Exception {
        Long expectedStudentId = 42L;
        Long expectedCourseId = 99L;

        mockMvc.perform(post("/enrolments")
                .param("studentId", expectedStudentId.toString())
                .param("courseId", expectedCourseId.toString()))
                .andExpect(status().isOk());

        verify(enrolmentApplicationService).enrol(expectedStudentId, expectedCourseId);
        verifyNoMoreInteractions(enrolmentApplicationService);
    }
}
