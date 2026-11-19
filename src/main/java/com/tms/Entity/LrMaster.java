package com.tms.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(
        name = "lr_master",
        indexes = {
                @Index(name = "idx_lr_master_lr_number", columnList = "lr_number", unique = true),
                @Index(name = "idx_lr_master_lr_date", columnList = "lr_date"),
                @Index(name = "idx_lr_master_consigner_id", columnList = "consigner_id"),
                @Index(name = "idx_lr_master_consignee_id", columnList = "consignee_id"),
                @Index(name = "idx_lr_master_memo_id", columnList = "memo_id"),
                @Index(name = "idx_lr_master_payment_mode", columnList = "payment_mode")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LrMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lr_id")
    private Long id;

    @Column(name = "lr_number", nullable = false, unique = true, length = 30)
    private String lrNumber;

    @Column(name = "lr_date", nullable = false)
    private LocalDate lrDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consigner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lr_consigner"))
    private Party consigner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consignee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lr_consignee"))
    private Party consignee;

    @Column(name = "number_of_goods_packages", nullable = false)
    private Integer numberOfGoodsPackages;

    @Column(name = "weight", nullable = false, precision = 12, scale = 3)
    private BigDecimal weight;

    @Column(name = "goods_description", nullable = false, length = 500)
    private String goodsDescription;

    @Column(name = "labour_charge", nullable = false, precision = 12, scale = 2)
    private BigDecimal labourCharge;

    @Column(name = "freight", nullable = false, precision = 12, scale = 2)
    private BigDecimal freight;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false, length = 20)
    private PaymentMode paymentMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", foreignKey = @ForeignKey(name = "fk_lr_delivery_memo"))
    private DeliveryMemo deliveryMemo;

    @Column(name = "lr_received_date")
    private LocalDate lrReceivedDate;

    @CreationTimestamp
    @Column(name = "time_stamp", nullable = false, updatable = false)
    private Instant timeStamp;
}