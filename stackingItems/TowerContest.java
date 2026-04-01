import java.util.*;
/**
 * Clase encargada de resolver el problema de la maratón y simular la solución.
 * Tower no se usa para resolver, solo para simular.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * @support Assisted by Gemini (Google AI) - March 2026
 * @version 2.0
 */
public class TowerContest {

    /**
     * Resuelve el problema para n tazas y altura favorita h.
     * @param n número de tazas.
     * @param h altura deseada.
     * @return una secuencia de alturas separadas por espacios o "impossible".
     */
    public String solve(int n, int h) {
        ArrayList<Integer> order = buildOrder(n, h);
        String response = (order == null) ? "impossible" : buildHeightsString(order);
        return response;
    }

    /**
     * Simula visualmente la solución, si existe.
     * @param n número de tazas.
     * @param h altura deseada.
     */
    public void simulate(int n, int h) {
        if (n > 20) {
            System.out.println("Simulación visual omitida: 'n' es demasiado grande para la vista.");
            return;
        }

        String solution = solve(n, h);

        if (solution.equals("impossible")) {
            System.out.println("impossible");
            return;
        }

        Tower tower = new Tower(800, h);
        tower.makeVisible();

        String[] heights = solution.split(" ");
        for (String height : heights) {
            tower.pushCup(Integer.parseInt(height));
        }
    }
    
    private ArrayList<Integer> buildOrder(int n, long h) {
        if (h < getMinHeight(n) || h > getMaxHeight(n)) {
            return null;
        }

        ArrayList<Integer> order = new ArrayList<>();
        boolean[] used = new boolean[n + 1];
        long[] bottoms = new long[n + 1]; // Guarda la posición Y del piso de cada taza
        
        if (solveBacktrack(n, h, order, used, bottoms, 0)) {
            return order;
        }
        return null;
    }

    private boolean solveBacktrack(int n, long targetH, ArrayList<Integer> order, boolean[] used, long[] bottoms, long currentMaxPeak) {
        if (order.size() == n) {
            return currentMaxPeak == targetH;
        }
        for (int i = n; i >= 1; i--) {
            if (!used[i]) {
                // Calcular dónde aterriza la taza evaluando colisión con TODAS las puestas
                long nextFloor = 0;
                for (int j : order) {
                    long restingPoint;
                    if (i < j) {
                        restingPoint = bottoms[j] + 1; // Cae adentro, aterriza en su base
                    } else {
                        restingPoint = bottoms[j] + (2L * j - 1); // No cabe, choca con la cima
                    }
                    if (restingPoint > nextFloor) {
                        nextFloor = restingPoint;
                    }
                }

                bottoms[i] = nextFloor;
                long nextPeak = Math.max(currentMaxPeak, nextFloor + (2L * i - 1));
                // Podar y explorar
                if (nextPeak <= targetH) {
                    used[i] = true;
                    order.add(i);
                    
                    if (solveBacktrack(n, targetH, order, used, bottoms, nextPeak)) {
                        return true;
                    }
                    
                    order.remove(order.size() - 1);
                    used[i] = false;
                }   
            }
        }
        return false;
    }

    private long getMinHeight(int n) {
        if (n <= 0) return 0;
        return (2L * n) - 1;
    }

    private long getMaxHeight(int n) {
        if (n <= 0) return 0;
        return (long) n * n;
    }   
    
    /**
     * Convierte una lista de números de taza al string de alturas que exige el problema.
     * @param order orden de números de taza.
     * @return alturas separadas por espacios.
     */
    private String buildHeightsString(ArrayList<Integer> order) {
        StringJoiner joiner = new StringJoiner(" "); //implementación de StringJoiner como sugerencia de IA

        for (int number : order) {
            joiner.add(String.valueOf((2 * number) - 1));
        }

        return joiner.toString();
    }
}