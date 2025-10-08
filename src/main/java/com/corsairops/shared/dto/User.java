package com.corsairops.shared.dto;

public record User(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        Long createdTimestamp
) {
}