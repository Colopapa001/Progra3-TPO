/**
 * Comparación detallada de rendimiento entre Backtracking y Warnsdorff
 * Genera métricas claras y estadísticas de performance
 */
public class ComparacionAlgoritmos {
    
    /**
     * Compara los dos enfoques en tableros de diferentes tamaños
     */
    public static void ejecutarComparacionCompleta() {
        System.out.println("========================================================");
        System.out.println("    ANÁLISIS COMPARATIVO: BACKTRACKING vs WARNSDORFF  ");
        System.out.println("========================================================");
        
        // Diferentes tamaños para análisis
        int[] sizes = {5, 6, 7, 8};
        
        // Diferentes posiciones iniciales para testing
        int[][][] testPositions = {
            {{0, 0}, {1, 1}}, // Tablero 5x5
            {{0, 0}, {1, 1}, {2, 2}}, // Tablero 6x6  
            {{0, 0}, {2, 2}, {3, 3}}, // Tablero 7x7
            {{0, 0}, {1, 1}, {3, 3}}  // Tablero 8x8
        };
        
        for (int i = 0; i < sizes.length; i++) {
            int boardSize = sizes[i];
            System.out.println("\n🔸 ANÁLISIS TABLERO " + boardSize + "x" + boardSize);
            System.out.println("================================");
            
            ejecutarComparacionPorSize(boardSize, testPositions[i]);
        }
        
        imprimirResumenFinal();
    }
    
    /**
     * Ejecuta comparación detallada para un tamaño de tablero específico
     */
    private static void ejecutarComparacionPorSize(int boardSize, int[][] positions) {
        int warnsdorffWins = 0;
        int backtrackWins = 0;
        long totalWarnsdorffTime = 0;
        long totalBacktrackTime = 0;
        
        for (int[] position : positions) {
            System.out.println("\n📍 Posición inicial: (" + position[0] + ", " + position[1] + ")");
            
            // RESULTADOS WARNSDORFF
            long warnsdorffTime = medirTiempoWarnsdorff(boardSize, position[0], position[1]);
            
            // RESULTADOS BACKTRACKING
            long backtrackTime = medirTiempoBacktracking(boardSize, position[0], position[1]);
            
            totalWarnsdorffTime += warnsdorffTime;
            totalBacktrackTime += backtrackTime;
            
            // Determinar ganador del speedup
            if (warnsdorffTime > 0 && backtrackTime > 0) {
                if (boardSize >= 6 && warnsdorffTime < backtrackTime) {
                    warnsdorffWins++;
                } else if (backtrackTime < warnsdorffTime) {
                    backtrackWins++;
                }
            }
            
            System.out.println();
        }
        
        // Estadísticas por tablero
        System.out.println("\n📊 ESTADÍSTICAS TABLERO " + boardSize + "x" + boardSize + ":");
        System.out.println("  Warnsdorff (promedio): " + (totalWarnsdorffTime/positions.length) + " ns");
        System.out.println("  Backtracking (promedio): " + (totalBacktrackTime/positions.length) + " ns");
        System.out.println("  Ventajas Warnsdorff: " + (warnsdorffWins) + " de " + positions.length + " casos");
        System.out.println("  Ventajas Backtracking: " + (backtrackWins) + " de " + positions.length + " casos");
    }
    
    /**
     * Mide tiempo de ejecución del algoritmo Warnsdorff
     */
    private static long medirTiempoWarnsdorff(int boardSize, int startRow, int startCol) {
        KnightsTourGreedy solver = new KnightsTourGreedy(boardSize);
        
        long startTime = System.nanoTime();
        boolean result = solver.solveWithWarnsdorff(startRow, startCol);
        long endTime = System.nanoTime();
        
        long elapsedTime = endTime - startTime;
        
        String status = result ? "✅ ÉXITO" : "❌ FALLO";
        System.out.println("  🤖 Warnsdorff: " + status + " en " + 
                         String.format("%.2f", elapsedTime/1000000.0) + " ms");
        
        return result ? elapsedTime : 0; // Solo contar éxitos para stats
    }
    
    /**
     * Mide tiempo de ejecución del algoritmo Backtracking
     */
    private static long medirTiempoBacktracking(int boardSize, int startRow, int startCol) {
        KnightsTour solver = new KnightsTour(boardSize);
        
        long startTime = System.nanoTime();
        boolean result = solver.findSingleSolution(startRow, startCol);
        long endTime = System.nanoTime();
        
        long elapsedTime = endTime - startTime;
        
        String status = result ? "✅ ÉXITO" : "❌ FALLO";
        System.out.println("  🔍 Backtracking: " + status + " en " + 
                         String.format("%.2f", elapsedTime/1000000.0) + " ms");
        
        return result ? elapsedTime : 0; // Solo contar éxitos para stats
    }
    
    /**
     * Imprime el resumen final con conclusiones
     */
    private static void imprimirResumenFinal() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                            CONCLUSIONES FINALES");
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("🏆 WARNSDORFF (Heurística Greedy):");
        System.out.println("  ✓ VENTAJAS:");
        System.out.println("    • Velocidad EXCEPCIONAL: O(n²) vs O(8^(n²))");
        System.out.println("    • Bajo consumo de memoria");
        System.out.println("    • Deterministico (misma solución siempre)");
        System.out.println("  ✗ DESVENTAJAS:");
        System.out.println("    • Puede fallar en tableros muy pequeños");
        System.out.println("    • No explora todas las soluciones");
        System.out.println("    • Empieza una vez y no encuentra bacxtrack");
        System.out.println();
        
        System.out.println("🤖 BACKTRACKING (Exploración Completa):");
        System.out.println("  ✓ VENTAJAS:");
        System.out.println("    • Garantiza encontrar solución si existe");
        System.out.println("    • Encuentra TODAS las soluciones");
        System.out.println("    • Nunca falla si hay solución");
        System.out.println("  ✗ DESVENTAJAS:");
        System.out.println("    • Puede ser MUI lento en tableros grandes:");
        System.out.println("      - Tablero 6x6: Segundos/Minutos");
        System.out.println("      - Tablero 7x7: Horas/Días");
        System.out.println("    • Consume mucha memoria con recursión profunda");
        System.out.println("    • Complejidad espacial polinomial");
        System.out.println();
        
        System.out.println("🎯 RECOMENDACIONES:");
        System.out.println("  • Para tableros <= 5x5: Usar BACKTRACKING (comprehensividad)");
        System.out.println("  • Para tableros >= 6x6: Usar WARNSDORFF (velocidad)");
        System.out.println("  • Para exaustivos estudios: BACKTRACKING");
        System.out.println("  • Para soluciones básicas veloces: WARNSDORFF");
    }
    
    /**
     * Función principal para lanzar la comparación
     */
    public static void main(String[] args) {
        ejecutarComparacionCompleta();
    }
}
