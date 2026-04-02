package tests;
import tower.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * The test class TowerContestAcceptanceTest.
 *
 * @author  Yeray Gauchetá
 * @version 2
 */
public class TowerContestAcceptanceTest {

    public static void main(String[] args) {
        TowerContest contest = new TowerContest();

        System.out.println("====================================");
        System.out.println("ACEPTACION CICLO 3 - TOWER CONTEST");
        System.out.println("====================================");

        testSolve(contest, 4, 7);    // altura minima
        testSolve(contest, 4, 16);   // altura maxima
        testSolve(contest, 4, 9);    // altura intermedia valida
        testSolve(contest, 4, 14);   // caso imposible

        pause(2000);

        System.out.println();
        System.out.println("====================================");
        System.out.println("SIMULACION VISUAL");
        System.out.println("====================================");

        System.out.println("Simulando n=4, h=9");
        contest.simulate(4, 9);

        pause(5000);

        System.out.println("====================================");
        System.out.println("FIN DE LA PRUEBA DE ACEPTACION");
        System.out.println("====================================");
    }

    private static void testSolve(TowerContest contest, int n, int h) {
        String solution = contest.solve(n, h);

        System.out.println("------------------------------------");
        System.out.println("Caso: n = " + n + ", h = " + h);
        System.out.println("solve(" + n + ", " + h + ") -> " + solution);

        if (!solution.equals("impossible")) {
            Tower tower = new Tower(800, h);
            String[] parts = solution.split(" ");

            for (String part : parts) {
                tower.pushCup(Integer.parseInt(part));
            }

            System.out.println("Altura obtenida al construir la torre: " + tower.height());
            System.out.println("Elementos usados:");

            String[][] items = tower.stackingItems();
            for (int i = 0; i < items.length; i++) {
                System.out.println(i + ": " + items[i][0] + " " + items[i][1]);
            }
        }

        System.out.println("------------------------------------");
    }

    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
