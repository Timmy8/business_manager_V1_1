package com.github.Timmy8.controller.payload;


public record MakeAppointmentPayload(String visitDate, String visitTime, Integer proposalId, String phoneNumber) {
}
