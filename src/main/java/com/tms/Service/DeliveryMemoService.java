package com.tms.Service;

import com.tms.DTO.DeliveryMemoRequest;
import com.tms.DTO.DeliveryMemoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface DeliveryMemoService {
    DeliveryMemoResponse createMemo(DeliveryMemoRequest request);
    DeliveryMemoResponse updateMemo(Long memoId, DeliveryMemoRequest request);
    DeliveryMemoResponse getMemoById(Long memoId);
    Page<DeliveryMemoResponse> searchMemos(String driverName, String vehicleId, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    void deleteMemo(Long memoId);
}
