package com.github.Timmy8.controller;

import com.github.Timmy8.controller.exception.PhoneNumberAlreadyExistsException;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("manager-api/clients/{clientId:\\d+}")
public class ClientRestController {
    private final Logger logger = LogManager.getLogger(ClientsRestController.class.getName());
    private final ClientService service;

    @GetMapping
    public Client getClient(@PathVariable("clientId") Integer clientId){
        return service.findClient(clientId)
                .orElseThrow(() -> new NoSuchElementException("api.clients.create.errors.client_not_found"));
    }

    @PatchMapping
    public  ResponseEntity<?> updateClient(@PathVariable("clientId") Integer clientId,
                                           @RequestBody @Valid UpdateClientPayload payload,
                                           BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else if (service.findClientByPhoneNumber(payload.phoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("api.clients.create.errors.phone_number_exist_error");
        } else {
            service.updateClient(clientId, payload.name(), payload.surname(), payload.phoneNumber(), payload.description(), payload.blocked());

            logger.info("\n--- Update Client ---\n#" + payload + "\n---\n");

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") Integer clientId){
        service.deleteClient(clientId);

        logger.info("\n--- Delete Client ---\n#" + clientId + "\n---\n");

        return ResponseEntity.noContent().build();
    }
}
