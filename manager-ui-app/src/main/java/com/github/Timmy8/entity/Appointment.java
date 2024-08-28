package com.github.Timmy8.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record Appointment(
        int id,
        @JsonFormat(pattern = "dd-MM-yy HH:mm")
        LocalDateTime visitDate,
        int clientId,
        List<Proposal> proposals
){}
