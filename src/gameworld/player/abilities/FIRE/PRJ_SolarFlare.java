package gameworld.player.abilities.FIRE;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.debuffs.DEBUF_FireExplosion;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
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
            case 0 ->
                    gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 1 ->
                    gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 2 ->
                    gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 3 ->
                    gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 4 ->
                    gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 5 ->
                    gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 6 ->
                    gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
            case 7 ->
                    gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX - 10, (int) worldPos.y - Player.worldY + Player.screenY - 80);
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



