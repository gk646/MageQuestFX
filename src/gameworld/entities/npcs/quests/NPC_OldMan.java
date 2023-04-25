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

package gameworld.entities.npcs.quests;

import gameworld.entities.NPC;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_OldMan extends NPC {


    public NPC_OldMan(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.zone = zone;
        this.mg = mainGame;
        this.animation = new ResourceLoaderEntity("npc/oldman");
        animation.load();
        goalTile = new Point(34, 34);
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
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
        drawNPCName(gc, "Deckard");
    }

    public void drawDialog(GraphicsContext gc) {
        dialog.drawDialog(gc, this);
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX + 17, screenY - 5);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX + 17, screenY - 5);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX + 17, screenY - 5);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX + 17, screenY - 5);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX + 17, screenY - 5);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX + 17, screenY - 5);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX + 17, screenY - 5);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX + 17, screenY - 5);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX + 17, screenY - 5);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX + 17, screenY - 5);
        }
    }

    @Override
    public void update() {
        super.update();
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 500);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
    }
}

