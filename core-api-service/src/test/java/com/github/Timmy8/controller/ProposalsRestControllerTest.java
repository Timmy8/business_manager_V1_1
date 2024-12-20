package com.github.Timmy8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Timmy8.controller.factory.EntityFactory;
import com.github.Timmy8.controller.payload.NewProposalPayload;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.service.NotificationProducer;
import com.github.Timmy8.service.ProposalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProposalsRestController.class)
@WithMockUser(roles = "MANAGER")
class ProposalsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProposalService service;

    @MockBean
    private NotificationProducer producer;

    @Test
    void testFindAllProposals() throws Exception{
        // Arrange
        List<Proposal> clients = EntityFactory.getProposalsList();
        String expectedJson = new ObjectMapper().writeValueAsString(clients);

        // Act
        when(service.findAllProposals()).thenReturn(clients);

        // Assert
        mockMvc.perform(get("/manager-api/proposals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findAllProposals();
        verifyNoMoreInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testCreateProposal_withValidUserAndValidPayload() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        String expectedJson = new ObjectMapper().writeValueAsString(proposal);
        NewProposalPayload payload = new NewProposalPayload(proposal.getName(), proposal.getDescription(), proposal.getPrice());
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act
        when(service.createProposal(payload.name(), payload.description(), payload.price()))
                .thenReturn(proposal);

        // Assert
        mockMvc.perform(post("/manager-api/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).createProposal(any(), any(), any());
        verifyNoMoreInteractions(service);
        verify(producer, times(1)).sendNotification(any());
        verifyNoMoreInteractions(producer);
    }

    @Test
    void testCreateProposal_withValidUserAndInvalidPayload() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        NewProposalPayload payload = new NewProposalPayload("", "", null);
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act

        // Assert
        mockMvc.perform(post("/manager-api/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verifyNoInteractions(service);
        verifyNoInteractions(producer);
    }
}