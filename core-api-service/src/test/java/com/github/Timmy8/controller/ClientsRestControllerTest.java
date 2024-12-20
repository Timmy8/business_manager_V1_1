package com.github.Timmy8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Timmy8.controller.factory.EntityFactory;
import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.service.ClientService;
import com.github.Timmy8.service.NotificationProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientsRestController.class)
@WithMockUser(roles = "MANAGER")
class ClientsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @MockBean
    private NotificationProducer producer;

    @Test
    void testGetAllClients_withValidUser() throws Exception{
        // Arrange
        List<Client> clients = EntityFactory.getClientsList();
        String expectedJson = new ObjectMapper().writeValueAsString(clients);

        // Act
        when(service.findAllClients()).thenReturn(clients);

        // Assert
        mockMvc.perform(get("/manager-api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findAllClients();
        verifyNoMoreInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testCreateClient_withValidUserAndValidData() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        String expectedJson = new ObjectMapper().writeValueAsString(client);
        NewClientPayload payload = new NewClientPayload(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getDescription());
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act
        when(service.createClient(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getDescription()))
                .thenReturn(client);
        when(service.findClientByPhoneNumber(client.getPhoneNumber()))
                .thenReturn(Optional.empty());

        // Assert
        mockMvc.perform(post("/manager-api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).createClient(any(), any(), any(), any());
        verify(service, times(1)).findClientByPhoneNumber(any());
        verifyNoMoreInteractions(service);
        verify(producer, times(1)).sendNotification(any());
        verifyNoMoreInteractions(producer);
    }

    @Test
    void testCreateClient_withValidUserAndValidPayloadButPhoneNumberExist() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        NewClientPayload payload = new NewClientPayload(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getDescription());
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act
        when(service.findClientByPhoneNumber(payload.phoneNumber()))
                .thenReturn(Optional.of(client));

        // Assert
        mockMvc.perform(post("/manager-api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClientByPhoneNumber(any());
        verifyNoMoreInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testCreateClient_withValidUserAndInvalidPayload() throws Exception {
        // Arrange
        Client client = EntityFactory.getClient();
        NewClientPayload payload = new NewClientPayload("", "", "", "");
        String jsonContent = new ObjectMapper().writeValueAsString(payload);

        // Act

        // Assert
        mockMvc.perform(post("/manager-api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        verifyNoInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testFindClient_withValidUserAndValidData() throws Exception{
        // Arrange
        Client client = EntityFactory.getClient();
        String expectedJson = new ObjectMapper().writeValueAsString(client);

        // Act
        when(service.findClientByPhoneNumber(client.getPhoneNumber()))
                .thenReturn(Optional.of(client));

        // Assert

        mockMvc.perform(get("/manager-api/clients/" + client.getPhoneNumber())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClientByPhoneNumber(any());
        verifyNoMoreInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testFindClient_withValidUserAndValidDataButUserNotExist() throws Exception{
        // Arrange
        Client client = EntityFactory.getClient();

        // Act
        when(service.findClientByPhoneNumber(client.getPhoneNumber()))
                .thenReturn(Optional.empty());

        // Assert

        mockMvc.perform(get("/manager-api/clients/" + client.getPhoneNumber())
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).findClientByPhoneNumber(any());
        verifyNoMoreInteractions(service);
        verifyNoInteractions(producer);
    }

    @Test
    void testFindClient_withValidUserAndInvalidData() throws Exception{
        // Arrange
        // Act
        // Assert

        mockMvc.perform(get("/manager-api/clients/" + "+3751234567890")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}