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

package gameworld.player.abilities.FIRE;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.debuffs.DEBUF_FireExplosion;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.List;


public class PRJ_SolarFlare extends PROJECTILE {


    public PRJ_SolarFlare(float weapon_damage_percent, List<PROJECTILE> projectiles) {
        this.weapon_damage_percent = weapon_damage_percent;
        this.type = DamageType.Fire;
        this.resource = ProjectilePreloader.solarFlare;
        this.sounds[0] = resource.sounds.get(0);
        this.procEffects[0] = new DEBUF_FireExplosion(projectiles);
        this.worldPos = new Point2D.Double(Player.worldX - Player.screenX + InputHandler.instance.lastMousePosition.x - 24, Player.worldY + InputHandler.instance.lastMousePosition.y - Player.screenY - 24);
        collisionBox = new Rectangle(0, 0, 48, 48);
        direction = "leftrightdownup";
        projectileType = ProjectileType.OneHitNoDMG;
        playStartSound();
    }


    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 117 / 13) {
            case 0 -> gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 1 -> gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 2 -> gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 3 -> gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 4 -> gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 5 -> gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 6 -> gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 7 -> gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 8 -> dead = true;
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



