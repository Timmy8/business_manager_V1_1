package com.github.Timmy8.service;

import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Proposal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAllAppointments();
    Optional<Appointment> findAppointment(Integer appointmentId);
    Appointment createAppointment(LocalDateTime visitDate, int clientId, List<Proposal> proposals);
    void updateAppointment(Integer appointmentId, LocalDateTime visitDate, int clientId, List<Proposal> proposals);
    void deleteAppointment(Integer appointmentId);
}
