package tower;
import shapes.*;
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
    private int idCounter;
    private int topPixel;
    private int colorIndex;
    private boolean ok;
    private boolean visible;
    
    /**
     * Constructor general de la clase Tower.
     * @param width ancho de la torre
     * @param maxHeight altura máxima
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
     * Construye una torre inicial con la cantidad de tazas indicada.
     * Las tazas se crean sin tapas y con tamaños impares crecientes.
     * @param cups cantidad de tazas que tendrá la torre inicial.
     */
    public Tower(int cups) {
        this(800, cups <= 0 ? 1 : (2 * cups) - 1); //ternario que cumple el condicional del ejercicio
    
        int centerX = width / 2;
    
        for (int number = cups; number >= 1; number--) {
            int size = (2 * number) - 1;
            String color = CUP_COLORS[colorIndex % CUP_COLORS.length];
    
            StackItem newCup = new Cup(idCounter, number, color, centerX, FLOOR_Y, size);
            items.add(newCup);
    
            idCounter++;
            colorIndex++;
        }
    
        refreshTower();
        ok = true;
    }

    /**
     * Añade un nuevo elemento de tipo taza a la torre con su tamaño.
     * @param i tamaño del objeto.
     */
    public void pushCup(int i) {
        int centerX = width / 2;
        String color = CUP_COLORS[colorIndex % CUP_COLORS.length];
        StackItem newCup = new Cup(idCounter, i, color, centerX, FLOOR_Y, i);

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
        StackItem newLid = new Lid(idCounter, i, findColorForLid(i), centerX, FLOOR_Y, i);

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
     * Intercambia la posición de dos objetos de la torre
     * identificados por su tipo y número lógico.
     * @param o1 datos del primer objeto.
     * @param o2 datos del segundo objeto.
     */
    public void swap(String[] o1, String[] o2) {
        StackItem firstItem = findItem(o1);
        StackItem secondItem = findItem(o2);
    
        if (firstItem == null || secondItem == null || firstItem == secondItem) {
            ok = false;
            return;
        }
    
        int firstIndex = items.indexOf(firstItem);
        int secondIndex = items.indexOf(secondItem);
        
        if (firstIndex == -1 || secondIndex == -1) {
            ok = false;
            return;
        }
        
        swapItems(firstIndex,secondIndex);
        refreshTower();
        ok = true;
    }
    
    /**
     * Reorganiza la torre para dejar cada taza seguida por su tapa cuando ambas existan dentro de la estructura.
     */
    public void cover() {
        ArrayList<StackItem> coveredItems = new ArrayList<>();
        ArrayList<StackItem> remainingItems = new ArrayList<>(items);
        boolean coveredAnyCup = false;
    
        for (StackItem item : items) {
            if (item.isCup()) {
                coveredItems.add(item);
                remainingItems.remove(item);
    
                StackItem lid = findAvailableLid(item.getNumber(), remainingItems);
                if (lid != null) {
                    coveredItems.add(lid);
                    remainingItems.remove(lid);
                    coveredAnyCup = true;
                }
            }
        }
    
        if (!coveredAnyCup) {
            ok = false;
            return;
        }
    
        coveredItems.addAll(remainingItems);
        items = coveredItems;
        refreshTower();
        ok = true;
    }
    
    /**
     * Calcula la altura total actual de la torre sumando los items.
     * @return La suma de las alturas.
     */
    public int height(){
        return (FLOOR_Y - topPixel) / StackItem.UNIT;
    }
    
    /**
     * Retorna los números de todas las tazas que tienen una tapa.
     * @return Arreglo de enteros con los IDs de las tazas selladas.
     */
    public int[] lidedCups(){
        ArrayList<Integer> sealedCupNumbers = new ArrayList<>();

        for (StackItem item : items) {
            if (item.isSealedCup()) {
                sealedCupNumbers.add(item.getNumber());
            }
        }

        Collections.sort(sealedCupNumbers);
        int[] result = new int[sealedCupNumbers.size()];

        for (int i = 0; i < sealedCupNumbers.size(); i++) {
            result[i] = sealedCupNumbers.get(i);
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
            matrix[i][1] = String.valueOf(item.getNumber());
        }
        return matrix;
    }
    
    /**
     * Consulta un intercambio de dos objetos que reduzca la altura actual de la torre sin modificar su estado final.
     * @return una matriz con los datos de los dos objetos a intercambiar,o una matriz vacía si no existe un intercambio que reduzca la altura.
     */
    public String[][] swapToReduce() {
        int currentHeight = height();
        int bestHeight = currentHeight;
        String[][] bestSwap = new String[0][0];
        
        if (items.size() < 2) {
            ok = false;
            return bestSwap;
        }
    
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                StackItem firstItem = items.get(i);
                StackItem secondItem = items.get(j);
    
                swapItems(i, j);
                refreshTower();
    
                int newHeight = height();
                if (newHeight < bestHeight) {
                    bestHeight = newHeight;
                    bestSwap = new String[][]{
                        itemData(firstItem),
                        itemData(secondItem)
                    };
                }
    
                swapItems(i, j);
                refreshTower();
            }
        }
    
        ok = bestSwap.length != 0;
        return bestSwap;
    }
    
    /**
     * Hace visibles todos los elementos de la torre.
     */
    public void makeVisible(){
        makeInvisible();

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
        for (StackItem item : items) {
            item.makeInvisible();
        }
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
    //Sobrecarga de findItem
    /**
     * Busca un objeto específico en la torre dado su Id.
     * @param id El identificador que se busca.
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
     * Busca un objeto específico en la torre dado su Id y número.
     * @param id El tipo de objeto que busca.
     * @param number El número busca.
     * @return El objeto StackItem si lo encuentra, o null si no.
     */
    private StackItem findItem(String type, int number) {
        for (StackItem item : items) {
            if (item.getTypeName().equals(type) && item.getNumber() == number) {
                return item;
            }
        }
        return null;
    }
    /**
     * Busca por un arreglo String[] que después se descompone en tipo + número.
     * @param data recibe un arreglo de texto con dos valores.
     * @return El objeto StackItem si lo encuentra, o null si no.
     */
    private StackItem findItem(String[] data) {
        if (data == null || data.length != 2) {
            return null;
        }
    
        int number;
        try {
            number = Integer.parseInt(data[1]);
        } catch (NumberFormatException e) {
            return null;
        }
    
        return findItem(data[0], number);
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
        for (StackItem lid : items) {
            if (lid.isLid() && lid.getPartnerId() == -1) {
                for (StackItem cup : items) {
                    if (cup.isCup() && cup.getPartnerId() == -1 && lid.getNumber() == cup.getNumber()) {
                        int rimY = cup.getY() - (cup.getSize() * StackItem.UNIT);
                        if (lid.getY() == rimY) {
                            lid.setPartnerId(cup.getId());
                            cup.setPartnerId(lid.getId());
                            lid.changeColor(cup.getColor());
                            break;
                        }
                    }
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
    private String findColorForLid(int number) {
        for (int i = items.size() - 1; i >= 0; i--) {
            StackItem item = items.get(i);
            if (item.isCup() && item.getNumber() == number) {
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
        makeInvisible();
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
    // Ciclo 2
    /**
     * Busca una tapa disponible con el número indicado dentro de una lista de candidatos.
     * @param number número "lógico" que debe tener la tapa.
     * @param candidates lista de objetos donde se realizará la búsqueda.
     * @return la tapa encontrada o null si no existe.
     */
    private StackItem findAvailableLid(int number, ArrayList<StackItem> candidates) {
        for (StackItem item : candidates) {
            if (item.isLid() && item.getNumber() == number) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Intercambia dos objetos dentro de la lista interna de la torre.
     * @param o1 posición del primer objeto.
     * @param o2 posición del segundo objeto.
     */
    private void swapItems(int o1, int o2) {
        StackItem temporary = items.get(o1);
        items.set(o1, items.get(o2));
        items.set(o2, temporary);
    }
    
    /**
     * Construye la referencia pública de un objeto usando su tipo y número.
     * @param item objeto del cual se obtendrán los datos.
     * @return arreglo con el tipo y el número del objeto.
     */
    private String[] itemData(StackItem item) {
        return new String[]{
            item.getTypeName(),
            String.valueOf(item.getNumber())
        };
    }
    // CICLO 4
    /**
     * Inserta y deja caer cualquier elemento personalizado en la torre.
     */
    public void pushItem(StackItem item) {
        int targetY = item.calculateY(items, items.size(), topPixel, FLOOR_Y);
        item.move(width / 2, targetY);
        items.add(item);
        
        // Recalcular el punto más alto de la torre
        topPixel = FLOOR_Y;
        for (StackItem s : items) {
            if (s.getTop() < topPixel) {
                topPixel = s.getTop();
            }
        }
        
        if (visible) {
            item.makeVisible();
        }
    }
} 
