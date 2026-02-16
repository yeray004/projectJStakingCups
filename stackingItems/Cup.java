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
    private int height;

    /**
     * Constructor for objects of class Cup
     * @param id Identificador.
     * @param color Color.
     * @param x Posición X.
     * @param y Posición Y.
     * @param height Altura específica de la taza.
     */
    public Cup(String id, String color, int x, int y, int height) {
        super(id, color, x, y); // Línea de código implementada por Inteligencia artificial
        this.height = height;
    }

    /**
     * Regresa la altura de la taza
     * 
     * @return height 
     */
    @Override
    public int getHeight() {
        return this.height;
    }
}