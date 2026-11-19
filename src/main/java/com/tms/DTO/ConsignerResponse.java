package com.tms.DTO;


import java.time.Instant;

public record ConsignerResponse(
        Long id,
        String name,
        String address,
        String gstNumber,
        Instant createdAt,
        Instant updatedAt
) {
}