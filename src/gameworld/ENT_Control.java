/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


/**
 * Used for handling Enemies
 */
public class ENT_Control {

    public final ArrayList<ENTITY> addToEntities = new ArrayList<>();
    private final MainGame mg;
    private final Image healthBar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/healthbars.png")));

    public ENT_Control(MainGame mg) {
        this.mg = mg;
    }

    /**
     * Draws all entities and healthbars
     */
    public void draw(GraphicsContext gc) {
        gc.setFont(FonT.editUndo16);
        synchronized (mg.ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.ENTITIES) {
                if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                    entity.draw(gc);
                    if (entity.hpBarOn) {
                        gc.setFill(Colors.Red);
                        gc.fillRect(entity.screenX, entity.screenY - 14, (int) ((entity.getHealth() / entity.maxHealth) * 48), 6);
                        gc.drawImage(healthBar, entity.screenX - 2, entity.screenY - 16);
                        gc.setFill(Color.WHITE);
                        gc.setEffect(mg.ui.shadow);
                        gc.fillText(String.valueOf((int) entity.getHealth()), entity.screenX + 14, entity.screenY - 15);
                        gc.setEffect(null);
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
            if (addToEntities.size() > 0) {
                mg.ENTITIES.addAll(addToEntities);
                addToEntities.clear();
            }
        }
    }
}


