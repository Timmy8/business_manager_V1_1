package com.github.Timmy8.service;

import com.github.Timmy8.entity.Client;
import com.github.Timmy8.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultClientService implements ClientService{
    private final ClientRepository repository;

    @Override
    public List<Client> findAllClients() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> findClient(Integer clientId) {
        return repository.findById(clientId);
    }

    @Override
    public Optional<Client> findClientByPhoneNumber(String phoneNumber) {
        return repository.findClientByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public Client createClient(String name, String surname, String phoneNumber, String description) {
        return repository.save(new Client(null, name, surname, phoneNumber, description, false));
    }

    @Override
    @Transactional
    public void updateClient(Integer clientId, String name, String surname, String phoneNumber, String description, boolean blocked) {
        repository.findById(clientId).ifPresentOrElse(client -> {
           client.setName(name);
           client.setSurname(surname);
           client.setPhoneNumber(phoneNumber);
           client.setDescription(description);
           client.setBlocked(blocked);
        }, () -> {
            throw new NoSuchElementException("api.clients.create.errors.client_not_found");
        });
    }

    @Override
    @Transactional
    public void deleteClient(Integer clientId) {
        repository.deleteById(clientId);
    }
}
