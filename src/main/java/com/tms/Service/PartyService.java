package com.tms.Service;

import com.tms.DTO.PartyRequest;
import com.tms.DTO.PartyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartyService {
    PartyResponse createParty(PartyRequest request);
    PartyResponse updateParty(Long partyId, PartyRequest request);
    PartyResponse getPartyById(Long partyId);
    Page<PartyResponse> searchParties(String name, String gstNumber, Pageable pageable);
    void deleteParty(Long partyId);
}