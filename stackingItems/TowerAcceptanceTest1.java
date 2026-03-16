public class TowerAcceptanceTest1 {

    public static void main(String[] args) {
        Tower tower = new Tower(4);

        pause(1000);
        tower.makeVisible();
        logState("Torre creada con Tower(4)", tower);

        pause(1500);
        tower.swap(new String[]{"cup", "4"}, new String[]{"cup", "2"});
        logState("Después de swap({cup,4}, {cup,2})", tower);

        pause(5000);
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
}