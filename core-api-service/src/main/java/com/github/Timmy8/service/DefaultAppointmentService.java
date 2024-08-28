package com.github.Timmy8.service;

import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultAppointmentService implements AppointmentService{
    private final AppointmentRepository repository;
    private final ProposalService proposalService;

    @Override
    public List<Appointment> findAllAppointments() {
        return repository.findAll();
    }

    @Override
    public Optional<Appointment> findAppointment(Integer appointmentId) {
        return repository.findById(appointmentId);
    }

    @Override
    @Transactional
    public Appointment createAppointment(LocalDateTime visitDate, int clientId, List<Proposal> proposals) {
        return repository.save(new Appointment(null, visitDate, clientId, proposals));
    }

    @Override
    @Transactional
    public void updateAppointment(Integer appointmentId, LocalDateTime visitDate, int clientId, List<Proposal> proposals) {
        repository.findById(appointmentId).ifPresentOrElse(appointment -> {
            appointment.setVisitDate(visitDate);
            appointment.setClientId(clientId);
            appointment.setProposals(proposals);
        }, () -> {
            throw new NoSuchElementException("{api.appointment.create.errors.appointment_not_found}");
        });
    }

    @Override
    @Transactional
    public void deleteAppointment(Integer appointmentId) {
        repository.deleteById(appointmentId);
    }
}
