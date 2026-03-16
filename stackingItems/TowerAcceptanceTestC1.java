public class TowerAcceptanceTestC1 {

    public static void main(String[] args) {
        Tower tower = new Tower(800, 20);

        tower.makeVisible();
        logState("Torre vacía", tower);

        pause(1000);
        tower.pushCup(7);
        logState("Después de pushCup(7)", tower);

        pause(1000);
        tower.pushCup(5);
        logState("Después de pushCup(5)", tower);

        pause(1000);
        tower.pushCup(3);
        logState("Después de pushCup(3)", tower);

        pause(1000);
        tower.pushLid(3);
        logState("Después de pushLid(3)", tower);

        pause(1000);
        tower.pushLid(7);
        logState("Después de pushLid(7)", tower);

        pause(1000);
        tower.reverseTower();
        logState("Después de reverseTower()", tower);

        pause(1000);
        tower.orderTower();
        logState("Después de orderTower()", tower);

        pause(1000);
        tower.popLid();
        logState("Después de popLid()", tower);

        pause(1000);
        tower.popCup();
        logState("Después de popCup()", tower);
        
        pause(1000);
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

    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // ciclo 2
    
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
}