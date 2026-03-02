/**
 * Clase que representa una tapa.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * 
 * @version 1.0
 */
public class Lid extends StackItem {
    private static final int UNID = 30;
    private static final int HEIGHT = 1;
    private int size;

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
        lid.changeSize(UNID, size * UNID); // 1 de alto, y un poquito más ancha que la taza para que sobresalga
        lid.changeColor(color);
        lid.moveHorizontal(x - ((size* UNID) / 2));
        lid.moveVertical(y - UNID);

        this.shapes.add(lid);
    }

    /**
     * Devuelve la altura de la tapa.
     * 
     * @return La altura constante de la tapa
     */
    @Override
    public int getSize() {
        return size;
    }
}