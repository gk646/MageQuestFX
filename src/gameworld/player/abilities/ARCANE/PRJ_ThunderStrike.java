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
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_ThunderStrike extends PROJECTILE {


    public PRJ_ThunderStrike(float weapon_damage_percent) {
        this.weapon_damage_percent = weapon_damage_percent;
        this.type = DamageType.Arcane;
        damageDead = true;
        this.resource = ProjectilePreloader.thunderStrike;
        this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(Player.worldX - Player.screenX + InputHandler.instance.lastMousePosition.x - 24, Player.worldY + InputHandler.instance.lastMousePosition.y - Player.screenY - 24);
        collisionBox = new Rectangle(0, 0, 48, 48);
        direction = "leftrightdownup";
        projectileType = ProjectileType.OneHitNoDMG;
        playStartSound();
    }


    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 168 / 12) {
            case 0 ->
                    gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 1 ->
                    gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 2 ->
                    gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 3 ->
                    gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 4 ->
                    gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 5 ->
                    gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 6 ->
                    gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 7 ->
                    gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 8 ->
                    gc.drawImage(resource.images1.get(8), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 9 ->
                    gc.drawImage(resource.images1.get(9), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 10 ->
                    gc.drawImage(resource.images1.get(10), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 11 ->
                    gc.drawImage(resource.images1.get(11), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 12 ->
                    gc.drawImage(resource.images1.get(12), (int) worldPos.x - Player.worldX + Player.screenX - 16, (int) worldPos.y - Player.worldY + Player.screenY - 16);
            case 13 -> dead = true;
        }

        if (spriteCounter == 60) {
            damageDead = false;
        }
        spriteCounter++;
    }

    @Override
    public void playHitSound() {

    }

    /**
     *
     */
    @Override
    public void update() {
        // outOfBounds();
        //tileCollision();
        if (dead) {
            //sounds[0].stop();
        }
    }
}
