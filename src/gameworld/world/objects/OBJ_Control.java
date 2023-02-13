package gameworld.world.objects;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import main.MainGame;

import java.awt.Rectangle;

public class OBJ_Control {
    Rectangle[] objects = new Rectangle[100];
    private final Rectangle[] objects_tutorial = new Rectangle[100];
    private final MainGame mg;

    public OBJ_Control(MainGame mg) {
        this.mg = mg;
      //  objects_tutorial[1] = new Rectangle(23 * 48, 8 * 48, 48, 48);
    }

    public void checkCollisionPlayer() {
        for (Rectangle rect : objects_tutorial) {
            if (rect != null) {
                if (rect.intersects(Player.worldX + mg.player.playerMovementSpeed, Player.worldY, 48, 48)) {
                    mg.player.collisionRight = true;
                } else if (rect.intersects(Player.worldX - mg.player.playerMovementSpeed, Player.worldY, 48, 48)) {
                    mg.player.collisionLeft = true;
                }
                if (rect.intersects(Player.worldX, Player.worldY - mg.player.playerMovementSpeed, 48, 48)) {
                    mg.player.collisionUp = true;
                } else if (rect.intersects(Player.worldX, Player.worldY + mg.player.playerMovementSpeed, 48, 48)) {
                    mg.player.collisionDown = true;
                }
            }
        }
    }

    public void checkCollisionEntity(ENTITY entity) {
        for (Rectangle rect : objects_tutorial) {
            if (rect != null) {
                if (rect.intersects(entity.worldX + entity.movementSpeed, entity.worldY, entity.entityWidth, entity.entityHeight)) {
                    entity.collisionRight = true;
                } else if (rect.intersects(entity.worldX - entity.movementSpeed, entity.worldY, entity.entityWidth, entity.entityHeight)) {
                    entity.collisionLeft = true;
                }
                if (rect.intersects(entity.worldX, entity.worldY - entity.movementSpeed, entity.entityWidth, entity.entityHeight)) {
                    entity.collisionUp = true;
                } else if (rect.intersects(entity.worldX, entity.worldY + entity.movementSpeed, entity.entityWidth, entity.entityHeight)) {
                    entity.collisionDown = true;
                }
            }
        }
    }
}
