package com.github.Timmy8.controller;

import com.github.Timmy8.controller.exception.PhoneNumberAlreadyExistsException;
import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.service.ClientService;
import com.github.Timmy8.service.NotificationProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("manager-api/clients")
public class ClientsRestController {
    private final Logger logger = LogManager.getLogger(ClientsRestController.class.getName());
    private final ClientService service;
    private final NotificationProducer producer;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllClients());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody NewClientPayload payload, BindingResult bindingResult)
            throws BindException {

        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else if (service.findClientByPhoneNumber(payload.phoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("api.clients.create.errors.phone_number_exist_error");
        } else {
            Client client = service.createClient(payload.name(), payload.surname(), payload.phoneNumber(), payload.description());

            logger.info("\n--- New client added ---\n{}\n---\n", client);
            producer.sendNotification("New client added:" + client);
            return ResponseEntity
                    .created(URI.create("/manager-api/clients/" + client.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(client);
        }
    }

    @GetMapping("/{phoneNumber:^\\+375\\d{9}$}")
    public ResponseEntity<Client> findClient(@PathVariable("phoneNumber") String phoneNumber){
        Client client=  service.findClientByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchElementException("api.clients.create.errors.client_not_found"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(client);
    }
}
