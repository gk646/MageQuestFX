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

package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.EntityPreloader;
import gameworld.player.Player;
import gameworld.player.abilities.enemies.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;
import main.system.enums.Zone;

import java.awt.Rectangle;


public class ENT_SkeletonArcher extends ENTITY {

    private int shotCooldown;
    private boolean attack1, attack2, attack3;

    /**
     * Main Enemy class
     *
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_SkeletonArcher(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        this.zone = zone;
        this.mg = mg;
        //Setting default values
        this.maxHealth = (9 + level) * (level + level - 1);
        if (level == 1) {
            maxHealth = 5;
        }
        this.animation = EntityPreloader.skeletonArcher;
        this.health = maxHealth;
        this.worldX = worldX;
        this.worldY = worldY;
        movementSpeed = 2;
        this.level = level;
        direction = "updownleftright";
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.collisionBox = new Rectangle(8, 10, 30, 30);
        this.onPath = false;
        getDisplayImage();
        this.searchTicks = 60;
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
    }

    @Override
    public void update() {
        super.update();
        if (shotCooldown >= 130 && !playerTooFarAbsolute()) {
            attack1 = true;
            shotCooldown = 0;
            spriteCounter = 0;
        }
        onPath = !attack1;
        if (onPath) {
            getNearestPlayer();
            searchPath(goalCol, goalRow, 16);
        }
        searchTicks++;
        shotCooldown++;
        if (hpBarCounter >= 600) {
            hpBarOn = false;
            hpBarCounter = 0;
        } else if (hpBarOn) {
            hpBarCounter++;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);

        if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            // drawAttack2(gc);
        } else if (attack3) {
            // drawAttack3(gc);
        } else {
            if (onPath) {
                drawWalk(gc);
            } else {
                drawIdle(gc);
            }
        }
        spriteCounter++;
        drawBuffsAndDeBuffs(gc);
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX - 20, screenY - 6);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX - 20, screenY - 6);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX - 20, screenY - 6);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX - 20, screenY - 6);
            case 4 -> gc.drawImage(animation.idle.get(4), screenX - 20, screenY - 6);
            case 5 -> gc.drawImage(animation.idle.get(5), screenX - 20, screenY - 6);
            case 6 -> gc.drawImage(animation.idle.get(6), screenX - 20, screenY - 6);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX - 35, screenY - 6);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX - 35, screenY - 6);
            case 2 -> gc.drawImage(animation.walk.get(2),
                    screenX - 35, screenY - 6);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX - 35, screenY - 6);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX - 35, screenY - 6);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX - 35, screenY - 6);
            case 6 -> gc.drawImage(animation.walk.get(6), screenX - 35, screenY - 6);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 160 / 10) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX - 20, screenY - 14);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX - 20, screenY - 14);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX - 20, screenY - 14);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX - 20, screenY - 14);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX - 20, screenY - 14);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX - 20, screenY - 14);
            case 6 -> gc.drawImage(animation.attack1.get(6), screenX - 20, screenY - 14);
            case 7 -> gc.drawImage(animation.attack1.get(7), screenX - 20, screenY - 14);
            case 8 -> gc.drawImage(animation.attack1.get(8), screenX - 20, screenY - 14);
            case 9 -> gc.drawImage(animation.attack1.get(9), screenX - 20, screenY - 14);
            case 10 -> gc.drawImage(animation.attack1.get(10), screenX - 20, screenY - 14);
            case 11 -> gc.drawImage(animation.attack1.get(11), screenX - 20, screenY - 14);
            case 12 -> gc.drawImage(animation.attack1.get(12), screenX - 20, screenY - 14);
            case 13 -> gc.drawImage(animation.attack1.get(13), screenX - 20, screenY - 14);
            case 14 -> gc.drawImage(animation.attack1.get(14), screenX - 20, screenY - 14);
            case 15 -> {
                attack1 = false;
                animation.playRandomSoundFromXToIndex(0, 0);
                mg.PROJECTILES.add(new PRJ_EnemyStandardShot((int) worldX, (int) worldY, level, (int) Player.worldX, (int) Player.worldY, level));
            }
        }
    }

    private void drawDeath(GraphicsContext gc) {
        switch (spriteCounter % 210 / 35) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX - 15, screenY - 51);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX - 15, screenY - 51);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX - 15, screenY - 51);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX - 15, screenY - 51);
            case 4 -> gc.drawImage(animation.dead.get(4), screenX - 15, screenY - 51);
            case 5 -> AfterAnimationDead = true;
        }
    }

    private void getDisplayImage() {
        enemyImage = Storage.shooterImage1;
    }


    private void getNearestCircularTile() {

    }
}
