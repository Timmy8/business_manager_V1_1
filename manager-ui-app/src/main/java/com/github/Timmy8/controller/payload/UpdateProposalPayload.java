package com.github.Timmy8.controller.payload;

import java.math.BigDecimal;

public record UpdateProposalPayload (String name, String description, BigDecimal price)
{}
