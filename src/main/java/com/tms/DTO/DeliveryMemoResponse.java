package com.tms.DTO;

import java.time.Instant;
import java.time.LocalDate;

public record DeliveryMemoResponse(
        Long memoId,
        LocalDate memoDate,
        String driverName,
        String vehicleId,
        Instant createdAt,
        Instant updatedAt
) {
}