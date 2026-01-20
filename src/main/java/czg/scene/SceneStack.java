package czg.scene;

import javax.swing.*;
import java.awt.*;

public class SceneStack {

    private Scene last = null;
    private final JPanel contentPane;

    /**
     * Einen neuen Szenen-Stapel erstellen.
     * @param contentPane Das {@link JPanel}, welches die Szenen enthalten soll
     */
    public SceneStack(JPanel contentPane) {
        contentPane.removeAll();
        this.contentPane = contentPane;
    }

    /**
     * Zeigt eine weitere Szene über allen bestehenden Szenen an
     * @param scene Beliebige Szene
     */
    public void push(Scene scene) {
        contentPane.add(scene);
        contentPane.setComponentZOrder(scene, contentPane.getComponentCount()-1);
        last = scene;
    }

    /**
     * Entfernt die oberste Szene
     */
    public void pop() {
        if(last != null)
            contentPane.remove(last);
        else
            System.err.println("Es wurde versucht, eine Szene zu entfernen, obwohl keine Szenen mehr auf dem Stapel sind!");
    }

    public void update() {
        for(Component c : contentPane.getComponents()) {
            if(c instanceof Scene) {
                ((Scene) c).update();
            }
        }
    }

}
