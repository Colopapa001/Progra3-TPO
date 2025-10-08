/**
 * Ejemplo de uso del tablero Knight's Tour
 * Muestra diferentes escenarios de uso del algoritmo
 */
public class EjemploUso {
    
    public static void main(String[] args) {
        
        System.out.println("=== EJEMPLOS DE USO DEL KNIGHT'S TOUR ===\n");
        
        // Ejemplo 1: Tablero 3x3 (solución simple)
        ejemploBasico();
        
        // Ejemplo 2: Tablero 4x4 (más complejo)
        ejemploIntermedio();
        
        // Ejemplo 3: Pruebas con diferentes posiciones iniciales
        ejemploDiferentesPosiciones();
        
        System.out.println("=== FIN DE EJEMPLOS ===");
    }
    
    /**
     * Ejemplo básico con tablero 3x3
     */
    private static void ejemploBasico() {
        System.out.println("--- EJEMPLO BÁSICO (3x3) ---");
        
        KnightsTour tour = new KnightsTour(3);
        
        try {
            if (tour.findSingleSolution(0, 0)) {
                System.out.println("SOLUCIÓN ENCONTRADA:");
                tour.displayBoard(tour.getBoardCopy());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * Ejemplo intermedio con tablero 4x4
     */
    private static void ejemploIntermedio() {
        System.out.println("--- EJEMPLO INTERMEDIO (4x4) ---");
        
        KnightsTour tour = new KnightsTour(4);
        
        System.out.println("Buscando solución desde (1,1):");
        if (tour.findSingleSolution(1, 1)) {
            tour.displayBoard(tour.getBoardCopy());
        }
        
        System.out.println("Contando todas las soluciones desde (0,0):");
        int soluciones = tour.findAllSolutions(0, 0);
        System.out.println("Total de soluciones: " + soluciones + "\n");
    }
    
    /**
     * Prueba con diferentes posiciones iniciales
     */
    private static void ejemploDiferentesPosiciones() {
        System.out.println("--- PRUEBA CON DIFERENTES POSICIONES INICIALES ---");
        
        KnightsTour tour = new KnightsTour(4);
        int[][] posiciones = {{0,0}, {1,1}, {2,2}, {3,3}};
        String[] etiquetas = {"Esquina (0,0)", "Centro (1,1)", "Esquina (2,2)", "Esquina (3,3)"};
        
        for (int i = 0; i < posiciones.length; i++) {
            System.out.println("Probando desde " + etiquetas[i] + ":");
            
            if (tour.findSingleSolution(posiciones[i][0], posiciones[i][1])) {
                System.out.println("✓ SOLUCIÓN ENCONTRADA");
                tour.displayBoard(tour.getBoardCopy());
            } else {
                System.out.println("✗ Sin solución desde esta posición");
            }
            System.out.println();
        }
    }
}
