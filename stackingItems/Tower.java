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
     * Devuelve la cantidad de items (tazas y tapas) en la torre.
     * @return El número de items en la lista. 
     */
    public int height(){
        return items.size();
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
     * Hace visibles todos los elementos de la torre.
     */
    public void makeVisible(){
        int h = 0;
        h = getTotalHeight();
        if(h <= maxHeight){
            for (StackItem item : items) {
                item.makeVisible();
            }
            ok = true;
        } else{
            ok = false;
        }
    }
    
    /**
     * Hace invisibles todos los elementos de la torre.
     */
    public void makeInvisible(){
        for (StackItem item : items) {
                item.makeInvisible();
        }
        ok= true;
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