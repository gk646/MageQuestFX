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

package gameworld.player.abilities.FIRE;

import gameworld.PRJ_Control;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.DamageEffect;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;

public class PRJ_FireBurst extends CollisionProjectiles {

    private final int version;
    private double RightAngle;

    /**
     * What happens when you press (1). Part of
     * {@link PRJ_Control}
     */
    public PRJ_FireBurst(int version, float weapon_damage) {
        //-------VALUES-----------
        this.movementSpeed = 3.2f;
        this.projectileHeight = 25;
        this.projectileWidth = 25;
        projectileType = ProjectileType.Continuous;
        this.resource = ProjectilePreloader.fireBurst;
        this.sounds[0] = resource.sounds.get(0);
        this.procEffects[0] = new DamageEffect(300, 10, true, DamageType.Fire, 30, PRJ_BurnSource.class);
        this.collisionBox = new Rectangle(12, 7, 23, 23);
        this.version = version;
        this.type = DamageType.Fire;
        this.weapon_damage_percent = weapon_damage;
        damageDead = false;
        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX, Player.worldY);
        this.direction = "downleftrightup";
        this.updateVector = new java.awt.geom.Point2D.Double(1, 1);
        getUpdateVector();
        playStartSound();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + 23, worldPos.y - Player.worldY + Player.screenY + 16);
        gc.rotate(RightAngle);
        switch (spriteCounter % 60 / 15) {
            case 0 -> gc.drawImage(resource.images1.get(0), -23, -16);
            case 1 -> gc.drawImage(resource.images1.get(1), -23, -16);
            case 2 -> gc.drawImage(resource.images1.get(2), -23, -16);
            case 3 -> gc.drawImage(resource.images1.get(3), -23, -16);
        }
        spriteCounter++;
        gc.restore();
    }

    @Override
    public void update() {
        outOfBounds();
        worldPos.x += updateVector.x;
        worldPos.y += updateVector.y;
    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }


    private void getUpdateVector() {
        if (version == 0) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = movementSpeed;
            RightAngle = 45;
        }
        if (version == 1) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
            RightAngle = -135;
        }
        if (version == 2) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = movementSpeed;
            RightAngle = 135;
        }
        if (version == 3) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
            RightAngle = -45;
        }
        if (version == 4) {
            this.updateVector.x = 0;
            this.updateVector.y = movementSpeed;
            RightAngle = 90;
        }
        if (version == 5) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = 0;
            RightAngle = -179.9;
        }
        if (version == 6) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = 0;
            RightAngle = 0;
        }
        if (version == 7) {
            this.updateVector.x = 0;
            this.updateVector.y = -1 * movementSpeed;
            RightAngle = -90;
        }
    }
}