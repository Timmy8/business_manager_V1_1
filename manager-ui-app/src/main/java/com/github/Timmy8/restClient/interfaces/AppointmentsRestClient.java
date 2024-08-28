package com.github.Timmy8.restClient.interfaces;

import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentsRestClient{
    List<Appointment> findAllAppointments();
    Optional<Appointment> findAppointment(int appointmentId);
    Appointment createAppointment(String visitDate, int clientId, int proposalId);
    void updateAppointment(int appointmentId, String visitDate, int clientId, int proposalId);
    void deleteAppointment(int appointmentId);
}
