package com.github.Timmy8.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "manager", name = "client")
public class Client {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "surname")
    @NotNull
    @Size(min = 2, max = 50)
    private String surname;

    @Column(name = "phoneNumber")
    @NotNull
    @Pattern(regexp = "^\\+375\\d{9}$")
    private String phoneNumber;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    @Column(name = "blocked")
    private boolean blocked;
}
