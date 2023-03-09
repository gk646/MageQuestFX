package gameworld.player.abilities;

import gameworld.PRJ_Control;
import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import main.system.Storage;
import main.system.ui.Effects;

import java.awt.Rectangle;

@SuppressWarnings("DuplicateBranchesInSwitch")
public class PRJ_Lightning extends PROJECTILE {

    /**
     * What happens when you press "2". Part of
     * {@link PRJ_Control}
     */
    public PRJ_Lightning(float weapon_damage_percent) {
        //-------VALUES-----------
        this.projectileHeight = 92;
        this.projectileWidth = 70;
        this.collisionBox = new Rectangle(30, 30, 40, 30);
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
