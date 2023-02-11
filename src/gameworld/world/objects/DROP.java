package gameworld.world.objects;

import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;

abstract public class DROP {
    public final Point worldPos = new Point();
    public ITEM item;
    public int size;
    protected int spriteCounter = 0;

    abstract public void draw(GraphicsContext gc);


    abstract public void update();
}
