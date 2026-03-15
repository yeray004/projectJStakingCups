import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TowerTest {
    private Tower tower;

    @BeforeEach
    public void setUp() {
        tower = new Tower(800, 10);
    }
    
    @Test
    public void shouldAddCupWhenNumberIsOddAndUnique() {
        tower.pushCup(5);

        assertStackEquals(new String[][]{
            {"cup", "5"}
        });
    }

    @Test
    public void shouldAddLidWhenNumberIsOddAndUnique() {
        tower.pushLid(5);

        assertStackEquals(new String[][]{
            {"lid", "5"}
        });
    }

    @Test
    public void shouldNotAddCupWhenNumberIsEven() {
        tower.pushCup(4);

        assertStackEquals(new String[][]{});
    }

    @Test
    public void shouldNotAddLidWhenNumberIsEven() {
        tower.pushLid(4);

        assertStackEquals(new String[][]{});
    }

    @Test
    public void shouldNotAddCupWhenSameNumberAlreadyExists() {
        tower.pushCup(5);
        tower.pushCup(5);

        assertStackEquals(new String[][]{
            {"cup", "5"}
        });
    }

    @Test
    public void shouldNotAddLidWhenSameNumberAlreadyExists() {
        tower.pushLid(5);
        tower.pushLid(5);

        assertStackEquals(new String[][]{
            {"lid", "5"}
        });
    }

    @Test
    public void shouldRemoveLastCupWhenTopElementIsCup() {
        tower.pushCup(5);

        tower.popCup();

        assertStackEquals(new String[][]{});
    }

    @Test
    public void shouldRemoveLastLidWhenTopElementIsLid() {
        tower.pushLid(5);

        tower.popLid();

        assertStackEquals(new String[][]{});
    }

    @Test
    public void shouldRemoveSpecificCupById() {
        tower.pushCup(5);
        tower.pushCup(3);

        tower.removeCup(1);

        assertStackEquals(new String[][]{
            {"cup", "3"}
        });
    }

    @Test
    public void shouldRemoveSpecificLidById() {
        tower.pushLid(5);
        tower.pushLid(3);

        tower.removeLid(1);

        assertStackEquals(new String[][]{
            {"lid", "3"}
        });
    }

    @Test
    public void shouldKeepHeightOfLargestCupWhenSmallerCupFitsInside() {
        tower.pushCup(7);
        tower.pushCup(5);

        assertEquals(7, tower.height());
    }

    @Test
    public void shouldIncreaseHeightByOneWhenCupIsClosedWithMatchingLid() {
        tower.pushCup(5);
        tower.pushLid(5);

        assertEquals(6, tower.height());
    }

    @Test
    public void shouldReturnLidedCupNumbersInAscendingOrder() {
        tower.pushCup(5);
        tower.pushLid(5);
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(3);

        assertArrayEquals(new int[]{1, 5}, tower.lidedCups());
    }

    @Test
    public void shouldOrderTowerFromLargestToSmallest() {
        tower.pushCup(1);
        tower.pushCup(7);
        tower.pushCup(5);

        tower.orderTower();

        assertStackEquals(new String[][]{
            {"cup", "7"},
            {"cup", "5"},
            {"cup", "1"}
        });
    }

    @Test
    public void shouldReverseTowerOrder() {
        tower.pushCup(5);
        tower.pushCup(3);
        tower.pushCup(1);
    
        tower.reverseTower();
    
        assertStackEquals(new String[][]{
            {"cup", "1"},
            {"cup", "3"},
            {"cup", "5"}
        });
    }

    @Test
    public void shouldReturnStackingItemsFromBaseToTopInLowerCase() {
        tower.pushCup(7);
        tower.pushLid(7);
        tower.pushCup(5);

        assertStackEquals(new String[][]{
            {"cup", "7"},
            {"lid", "7"},
            {"cup", "5"}
        });
    }

    private void assertStackEquals(String[][] expected) {
        String[][] actual = tower.stackingItems();

        assertEquals(expected.length, actual.length, "La cantidad de elementos apilados no coincide.");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], "Diferencia en la posicion " + i + ".");
        }
    }
}