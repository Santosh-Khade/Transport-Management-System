package com.tms.DTO;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DeliveryMemoRequest(
        @NotNull(message = "Memo date is required")
        LocalDate memoDate,

        @NotBlank(message = "Driver name is required")
        @Size(max = 120, message = "Driver name must not exceed 120 characters")
        String driverName,

        @NotBlank(message = "Vehicle ID is required")
        @Size(max = 50, message = "Vehicle ID must not exceed 50 characters")
        String vehicleId
) {
}