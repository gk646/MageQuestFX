package gameworld.entities;

import javafx.scene.canvas.GraphicsContext;

abstract public class BOSS extends ENTITY {
    /**
     * @param gc Graphics context
     */
    @Override
    abstract public void draw(GraphicsContext gc);


    /**
     *
     */
    @Override
    abstract public void update();
}
