package com.financial.reactive.infrastructure.controller;

import com.financial.reactive.domain.model.Transaction;
import com.financial.reactive.domain.model.Transaction.TransactionStatus;
import com.financial.reactive.domain.port.TransactionRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(TransactionController.class)
@DisplayName("TransactionController - Pruebas de Integración")
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransactionRepositoryPort transactionRepository;

    private String generateValidTransactionJson() {
        return """
            {
                "accountId": "ACC-001",
                "amount": 1000.00,
                "currency": "USD",
                "description": "Test transaction"
            }
            """;
    }

    private Transaction createTestTransaction() {
        return new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("1000.00"),
            "USD",
            "Test transaction",
            LocalDateTime.now(),
            TransactionStatus.COMPLETED,
            "TXN-" + UUID.randomUUID().toString().substring(0, 8)
        );
    }

    @Test
    @DisplayName("POST /api/transactions - Crear transacción exitosamente")
    void createTransaction_ReturnsCreatedStatus() {
        Transaction savedTransaction = createTestTransaction();

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(savedTransaction));

        webTestClient.post()
            .uri("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(generateValidTransactionJson())
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").exists()
            .jsonPath("$.accountId").isEqualTo("ACC-001")
            .jsonPath("$.status").isEqualTo("COMPLETED");
    }

    @Test
    @DisplayName("POST /api/transactions - Validar monto negativo")
    void createTransaction_WithNegativeAmount_ReturnsBadRequest() {
        String invalidJson = """
            {
                "accountId": "ACC-001",
                "amount": -100.00,
                "currency": "USD",
                "description": "Invalid transaction"
            }
            """;

        webTestClient.post()
            .uri("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidJson)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("POST /api/transactions - Validar monto requerido")
    void createTransaction_WithoutAmount_ReturnsBadRequest() {
        String invalidJson = """
            {
                "accountId": "ACC-001",
                "currency": "USD",
                "description": "Missing amount"
            }
            """;

        webTestClient.post()
            .uri("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidJson)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("GET /api/transactions/{id} - Obtener transacción por ID")
    void getTransactionById_ReturnsTransaction() {
        Transaction transaction = createTestTransaction();

        when(transactionRepository.findById(any(UUID.class)))
            .thenReturn(Mono.just(transaction));

        webTestClient.get()
            .uri("/api/transactions/{id}", transaction.getId())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(transaction.getId().toString())
            .jsonPath("$.accountId").isEqualTo("ACC-001");
    }

    @Test
    @DisplayName("GET /api/transactions/{id} - Transacción no encontrada")
    void getTransactionById_WhenNotFound_ReturnsNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        when(transactionRepository.findById(nonExistentId))
            .thenReturn(Mono.empty());

        webTestClient.get()
            .uri("/api/transactions/{id}", nonExistentId)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("GET /api/transactions/account/{accountId} - Listar transacciones por cuenta")
    void getTransactionsByAccount_ReturnsTransactionList() {
        Transaction tx1 = new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("100.00"),
            "USD",
            "First transaction",
            LocalDateTime.now(),
            TransactionStatus.COMPLETED,
            "TXN-001"
        );

        Transaction tx2 = new Transaction(
            UUID.randomUUID(),
            "ACC-001",
            new BigDecimal("200.00"),
            "EUR",
            "Second transaction",
            LocalDateTime.now(),
            TransactionStatus.COMPLETED,
            "TXN-002"
        );

        when(transactionRepository.findByAccountId("ACC-001"))
            .thenReturn(Flux.just(tx1, tx2));

        webTestClient.get()
            .uri("/api/transactions/account/{accountId}", "ACC-001")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.length()").isEqualTo(2)
            .jsonPath("$[0].accountId").isEqualTo("ACC-001")
            .jsonPath("$[1].accountId").isEqualTo("ACC-001");
    }

    @Test
    @DisplayName("GET /api/transactions/status/{status} - Listar transacciones por estado")
    void getTransactionsByStatus_ReturnsTransactionList() {
        Transaction pendingTx = new Transaction(
            UUID.randomUUID(),
            "ACC-002",
            new BigDecimal("500.00"),
            "USD",
            "Pending transaction",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        when(transactionRepository.findByStatus(TransactionStatus.PENDING))
            .thenReturn(Flux.just(pendingTx));

        webTestClient.get()
            .uri("/api/transactions/status/{status}", "PENDING")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].status").isEqualTo("PENDING");
    }

    @Test
    @DisplayName("DELETE /api/transactions/{id} - Eliminar transacción")
    void deleteTransaction_ReturnsNoContent() {
        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.deleteById(transactionId))
            .thenReturn(Mono.empty());

        webTestClient.delete()
            .uri("/api/transactions/{id}", transactionId)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("GET /api/transactions - Listar todas las transacciones")
    void getAllTransactions_ReturnsTransactionList() {
        Transaction tx1 = createTestTransaction();
        Transaction tx2 = new Transaction(
            UUID.randomUUID(),
            "ACC-003",
            new BigDecimal("3000.00"),
            "GBP",
            "Another transaction",
            LocalDateTime.now(),
            TransactionStatus.COMPLETED,
            "TXN-003"
        );

        when(transactionRepository.findByAccountId(anyString()))
            .thenReturn(Flux.empty());

        webTestClient.get()
            .uri("/api/transactions")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("POST /api/transactions - Error interno del servidor")
    void createTransaction_WhenRepositoryFails_ReturnsInternalServerError() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeException("Database error")));

        webTestClient.post()
            .uri("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(generateValidTransactionJson())
            .exchange()
            .expectStatus().is5xxServerError();
    }
}