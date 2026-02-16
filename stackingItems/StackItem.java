
/**
 * Write a description of class StackItem here.
 * 
 * @author Yeray Guachetá
 * @version 1
 */
public abstract class StackItem{
    
    private int id;
    private String color;
    private int x;
    private int y;
    private Rectangle shape;

    /**
     * Constructor de la clase Cup
     * @param id El identificador único de la taza
     * @param color El color de la taza
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     */
    public StackItem(int id, String color, int x, int y){
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;
        this.shape = new Rectangle();
    }

    /**
     * Mueve el objeto a una nueva posición.
     */
    public void move(int newX, int newY){
        this.x = newX;
        this.y = newY;
    }

    /**
     * Le dice al objeto que se muestre.
     */
    public void makeVisible(){
        shape.makeVisible();
    }

    /**
     * Le dice al objeto que se oculte.
     */
    public void makeInvisible(){
        shape.makeInvisible();
    }
    /**
     * Regresa el Id del objeto.
     */    
    public int getId() {
        return id;
    }
    
    public abstract int getHeight();
}