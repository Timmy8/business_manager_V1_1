package com.github.Timmy8.restClient;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultClientsRestClient implements ClientsRestClient {
    private final RestClient restClient;

    @Override
    public List<Client> findAllClients() {
        return restClient
                .get()
                .uri("manager-api/clients")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Optional<Client> findClient(int clientId) {
        try{
            return Optional.ofNullable(restClient
                    .get()
                    .uri("manager-api/clients/{clientId}", clientId)
                    .retrieve()
                    .body(Client.class));
        } catch (HttpClientErrorException.NotFound exception){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> findClientByPhoneNumber(String phoneNumber) {
        try{
            return Optional.ofNullable(restClient
                    .get()
                    .uri("manager-api/clients/{phoneNumber}", phoneNumber)
                    .retrieve()
                    .body(Client.class));
        } catch (HttpClientErrorException.NotFound exception){
            return Optional.empty();
        }
    }

    @Override
    public Client createClient(String name, String surname, String phoneNumber, String description) {
        try{
            return restClient
                    .post()
                    .uri("manager-api/clients")
                    .body(new NewClientPayload(name,surname,phoneNumber,description))
                    .retrieve()
                    .body(Client.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});

            throw new BadRequestException(errors);
        } catch (HttpClientErrorException.NotFound exception) {
            String error = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});

            throw new BadRequestException(List.of(error));
        }
    }

    @Override
    public void updateClient(int clientId, String name, String surname, String phoneNumber, String description, boolean blocked) {
        try{
            restClient
                    .patch()
                    .uri("manager-api/clients/{clientId}", clientId)
                    .body(new UpdateClientPayload(name, surname, phoneNumber, description, blocked))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception){
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void deleteClient(int clientId) {
        try{
            restClient
                    .delete()
                    .uri("manager-api/clients/{clientId}", clientId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            throw new NoSuchElementException(exception);
        }
    }
}
