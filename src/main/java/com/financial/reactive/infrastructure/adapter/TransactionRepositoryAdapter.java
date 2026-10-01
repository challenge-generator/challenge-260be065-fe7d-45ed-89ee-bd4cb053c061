package com.financial.reactive.infrastructure.adapter;

import com.financial.reactive.domain.model.Transaction;
import com.financial.reactive.domain.model.Transaction.TransactionStatus;
import com.financial.reactive.domain.port.TransactionRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Adaptador de repositorio que implementa el puerto del dominio.
 * Utiliza almacenamiento en memoria con estructura concurrente para demostrar
 * el patrón reactivo sin dependencia de base de datos externa.
 * En producción, este adaptadores sustituiría por una implementación real
 * con R2DBC, MongoDB reactivo o PostgreSQL reactivo.
 */
@Repository
@Slf4j
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final Map<UUID, Transaction> transactionStore = new ConcurrentHashMap<>();
    private final Map<String, Map<UUID, Transaction>> accountIndex = new ConcurrentHashMap<>();
    private final AtomicLong operationCounter = new AtomicLong(0);
    private static final int MAX_STORE_SIZE = 10000;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return Mono.fromCallable(() -> {
            operationCounter.incrementAndGet();
            log.debug("Guardando transacción: {} - Total operaciones: {}", 
                transaction.getId(), operationCounter.get());
            
            if (transactionStore.size() >= MAX_STORE_SIZE && !transactionStore.containsKey(transaction.getId())) {
                throw new IllegalStateException("Store capacity exceeded");
            }
            
            transactionStore.put(transaction.getId(), transaction);
            
            String accountId = transaction.getAccountId();
            if (accountId != null) {
                accountIndex.computeIfAbsent(accountId, k -> new ConcurrentHashMap<>())
                    .put(transaction.getId(), transaction);
            }
            
            log.info("Transacción guardada exitosamente: {} para cuenta: {}", 
                transaction.getId(), accountId);
            return transaction;
        });
    }

    @Override
    public Mono<Transaction> findById(UUID id) {
        return Mono.fromCallable(() -> {
            log.debug("Buscando transacción por ID: {}", id);
            Transaction transaction = transactionStore.get(id);
            if (transaction == null) {
                log.warn("Transacción no encontrada: {}", id);
            }
            return transaction;
        });
    }

    @Override
    public Flux<Transaction> findByAccountId(String accountId) {
        return Flux.fromIterable(() -> {
            log.debug("Consultando transacciones para cuenta: {}", accountId);
            Map<UUID, Transaction> accountTransactions = accountIndex.get(accountId);
            if (accountTransactions == null) {
                return java.util.Collections.emptyIterator();
            }
            return accountTransactions.values().iterator();
        }).sort((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()));
    }

    @Override
    public Flux<Transaction> findByStatus(TransactionStatus status) {
        return Flux.fromIterable(() -> {
            log.debug("Consultando transacciones con estado: {}", status);
            return transactionStore.values().stream()
                .filter(t -> t.getStatus() == status)
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .iterator();
        });
    }

    @Override
    public Flux<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return Flux.fromIterable(() -> {
            log.debug("Consultando transacciones entre {} y {}", start, end);
            return transactionStore.values().stream()
                .filter(t -> !t.getTimestamp().isBefore(start) && !t.getTimestamp().isAfter(end))
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .iterator();
        });
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return Mono.fromRunnable(() -> {
            log.info("Eliminando transacción: {}", id);
            Transaction removed = transactionStore.remove(id);
            if (removed != null && removed.getAccountId() != null) {
                Map<UUID, Transaction> accountTransactions = accountIndex.get(removed.getAccountId());
                if (accountTransactions != null) {
                    accountTransactions.remove(id);
                }
            }
            log.info("Transacción eliminada: {}", id);
        });
    }

    /**
     * Método de utilidad para limpiar el store (útil para tests).
     */
    public Mono<Void> clearAll() {
        return Mono.fromRunnable(() -> {
            log.warn("Limpiando todos los datos del repositorio");
            transactionStore.clear();
            accountIndex.clear();
            log.info("Repositorio limpiado");
        });
    }

    /**
     * Obtiene estadísticas del repositorio.
     */
    public Mono<Map<String, Object>> getStatistics() {
        return Mono.fromCallable(() -> {
            Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("totalTransactions", transactionStore.size());
            stats.put("totalAccounts", accountIndex.size());
            stats.put("totalOperations", operationCounter.get());
            
            Map<String, Long> statusCount = new java.util.HashMap<>();
            transactionStore.values().forEach(t -> {
                String status = t.getStatus().name();
                statusCount.merge(status, 1L, Long::sum);
            });
            stats.put("byStatus", statusCount);
            
            return stats;
        });
    }
}