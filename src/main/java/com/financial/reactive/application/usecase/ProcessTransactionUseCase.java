package com.financial.reactive.application.usecase;

import com.financial.reactive.domain.model.Transaction;
import com.financial.reactive.domain.model.Transaction.TransactionStatus;
import com.financial.reactive.domain.port.TransactionRepositoryPort;
import com.financial.reactive.infrastructure.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Caso de uso para el procesamiento de transacciones financieras.
 * Implementa la lógica de negocio de manera reactiva y funcional,
 * utilizando operadores de Reactor para composición de operaciones.
 */
@Component
@Slf4j
public class ProcessTransactionUseCase {

    private final TransactionRepositoryPort transactionRepository;
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DURATION_MS = 500L;

    public ProcessTransactionUseCase(TransactionRepositoryPort transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Procesa una nueva transacción con validación y persistencia reactiva.
     * Aplica retry automático en caso de fallos transitorios.
     */
    public Mono<Transaction> processTransaction(Transaction transaction) {
        log.info("Iniciando procesamiento de transacción para cuenta: {}", transaction.getAccountId());
        
        return validateTransaction(transaction)
            .flatMap(this::enrichTransaction)
            .flatMap(this::persistWithRetry)
            .doOnSuccess(result -> log.info("Transacción procesada exitosamente: {}", result.getId()))
            .doOnError(error -> log.error("Error al procesar transacción: {}", error.getMessage()));
    }

    /**
     * Valida los datos de la transacción antes de procesarla.
     * Retorna un error tipado si la validación falla.
     */
    private Mono<Transaction> validateTransaction(Transaction transaction) {
        return Mono.just(transaction)
            .filter(t -> t.getAccountId() != null && !t.getAccountId().isBlank())
            .switchIfEmpty(Mono.error(new TransactionException(
                "INVALID_ACCOUNT", 
                "El ID de cuenta es requerido",
                TransactionException.ErrorType.VALIDATION)))
            .filter(t -> t.getAmount() != null && t.getAmount().compareTo(MIN_AMOUNT) >= 0)
            .switchIfEmpty(Mono.error(new TransactionException(
                "INVALID_AMOUNT",
                "El monto debe ser mayor a " + MIN_AMOUNT,
                TransactionException.ErrorType.VALIDATION)))
            .filter(t -> t.getAmount().compareTo(MAX_AMOUNT) <= 0)
            .switchIfEmpty(Mono.error(new TransactionException(
                "AMOUNT_EXCEEDED",
                "El monto excede el máximo permitido de " + MAX_AMOUNT,
                TransactionException.ErrorType.VALIDATION)))
            .filter(t -> t.getCurrency() != null && t.getCurrency().matches("^[A-Z]{3}$"))
            .switchIfEmpty(Mono.error(new TransactionException(
                "INVALID_CURRENCY",
                "La moneda debe ser un código ISO de 3 letras",
                TransactionException.ErrorType.VALIDATION)));
    }

    /**
     * Enriquece la transacción con datos adicionales antes de persistir.
     */
    private Mono<Transaction> enrichTransaction(Transaction transaction) {
        return Mono.just(transaction)
            .map(t -> {
                if (t.getId() == null) {
                    t = new Transaction(
                        UUID.randomUUID(),
                        t.getAccountId(),
                        t.getAmount(),
                        t.getCurrency(),
                        t.getDescription(),
                        java.time.LocalDateTime.now(),
                        TransactionStatus.PENDING,
                        null
                    );
                }
                return t;
            });
    }

    /**
     * Persiste la transacción con mecanismo de retry para fallos transitorios.
     */
    private Mono<Transaction> persistWithRetry(Transaction transaction) {
        return transactionRepository.save(transaction)
            .retryWhen(Retry.backoff(MAX_RETRY_ATTEMPTS, Duration.ofMillis(RETRY_DURATION_MS))
                .filter(throwable -> isTransientError(throwable))
                .doBeforeRetry(signal -> log.warn("Reintentando persistencia de transacción, intento: {}", 
                    signal.totalRetries() + 1)))
            .onErrorResume(TransactionException.class, error -> Mono.error(error))
            .onErrorResume(Exception.class, error -> Mono.error(new TransactionException(
                "PERSISTENCE_ERROR",
                "Error al persistir la transacción: " + error.getMessage(),
                TransactionException.ErrorType.PERSISTENCE)));
    }

    /**
     * Determina si un error es transitorio y puede ser reintentado.
     */
    private boolean isTransientError(Throwable error) {
        return error instanceof java.io.IOException ||
               error instanceof java.net.SocketException ||
               error instanceof org.springframework.dao.DataAccessException;
    }

    /**
     * Recupera el historial de transacciones de una cuenta específica.
     */
    public Flux<Transaction> getAccountTransactions(String accountId) {
        log.info("Consultando transacciones para cuenta: {}", accountId);
        
        return transactionRepository.findByAccountId(accountId)
            .take(100)
            .doOnComplete(() -> log.info("Consulta de transacciones completada para cuenta: {}", accountId))
            .doOnError(error -> log.error("Error al consultar transacciones: {}", error.getMessage()));
    }

    /**
     * Recupera transacciones por estado con paginación reactiva.
     */
    public Flux<Transaction> getTransactionsByStatus(TransactionStatus status) {
        log.info("Consultando transacciones con estado: {}", status);
        
        return transactionRepository.findByStatus(status)
            .take(50)
            .doOnComplete(() -> log.info("Consulta por estado completada: {}", status));
    }

    /**
     * Procesa múltiples transacciones de forma paralela usando merge.
     */
    public Flux<Transaction> processBatchTransactions(Flux<Transaction> transactions) {
        log.info("Iniciando procesamiento de lote de transacciones");
        
        return transactions
            .flatMap(this::processTransaction)
            .collectList()
            .flatMapMany(results -> {
                long successful = results.stream()
                    .filter(t -> t.getStatus() == TransactionStatus.COMPLETED)
                    .count();
                log.info("Lote procesado: {} exitosas de {}", successful, results.size());
                return Flux.fromIterable(results);
            });
    }

    /**
     * Cancela una transacción existente actualizando su estado.
     */
    public Mono<Transaction> cancelTransaction(UUID transactionId) {
        log.info("Cancelando transacción: {}", transactionId);
        
        return transactionRepository.findById(transactionId)
            .switchIfEmpty(Mono.error(new TransactionException(
                "TRANSACTION_NOT_FOUND",
                "Transacción no encontrada: " + transactionId,
                TransactionException.ErrorType.NOT_FOUND)))
            .filter(t -> t.getStatus() == TransactionStatus.PENDING)
            .switchIfEmpty(Mono.error(new TransactionException(
                "INVALID_STATE_TRANSITION",
                "Solo se pueden cancelar transacciones en estado PENDING",
                TransactionException.ErrorType.STATE)))
            .flatMap(t -> {
                Transaction cancelled = new Transaction(
                    t.getId(),
                    t.getAccountId(),
                    t.getAmount(),
                    t.getCurrency(),
                    t.getDescription(),
                    t.getTimestamp(),
                    TransactionStatus.CANCELLED,
                    t.getReferenceId()
                );
                return transactionRepository.save(cancelled);
            })
            .doOnSuccess(result -> log.info("Transacción cancelada exitosamente: {}", transactionId));
    }
}