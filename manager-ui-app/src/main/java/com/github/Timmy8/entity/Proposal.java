package com.github.Timmy8.entity;

import java.math.BigDecimal;

public record Proposal(
        int id,
        String name,
        String description,
        BigDecimal price
) {
}
