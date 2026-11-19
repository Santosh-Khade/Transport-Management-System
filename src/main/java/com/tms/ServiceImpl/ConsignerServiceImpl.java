package com.tms.ServiceImpl;


import com.tms.DTO.ConsignerRequest;
import com.tms.DTO.ConsignerResponse;
import com.tms.Entity.Consigner;
import com.tms.Exception.DuplicateResourceException;

import com.tms.Repository.ConsignerRepository;
import com.tms.Service.ConsignerService;
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
public class ConsignerServiceImpl implements ConsignerService {

    private final ConsignerRepository consignerRepository;

    @Override
    @Transactional
    public ConsignerResponse createConsigner(ConsignerRequest request) {
        Consigner entity = Consigner.builder()
                .name(request.name().trim())
                .address(request.address().trim())
                .gstNumber(normalizeGst(request.gstNumber()))
                .build();
        return toResponse(consignerRepository.save(entity));
    }


    public void validateUniqueConsigner(String name, Long excludeId) {
        List<Consigner> existingByName = consignerRepository.findAllByNameIgnoreCase(name);
        boolean duplicateName = existingByName.stream()
                .anyMatch(c -> excludeId == null || !c.getId().equals(excludeId));
        if (duplicateName) {
            throw new DuplicateResourceException("Consigner already exists with name: " + name);
        }
    }

    @Override
    @Transactional
    public ConsignerResponse updateConsigner(Long id, ConsignerRequest request) {
        Consigner entity = findOrThrow(id);
        entity.setName(request.name().trim());
        entity.setAddress(request.address().trim());
        entity.setGstNumber(normalizeGst(request.gstNumber()));
        return toResponse(consignerRepository.save(entity));
    }

    @Override
    public ConsignerResponse getConsignerById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public Page<ConsignerResponse> searchConsigners(String name, String gstNumber, Pageable pageable) {
        Specification<Consigner> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(gstNumber)) {
                predicates.add(cb.like(cb.upper(root.get("gstNumber")), "%" + gstNumber.trim().toUpperCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return consignerRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteConsigner(Long id) {
        consignerRepository.delete(findOrThrow(id));
    }

    public Consigner findOrThrow(Long id) {
        return consignerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consigner not found with id: " + id));
    }

    private String normalizeGst(String gst) {
        return StringUtils.hasText(gst) ? gst.trim().toUpperCase() : null;
    }

    private ConsignerResponse toResponse(Consigner c) {
        return new ConsignerResponse(c.getId(), c.getName(), c.getAddress(), c.getGstNumber(), c.getCreatedAt(), c.getUpdatedAt());
    }
}