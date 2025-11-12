/**
 * Solucionador del Problema del Caballo
 * Problema: Un caballo de ajedrez debe recorrer un tablero de n x n 
 * visitando cada casilla exactamente una vez usando backtracking.
 * Este algoritmo encuentra todas las soluciones posibles.
 */
public class KnightsTour {
    
    // Constante para casilla no visitada
    private static final int UNVISITED = -1;
    
    // Tamaño del tablero
    private final int boardSize;
    
    // Tablero matriz n x n
    private int[][] board;
    
    // Método público para obtener una copia del tablero para visualización
    public int[][] getBoardCopy() {
        int[][] copy = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    // Todas las posibles posiciones de movimiento del caballo (8 direcciones)
    // En ajedrez: el caballo se mueve en forma de L (2 + 1 cuadros)
    private static final int[][] KNIGHT_MOVES = {
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1},    // Cuadrantes I y II
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1}  // Cuadrantes III y IV
    };
    
    // Contador de soluciones encontradas
    private int solutionCount;
    
    /**
     * Constructor para inicializar el solucionador
     * @param boardSize tamaño del tablero (boardSize x boardSize)
     * @throws IllegalArgumentException si boardSize es menor que 3
     */
    public KnightsTour(int boardSize) {
        if (boardSize < 3) {
            throw new IllegalArgumentException("El tablero debe ser de al menos 3x3");
        }
        
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
        this.solutionCount = 0;
        
        // Inicializar el tablero marcando todas las casillas como no visitadas
        initializeBoard();
    }
    
    /**
     * Inicializa el tablero con valores de no visitado
     */
    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = UNVISITED;
            }
        }
    }
    
    /**
     * Verifica si una posición es válida dentro del tablero
     * @param row fila de la posición
     * @param col columna de la posición
     * @return true si la posición es válida y no ha sido visitada
     */
    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < boardSize && 
                col >= 0 && col < boardSize && 
                board[row][col] == UNVISITED);
    }
    
    /**
     * Imprime el tablero con el recorrido del caballo de forma atractiva
     * Las casillas muestran el número de movimiento
     * @param board el tablero a imprimir
     */
    public void displayBoard(int[][] board) {
        System.out.println("Tablero del Caballo:");
        System.out.println("===================");
        
        // Borde superior
        System.out.print("+");
        for (int j = 0; j < boardSize; j++) {
            System.out.print("--+");
        }
        System.out.println();
        
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == UNVISITED) {
                    System.out.print("  |");
                } else {
                    System.out.printf("%2d|", board[i][j]);
                }
            }
            System.out.println();
            
            // Borde inferior de cada fila
            System.out.print("+");
            for (int j = 0; j < boardSize; j++) {
                System.out.print("--+");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Encuentra una sola solución al problema del Caballo
     * @param startingRow fila inicial (índice 0)
     * @param startingCol columna inicial (índice 0)
     * @return true si se encontró una solución
     */
    public boolean findSingleSolution(int startingRow, int startingCol) {
        // Validar coordenadas de entrada
        if (!isValidPosition(startingRow, startingCol)) {
            System.out.println("Posición inicial inválida.");
            return false;
        }
        
        // Reinicializar el tablero para búsqueda limpia
        initializeBoard();
        
        // Marcar la posición inicial como movimiento 0
        board[startingRow][startingCol] = 0;
        
        // Intentar resolver desde la posición inicial con recursión
        if (solveRecursiveSingle(startingRow, startingCol, 1)) {
            return true;
        } else {
            System.out.println("No se encontró solución para esta posición inicial.");
            return false;
        }
    }
    
    /**
     * Valida que las coordenadas estén dentro de los límites del tablero
     * (sin verificar si está visitada)
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    /**
     * Función recursiva optimizada de backtracking para encontrar una sola solución
     * OPTIMIZACIONES APLICADAS:
     * 1. Poda temprana: retorna inmediatamente al encontrar solución
     * 2. Ordenamiento de movimientos: prioriza movimientos con menos opciones futuras (heurística Warnsdorff)
     * 3. Validación anticipada: verifica movimientos válidos antes de explorar
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual  
     * @param moveNumber número de movimiento actual
     * @return true si se completó exitosamente el recorrido
     */
    private boolean solveRecursiveSingle(int currentRow, int currentCol, int moveNumber) {
        // Condición terminal: hemos visitado todas las casillas
        if (moveNumber == boardSize * boardSize) {
            return true; // ¡Solución encontrada!
        }
        
        // OPTIMIZACIÓN: Ordenar movimientos por número de opciones futuras (poda heurística)
        // Esto guía la búsqueda hacia áreas más prometedoras primero
        int[][] movesWithAccessibility = getMovesWithAccessibility(currentRow, currentCol);
        
        // Evaluar movimientos ordenados por accesibilidad (menos opciones futuras primero)
        for (int[] moveInfo : movesWithAccessibility) {
            int nextRow = moveInfo[0];
            int nextCol = moveInfo[1];
            
            // Hacer el movimiento: marcar casilla con número de paso
            board[nextRow][nextCol] = moveNumber;
            
            // Explorar recursivamente desde la nueva posición
            if (solveRecursiveSingle(nextRow, nextCol, moveNumber + 1)) {
                return true; // ¡Solución completa encontrada! (PODA TEMPRANA)
            }
            
            // Backtracking: deshacer el movimiento si no lleva a solución
            board[nextRow][nextCol] = UNVISITED;
        }
        
        // Ningún movimiento lleva a una solución desde esta posición
        return false;
    }
    
    /**
     * Obtiene movimientos válidos ordenados por accesibilidad (heurística de poda)
     * Movimientos con menos opciones futuras tienen prioridad
     * Esto implementa una variante de la heurística de Warnsdorff para guiar el backtracking
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @return array de [fila, columna, accesibilidad] ordenado por accesibilidad ascendente
     */
    private int[][] getMovesWithAccessibility(int currentRow, int currentCol) {
        // Array temporal para almacenar movimientos válidos con su accesibilidad
        int[][] validMoves = new int[KNIGHT_MOVES.length][3];
        int validCount = 0;
        
        // Calcular accesibilidad para cada movimiento válido
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            if (isValidMove(nextRow, nextCol)) {
                int accessibility = countFutureMoves(nextRow, nextCol);
                validMoves[validCount][0] = nextRow;
                validMoves[validCount][1] = nextCol;
                validMoves[validCount][2] = accessibility;
                validCount++;
            }
        }
        
        // Ordenar por accesibilidad (menor = mejor, más restrictivo)
        // Usar ordenamiento por inserción simple (eficiente para arrays pequeños)
        for (int i = 1; i < validCount; i++) {
            int[] key = validMoves[i];
            int j = i - 1;
            while (j >= 0 && validMoves[j][2] > key[2]) {
                validMoves[j + 1] = validMoves[j];
                j--;
            }
            validMoves[j + 1] = key;
        }
        
        // Retornar solo los movimientos válidos (sin el campo de accesibilidad)
        int[][] result = new int[validCount][2];
        for (int i = 0; i < validCount; i++) {
            result[i][0] = validMoves[i][0];
            result[i][1] = validMoves[i][1];
        }
        
        return result;
    }
    
    /**
     * Cuenta cuántos movimientos válidos hay desde una posición dada
     * Usado para la heurística de poda (priorizar movimientos más restrictivos)
     * 
     * @param row fila de la posición
     * @param col columna de la posición
     * @return número de movimientos válidos desde esa posición
     */
    private int countFutureMoves(int row, int col) {
        int count = 0;
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];
            if (isValidMove(nextRow, nextCol)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Encuentra todas las soluciones posibles del problema del Caballo
     * IMPORTANTE: Para tableros grandes esto puede tomar mucho tiempo
     * 
     * OPTIMIZACIÓN: Usa el tablero principal y lo reinicializa después
     * para evitar crear copias innecesarias en memoria
     * 
     * @param startingRow fila inicial
     * @param startingCol columna inicial  
     * @return número total de soluciones encontradas
     */
    public int findAllSolutions(int startingRow, int startingCol) {
        // Validar coordenadas de entrada
        if (!isValidPosition(startingRow, startingCol)) {
            System.out.println("Posición inicial inválida.");
            return 0;
        }
        
        solutionCount = 0;
        
        // Reinicializar el tablero para búsqueda limpia
        initializeBoard();
        
        // Marcar la posición inicial como primer movimiento
        board[startingRow][startingCol] = 0;
        
        // Buscar todas las soluciones con backtracking
        solveRecursiveAll(startingRow, startingCol, 1);
        
        // Reinicializar el tablero después de la búsqueda
        initializeBoard();
        
        return solutionCount;
    }
    
    /**
     * Función recursiva de backtracking especializada en encontrar TODAS las soluciones
     * Este método no para en la primera solución sino que explora todo el árbol de posibilidades
     * 
     * OPTIMIZACIÓN: Usa el tablero principal (this.board) en lugar de una copia
     * para reducir uso de memoria. El tablero se reinicializa después de la búsqueda.
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @param moveNumber número de movimiento actual
     */
    private void solveRecursiveAll(int currentRow, int currentCol, int moveNumber) {
        // Condición terminal: hemos completado el recorrido completo
        if (moveNumber == boardSize * boardSize) {
            solutionCount++;
            // Opcional: imprimir cada solución encontrada
            if (solutionCount <= 10) { // Limitar para evitar spam en consola
                System.out.println("Solución #" + solutionCount + ":");
                displayBoard(board);
            } else if (solutionCount == 11) {
                System.out.println("... (limitando visualización a las primeras 10 soluciones)");
            }
            return;
        }
        
        // Explorar cada movimiento posible del caballo
        // NOTA: No ordenamos aquí para encontrar TODAS las soluciones sin sesgo
        // (el ordenamiento podría omitir algunas soluciones equivalentes)
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar si podemos hacer este movimiento (usar método helper para consistencia)
            if (isValidMove(nextRow, nextCol)) {
                // Hacer el movimiento
                board[nextRow][nextCol] = moveNumber;
                
                // Explorar recursivamente esta rama
                solveRecursiveAll(nextRow, nextCol, moveNumber + 1);
                
                // Backtracking: deshacer movimiento para explorar otras opciones
                board[nextRow][nextCol] = UNVISITED;
            }
        }
    }
    
}