package com.tms.DTO;


import java.time.Instant;

public record ConsigneeResponse(
        Long id,
        String name,
        String address,
        String gstNumber,
        Instant createdAt,
        Instant updatedAt
) {
}
