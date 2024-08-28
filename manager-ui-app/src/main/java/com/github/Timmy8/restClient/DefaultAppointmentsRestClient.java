package com.github.Timmy8.restClient;

import com.github.Timmy8.controller.payload.NewAppointmentPayload;
import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.controller.payload.UpdateAppointmentPayload;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.AppointmentsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultAppointmentsRestClient implements AppointmentsRestClient {
    private final RestClient restClient;

    @Override
    public List<Appointment> findAllAppointments() {
        return restClient
                .get()
                .uri("manager-api/appointments")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Optional<Appointment> findAppointment(int appointmentId) {
        try{
            return Optional.ofNullable(restClient
                    .get()
                    .uri("manager-api/appointments/{appointmentId}", appointmentId)
                    .retrieve()
                    .body(Appointment.class));
        } catch (HttpClientErrorException.NotFound exception){
            return Optional.empty();
        }
    }

    @Override
    public Appointment createAppointment(String visitDate, int clientId, int proposalId) {
        try{
            return restClient
                    .post()
                    .uri("manager-api/appointments")
                    .body(new NewAppointmentPayload(visitDate, clientId, proposalId))
                    .retrieve()
                    .body(Appointment.class);
        } catch (HttpClientErrorException.BadRequest exception){
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});

            throw new BadRequestException(errors);
        } catch (HttpClientErrorException.NotFound exception) {
            String errors = exception.getResponseBodyAs(String.class);

            throw new BadRequestException(List.of(errors));
        }
    }

    @Override
    public void updateAppointment(int appointmentId, String visitDate, int clientId, int proposalId) {
        try{
            restClient
                    .patch()
                    .uri("manager-api/appointments/{appointmentId}", appointmentId)
                    .body(new UpdateAppointmentPayload(visitDate, clientId, proposalId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception){
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});

            throw new BadRequestException(errors);
        } catch (HttpClientErrorException.NotFound exception) {
            String errors = exception.getResponseBodyAs(String.class);

            throw new BadRequestException(List.of(errors));
        }
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        try{
            restClient
                    .delete()
                    .uri("manager-api/appointments/{appointmentId}", appointmentId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            throw new BadRequestException(List.of(exception.getMessage()));
        }
    }
}
