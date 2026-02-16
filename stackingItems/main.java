import java.util.ArrayList;
import java.util.Collections;

public class main {
    private int width;
    private int maxHeight;
    private ArrayList<Integer> tazas;
    private ArrayList<Integer> tapas;
    private boolean ultimaOperacionExitosa;

    // 1. Crear la torre
    public main(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.tazas = new ArrayList<Integer>();
        this.tapas = new ArrayList<Integer>();
        this.ultimaOperacionExitosa = true;
    }

    // 2. Adicionar o eliminar taza
    public void pushCup(int i) {
        if (this.height() < maxHeight) {
            tazas.add(i);
            ultimaOperacionExitosa = true;
        } else {
            ultimaOperacionExitosa = false;
        }
    }

    public void popCup() {
        if (tazas.size() > 0) {
            tazas.remove(tazas.size() - 1);
            ultimaOperacionExitosa = true;
        } else {
            ultimaOperacionExitosa = false;
        }
    }

    // 3. Adicionar o eliminar tapa
    public void pushLid(int i) {
        // Regla: Solo si la taza existe
        if (tazas.contains(i)) {
            tapas.add(i);
            ultimaOperacionExitosa = true;
        } else {
            ultimaOperacionExitosa = false;
        }
    }

    public void popLid() {
        if (tapas.size() > 0) {
            tapas.remove(tapas.size() - 1);
            ultimaOperacionExitosa = true;
        } else {
            ultimaOperacionExitosa = false;
        }
    }

    // 4. Ordenar de mayor a menor
    public void orderTower() {
        Collections.sort(tazas);
        Collections.reverse(tazas); // Así queda de mayor a menor
        ultimaOperacionExitosa = true;
    }

    // 5. Orden inverso
    public void reverseTower() {
        Collections.reverse(tazas);
        ultimaOperacionExitosa = true;
    }

    // 6. Consultar altura
    public int height() {
        // Tazas y tapas miden 1cm
        return tazas.size() + tapas.size();
    }

    // Para el método ok() del diseño
    public boolean ok() {
        return ultimaOperacionExitosa;
    }
}