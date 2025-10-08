# Análisis de Complejidad Temporal - Knight's Tour

## Resumen Ejecutivo

Este documento presenta un análisis detallado de la complejidad temporal de los tres algoritmos implementados para resolver el problema Knight's Tour:

1. **Backtracking** (exploración completa)
2. **Heurística Greedy** (Regla de Warnsdorff)  
3. **Programación Dinámica** (maximización de puntuación)

---

## 1. Algoritmo de Backtracking

### Análisis de Complejidad

**Complejidad Temporal: O(8^(n²))**

#### Justificación:

1. **Estructura del problema**: 
   - Tablero de n×n casillas = n² posiciones totales
   - Cada posición puede tener hasta 8 movimientos válidos del caballo
   - El algoritmo explora todas las combinaciones posibles

2. **Análisis paso a paso**:
   ```
   - Movimiento 1: 8 opciones
   - Movimiento 2: hasta 8 opciones (dependiendo de posición)
   - Movimiento 3: hasta 8 opciones
   - ...
   - Movimiento n²: hasta 8 opciones
   ```

3. **Caso peor**: En el peor escenario, cada posición tiene 8 movimientos válidos
   - Total de combinaciones: 8 × 8 × 8 × ... × 8 (n² veces)
   - Resultado: 8^(n²)

#### Optimizaciones implementadas:
- **Poda temprana**: Se detiene al encontrar la primera solución
- **Validación temprana**: Verifica límites antes de recursión
- **Backtracking eficiente**: Deshace movimientos solo cuando es necesario

#### Complejidad Espacial: O(n²)
- Tablero de n×n: O(n²)
- Pila de recursión: O(n²) en el peor caso

---

## 2. Heurística Greedy (Regla de Warnsdorff)

### Análisis de Complejidad

**Complejidad Temporal: O(n²)**

#### Justificación:

1. **Estructura del algoritmo**:
   - Realiza exactamente n² movimientos (uno por casilla)
   - En cada movimiento, evalúa hasta 8 posiciones vecinas
   - Para cada posición vecina, cuenta movimientos futuros (hasta 8)

2. **Análisis detallado**:
   ```
   Para cada movimiento i (donde i = 1 a n²):
   - Evaluar 8 movimientos posibles: O(8) = O(1)
   - Para cada movimiento válido:
     - Contar movimientos futuros: O(8) = O(1)
   - Seleccionar el mejor: O(1)
   
   Total: n² × O(1) = O(n²)
   ```

3. **Optimizaciones clave**:
   - **Salida temprana**: Si encuentra 0 movimientos futuros, selecciona inmediatamente
   - **Evaluación local**: Solo considera movimientos desde posición actual
   - **Sin recursión**: Algoritmo iterativo puro

#### Complejidad Espacial: O(n²)
- Tablero de n×n: O(n²)
- Variables auxiliares: O(1)

#### Ventajas:
- **Determinístico**: Misma entrada → misma salida
- **Extremadamente rápido**: Complejidad polinomial
- **Bajo uso de memoria**: No requiere pila de recursión

#### Desventajas:
- **No garantiza solución**: Puede fallar en tableros pequeños
- **Una sola solución**: No explora alternativas

---

## 3. Programación Dinámica

### Análisis de Complejidad

**Complejidad Temporal: O(n² × k × 8)**

Donde:
- n = tamaño del tablero (n×n)
- k = número de movimientos permitidos
- 8 = movimientos posibles del caballo

#### Justificación:

1. **Estructura del problema adaptado**:
   - Problema modificado: maximizar puntuación en k movimientos
   - No requiere visitar todas las casillas
   - Usa memoización para evitar recálculos

2. **Análisis de la función DP**:
   ```
   dpMaximizeScore(row, col, remainingMoves):
   - Estados posibles: n² × k
   - Para cada estado:
     - Evalúa 8 movimientos del caballo
     - Cada evaluación: O(1) con memoización
   
   Total: O(n² × k × 8) = O(n² × k)
   ```

3. **Comparación con enfoque naive**:
   - **Sin DP**: O(8^k) - EXPONENCIAL
   - **Con DP**: O(n² × k) - POLINOMIAL
   - **Mejora**: De exponencial a polinomial

#### Complejidad Espacial: O(n² × k)
- Tabla de memoización: O(n² × k)
- Pila de recursión: O(k) en el peor caso

#### Implementaciones:
1. **Memoización (Top-down)**: O(n² × k)
2. **Tabulation (Bottom-up)**: O(n² × k)

---

## 4. Tabla Comparativa de Complejidades

| Algoritmo | Complejidad Temporal | Complejidad Espacial | Garantía de Solución | Velocidad |
|-----------|---------------------|---------------------|---------------------|-----------|
| **Backtracking** | O(8^(n²)) | O(n²) | ✅ Sí | 🐌 Muy lenta |
| **Warnsdorff (Greedy)** | O(n²) | O(n²) | ❌ No | ⚡ Muy rápida |
| **Programación Dinámica** | O(n² × k) | O(n² × k) | ✅ Sí* | 🚀 Rápida |

*Para el problema adaptado de maximización de puntuación

---

## 5. Análisis Comparativo Detallado

### Escalabilidad por Tamaño de Tablero

| Tamaño | Backtracking | Warnsdorff | DP (k=10) |
|--------|-------------|------------|-----------|
| 3×3 | ~8^9 ≈ 134M | 9 | 90 |
| 4×4 | ~8^16 ≈ 2.8×10^14 | 16 | 160 |
| 5×5 | ~8^25 ≈ 3.8×10^22 | 25 | 250 |
| 6×6 | ~8^36 ≈ 2.3×10^32 | 36 | 360 |
| 8×8 | ~8^64 ≈ 1.8×10^57 | 64 | 640 |

### Factores de Mejora

| Comparación | Factor de Mejora |
|-------------|------------------|
| Warnsdorff vs Backtracking | 8^(n²) / n² ≈ 8^(n²-1) |
| DP vs Backtracking (k=10) | 8^(n²) / (n² × 10) ≈ 8^(n²-1) / 10 |
| DP vs Naive Recursive | 8^k / (n² × k) |

---

## 6. Conclusiones y Recomendaciones

### Cuándo usar cada algoritmo:

#### **Backtracking**
- ✅ **Cuándo usar**: Tableros pequeños (≤5×5), cuando se necesita garantía de solución
- ❌ **Cuándo NO usar**: Tableros grandes, aplicaciones en tiempo real
- 🎯 **Caso de uso**: Investigación, análisis exhaustivo, educación

#### **Warnsdorff (Greedy)**
- ✅ **Cuándo usar**: Tableros grandes (≥6×6), aplicaciones en tiempo real
- ❌ **Cuándo NO usar**: Cuando se requiere garantía absoluta de solución
- 🎯 **Caso de uso**: Juegos, simulaciones, prototipado rápido

#### **Programación Dinámica**
- ✅ **Cuándo usar**: Problemas de optimización con restricciones, tableros medianos
- ❌ **Cuándo NO usar**: Cuando k es muy grande (k > n²/2)
- 🎯 **Caso de uso**: Optimización de recursos, problemas con puntuaciones

### Impacto de la Complejidad en la Práctica:

1. **Backtracking**: Impracticable para n > 5
2. **Warnsdorff**: Practicable hasta n = 100+ 
3. **DP**: Practicable hasta n = 50+ (dependiendo de k)

### Recomendación Final:

Para el **Knight's Tour clásico** (visitar todas las casillas):
- **Tableros pequeños (≤5×5)**: Backtracking
- **Tableros grandes (≥6×6)**: Warnsdorff

Para **problemas de optimización** (maximizar puntuación en k movimientos):
- **Programación Dinámica** es la opción óptima

---

*Análisis realizado sobre las implementaciones en Java del proyecto Knight's Tour*
