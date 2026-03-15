import java.util.*;
import javax.swing.JOptionPane;

/**
 * Clase principal que gestiona la torre de tazas y tapas.
 * 
 * @author Andrés Sotelo
 * @author Yeray Guachetá
 * @support Assisted by Gemini (Google AI) - March 2026
 * @version 4.0
 */
public class Tower{
    private static final int FLOOR_Y = 600;
    private static final String DEFAULT_LID_COLOR = "red";
    private static final String[] CUP_COLORS = {"blue", "green", "yellow", "magenta", "red", "black"};
    
    private ArrayList<StackItem> items;
    private int width;
    private int maxHeight;
    private boolean ok;
    private boolean visible;
    private int idCounter;
    private int topPixel;
    private int colorIndex;
    
    /**
     * Constructor for objects of class Tower
     */ 
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        items = new ArrayList<>();
        ok = true;
        visible = false;
        idCounter = 1;
        topPixel = FLOOR_Y;
        colorIndex = 0;
    }

    /**
     * Añade un nuevo elemento de tipo taza a la torre con su tamaño.
     * @param i tamaño del objeto.
     */
    public void pushCup(int i) {
        int centerX = width / 2;
        String color = CUP_COLORS[colorIndex % CUP_COLORS.length];
        StackItem newCup = new Cup(idCounter, color, centerX, FLOOR_Y, i);

        if (!canAdd(newCup)) {
            ok = false;
            return;
        }

        items.add(newCup);
        idCounter++;
        colorIndex++;

        refreshTower();
        ok = true;
    }
    
    /**
     * Elimina el último elemento de tipo taza de la torre.
     */
    public void popCup(){
        if (items.isEmpty()) {
            ok = false;
            return;
        }
        StackItem lastItem = items.get(items.size() - 1);
    
        if (!lastItem.isCup()) {
            ok = false;
            return;
        }
    
        removeItemAndPartner(lastItem);
        refreshTower();
        ok = true;
    }
    
    /**
     * Elimina una taza específica de la torre dado su ID.
     * @param i El identificador de la taza a eliminar.
     */
    public void removeCup(int i){
        StackItem item = findItem(i);

        if (item == null || !(item instanceof Cup)) {
            ok = false;
            return;
        }
    
        removeItemAndPartner(item);
        refreshTower();
        ok = true;
    }
    
    /**
     * Añade un nuevo elemento de tipo tapa a la torre con su tamaño.
     * @param i tamaño del objeto.
     */
    public void pushLid(int i){
        int centerX = width / 2;
        StackItem newLid = new Lid(idCounter, findColorForLid(i), centerX, FLOOR_Y, i);

        if (!canAdd(newLid)) {
            ok = false;
            return;
        }

        items.add(newLid);
        idCounter++;

        refreshTower();
        ok = true;
    }
    
    /**
     * Elimina el último elemento de tipo tapa de la torre.
     */
    public void popLid(){
        if (items.isEmpty()) {
            ok = false;
            return;
        }
        StackItem lastItem = items.get(items.size() - 1);
    
        if (!lastItem.isLid()) {
            ok = false;
            return;
        }
    
        removeItemAndPartner(lastItem);
        refreshTower();
        ok = true;
    }
    
    /**
     * Elimina una tapa específica de la torre dado su ID.
     * @param i El identificador de la taza a eliminar.
     */
    public void removeLid(int i){
        StackItem item = findItem(i);

        if (item == null || !item.isLid()) {
            ok = false;
            return;
        }
    
        removeItemAndPartner(item);
        refreshTower();
        ok = true;
    }
    
    /**
     * Ordena los elemento de la torre de mayor a menor. Solo deja en la torre los elementos que caben.
     */ // Método modificado con ayuda de inteligencia artificial
    public void orderTower(){
        ArrayList<ArrayList<StackItem>> groups = buildMoveGroups();
        groups.sort(Comparator
            .comparingInt(this::getGroupSize)
            .reversed()
            .thenComparingInt(this::getGroupPriority));
        reorganizeGroups(groups);
    }
    
    /**
     * Invierte los elementos de la torre
     */ // Método modificado con ayuda de inteligencia artificial
    public void reverseTower(){
        ArrayList<ArrayList<StackItem>> groups = buildMoveGroups();
        Collections.reverse(groups);
        reorganizeGroups(groups);
    }
    
    /**
     * Calcula la altura total actual de la torre sumando los items.
     * @return La suma de las alturas.
     */
    public int height(){
        return (FLOOR_Y - topPixel) / StackItem.UNIT;
    }
    
    /**
     * Retorna los tamaños de todas las tazas que tienen una tapa.
     * @return Arreglo de enteros con los IDs de las tazas selladas.
     */
    public int[] lidedCups(){
        ArrayList<Integer> sealedCupSizes = new ArrayList<>();

        for (StackItem item : items) {
            if (item.isSealedCup()) {
                sealedCupSizes.add(item.getSize());
            }
        }

        Collections.sort(sealedCupSizes);
        int[] result = new int[sealedCupSizes.size()];

        for (int i = 0; i < sealedCupSizes.size(); i++) {
            result[i] = sealedCupSizes.get(i);
        }

        return result;
    }
    
    /**
     * Regresa una lista de los elementos en la torre.
     * @return Arreglo de String con el tipo de objeto y sus IDs.
     */
    public String[][] stackingItems(){
        String[][] matrix = new String[items.size()][2];
        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            matrix[i][0] = item.getTypeName();
            matrix[i][1] = String.valueOf(item.getSize());
        }
        return matrix;
    }
    
    /**
     * Hace visibles todos los elementos de la torre.
     */
    public void makeVisible(){
        hideAllItems();

        if (!fitsInTower(new ArrayList<>(items))) {
            visible = false;
            ok = false;
            return;
        }

        visible = true;
        refreshTower();
        ok = true;
    }
    
    /**
     * Hace invisibles todos los elementos de la torre.
     */
    public void makeInvisible(){
        hideAllItems();
        visible = false;
        ok = true;
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
        if (visible) { //Condición para los tests
            if (ok) {
                JOptionPane.showMessageDialog(null, "Operación realizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null,"La operación no pudo completarse.","Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
        return ok;
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
        int center = width / 2;
        int currentTop = FLOOR_Y;

        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            item.makeInvisible();
            item.clearPartner();

            int newY = item.calculateY(items, i, currentTop, FLOOR_Y);
            item.move(center, newY);
            currentTop = Math.min(currentTop, item.getTop());
        }

        topPixel = currentTop;
        updatePartnerships();

        if (visible) {
            for (StackItem item : items) {
                item.makeVisible();
            }
        }
    }
    
    /**
     * Analiza la torre buscando piezas que deban unificarse.
     */
    private void updatePartnerships() {
        for (StackItem item : items) {
            if (!item.hasPartner()) {
                StackItem partner = item.findPartner(items);
                if (partner != null && !partner.hasPartner()) {
                    item.setPartnerId(partner.getId());
                    partner.setPartnerId(item.getId());
                }
            }
        }
    }
    
    /**
     * Verifica si el item recibido puede ser agregado.
     *
     * @param candidate item que se desea agregar.
     * @return true si se puede agregar.
     */
    private boolean canAdd(StackItem candidate) {
        return isValidNumber(candidate.getSize()) && !existsConflict(candidate);
    }

    /**
     * Indica si un número es válido para el problema.
     *
     * @param size número a validar.
     * @return true si es un número impar positivo.
     */
    private boolean isValidNumber(int size) {
        return size > 0 && size % 2 != 0;
    }

    /**
     * Verifica si ya existe un item del mismo tipo y tamaño.
     *
     * @param candidate item a validar.
     * @return true si ya existe un conflicto del mismo tipo y tamaño.
     */
    private boolean existsConflict(StackItem candidate) {
        for (StackItem item : items) {
            if (item.conflictsWith(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca el color que debe usar una tapa según la taza del mismo tamaño.
     * @param size tamaño de la tapa.
     * @return color que debe usar la tapa.
     */
    private String findColorForLid(int size) {
        for (int i = items.size() - 1; i >= 0; i--) {
            StackItem item = items.get(i);
            if (item.isCup() && item.getSize() == size) {
                return item.getColor();
            }
        }
        return DEFAULT_LID_COLOR;
    }

    /**
     * Elimina un item y su pareja, si existe.
     * @param item item a eliminar.
     */
    private void removeItemAndPartner(StackItem item) {
        int partnerId = item.getPartnerId();
        item.makeInvisible();
        items.remove(item);

        if (partnerId != StackItem.NO_PARTNER) {
            StackItem partner = findItem(partnerId);
            if (partner != null) {
                partner.makeInvisible();
                items.remove(partner);
            }
        }
    }

    /**
     * Construye los bloques de movimiento de la torre.
     * Una taza y su tapa sellada se tratan como un solo bloque.
     * @return bloques de movimiento en el orden actual.
     */
    private ArrayList<ArrayList<StackItem>> buildMoveGroups() {
        ArrayList<ArrayList<StackItem>> groups = new ArrayList<>();
        Set<Integer> processedIds = new HashSet<>();

        for (StackItem item : items) {
            if (processedIds.contains(item.getId())) {
                continue;
            }

            ArrayList<StackItem> group = buildGroupFor(item, processedIds);
            groups.add(group);
        }

        return groups;
    }

    /**
     * Construye el bloque de movimiento de un item específico.
     *
     * @param item item base del bloque.
     * @param processedIds ids ya incluidos en otros bloques.
     * @return bloque de movimiento asociado al item.
     */
    private ArrayList<StackItem> buildGroupFor(StackItem item, Set<Integer> processedIds) {
        ArrayList<StackItem> group = new ArrayList<>();
        StackItem cup = getGroupedCup(item);
        StackItem lid = getGroupedLid(item);

        if (cup != null && lid != null) {
            group.add(cup);
            group.add(lid);
            processedIds.add(cup.getId());
            processedIds.add(lid.getId());
            return group;
        }

        group.add(item);
        processedIds.add(item.getId());
        return group;
    }

    /**
     * Retorna la taza que debe liderar un bloque taza-tapa.
     * @param item item de referencia.
     * @return taza del bloque o null si no existe un bloque sellado.
     */
    private StackItem getGroupedCup(StackItem item) {
        if (item.isCup() && item.hasPartner()) {
            return item;
        }
        if (item.isLid() && item.hasPartner()) {
            StackItem partner = findItem(item.getPartnerId());
            if (partner != null && partner.isCup()) {
                return partner;
            }
        }
        return null;
    }

    /**
     * Retorna la tapa que debe cerrar un bloque taza-tapa.
     * @param item item de referencia.
     * @return tapa del bloque o null si no existe un bloque sellado.
     */
    private StackItem getGroupedLid(StackItem item) {
        if (item.isLid() && item.hasPartner()) {
            return item;
        }
        if (item.isCup() && item.hasPartner()) {
            StackItem partner = findItem(item.getPartnerId());
            if (partner != null && partner.isLid()) {
                return partner;
            }
        }
        return null;
    }

    /**
     * Reorganiza la torre a partir de los bloques recibidos,
     * conservando únicamente los que caben en la torre.
     * @param groups bloques objetivo en el nuevo orden.
     */
    private void reorganizeGroups(ArrayList<ArrayList<StackItem>> groups) {
        hideAllItems();
        ArrayList<StackItem> keptItems = keepOnlyGroupsThatFit(groups);
        items.clear();
        items.addAll(keptItems);
        refreshTower();
        ok = true;
    }

    /**
     * Conserva únicamente los bloques que caben dentro del ancho y la altura.
     * @param groups bloques a evaluar.
     * @return items que finalmente quedan en la torre.
     */
    private ArrayList<StackItem> keepOnlyGroupsThatFit(ArrayList<ArrayList<StackItem>> groups) {
        ArrayList<StackItem> keptItems = new ArrayList<>();

        for (ArrayList<StackItem> group : groups) {
            ArrayList<StackItem> candidate = new ArrayList<>(keptItems);
            candidate.addAll(group);

            if (fitsInTower(candidate)) {
                keptItems = candidate;
            }
        }

        return keptItems;
    }

    /**
     * Verifica si una disposición dada cabe en la torre declarada.
     * @param arrangement disposición a validar.
     * @return true si todos los elementos caben en ancho y altura.
     */
    private boolean fitsInTower(ArrayList<StackItem> arrangement) {
        if (!fitsWidth(arrangement)) {
            return false;
        }

        int center = width / 2;
        int currentTop = FLOOR_Y;

        for (int i = 0; i < arrangement.size(); i++) {
            StackItem item = arrangement.get(i);
            int newY = item.calculateY(arrangement, i, currentTop, FLOOR_Y);
            item.move(center, newY);
            currentTop = Math.min(currentTop, item.getTop());
        }

        return getHeightForTop(currentTop) <= maxHeight;
    }

    /**
     * Verifica si los elementos caben horizontalmente.
     * @param arrangement disposición a validar.
     * @return true si cada elemento cabe dentro del ancho declarado.
     */
    private boolean fitsWidth(ArrayList<StackItem> arrangement) {
        for (StackItem item : arrangement) {
            if ((item.getSize() * StackItem.UNIT) > width) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calcula la altura lógica a partir del punto más alto de la torre.
     * @param top punto más alto de la disposición.
     * @return altura lógica resultante.
     */
    private int getHeightForTop(int top) {
        return (FLOOR_Y - top) / StackItem.UNIT;
    }

    /**
     * Retorna el tamaño principal de un bloque.
     * @param group bloque a consultar.
     * @return tamaño principal del bloque.
     */
    private int getGroupSize(ArrayList<StackItem> group) {
        return group.get(0).getSize();
    }

    /**
     * Retorna la prioridad principal de un bloque.
     * @param group bloque a consultar.
     * @return prioridad principal del bloque.
     */
    private int getGroupPriority(ArrayList<StackItem> group) {
        return group.get(0).getOrderPriority();
    }

    /**
     * Hace invisibles todos los elementos sin cambiar el estado lógico de visibilidad.
     */
    private void hideAllItems() {
        for (StackItem item : items) {
            item.makeInvisible();
        }
    }
} 