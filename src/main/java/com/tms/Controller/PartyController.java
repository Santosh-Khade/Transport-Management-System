package com.tms.Controller;


import com.tms.DTO.PartyRequest;
import com.tms.DTO.PartyResponse;
import com.tms.Service.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyResponse> createParty(@Valid @RequestBody PartyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.createParty(request));
    }

    @PutMapping("/{partyId}")
    public ResponseEntity<PartyResponse> updateParty(
            @PathVariable Long partyId,
            @Valid @RequestBody PartyRequest request
    ) {
        return ResponseEntity.ok(partyService.updateParty(partyId, request));
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<PartyResponse> getPartyById(@PathVariable Long partyId) {
        return ResponseEntity.ok(partyService.getPartyById(partyId));
    }

    @GetMapping
    public ResponseEntity<Page<PartyResponse>> searchParties(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gstNumber,
            @PageableDefault(size = 10, sort = "partyId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(partyService.searchParties(name, gstNumber, pageable));
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long partyId) {
        partyService.deleteParty(partyId);
        return ResponseEntity.noContent().build();
    }
}