# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/financial/reactive/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/financial/reactive/domain/port/TransactionRepositoryPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `reactor.util.retry`: El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.http`: El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.resources`: El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.retry`: El import reactor.netty.retry.RetryBackoffSpec pertenece a reactor.netty.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `Transaction.getAccountId`: Se invoca `getAccountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.getAccountId`: Se invoca `getAccountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.execute`: Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.accountId`: Se invoca `accountId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`: Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.currency`: Se invoca `currency` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`: Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java` — `TransactionException.getMessage`: Se invoca `getMessage` sobre `TransactionException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.execute`: Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior

### Brecha de conocimiento
Implementa un paradigma de programación distinto al imperativo, como el paradigma reactivo o el paradigma funcional. Domina los cuatro pilares especificados en el manifiesto de sistemas reactivos, favoreciendo mejor rendimiento, una mayor escalabilidad y una mayor resiliencia. Conoce las ventajas, desventajas y operadores básicos en la implementación de este paradigma.

### Misión / candidato
Desarrollador Senior en Backend con experiencia en Java, buscando expandir su dominio a paradigmas no imperativos.

### Reto
- Tema: Adopción de Paradigmas de Programación No Imperativos: Con Enfoque Reactivo y Funcional
- Seniority: senior-l2
- Tipo: theoretical
- Título: Exploración de Paradigmas de Programación No Imperativos en el Contexto de Servicios Financieros
- Tiempo estimado: 2 semanas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Fundamentos de Programación Reactiva y Funcional — objetivo: Comprender los conceptos básicos y diferencias entre programación imperativa, reactivo y funcional. — entregable (NO resolver): Documento que detalla los conceptos, ventajas y desventajas de programación reactivo y funcional.
- Fase 2: Aplicación de Paradigmas en Casos de Uso Financieros — objetivo: Aplicar los conceptos de programación reactivo y funcional a casos de uso específicos en el dominio financiero. — entregable (NO resolver): Descripción detallada de la aplicación de programación reactivo y funcional en un caso de uso financiero, incluyendo edge cases y manejo de errores.
- Fase 3: Evaluación de Trade-offs y Decisiones de Diseño — objetivo: Evaluar los trade-offs y tomar decisiones de diseño informadas al aplicar programación reactivo y funcional en sistemas financieros. — entregable (NO resolver): Documento que evalúa los trade-offs y describe las decisiones de diseño tomadas para aplicar programación reactivo y funcional en sistemas financieros.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.financial</groupId>
    <artifactId>reactive-financial</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>reactive-financial</name>
    <description>Microservicio reactivo para procesamiento de transacciones financieras</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/financial/reactive/ReactiveFinancialApplication.java ===
package com.financial.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.financial.reactive")
public class ReactiveFinancialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveFinancialApplication.class, args);
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  netty:
    connection-timeout: 2s
    idle-timeout: 15s

spring:
  application:
    name: reactive-financial-service
  profiles:
    active: dev
  main:
    web-application-type: reactive

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true
  metrics:
    tags:
      application: ${spring.application.name}

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.web.reactive.function.client.WebClientResponseException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
    instances:
      transactionService:
        baseConfig: default
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 100ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - org.springframework.web.reactive.function.client.WebClientResponseException$InternalServerError
          - java.io.IOException
          - java.util.concurrent.TimeoutException
    instances:
      transactionService:
        baseConfig: default
  bulkhead:
    configs:
      default:
        maxConcurrentCalls: 10
        maxWaitDuration: 10ms
    instances:
      transactionService:
        baseConfig: default
  thread-pool-bulkhead:
    configs:
      default:
        maxThreadPoolSize: 4
        coreThreadPoolSize: 2
        queueCapacity: 2
        keepAliveDuration: 20ms
    instances:
      transactionService:
        baseConfig: default

logging:
  level:
    com.financial.reactive: DEBUG
    org.springframework.web: INFO
    reactor.netty.http.client: DEBUG
    io.github.resilience4j: INFO

// === ARCHIVO: src/main/java/com/financial/reactive/domain/model/Transaction.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/domain/port/TransactionRepositoryPort.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/config/Resilience4jConfig.java ===
package com.financial.reactive.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .writableStackTraceEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(100)
                .maxWaitDuration(Duration.ofMillis(500))
                .build();
        return BulkheadRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java ===
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

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java ===
package com.financial.reactive.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(TransactionException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTransactionException(TransactionException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid argument: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }

    public Mono<ErrorResponse> mapToErrorResponse(Throwable error) {
        if (error instanceof TransactionException txError) {
            return Mono.just(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    txError.getMessage(),
                    LocalDateTime.now()
            ));
        } else if (error instanceof IllegalArgumentException iae) {
            return Mono.just(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid argument: " + iae.getMessage(),
                    LocalDateTime.now()
            ));
        } else {
            return Mono.just(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    LocalDateTime.now()
            ));
        }
    }

    public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
}

// === ARCHIVO: src/main/java/com/financial/reactive/infrastructure/exception/TransactionException.java ===
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

// === ARCHIVO: src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java ===
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
```
