package com.tms.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lr_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LrSequence {

    @Id
    @Column(name = "seq_key", length = 20, nullable = false)
    private String seqKey;

    @Column(name = "current_value", nullable = false)
    private Long currentValue;
}