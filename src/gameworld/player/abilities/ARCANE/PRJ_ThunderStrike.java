package gameworld.player.abilities.ARCANE;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
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
