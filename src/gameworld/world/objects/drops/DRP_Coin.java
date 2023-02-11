package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.Storage;

public class DRP_Coin extends DROP {
    private Image coin1;
    private Image coin2;
    private Image coin3;
    private Image coin4;

    public final int amount;


    public DRP_Coin(int x, int y, int amount) {
        getImages();
        this.amount = amount;
        this.size = 10;
        this.worldPos.x = x + 5;
        this.worldPos.y = y + 5;
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        spriteCounter++;
        int spriteIndex = spriteCounter % 80 / 20;
        switch (spriteIndex) {
            case 0 ->
                    gc.drawImage(coin1, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 1 ->
                    gc.drawImage(coin2, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 2 ->
                    gc.drawImage(coin3, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 3 ->
                    gc.drawImage(coin4, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
        }
    }

    /**
     * update method
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
