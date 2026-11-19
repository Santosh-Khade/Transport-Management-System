package com.tms.Service;


import com.tms.DTO.ConsigneeRequest;
import com.tms.DTO.ConsigneeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsigneeService {
    void validateUniqueConsigner(String name, Long excludeId);
    ConsigneeResponse findOrThrow(Long id);
    ConsigneeResponse createConsignee(ConsigneeRequest request);
    ConsigneeResponse updateConsignee(Long id, ConsigneeRequest request);
    ConsigneeResponse getConsigneeById(Long id);
    Page<ConsigneeResponse> searchConsignees(String name, String gstNumber, Pageable pageable);
    void deleteConsignee(Long id);
}
