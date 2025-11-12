/**
 * Implementación de Knight's Tour usando Programación Dinámica
 * 
 * Adaptación del problema original:
 * - Tablero con casillas con puntajes asignados
 * - Caballo debe maximizar suma de puntos
 * - Exactamente k movimientos
 * - Solución optimizada con DP (memoización + tabulation)
 */
public class KnightsTourDP {
    
    private int[][] scores;
    private int boardSize;
    private int maxMoves;
    
    // Memoización map para caching resultados
    // memo[row][col][moves] = máximo score desde (row,col) con 'moves' movimientos restantes
    private int[][][] memo;
    
    // Todos los posibles movimientos del caballo (8 direcciones)
    private static final int[][] KNIGHT_MOVES = {
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
    };
    
    /**
     * Constructor para inicializar el solver DP
     * @param boardSize tamaño del tablero (boardSize x boardSize)
     * @param k número de movimientos que debe realizar el caballo
     */
    public KnightsTourDP(int boardSize, int k) {
        this.boardSize = boardSize;
        this.maxMoves = k;
        this.scores = new int[boardSize][boardSize];
        this.memo = new int[boardSize][boardSize][k + 1];
        
        // Inicializar matriz de memo con valores no calculados
        initializeMemo();
        initializeBoard();
    }
    
    /**
     * Inicializa el sistema de puntuación del tablero
     * Puede ser configurado manualmente o generado aleatoriamente
     */
    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Genera puntajes aleatorios entre 1-10 para prueba
                scores[i][j] = (int)(Math.random() * 10) + 1;
            }
        }
    }
    
    /**
     * Configuración manual de puntuación para tablero específico
     * @param customScores matriz de puntajes personalizada
     */
    public void setCustomScores(int[][] customScores) {
        for (int i = 0; i < boardSize && i < customScores.length; i++) {
            for (int j = 0; j < boardSize && j < customScores[i].length; j++) {
                scores[i][j] = customScores[i][j];
            }
        }
    }
    
    /**
     * Inicializa la memoización con valores indicadores
     */
    private void initializeMemo() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int m = 0; m <= maxMoves; m++) {
                    memo[i][j][m] = -1; // Indica valor no calculado
                }
            }
        }
    }
    
    /**
     * Función principal que resuelve el problema de maximización
     * usando Programación Dinámica con memoización
     * @param startRow posición inicial fila
     * @param startCol posición inicial columna
     * @return máximo puntaje obtenible en k movimientos
     */
    public int solveMaximizeScore(int startRow, int startCol) {
        // Validar posición inicial
        if (!isValidPosition(startRow, startCol)) {
            System.out.println("Posición inicial inválida.");
            return -1;
        }
        
        System.out.println("Ejecutando Programación Dinámica...");
        System.out.println("Posición inicial: (" + startRow + ", " + startCol + ")");
        System.out.println("Movimientos permitidos: " + maxMoves);
        System.out.println();
        
        // Calcular máximo score usando DP
        int maxScore = dpMaximizeScore(startRow, startCol, maxMoves);
        
        if (maxScore > 0) {
            System.out.println("✅ Máxima puntuación alcanzable: " + maxScore);
            showOptimalPath(startRow, startCol);
        } else {
            System.out.println("❌ No se puede alcanzar puntuación válida");
        }
        
        return maxScore;
    }
    
    /**
     * Implementación principal de Programación Dinámica con memoización
     * Encuentra el máximo puntaje recorriendo el tablero en exactamente k movimientos
     * 
     * IMPORTANTE: Permite visitar casillas múltiples veces para maximizar el score.
     * Esta es la diferencia clave con el Knight's Tour clásico.
     * 
     * Complejidad: O(n^2 * m * 8) donde n = boardSize, m = maxMoves, 8 = knight moves
     * Sin DP: O(8^m) - EXPONENCIAL!
     * Con DP: O(n^2 * m) - POLINOMIAL!
     * 
     * @param currentRow fila actual
     * @param currentCol columna actual
     * @param remainingMoves movimientos restantes
     * @return máximo puntaje posible desde esta posición
     */
    private int dpMaximizeScore(int currentRow, int currentCol, int remainingMoves) {
        // Caso base: si no quedan movimientos, retornar solo el puntaje de la posición actual
        if (remainingMoves == 0) {
            return scores[currentRow][currentCol];
        }
        
        // Verificar memo cache
        if (memo[currentRow][currentCol][remainingMoves] != -1) {
            return memo[currentRow][currentCol][remainingMoves];
        }
        
        // Inicializar con el score de quedarse en la posición actual
        // (si no hay movimientos válidos desde aquí, al menos obtenemos este score)
        int maxScore = scores[currentRow][currentCol];
        
        // Explorar todos los movimientos posibles del caballo
        for (int[] move : KNIGHT_MOVES) {
            int nextRow = currentRow + move[0];
            int nextCol = currentCol + move[1];
            
            // Verificar si el movimiento es válido (solo verifica límites del tablero)
            // NO verificamos visitados - permitimos volver a casillas para maximizar score
            if (isValidPosition(nextRow, nextCol)) {
                // Recurrencia: score de posición actual + máximo score desde la siguiente posición
                // con (remainingMoves - 1) movimientos restantes
                int optionScore = scores[currentRow][currentCol] + 
                                 dpMaximizeScore(nextRow, nextCol, remainingMoves - 1);
                
                // Maximizar sobre todas las opciones
                maxScore = Math.max(maxScore, optionScore);
            }
        }
        
        // Guardar resultado en memo cache
        memo[currentRow][currentCol][remainingMoves] = maxScore;
        return maxScore;
    }
    
    /**
     * Versión alternativa con programación dinámica ITERATIVA (tabulation)
     * Usa bottom-up approach en lugar de memoization
     * 
     * NOTA: Esta implementación es CONSISTENTE con el método recursivo.
     * Ambos permiten visitar casillas múltiples veces para maximizar el score.
     * 
     * @param startRow posición inicial fila
     * @param startCol posición inicial columna
     * @return máximo puntaje alcanzable
     */
    public int solveMaximizeScoreITO(int startRow, int startCol) {
        System.out.println("Ejecutando PD Iterativa (Tabulation)...");
        
        // dp[i][j][k] = máximo puntaje desde posición (i,j) con k movimientos restantes
        int[][][] dp = new int[boardSize][boardSize][maxMoves + 1];
        
        // Llenar tabla DP bottom-up (desde 0 movimientos hasta maxMoves)
        for (int move = 0; move <= maxMoves; move++) {
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    
                    if (move == 0) {
                        // Caso base: 0 movimientos restantes = solo score de posición actual
                        dp[row][col][move] = scores[row][col];
                    } else {
                        // Inicializar con score de posición actual (caso: no moverse)
                        int maxScore = scores[row][col];
                        
                        // Explorar todos los movimientos válidos del caballo
                        for (int[] knightMove : KNIGHT_MOVES) {
                            int nextRow = row + knightMove[0];
                            int nextCol = col + knightMove[1];
                            
                            if (isValidPosition(nextRow, nextCol)) {
                                // Recurrencia: score actual + máximo desde siguiente posición
                                maxScore = Math.max(maxScore, 
                                    scores[row][col] + dp[nextRow][nextCol][move - 1]);
                            }
                        }
                        
                        dp[row][col][move] = maxScore;
                    }
                }
            }
        }
        
        int result = dp[startRow][startCol][maxMoves];
        System.out.println("✅ Máximo puntaje (Iterativo): " + result);
        return result;
    }
    
    /**
     * Muestra información sobre el camino óptimo que lleva al máximo puntaje
     * Nota: La reconstrucción completa del camino requiere almacenar decisiones,
     * no solo los valores óptimos. Por simplicidad, mostramos la información disponible.
     */
    private void showOptimalPath(int startRow, int startCol) {
        System.out.println("--- Análisis de Path Óptimo ---");
        int maxScoreCurrent = memo[startRow][startCol][maxMoves];
        System.out.println("Puntuación máxima desde (" + startRow + "," + startCol + 
                         ") con " + maxMoves + " movimientos: " + maxScoreCurrent);
        System.out.println("(Reconstrucción completa del camino requiere almacenar decisiones en memo)");
    }
    
    /**
     * Muestra matriz de puntuaciones del tablero
     */
    public void displayScores() {
        System.out.println("Tablero de Puntuaciones:");
        System.out.println("======================");
        
        // Borde superior
        System.out.print("+");
        for (int j = 0; j < boardSize; j++) {
            System.out.print("---+");
        }
        System.out.println();
        
        for (int i = 0; i < boardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < boardSize; j++) {
                System.out.printf("%3d|", scores[i][j]);
            }
            System.out.println();
            
            // Borde inferior  
            System.out.print("+");
            for (int j = 0; j < boardSize; j++) {
                System.out.print("---+");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Helper methods ---------------------------------------------------
    
    /**
     * Verifica si una posición está dentro de los límites del tablero
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < boardSize && 
               col >= 0 && col < boardSize;
    }
    
}
