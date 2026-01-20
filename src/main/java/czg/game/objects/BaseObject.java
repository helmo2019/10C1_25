package czg.game.objects;

import czg.game.Images;

import java.awt.*;

import static czg.MainWindow.PIXEL_SCALE;

/**
 * Ein minimales Spiel-Objekt, bestehend aus einer Position und
 * einem Bild.
 */
public /*abstract*/ class BaseObject {

    public int x = 0;
    public int y = 0;

    public Image sprite;

    public /*(protected)*/ BaseObject(String spritePath) {
        sprite = Images.get(spritePath);
    }

    public void draw(Graphics2D g) {
        g.drawImage(
                sprite,
                x * PIXEL_SCALE, y * PIXEL_SCALE,
                sprite.getWidth(null) * PIXEL_SCALE, sprite.getHeight(null) * PIXEL_SCALE,
                null
        );
    }

    public void update() {
        x+=1;
    }

}
