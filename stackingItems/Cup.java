/**
 * Clase que representa una Taza en la torre.
 * Hereda los atributos y métodos de StackItem.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * 
 * @version 1.0
 */
public class Cup extends StackItem{
    private static final int ORDER_PRIORITY = 0;
    private static final String TYPE_NAME = "cup";

    private final int size;

    /**
     * Construye una taza.
     * @param id Identificador.
     * @param color Color.
     * @param x Posición X.
     * @param y Posición Y.
     * @param height Altura específica de la taza.
     */
    public Cup(int id, String color, int x, int y, int size) {
        super(id, color, x, y); // Línea de código implementada por Inteligencia artificial
        this.size = size;
        
        if (size ==1){
            Rectangle solid = new Rectangle();
            solid.changeColor(color);
            solid.moveHorizontal(x - (UNIT/ 2));
            solid.moveVertical(y - UNIT);
            this.shapes.add(solid);
        } else{
            int calc = size * UNIT;
            
            Rectangle left = new Rectangle();
            left.changeSize(calc, UNIT); // Alto: size, Ancho: 1 bloque
            left.changeColor(color);
            left.moveHorizontal(x - (calc / 2));
            left.moveVertical(y - calc);

            Rectangle right = new Rectangle();
            right.changeSize(calc, UNIT);
            right.changeColor(color);
            right.moveHorizontal(x + (calc / 2) - UNIT);
            right.moveVertical(y - calc);

            Rectangle base = new Rectangle();
            base.changeSize(UNIT, (size - 2) * UNIT); // Alto: 1 bloque, Ancho: el hueco
            base.changeColor(color);
            base.moveHorizontal(x - (calc / 2) + UNIT);
            base.moveVertical(y - UNIT);
            // Añadimos a la lista de la madre
            shapes.add(left);
            shapes.add(right);
            shapes.add(base);
        }
    }

    /**
     * Indica si esta taza puede contener a otro objeto.
     * @param other elemento a contener.
     * @return true si el otro elemento cabe dentro de esta taza.
     */
    @Override
    public boolean canContain(StackItem other) {
        return other.canBeContainedBy(this);
    }

    /**
     * Indica si esta taza puede ser sellada por una tapa.
     * @param lid tapa candidata.
     * @return true si la tapa la puede sellar.
     */
    @Override
    public boolean canBeSealedBy(Lid lid) {
        return size == lid.getSize();
    }

    /**
     * Indica si esta taza está tapada.
     * @return true si la taza tiene una pareja asociada.
     */
    @Override
    public boolean isSealedCup() {
        return hasPartner();
    }

    /**
     * Indica si el item corresponde al tipo taza.
     * @return true siempre para una taza.
     */
    @Override
    public boolean isCup() {
        return true;
    }

    /**
     * Devuelve la prioridad usada al ordenar elementos del mismo tamaño.
     * @return prioridad de orden de la taza.
     */
    @Override
    public int getOrderPriority() {
        return ORDER_PRIORITY;
    }

    /**
     * Devuelve el tipo del elemento.
     * @return tipo cup.
     */
    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * Devuelve la altura lógica del objeto en unidades.
     * @return altura lógica de la taza.
     */
    @Override
    public int getHeightUnits() {
        return size;
    }

    /**
     * Regresa el piso interno de la taza.
     * @return coordenada Y del piso interno.
     */
    @Override
    public int getInnerFloorY() {
        return getY() - UNIT;
    }

    /**
     * Regresa el tamaño de la taza.
     * @return tamaño de la taza.
     */
    @Override
    public int getSize() {
        return size;
    }
}