package com.tms.Controller;

import com.tms.DTO.ConsignerRequest;
import com.tms.DTO.ConsignerResponse;
import com.tms.Service.ConsignerService;
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
@RequestMapping("/api/consigners")
@RequiredArgsConstructor
public class ConsignerController {

    private final ConsignerService consignerService;

    @PostMapping
    public ResponseEntity<ConsignerResponse> createConsigner(
            @Valid @RequestBody ConsignerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consignerService.createConsigner(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsignerResponse> updateConsigner(
            @PathVariable Long id,
            @Valid @RequestBody ConsignerRequest request
    ) {
        return ResponseEntity.ok(consignerService.updateConsigner(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsignerResponse> getConsignerById(@PathVariable Long id) {
        return ResponseEntity.ok(consignerService.getConsignerById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ConsignerResponse>> searchConsigners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gstNumber,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(consignerService.searchConsigners(name, gstNumber, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsigner(@PathVariable Long id) {
        consignerService.deleteConsigner(id);
        return ResponseEntity.noContent().build();
    }
}