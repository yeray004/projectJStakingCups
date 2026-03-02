import java.util.*;
/**
 * Write a description of class StackItem here.
 * 
 * @author Yeray Guachetá
 * @version 1
 */
public abstract class StackItem{
    
    private int id;
    private int x;
    private int y;
    private int partnerId;
    private String color;
    public ArrayList<Rectangle> shapes;

    /**
     * Constructor de la clase Cup
     * @param id El identificador único de la taza
     * @param color El color de la taza
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     */
    public StackItem(int id, String color, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.partnerId=-1;
        this.shapes = new ArrayList<>();
        this.color= color;
    }

    /**
     * Mueve el objeto a una nueva posición.
     */
    public void move(int newX, int newY){
        int deltaX = newX - this.x;
        int deltaY = newY - this.y;
        for (Rectangle s: shapes){
            s.moveHorizontal(deltaX);
            s.moveVertical(deltaY);
        }
        this.x = newX;
        this.y = newY;
    }

    /**
     * Le dice al objeto que se muestre.
     */
    public void makeVisible(){
        for(Rectangle s : shapes){
            s.makeVisible();
        }
    }

    /**
     * Le dice al objeto que se oculte.
     */
    public void makeInvisible(){
        for(Rectangle s : shapes){
            s.makeInvisible();
        }
    }
    /**
     * Regresa el Id del objeto.
     */    
    public int getId() {
        return id;
    }
    
    public abstract int getSize();
    
    public int getY(){
        return y;
    }
    
    public void setPartnerId(int id){
        this.partnerId = id;
    }
    
    public int getPartnerId() { 
        return this.partnerId;
    }
    public String getColor(){
        return this.color;
    }
}