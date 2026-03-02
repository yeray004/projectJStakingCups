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
    private static final int UNIT = 30; //represents a unit in the program
    private int size;
    private Lid myLid;

    /**
     * Constructor for objects of class Cup
     * @param id Identificador.
     * @param color Color.
     * @param x Posición X.
     * @param y Posición Y.
     * @param height Altura específica de la taza.
     */
    public Cup(int id, String color, int x, int y, int size) {
        super(id, color, x, y); // Línea de código implementada por Inteligencia artificial
        this.size = size;
        this.myLid = null;
        
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
            this.shapes.add(left);
            this.shapes.add(right);
            this.shapes.add(base);
        }
    }

    /**
     * Regresa la altura de la taza
     * 
     * @return height 
     */
    @Override
    public int getSize() {
        return this.size;
    }
}