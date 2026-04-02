package tests;
import tower.*;
/**
 * The test class TowerAcceptanceTestC2.
 *
 * @author  Yeray Gauchetá
 * @version 2
 */
public class TowerAcceptanceTest2C2 {
    public static void main(String[] args) {
        Tower tower = new Tower(800, 20);

        pause(1000);
        tower.makeVisible();
        logState("Torre inicial vacía", tower);

        pause(1000);
        tower.pushLid(3);
        pause(500);
        tower.pushCup(5);
        pause(500);
        tower.pushLid(5);
        pause(500);
        tower.pushCup(3);
        logState("Antes de cover()", tower);

        pause(1500);
        tower.cover();
        logState("Después de cover()", tower);

        pause(1500);
        String[][] suggestion = tower.swapToReduce();
        logSwapSuggestion(suggestion);

        pause(2000);
    }
    // visualización textual generada con inteligencia artificial
    private static void logState(String title, Tower tower) {
        System.out.println("====================================");
        System.out.println(title);
        System.out.println("Height: " + tower.height());

        String[][] items = tower.stackingItems();
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ": " + items[i][0] + " " + items[i][1]);
        }

        int[] lided = tower.lidedCups();
        System.out.print("Lided cups: ");
        for (int i = 0; i < lided.length; i++) {
            System.out.print(lided[i] + " ");
        }
        System.out.println();
        System.out.println("====================================");
    }

    private static void logSwapSuggestion(String[][] suggestion) {
        System.out.println("====================================");
        System.out.println("Sugerencia de swapToReduce():");

        if (suggestion.length == 0) {
            System.out.println("No existe un intercambio que reduzca la altura.");
        } else {
            for (int i = 0; i < suggestion.length; i++) {
                System.out.println(suggestion[i][0] + " " + suggestion[i][1]);
            }
        }

        System.out.println("====================================");
    }

    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
