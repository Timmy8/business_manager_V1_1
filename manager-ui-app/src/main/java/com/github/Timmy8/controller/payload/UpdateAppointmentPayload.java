package com.github.Timmy8.controller.payload;

import com.github.Timmy8.entity.Proposal;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateAppointmentPayload(String visitDate, int clientId, int proposalId)
{ }
