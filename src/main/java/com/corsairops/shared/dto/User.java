package com.corsairops.shared.dto;

import java.util.List;

public record User(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        Long createdTimestamp,
        List<String> roles
) {
}