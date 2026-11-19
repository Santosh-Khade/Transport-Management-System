package com.tms.DTO;


import java.time.Instant;

public record PartyResponse(
        Long partyId,
        String name,
        String address,
        String gstNumber,
        Instant createdAt,
        Instant updatedAt
) {
}
