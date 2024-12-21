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

package gameworld.entities.companion;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Rectangle;
import java.util.Objects;

public class ENT_Owly extends ENTITY {
    /**
     * A possible companion
     *
     * @param mg        Maingame instance
     * @param worldX    worldX coordinate
     * @param worldY    worldY coordinate
     * @param maxHealth maximum health
     */
    public ENT_Owly(MainGame mg, int worldX, int worldY, int maxHealth) {
        this.mg = mg;
        //Setting default values
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.worldX = worldX;
        this.worldY = worldY;
        movementSpeed = 4;
        direction = "updownleftright";
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.collisionBox = new Rectangle(6, 6, 30, 30);
        this.onPath = true;
        getOwlyImage();
        this.searchTicks = 60;
        updatePos();
    }

    @Override
    public void update() {
        screenX = (int) (worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2 - 24);
        screenY = (int) (worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24);
        if (worldX / mg.tileSize != Player.worldX / mg.tileSize || worldY / mg.tileSize != Player.worldY / mg.tileSize) {
            onPath = true;
        }
        owlyMovement();
        searchTicks++;
    }

    private void updatePos() {
        screenX = (int) (worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2 - 24);
        screenY = (int) (worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24);
    }

    private void getOwlyImage() {
        entityImage1 = setup("owly01.png");
        entityImage2 = setup("owly02.png");
        entityImage3 = setup("owly03.png");
        entityImage4 = setup("owly04.png");
        entityImage5 = setup("owly05.png");
        entityImage6 = setup("owly06.png");
    }

    private Image setup(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/owly/" + imagePath)));
    }

    @Override
    public void draw(GraphicsContext g2) {
        if (spriteCounter <= 8) {
            g2.drawImage(entityImage1, screenX, screenY, entityWidth, entityHeight);
        }
        if (spriteCounter >= 9 && spriteCounter <= 17) {
            g2.drawImage(entityImage2, screenX, screenY, entityWidth, entityHeight);
        }
        if (spriteCounter >= 18 && spriteCounter <= 24) {
            g2.drawImage(entityImage3, screenX, screenY, entityWidth, entityHeight);
        }
        if (spriteCounter >= 25 && spriteCounter <= 33) {
            g2.drawImage(entityImage4, screenX, screenY, entityWidth, entityHeight);
        }
        if (spriteCounter >= 34 && spriteCounter <= 42) {
            g2.drawImage(entityImage5, screenX, screenY, entityWidth, entityHeight);
        }
        if (spriteCounter >= 43 && spriteCounter <= 51) {
            g2.drawImage(entityImage6, screenX, screenY, entityWidth, entityHeight);
            spriteCounter = 0;
        }

        spriteCounter++;
    }

    private void owlyMovement() {
        if (onPath && searchTicks >= Math.random() * 45) {
            getNearestPlayer();
            searchPath(goalCol, goalRow, 50);
            searchTicks = 0;
        } else if (onPath) {
            trackPath();
        }
    }
}


