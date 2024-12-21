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

package gameworld.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.system.ui.FonT;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public boolean blockInteraction;
    protected String displayName;
    public boolean show_dialog;
    public Point playerTalkLocation = new Point();
    public int dialogHideDelay;
    protected int checkpointIndex = 0;
    public Point[] checkPoints;

    public Point goalTile;
    public int stuckCounter;

    protected void moveTo(int goalX, int goalY, Point... checkpoints) {
        if (checkpoints != null && checkpoints.length > 0) {
            if (checkpointIndex < checkpoints.length) {
                if (checkpoints[checkpointIndex].equals(activeTile)) {
                    checkpointIndex++;
                } else {
                    moveToTileSuperVised(checkpoints[checkpointIndex].x, checkpoints[checkpointIndex].y);
                }
            } else if (!(new Point(goalX, goalY).equals(activeTile))) {
                moveToTileSuperVised(goalX, goalY);
            } else if (new Point(goalX, goalY).equals(activeTile)) {
                onPath = false;
                checkpointIndex = 0;
            }
        } else if (!new Point(goalX, goalY).equals(activeTile)) {
            moveToTileSuperVised(goalX, goalY);
        } else {
            onPath = false;
            checkpointIndex = 0;
        }
    }

    public void setPosition(int x, int y) {
        worldX = x * 48;
        worldY = y * 48;
    }

    @Override
    public void update() {
        activeTile.x = (int) ((worldX + 24) / 48);
        activeTile.y = (int) ((worldY + 24) / 48);
    }

    abstract public void drawDialog(GraphicsContext gc);

    protected boolean collidingWithPlayer() {
        return activeTile.x == mg.playerX && activeTile.y == mg.playerY;
    }

    protected void drawNPCName(GraphicsContext gc, String name) {
        gc.setEffect(mg.ui.shadow);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.varnished14);
        drawCenteredTextAroundX(screenX + 28, gc, name, screenY - 12);
        gc.setEffect(null);
    }

    protected void drawNPCName(GraphicsContext gc, String name, String title) {
        gc.setEffect(mg.ui.shadow);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.varnished14);
        drawCenteredTextAroundX(screenX + 28, gc, name, screenY - 25);
        drawCenteredTextAroundX(screenX + 28, gc, "<" + title + ">", screenY - 10);
        gc.setEffect(null);
    }

    private void drawCenteredTextAroundX(int x, GraphicsContext gc, String text, float y) {
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        gc.fillText(text, x - (textWidth / 2), y);
    }
}
