package com.github.Timmy8.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "core_api_service", name = "appointment")
public class Appointment {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "visit_date")
    @NotNull
    @JsonFormat(pattern = "dd-MM-yy HH:mm")
    private LocalDateTime visitDate;

    @Column(name = "client_id")
    @NotNull
    private int clientId;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            schema = "manager",
            name = "appointment_proposals",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "proposal_id"))
    private List<Proposal> proposals = new ArrayList<>();
}
