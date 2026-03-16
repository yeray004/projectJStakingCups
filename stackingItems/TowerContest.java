import java.util.*;

/**
 * Clase encargada de resolver el problema de la maratón y simular la solución.
 * Tower no se usa para resolver, solo para simular.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * @support Assisted by Gemini (Google AI) - March 2026
 * @version 1.0
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

        if (order == null) {
            return "impossible";
        }

        return;
    }

    /**
     * Simula visualmente la solución, si existe.
     * @param n número de tazas.
     * @param h altura deseada.
     */
    public void simulate(int n, int h) {
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
    
    /**
     * Construye recursivamente un orden válido de tazas para alcanzar la altura h.
     * Si no existe, retorna null.
     * @param n número de tazas disponibles.
     * @param h altura deseada.
     * @return lista con el orden de los números de taza o null si no existe solución.
     */
    // Metodo elaborado con apoyo de inteligencia artificial.
    private ArrayList<Integer> buildOrder(int n, int h) {
        if (!isReachable(n, h)) {return null;}

        if (n == 1) {
            ArrayList<Integer> single = new ArrayList<>();
            single.add(1);
            return single;
        }

        int minHeight = getMinHeight(n);
        if (h == minHeight) {return buildDescendingNumbers(n);}
        
        if (canPlaceLargestFirst(n, h)) {
            ArrayList<Integer> suffix = buildOrder(n - 1, h - 1);
            if (suffix != null) {
                ArrayList<Integer> result = new ArrayList<>();
                result.add(n);
                result.addAll(suffix);
                return result;
            }
        }

        int targetBeforeLargest = h - minHeight;
        int k = chooseCupCount(targetBeforeLargest, n);
        if (k == -1) { return null;}

        ArrayList<Integer> prefix;
        if (k == 0) {
            prefix = new ArrayList<>();
        } else {
            prefix = buildOrder(k, targetBeforeLargest);
        }

        if (prefix == null) {
            return null;
        }

        ArrayList<Integer> result = new ArrayList<>(prefix);
        result.add(n);
        for (int i = n - 1; i > k; i--) {
            result.add(i);
        }
        return result;
    }
    
    /**
     * Indica si la altura h es alcanzable con n tazas.
     * @param n número de tazas.
     * @param h altura objetivo.
     * @return true si la altura se puede construir.
     */
    private boolean isReachable(int n, int h) {
        if (n < 1) {
            return false;
        }

        int minHeight = getMinHeight(n);
        int maxHeight = getMaxHeight(n);
        boolean res = h >= minHeight && h <= maxHeight && h != maxHeight - 2;
        return res;
    }
    
    /**
     * Determina si conviene ubicar primero la taza más grande.
     * @param n número de tazas.
     * @param h altura deseada.
     * @return true si se puede seguir el caso recursivo con la taza mayor al inicio.
     */
    private boolean canPlaceLargestFirst(int n, int h) {
        int upperBound = getMaxHeight(n - 1) + 1;
        int forbiddenValue = getMaxHeight(n - 1) - 1;
        boolean res = h <= upperBound && h != forbiddenValue; 
        return res;
    }
    
    /**
     * Escoge cuántas tazas pequeñas deben ir antes de la taza mayor.
     * @param target altura que debe lograrse antes de ubicar la taza más grande.
     * @param limit número máximo de tazas disponibles.
     * @return cantidad k de tazas a usar o -1 si no existe una elección válida.
     */
    private int chooseCupCount(int target, int limit) {
        if (target == 0) {
            return 0;
        }

        int k = (int) Math.sqrt(target);
        if (k * k < target) {
            k++;
        }

        if ((k * k) - 2 == target) {
            k++;
        }

        if (k >= limit) {
            return -1;
        }

        if (target < getMinHeight(k) || target > getMaxHeight(k)) {
            return -1;
        }

        return k;
    }
    
    /**
     * Indica si la cantidad de tazas es válida.
     * @param n número de tazas.
     * @return true si n es válido.
     */
    private boolean isValidCupCount(int n) {
        return n >= 1;
    }

    /**
     * Calcula la altura mínima posible usando n tazas.
     * @param n número de tazas.
     * @return altura mínima.
     */
    private int getMinHeight(int n) {
        if (n == 0) {
            return 0;
        }
        return (2 * n) - 1;
    }

    /**
     * Calcula la altura máxima posible con n tazas.
     * @param n número de tazas.
     * @return altura máxima posible.
     */
    private int getMaxHeight(int n) {
        if (n == 0) {
            return 0;
        }
        return n * n;
    }

    /**
     * Construye los números de taza en orden descendente.
     * @param n número de tazas.
     * @return lista descendente desde n hasta 1.
     */
    private ArrayList<Integer> buildDescendingNumbers(int n) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = n; i >= 1; i--) {
            result.add(i);
        }

        return result;
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