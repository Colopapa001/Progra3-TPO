# Resumen Ejecutivo - Análisis de Complejidad Temporal

## Tabla Comparativa de Complejidades

| Algoritmo | Complejidad Temporal | Complejidad Espacial | Garantía | Velocidad | Escalabilidad |
|-----------|---------------------|---------------------|----------|-----------|---------------|
| **Backtracking** | O(8^(n²)) | O(n²) | ✅ Sí | 🐌 Muy lenta | n ≤ 5 |
| **Warnsdorff** | O(n²) | O(n²) | ❌ No | ⚡ Muy rápida | n ≤ 100+ |
| **Programación Dinámica** | O(n² × k) | O(n² × k) | ✅ Sí* | 🚀 Rápida | n ≤ 50+ |

*Para el problema adaptado de maximización de puntuación

## Análisis Detallado por Algoritmo

### 1. Backtracking - O(8^(n²))

**Justificación:**
- Explora todas las combinaciones posibles de movimientos
- En cada posición puede tener hasta 8 movimientos válidos
- Total de posiciones: n²
- Complejidad: 8 × 8 × ... × 8 (n² veces) = 8^(n²)

**Ejemplos de crecimiento:**
- Tablero 3×3: 8^9 ≈ 134 millones de operaciones
- Tablero 4×4: 8^16 ≈ 2.8 × 10^14 operaciones
- Tablero 5×5: 8^25 ≈ 3.8 × 10^22 operaciones

### 2. Warnsdorff (Greedy) - O(n²)

**Justificación:**
- Realiza exactamente n² movimientos (uno por casilla)
- En cada movimiento evalúa hasta 8 posiciones vecinas
- Para cada posición vecina cuenta movimientos futuros (hasta 8)
- Total: n² × 8 × 8 = O(n²)

**Ejemplos de crecimiento:**
- Tablero 3×3: 9 operaciones
- Tablero 4×4: 16 operaciones
- Tablero 5×5: 25 operaciones
- Tablero 8×8: 64 operaciones

### 3. Programación Dinámica - O(n² × k)

**Justificación:**
- Estados posibles: n² posiciones × k movimientos
- Para cada estado evalúa 8 movimientos del caballo
- Con memoización, cada estado se calcula una sola vez
- Total: n² × k × 8 = O(n² × k)

**Ejemplos (con k=8):**
- Tablero 3×3: 9 × 8 = 72 operaciones
- Tablero 4×4: 16 × 8 = 128 operaciones
- Tablero 5×5: 25 × 8 = 200 operaciones

## Factores de Mejora

| Comparación | Factor de Mejora |
|-------------|------------------|
| Warnsdorff vs Backtracking | 8^(n²) / n² ≈ 8^(n²-1) |
| DP vs Backtracking (k=8) | 8^(n²) / (n² × 8) ≈ 8^(n²-1) / 8 |
| DP vs Naive Recursive | 8^k / (n² × k) |

### Ejemplos de Speedup:

**Tablero 4×4:**
- Warnsdorff vs Backtracking: 2.8×10^14 / 16 ≈ 1.75×10^13x más rápido
- DP vs Backtracking: 2.8×10^14 / 128 ≈ 2.2×10^12x más rápido

**Tablero 5×5:**
- Warnsdorff vs Backtracking: 3.8×10^22 / 25 ≈ 1.5×10^21x más rápido
- DP vs Backtracking: 3.8×10^22 / 200 ≈ 1.9×10^20x más rápido

## Recomendaciones de Uso

### Cuándo usar Backtracking:
- ✅ Tableros pequeños (≤5×5)
- ✅ Cuando se requiere garantía absoluta de solución
- ✅ Análisis exhaustivo o investigación
- ❌ Tableros grandes o aplicaciones en tiempo real

### Cuándo usar Warnsdorff:
- ✅ Tableros grandes (≥6×6)
- ✅ Aplicaciones en tiempo real
- ✅ Juegos o simulaciones
- ❌ Cuando se requiere garantía de solución

### Cuándo usar Programación Dinámica:
- ✅ Problemas de optimización con restricciones
- ✅ Maximización de puntuación en k movimientos
- ✅ Tableros medianos con restricciones de tiempo
- ❌ Cuando k es muy grande (k > n²/2)

## Conclusiones

1. **Warnsdorff** es el algoritmo más eficiente para el Knight's Tour clásico
2. **Backtracking** es necesario cuando se requiere garantía de solución
3. **Programación Dinámica** es óptima para problemas de optimización adaptados
4. La diferencia de rendimiento es **exponencial** entre algoritmos
5. La elección del algoritmo debe basarse en el contexto específico del problema

## Impacto Práctico

- **Backtracking**: Impracticable para n > 5
- **Warnsdorff**: Practicable hasta n = 100+
- **DP**: Practicable hasta n = 50+ (dependiendo de k)

La implementación de algoritmos más eficientes puede resultar en mejoras de rendimiento de **millones a billones de veces** en tableros grandes.
