package com.tms.Service;


import com.tms.DTO.ConsignerRequest;
import com.tms.DTO.ConsignerResponse;
import com.tms.Entity.Consigner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsignerService {
    void validateUniqueConsigner(String name, Long excludeId);
    Consigner findOrThrow(Long id);
    ConsignerResponse createConsigner(ConsignerRequest request);
    ConsignerResponse updateConsigner(Long id, ConsignerRequest request);
    ConsignerResponse getConsignerById(Long id);
    Page<ConsignerResponse> searchConsigners(String name, String gstNumber, Pageable pageable);
    void deleteConsigner(Long id);
}