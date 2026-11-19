package com.tms.Controller;

import com.tms.DTO.LrCreateRequest;
import com.tms.DTO.LrResponse;
import com.tms.Entity.PaymentMode;
import com.tms.Service.LrMasterService;
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
@RequestMapping("/api/lrs")
@RequiredArgsConstructor
public class LrMasterController {

    private final LrMasterService lrMasterService;

    @PostMapping
    public ResponseEntity<LrResponse> createLr(@Valid @RequestBody LrCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lrMasterService.createLr(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LrResponse> updateLr(
            @PathVariable Long id,
            @Valid @RequestBody LrCreateRequest request
    ) {
        return ResponseEntity.ok(lrMasterService.updateLr(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LrResponse> getLrById(@PathVariable Long id) {
        return ResponseEntity.ok(lrMasterService.getLrById(id));
    }

    @GetMapping("/number/{lrNumber}")
    public ResponseEntity<LrResponse> getLrByNumber(@PathVariable String lrNumber) {
        return ResponseEntity.ok(lrMasterService.getLrByNumber(lrNumber));
    }

    @GetMapping
    public ResponseEntity<Page<LrResponse>> searchLrs(
            @RequestParam(required = false) String lrNumber,
            @RequestParam(required = false) Long consignerId,
            @RequestParam(required = false) Long consigneeId,
            @RequestParam(required = false) Long memoId,
            @RequestParam(required = false) PaymentMode paymentMode,
            @RequestParam(required = false) String goodsDescription,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @PageableDefault(size = 10, sort = "lrDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<LrResponse> result = lrMasterService.searchLrs(
                lrNumber,
                consignerId,
                consigneeId,
                memoId,
                paymentMode,
                goodsDescription,
                fromDate,
                toDate,
                pageable
        );
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLr(@PathVariable Long id) {
        lrMasterService.deleteLr(id);
        return ResponseEntity.noContent().build();
    }
}
