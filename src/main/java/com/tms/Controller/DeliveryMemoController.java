package com.tms.Controller;

import com.tms.DTO.DeliveryMemoRequest;
import com.tms.DTO.DeliveryMemoResponse;
import com.tms.Service.DeliveryMemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/delivery-memos")
@RequiredArgsConstructor
public class DeliveryMemoController {

    private final DeliveryMemoService deliveryMemoService;

    @PostMapping
    public ResponseEntity<DeliveryMemoResponse> createMemo(@Valid @RequestBody DeliveryMemoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryMemoService.createMemo(request));
    }

    @PutMapping("/{memoId}")
    public ResponseEntity<DeliveryMemoResponse> updateMemo(
            @PathVariable Long memoId,
            @Valid @RequestBody DeliveryMemoRequest request
    ) {
        return ResponseEntity.ok(deliveryMemoService.updateMemo(memoId, request));
    }

    @GetMapping("/{memoId}")
    public ResponseEntity<DeliveryMemoResponse> getMemoById(@PathVariable Long memoId) {
        return ResponseEntity.ok(deliveryMemoService.getMemoById(memoId));
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryMemoResponse>> searchMemos(
            @RequestParam(required = false) String driverName,
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @PageableDefault(size = 10, sort = "memoDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(deliveryMemoService.searchMemos(driverName, vehicleId, fromDate, toDate, pageable));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long memoId) {
        deliveryMemoService.deleteMemo(memoId);
        return ResponseEntity.noContent().build();
    }
}