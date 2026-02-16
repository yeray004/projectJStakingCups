/**
 * Clase que representa una tapa.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * 
 * @version 1.0
 */
public class Lid extends StackItem {

    private static final int HEIGHT = 1;

    /**
     * Constructor for objects of class Lid
     * @param id Identificador único de la tapa.
     * @param color Color de la tapa.
     * @param x Posición horizontal.
     * @param y Posición vertical.
     */
    public Lid(String id, String color, int x, int y){
        super(id, color, x, y); // Línea de código implementada por Inteligencia artificial
    }

    /**
     * Devuelve la altura de la tapa.
     * 
     * @return La altura constante de la tapa
     */
    @Override
    public int getHeight() {
        return HEIGHT;
    }
}