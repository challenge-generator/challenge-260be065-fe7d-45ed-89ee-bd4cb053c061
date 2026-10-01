package com.financial.reactive.infrastructure.exception;

import java.util.UUID;

public class TransactionException extends RuntimeException {

    private final UUID transactionId;
    private final String errorCode;
    private final String details;

    public TransactionException(String message, String errorCode) {
        super(message);
        this.transactionId = null;
        this.errorCode = errorCode;
        this.details = null;
    }

    public TransactionException(String message, String errorCode, UUID transactionId) {
        super(message);
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.details = null;
    }

    public TransactionException(String message, String errorCode, UUID transactionId, String details) {
        super(message);
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.details = details;
    }

    public TransactionException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.transactionId = null;
        this.errorCode = errorCode;
        this.details = null;
    }

    public TransactionException(String message, Throwable cause, String errorCode, UUID transactionId) {
        super(message, cause);
        this.transactionId = transactionId;
        this.errorCode = errorCode;
        this.details = null;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    public static TransactionException invalidAmount(UUID transactionId, String reason) {
        return new TransactionException(
            "El monto de la transacción es inválido: " + reason,
            "INVALID_AMOUNT",
            transactionId,
            reason
        );
    }

    public static TransactionException accountNotFound(String accountId) {
        return new TransactionException(
            "Cuenta no encontrada: " + accountId,
            "ACCOUNT_NOT_FOUND"
        );
    }

    public static TransactionException insufficientFunds(UUID transactionId, BigDecimal available, BigDecimal required) {
        return new TransactionException(
            "Fondos insuficientes. Disponible: " + available + ", Requerido: " + required,
            "INSUFFICIENT_FUNDS",
            transactionId,
            "available=" + available + ",required=" + required
        );
    }

    public static TransactionException processingError(UUID transactionId, Throwable cause) {
        return new TransactionException(
            "Error al procesar la transacción",
            cause,
            "PROCESSING_ERROR",
            transactionId
        );
    }

    public static TransactionException duplicateTransaction(UUID transactionId, String referenceId) {
        return new TransactionException(
            "Transacción duplicada detectada",
            "DUPLICATE_TRANSACTION",
            transactionId,
            "referenceId=" + referenceId
        );
    }
}