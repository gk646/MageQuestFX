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

package gameworld.player.abilities.POISON;

import gameworld.entities.damage.DamageType;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;
import main.system.Storage;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PRJ_AutoShot extends CollisionProjectiles {

    /**
     * What happens when you press main mouse button
     */
    public PRJ_AutoShot(int x, int y, float damage) {
        //-------VALUES-----------
        projectileType = ProjectileType.OneHitCompletelyDead;
        this.movementSpeed = 5;
        this.weapon_damage_percent = damage;
        this.type = DamageType.Poison;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = Storage.box_primaryFire;
        this.direction = "downleftrightup";
        //------POSITION-----------
        this.worldPos = new Point2D.Double(Player.worldX + 24 - projectileWidth / 2.0f, Player.worldY + 24 - projectileHeight / 2.0f);
        this.updateVector = getTrajectory(new Point(x, y));
        getPlayerImage();
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.drawImage(projectileImage1, (int) (worldPos.x - Player.worldX + Player.screenX), (int) (worldPos.y - Player.worldY + Player.screenY), projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBounds();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 24, mousePosition.x - Player.screenX - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }


    private void getPlayerImage() {
        projectileImage1 = Storage.primaryFire1;
    }
}