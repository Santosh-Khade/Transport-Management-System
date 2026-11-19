package com.tms.DTO;

import java.time.Instant;

public record UserResponse(
        Long id,
        String username,
        Instant createdAt
) {
}
