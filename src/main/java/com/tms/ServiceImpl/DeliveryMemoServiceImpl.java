package com.tms.ServiceImpl;

import com.tms.DTO.DeliveryMemoRequest;
import com.tms.DTO.DeliveryMemoResponse;
import com.tms.Entity.DeliveryMemo;
import com.tms.Repository.DeliveryMemoRepository;
import com.tms.Service.DeliveryMemoService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryMemoServiceImpl implements DeliveryMemoService {

    private final DeliveryMemoRepository deliveryMemoRepository;

    @Override
    @Transactional
    public DeliveryMemoResponse createMemo(DeliveryMemoRequest request) {
        DeliveryMemo memo = DeliveryMemo.builder()
                .memoDate(request.memoDate())
                .driverName(request.driverName().trim())
                .vehicleId(request.vehicleId().trim().toUpperCase())
                .build();
        return toResponse(deliveryMemoRepository.save(memo));
    }

    @Override
    @Transactional
    public DeliveryMemoResponse updateMemo(Long memoId, DeliveryMemoRequest request) {
        DeliveryMemo memo = findMemoOrThrow(memoId);
        memo.setMemoDate(request.memoDate());
        memo.setDriverName(request.driverName().trim());
        memo.setVehicleId(request.vehicleId().trim().toUpperCase());
        return toResponse(deliveryMemoRepository.save(memo));
    }

    @Override
    public DeliveryMemoResponse getMemoById(Long memoId) {
        return toResponse(findMemoOrThrow(memoId));
    }

    @Override
    public Page<DeliveryMemoResponse> searchMemos(
            String driverName,
            String vehicleId,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {
        Specification<DeliveryMemo> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(driverName)) {
                predicates.add(cb.like(cb.lower(root.get("driverName")), "%" + driverName.trim().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(vehicleId)) {
                predicates.add(cb.like(cb.upper(root.get("vehicleId")), "%" + vehicleId.trim().toUpperCase() + "%"));
            }
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("memoDate"), fromDate));
            }
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("memoDate"), toDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return deliveryMemoRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteMemo(Long memoId) {
        DeliveryMemo memo = findMemoOrThrow(memoId);
        deliveryMemoRepository.delete(memo);
    }

    private DeliveryMemo findMemoOrThrow(Long memoId) {
        return deliveryMemoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery memo not found with id: " + memoId));
    }

    private DeliveryMemoResponse toResponse(DeliveryMemo memo) {
        return new DeliveryMemoResponse(
                memo.getMemoId(),
                memo.getMemoDate(),
                memo.getDriverName(),
                memo.getVehicleId(),
                memo.getCreatedAt(),
                memo.getUpdatedAt()
        );
    }
}