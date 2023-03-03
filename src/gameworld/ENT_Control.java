package gameworld;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.util.Iterator;


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
        gc.setFont(FonT.editUndo18);
        synchronized (mg.ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.ENTITIES) {
                if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                    entity.draw(gc);
                    if (entity.hpBarOn) {
                        gc.setFill(Colors.Red);
                        gc.fillRect(entity.screenX, entity.screenY - 10, (int) ((entity.getHealth() / entity.maxHealth) * 48), 8);
                        gc.setFill(Color.WHITE);
                        gc.fillText(String.valueOf((int) entity.getHealth()), entity.screenX + 14, entity.screenY);
                    }
                }
            }
        }
    }

    /**
     * Updates all entity positions and gamestates
     */


    public void update() {
        synchronized (mg.ENTITIES) {
            Iterator<ENTITY> entityIterator = mg.ENTITIES.iterator();
            while (entityIterator.hasNext()) {
                ENTITY entity = entityIterator.next();
                if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 900) {
                    if (!entity.dead) {
                        entity.update();
                        if (mg.collisionChecker.checkEntityAgainstPlayer(entity, 8)) {
                            entity.collidingWithPlayer = true;
                            mg.player.getDurabilityDamageArmour();
                        }
                    } else if (entity.AfterAnimationDead) {
                        mg.prj_control.recordDeath(entity);
                        entityIterator.remove();
                    }
                }
            }
        }
    }
}


