package com.financial.reactive.infrastructure.controller;


import com.financial.reactive.domain.model.TransactionStatus;
import com.financial.reactive.application.usecase.ProcessTransactionUseCase;
import com.financial.reactive.domain.model.Transaction;
import com.financial.reactive.domain.port.TransactionRepositoryPort;
import com.financial.reactive.infrastructure.exception.TransactionException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final ProcessTransactionUseCase processTransactionUseCase;
    private final TransactionRepositoryPort transactionRepository;

    public TransactionController(
            ProcessTransactionUseCase processTransactionUseCase,
            TransactionRepositoryPort transactionRepository) {
        this.processTransactionUseCase = processTransactionUseCase;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Transaction>> processTransaction(@RequestBody TransactionRequest request) {
        return processTransactionUseCase.execute(
                request.accountId(),
                request.amount(),
                request.currency(),
                request.description()
        ).map(transaction -> {
            if (transaction.getStatus() == Transaction.TransactionStatus.COMPLETED) {
                return ResponseEntity.ok(transaction);
            } else if (transaction.getStatus() == Transaction.TransactionStatus.FAILED) {
                return ResponseEntity.badRequest().body(transaction);
            } else {
                return ResponseEntity.accepted().body(transaction);
            }
        }).onErrorResume(TransactionException.class, e ->
                Mono.error(e)
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Transaction>> getTransactionById(@PathVariable UUID id) {
        return transactionRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Transaction> getTransactionsByAccount(@PathVariable String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Transaction> getTransactionsByStatus(@PathVariable String status) {
        try {
            Transaction.TransactionStatus transactionStatus = Transaction.TransactionStatus.valueOf(status.toUpperCase());
            return transactionRepository.findByStatus(transactionStatus);
        } catch (IllegalArgumentException e) {
            return Flux.error(new TransactionException("Invalid status: " + status));
        }
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable UUID id) {
        return transactionRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    public record TransactionRequest(
            String accountId,
            BigDecimal amount,
            String currency,
            String description
    ) {}
}