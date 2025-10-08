# Explicación Detallada del Algoritmo Knight's Tour

## ¿Qué es el Knight's Tour?

El Knight's Tour consiste en encontrar un recorrido completo de un caballo por un tablero de ajedrez de `n` x `n` tal que:
1. Todo el tablero se visite
2. Cada casilla se visite exactamente una vez
3. El recorrido termine en la última casilla

## Algoritmo Backtracking Explicado

### Paso 1: Inicialización
```java
// El tablero se marca con "-1" en todas las posiciones
// Para indicar que ninguna casilla ha sido visitada
for (int i = 0; i < boardSize; i++) {
    for (int j = 0; j < boardSize; j++) {
        board[i][j] = UNVISITED;  // -1 significa no visitado
    }
}
```

### Paso 2: Movimientos del Caballo
Un caballo se mueve en forma de L en ocho direcciones:
- Derecha 2, Abajo 1       →  (2,1)
- Derecha 1, Abajo 2       →  (1,2)
- Izquierda 1, Abajo 2     →  (-1,2)
- Izquierda 2, Abajo 1     →  (-2,1)
- ... (y así para los 8 movimientos)

### Paso 3: Función de Backtracking Principal

```java
solveRecursive(currentRow, currentCol, moveNumber) {
    // CASO BASE: ¿Hemos completado el recorrido?
    if (moveNumber == boardSize × boardSize) {
        return true;  // ¡Succes! Tour completado.
    }
    
    // Para cada movimiento posible del caballo
    for (int[] move: KNGHT_MOVES) {
        nextRow = currentRow + move[0]
        nextCol = currentCol + move[1]
        
        // ¿El nuevo movimiento es válido?
        if (positionValid && positionUnvisited) {
            // HACER MOVIMIENTO
            board[nextRow][nextCol] = moveNumber;
            
            // EXPLORAR RECURSIVAMENTE la nueva posición
            if (solveRecursive(nextRow, nextCol, moveNumber+1)) {
                return true;  // ¡Solución encontrada!
            }
            
            // BACKTRACK: Deshacer movimiento si no lleva a solución
            board[nextRow][nextCol] = UNVISITED;
        }
    }
    return false;  // No hubo continuación válida
}
```

### Paso 4: Estrategias de Optimización

#### 1. **Validación Temprana**
```java
// Solo evalúo posiciones válidas antes de operaciones costosas
if (row >= 0 && row < boardSize && 
    col >= 0 && col < boardSize && 
    board[row][col] == UNVISITED) {
    // Proceso pesado solo aquí dentro
}
```

#### 2. **Backtracking Inteligente**
```java
// Si encuentro una solución, bajo inmediatamente la pila recursiva
if (solveRecursive(...)) {
    return true;  // ¡STOP! No sigas explorando
}
```

#### 3. **Estado Immutable**
```java
// Al volver de la recursión, dejo la casilla limpia
board[nextRow][nextCol] = UNVISITED;  
```

## Casos de Uso Comunes

### Para tablero 3x3:
- Total de casillas: 9
- Complejidad reducida para testing
- Resuelve rápido para demostraciones

### Para tablero 4x4:
- Total de casillas: 16
- Muchas soluciones distintas (1000+)
- Excelente para analizar la función findAllSolutions()

### Para tablero 5x5:
- Total de casillas: 25
- Más medible sus tiempos de exploración
- Límite práctico antes del crecimiento exponencial del algoritmo

## Ejemplo de Salida

Tablero 4x4 con recorrido:
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

En este ejemplo:
- La casilla (0,0) es el primer movimiento
- La casilla (0,1) es el undécimo movimiento  
- No reinicia posiciones una vez pisadas
- Los números van del 0 hasta (n²-1)
- El último movimiento es la casilla con índice mayor

## Ventajas del Código Optimizado

1. **Eficiencia**: Solo explora ramas válidas del árbol
2. **Flexibilidad**: Permite buscar una solución *o* todas las soluciones
3. **Mantenibilidad**: El código es legible y extensible
4. **Error Oriented**: Maneja posibles casos de error del usuario
5. **Modular**: Separación clara entre validación, solución y visualización

## Conclusión

El algoritmo Knight's Tour es un excelente ejemplo de:
- **Recursión en programación**
- **Técnica de backtracking**  
- **Optimización en algoritmos de diseño** 
- **Visualización exitosa de un proceso abstracto**

Este código en Java proporciona una implementación clara, eficiente y educativa de este clásico algoritmo.
