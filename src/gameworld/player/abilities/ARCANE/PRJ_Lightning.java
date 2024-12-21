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

import gameworld.PRJ_Control;
import gameworld.entities.damage.DamageType;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import main.system.Storage;
import main.system.ui.Effects;

import java.awt.Rectangle;

@SuppressWarnings("DuplicateBranchesInSwitch")
public class PRJ_Lightning extends CollisionProjectiles {

    /**
     * What happens when you press "2". Part of
     * {@link PRJ_Control}
     */
    public PRJ_Lightning(float weapon_damage_percent) {
        //-------VALUES-----------
        this.projectileHeight = 92;
        this.projectileWidth = 70;
        this.collisionBox = new Rectangle(10, 15, 40, 30);
        this.weapon_damage_percent = weapon_damage_percent;
        projectileType = ProjectileType.OneHitNoDMG;
        resource = ProjectilePreloader.lightning;
        sounds[0] = resource.sounds.get(0);
        type = DamageType.Arcane;

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX + InputHandler.instance.lastMousePosition.x - Player.screenX - 24, Player.worldY + InputHandler.instance.lastMousePosition.y - Player.screenY - 24);
        getImages();
        playStartSound();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setEffect(Effects.blueGlow);

        int spriteIndex = spriteCounter / 6;
        switch (spriteIndex) {
            case 0 ->
                    gc.drawImage(projectileImage1, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 1 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 2 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 4 ->
                    gc.drawImage(projectileImage3, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 5 ->
                    gc.drawImage(projectileImage4, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 6 ->
                    gc.drawImage(projectileImage5, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 7 ->
                    gc.drawImage(projectileImage6, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 8 ->
                    gc.drawImage(projectileImage7, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 9 ->
                    gc.drawImage(projectileImage8, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 10 ->
                    gc.drawImage(projectileImage9, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 11 ->
                    gc.drawImage(projectileImage10, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 12 ->
                    gc.drawImage(projectileImage10, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY - 50, projectileWidth, projectileHeight);
            case 13 -> dead = true;
        }
        gc.setEffect(null);
        spriteCounter++;
    }

    public void update() {

    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }

    private void getImages() {
        projectileImage1 = Storage.Lightning1;
        projectileImage2 = Storage.Lightning2;
        projectileImage3 = Storage.Lightning3;
        projectileImage4 = Storage.Lightning4;
        projectileImage5 = Storage.Lightning5;
        projectileImage6 = Storage.Lightning6;
        projectileImage7 = Storage.Lightning7;
        projectileImage8 = Storage.Lightning8;
        projectileImage9 = Storage.Lightning9;
        projectileImage10 = Storage.Lightning10;
    }
}
