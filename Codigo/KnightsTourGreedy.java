/**
 * Implementación del Knight's Tour usando la Regla de Warnsdorff (Heurística Greedy)
 * 
 * Regla de Warnsdorff: En cada movimiento, selecciona la posición vecina 
 * que tenga el menor número de movimientos futuros disponibles.
 * 
 * Esta heurística es extremadamente eficiente para Knight's Tour compara-
 * do con backtracking puro.
 */
public class KnightsTourGreedy {
    
    // Constante para casilla no visitada
    private static final int UNVISITED = -1;
    
    // Tamaño del tablero
    private final int boardSize;
    
    // Tablero matriz n x n
    private int[][] board;
    
    // Todos los posibles movimientos del caballo (8 direcciones)
    private static final int[][] KNIGHT_MOVES = {
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1},    // Cuadrantes I y II
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1}  // Cuadrantes III y IV
    };
    
    /**
     * Constructor para inicializar el solucionador Greedy
     * @param boardSize tamaño del tablero (boardSize x boardSize)
     * @throws IllegalArgumentException si boardSize es menor que 3
     */
    public KnightsTourGreedy(int boardSize) {
        if (boardSize < 3) {
            throw new IllegalArgumentException("El tablero debe ser de al menos 3x3");
        }
        
        this.boardSize = boardSize;
        this.board = new int[boardSize][boardSize];
        
        // Inicializar el tablero
        initializeBoard();
    }
    
    /**
     * Inicializa el tablero marcando todas las casillas como no visitadas
     */
    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = UNVISITED;
            }
        }
    }
    
    /**
     * Método principal que usa la Regla de Warnsdorff para resolver el Knight's Tour
     * @param startRow fila inicial (índice 0)
     * @param startCol columna inicial (índice 0)
     * @return true si se encontró una solución, false en caso contrario
     */
    public boolean solveWithWarnsdorff(int startRow, int startCol) {
        // Validar coordenadas de entrada
        if (!isValidPosition(startRow, startCol)) {
            System.out.println("Posición inicial inválida.");
            return false;
        }
        
        // Reinicializar el tablero
        initializeBoard();
        
        // Iniciar desde la posición dada
        int currentRow = startRow;
        int currentCol = startCol;
        board[currentRow][currentCol] = 0; // Movimiento inicial
        
        // Aplicar Regla de Warnsdorff paso a paso
        for (int moveNumber = 1; moveNumber < boardSize * boardSize; moveNumber++) {
            int[] nextMove = findBestNextMove(currentRow, currentCol);
            
            // Si no se encuentra movimiento válido, el tour falló
            if (nextMove == null) {
                return false;
            }
            
            // Realizar el movimiento elegido
            int nextRow = nextMove[0];
            int nextCol = nextMove[1];
            board[nextRow][nextCol] = moveNumber;
            
            // Actualizar posición actual
            currentRow = nextRow;
            currentCol = nextCol;
        }
        
        return true; // ¡Tour completado exitosamente!
    }
    
    /**
     * Encuentra el mejor próximo movimiento usando la Regla de Warnsdorff OPTIMIZADA
     * La regla dice: "Mueve a la casilla que tenga MENOR número de salidas futuras"
     * 
     * OPTIMIZACIÓN: Se evaluan solo movimientos válidos y se sale temprano
     * cuando se encuentra el mínimo posible (0 movimientos). 
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @return array con [fila, columna] del mejor movimiento, o null si no hay
     */
    private int[] findBestNextMove(int currentRow, int currentCol) {
        int[] bestMove = null;
        int minFutureMoves = Integer.MAX_VALUE;
        
        // Evaluar TODOS los movimientos posibles desde la posición actual
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar que el movimiento sea válido (dentro del tablero y no visitado)
            if (isValidMove(nextRow, nextCol)) {
                // Contar cuántos movimientos futuros tendría desde esa posición
                int futureMoves = countFutureMoves(nextRow, nextCol);
                
                // OPTIMIZACIÓN: Si 0 futuras acciones, esa es la mejor inmediatamente
                if (futureMoves == 0) {
                    return new int[]{nextRow, nextCol};
                }
                
                // Elegir el movimiento con MENOR número de futuras opciones
                if (futureMoves < minFutureMoves) {
                    minFutureMoves = futureMoves;
                    bestMove = new int[]{nextRow, nextCol};
                }
            }
        }
        
        return bestMove;
    }
    
    /**
     * Cuenta cuántos movimientos válidos tendríamos desde una posición dada
     * Esta es la función clave de la heurística
     * 
     * @param row fila para evaluar
     * @param col columna para evaluar  
     * @return número de movimientos futuros válidos desde esa posición
     */
    private int countFutureMoves(int row, int col) {
        int count = 0;
        
        // Para cada posible movimiento desde esa posición...
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];
            
            // Si el movimiento es válido, cuenta como opción futura
            if (isValidMove(nextRow, nextCol)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Verifica si una posición es válida y no ha sido visitada
     * @param row fila de la posición
     * @param col columna de la posición
     * @return true si la posición es válida y accesible
     */
    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < boardSize && 
                col >= 0 && col < boardSize && 
                board[row][col] == UNVISITED);
    }
    
    /**
     * Valida coordenadas dentro de los límites del tablero
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    /**
     * Muestra el tablero del caballo de forma visualmente atractiva
     * @param board el tablero a mostrar
     */
    public void displayBoard() {
        displayBoard(this.board);
    }
    
    /**
     * Muestra un tablero cualquiera
     */
    public void displayBoard(int[][] board) {
        System.out.println("Tablero del Caballo (Warnsdorff):");
        System.out.println("=================================");
        
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
            
            // Borde inferior
            System.out.print("+");
            for (int j = 0; j < boardSize; j++) {
                System.out.print("--+");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Obtiene una copia segura del tablero actual
     */
    public int[][] getBoardCopy() {
        int[][] copy = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    /**
     * Demostración y testing de la heurística Greedy vs Backtracking
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("     COMPARACIÓN: WARNSDORFF GREEDY vs BACKTRACKING");
        System.out.println("========================================================================");
        
        // Probar tableros de diferentes tamaños
        int[] sizes = {5, 6, 7, 8};
        
        for (int size : sizes) {
            System.out.println("\n🔸 TABLERO " + size + "x" + size);
            System.out.println("=====================================");
            
            // Prueba con varias posiciones iniciales
            int[][] positions = {{0, 0}, {1, 1}, {size/2, size/2}};
            
            for (int[] pos : positions) {
                System.out.println("\n📍 Posición inicial: (" + pos[0] + ", " + pos[1] + ")");
                
                // 1. PRUEBA: Heurística Greedy (Warnsdorff)
                KnightsTourGreedy greedySolver = new KnightsTourGreedy(size);
                long startTime = System.nanoTime();
                
                boolean greedySolution = greedySolver.solveWithWarnsdorff(pos[0], pos[1]);
                long greedyTime = System.nanoTime() - startTime;
                
                System.out.println("✅ Warnsdorff: " + (greedySolution ? "ENCONTRÓ solucion" : "NO encontró solucion") + 
                                 " en " + (greedyTime/1000) + " microsegundos");
                
                if (greedySolution) {
                    greedySolver.displayBoard();
                }
                
                // 2. PRUEBA: Backtracking tradicional
                KnightsTour backtrackSolver = new KnightsTour(size);
                startTime = System.nanoTime();
                
                boolean backtrackSolution = backtrackSolver.findSingleSolution(pos[0], pos[1]);
                long backtrackTime = System.nanoTime() - startTime;
                
                System.out.println("🤖 Backtracking: " + (backtrackSolution ? "ENCONTRÓ solucion" : "NO encontró solucion") + 
                                 " en " + (backtrackTime/1000) + " microsegundos");
                
                // Comparación de rendimiento
                if (greedySolution && backtrackSolution) {
                    double speedup = (double) backtrackTime / greedyTime;
                    System.out.println("🏆 SPEEDUP Warnsdorff vs Backtracking: " + 
                                     String.format("%.1f", speedup) + "x más rápido");
                }
            }
        }
        
        System.out.println("\n========================================================================");
        System.out.println("                      RESUMEN DE ANÁLISIS");
        System.out.println("========================================================================");
        System.out.println("🔍 OBSERVACIONES:");
        System.out.println("   • Warnsdorff suele ser muchísimo más rápido para tableros grandes");
        System.out.println("   • Greedy puede NO encontrar solución en tableros muy pequeños");
        System.out.println("   • Backtracking es más metodico pero explorativo (más lento)");
        System.out.println("   • Warnsdorff tiene complejidad O(n²) vs Backtracking O(8^(n²))");
    }
}
