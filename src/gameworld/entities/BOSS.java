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

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Point;
import java.util.Objects;


abstract public class BOSS extends ENTITY {
    protected String name;
    private final Image bossBar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/bossbar.png")));

    public BOSS(MainGame mg, int x, int y, int level, int health, Zone zone) {
        this.mg = mg;
        this.zone = zone;
        this.worldX = x;
        this.worldY = y;
        this.level = level;
        this.maxHealth = ((9 + level) * (level + level - 1)) * 12;
        movementSpeed = 2;
        this.health = maxHealth;
        this.direction = "leftrightdownup";
    }

    /**
     * @param gc Graphics context
     */
    @Override
    abstract public void draw(GraphicsContext gc);


    /**
     *
     */
    @Override
    public void update() {
        tickEffects();
        activeTile.x = (int) ((worldX + 24) / 48);
        activeTile.y = (int) ((worldY + 24) / 48);
        if (health <= 0) {
            dead = true;
            playGetHitSound();
        }
        if (hpBarCounter >= 600) {
            hpBarOn = false;
            hpBarCounter = 0;
        } else if (hpBarOn) {
            hpBarCounter++;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")

    public boolean playerTooFarAbsoluteBoss(int x) {
        return Math.abs(worldX - Player.worldX) >= x || Math.abs(worldY - Player.worldY) >= x;
    }

    protected boolean isOnPlayer() {
        return (int) (worldX + 24) / 48 == mg.playerX && (int) (worldY + 24) / 48 == mg.playerY;
    }

    protected boolean closeToPlayerAbsolute(int distance) {
        return new Point((int) Player.worldX, (int) Player.worldY).distance(worldX, worldY) < distance;
    }

    protected void drawBossHealthBar(GraphicsContext gc) {
        gc.setEffect(mg.ui.shadow);
        gc.setFill(Colors.bossNamePurple);
        gc.setFont(FonT.editUndo22);
        drawCenteredText(gc, name, MainGame.SCREEN_HEIGHT * 0.051f);
        gc.setEffect(null);
        gc.setFill(Colors.Red);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.736f, MainGame.SCREEN_HEIGHT * 0.061f, (health / (float) maxHealth) * 322, 15);
        gc.drawImage(bossBar, MainGame.SCREEN_HEIGHT * 0.723f, MainGame.SCREEN_HEIGHT * 0.047_5f);
    }

    private void drawCenteredText(GraphicsContext gc, String text, float y) {
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double x = (gc.getCanvas().getWidth() - textWidth) / 2;
        gc.fillText(text, x, y);
    }

    private void decideMovementBoss(int nextX, int nextY) {
        float enLeftX = worldX;
        float enRightX = (worldX + entityWidth);
        float enTopY = worldY;
        float enBottomY = (worldY + entityHeight);
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        mg.collisionChecker.checkBossAgainstTile(this);
        if (enLeftX < nextX && !collisionRight) {
            worldX += movementSpeed;
        } else if (enLeftX > nextX && !collisionLeft) {
            worldX -= movementSpeed;
        } else if (enTopY < nextY && !collisionDown) {
            worldY += movementSpeed;
        } else if (enTopY > nextY && !collisionUp) {
            worldY -= movementSpeed;
        } else if (enRightX > nextX) {
            worldX -= movementSpeed;
        } else if (enRightX < nextX) {
            worldX += movementSpeed;
        } else if (enBottomY > nextY) {
            worldX -= movementSpeed;
        } else if (enBottomY < nextX) {
            worldX += movementSpeed;
        }
    }

    protected void searchPathBigEnemies(int goalCol, int goalRow, int maxDistance) {
        int startCol = activeTile.x;
        int startRow = activeTile.y;
        mg.pathF.setNodes(startCol, startRow, goalCol, goalRow, maxDistance);
        if (!(startCol == goalCol && startRow == goalRow) && mg.pathF.searchUncapped()) {
            int nextX = mg.pathF.pathList.get(0).col * 48;
            int nextY = mg.pathF.pathList.get(0).row * 48;
            decideMovementBoss(nextX, nextY);
        } else {
            onPath = false;
        }
    }
}
