package com.tms.Controller;

import com.tms.DTO.ConsigneeRequest;
import com.tms.DTO.ConsigneeResponse;
import com.tms.Service.ConsigneeService;
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
@RequestMapping("/api/consignees")
@RequiredArgsConstructor
public class ConsigneeController {

    private final ConsigneeService consigneeService;

    @PostMapping
    public ResponseEntity<ConsigneeResponse> createConsignee(
            @Valid @RequestBody ConsigneeRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consigneeService.createConsignee(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsigneeResponse> updateConsignee(
            @PathVariable Long id,
            @Valid @RequestBody ConsigneeRequest request
    ) {
        return ResponseEntity.ok(consigneeService.updateConsignee(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsigneeResponse> getConsigneeById(@PathVariable Long id) {
        return ResponseEntity.ok(consigneeService.getConsigneeById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ConsigneeResponse>> searchConsignees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gstNumber,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(consigneeService.searchConsignees(name, gstNumber, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsignee(@PathVariable Long id) {
        consigneeService.deleteConsignee(id);
        return ResponseEntity.noContent().build();
    }
}