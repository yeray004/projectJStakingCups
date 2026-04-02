package tower;
import java.util.List;
/**
 * A cup that destroys any strictly smaller items in its falling path until it finds a valid base.
 * 
 * @author Yeray Guachetá
 * @version 1.3
 */
public class OpenerCup extends Cup {

    public OpenerCup(int id, int number, String color, int x, int y, int size) {
        super(id, number, color, x, y, size);
        changeColor("black"); // Diferenciador visual automático
    }

    @Override
    public int calculateY(List<StackItem> items, int index, int towerTop, int floorY) {
        if (items.isEmpty()) {
            return floorY;
        }

        StackItem container = null;
        int innerTop = floorY;
        int actualTowerTop = floorY; 

        // Iteramos hacia atrás para poder eliminar elementos con seguridad
        for (int i = items.size() - 1; i >= 0; i--) {
            StackItem candidate = items.get(i);

            // LÓGICA REAL: Si es más pequeño, se destruye VISUAL y LÓGICAMENTE
            if (candidate.getHeightUnits() < this.getHeightUnits()) {
                candidate.makeInvisible(); 
                items.remove(i); // ¡Eliminamos al fantasma de la torre!
                continue; 
            }

            // Actualizamos el tope solo con los objetos que sobrevivieron
            if (candidate.getTop() < actualTowerTop) {
                actualTowerTop = candidate.getTop();
            }

            // Usamos items.size() dinámico porque la lista pudo haberse encogido
            if (container == null && candidate.canContain(this) && !isClosedCupBefore(items, i, items.size())) {
                container = candidate;
                innerTop = candidate.getInnerFloorY();

                for (int j = i + 1; j < items.size(); j++) {
                    StackItem innerItem = items.get(j);
                    if (innerItem.getTop() < innerTop) {
                        innerTop = innerItem.getTop();
                    }
                }
            }
        }

        return container != null ? innerTop : actualTowerTop; 
    }

    @Override
    public String getTypeName() {
        return "opener";
    }
}