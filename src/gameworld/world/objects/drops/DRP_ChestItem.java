package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;

public class DRP_ChestItem extends DROP {

    private final int startX;
    private final int startY;
    private final int controlX;
    private final int controlY;
    private final int endX;
    private final int endY;
    Image icon;
    private float arcPosition = 0;


    public DRP_ChestItem(MainGame mg, int worldX, int worldY, Zone zone, int level) {
        this.item = mg.dropI.getFinishedRandomItem(level);
        this.zone = zone;
        this.icon = item.icon;
        blockPickup = true;
        this.worldPos = new Point(worldX, worldY);
        if (Math.random() > 0.5f) {
            this.startX = worldPos.x;
            this.startY = worldPos.y;
            controlX = worldPos.x + 25;
            controlY = worldPos.y - 100;
            endX = worldPos.x + 50;
            endY = worldPos.y;
        } else {
            this.startX = worldPos.x;
            this.startY = worldPos.y;
            controlX = worldPos.x - 25;
            controlY = worldPos.y - 100;
            endX = worldPos.x - 50;
            endY = worldPos.y;
        }
        this.size = 32;
    }


    /**
     * @param gc drawing
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (arcPosition <= 1.0) {
            worldPos.x = (int) ((1 - arcPosition) * (1 - arcPosition) * startX + 2 * (1 - arcPosition) * arcPosition * controlX + arcPosition * arcPosition * endX);
            worldPos.y = (int) ((1 - arcPosition) * (1 - arcPosition) * startY + 2 * (1 - arcPosition) * arcPosition * controlY + arcPosition * arcPosition * endY);
            worldPos.x = Math.min(Math.max(0, worldPos.x), WorldRender.worldData.length * 48);
            worldPos.y = Math.min(Math.max(0, worldPos.y), WorldRender.worldData.length * 48);
            gc.drawImage(icon, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY, 32, 32);
            arcPosition += 0.01f;
        } else {
            blockPickup = false;
            gc.drawImage(icon, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY, 32, 32);
        }
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
