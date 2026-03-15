import java.util.List;

/**
 * Clase que representa una tapa.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * 
 * @version 4.0
 */
public class Lid extends StackItem {
    private static final int ORDER_PRIORITY = 1;
    private static final int HEIGHT_UNITS = 1;
    private static final String TYPE_NAME = "lid";
    private final int size;

    /**
     * Constructor for objects of class Lid
     * @param id Identificador único de la tapa.
     * @param color Color de la tapa.
     * @param x Posición horizontal.
     * @param y Posición vertical.
     */
    public Lid(int id, String color, int x, int y, int size){
        super(id, color, x, y); // Línea de código implementada por Inteligencia artificial
        this.size = size;
        
        Rectangle lid = new Rectangle();
        lid.changeSize(UNIT, size * UNIT);
        lid.changeColor(color);
        lid.moveHorizontal(x - ((size* UNIT) / 2));
        lid.moveVertical(y - UNIT);
        this.shapes.add(lid);
    }

    /**
     * Indica si esta tapa puede sellar a otro objeto.
     *
     * @param other elemento candidato a sellar.
     * @return true si el otro elemento puede ser sellado por esta tapa.
     */
    @Override
    public boolean canSeal(StackItem other) {
        return other.canBeSealedBy(this);
    }

    /**
     * Busca la taza que esta tapa está sellando.
     *
     * @param items elementos de la torre.
     * @return taza asociada o null si no existe.
     */
    @Override
    public StackItem findPartner(List<StackItem> items) {
        for (StackItem item : items) {
            if (!item.hasPartner() && canSeal(item) && getY() == item.getSealY()) {
                return item;
            }
        }
        return null;
    }

    /**
     * Indica si el item corresponde al tipo tapa.
     *
     * @return true siempre para una tapa.
     */
    @Override
    public boolean isLid() {
        return true;
    }

    /**
     * Devuelve la prioridad usada al ordenar elementos del mismo tamaño.
     *
     * @return prioridad de orden de la tapa.
     */
    @Override
    public int getOrderPriority() {
        return ORDER_PRIORITY;
    }

    /**
     * Devuelve el tipo del elemento.
     *
     * @return tipo lid.
     */
    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * Devuelve la altura lógica del objeto en unidades.
     *
     * @return altura lógica de la tapa.
     */
    @Override
    public int getHeightUnits() {
        return HEIGHT_UNITS;
    }

    /**
     * Regresa el tamaño de la tapa.
     *
     * @return tamaño de la tapa.
     */
    @Override
    public int getSize() {
        return size;
    }
}