/**
 * Archivo de pruebas unificado para los 3 algoritmos: Backtracking, Greedy y DP
 * Permite comparar el rendimiento y funcionamiento de los diferentes enfoques
 */
public class Pruebas {
    
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("    PRUEBAS COMPARATIVAS - KNIGHT'S TOUR");
        System.out.println("================================================================");
        System.out.println();
        
        // Menú de opciones
        if (args.length == 0) {
            mostrarMenu();
        } else {
            String opcion = args[0].toLowerCase();
            
            switch(opcion) {
                case "1":
                case "backtracking":
                    ejecutarPruebasBacktracking();
                    break;
                case "2":
                case "greedy":
                    ejecutarPruebasGreedy();
                    break;
                case "3":
                case "dp":
                case "programacion":
                    ejecutarPruebasDP();
                    break;
                case "4":
                case "completo":
                case "all":
                    ejecutarPruebasCompletas();
                    break;
                case "5":
                case "comparacion":
                    ejecutarComparacion();
                    break;
                default:
                    System.out.println("Opción no válida. Use 'java Pruebas' para ver el menú.");
            }
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("Opciones disponibles:");
        System.out.println("1. Ejecutar solo Backtracking");
        System.out.println("2. Ejecutar solo Greedy (Warnsdorff)");
        System.out.println("3. Ejecutar solo Programación Dinámica");
        System.out.println("4. Ejecutar todos los algoritmos");
        System.out.println("5. Ejecutar comparación de rendimiento");
        System.out.println();
        System.out.println("Uso: java Pruebas [opción]");
        System.out.println("Ejemplo: java Pruebas 1");
    }
    
    private static void ejecutarPruebasBacktracking() {
        System.out.println("\n=== PRUEBAS DE BACKTRACKING ===");
        System.out.println();
        
        int[] tamaños = {3, 4, 5};
        for (int size : tamaños) {
            System.out.println("Tablero " + size + "x" + size + ":");
            System.out.println("-----------------------------------");
            
            KnightsTour solver = new KnightsTour(size);
            
            long startTime = System.nanoTime();
            boolean encontrado = solver.findSingleSolution(0, 0);
            long endTime = System.nanoTime();
            
            if (encontrado) {
                System.out.println("✓ Solución encontrada en " + (endTime - startTime) / 1000000.0 + " ms");
                if (size <= 4) {
                    solver.displayBoard(solver.getBoardCopy());
                }
            } else {
                System.out.println("✗ No se encontró solución");
            }
            System.out.println();
        }
    }
    
    private static void ejecutarPruebasGreedy() {
        System.out.println("\n=== PRUEBAS DE HEURÍSTICA GREEDY (WARNSDORFF) ===");
        System.out.println();
        
        int[] tamaños = {5, 6, 7, 8};
        for (int size : tamaños) {
            System.out.println("Tablero " + size + "x" + size + ":");
            System.out.println("-----------------------------------");
            
            KnightsTourGreedy solver = new KnightsTourGreedy(size);
            
            long startTime = System.nanoTime();
            boolean encontrado = solver.solveWithWarnsdorff(0, 0);
            long endTime = System.nanoTime();
            
            if (encontrado) {
                System.out.println("✓ Solución encontrada en " + (endTime - startTime) / 1000.0 + " μs");
                if (size <= 6) {
                    solver.displayBoard();
                }
            } else {
                System.out.println("✗ No se encontró solución");
            }
            System.out.println();
        }
    }
    
    private static void ejecutarPruebasDP() {
        System.out.println("\n=== PRUEBAS DE PROGRAMACIÓN DINÁMICA ===");
        System.out.println();
        
        // Prueba 1: Tablero 4x4 con k=5
        System.out.println("Tablero 4x4, k=5 movimientos:");
        System.out.println("-----------------------------------");
        
        KnightsTourDP dp1 = new KnightsTourDP(4, 5);
        dp1.setCustomScores(new int[][]{
            {8, 12, 10, 6},
            {5, 15, 4, 9},
            {11, 7, 13, 3},
            {14, 2, 8, 6}
        });
        
        dp1.displayScores();
        
        long startTime = System.nanoTime();
        int resultado1 = dp1.solveMaximizeScore(0, 0);
        long endTime = System.nanoTime();
        
        System.out.println("Máxima puntuación: " + resultado1);
        System.out.println("Tiempo: " + (endTime - startTime) / 1000.0 + " μs");
        System.out.println();
        
        // Prueba 2: Tablero 5x5 con k=8
        System.out.println("Tablero 5x5, k=8 movimientos:");
        System.out.println("-----------------------------------");
        
        KnightsTourDP dp2 = new KnightsTourDP(5, 8);
        dp2.displayScores();
        
        startTime = System.nanoTime();
        int resultado2 = dp2.solveMaximizeScore(0, 0);
        endTime = System.nanoTime();
        
        System.out.println("Máxima puntuación: " + resultado2);
        System.out.println("Tiempo: " + (endTime - startTime) / 1000.0 + " μs");
        System.out.println();
    }
    
    private static void ejecutarPruebasCompletas() {
        System.out.println("\n=== EJECUTANDO TODOS LOS ALGORITMOS ===");
        ejecutarPruebasBacktracking();
        ejecutarPruebasGreedy();
        ejecutarPruebasDP();
    }
    
    private static void ejecutarComparacion() {
        System.out.println("\n=== COMPARACIÓN DE RENDIMIENTO ===");
        System.out.println();
        
        int[] tamaños = {4, 5, 6};
        
        System.out.printf("%-15s | %-15s | %-15s | %-15s%n", 
                         "Tablero", "Backtracking", "Greedy", "Speedup");
        System.out.println("-----------------|-----------------|-----------------|-----------------");
        
        for (int size : tamaños) {
            // Backtracking
            long tiempoBT = 0;
            if (size <= 5) {
                KnightsTour btSolver = new KnightsTour(size);
                long start = System.nanoTime();
                btSolver.findSingleSolution(0, 0);
                tiempoBT = System.nanoTime() - start;
            }
            
            // Greedy
            KnightsTourGreedy greedySolver = new KnightsTourGreedy(size);
            long start = System.nanoTime();
            greedySolver.solveWithWarnsdorff(0, 0);
            long tiempoGreedy = System.nanoTime() - start;
            
            // Mostrar resultados
            String btStr = (tiempoBT > 0) ? String.format("%.2f ms", tiempoBT/1000000.0) : "N/A";
            String greedyStr = String.format("%.2f μs", tiempoGreedy/1000.0);
            String speedup = (tiempoBT > 0 && tiempoGreedy > 0) ? 
                           String.format("%.0fx", (double)tiempoBT/tiempoGreedy) : "N/A";
            
            System.out.printf("%-15s | %-15s | %-15s | %-15s%n", 
                             size + "x" + size, btStr, greedyStr, speedup);
        }
        
        System.out.println();
        System.out.println("Tabla de referencia:");
        System.out.println("- Backtracking: Complejidad O(8^(n²)) - Exponencial");
        System.out.println("- Greedy: Complejidad O(n²) - Polinomial");
        System.out.println("- DP: Complejidad O(n²×k) - Polinomial");
    }
}

