package tests;
import shapes.*;
import tower.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TowerC4Test.
 *
 * @author  Yeray Guachetá
 * @version 1
 */
public class TowerC4Test{
    //PRUEBAS OPENER CUP
    @Test
    public void shouldOpenerCupDestroySmallerItems() {
        ArrayList<StackItem> items = new ArrayList<>();
        Cup smallCup = new Cup(1, 1, "blue", 400, 600, 3);
        items.add(smallCup);

        OpenerCup opener = new OpenerCup(2, 2, "green", 400, 100, 5);
        
        int restingY = opener.calculateY(items, 1, 100, 600);

        //como Opener(5) es mayor que Cup(3), la destruye y sigue hasta el suelo (Y=600)
        assertEquals(600, restingY, "Debe ignorar/destruir el elemento menor y llegar al suelo.");
    }

    @Test
    public void shouldntOpenerCupDestroyLargerItems() {
        ArrayList<StackItem> items = new ArrayList<>();
        Cup largeCup = new Cup(1, 1, "blue", 400, 600, 7);
        items.add(largeCup);

        OpenerCup opener = new OpenerCup(2, 2, "green", 400, 100, 5);
        
        int restingY = opener.calculateY(items, 1, 100, 600);

        //como Opener(5) cabe en la Cup(7), NO la destruye y se apoya dentro
        assertEquals(largeCup.getInnerFloorY(), restingY, "NO debe destruir la taza mayor, debe apoyarse en su interior.");
    }
    //PRUEBAS CRAZY LID
    @Test
    public void shouldCrazyLidNeverSeal() {
        Cup cup = new Cup(1, 1, "blue", 400, 600, 5);
        CrazyLid crazyLid = new CrazyLid(2, 1, "red", 400, 100, 5);
        
        assertFalse(crazyLid.canSeal(cup), "La CrazyLid NO debe sellar su propia taza.");
    }

    @Test
    public void shouldntCrazyLidSealOtherCups() {
        Cup cup = new Cup(1, 2, "blue", 400, 600, 3);
        CrazyLid crazyLid = new CrazyLid(2, 1, "red", 400, 100, 5);
        
        assertFalse(crazyLid.canSeal(cup), "La CrazyLid TAMPOCO debe sellar tazas de otros tamaños.");
    }
}