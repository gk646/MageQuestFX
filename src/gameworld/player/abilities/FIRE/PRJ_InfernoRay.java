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

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.DamageEffect;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_InfernoRay extends PROJECTILE {
    private double RightAngle;

    public PRJ_InfernoRay(float weapon_damage_percent) {
        this.weapon_damage_percent = weapon_damage_percent;
        this.type = DamageType.Fire;
        damageDead = true;
        this.resource = ProjectilePreloader.infernoRay;
        this.sounds[0] = new MediaPlayer(resource.sounds.get(0).getMedia());
        this.sounds[0].setOnEndOfMedia(() -> sounds[0].dispose());
        this.worldPos = new Point2D.Double(Player.worldX, Player.worldY);
        this.procEffects[0] = new DamageEffect(300, 10, true, DamageType.Fire, 30, PRJ_BurnSource.class);
        direction = "leftrightdownup";
        projectileType = ProjectileType.OneHitNoDMG;
        double dx = InputHandler.instance.lastMousePosition.getX() - (Player.screenX + 24);
        double dy = InputHandler.instance.lastMousePosition.getY() - (Player.screenY + 24);
        RightAngle = Math.toDegrees(Math.atan2(dy, dx));
        RightAngle = getClosestRightAngle((int) RightAngle);
        collisionBox = new Rectangle(48, 0, 262, 62);
        if (RightAngle == -90) {
            collisionBox = new Rectangle(0, -262, 62, 262);
        } else if (RightAngle == 90) {
            collisionBox = new Rectangle(0, 48, 62, 262);
        } else if (RightAngle == 180) {
            collisionBox = new Rectangle(-262, 0, 262, 61);
        }
        playStartSound();
    }

    private int getClosestRightAngle(int calculatedAngle) {
        // Normalize the calculated angle to be between 0 and 359
        calculatedAngle = (calculatedAngle % 360 + 360) % 360;

        // Find the closest 90-degree angle
        int closestRightAngle = (int) (Math.round(calculatedAngle / 90.0) * 90) % 360;

        // Convert 180 degrees back to -180 degrees for your desired scale

        if (closestRightAngle == 270) {
            return -90;
        }
        return closestRightAngle;
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + 24, worldPos.y - Player.worldY + Player.screenY + 31);
        gc.rotate(RightAngle);
        switch (spriteCounter % 144 / 12) {
            case 0 -> gc.drawImage(resource.images1.get(0), 0, -31);
            case 1 -> gc.drawImage(resource.images1.get(1), 0, -31);
            case 2 -> gc.drawImage(resource.images1.get(2), 0, -31);
            case 3 -> gc.drawImage(resource.images1.get(3), 0, -31);
            case 4 -> gc.drawImage(resource.images1.get(4), 0, -31);
            case 5 -> gc.drawImage(resource.images1.get(5), 0, -31);
            case 6 -> gc.drawImage(resource.images1.get(6), 0, -31);
            case 7 -> gc.drawImage(resource.images1.get(7), 0, -31);
            case 8 -> gc.drawImage(resource.images1.get(8), 0, -31);
            case 9 -> gc.drawImage(resource.images1.get(9), 0, -31);
            case 10 -> gc.drawImage(resource.images1.get(10), 0, -31);
            case 11 -> dead = true;
        }
        gc.restore();

        if (spriteCounter == 30) {
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

    }
}
