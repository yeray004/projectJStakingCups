import java.util.*;
/**
 * Clase base para cualquier elemento apilable de la torre.
 * 
 * @author Yeray Guachetá
 * @author Andrés Sotelo
 * @version 1
 */
public abstract class StackItem{
    public static final int UNIT = 30;
    public static final int NO_PARTNER = -1;
    
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
        this.color= color;
        this.x = x;
        this.y = y;
        partnerId=-1;
        shapes = new ArrayList<>();
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
        x = newX;
        y = newY;
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
     * Calcula la posición vertical del item dentro de la torre.
     * Usa polimorfismo para decidir si debe sellar otra pieza o quedar dentro de una taza.
     *
     * @param items elementos actuales de la torre.
     * @param index posición del item dentro de la lista.
     * @param towerTop cima actual de la torre.
     * @param floorY coordenada del piso.
     * @return coordenada Y en la que debe ubicarse el objeto.
     */
    public int calculateY(List<StackItem> items, int index, int towerTop, int floorY) {
        if (index == 0) {
            return floorY;
        }

        StackItem container = null;
        int innerTop = floorY;

        for (int i = index - 1; i >= 0; i--) {
            StackItem candidate = items.get(i);

            if (canSeal(candidate)) {
                return candidate.getSealY();
            }

            if (candidate.canContain(this)) {
                container = candidate;
                innerTop = candidate.getInnerFloorY();

                for (int j = i + 1; j < index; j++) {
                    StackItem innerItem = items.get(j);
                    if (innerItem.getTop() < innerTop) {
                        innerTop = innerItem.getTop();
                    }
                }
                break;
            }
        }

        if (container != null) {
            return innerTop;
        }

        return towerTop;
    }
    /**
     * Busca una pareja válida para el item.
     * Las tazas no buscan pareja; las tapas sí.
     *
     * @param items elementos de la torre.
     * @return pareja encontrada o null si no existe.
     */
    public StackItem findPartner(List<StackItem> items) {
        return null;
    }

    /**
     * Indica si este objeto puede contener a otro.
     *
     * @param other elemento candidato a quedar dentro.
     * @return true si puede contenerlo.
     */
    public boolean canContain(StackItem other) {
        return false;
    }

    /**
     * Indica si este objeto puede sellar a otro.
     *
     * @param other elemento candidato a quedar tapado.
     * @return true si puede sellarlo.
     */
    public boolean canSeal(StackItem other) {
        return false;
    }

    /**
     * Indica si este objeto cabe dentro de una taza dada.
     *
     * @param cup taza contenedora.
     * @return true si cabe dentro de la taza.
     */
    public boolean canBeContainedBy(Cup cup) {
        return getSize() <= cup.getSize() - 2;
    }

    /**
     * Indica si este objeto puede ser sellado por una tapa.
     *
     * @param lid tapa candidata.
     * @return true si puede ser sellado.
     */
    public boolean canBeSealedBy(Lid lid) {
        return false;
    }

    /**
     * Indica si este item representa una taza tapada.
     * @return true si representa una taza sellada.
     */
    public boolean isSealedCup() {
        return false;
    }

    /**
     * Indica si el item corresponde al tipo taza.
     * @return true si es una taza.
     */
    public boolean isCup() {
        return false;
    }

    /**
     * Indica si el item corresponde al tipo tapa.
     * @return true si es una tapa.
     */
    public boolean isLid() {
        return false;
    }

    /**
     * Indica si este item y el recibido corresponden al mismo tipo lógico.
     * @param other item con el que se compara.
     * @return true si ambos son del mismo tipo.
     */
    public boolean hasSameTypeAs(StackItem other) {
        return (isCup() && other.isCup()) || (isLid() && other.isLid());
    }

    /**
     * Indica si este item entra en conflicto con otro al intentar agregarlo.
     * Dos items entran en conflicto si son del mismo tipo y tienen el mismo tamaño.
     * @param other item a evaluar.
     * @return true si no pueden coexistir en la torre.
     */
    public boolean conflictsWith(StackItem other) {
        return hasSameTypeAs(other) && getSize() == other.getSize();
    }

    /**
     * Devuelve la prioridad usada al ordenar elementos del mismo tamaño.
     * Menor número significa mayor prioridad.
     * @return prioridad de orden.
     */
    public abstract int getOrderPriority();

    /**
     * Devuelve el tipo del elemento en minúscula.
     * @return tipo del elemento.
     */
    public abstract String getTypeName();

    /**
     * Devuelve la altura lógica del objeto en unidades.
     * @return altura lógica.
     */
    public abstract int getHeightUnits();

    /**
     * Regresa el tamaño del objeto.
     * @return tamaño del objeto.
     */
    public abstract int getSize();

    /**
     * Regresa el identificador del objeto.
     * @return identificador del objeto.
     */
    public int getId() {
        return id;
    }

    /**
     * Regresa la coordenada Y del objeto.
     * @return posición Y actual.
     */
    public int getY() {
        return y;
    }

    /**
     * Regresa el color del objeto.
     * @return color del objeto.
     */
    public String getColor() {
        return color;
    }

    /**
     * Regresa la coordenada superior del objeto.
     * @return coordenada superior.
     */
    public int getTop() {
        return y - (getHeightUnits() * UNIT);
    }

    /**
     * Regresa la coordenada donde una tapa debe ubicarse para sellar este objeto.
     * @return coordenada Y de sellado.
     */
    public int getSealY() {
        return getTop();
    }

    /**
     * Regresa el piso interior del objeto para ubicar elementos contenidos.
     * @return coordenada Y del piso interior.
     */
    public int getInnerFloorY() {
        return y;
    }

    /**
     * Actualiza el identificador de la pareja del objeto.
     * @param id identificador de la pareja.
     */
    public void setPartnerId(int id) {
        partnerId = id;
    }

    /**
     * Limpia la pareja actual del objeto.
     */
    public void clearPartner() {
        partnerId = NO_PARTNER;
    }

    /**
     * Indica si el objeto tiene pareja.
     * @return true si tiene pareja.
     */
    public boolean hasPartner() {
        return partnerId != NO_PARTNER;
    }

    /**
     * Regresa el identificador de la pareja del objeto.
     * @return id de la pareja o NO_PARTNER.
     */
    public int getPartnerId() {
        return partnerId;
    }
}