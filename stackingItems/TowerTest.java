import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TowerTest {
    private Tower t;

    @BeforeEach
    public void setUp() {
        t = new Tower(800, 10);
    }
    
    // --- PRUEBAS DE TORRE COMPLEJA ---
    @Test
    public void testBuilding() {
        t.pushCup(5);
        t.pushCup(3);
        t.pushCup(3);
        t.pushCup(5);
        t.pushLid(1);
        t.pushLid(3);
        t.pushCup(1);
        t.pushCup(1);
        t.pushLid(5);
        assertTrue(t.ok(), "ok() debería ser true tras completar el armado.");
    }
    
    // --- PRUEBAS DE INSERCIÓN (Push) ---
    @Test
    public void testPushCup() {
        t.pushCup(10);
        assertEquals(1, t.stackingItems().length, "Debería haber 1 elemento.");
        assertTrue(t.ok(), "ok() debería ser true tras push.");
    }

    @Test
    public void testPushLid() {
        t.pushLid(10);
        assertEquals(1, t.stackingItems().length, "Debería haber 1 elemento.");
        assertTrue(t.ok(), "ok() debería ser true tras push.");
    }

    // --- PRUEBAS DE EXTRACCIÓN (Pop) ---
    @Test
    public void testPopCupSuccess() {
        t.pushCup(5);
        t.popCup();
        assertEquals(0, t.stackingItems().length, "La torre debería quedar vacía.");
    }

    @Test
    public void testPopCupFail() {
        t.pushLid(5); // Ponemos una tapa arriba
        t.popCup();   // Intentamos hacer pop de taza
        assertFalse(t.ok(), "ok() debería ser false al intentar popCup sobre una Lid.");
    }

    @Test
    public void testPopLidSuccess() {
        t.pushLid(5);
        t.popLid();
        assertEquals(0, t.stackingItems().length);
    }

    // --- PRUEBAS DE ELIMINACIÓN ESPECÍFICA (Remove) ---
    @Test
    public void testRemoveCupValid() {
        t.pushCup(5); // ID 1
        t.removeCup(1);
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void testRemoveInvalidID() {
        t.pushCup(5);
        t.removeCup(99); // ID inexistente
        assertFalse(t.ok(), "Debe fallar con un ID inexistente.");
    }

    // --- PRUEBAS DE ALTURA Y FÍSICA (Height) ---
    @Test
    public void testHeightNesting() {
        t.pushCup(10);
        t.pushCup(5);  // Dentro de la de 10
        assertEquals(10, t.height(), "La altura debería ser 10 (la de la base).");
    }

    @Test
    public void testHeightSealing() {
        t.pushCup(5);
        t.pushLid(5); // Sella la de 5
        assertEquals(6, t.height(), "La altura debería ser 5 + 1 de la tapa.");
    }

    // --- PRUEBAS DE UNIFICACIÓN (lidedCups) ---
    @Test
    public void testLidedCupsReport() {
        t.pushCup(5); // ID 1
        t.pushLid(5); // ID 2 -> Se sellan
        t.pushCup(3); // ID 3 -> Sin sello
        
        int[] sealed = t.lidedCups();
        assertEquals(1, sealed.length);
        assertEquals(1, sealed[0], "El ID sellado debe ser el 1.");
    }

    // --- PRUEBAS DE REORDENAMIENTO (Order/Reverse) ---
    @Test
    public void testOrderTower() {
        t.pushCup(2);
        t.pushCup(8);
        t.pushCup(5);
        t.orderTower();
        String[][] matrix = t.stackingItems();
        assertEquals("8", matrix[0][1], "El más grande debe estar en la base.");
        assertEquals("2", matrix[2][1], "El más pequeño debe estar arriba.");
    }

    @Test
    public void testReverseTower() {
        t.pushCup(10);
        t.pushCup(5);
        t.reverseTower();
        String[][] matrix = t.stackingItems();
        assertEquals("5", matrix[0][1], "El orden de la lista debe invertirse.");
    }

    // --- PRUEBAS DE REPORTE (stackingItems) ---
    @Test
    public void testStackingItemsMatrix() {
        t.pushCup(7);
        String[][] res = t.stackingItems();
        assertEquals("Cup", res[0][0]);
        assertEquals("7", res[0][1]);
    }

    // --- PRUEBAS DE VISIBILIDAD ---
    @Test
    public void testVisibilityMethods() {
        t.pushCup(5);
        t.makeInvisible();
        assertTrue(t.ok());
        t.makeVisible();
        assertTrue(t.ok());
    }

    // --- PRUEBAS DE ESTADO (ok) ---
    @Test
    public void testOkStatus() {
        t.pushCup(5);
        assertTrue(t.ok(), "Debería ser true tras una operación válida.");
        t.removeCup(500); // Forzar error
        assertFalse(t.ok(), "Debería ser false tras un error.");
    }
}