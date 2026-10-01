package com.financial.reactive.application.usecase;

import com.financial.reactive.domain.model.Transaction;
import com.financial.reactive.domain.model.Transaction.TransactionStatus;
import com.financial.reactive.domain.port.TransactionRepositoryPort;
import com.financial.reactive.infrastructure.exception.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessTransactionUseCase - Pruebas Unitarias")
class ProcessTransactionUseCaseTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    private ProcessTransactionUseCase processTransactionUseCase;

    private Transaction createTestTransaction() {
        return new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("1000.00"),
            "USD",
            "Test transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );
    }

    @BeforeEach
    void setUp() {
        processTransactionUseCase = new ProcessTransactionUseCase(transactionRepository);
    }

    @Test
    @DisplayName("Ejecutar transacción exitosamente cuando los datos son válidos")
    void execute_WithValidTransaction_ReturnsCompletedTransaction() {
        Transaction transaction = createTestTransaction();
        Transaction completedTransaction = transaction.withStatus(TransactionStatus.COMPLETED)
            .withReferenceId("TXN-" + UUID.randomUUID().toString().substring(0, 8));

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(completedTransaction));

        StepVerifier.create(processTransactionUseCase.execute(transaction))
            .expectNextMatches(tx -> tx.getStatus() == TransactionStatus.COMPLETED
                && tx.getReferenceId() != null
                && !tx.getReferenceId().isEmpty())
            .verifyComplete();

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Fallar cuando el monto es negativo")
    void execute_WithNegativeAmount_ReturnsError() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("-100.00"),
            "USD",
            "Invalid transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        StepVerifier.create(processTransactionUseCase.execute(invalidTransaction))
            .expectErrorSatisfies(throwable -> {
                assert throwable instanceof TransactionException;
                TransactionException ex = (TransactionException) throwable;
                assert ex.getErrorCode().equals("INVALID_AMOUNT");
            })
            .verify();

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Fallar cuando el monto es cero")
    void execute_WithZeroAmount_ReturnsError() {
        Transaction zeroAmountTransaction = new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            BigDecimal.ZERO,
            "USD",
            "Zero amount transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        StepVerifier.create(processTransactionUseCase.execute(zeroAmountTransaction))
            .expectErrorSatisfies(throwable -> {
                assert throwable instanceof TransactionException;
                TransactionException ex = (TransactionException) throwable;
                assert ex.getErrorCode().equals("INVALID_AMOUNT");
            })
            .verify();
    }

    @Test
    @DisplayName("Fallar cuando la cuenta es nula")
    void execute_WithNullAccountId_ReturnsError() {
        Transaction nullAccountTransaction = new Transaction(
            UUID.randomUUID(),
            null,
            new BigDecimal("100.00"),
            "USD",
            "Transaction without account",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        StepVerifier.create(processTransactionUseCase.execute(nullAccountTransaction))
            .expectErrorSatisfies(throwable -> {
                assert throwable instanceof TransactionException;
                TransactionException ex = (TransactionException) throwable;
                assert ex.getErrorCode().equals("INVALID_AMOUNT");
            })
            .verify();
    }

    @Test
    @DisplayName("Manejar error de repositorio al guardar transacción")
    void execute_WhenRepositoryFails_ReturnsError() {
        Transaction transaction = createTestTransaction();

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        StepVerifier.create(processTransactionUseCase.execute(transaction))
            .expectErrorSatisfies(throwable -> {
                assert throwable instanceof TransactionException;
                TransactionException ex = (TransactionException) throwable;
                assert ex.getErrorCode().equals("PROCESSING_ERROR");
            })
            .verify();
    }

    @Test
    @DisplayName("Ejecutar múltiples transacciones en paralelo correctamente")
    void execute_MultipleTransactions_CompletesAllSuccessfully() {
        Transaction tx1 = createTestTransaction();
        Transaction tx2 = new Transaction(
            UUID.randomUUID(),
            "ACC-002",
            new BigDecimal("2000.00"),
            "EUR",
            "Second transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        Transaction completedTx1 = tx1.withStatus(TransactionStatus.COMPLETED)
            .withReferenceId("TXN-001");
        Transaction completedTx2 = tx2.withStatus(TransactionStatus.COMPLETED)
            .withReferenceId("TXN-002");

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(completedTx1))
            .thenReturn(Mono.just(completedTx2));

        StepVerifier.create(
                processTransactionUseCase.execute(tx1)
                    .then(processTransactionUseCase.execute(tx2))
            )
            .verifyComplete();

        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Validar moneda soportada - USD")
    void execute_WithValidCurrencyUSD_CompletesSuccessfully() {
        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("500.00"),
            "USD",
            "USD transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        Transaction completed = transaction.withStatus(TransactionStatus.COMPLETED)
            .withReferenceId("TXN-USD-001");

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(completed));

        StepVerifier.create(processTransactionUseCase.execute(transaction))
            .expectNextMatches(tx -> tx.getStatus() == TransactionStatus.COMPLETED)
            .verifyComplete();
    }
}