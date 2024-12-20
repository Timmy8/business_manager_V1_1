package com.github.Timmy8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Timmy8.controller.factory.EntityFactory;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientRestController.class)
@WithMockUser(roles = "MANAGER")
class ClientRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Test
    void testGetClient_withValidUserAndValidClient() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        String expectedJson = new ObjectMapper().writeValueAsString(client);

        // Act
        when(service.findClient(client.getId())).thenReturn(Optional.of(client));

        // Assert
        mockMvc.perform(get("/manager-api/clients/{clientId}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClient(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testGetClient_withValidUserAndValidClientButClientNotFound() throws Exception {
        // Arrange

        // Act
        when(service.findClient(any())).thenReturn(Optional.empty());

        // Assert
        mockMvc.perform(get("/manager-api/clients/{clientId}", "1"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClient(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testUpdateClient_withValidUserAndValidPayloadWithSamePhoneNumber() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        UpdateClientPayload payload = new UpdateClientPayload(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getDescription(), false);
        String contentJson = new ObjectMapper().writeValueAsString(payload);
        // Act
        when(service.findClientByPhoneNumber(payload.phoneNumber())).thenReturn(Optional.of(client));

        // Assert
        mockMvc.perform(patch("/manager-api/clients/{clientId}", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentJson)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClientByPhoneNumber(any());
        verify(service, times(1)).updateClient(any(), any(), any(), any(), any(), anyBoolean());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testUpdateClient_withValidUserAndValidPayloadButNotUniquePhoneNumber() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        UpdateClientPayload payload = new UpdateClientPayload(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getDescription(), false);
        String contentJson = new ObjectMapper().writeValueAsString(payload);
        // Act
        when(service.findClientByPhoneNumber(payload.phoneNumber())).thenReturn(Optional.of(client));

        // Assert
        mockMvc.perform(patch("/manager-api/clients/{clientId}", client.getId()+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentJson)
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClientByPhoneNumber(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void testUpdateClient_withValidUserAndInvalidPayload() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        UpdateClientPayload payload = new UpdateClientPayload("", "", "", "", false);
        String contentJson = new ObjectMapper().writeValueAsString(payload);
        // Act

        // Assert
        mockMvc.perform(patch("/manager-api/clients/{clientId}", client.getId()+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentJson)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verifyNoInteractions(service);
    }

    @Test
    void testDeleteClient_withValidUserAndValidPayloadWithSamePhoneNumber() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        // Act
        // Assert
        mockMvc.perform(delete("/manager-api/clients/{clientId}", client.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).deleteClient(client.getId());
        verifyNoMoreInteractions(service);
    }
}