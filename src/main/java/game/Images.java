package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// Zentraler Zugriff auf Bilddateien im "src/main/resources"-Ordner
public class Images {

    private static final Map<String, Image> loaded = new HashMap<>();

    // Bild, welches verwendet wird für den Fall, dass das eigentliche
    // Bild nicht geladen werden kann. Ein 2x2-Schachbrett-Muster in
    // Schwarz und Magenta (wie in z.B. Minecraft).
    static final Image missingTexture;

    static {
        missingTexture = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) missingTexture.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,8,8);

        g.setColor(Color.MAGENTA);
        g.fillRect(3,0,4,4);
        g.fillRect(0,3,4,4);
    }

    /**
     * Das Bild "src/main/resources/<path>" ggf. laden und zurückgeben
     * @param path Dateipfad, z.B. "items/taschenrechner.png"
     * @return Das geladene Bild
     */
    public static Image get(String path) {
        return loaded.computeIfAbsent(path, p -> {
            try(InputStream stream = Images.class.getResourceAsStream(p)) {
                // Datei nicht gefunden → missingTexture
                if(stream == null) {
                    System.err.printf("Datei nicht gefunden: %s%n", p);
                    return missingTexture;
                }

                // Versuchen, das Bild zu laden. Die mögliche IOException
                // wird abgefangen und resultiert in der missingTexture.
                return ImageIO.read(stream);
            } catch (IOException x) {
                System.err.printf("Eingabe/Ausgabe-Fehler beim laden von %s%n", p);
                return missingTexture;
            }
        });

    }

}
