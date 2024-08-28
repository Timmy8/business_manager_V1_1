package com.github.Timmy8.entity;

public record Client (
        int id,
        String name,
        String surname,
        String phoneNumber,
        String description,
        boolean blocked
)
{}
