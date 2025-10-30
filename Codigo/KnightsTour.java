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
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    /**
     * Función recursiva optimizada de backtracking para encontrar una sola solución
     * Este método usa una estrategia de poda temprana cuando encuentra una solución
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
        
        // Evaluar cada uno de los 8 posibles movimientos del caballo
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar si este movimiento es válido
            if (isValidMove(nextRow, nextCol)) {
                // Hacer el movimiento: marcar casilla con número de paso
                board[nextRow][nextCol] = moveNumber;
                
                // Explorar recursivamente desde la nueva posición
                if (solveRecursiveSingle(nextRow, nextCol, moveNumber + 1)) {
                    return true; // ¡Solución completa encontrada!
                }
                
                // Backtracking: deshacer el movimiento si no lleva a solución
                board[nextRow][nextCol] = UNVISITED;
            }
        }
        
        // Ningún movimiento lleva a una solución desde esta posición
        return false;
    }
    
    /**
     * Encuentra todas las soluciones posibles del problema del Caballo
     * IMPORTANTE: Para tableros grandes esto puede tomar mucho tiempo
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
        
        // Crear una copia temporal del tablero para no modificar el tablero principal
        int[][] tempBoard = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                tempBoard[i][j] = UNVISITED;
            }
        }
        
        // Marcar la posición inicial como primer movimiento
        tempBoard[startingRow][startingCol] = 0;
        
        // Buscar todas las soluciones con backtracking
        solveRecursiveAll(tempBoard, startingRow, startingCol, 1);
        
        return solutionCount;
    }
    
    /**
     * Función recursiva de backtracking especializada en encontrar TODAS las soluciones
     * Este método no para en la primera solución sino que explora todo el árbol de posibilidades
     * @param board tablero de trabajo (copia para no afectar resultados previos)
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @param moveNumber número de movimiento actual
     */
    private void solveRecursiveAll(int[][] board, int currentRow, int currentCol, int moveNumber) {
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
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar si podemos hacer este movimiento
            if (nextRow >= 0 && nextRow < boardSize && 
                nextCol >= 0 && nextCol < boardSize && 
                board[nextRow][nextCol] == UNVISITED) {
                
                // Hacer el movimiento
                board[nextRow][nextCol] = moveNumber;
                
                // Explorar recursivamente esta rama
                solveRecursiveAll(board, nextRow, nextCol, moveNumber + 1);
                
                // Backtracking: deshacer movimiento para explorar otras opciones
                board[nextRow][nextCol] = UNVISITED;
            }
        }
    }
    
}