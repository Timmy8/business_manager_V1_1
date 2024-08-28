package com.github.Timmy8.service;

import com.github.Timmy8.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAllClients();
    Optional<Client> findClient(Integer clientId);
    Optional<Client> findClientByPhoneNumber(String phoneNumber);
    Client createClient(String name, String surname, String phoneNumber, String description);
    void updateClient(Integer clientId, String name, String surname, String phoneNumber, String description, boolean blocked);
    void deleteClient(Integer clientId);
}
