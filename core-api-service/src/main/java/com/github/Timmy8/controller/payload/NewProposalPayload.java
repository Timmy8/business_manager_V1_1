package com.github.Timmy8.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record NewProposalPayload(
        @NotNull(message = "{api.proposal.create.errors.name_is_null}")
        @Size(min = 2, max = 50, message = "{api.proposal.create.errors.name_size_is_invalid}")
        String name,

        @Size(max = 1000, message = "{api.proposal.create.errors.description_size_is_invalid}")
        String description,

        @NotNull(message = "{api.proposal.create.errors.price_is_null}")
        @PositiveOrZero(message = "{api.proposal.create.errors.price_value_is_invalid}")
        BigDecimal price
)
{}
