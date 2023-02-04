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
        spriteCounter++;
        int spriteIndex = spriteCounter / 6;
        switch (spriteIndex) {
            case 0 ->
                    gc.drawImage(coin1, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY - 50);
            case 1 ->
                    gc.drawImage(coin2, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY - 50);
            case 2 ->
                    gc.drawImage(coin3, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY - 50);
            case 4 ->
                    gc.drawImage(coin4, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY - 50);
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
