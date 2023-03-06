package gameworld.entities.props;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;

import java.awt.Rectangle;
import java.util.Objects;

public class DeadWolf extends ENTITY {

    public DeadWolf(int x, int y, Zone zone) {
        this.worldX = x * 48;
        this.zone = zone;
        this.worldY = y * 48;
        this.entityImage1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Entitys/enemies/wolf/death/5.png")));
        this.collisionBox = new Rectangle();
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(entityImage1, worldX - Player.worldX + Player.screenX, worldY - Player.worldY + Player.screenY);
    }
}
