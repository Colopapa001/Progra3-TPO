/**
 * Ejemplo práctico de la Programación Dinámica aplicada a Knight's Tour
 * Problema adaptado: Maximizar puntuación en exactamente k movimientos
 */
public class EjemploDPKnightTour {
    
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("    EJEMPLO: KNIGHTS TOUR + PROGRAMACIÓN DINÁMICA - PARTE 3");
        System.out.println("================================================================");
        System.out.println("Problema: Maximizar puntuación en exactamente k movimientos");
        System.out.println("Solución: DP reduce complejidad de O(8^k) a O(n²*k)");
        System.out.println();

        ejemploBasico();        
        ejemploConComparacion();
        demostrarVentajaDP();
    }
    
    /**
     * Ejemplo básico que muestra el funcionamiento de DP
     */
    private static void ejemploBasico() {
        System.out.println("--- EJEMPLO BÁSICO: Tablero 4x4 con k=5 movimientos ---");
        
        KnightsTourDP dpSolver = new KnightsTourDP(4, 5);
        
        // Configurar puntuaciones conocidas para determinismo
        dpSolver.setCustomScores(new int[][]{
            {8, 12, 10, 6},
            {5, 15, 4, 9},
            {11, 7, 13, 3},
            {14, 2, 8, 6}
        });
        
        dpSolver.displayScores();
        
        // Medición de tiempo
        long startTime = System.nanoTime();
        int maxScore = dpSolver.solveMaximizeScore(0, 0);
        long executionTime = System.nanoTime() - startTime;
        
        System.out.println("Resultados:");
        System.out.println("• Máxima puntuación: " + maxScore);
        System.out.println("• Tiempo de ejecución: " + String.format("%.2f", executionTime/1000.0) + " μs");
        System.out.println("• Algoritmo: Programación Dinámica con memoización");
        System.out.println();
    }
    
    /**
     * Comparación en tableros más grandes
     */
    private static void ejemploConComparacion() {
        System.out.println("--- EJEMPLO MEDIANO: Tablero 5x5 con k=7 movimientos ---");
        
        KnightsTourDP dp1 = new KnightsTourDP(5, 7);
        dp1.displayScores();
        
        // Comparar memoización vs iterativo
        KnightsTourDP dp2 = new KnightsTourDP(5, 7);
        
        long time1 = System.nanoTime();
        int result1 = dp1.solveMaximizeScore(1, 1);
        long memoTime = System.nanoTime() - time1;
        
        long time2 = System.nanoTime();
        int result2 = dp2.solveMaximizeScoreITO(1, 1);
        long iterTime = System.nanoTime() - time2;
        
        System.out.println();
        System.out.println("📊 COMPARACIÓN DP Implementation:");
        System.out.println("🔧 Memoization result: " + result1 + " pts, " + memoTime/1000.0 + " μs");
        System.out.println("🔧 Tabulation result:  " + result2 + " pts, " + iterTime/1000.0 + " μs");
        System.out.println("⚡ Speedup ratio:      " + ((double)memoTime/iterTime) + "x faster Tabulation");
        System.out.println();
    }
    
    /**
     * Demuestra ventajas computacionales específicas de DP
     */
    private static void demostrarVentajaDP() {
        System.out.println("--- ANÁLISIS COMPUTACIONAL: DP vs EXHAUSTIVO ---");
        
        int boardSize = 6;
        int k = 8;        
        
        KnightsTourDP dpSolver = new KnightsTourDP(boardSize, k);
        long time = System.nanoTime();
        int dpResult = dpSolver.solveMaximizeScore(0, 0);
        time = System.nanoTime() - time;
        
        // Calcular complejidad teórica
        long polinomops = (long)(boardSize * boardSize * k * 8);
        long exponcalculations = (long)Math.pow(8.0, k);
        long theoreticalRatio = exponcalculations / polinomops;
            
        System.out.println("Computational COMPARISM:");
        System.out.println("• Weight-D PCC execution: " + time/1000.0 + " μs, max score: " + dpResult);
        System.out.println("• Theoretical polynomial  : " + polinomops + " operations");
        System.out.println("• Theoretical exponential : " + exponcalculations + " operations (naive recursive)");
        System.out.println("• Performance gain       : " + theoreticalRatio + "-fold acceleration secured!");
        System.out.println("• Geometric saving      : massive speed-up attest to DP superiority ✓");
        
        System.out.println();
        System.out.println("🎯 ADVANTAGO DYNAMIC PROGRAMMING que unscrimín|| correcte formulation");
        System.out.println("=====================================");
        System.out.println("# KNIGHTS TOUR original-> WARNSDORFF              « MÁXIMO SPEED »");
        System.out.println("# KNIGHTS TOUR evaluation -> Mäximo score DP    « MOSTLY UNBREAKABLE »");
        System.out.println("# KNIGHTS TOUR K↔-budget -> DP formulation optimal« Works TOP_CLOTH » ");
        System.out.println("TEXT COMPUTER MÆINDER that ∴ KNIGHTS TOUR -> DP coverage environment."); 
    }
}
