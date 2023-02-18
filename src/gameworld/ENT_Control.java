package gameworld;

import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;


/**
 * Used for handling Enemies
 */
public class ENT_Control {


    private final MainGame mg;

    public ENT_Control(MainGame mg) {
        this.mg = mg;
    }

    /**
     * Draws all entities and healthbars
     */
    public void draw(GraphicsContext gc) {
        synchronized (MainGame.ENTITIES) {
            for (gameworld.entities.ENTITY entity : MainGame.ENTITIES) {
                if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                    entity.draw(gc);
                    if (entity.hpBarOn) {
                        gc.setFill(Colors.Red);
                        gc.fillRect(entity.screenX, entity.screenY - 10, (int) ((entity.getHealth() / entity.maxHealth) * 48), 8);
                        gc.setFill(Color.WHITE);
                        gc.fillText(String.valueOf(entity.getHealth()), entity.screenX + 14, entity.screenY);
                    }
                }
            }
        }
    }

    /**
     * Updates all entity positions and gamestates
     */


    public void update() {
        synchronized (mg.PROXIMITY_ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                    entity.update();
                    if (entity.hitDelay >= 30 && mg.collisionChecker.checkEntityAgainstPlayer(entity, 8)) {
                        mg.player.health -= entity.level;
                        mg.player.getDurabilityDamageArmour();
                        entity.hitDelay = 0;
                    }
                }
            }
        }
    }
}


