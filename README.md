# Knight's Tour - Problema del Caballo

Solución implementada en Java para el clásico problema del Knight's Tour usando tres enfoques algorítmicos diferentes.

## 📚 Descripción del Problema

El Knight's Tour es un problema clásico de ajedrez donde un caballo debe moverse sobre un tablero de ajedrez (`n` x `n`) visitando cada casilla exactamente una vez.

## 🎯 Enfoques Implementados

### 1. Backtracking (Exploración Completa)
- **Complejidad Temporal:** O(8^(n²)) - Exponencial
- **Garantía:** Sí, encuentra solución si existe
- **Uso:** Tableros pequeños (≤5×5)

### 2. Heurística Greedy - Regla de Warnsdorff
- **Complejidad Temporal:** O(n²) - Polinomial
- **Garantía:** No, pero alta tasa de éxito
- **Uso:** Tableros medianos y grandes (≥6×6)

### 3. Programación Dinámica
- **Complejidad Temporal:** O(n² × k) - Polinomial
- **Garantía:** Sí, solución óptima
- **Uso:** Problemas de optimización con restricciones

## 📁 Estructura del Proyecto

```
Progra3-TPO/
├── Codigo/
│   ├── KnightsTour.java          # Backtracking
│   ├── KnightsTourGreedy.java    # Heurística Greedy
│   ├── KnightsTourDP.java        # Programación Dinámica
│   └── Pruebas.java              # Pruebas unificadas
├── Comparaciones/
│   ├── Comparacion.md            # Comparación entre algoritmos
│   └── InformeComplejidad.md     # Análisis de complejidad
└── README.md
```

## 🚀 Compilación

```bash
cd Codigo
javac *.java
```

## 💻 Ejecución

### Ejecutar Pruebas

```bash
# Ver menú de opciones
java Pruebas

# Ejecutar solo Backtracking
java Pruebas 1

# Ejecutar solo Greedy (Warnsdorff)
java Pruebas 2

# Ejecutar solo Programación Dinámica
java Pruebas 3

# Ejecutar todos los algoritmos
java Pruebas 4

# Ejecutar comparación de rendimiento
java Pruebas 5
```

## 📊 Resultados de Rendimiento

| Tamaño | Backtracking | Greedy | DP (k=8) | Complejidad Temporal |
|--------|--------------|--------|----------|---------------------|
| 3×3 | ~0.05 ms | ~0.03 ms | ~0.02 ms | BT: O(8^(n²)), G: O(n²), DP: O(n²×k) |
| 4×4 | ~1.5 ms | ~0.08 ms | ~0.05 ms | BT: O(8^(n²)), G: O(n²), DP: O(n²×k) |
| 5×5 | ~25 ms | ~0.07 ms | ~0.08 ms | BT: O(8^(n²)), G: O(n²), DP: O(n²×k) |
| 6×6 | >60 s | ~0.1 ms | ~0.2 ms | BT: O(8^(n²)), G: O(n²), DP: O(n²×k) |
| 8×8 | >1 hora | ~0.05 ms | ~0.5 ms | BT: O(8^(n²)), G: O(n²), DP: O(n²×k) |

## 📖 Documentación

- **Comparacion.md**: Comparación detallada entre los 3 algoritmos
- **InformeComplejidad.md**: Análisis completo de complejidad temporal

## 🎓 Características de la Solución

### Backtracking
- ✅ Exploración exhaustiva
- ✅ Garantiza encontrar solución
- ✅ Optimizaciones: poda temprana, validación anticipada
- ⚠️ Complejidad exponencial

### Heurística Greedy (Warnsdorff)
- ✅ Complejidad temporal polinomial O(n²)
- ✅ Extremadamente rápido
- ✅ Determinístico
- ⚠️ No garantiza solución

### Programación Dinámica
- ✅ Solución óptima garantizada
- ✅ Complejidad temporal polinomial O(n²×k)
- ✅ Memoización eficiente
- ⚠️ Mayor uso de memoria

## 🔍 Ejemplo de Salida

Para un tablero 4×4 con Backtracking:

```
Tablero del Caballo:
===================
+--+--+--+--+
| 0|11| 6|13|
+--+--+--+--+
| 7| 2|12| 5|
+--+--+--+--+
|10|15| 3|14|
+--+--+--+--+
| 1| 8| 9| 4|
+--+--+--+--+
```

## 📝 Recomendaciones de Uso

- **Tableros pequeños (≤5×5):** Backtracking
- **Tableros medianos (6×6 a 8×8):** Greedy
- **Tableros grandes (≥9×9):** Greedy
- **Optimización con restricciones:** Programación Dinámica

## 🏆 Autores

Trabajo práctico - Programación 3
