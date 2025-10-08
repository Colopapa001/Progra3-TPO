# Knight's Tour - Problema del Caballo

Solución implementada en Java para el clásico problema del Knight's Tour usando el algoritmo de backtracking.

## Descripción del Problema

El Knight's Tour es un problema clásico de ajedrez donde un caballo debe moverse sobre un tablero de ajedrez (`n` x `n`) visitando cada casilla exactamente una vez.

## Características de la Solución

- ✅ **Algoritmo Backtracking**: Solo usa recursión sin librerías externas
- ✅ **Optimizaciones**: Validación temprana y poda de ramas
- ✅ **Flexibilidad**: Permite encontrar una solución o todas las soluciones
- ✅ **Validaciones**: Control de errores y números heurísticos
- ✅ **Documentación**: Código completamente comentado y ejemplos

## Uso

### Compilación
```bash
javac KnightsTour.java
```

### Ejecución
```bash
# Ejecutar ejemplos por defecto
java KnightsTour

# Ejecutar con tamaño de tablero personalizado  
java KnightsTour 4
```

## Ejemplo de Salida

Para un tablero 3x3 desde posición (0,0):

```
Tablero del Caballo:
===================
+--+--+--+
| 0| 7| 4|
+--+--+--+
| 5| 2| 1|
+--+--+--+
| 8| 3| 6|
+--+--+--+
```

## Estructura del Código

### Métodos Principales
- `findSingleSolution()`: Encuentra la primera solución válida
- `findAllSolutions()`: Encuentra todas las soluciones posibles
- `displayBoard()`: Imprime el tablero de forma atractiva

### Optimizaciones Implementadas
1. **Constantes para movimientos**: Array estático para los 8 movimientos del caballo
2. **Validación temprana**: Verifica límites antes de evaluaciones costosas
3. **Backtracking eficiente**: Solo deshace movimientos cuando es necesario
4. **Limitación de output**: Para tableros grandes previene spam en consola

## Files del Proyecto

- `KnightsTour.java`: Implementación principal con todos los algoritmos
- `README.md`: Documentación y guía de uso

## Explicación del Algoritmo

1. **Inicialización**: Crea un tablero con casillas marcadas como no visitadas (-1)
2. **Movimiento**: Intenta los 8 movimientos posibles del caballo en forma de L
3. **Validación**: Verifica límites y casillas no visitadas antes de proceder
4. **Recursión**: Llama recursivamente con la nueva posición y contador incrementado  
5. **Backtracking**: Si no encuentra solución, deshace el último movimiento
6. **Terminación**: Se detiene cuando todas las casillas han sido visitadas

Esta estrategia asegura explorar todas las soluciones posibles del polinomio del Knight's Tour.
