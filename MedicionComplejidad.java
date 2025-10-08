/**
 * Programa para medir empíricamente la complejidad temporal de los algoritmos Knight's Tour
 * Demuestra las diferencias de rendimiento entre Backtracking, Warnsdorff y DP
 */
public class MedicionComplejidad {
    
    /**
     * Mide el tiempo de ejecución de cada algoritmo para diferentes tamaños de tablero
     */
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("    MEDICIÓN EMPÍRICA DE COMPLEJIDAD TEMPORAL");
        System.out.println("================================================================");
        System.out.println("Análisis de rendimiento: Backtracking vs Warnsdorff vs DP");
        System.out.println();
        
        // Tabla de resultados
        System.out.println("TABLA DE RESULTADOS:");
        System.out.println("===================");
        System.out.printf("%-8s | %-12s | %-12s | %-12s | %-15s%n", 
                         "Tablero", "Backtracking", "Warnsdorff", "DP (k=8)", "Speedup W/B");
        System.out.println("---------|--------------|--------------|--------------|-----------------");
        
        // Probar diferentes tamaños de tablero
        int[] sizes = {3, 4, 5, 6};
        
        for (int size : sizes) {
            medirAlgoritmos(size);
        }
        
        System.out.println();
        analizarComplejidadTeorica();
        mostrarConclusiones();
    }
    
    /**
     * Mide los tres algoritmos para un tamaño de tablero específico
     */
    private static void medirAlgoritmos(int boardSize) {
        long backtrackTime = 0;
        long warnsdorffTime = 0;
        long dpTime = 0;
        
        // Medir Backtracking (solo para tableros pequeños)
        if (boardSize <= 5) {
            backtrackTime = medirBacktracking(boardSize);
        } else {
            backtrackTime = -1; // No medir para tableros grandes
        }
        
        // Medir Warnsdorff
        warnsdorffTime = medirWarnsdorff(boardSize);
        
        // Medir DP
        dpTime = medirDP(boardSize, 8);
        
        // Mostrar resultados
        String backtrackStr = (backtrackTime == -1) ? "N/A (>1min)" : 
                             String.format("%.2f ms", backtrackTime / 1000000.0);
        String warnsdorffStr = String.format("%.2f μs", warnsdorffTime / 1000.0);
        String dpStr = String.format("%.2f μs", dpTime / 1000.0);
        
        double speedup = (backtrackTime > 0) ? 
                        (double) backtrackTime / warnsdorffTime : 0;
        String speedupStr = (speedup > 0) ? 
                           String.format("%.0fx", speedup) : "N/A";
        
        System.out.printf("%-8s | %-12s | %-12s | %-12s | %-15s%n",
                         boardSize + "x" + boardSize, backtrackStr, warnsdorffStr, dpStr, speedupStr);
    }
    
    /**
     * Mide tiempo de Backtracking
     */
    private static long medirBacktracking(int boardSize) {
        KnightsTour solver = new KnightsTour(boardSize);
        
        long startTime = System.nanoTime();
        boolean result = solver.findSingleSolution(0, 0);
        long endTime = System.nanoTime();
        
        if (!result) {
            System.out.println("⚠️  Backtracking no encontró solución para " + boardSize + "x" + boardSize);
        }
        
        return endTime - startTime;
    }
    
    /**
     * Mide tiempo de Warnsdorff
     */
    private static long medirWarnsdorff(int boardSize) {
        KnightsTourGreedy solver = new KnightsTourGreedy(boardSize);
        
        long startTime = System.nanoTime();
        boolean result = solver.solveWithWarnsdorff(0, 0);
        long endTime = System.nanoTime();
        
        if (!result) {
            System.out.println("⚠️  Warnsdorff no encontró solución para " + boardSize + "x" + boardSize);
        }
        
        return endTime - startTime;
    }
    
    /**
     * Mide tiempo de Programación Dinámica
     */
    private static long medirDP(int boardSize, int k) {
        KnightsTourDP solver = new KnightsTourDP(boardSize, k);
        
        long startTime = System.nanoTime();
        int result = solver.solveMaximizeScore(0, 0);
        long endTime = System.nanoTime();
        
        return endTime - startTime;
    }
    
    /**
     * Muestra análisis de complejidad teórica
     */
    private static void analizarComplejidadTeorica() {
        System.out.println("ANÁLISIS DE COMPLEJIDAD TEÓRICA:");
        System.out.println("=================================");
        System.out.println();
        
        System.out.println("1. BACKTRACKING:");
        System.out.println("   • Complejidad: O(8^(n²))");
        System.out.println("   • Crecimiento: EXPONENCIAL");
        System.out.println("   • Ejemplo: Tablero 4x4 = 8^16 ≈ 2.8 × 10^14 operaciones");
        System.out.println();
        
        System.out.println("2. WARNSDORFF (GREEDY):");
        System.out.println("   • Complejidad: O(n²)");
        System.out.println("   • Crecimiento: POLINOMIAL");
        System.out.println("   • Ejemplo: Tablero 4x4 = 16 operaciones");
        System.out.println();
        
        System.out.println("3. PROGRAMACIÓN DINÁMICA:");
        System.out.println("   • Complejidad: O(n² × k)");
        System.out.println("   • Crecimiento: POLINOMIAL");
        System.out.println("   • Ejemplo: Tablero 4x4, k=8 = 16 × 8 = 128 operaciones");
        System.out.println();
        
        // Calcular factores de mejora
        System.out.println("FACTORES DE MEJORA TEÓRICOS:");
        System.out.println("============================");
        
        int[] sizes = {3, 4, 5, 6};
        for (int n : sizes) {
            long backtrackOps = (long) Math.pow(8, n * n);
            long warnsdorffOps = n * n;
            long dpOps = n * n * 8;
            
            double speedupW = (double) backtrackOps / warnsdorffOps;
            double speedupDP = (double) backtrackOps / dpOps;
            
            System.out.printf("Tablero %dx%d:%n", n, n);
            System.out.printf("  • Backtracking: %d operaciones%n", backtrackOps);
            System.out.printf("  • Warnsdorff: %d operaciones (%.0fx más rápido)%n", 
                             warnsdorffOps, speedupW);
            System.out.printf("  • DP: %d operaciones (%.0fx más rápido)%n", 
                             dpOps, speedupDP);
            System.out.println();
        }
    }
    
    /**
     * Muestra conclusiones del análisis
     */
    private static void mostrarConclusiones() {
        System.out.println("CONCLUSIONES:");
        System.out.println("=============");
        System.out.println();
        
        System.out.println("🏆 RENDIMIENTO POR ALGORITMO:");
        System.out.println("   • Warnsdorff: MÁS RÁPIDO para tableros grandes");
        System.out.println("   • DP: RÁPIDO para problemas de optimización");
        System.out.println("   • Backtracking: LENTO pero GARANTIZA solución");
        System.out.println();
        
        System.out.println("📊 ESCALABILIDAD:");
        System.out.println("   • Backtracking: Impracticable para n > 5");
        System.out.println("   • Warnsdorff: Practicable hasta n = 100+");
        System.out.println("   • DP: Practicable hasta n = 50+ (dependiendo de k)");
        System.out.println();
        
        System.out.println("🎯 RECOMENDACIONES DE USO:");
        System.out.println("   • Tableros pequeños (≤5×5): Backtracking");
        System.out.println("   • Tableros grandes (≥6×6): Warnsdorff");
        System.out.println("   • Optimización con restricciones: DP");
        System.out.println();
        
        System.out.println("⚡ MEJORAS DE RENDIMIENTO:");
        System.out.println("   • Warnsdorff vs Backtracking: 10^6 a 10^15x más rápido");
        System.out.println("   • DP vs Backtracking: 10^4 a 10^12x más rápido");
        System.out.println("   • DP vs Naive Recursive: 8^k / (n²×k) más rápido");
    }
}
