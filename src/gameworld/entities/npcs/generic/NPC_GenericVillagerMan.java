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

package gameworld.entities.npcs.generic;

import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_GenericVillagerMan extends NPC_Generic {
    public NPC_GenericVillagerMan(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/man");
        this.mg = mainGame;
        this.zone = zone;
        goalTile = new Point();
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }

    /**
     *
     */
    @Override
    public void draw(GraphicsContext gc) {
        drawNPCName(gc, "Villager");
    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     * @param gc
     */
    @Override
    public void drawDialog(GraphicsContext gc) {
        dialog.drawDialog(gc, this);
    }
}
