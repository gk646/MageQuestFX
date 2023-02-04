package gameworld;

import gameworld.entities.companion.ENT_Owly;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;

import java.util.ConcurrentModificationException;


/**
 * Only used for handling Enemies atm.
 * Main inheritable class for all game world entity's
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
        for (gameworld.entities.ENTITY entity : MainGame.ENTITIES) {
            entity.draw(gc);
            if (!(entity instanceof ENT_Owly)) {
                if (entity.hpBarOn) {
                    gc.setFill(Colors.Red);
                    gc.fillRect(entity.screenX, entity.screenY - 10, (int) (((float) entity.health / entity.maxHealth) * 48), 8);
                    gc.setFill(Color.WHITE);
                    gc.fillText(entity.health + "", entity.screenX + 14, entity.screenY);
                }
            }
        }
    }

    /**
     * Updates all entity positions and gamestates
     */


    public void update() {
        try {
            for (gameworld.entities.ENTITY entity : MainGame.ENTITIES) {
                entity.update();
                if (!(entity instanceof ENT_Owly)) {
                    if (entity.hitDelay >= 30 && mg.collisionChecker.checkEntityAgainstEntity(mg.player, entity)) {
                        mg.player.health -= entity.level;
                        mg.player.getDurabilityDamageArmour();
                        entity.hitDelay = 0;
                    }
                    if (entity.hpBarCounter >= 600) {
                        entity.hpBarOn = false;
                        entity.hpBarCounter = 0;
                    }
                    if (entity.hpBarOn) {
                        entity.hpBarCounter++;
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }
}
