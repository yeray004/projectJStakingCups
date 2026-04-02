package tower;
import java.util.*;

/**
 * Una tapa que nunca cierra. Actua como la base deslizandose por debajoy empujando hacia arriba la taza sobre ella.
 * 
 * @author Yeray Guachetá
 * @version 1.2
 */
public class CrazyLid extends Lid {
    public CrazyLid(int id, int number, String color, int x, int y, int size) {
        super(id, number, color, x, y, size);
        changeColor("magenta"); // Diferenciador visual
    }
    
    /**
     * Modificación del método para la lógica de la tapa loca.
     */
    @Override
    public int calculateY(List<StackItem> items, int index, int towerTop, int floorY) {
        if (items.isEmpty()) {
            return floorY;
        }
        //Revisamos el último objeto de la torre
        StackItem lastItem = items.get(items.size() - 1);

        // si el anterior era una taza se desplaza hacia arriba
        if (lastItem.isCup()) {
            int targetY = lastItem.getY(); // La tapa toma la base de la taza
            //para subir restamos la unidad (Y - UNIT)
            for (StackItem item : items) {
                // Si el objeto es la taza, o cualquier elemento contenido en ella
                if (item.getY() <= targetY) {
                    item.move(item.getX(), item.getY() - UNIT);
                }
            }
            // Retornamos el Y original para que la tapa aterrice justo como la nueva base
            return targetY; 
        }
        //Si el último objeto NO era una taza
        int top = floorY;
        for (StackItem item : items) {
            if (item.getTop() < top) {
                top = item.getTop();
            }
        }
        return top;
    }
    /**
     * Retorna el tipo de tapa.
     */
    @Override
    public String getTypeName() {
        return "crazy";
    }
    
    /**
     * Por deficinión del objeto estetipo de tapa nunca cierra.
     */
    @Override
    public boolean canSeal(StackItem other) {
        return false;
    }
}