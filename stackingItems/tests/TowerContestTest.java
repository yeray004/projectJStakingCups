package tests;
import tower.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TowerContestTest.
 *
 * @author  Yeray Gauchetá
 * @version 2
 */
public class TowerContestTest {
    private TowerContest contest;
    @BeforeEach
    public void setUp() {
        contest = new TowerContest();
    }

    @Test
    public void shouldReturnSingleCupForSmallestValidCase() {
        assertEquals("1", contest.solve(1, 1));
    }

    @Test
    public void shouldReturnImpossibleWhenHeightIsBelowMinimum() {
        assertEquals("impossible", contest.solve(4, 6));
    }

    @Test
    public void shouldReturnImpossibleWhenHeightIsAboveMaximum() {
        assertEquals("impossible", contest.solve(4, 17));
    }

    @Test
    public void shouldReturnDescendingOrderForMinimumHeight() {
        assertEquals("7 5 3 1", contest.solve(4, 7));
    }

    @Test
    public void shouldReturnAscendingOrderForMaximumHeight() {
        assertEquals("1 3 5 7", contest.solve(4, 16));
    }
    
    @Test
    public void shouldReturnValidSolutionForSampleIntermediateHeight() {
        assertValidSolution(4, 9);
    }

    @Test
    public void shouldReturnValidSolutionForAnotherIntermediateHeight() {
        assertValidSolution(5, 15);
    }
    
    // Alturas intermedias
    //método elaborado con apoyo de inteligencia artificial
    private void assertValidSolution(int n, int h) {
        String solution = contest.solve(n, h);
    
        assertNotEquals("impossible", solution, "No debería ser imposible.");
        String[] parts = solution.split(" ");
        assertEquals(n, parts.length, "La solución debe usar todas las tazas.");
    
        boolean[] seen = new boolean[n + 1];
        Tower tower = new Tower(800, h);
    
        for (String part : parts) {
            int value = Integer.parseInt(part);
    
            assertTrue(value % 2 != 0, "Todas las alturas deben ser impares.");
    
            int cupNumber = (value + 1) / 2;
            assertTrue(cupNumber >= 1 && cupNumber <= n, "La taza debe existir.");
            assertFalse(seen[cupNumber], "No se debe repetir ninguna taza.");
    
            seen[cupNumber] = true;
            tower.pushCup(value);
        }
    
        for (int i = 1; i <= n; i++) {
            assertTrue(seen[i], "Falta la taza " + i + " en la solución.");
        }
    
        assertEquals(h, tower.height(), "La torre construida debe tener la altura pedida.");
    }
}
