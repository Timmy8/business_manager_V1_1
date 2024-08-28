package com.github.Timmy8.controller.managerController;

import com.github.Timmy8.controller.payload.UpdateAppointmentPayload;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.AppointmentsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/appointments/{appointmentId:\\d+}")
public class AppointmentController {
    private final AppointmentsRestClient restClient;

    @ModelAttribute
    public Appointment appointment(@PathVariable("appointmentId") int appointmentId){
        return restClient.findAppointment(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("api.appointment.create.errors.appointment_not_found"));
    }

    @GetMapping
    public String getAppointmentPage(){
        return "manager/appointments/appointment";
    }

    @GetMapping("edit")
    public String getEditAppointmentPage(){
        return "manager/appointments/edit";
    }

    @PostMapping("edit")
    public String updateClient(@ModelAttribute(name = "appointment", binding = false) Appointment appointment,
                               UpdateAppointmentPayload payload,
                               Model model){
        try{
            restClient.updateAppointment(appointment.id(), payload.visitDate(), payload.clientId(), payload.proposalId());

            return "redirect:/manager/appointments/%d".formatted(appointment.id());
        } catch (BadRequestException exception){
            model.addAttribute("errors", exception.getErrors());
            model.addAttribute("payload", payload);

            return "manager/appointments/edit";
        }
    }

    @PostMapping("delete")
    public String deleteClient(@ModelAttribute("appointment") Appointment appointment){
        restClient.deleteAppointment(appointment.id());

        return "redirect:/manager/appointments/list";
    }
}
