package tests;
import tower.*;
import javax.swing.JOptionPane;
import java.util.*;

/**
 * TowerATest - Clase para mostar el comportamiento de los nuevos tipos de tazas y tapas.
 * 
 * @author Yeray Guachetá
 * @version 3
 */
public class TowerATest {

    public static void main(String[] args) {
        try {
            Tower tower = new Tower(800, 600);
            tower.makeVisible();

            //PRUEBA 1: OPENER CUP
            JOptionPane.showMessageDialog(null, "PRUEBA 1: OpenerCup\nColocaremos una taza (3) y su tapa.\nLuego dejaremos caer una OpenerCup (5).");
            
            tower.pushCup(3);
            Thread.sleep(500); 
            tower.pushLid(3); 
            Thread.sleep(1500);
            
            JOptionPane.showMessageDialog(null, "Cayendo OpenerCup. Debe destruir la taza y la tapa (son menores) y caer al piso.");
            
            OpenerCup opener = new OpenerCup(99, 99, "black", 400, 100, 5);
            tower.pushItem(opener);
            Thread.sleep(1500); 
            
            int resp1 = JOptionPane.showConfirmDialog(null, "¿La OpenerCup cayó correctamente al fondo?", "Aceptación 1", JOptionPane.YES_NO_OPTION);
            
            // Limpiamos pantalla
            tower.makeInvisible();
            Thread.sleep(500);

            //PRUEBA 2: CRAZY LID
            Tower tower2 = new Tower(800, 600);
            tower2.makeVisible();

            JOptionPane.showMessageDialog(null, "PRUEBA 2: CrazyLid\nColocaremos una taza (5).\nLuego dejaremos caer una CrazyLid de su mismo tamaño.");
            
            tower2.pushCup(5);
            Thread.sleep(1000);
            
            JOptionPane.showMessageDialog(null, "Cayendo CrazyLid. Debe ignorar a la taza y apilarse como base.");
            
            CrazyLid crazyLid = new CrazyLid(100, 100, "magenta", 400, 100, 5);
            tower2.pushItem(crazyLid); 
            Thread.sleep(1500);
            
            int resp2 = JOptionPane.showConfirmDialog(null, "¿La CrazyLid ignoró el sellado?", "Aceptación 2", JOptionPane.YES_NO_OPTION);
            
            if(resp1 == JOptionPane.YES_OPTION && resp2 == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Ciclo 4 completado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Hubo fallos visuales.");
            }
            
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}