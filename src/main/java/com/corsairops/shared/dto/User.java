package com.corsairops.shared.dto;

public record User(
        String id,
        String email,
        String givenName,
        String familyName,
        String gender
) {
}