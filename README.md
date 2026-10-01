# Exploración de Paradigmas de Programación No Imperativos en el Contexto de Servicios Financieros

Como desarrollador senior en un equipo de backend para servicios financieros, necesitas implementar soluciones que no solo sean eficientes y escalables, sino también resilientes ante fallos. Tu tarea es explorar y comprender los paradigmas de programación reactivo y funcional, identificando sus ventajas, desventajas y operadores básicos. El objetivo es aplicar estos conceptos para mejorar la arquitectura de servicios existentes, favoreciendo un mejor rendimiento, mayor escalabilidad y resiliencia. Los servicios financieros operan con altos volúmenes de transacciones (hasta 10,000 tx/s) y requieren una disponibilidad del 99.99%.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Adopción de Paradigmas de Programación No Imperativos: Con Enfoque Reactivo y Funcional |
| **Nivel** | senior-l2 |
| **Tipo** | theoretical |
| **Tiempo estimado** | 2 semanas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Fundamentos de Programación Reactiva y Funcional

**Objetivo:** Comprender los conceptos básicos y diferencias entre programación imperativa, reactivo y funcional.

**Tiempo estimado:** 3 días

**Instrucciones:**

- Investiga y documenta los cuatro pilares de sistemas reactivos: respuesta, elasticidad, resiliencia y mensajería.
- Identifica y describe las ventajas y desventajas de utilizar programación funcional en comparación con programación imperativa.

**Entregable:** Documento que detalla los conceptos, ventajas y desventajas de programación reactivo y funcional.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo la programación funcional puede mejorar la consistencia y previsibilidad en sistemas de alta concurrencia.
- Reflexiona sobre cómo la programación reactivo puede manejar la backpressure y la elasticidad en sistemas de alta carga.

</details>

### Fase 2: Aplicación de Paradigmas en Casos de Uso Financieros

**Objetivo:** Aplicar los conceptos de programación reactivo y funcional a casos de uso específicos en el dominio financiero.

**Tiempo estimado:** 4 días

**Instrucciones:**

- Selecciona un caso de uso financiero (por ejemplo, procesamiento de transacciones) y describe cómo aplicarías programación reactivo y funcional para mejorar su rendimiento y resiliencia.
- Identifica posibles edge cases y cómo estos paradigmas pueden manejarlos de manera efectiva.

**Entregable:** Descripción detallada de la aplicación de programación reactivo y funcional en un caso de uso financiero, incluyendo edge cases y manejo de errores.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo la programación reactivo puede manejar la backpressure en un sistema de procesamiento de transacciones.
- Reflexiona sobre cómo la programación funcional puede mejorar la consistencia y previsibilidad en el manejo de edge cases.

</details>

### Fase 3: Evaluación de Trade-offs y Decisiones de Diseño

**Objetivo:** Evaluar los trade-offs y tomar decisiones de diseño informadas al aplicar programación reactivo y funcional en sistemas financieros.

**Tiempo estimado:** 3 días

**Instrucciones:**

- Evalúa los trade-offs entre programación imperativa, reactivo y funcional en el contexto de sistemas financieros.
- Toma decisiones de diseño informadas sobre cuándo y cómo aplicar cada paradigma para maximizar rendimiento, escalabilidad y resiliencia.

**Entregable:** Documento que evalúa los trade-offs y describe las decisiones de diseño tomadas para aplicar programación reactivo y funcional en sistemas financieros.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los costos y beneficios de introducir programación funcional en un sistema imperativo existente.
- Reflexiona sobre cómo la programación reactivo puede mejorar la resiliencia y escalabilidad, pero también puede introducir complejidad.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son los paradigmas de programación reactivo y funcional y cómo se diferencian del paradigma imperativo?
- **paraQueSirve**: ¿En qué escenarios es beneficioso aplicar programación reactivo y funcional en sistemas financieros?
- **comoSeUsa**: ¿Cómo puedes aplicar programación reactivo y funcional a un caso de uso específico en el dominio financiero?
- **erroresComunes**: ¿Cuáles son los errores comunes al aplicar programación reactivo y funcional y cómo puedes evitarlos?
- **queDecisionesImplica**: ¿Qué decisiones de diseño debes tomar al aplicar programación reactivo y funcional en sistemas financieros?

## Criterios de Evaluacion

- Comprensión clara de los paradigmas de programación reactivo y funcional.
- Aplicación efectiva de estos paradigmas en casos de uso financieros.
- Evaluación de trade-offs y toma de decisiones de diseño informadas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
