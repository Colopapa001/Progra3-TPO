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
     * 
     * ALGORITMO:
     * 1. Inicia desde la posición inicial
     * 2. En cada paso, aplica la Regla de Warnsdorff para elegir el próximo movimiento
     * 3. Continúa hasta completar el tour o hasta que no haya movimientos válidos
     * 
     * COMPLEJIDAD: O(n²) donde n = boardSize
     * - Cada casilla se visita exactamente una vez
     * - Para cada casilla, se evalúan 8 movimientos (O(1))
     * - Total: O(n² × 1) = O(n²)
     * 
     * @param startRow fila inicial (índice 0)
     * @param startCol columna inicial (índice 0)
     * @return true si se encontró una solución completa, false en caso contrario
     */
    public boolean solveWithWarnsdorff(int startRow, int startCol) {
        // Validar coordenadas de entrada
        if (!isValidPosition(startRow, startCol)) {
            System.out.println("Posición inicial inválida.");
            return false;
        }
        
        // Reinicializar el tablero para una búsqueda limpia
        initializeBoard();
        
        // Marcar la posición inicial como el primer movimiento (movimiento 0)
        int currentRow = startRow;
        int currentCol = startCol;
        board[currentRow][currentCol] = 0;
        
        // Aplicar Regla de Warnsdorff para cada movimiento restante
        // Total de movimientos: n² - 1 (ya hicimos el movimiento inicial)
        for (int moveNumber = 1; moveNumber < boardSize * boardSize; moveNumber++) {
            // Encontrar el mejor próximo movimiento usando la heurística
            int[] nextMove = findBestNextMove(currentRow, currentCol);
            
            // Si no se encuentra movimiento válido, el tour falló
            if (nextMove == null) {
                return false;
            }
            
            // Realizar el movimiento elegido y marcarlo en el tablero
            currentRow = nextMove[0];
            currentCol = nextMove[1];
            board[currentRow][currentCol] = moveNumber;
        }
        
        // Tour completado exitosamente
        return true;
    }
    
    /**
     * Encuentra el mejor próximo movimiento usando la Regla de Warnsdorff
     * 
     * REGLA DE WARNSDORFF: "Mueve a la casilla que tenga MENOR número de salidas futuras"
     * 
     * ESTRATEGIA DE IMPLEMENTACIÓN:
     * 1. Evalúa todos los movimientos válidos desde la posición actual
     * 2. Para cada movimiento válido, cuenta cuántos movimientos futuros tendría
     * 3. Prioriza movimientos con menos opciones futuras (más restrictivos primero)
     * 4. Solo acepta movimientos con 0 futuras opciones si no hay alternativas
     *    (esto evita callejones sin salida prematuros)
     * 
     * COMPLEJIDAD: O(8 * 8) = O(64) = O(1) por movimiento (constante)
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @return array con [fila, columna] del mejor movimiento, o null si no hay movimientos válidos
     */
    private int[] findBestNextMove(int currentRow, int currentCol) {
        int[] bestMove = null;
        int minFutureMoves = Integer.MAX_VALUE;
        boolean foundZeroMove = false;
        int[] zeroMove = null;
        
        // Evaluar todos los 8 posibles movimientos del caballo
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar que el movimiento sea válido (dentro del tablero y no visitado)
            if (isValidMove(nextRow, nextCol)) {
                // Contar cuántos movimientos futuros válidos tendría desde esa posición
                int futureMoves = countFutureMoves(nextRow, nextCol);
                
                // CASO ESPECIAL: Movimiento con 0 opciones futuras
                // Solo lo consideramos si NO hay otras opciones (evita callejones sin salida)
                if (futureMoves == 0) {
                    foundZeroMove = true;
                    zeroMove = new int[]{nextRow, nextCol};
                    // Continuamos evaluando para ver si hay opciones con movimientos futuros
                    continue;
                }
                
                // REGLA DE WARNSDORFF: Elegir movimiento con MENOR número de opciones futuras
                if (futureMoves < minFutureMoves) {
                    minFutureMoves = futureMoves;
                    bestMove = new int[]{nextRow, nextCol};
                }
            }
        }
        
        // Prioridad 1: Si hay movimientos con opciones futuras, elegir el mejor
        if (bestMove != null) {
            return bestMove;
        }
        
        // Prioridad 2: Si solo hay movimientos con 0 opciones futuras, usar el único disponible
        // (esto ocurre al final del tour cuando solo queda una casilla por visitar)
        if (foundZeroMove) {
            return zeroMove;
        }
        
        // No hay movimientos válidos (caso de error o tour imposible)
        return null;
    }
    
    /**
     * Cuenta cuántos movimientos válidos hay desde una posición dada
     * 
     * Esta es la función clave de la heurística de Warnsdorff.
     * Cuenta cuántas casillas no visitadas son accesibles desde la posición dada.
     * 
     * COMPLEJIDAD: O(8) = O(1) - constante (siempre evalúa 8 movimientos)
     * 
     * @param row fila de la posición a evaluar
     * @param col columna de la posición a evaluar  
     * @return número de movimientos futuros válidos desde esa posición (0-8)
     */
    private int countFutureMoves(int row, int col) {
        int count = 0;
        
        // Evaluar cada uno de los 8 posibles movimientos del caballo
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];
            
            // Si el movimiento es válido (dentro del tablero y no visitado), contar
            if (isValidMove(nextRow, nextCol)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Verifica si un movimiento es válido (dentro del tablero y no visitado)
     * 
     * @param row fila de la posición
     * @param col columna de la posición
     * @return true si la posición está dentro del tablero y no ha sido visitada
     */
    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < boardSize && 
                col >= 0 && col < boardSize && 
                board[row][col] == UNVISITED);
    }
    
    /**
     * Valida que las coordenadas estén dentro de los límites del tablero
     * (sin verificar si está visitada)
     * 
     * @param row fila de la posición
     * @param col columna de la posición
     * @return true si las coordenadas están dentro de los límites del tablero
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    /**
     * Muestra el tablero actual del caballo de forma visualmente atractiva
     */
    public void displayBoard() {
        displayBoard(this.board);
    }
    
    /**
     * Muestra un tablero dado de forma visualmente atractiva
     * 
     * @param board el tablero a mostrar (puede ser el tablero interno o una copia)
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
    
}
