package com.github.Timmy8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Timmy8.controller.factory.EntityFactory;
import com.github.Timmy8.controller.payload.NewAppointmentPayload;
import com.github.Timmy8.controller.payload.NewProposalPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.service.AppointmentService;
import com.github.Timmy8.service.NotificationProducer;
import com.github.Timmy8.service.ProposalService;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentsRestController.class)
@WithMockUser(roles = "MANAGER")
class AppointmentsRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AppointmentService appointmentService;
    @MockBean
    ProposalService proposalService;
    @MockBean
    NotificationProducer producer;

    List<Appointment> appointments;
    Appointment appointment;

    @BeforeEach
    public void setUp(ApplicationContext context){
        appointments = EntityFactory.getAppointmentsList();
        appointment = EntityFactory.getAppointment();
        when(appointmentService.findAllAppointments()).thenReturn(appointments);
    }

    @Test
    void testFindAllAppointments() throws Exception{
        // Arrange

        // Act

        // Assert
        mockMvc.perform(get("/manager-api/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(appointmentService, times(1)).findAllAppointments();
        verifyNoMoreInteractions(appointmentService);
        verifyNoInteractions(proposalService);
        verifyNoInteractions(producer);
    }

    @Test
    void testCreateProposal_withValidUserAndValidPayload() throws Exception {
        // Arrange
        String expectedJson = new ObjectMapper().writeValueAsString(appointment);
        NewAppointmentPayload payload = new NewAppointmentPayload(appointment.getVisitDate().toString(), appointment.getClientId(), appointment.getProposals().getFirst().getId());
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act
        when(appointmentService.createAppointment(appointment.getVisitDate(), payload.clientId(), appointment.getProposals()))
                .thenReturn(appointment);

        // Assert
        mockMvc.perform(post("/manager-api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(appointmentService, times(1)).createAppointment(any(), any(), any());
        verifyNoMoreInteractions(appointmentService);
        verify(producer, times(1)).sendNotification(any());
        verifyNoMoreInteractions(producer);
    }

//    @Test
//    void testCreateProposal_withValidUserAndInvalidPayload() throws Exception {
//        // Arrange
//        Proposal proposal = EntityFactory.getProposal();
//        NewProposalPayload payload = new NewProposalPayload("", "", null);
//        String jsonContent = new ObjectMapper().writeValueAsString(payload);
//
//        // Act
//
//        // Assert
//        mockMvc.perform(post("/manager-api/proposals")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonContent)
//                        .with(csrf()))
//                .andExpect(status().isBadRequest())
//                .andDo(MockMvcResultHandlers.print());
//
//        verifyNoInteractions(service);
//        verifyNoInteractions(producer);
//    }
}