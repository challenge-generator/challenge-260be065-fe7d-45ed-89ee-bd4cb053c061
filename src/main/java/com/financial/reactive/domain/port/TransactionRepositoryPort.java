package com.financial.reactive.domain.port;


import com.financial.reactive.domain.model.TransactionStatus;
import com.financial.reactive.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionRepositoryPort {
    Mono<Transaction> save(Transaction transaction);
    
    Mono<Transaction> findById(UUID id);
    
    Flux<Transaction> findByAccountId(String accountId);
    
    Flux<Transaction> findByStatus(Transaction.TransactionStatus status);
    
    Flux<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    Mono<Void> deleteById(UUID id);
}