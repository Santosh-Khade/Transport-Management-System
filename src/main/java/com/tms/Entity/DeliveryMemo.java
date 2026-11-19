package com.tms.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(
        name = "delivery_memos",
        indexes = {
                @Index(name = "idx_delivery_memos_memo_date", columnList = "memo_date"),
                @Index(name = "idx_delivery_memos_vehicle_id", columnList = "vehicle_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memoId;

    @Column(name = "memo_date", nullable = false)
    private LocalDate memoDate;

    @Column(name = "driver_name", nullable = false, length = 120)
    private String driverName;

    @Column(name = "vehicle_id", nullable = false, length = 50)
    private String vehicleId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}