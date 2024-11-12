package com.github.Timmy8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.controller.payload.UpdateProposalPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.service.ProposalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProposalRestController.class)
@WithMockUser(roles = "MANAGER")
class ProposalRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProposalService service;

    @Test
    void testGetProposal_withValidUserAndValidProposal() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        String expectedJson = new ObjectMapper().writeValueAsString(proposal);

        // Act
        when(service.findProposal(proposal.getId())).thenReturn(Optional.of(proposal));

        // Assert
        mockMvc.perform(get("/manager-api/proposals/{proposalId}", proposal.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findProposal(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testGetProposal_withValidUserAndValidProposalButProposalNotFound() throws Exception {
        // Arrange

        // Act
        when(service.findProposal(any())).thenReturn(Optional.empty());

        // Assert
        mockMvc.perform(get("/manager-api/proposals/{proposalId}", "1"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findProposal(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testUpdateProposal_withValidUserAndValidPayload() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        UpdateProposalPayload payload = new UpdateProposalPayload(proposal.getName(), proposal.getDescription(), proposal.getPrice());
        String contentJson = new ObjectMapper().writeValueAsString(payload);

        // Act

        // Assert
        mockMvc.perform(patch("/manager-api/proposals/{proposalId}", proposal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentJson)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).updateProposal(any(), any(), any(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testUpdateClient_withValidUserAndInvalidPayload() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        UpdateProposalPayload payload = new UpdateProposalPayload("", "", null);
        String contentJson = new ObjectMapper().writeValueAsString(payload);
        // Act

        // Assert
        mockMvc.perform(patch("/manager-api/proposals/{proposalId}", proposal.getId()+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentJson)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verifyNoInteractions(service);
    }

    @Test
    void testDeleteProposal_withValidUserAndValidPayload() throws Exception {
        // Arrange
        Proposal proposal = EntityFactory.getProposal();
        // Act
        // Assert
        mockMvc.perform(delete("/manager-api/proposals/{proposalId}", proposal.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).deleteProposal(proposal.getId());
        verifyNoMoreInteractions(service);
    }
}