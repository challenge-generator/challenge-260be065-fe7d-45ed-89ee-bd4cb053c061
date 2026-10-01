package com.financial.reactive.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private UUID id;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private String referenceId;

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REVERSED
    }

    public Transaction withStatus(TransactionStatus newStatus) {
        return this.toBuilder().status(newStatus).build();
    }

    public Transaction withReferenceId(String referenceId) {
        return this.toBuilder().referenceId(referenceId).build();
    }
}