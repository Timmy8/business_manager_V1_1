package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/clients/{clientId:\\d+}")
public class ClientController {
    private final ClientsRestClient restClient;

    @ModelAttribute
    public Client client(@PathVariable("clientId") int clientId){
       return restClient.findClient(clientId)
               .orElseThrow(() -> new NoSuchElementException("api.clients.create.errors.client_not_found"));
    }

    @GetMapping
    public String getClientPage(){
        return "manager/clients/client";
    }

    @GetMapping("edit")
    public String getEditClientPage(){
        return "manager/clients/edit";
    }

    @PostMapping("edit")
    public String updateClient(@ModelAttribute(name = "client", binding = false) Client client,
                               UpdateClientPayload payload,
                               Model model){
        try{
            restClient.updateClient(client.id(), payload.name(), payload.surname(),
                    payload.phoneNumber(), payload.description(), payload.blocked());

            return "redirect:/manager/clients/list";
        } catch (BadRequestException exception){
            model.addAttribute("errors", exception.getErrors());
            model.addAttribute("payload", payload);

            return "manager/clients/edit";
        }
    }

    @PostMapping("delete")
    public String deleteClient(@ModelAttribute("client") Client client){
        restClient.deleteClient(client.id());

        return "redirect:/manager/clients/list";
    }

    @GetMapping("block")
    public String changeClientBlockStatus(@ModelAttribute(name = "client", binding = false) Client client,
                               Model model){
        try{
            restClient.updateClient(client.id(), client.name(), client.surname(),
                    client.phoneNumber(), client.description(), !client.blocked());

            return "redirect:/manager/clients/list";
        } catch (BadRequestException exception){
            model.addAttribute("errors", exception.getErrors());

            return "redirect:/manager/clients/list";
        }
    }
}
