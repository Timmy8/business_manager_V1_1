package com.github.Timmy8.controller.payload;

public record UpdateClientPayload(String name, String surname, String phoneNumber, String description, boolean blocked)
{ }
