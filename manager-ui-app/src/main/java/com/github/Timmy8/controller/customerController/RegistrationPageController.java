package com.github.Timmy8.controller.customerController;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationPageController {
    private final ClientsRestClient restClient;

    @GetMapping
    public String getRegistrationPage(){
        return "customer/registration.html";
    }

    @PostMapping
    public String registerClient(NewClientPayload payload, Model model){
        try{
            Client client = restClient.createClient(payload.name(), payload.surname(), payload.phoneNumber(), payload.description());
            model.addAttribute("client", client);

            return "customer/successfully_registered.html";
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "customer/registration.html";
        }
    }
}
