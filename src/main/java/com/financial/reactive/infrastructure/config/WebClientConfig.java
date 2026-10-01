package com.financial.reactive.infrastructure.config;


import com.financial.reactive.infrastructure.exception.TransactionException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.retry.RetryBackoffSpec;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Configuración de WebClient para llamadas HTTP reactivas.
 * Configura connection pooling, timeouts, retry y circuit breaker.
 */
@Configuration
@Slf4j
public class WebClientConfig {

    private static final int MAX_CONNECTIONS = 100;
    private static final int MAX_PENDING_REQUESTS = 100;
    private static final long CONNECT_TIMEOUT_MS = 5000L;
    private static final long RESPONSE_TIMEOUT_MS = 30000L;
    private static final int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_INITIAL_INTERVAL_MS = 1000L;
    private static final double RETRY_MULTIPLIER = 2.0;
    private static final long CIRCUIT_BREAKER_FAILURE_RATE_THRESHOLD = 50;
    private static final int CIRCUIT_BREAKER_MIN_CALLS = 10;
    private static final long CIRCUIT_BREAKER_WAIT_DURATION_MS = 30000L;

    @Bean
    public ConnectionProvider connectionProvider() {
        log.info("Configurando ConnectionProvider con {} conexiones máximas", MAX_CONNECTIONS);
        
        return ConnectionProvider.builder("financial-http-pool")
            .maxConnections(MAX_CONNECTIONS)
            .maxPendingRequests(MAX_PENDING_REQUESTS)
            .connectTimeout(Duration.ofMillis(CONNECT_TIMEOUT_MS))
            .pendingAcquireTimeout(Duration.ofMillis(RESPONSE_TIMEOUT_MS))
            .pendingAcquireMaxCount(MAX_PENDING_REQUESTS)
            .evictInBackground(Duration.ofSeconds(30))
            .build();
    }

    @Bean
    public HttpClient httpClient(ConnectionProvider connectionProvider) {
        log.info("Configurando HttpClient con timeouts");
        
        return HttpClient.create(connectionProvider)
            .responseTimeout(Duration.ofMillis(RESPONSE_TIMEOUT_MS))
            .keepAlive(true)
            .compress(true)
            .doOnConnected(conn -> {
                conn.addHandlerLast(new io.netty.handler.timeout.ReadTimeoutHandler(
                    (int) RESPONSE_TIMEOUT_MS, TimeUnit.MILLISECONDS));
                conn.addHandlerLast(new io.netty.handler.timeout.WriteTimeoutHandler(
                    (int) CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS));
            });
    }

    @Bean
    public WebClient webClient(HttpClient httpClient) {
        log.info("Inicializando WebClient con configuración reactiva");
        
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(MAX_IN_MEMORY_SIZE))
            .build();
        
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(strategies)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .filter(this::loggingFilter)
            .filter(this::errorHandlingFilter)
            .build();
    }

    /**
     * Filtro de logging para registrar requests y responses.
     */
    private reactor.function.Function<org.springframework.web.reactive.function.client.ClientRequest,
            reactor.core.publisher.Mono<org.springframework.web.reactive.function.client.ClientResponse>> 
            loggingFilter(org.springframework.web.reactive.function.client.ClientRequest request,
                          org.springframework.web.reactive.function.client.ExchangeFunction next) {
        log.debug("Enviando request: {} {}", request.method(), request.url());
        
        return next.exchange(request)
            .doOnSuccess(response -> log.debug("Response recibida: {} {}", 
                response.statusCode().value(), request.url()))
            .doOnError(error -> log.error("Error en request: {} {} - {}", 
                request.method(), request.url(), error.getMessage()));
    }

    /**
     * Filtro de manejo de errores que convierte excepciones de WebClient
     * en excepciones tipadas del dominio.
     */
    private reactor.function.Function<org.springframework.web.reactive.function.client.ClientRequest,
            reactor.core.publisher.Mono<org.springframework.web.reactive.function.client.ClientResponse>>
            errorHandlingFilter(org.springframework.web.reactive.function.client.ClientRequest request,
                                org.springframework.web.reactive.function.client.ExchangeFunction next) {
        return next.exchange(request)
            .onErrorResume(WebClientResponseException.NotFound.class, error -> {
                log.warn("Recurso no encontrado: {}", request.url());
                return reactor.core.publisher.Mono.error(
                    new com.financial.reactive.infrastructure.exception.TransactionException(
                        "EXTERNAL_SERVICE_NOT_FOUND",
                        "Servicio externo no encontrado: " + request.url(),
                        com.financial.reactive.infrastructure.exception.TransactionException.ErrorType.EXTERNAL_SERVICE));
            })
            .onErrorResume(WebClientResponseException.ServiceUnavailable.class, error -> {
                log.error("Servicio externo no disponible: {}", request.url());
                return reactor.core.publisher.Mono.error(
                    new com.financial.reactive.infrastructure.exception.TransactionException(
                        "EXTERNAL_SERVICE_UNAVAILABLE",
                        "Servicio externo no disponible",
                        com.financial.reactive.infrastructure.exception.TransactionException.ErrorType.EXTERNAL_SERVICE));
            })
            .onErrorResume(WebClientResponseException.class, error -> {
                log.error("Error de cliente HTTP: {} - {}", error.getStatusCode(), error.getMessage());
                return reactor.core.publisher.Mono.error(
                    new com.financial.reactive.infrastructure.exception.TransactionException(
                        "EXTERNAL_SERVICE_ERROR",
                        "Error en servicio externo: " + error.getMessage(),
                        com.financial.reactive.infrastructure.exception.TransactionException.ErrorType.EXTERNAL_SERVICE));
            })
            .onErrorResume(Exception.class, error -> {
                log.error("Error inesperado en llamada HTTP: {}", error.getMessage());
                return reactor.core.publisher.Mono.error(
                    new com.financial.reactive.infrastructure.exception.TransactionException(
                        "EXTERNAL_CONNECTION_ERROR",
                        "Error de conexión: " + error.getMessage(),
                        com.financial.reactive.infrastructure.exception.TransactionException.ErrorType.CONNECTION));
            });
    }

    @Bean
    public RetryRegistry retryRegistry() {
        log.info("Configurando RetryRegistry");
        
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(MAX_RETRY_ATTEMPTS)
            .waitDuration(Duration.ofMillis(RETRY_INITIAL_INTERVAL_MS))
            .retryExceptions(
                java.io.IOException.class,
                java.net.SocketException.class,
                WebClientResponseException.ServiceUnavailable.class,
                WebClientResponseException.GatewayTimeout.class)
            .ignoreExceptions(
                WebClientResponseException.NotFound.class,
                WebClientResponseException.BadRequest.class)
            .build();
        
        return RetryRegistry.of(config);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        log.info("Configurando CircuitBreakerRegistry");
        
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(CIRCUIT_BREAKER_FAILURE_RATE_THRESHOLD)
            .waitDurationInOpenState(Duration.ofMillis(CIRCUIT_BREAKER_WAIT_DURATION_MS))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(CIRCUIT_BREAKER_MIN_CALLS)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return CircuitBreakerRegistry.of(config);
    }

    /**
     * Configura retry backoff para operaciones reactivas.
     */
    public RetryBackoffSpec configureRetryBackoff() {
        return reactor.netty.retry.RetryBackoffSpec.backoff(
                MAX_RETRY_ATTEMPTS,
                Duration.ofMillis(RETRY_INITIAL_INTERVAL_MS))
            .filter(throwable -> throwable instanceof java.io.IOException ||
                               throwable instanceof java.net.SocketException)
            .doBeforeRetry(signal -> log.warn("Reintentando operación, intento: {}", 
                signal.totalRetries() + 1));
    }
}