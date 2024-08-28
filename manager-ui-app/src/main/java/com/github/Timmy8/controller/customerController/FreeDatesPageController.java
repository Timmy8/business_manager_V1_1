package com.github.Timmy8.controller.customerController;

import com.github.Timmy8.controller.payload.MakeAppointmentPayload;
import com.github.Timmy8.controller.payload.NewAppointmentPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.AppointmentsRestClient;
import com.github.Timmy8.restClient.interfaces.ClientsRestClient;
import com.github.Timmy8.restClient.interfaces.ProposalsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class FreeDatesPageController {
    private final AppointmentsRestClient appointmentsRestClient;
    private final ProposalsRestClient proposalsRestClient;
    private final ClientsRestClient clientsRestClient;

    @GetMapping
    public String getFreeDates(Model model){
        model.addAttribute("proposals", proposalsRestClient.findAllProposals());

        return "customer/free_dates.html";
    }

    @PostMapping
    public String makeAppointment(MakeAppointmentPayload payload, Model model){
        try{
            Client client = clientsRestClient.findClientByPhoneNumber(payload.phoneNumber())
                    .orElseThrow(() -> new BadRequestException(List.of("Client with this phone number not found!\nPlease register first")));

            System.err.println(payload.visitDate() + " " + payload.visitTime());
            Appointment appointment = appointmentsRestClient.createAppointment(
                    payload.visitDate() + " " + payload.visitTime(),
                    client.id(), payload.proposalId());

            return "customer/successfully_created.html";
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "customer/free_dates.html";
        }
    }
}
