package com.github.Timmy8.controller;

import com.github.Timmy8.controller.payload.NewAppointmentPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.service.AppointmentService;
import com.github.Timmy8.service.NotificationProducer;
import com.github.Timmy8.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("manager-api/appointments")
public class AppointmentsRestController {
    private final Logger logger = LogManager.getLogger(AppointmentsRestController.class.getName());
    private final AppointmentService appointmentService;
    private final ProposalService proposalService;
    private final NotificationProducer producer;

    @GetMapping
    public List<Appointment> findAllProposals(){
        return appointmentService.findAllAppointments();
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody @Valid NewAppointmentPayload payload,
                                            BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else {
            Appointment appointment = appointmentService.createAppointment(
                    LocalDateTime.parse(payload.visitDate(), DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")),
                    payload.clientId(),
                    List.of(proposalService.findProposal(payload.proposalId())
                            .orElseThrow(() -> new NoSuchElementException("api.appointment.create.errors.proposal_not_found"))
                    )
            );

            logger.info("\n--- New Appointment added ---\n#" + appointment + "\n---\n");
            producer.sendNotification("New Appointment added: " + appointment);
            return ResponseEntity
                    .created(URI.create("manager-api/proposals/" + appointment.getId()))
                    .body(appointment);
        }
    }


}
