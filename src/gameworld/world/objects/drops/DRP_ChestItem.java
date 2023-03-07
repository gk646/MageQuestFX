package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;
import main.system.ui.Effects;

import java.awt.Point;

public class DRP_ChestItem extends DROP {

    private final int startX;
    private final int startY;
    private final int controlX;
    private final int controlY;
    private final int endX;
    private final int endY;
    final Image icon;
    private float arcPosition = 0;


    public DRP_ChestItem(MainGame mg, int worldX, int worldY, Zone zone, int level, boolean left) {
        this.item = mg.dropManager.getGuaranteedRandomItem(level);
        this.zone = zone;
        this.icon = item.icon;
        blockPickup = true;
        this.worldPos = new Point(worldX - 16, worldY - 16);
        if (!left && (Math.random() > 0.5f && !mg.wRender.tileStorage[WorldRender.worldData[(worldPos.x + 40) / 48][worldPos.y / 48]].collision)) {
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

    public DRP_ChestItem(MainGame mg, int worldX, int worldY, Zone zone, boolean left, ITEM item) {
        this.item = item;
        this.zone = zone;
        this.icon = item.icon;
        blockPickup = true;
        this.worldPos = new Point(worldX - 16, worldY - 16);
        if (!left && (Math.random() > 0.5f && !mg.wRender.tileStorage[WorldRender.worldData[(worldPos.x + 40) / 48][worldPos.y / 48]].collision)) {
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
        setRarityEffect(gc);
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

    private void setRarityEffect(GraphicsContext gc) {
        if (item.rarity == 1) {
            gc.setEffect(Effects.rarity_1glow);
        } else if (item.rarity == 2) {
            gc.setEffect(Effects.rarity_2glow);
        } else if (item.rarity == 3) {
            gc.setEffect(Effects.rarity_3glow);
        } else if (item.rarity == 4 || item.rarity == 10) {
            gc.setEffect(Effects.rarity_4glow);
        } else if (item.rarity == 5) {
            gc.setEffect(Effects.rarity_5glow);
        }
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
