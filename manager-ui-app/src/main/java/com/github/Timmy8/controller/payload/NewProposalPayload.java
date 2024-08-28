package com.github.Timmy8.controller.payload;

import java.math.BigDecimal;

public record NewProposalPayload(String name, String description, BigDecimal price)
{ }
