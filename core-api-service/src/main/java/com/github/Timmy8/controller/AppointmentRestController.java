package com.github.Timmy8.controller;

import com.github.Timmy8.controller.payload.UpdateAppointmentPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.service.AppointmentService;
import com.github.Timmy8.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@RestController
@RequestMapping("manager-api/appointments/{appointmentId:\\d+}")
public class AppointmentRestController {
    private final Logger logger = LogManager.getLogger(AppointmentsRestController.class.getName());
    private final AppointmentService appointmentService;
    private final ProposalService proposalService;


    @GetMapping
    public Appointment getAppointment(@PathVariable("appointmentId") Integer appointmentId){
        return appointmentService.findAppointment(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("api.appointment.create.errors.appointment_not_found"));
    }

    @PatchMapping
    public ResponseEntity<?> updateAppointment(@PathVariable("appointmentId") Integer appointmentId,
                                               @RequestBody @Valid UpdateAppointmentPayload payload,
                                               BindingResult bindingResult) throws BindException{
        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else {
            appointmentService.updateAppointment(
                    appointmentId,
                    LocalDateTime.parse(payload.visitDate(), DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")),
                    payload.clientId(),
                    List.of(proposalService.findProposal(payload.proposalId())
                            .orElseThrow(() -> new NoSuchElementException("api.appointment.create.errors.proposal_not_found"))
                    )
            );

            logger.info("\n--- Update Appointment ---\n" + payload + "\n---\n");

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") Integer appointmentId){
        appointmentService.deleteAppointment(appointmentId);

        logger.info("\n--- Delete Appointment ---\n#" + appointmentId + "\n---\n");

        return ResponseEntity.noContent().build();
    }
}
