package com.github.Timmy8.restClient.interfaces;

import com.github.Timmy8.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientsRestClient {
    List<Client> findAllClients();
    Optional<Client> findClient(int clientId);
    Optional<Client> findClientByPhoneNumber(String phoneNumber);
    Client createClient(String name, String surname, String phoneNumber, String description);
    void updateClient(int clientId, String name, String surname, String phoneNumber, String description, boolean blocked);
    void deleteClient(int clientId);
}
