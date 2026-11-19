package com.tms.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConsignerRequest(
        @NotBlank(message = "Consigner name is required")
        @Size(max = 150, message = "Consigner name must not exceed 150 characters")
        String name,

        @NotBlank(message = "Address is required")
        @Size(max = 500, message = "Address must not exceed 500 characters")
        String address,

        @Size(max = 20, message = "GST number must not exceed 20 characters")
        @Pattern(
                regexp = "^$|^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
                message = "Invalid GSTIN format (expected 15-character GSTIN)"
        )
        String gstNumber
) {
}
