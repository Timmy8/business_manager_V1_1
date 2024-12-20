package com.github.Timmy8.controller.factory;

import com.github.Timmy8.entity.Appointment;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EntityFactory {
    public static List<Client> getClientsList(){

        Client client1 = new Client(1, "John", "Doe", "+375123456789", "Some description", false);
        Client client2 = new Client(2, "Jane", "Doe", "+375000000000", "Another description", true);
        return List.of(client1, client2);
    }
    public static Client getClient(){
        return getClientsList().getFirst();
    }

    public static List<Proposal> getProposalsList(){

        Proposal proposal1 = new Proposal(1, "John", "Some description", BigDecimal.valueOf(121.121), List.of());
        Proposal proposal2 = new Proposal(2, "Jane", "Another description", BigDecimal.valueOf(100), List.of());
        return List.of(proposal1, proposal2);
    }
    public static Proposal getProposal(){
        return getProposalsList().getFirst();
    }

    public static List<Appointment> getAppointmentsList(){

        Appointment appointment1 = new Appointment(1, LocalDateTime.of(2024, 11, 10, 10, 30), 1, List.of(getProposal()));
        Appointment appointment2 = new Appointment(2, LocalDateTime.of(2024, 11, 9, 12, 35), 2, List.of(getProposal()));
        return List.of(appointment1, appointment2);
    }
    public static Appointment getAppointment(){
        return getAppointmentsList().getFirst();
    }
}
