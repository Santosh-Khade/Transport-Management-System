package com.tms.ServiceImpl;


import com.tms.DTO.ConsigneeRequest;
import com.tms.DTO.ConsigneeResponse;
import com.tms.Entity.Consignee;
import com.tms.Exception.DuplicateResourceException;
import com.tms.Repository.ConsigneeRepository;
import com.tms.Service.ConsigneeService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsigneeServiceImpl implements ConsigneeService {

    private final ConsigneeRepository consigneeRepository;

    @Override
    @Transactional
    public ConsigneeResponse createConsignee(ConsigneeRequest request) {
        Consignee entity = Consignee.builder()
                .name(request.name().trim())
                .address(request.address().trim())
                .gstNumber(normalizeGst(request.gstNumber()))
                .build();
        return toResponse(consigneeRepository.save(entity));
    }

    @Override
    @Transactional
    public ConsigneeResponse updateConsignee(Long id, ConsigneeRequest request) {
        Consignee entity = findConsigneeOrThrow(id);
        entity.setName(request.name().trim());
        entity.setAddress(request.address().trim());
        entity.setGstNumber(normalizeGst(request.gstNumber()));
        return toResponse(consigneeRepository.save(entity));
    }




    @Override
    public ConsigneeResponse getConsigneeById(Long id) {
        return toResponse(findConsigneeOrThrow(id));
    }

    @Override
    public Page<ConsigneeResponse> searchConsignees(String name, String gstNumber, Pageable pageable) {
        Specification<Consignee> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(gstNumber)) {
                predicates.add(cb.like(cb.upper(root.get("gstNumber")), "%" + gstNumber.trim().toUpperCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return consigneeRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteConsignee(Long id) {
        consigneeRepository.delete(findConsigneeOrThrow(id));
    }

    @Override
    public void validateUniqueConsigner(String name, Long excludeId) {
        List<Consignee> existingByName = consigneeRepository.findAllByNameIgnoreCase(name);
        boolean duplicateName = existingByName.stream()
                .anyMatch(c -> excludeId == null || !c.getId().equals(excludeId));
        if (duplicateName) {
            throw new DuplicateResourceException("Consignee already exists with name: " + name);
        }
    }

    @Override
    public ConsigneeResponse findOrThrow(Long id) {
        return toResponse(findConsigneeOrThrow(id));
    }

    private Consignee findConsigneeOrThrow(Long id) {
        return consigneeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consignee not found with id: " + id));
    }

    private String normalizeGst(String gst) {
        return StringUtils.hasText(gst) ? gst.trim().toUpperCase() : null;
    }

    private ConsigneeResponse toResponse(Consignee c) {
        return new ConsigneeResponse(c.getId(), c.getName(), c.getAddress(), c.getGstNumber(), c.getCreatedAt(), c.getUpdatedAt());
    }
}
