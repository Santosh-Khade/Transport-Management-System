package com.tms.DTO;



import com.tms.Entity.PaymentMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
public record LrCreateRequest(
        @NotNull(message = "LR date is required")
        LocalDate lrDate,

        @NotBlank(message = "Consigner name is required")
        @Size(max = 150, message = "Consigner name must not exceed 150 characters")
        String consignerName,

        @NotBlank(message = "Consignee name is required")
        @Size(max = 150, message = "Consignee name must not exceed 150 characters")
        String consigneeName,

        @NotNull(message = "Number of goods packages is required")
        @Min(value = 1, message = "Number of goods packages must be at least 1")
        Integer numberOfGoodsPackages,

        @NotNull(message = "Weight is required")
        @DecimalMin(value = "0.001", inclusive = true, message = "Weight must be greater than 0")
        @Digits(integer = 9, fraction = 3, message = "Weight format is invalid")
        BigDecimal weight,

        @NotBlank(message = "Goods description is required")
        @Size(max = 500, message = "Goods description must not exceed 500 characters")
        String goodsDescription,

        @NotNull(message = "Labour charge is required")
        @DecimalMin(value = "0.00", inclusive = true, message = "Labour charge cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Labour charge format is invalid")
        BigDecimal labourCharge,

        @NotNull(message = "Freight is required")
        @DecimalMin(value = "0.00", inclusive = true, message = "Freight cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Freight format is invalid")
        BigDecimal freight,

        @NotNull(message = "Payment mode is required")
        PaymentMode paymentMode,

        @Positive(message = "Memo ID must be positive")
        Long memoId,

        LocalDate lrReceivedDate
) {
}