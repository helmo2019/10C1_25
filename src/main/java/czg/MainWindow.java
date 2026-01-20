package czg;

import czg.game.Images;
import czg.game.objects.BaseObject;
import czg.scene.Scene;
import czg.scene.SceneStack;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements Runnable {

    /**
     * Wie viele Bildschirm-Pixel ein Textur-Pixel beansprucht
     */
    public static final int PIXEL_SCALE = 6;

    /**
     * Wie viele Bildschirm-Pixel das Fenster breit ist
     */
    public static final int WIDTH = 140 * PIXEL_SCALE;

    /**
     * Wie viele Bildschirm-Pixel das Fenster hoch ist
     */
    public static final int HEIGHT = 105 * PIXEL_SCALE;

    /**
     * Einzelbilder pro Sekunde
     */
    public static final int FPS = 30;

    private static MainWindow instance = null;

    /**
     * Die einzige Instanz des Szenen-Stapels
     */
    public final SceneStack sceneStack;

    /**
     * @return Die Instanz des Fensters zugreifen
     */
    public static MainWindow getInstance() {
        return instance;
    }

    private MainWindow() {
        super("CZGame");

        // Feste Größe
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setResizable(false);

        // Haupt-Panel erstellen
        JPanel contentPane = new JPanel();
        // Komponenten werden von Hand platziert
        contentPane.setLayout(null);
        // Gesamtes Fenster ausfüllen
        contentPane.setSize(WIDTH, HEIGHT);
        // An den SceneStack übergeben. Dieser platziert und entfernt
        // die Szenen dann auf bzw. von dieser, und blendet sie ein
        // und aus.
        sceneStack = new SceneStack(contentPane);
        // Haupt-Panel tatsächlich festlegen
        this.setContentPane(contentPane);

        // Gesamtes Programm wird beendet, wenn das Fenster geschlossen wird
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // In die Mitte des Bildschirms platzieren
        this.setLocationRelativeTo(null);

        // Zeigen
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // OpenGL-Grafikschnittstelle und damit (hoffentlich) die Grafikkarte verwenden
        //System.setProperty("sun.java2d.opengl","true");

        // Fenster erstellen
        instance = new MainWindow();


        // DEBUG
        Scene myScene = new Scene();
        myScene.setBackgroundImage(Images.get("/background.png"));
        BaseObject cat = new BaseObject("/moppel_side_R_1.png");
        myScene.objects.add(cat);

        getInstance().sceneStack.push(myScene);


        // Haupt-Schleife starten
        new Thread(instance).start();
    }

    /**
     * Haupt-Logik
     */
    @Override
    public void run() {
        final double drawInterval = 1e9 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        System.out.println("Haupt-Schleife beginnt");

        while(true) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                getInstance().sceneStack.update();
                repaint();

                delta--;
                drawCount++;
            }

        }

    }
}
