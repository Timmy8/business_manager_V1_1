package com.github.Timmy8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "core_api_service", name = "proposal")
public class Proposal {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @Column(name = "price")
    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @ManyToMany(mappedBy = "proposals")
    @JsonIgnore
    @ToStringExclude
    private List<Appointment> appointments;
}
