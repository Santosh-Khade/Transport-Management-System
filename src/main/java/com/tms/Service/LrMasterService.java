package com.tms.Service;


import com.tms.DTO.LrCreateRequest;
import com.tms.DTO.LrResponse;
import com.tms.Entity.PaymentMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface LrMasterService {
    LrResponse createLr(LrCreateRequest request);
    LrResponse updateLr(Long id, LrCreateRequest request);
    LrResponse getLrById(Long id);
    LrResponse getLrByNumber(String lrNumber);
    Page<LrResponse> searchLrs(
            String lrNumber,
            Long consignerId,
            Long consigneeId,
            Long memoId,
            PaymentMode paymentMode,
            String goodsDescription,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    );
    void deleteLr(Long id);
}
