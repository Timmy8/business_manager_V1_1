package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/clients")
public class ClientsController {
    private final ClientsRestClient restClient;

    @GetMapping("list")
    public String getClientsListPage(Model model){
        model.addAttribute("clients", restClient.findAllClients());

        return "manager/clients/list";
    }

    @GetMapping("create")
    public String getCreateClientPage(){
        return "manager/clients/new_client";
    }

    @PostMapping("create")
    public String createClient(NewClientPayload payload, Model model){
        try{
            Client client = restClient.createClient(payload.name(), payload.surname(), payload.phoneNumber(), payload.description());

            return "redirect:/manager/clients/list";
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "manager/clients/new_client";
        }
    }
}
