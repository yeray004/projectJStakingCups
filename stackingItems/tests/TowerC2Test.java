package tests;
import tower.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TowerC2Test.
 *
 * @author Yeray Guachetá
 * @author Andrés Sotelo
 * @version 1
 */
public class TowerC2Test {
    
    private void assertStackEquals(Tower tower, String[][] expected) {
        String[][] actual = tower.stackingItems();

        assertEquals(expected.length, actual.length, "La cantidad de elementos apilados no coincide.");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], "Diferencia en la posicion " + i + ".");
        }
    }
    // TOWER(CUPS)
    @Test
    public void shouldCreateTowerWithRequestedNumberOfCups() {
        Tower tower = new Tower(4);

        assertStackEquals(tower, new String[][]{
            {"cup", "4"},
            {"cup", "3"},
            {"cup", "2"},
            {"cup", "1"}
        });
    }

    @Test
    public void shouldCreateTowerWithoutLids() {
        Tower tower = new Tower(4);

        assertArrayEquals(new int[]{}, tower.lidedCups());
    }

    @Test
    public void shouldCreateTowerWithHeightOfLargestCup() {
        Tower tower = new Tower(4);

        assertEquals(7, tower.height());
    }

    @Test
    public void shouldCreateEmptyTowerWhenCupCountIsZero() {
        Tower tower = new Tower(0);

        assertStackEquals(tower, new String[][]{});
        assertEquals(0, tower.height());
    }

    @Test
    public void shouldCreateTowerWithOneCup() {
        Tower tower = new Tower(1);

        assertStackEquals(tower, new String[][]{
            {"cup", "1"}
        });
        assertEquals(1, tower.height());
    }
    // SWAP
    @Test
    public void shouldSwapTwoExistingCups() {
        Tower tower = new Tower(4);
    
        tower.swap(new String[]{"cup", "4"}, new String[]{"cup", "2"});
    
        assertStackEquals(tower, new String[][]{
            {"cup", "2"},
            {"cup", "3"},
            {"cup", "4"},
            {"cup", "1"}
        });
    }
    
    @Test
    public void shouldNotSwapWhenFirstItemDoesNotExist() {
        Tower tower = new Tower(4);
    
        tower.swap(new String[]{"lid", "4"}, new String[]{"cup", "2"});
    
        assertStackEquals(tower, new String[][]{
            {"cup", "4"},
            {"cup", "3"},
            {"cup", "2"},
            {"cup", "1"}
        });
    }
    
    @Test
    public void shouldNotSwapWhenSecondItemDoesNotExist() {
        Tower tower = new Tower(4);
    
        tower.swap(new String[]{"cup", "4"}, new String[]{"lid", "2"});
    
        assertStackEquals(tower, new String[][]{
            {"cup", "4"},
            {"cup", "3"},
            {"cup", "2"},
            {"cup", "1"}
        });
    }
    
    @Test
    public void shouldNotSwapSameItemWithItself() {
        Tower tower = new Tower(4);
    
        tower.swap(new String[]{"cup", "3"}, new String[]{"cup", "3"});
    
        assertStackEquals(tower, new String[][]{
            {"cup", "4"},
            {"cup", "3"},
            {"cup", "2"},
            {"cup", "1"}
        });
    }
    // COVER
    @Test
    public void shouldCoverCupsThatHaveTheirLidsInTower() {
        Tower tower = new Tower(800, 20);
    
        tower.pushLid(3);
        tower.pushCup(5);
        tower.pushLid(5);
        tower.pushCup(3);
    
        tower.cover();
    
        assertStackEquals(tower, new String[][]{
            {"cup", "5"},
            {"lid", "5"},
            {"cup", "3"},
            {"lid", "3"}
        });
        assertArrayEquals(new int[]{3, 5}, tower.lidedCups());
    }
    
    @Test
    public void shouldKeepUnmatchedItemsWhileCoveringExistingPairs() {
        Tower tower = new Tower(800, 20);
    
        tower.pushLid(1);
        tower.pushCup(5);
        tower.pushLid(5);
        tower.pushCup(3);
    
        tower.cover();
    
        assertStackEquals(tower, new String[][]{
            {"cup", "5"},
            {"lid", "5"},
            {"cup", "3"},
            {"lid", "1"}
        });
        assertArrayEquals(new int[]{5}, tower.lidedCups());
    }
    
    @Test
    public void shouldNotChangeTowerWhenNoCupCanBeCovered() {
        Tower tower = new Tower(800, 20);
    
        tower.pushCup(5);
        tower.pushLid(3);
    
        tower.cover();
    
        assertStackEquals(tower, new String[][]{
            {"cup", "5"},
            {"lid", "3"}
        });
        assertFalse(tower.ok());
    }
    //SWAP TO REDUCE
    private void assertSwapEquals(String[][] expected, String[][] actual) {
        assertEquals(expected.length, actual.length, "La cantidad de elementos del intercambio no coincide.");
    
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], "Diferencia en la posicion " + i + ".");
        }
    }
    
    @Test
    public void shouldReturnSwapThatReducesHeight() {
        Tower tower = new Tower(800, 20);
    
        tower.pushCup(3);
        tower.pushCup(7);
    
        String[][] result = tower.swapToReduce();
    
        assertSwapEquals(new String[][]{
            {"cup", "3"},
            {"cup", "7"}
        }, result);
    }
    
    @Test
    public void shouldReturnEmptyMatrixWhenNoSwapReducesHeight() {
        Tower tower = new Tower(4);
    
        String[][] result = tower.swapToReduce();
    
        assertEquals(0, result.length);
    }
    
    @Test
    public void shouldNotModifyTowerWhenConsultingSwapToReduce() {
        Tower tower = new Tower(800, 20);
    
        tower.pushCup(3);
        tower.pushCup(7);
    
        tower.swapToReduce();
    
        assertStackEquals(tower, new String[][]{
            {"cup", "3"},
            {"cup", "7"}
        });
    }
}
