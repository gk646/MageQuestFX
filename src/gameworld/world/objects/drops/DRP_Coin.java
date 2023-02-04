package gameworld.world.objects.drops;

import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DRP_Coin extends DROP {
    private Image coin1;
    private Image coin2;
    private Image coin3;
    private Image coin4;
    private int spriteCounter = 0;


    public DRP_Coin(int x, int y) {

    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {

        spriteCounter++;
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
