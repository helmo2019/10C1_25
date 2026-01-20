package czg.scene;

import czg.MainWindow;
import czg.game.objects.BaseObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Scene extends JPanel {

    private boolean useBackgroundColor = true;
    private Color backgroundColor = Color.BLACK;
    private Image backgroundImage = null;

    public final List<BaseObject> objects = new ArrayList<>();

    /**
     * Ob diese Szene vollständig ausgeblendet werden soll, wenn eine
     * andere über ihr angezeigt wird, oder ob sie immer noch darunter
     * sichtbar sein soll.
     */
    public boolean canBeCovered = false;

    /**
     * Ob diese Szene aktuell von einer anderen verdeckt ist.
     * Sollte nicht von hand gesetzt werden.
     */
    public boolean isCovered = false;


    public Scene() {
        // Komplettes Fenster ausfüllen
        setBounds(0,0,MainWindow.WIDTH,MainWindow.HEIGHT);

        // Transparent sein
        setOpaque(false);
    }

    public void setBackgroundColor(Color c) {
        useBackgroundColor = true;
        backgroundColor = c;
    }

    public void setBackgroundImage(Image i) {
        useBackgroundColor = false;
        backgroundImage = i;
    }

    public void update() {
        objects.forEach(BaseObject::update);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Nichts zeichnen, wenn die Szene verdeckt ist
        if(isCovered)
            return;

        Graphics2D g2 = (Graphics2D) g;

        // Hintergrund zeichnen:
        if(useBackgroundColor) {
            // Einfarbig
            g2.setColor(backgroundColor);
            g2.fillRect(0,0,MainWindow.WIDTH,MainWindow.HEIGHT);
        } else {
            // Bild
            g2.drawImage(backgroundImage, 0, 0, MainWindow.WIDTH, MainWindow.HEIGHT, null);
        }

        // Objekte zeichnen
        objects.forEach(o -> o.draw(g2));
    }
}
