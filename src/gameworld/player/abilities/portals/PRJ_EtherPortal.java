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

package gameworld.player.abilities.portals;

import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.entities.npcs.quests.ENT_RealmKeeper;
import gameworld.player.EnemyProjectile;
import gameworld.player.Player;
import gameworld.player.PlayerPrompts;
import gameworld.world.WorldController;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_EtherPortal extends EnemyProjectile {
    private boolean remove;
    private final MainGame mg;

    public PRJ_EtherPortal(MainGame mg, int x, int y) {
        this.mg = mg;
        movementSpeed = 0;
        this.dead = false;
        this.damageDead = false;
        this.resource = ProjectilePreloader.etherPortal;
        //this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(x * 48, y * 48);
        collisionBox = new Rectangle(-10, -10, 58, 58);
        direction = "leftrightdownup";
        //playStartSound();
    }

    /**
     *
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (spriteCounter < 105) {
            switch (spriteCounter % 105 / 15) {
                case 0 ->
                        gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 1 ->
                        gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 2 ->
                        gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 3 ->
                        gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 4 ->
                        gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 5 ->
                        gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 6 ->
                        gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
            }
        } else {
            switch (spriteCounter % 120 / 15) {
                case 0 ->
                        gc.drawImage(resource.images1.get(8), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 1 ->
                        gc.drawImage(resource.images1.get(9), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 2 ->
                        gc.drawImage(resource.images1.get(10), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 3 ->
                        gc.drawImage(resource.images1.get(11), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 4 ->
                        gc.drawImage(resource.images1.get(12), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 5 ->
                        gc.drawImage(resource.images1.get(13), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 6 ->
                        gc.drawImage(resource.images1.get(14), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 7 ->
                        gc.drawImage(resource.images1.get(15), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
            }
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
        //worldPos.x = Player.worldX;
        // worldPos.y = Player.worldY;
        if (dead) {
            if (PlayerPrompts.Ecounter < 60 && InputHandler.instance.e_typed) {
                InputHandler.instance.e_typed = false;
                teleportPlayer();
            }
            PlayerPrompts.setETrue();
            //sounds[0].stop();
        }
        dead = remove;
    }

    private void teleportPlayer() {
        if (WorldController.currentWorld != Zone.EtherRealm) {
            mg.generator.loadRandomMap();
        } else {
            mg.wControl.loadMap(Zone.Hillcrest, 29, 22);
            ENT_RealmKeeper.ETHER_ACTIVE = false;
        }
        remove = true;
    }
}


