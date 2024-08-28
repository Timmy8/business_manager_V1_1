package com.github.Timmy8.controller.payload;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateAppointmentPayload (
    @NotNull(message = "{api.appointment.create.errors.visit_date_is_null}")
    String visitDate,
    @NotNull(message = "{api.appointment.create.errors.client_id_is_null}")
    int clientId,
    @NotNull(message = "{api.appointment.create.errors.proposal_id_is_null}")
    int proposalId
)
{}
