package com.tms.ServiceImpl;

import com.tms.DTO.LrCreateRequest;
import com.tms.DTO.LrResponse;
import com.tms.Entity.DeliveryMemo;
import com.tms.Entity.LrMaster;
import com.tms.Entity.LrSequence;
import com.tms.Entity.Party;
import com.tms.Entity.PaymentMode;
import com.tms.Exception.ResourceNotFoundException;
import com.tms.Repository.DeliveryMemoRepository;
import com.tms.Repository.LrMasterRepository;
import com.tms.Repository.LrSequenceRepository;
import com.tms.Repository.PartyRepository;
import com.tms.Service.LrMasterService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class LrMasterServiceImpl implements LrMasterService {

    private final LrMasterRepository lrMasterRepository;
    private final LrSequenceRepository lrSequenceRepository;
    private final PartyRepository partyRepository;
    private final DeliveryMemoRepository deliveryMemoRepository;

    @Override
    @Transactional
    public LrResponse createLr(LrCreateRequest request) {
        validateLrRequest(request);

        Party consigner = resolveParty(request, true);
        Party consignee = resolveParty(request, false);

        if (consigner.getPartyId().equals(consignee.getPartyId())) {
            throw new IllegalArgumentException("Consigner and Consignee cannot be the same party");
        }

        DeliveryMemo memo = resolveMemo(request.memoId());
        String generatedLrNumber = generateNextLrNumber(request.lrDate().getYear());

        LrMaster lr = LrMaster.builder()
                .lrNumber(generatedLrNumber)
                .lrDate(request.lrDate())
                .consigner(consigner)
                .consignee(consignee)
                .numberOfGoodsPackages(request.numberOfGoodsPackages())
                .weight(request.weight())
                .goodsDescription(request.goodsDescription().trim())
                .labourCharge(request.labourCharge())
                .freight(request.freight())
                .paymentMode(request.paymentMode())
                .deliveryMemo(memo)
                .lrReceivedDate(request.lrReceivedDate())
                .build();

        return toResponse(lrMasterRepository.save(lr));
    }

    @Override
    @Transactional
    public LrResponse updateLr(Long id, LrCreateRequest request) {
        validateLrRequest(request);

        LrMaster lr = lrMasterRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LR not found with id: " + id));

        Party consigner = resolveParty(request, true);
        Party consignee = resolveParty(request, false);

        if (consigner.getPartyId().equals(consignee.getPartyId())) {
            throw new IllegalArgumentException("Consigner and Consignee cannot be the same party");
        }

        DeliveryMemo memo = resolveMemo(request.memoId());

        lr.setLrDate(request.lrDate());
        lr.setConsigner(consigner);
        lr.setConsignee(consignee);
        lr.setNumberOfGoodsPackages(request.numberOfGoodsPackages());
        lr.setWeight(request.weight());
        lr.setGoodsDescription(request.goodsDescription().trim());
        lr.setLabourCharge(request.labourCharge());
        lr.setFreight(request.freight());
        lr.setPaymentMode(request.paymentMode());
        lr.setDeliveryMemo(memo);
        lr.setLrReceivedDate(request.lrReceivedDate());

        return toResponse(lrMasterRepository.save(lr));
    }

    @Override
    public LrResponse getLrById(Long id) {
        LrMaster lr = lrMasterRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LR not found with id: " + id));
        return toResponse(lr);
    }

    @Override
    public LrResponse getLrByNumber(String lrNumber) {
        LrMaster lr = lrMasterRepository.findByLrNumber(lrNumber.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("LR not found with lrNumber: " + lrNumber));
        return toResponse(lr);
    }

    @Override
    public Page<LrResponse> searchLrs(
            String lrNumber,
            Long consignerId,
            Long consigneeId,
            Long memoId,
            PaymentMode paymentMode,
            String goodsDescription,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {
        Specification<LrMaster> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(lrNumber)) {
                predicates.add(cb.like(cb.upper(root.get("lrNumber")), "%" + lrNumber.trim().toUpperCase() + "%"));
            }
            if (consignerId != null) {
                Join<LrMaster, Party> consignerJoin = root.join("consigner", JoinType.INNER);
                predicates.add(cb.equal(consignerJoin.get("partyId"), consignerId));
            }
            if (consigneeId != null) {
                Join<LrMaster, Party> consigneeJoin = root.join("consignee", JoinType.INNER);
                predicates.add(cb.equal(consigneeJoin.get("id"), consigneeId));
            }
            if (memoId != null) {
                Join<LrMaster, DeliveryMemo> memoJoin = root.join("deliveryMemo", JoinType.LEFT);
                predicates.add(cb.equal(memoJoin.get("memoId"), memoId));
            }
            if (paymentMode != null) {
                predicates.add(cb.equal(root.get("paymentMode"), paymentMode));
            }
            if (StringUtils.hasText(goodsDescription)) {
                predicates.add(cb.like(cb.lower(root.get("goodsDescription")), "%" + goodsDescription.trim().toLowerCase() + "%"));
            }
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("lrDate"), fromDate));
            }
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("lrDate"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return lrMasterRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteLr(Long id) {
        LrMaster lr = lrMasterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LR not found with id: " + id));
        lrMasterRepository.delete(lr);
    }

    private void validateLrRequest(LrCreateRequest request) {
        if (StringUtils.hasText(request.consignerName())
                && StringUtils.hasText(request.consigneeName())
                && request.consignerName().trim().equalsIgnoreCase(request.consigneeName().trim())) {
            throw new IllegalArgumentException("Consigner and Consignee cannot be the same party");
        }
        if (request.lrReceivedDate() != null && request.lrReceivedDate().isBefore(request.lrDate())) {
            throw new IllegalArgumentException("LR received date cannot be before LR date");
        }
    }

    private Party resolveParty(LrCreateRequest request, boolean isConsigner) {
        String role = isConsigner ? "Consigner" : "Consignee";
        String name = isConsigner ? request.consignerName() : request.consigneeName();

        if (StringUtils.hasText(name)) {
            return findPartyByNameOrThrow(name, role);
        }
        throw new IllegalArgumentException(role + " name is required");
    }

    private Party findPartyByIdOrThrow(Long id, String role) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(role + " party not found with id: " + id));
    }

    private Party findPartyByNameOrThrow(String rawName, String role) {
        String normalizedName = rawName.trim();
        List<Party> matches = partyRepository.findAllByNameIgnoreCase(normalizedName);
        if (matches.isEmpty()) {
            throw new ResourceNotFoundException(role + " party not found with name: " + normalizedName);
        }
        if (matches.size() > 1) {
            throw new IllegalArgumentException("Multiple parties found with name: " + normalizedName);
        }
        return matches.get(0);
    }

    private String generateNextLrNumber(int year) {
        String seqKey = String.valueOf(year);
        LrSequence sequence = lrSequenceRepository.findByIdForUpdate(seqKey)
                .orElseGet(() -> lrSequenceRepository.saveAndFlush(
                        LrSequence.builder().seqKey(seqKey).currentValue(0L).build()
                ));

        long nextValue = sequence.getCurrentValue() + 1;
        sequence.setCurrentValue(nextValue);
        lrSequenceRepository.save(sequence);

        return String.format("LR-%d-%06d", year, nextValue);
    }

    private DeliveryMemo resolveMemo(Long memoId) {
        if (memoId == null) {
            return null;
        }
        return deliveryMemoRepository.findById(memoId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery memo not found with id: " + memoId));
    }

    private LrResponse toResponse(LrMaster lr) {
        Party consigner = lr.getConsigner();
        Party consignee = lr.getConsignee();
        DeliveryMemo memo = lr.getDeliveryMemo();

        return new LrResponse(
                lr.getId(),
                lr.getLrNumber(),
                lr.getLrDate(),
                consigner != null ? consigner.getPartyId() : null,
                consigner != null ? consigner.getName() : null,
                consignee != null ? consignee.getPartyId() : null,
                consignee != null ? consignee.getName() : null,
                lr.getNumberOfGoodsPackages(),
                lr.getWeight(),
                lr.getGoodsDescription(),
                lr.getLabourCharge(),
                lr.getFreight(),
                lr.getPaymentMode(),
                memo != null ? memo.getMemoId() : null,
                memo != null ? memo.getVehicleId() : null,
                lr.getLrReceivedDate(),
                lr.getTimeStamp()
        );
    }
}