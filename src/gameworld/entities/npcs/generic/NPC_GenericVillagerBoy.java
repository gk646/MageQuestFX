/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
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

package gameworld.entities.npcs.generic;

import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;


public class NPC_GenericVillagerBoy extends NPC_Generic {


    public NPC_GenericVillagerBoy(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/boy");
        animation.load();
        this.mg = mainGame;
        this.zone = zone;
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2.0f;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
        spriteCounter = (int) (Math.random() * 20);
        displayName = "Village Boy";
    }

    @Override
    public void update() {
        super.update();
        scriptMovement();
    }

    /**
     * @param gc
     */
    @Override
    public void drawDialog(GraphicsContext gc) {
        dialog.drawDialog(gc, this);
    }


    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);

        if (onPath) {
            drawWalk(gc);
        } else {
            drawIdle(gc);
        }
        spriteCounter++;
        drawNPCName(gc, displayName);
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX, screenY);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX, screenY);
        }
    }
}
