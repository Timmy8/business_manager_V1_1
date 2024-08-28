package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.NewAppointmentPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.AppointmentsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/appointments")
public class AppointmentsController {
    private final AppointmentsRestClient restClient;

    @GetMapping("list")
    public String getAppointmentsListPage(Model model){
        model.addAttribute("appointments", restClient.findAllAppointments());

        return "manager/appointments/list";
    }

    @GetMapping("create")
    public String getCreateAppointmentsPage(){
        return "manager/appointments/new_appointment";
    }

    @PostMapping("create")
    public String createAppointments(NewAppointmentPayload payload, Model model){
        try{
            Appointment appointment = restClient.createAppointment(payload.visitDate(), payload.clientId(), payload.proposalId());

            return "redirect:/manager/appointments/list";
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());

            return "manager/appointments/new_appointment";
        }
    }
}
