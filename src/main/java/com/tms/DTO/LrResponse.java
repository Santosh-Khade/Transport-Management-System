package com.tms.DTO;





import com.tms.Entity.PaymentMode;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record LrResponse(
        Long id,
        String lrNumber,
        LocalDate lrDate,
        Long consignerId,
        String consignerName,
        Long consigneeId,
        String consigneeName,
        Integer numberOfGoodsPackages,
        BigDecimal weight,
        String goodsDescription,
        BigDecimal labourCharge,
        BigDecimal freight,
        PaymentMode paymentMode,
        Long memoId,
        String vehicleId,
        LocalDate lrReceivedDate,
        Instant timeStamp
) {
}