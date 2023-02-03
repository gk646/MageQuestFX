package gameworld.world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Point;

abstract public class OBJECT {
    public Point worldPos;
    Image objectImage1;

    abstract public void draw(GraphicsContext gc);


    abstract public void update();
}
