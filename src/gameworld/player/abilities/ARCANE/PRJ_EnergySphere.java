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

package gameworld.player.abilities.ARCANE;

import gameworld.entities.damage.DamageType;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import main.system.Storage;
import main.system.sound.Sound;
import main.system.ui.Effects;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PRJ_EnergySphere extends CollisionProjectiles {


    /**
     * Energy Sphere projectile
     */
    public PRJ_EnergySphere(float damage) {
        sounds[0] = new MediaPlayer(Sound.energySphereBeginning);
        sounds[0].setVolume(Sound.EFFECTS_VOLUME);
        sounds[1] = new MediaPlayer(Sound.energySphereHit);
        sounds[1].setVolume(Sound.EFFECTS_VOLUME);
        this.type = DamageType.Arcane;
        projectileType = ProjectileType.Continuous;
        //-------VALUES-----------
        this.movementSpeed = 2.5f;
        this.weapon_damage_percent = damage;
        this.projectileHeight = 32;
        this.projectileWidth = 32;
        this.collisionBox = Storage.box_secondaryFire;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX + 24 - projectileWidth / 2.0f, Player.worldY + 24 - projectileHeight / 2.0f);
        this.updateVector = getTrajectory(InputHandler.instance.lastMousePosition);
        getProjectileImage();
        playStartSound();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setEffect(Effects.blueGlow);
        switch (spriteCounter % 60 / 10) {
            case 0 ->
                    gc.drawImage(projectileImage1, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 1 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 2 ->
                    gc.drawImage(projectileImage3, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 3 ->
                    gc.drawImage(projectileImage4, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 4 ->
                    gc.drawImage(projectileImage5, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 5 ->
                    gc.drawImage(projectileImage6, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
        }
        spriteCounter++;
        gc.setEffect(null);
    }

    @Override
    public void update() {
        outOfBounds();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
        if (dead) {
            sounds[0].dispose();
            sounds[1].play();
            sounds[1].setOnEndOfMedia(() -> sounds[1].dispose());
        }
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 24, mousePosition.x - Player.screenX - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    private void getProjectileImage() {
        projectileImage1 = Storage.secondaryFire1;
        projectileImage2 = Storage.secondaryFire2;
        projectileImage3 = Storage.secondaryFire3;
        projectileImage4 = Storage.secondaryFire4;
        projectileImage5 = Storage.secondaryFire5;
        projectileImage6 = Storage.secondaryFire6;
    }
}
