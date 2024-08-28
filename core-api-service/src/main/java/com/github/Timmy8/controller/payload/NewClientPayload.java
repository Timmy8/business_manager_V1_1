package com.github.Timmy8.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewClientPayload(
        @NotNull(message = "{api.clients.create.errors.name_is_null}")
        @Size(min = 2, max = 50, message = "{api.clients.create.errors.name_size_is_invalid}")
        String name,
        @NotNull(message = "{api.clients.create.errors.surname_is_null}")
        @Size(min = 2, max = 50, message = "{api.clients.create.errors.surname_size_is_invalid}")
        String surname,
        @NotNull(message = "{api.clients.create.errors.phone_number_is_null}")
        @Pattern(regexp = "^\\+375\\d{9}$", message = "{api.clients.create.errors.phone_number_pattern_invalid}")
        String phoneNumber,
        @Size(max = 1000, message = "{api.clients.create.errors.description_size_is_invalid}")
        String description
)
{ }
