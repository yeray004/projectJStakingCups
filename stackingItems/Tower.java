import java.util.ArrayList;
import java.util.Collections;
/**
 * Clase principal que gestiona la torre de tazas y tapas.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * @version 1.0
 */
public class Tower{
    private ArrayList<StackItem> items;
    private int width;
    private int maxHeight;
    private boolean ok;
    
    /**
     * Constructor for objects of class Tower
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void pushCup(int i){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void popCup(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void removeCup(int i){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void pushLid(int i){
        
    }
    
        /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void popLid(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void removeLid(int i){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void orderTower(){
        
    }
    
        /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void reverseTower(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int height(){
        return 1;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int[] lidedCups(int i){
        return new int[] { 1, 2, 3 }; //sintaxis
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String[][] stackingItems(){
        return new String[][] {
            {"A", "B"},
            {"C", "D"} //sintaxis de ejemplo
        };
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void makeVisible(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void makeInvisible(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void exit(){
        
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public boolean ok(){
        return this.ok;
    }
    
    /**
     * Calcula la altura total actual de la torre sumando los items.
     * @return La suma de las alturas.
     */
    private int getTotalHeight() {
        int total = 0;
        for (StackItem item : items) {
            total += item.getHeight();
        }
        return total;
    }

    /**
     * Verifica si un identificador ya existe en la lista.
     * @param id El identificador a buscar.
     * @return true si ya existe, false si no.
     */
    private boolean idExist(int id) {
        return findItem(id) != null;
    }

    /**
     * Busca un objeto específico en la torre dado su Id.
     * @param id El identificador que se buca.
     * @return El objeto StackItem si lo encuentra, o null si no.
     */
    private StackItem findItem(int id) {
        for (StackItem item : items) {
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    /**
     * Reorganiza visualmente la torre. ()
     * Recalcula las posiciones (x, y) de cada elemento para que queden apilados.
     */
    private void refreshTower() {
        int y = 0;
        int x = this.width / 2; //calcula el centro del tablero
        for (StackItem item : items) {
            item.makeInvisible();
            item.move(x, y);
            item.makeVisible();
            
            y += item.getHeight();
        }
    }
}