import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
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
    private int idCounter = 1;
    private int topPixel = 600;
    private String[] cupColors = {"blue", "green", "yellow", "orange", "magenta", "CYAN"};
    private int colorIndex = 0;
    
    /**
     * Constructor for objects of class Tower
     */ 
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
    }

    /**
     * Añade un nuevo elemento de tipo taza a la torre con su tamaño.
     * @param i tamaño del objeto.
     */
    public void pushCup(int i){
        int centerX = this.width / 2;
        int floorY = 600;
        int id = this.idCounter++;
        
        String color = cupColors[colorIndex % cupColors.length];
        colorIndex++;
        
        Cup newCup = new Cup(id, color, centerX, floorY, i);
        items.add(newCup);

        refreshTower();
        this.ok = true;
    }
    
    /**
     * Elimina el último elemento de tipo taza de la torre.
     */
    public void popCup(){
        int index = items.size() - 1;
        StackItem lastItem = items.get(index);
        
        if (items.isEmpty()) {
            this.ok = false;
        }

        if (lastItem instanceof Cup) {
            int pId = lastItem.getPartnerId();
            lastItem.makeInvisible();
            items.remove(index);
            
            if (pId != -1) {
                StackItem partner = findItem(pId);
                if (partner != null) {
                    partner.makeInvisible();
                    items.remove(partner);
                }
            }
            refreshTower();
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Elimina una taza específica de la torre dado su ID.
     * @param i El identificador de la taza a eliminar.
     */
    public void removeCup(int i){
        StackItem item = findItem(i);
        
        if (item != null && item instanceof Cup) {
            item.makeInvisible();
            items.remove(item);
            int pId = item.getPartnerId();
            
            if (pId != -1) {
                StackItem partner = findItem(pId);
                if (partner != null) {
                    partner.makeInvisible();
                    items.remove(partner);
                }
            }
            
            refreshTower();
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Añade un nuevo elemento de tipo tapa a la torre con su tamaño.
     * @param i tamaño del objeto.
     */
    public void pushLid(int i){
        int centerX = this.width / 2;
        int floorY = 600;
        int id = this.idCounter++;

        String color = "red";
        
        for (int j = items.size() - 1; j >= 0; j--) {
            StackItem searchItem = items.get(j);
            if (searchItem instanceof Cup && searchItem.getSize() == i) {
                color = searchItem.getColor(); 
                break;
            }
        }
        
        Lid nuevaTapa = new Lid(id, color, centerX, floorY, i);
        items.add(nuevaTapa);

        refreshTower();
        this.ok = true;
    }
    
    /**
     * Elimina el último elemento de tipo tapa de la torre.
     */
    public void popLid(){
        int index = items.size() - 1;
        StackItem lastItem = items.get(index);
        
        if (items.isEmpty()) {
            this.ok = false;
        }

        if (lastItem instanceof Lid) {
            int pId = lastItem.getPartnerId();
            lastItem.makeInvisible();
            items.remove(index);
            
            if (pId != -1) {
                StackItem partner = findItem(pId);
                if (partner != null) {
                    partner.makeInvisible();
                    items.remove(partner);
                }
            }
            refreshTower();
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Elimina una tapa específica de la torre dado su ID.
     * @param i El identificador de la taza a eliminar.
     */
    public void removeLid(int i){
        StackItem item = findItem(i);
        
        if (item != null && item instanceof Lid) {
            item.makeInvisible();
            items.remove(item);
            int pId = item.getPartnerId();
            
            if (pId != -1) {
                StackItem partner = findItem(pId);
                if (partner != null) {
                    partner.makeInvisible();
                    items.remove(partner);
                }
            }
            
            refreshTower();
            this.ok = true;
        } else {
            this.ok = false;
        }

    }
    
    /**
     * Ordena los elemento de la torre
     */
    public void orderTower(){
        // Línea de código implementada con Inteligencia artificial
        Collections.sort(items, (a, b) -> Integer.compare(b.getSize(), a.getSize()));
        refreshTower();
        this.ok = true;
    }
    
    /**
     * Invierte los elementos de la torre
     */
    public void reverseTower(){
        Collections.reverse(items);
        refreshTower();
        this.ok = true;
    }
    
    /**
     * Calcula la altura total actual de la torre sumando los items.
     * @return La suma de las alturas.
     */
    public int height(){
        return (600 - this.topPixel) / 30;
    }
    
    /**
     * Retorna los identificadores de todas las tazas que tienen una tapa.
     * @return Arreglo de enteros con los IDs de las tazas selladas.
     */
    public int[] lidedCups(){
        ArrayList<Integer> unified = new ArrayList<>();
        for (StackItem item : items) {
            if (item instanceof Cup && item.getPartnerId() != -1) {
                unified.add(item.getId());
            }
        }
        //Vec de int
        int[] res = new int[unified.size()];
        for (int i = 0; i < unified.size(); i++) {
            res[i] = unified.get(i);
        }
        return res;
    }
    
    /**
     * Regresa una lista de los elementos en la torre.
     * @return Arreglo de String con el tipo de objeto y sus IDs.
     */
    public String[][] stackingItems(){
        String[][] matrix = new String[items.size()][2];
        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            matrix[i][0] = (item instanceof Cup) ? "Cup" : "Lid"; //ternario añadido
            matrix[i][1] = String.valueOf(item.getSize());
        }
        return matrix;
    }
    
    /**
     * Hace visibles todos los elementos de la torre.
     */
    public void makeVisible(){
        for (StackItem item : items) {
                item.makeVisible();
        }
        ok= true;
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
     * Finaliza la ejecución del simulador y cierra la ventana de la aplicación.
     */
    public void exit(){
        System.exit(0);
    }
    
    /**
     * Verifica si la acción ejecutada fue exitosa o no.
     */
    public boolean ok(){
        if (this.ok) {
            JOptionPane.showMessageDialog(null, "Operación realizada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "La operación no pudo completarse.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        return this.ok;
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
     * Reorganiza visualmente la torre.
     */
    private void refreshTower() {

        int center = this.width / 2; //centro del canvas
        StackItem anterior = null;
        int maxHigh = 600;
        
        for (StackItem item : items) {
            item.makeInvisible();
            item.setPartnerId(-1);
            
            int newY = calculateY(item, anterior, maxHigh);
            item.move(center, newY);
            
            int objectTop;
            if (item instanceof Lid){
                objectTop = newY - 30;
            } else{
                objectTop = newY - (item.getSize()* 30);
            }
            if (objectTop < maxHigh){
                maxHigh = objectTop;
            }
            
            item.makeVisible();
            anterior = item;
        }
        this.topPixel = maxHigh;
        updatePartnerships();
    }
    
    /**
     * Calcula la coordenada "Y" para un objeto basándose en el anterior.
     * @param actual El nuevo objeto anadido a la torre.
     * @param anterior El último objeto anadido a la torre.
     * @return calculo de la altura (si cabe dentro del último objeto  o se ubica sobre él).
     */
    private int calculateY(StackItem actual, StackItem anterior, int maxHigh) {
        StackItem contenedorReal = null;
        int maxHighInterno = 600;
        int idxActual = items.indexOf(actual);
        if (anterior == null){ return  600; } 
        // For anidado complementado con inteligencia artificial **********************************************
        // Buscamos hacia atrás la taza donde este objeto quepa físicamente
        for (int i = idxActual - 1; i >= 0; i--) {
            StackItem busqueda = items.get(i);
            
            if (actual instanceof Lid && busqueda instanceof Cup && actual.getSize() == busqueda.getSize()) {
                return busqueda.getY() - (busqueda.getSize() * 30);
            }
            if (busqueda instanceof Cup && actual.getSize() <= (busqueda.getSize() - 2)) {
                contenedorReal = busqueda;
                // El "suelo" inicial es el fondo de esta taza
                maxHighInterno = busqueda.getY() - 30; 
                
                // 2. PUNTO DE CAMBIO: Solo sumamos si el objeto 'j' está DENTRO del contenedor 'i'
                for (int j = i + 1; j < idxActual; j++) {
                    StackItem interno = items.get(j);
                    int topeInterno = interno.getY() - ((interno instanceof Lid) ? 30 : interno.getSize() * 30);
                    // Si el objeto interno está más arriba que nuestro suelo actual, actualizamos
                    if (topeInterno < maxHighInterno) {
                        maxHighInterno = topeInterno;
                    }
                }
                break; 
            }
        } // fin de ayuda con IA
        
        // Si encontramos un contenedor (taza), se apila en su fondo sumando lo que ya hay
        if (contenedorReal != null) {
            return maxHighInterno;
        }
    
        // Si es una tapa que sella la taza anterior (tamaños iguales)
        if (anterior instanceof Cup && actual instanceof Lid && actual.getSize() == anterior.getSize()) {
            return anterior.getY() - (anterior.getSize() * 30);
        }
    
        // Si no cabe en nada ni sella, se apoya en la cima de la torre
        return maxHigh; 
    }
    
    /**
     * Analiza la torre buscando piezas que deban unificarse.
     */
    private void updatePartnerships() {
        for (StackItem lid : items) {
            if (lid instanceof Lid && lid.getPartnerId() == -1) {
                for (StackItem cup : items) {
                    // Buscamos una taza libre del mismo tamaño que esté justo debajo
                    if (cup instanceof Cup && cup.getPartnerId() == -1 && lid.getSize() == cup.getSize()) {
                        int rimY = cup.getY() - (cup.getSize() * 30);
                        if (lid.getY() == rimY) {
                            lid.setPartnerId(cup.getId());
                            cup.setPartnerId(lid.getId());
                            break; 
                        }
                    }
                }
            }
        }
    }
} 