
/**
 * Write a description of class StackItem here.
 * 
 * @author Yeray Guachetá
 * @version 1
 */
public abstract class StackItem{
    
    private String id;
    private String color;
    private int x;
    private int y;
    private boolean isVisible;
    private Rectangle shape;

    /**
     * Constructor de la clase Cup
     * @param id El identificador único de la taza
     * @param color El color de la taza
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     */
    public StackItem(String id, String color, int x, int y){
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;
        this.isVisible = false;
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
     * Le dice al rectángulo que se muestre
     */
    public void makeVisible(){
        this.isVisible = true;
        shape.makeVisible();
    }

    /**
     * Le dice al rectángulo que se oculte
     */
    public void makeInvisible(){
        this.isVisible = false;
        shape.makeVisible();
    }
    
    public String getId() {
        return id;
    }
    
    public abstract int getHeight();
}