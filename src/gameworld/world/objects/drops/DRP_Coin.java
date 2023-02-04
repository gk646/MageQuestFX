package gameworld.world.objects.drops;

import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.Storage;

public class DRP_Coin extends DROP {
    private Image coin1;
    private Image coin2;
    private Image coin3;
    private Image coin4;
    private int spriteCounter = 0;


    public DRP_Coin(int x, int y) {
        getImages();
        this.worldPos.x = x;
        this.worldPos.y = y;
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

    private void getImages() {
        coin1 = Storage.coin1;
        coin2 = Storage.coin2;
        coin3 = Storage.coin3;
        coin4 = Storage.coin4;
    }
}
