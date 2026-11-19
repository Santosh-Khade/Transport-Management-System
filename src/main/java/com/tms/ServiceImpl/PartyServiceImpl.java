package com.tms.ServiceImpl;

import com.tms.DTO.PartyRequest;
import com.tms.DTO.PartyResponse;
import com.tms.Entity.Party;
import com.tms.Repository.PartyRepository;
import com.tms.Service.PartyService;
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
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    @Override
    @Transactional
    public PartyResponse createParty(PartyRequest request) {
        Party party = Party.builder()
                .name(request.name().trim())
                .address(request.address().trim())
                .gstNumber(normalizeGst(request.gstNumber()))
                .build();
        return toResponse(partyRepository.save(party));
    }

    @Override
    @Transactional
    public PartyResponse updateParty(Long partyId, PartyRequest request) {
        Party party = findPartyOrThrow(partyId);
        party.setName(request.name().trim());
        party.setAddress(request.address().trim());
        party.setGstNumber(normalizeGst(request.gstNumber()));
        return toResponse(partyRepository.save(party));
    }

    @Override
    public PartyResponse getPartyById(Long partyId) {
        return toResponse(findPartyOrThrow(partyId));
    }

    @Override
    public Page<PartyResponse> searchParties(String name, String gstNumber, Pageable pageable) {
        Specification<Party> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(gstNumber)) {
                predicates.add(cb.like(cb.upper(root.get("gstNumber")), "%" + gstNumber.trim().toUpperCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return partyRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public void deleteParty(Long partyId) {
        Party party = findPartyOrThrow(partyId);
        partyRepository.delete(party);
    }

    private Party findPartyOrThrow(Long partyId) {
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found with id: " + partyId));
    }

    private String normalizeGst(String gst) {
        if (!StringUtils.hasText(gst)) {
            return null;
        }
        return gst.trim().toUpperCase();
    }

    private PartyResponse toResponse(Party party) {
        return new PartyResponse(
                party.getPartyId(),
                party.getName(),
                party.getAddress(),
                party.getGstNumber(),
                party.getCreatedAt(),
                party.getUpdatedAt()
        );
    }
}
