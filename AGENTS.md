# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Exploración de Paradigmas de Programación No Imperativos en el Contexto de Servicios Financieros**.

| | |
|---|---|
| Tema | Adopción de Paradigmas de Programación No Imperativos: Con Enfoque Reactivo y Funcional |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring WebFlux 3.5.6 |
| Patron arquitectonico | microservicio reactivo con arquitectura hexagonal |
| Tiempo estimado | 2 semanas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.5.6
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- io.github.resilience4j:resilience4j-reactor 2.2.0
- org.projectlombok:lombok n/a
- org.springframework.boot:spring-boot-starter-test n/a
- io.projectreactor:reactor-test n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Fundamentos de Programación Reactiva y Funcional**: Documento que detalla los conceptos, ventajas y desventajas de programación reactivo y funcional.
- **Fase 2 — Aplicación de Paradigmas en Casos de Uso Financieros**: Descripción detallada de la aplicación de programación reactivo y funcional en un caso de uso financiero, incluyendo edge cases y manejo de errores.
- **Fase 3 — Evaluación de Trade-offs y Decisiones de Diseño**: Documento que evalúa los trade-offs y describe las decisiones de diseño tomadas para aplicar programación reactivo y funcional en sistemas financieros.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/financial/reactive/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (22)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/financial/reactive/domain/port/TransactionRepositoryPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `reactor.util.retry`
      El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.http`
      El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.resources`
      El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java` — `reactor.netty.retry`
      El import reactor.netty.retry.RetryBackoffSpec pertenece a reactor.netty.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java` — `Transaction.getAccountId`
      Se invoca `getAccountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.getAccountId`
      Se invoca `getAccountId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `ProcessTransactionUseCase.execute`
      Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.accountId`
      Se invoca `accountId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.amount`
      Se invoca `amount` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.currency`
      Se invoca `currency` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java` — `TransactionRequest.description`
      Se invoca `description` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java` — `TransactionException.getMessage`
      Se invoca `getMessage` sobre `TransactionException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java` — `ProcessTransactionUseCase.execute`
      Se invoca `execute` sobre `ProcessTransactionUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (14)

- `pom.xml`
- `src/main/java/com/financial/reactive/ReactiveFinancialApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/financial/reactive/domain/model/Transaction.java`
- `src/main/java/com/financial/reactive/domain/port/TransactionRepositoryPort.java`
- `src/main/java/com/financial/reactive/application/usecase/ProcessTransactionUseCase.java`
- `src/main/java/com/financial/reactive/infrastructure/adapter/TransactionRepositoryAdapter.java`
- `src/main/java/com/financial/reactive/infrastructure/config/WebClientConfig.java`
- `src/main/java/com/financial/reactive/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/financial/reactive/infrastructure/controller/TransactionController.java`
- `src/main/java/com/financial/reactive/infrastructure/exception/GlobalErrorHandler.java`
- `src/main/java/com/financial/reactive/infrastructure/exception/TransactionException.java`
- `src/test/java/com/financial/reactive/application/usecase/ProcessTransactionUseCaseTest.java`
- `src/test/java/com/financial/reactive/infrastructure/controller/TransactionControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/financial/reactive`
- `src/main/java/com/financial/reactive/domain`
- `src/main/java/com/financial/reactive/domain/model`
- `src/main/java/com/financial/reactive/domain/port`
- `src/main/java/com/financial/reactive/application`
- `src/main/java/com/financial/reactive/application/usecase`
- `src/main/java/com/financial/reactive/infrastructure`
- `src/main/java/com/financial/reactive/infrastructure/adapter`
- `src/main/java/com/financial/reactive/infrastructure/config`
- `src/main/java/com/financial/reactive/infrastructure/controller`
- `src/main/java/com/financial/reactive/infrastructure/exception`
- `src/main/resources`
- `src/test/java/com/financial/reactive`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **microservicio reactivo con arquitectura hexagonal**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior
- Brecha que el reto ataca: Implementa un paradigma de programación distinto al imperativo, como el paradigma reactivo o el paradigma funcional. Domina los cuatro pilares especificados en el manifiesto de sistemas reactivos, favoreciendo mejor rendimiento, una mayor escalabilidad y una mayor resiliencia. Conoce las ventajas, desventajas y operadores básicos en la implementación de este paradigma.
- Mision: Desarrollador Senior en Backend con experiencia en Java, buscando expandir su dominio a paradigmas no imperativos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
