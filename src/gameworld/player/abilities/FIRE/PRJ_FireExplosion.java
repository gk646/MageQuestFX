package gameworld.player.abilities.FIRE;

import gameworld.PRJ_Control;
import gameworld.entities.damage.DamageType;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;


public class PRJ_FireExplosion extends PROJECTILE {


    /**
     * What happens when you press (1). Part of
     * {@link PRJ_Control}
     */
    public PRJ_FireExplosion(float weapon_damage, int x, int y) {
        //-------VALUES-----------
        this.movementSpeed = 0;
        this.projectileHeight = 32;
        this.projectileWidth = 32;
        projectileType = ProjectileType.OneHitNoDMG;
        this.resource = ProjectilePreloader.fireExplosion;
        //this.sounds[0] = resource.sounds.get(0);
        this.collisionBox = new Rectangle(-4, -4, 40, 40);
        this.type = DamageType.Fire;
        this.weapon_damage_percent = weapon_damage;
        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(x + 10, y - 30);
        this.direction = "";
        //playStartSound();
    }

    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 125 / 25) {
            case 0 -> {
                gc.drawImage(resource.images1.get(0), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(0), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(0), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(0), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
            }

            case 1 -> {
                gc.drawImage(resource.images1.get(1), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(1), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(1), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(1), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
            }
            case 2 -> {
                gc.drawImage(resource.images1.get(2), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(2), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(2), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(2), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
            }
            case 3 -> {
                gc.drawImage(resource.images1.get(3), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(3), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(3), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
                gc.drawImage(resource.images1.get(3), worldPos.x - Player.worldX + Player.screenX + 10 + (Math.random() * 20 - 40), worldPos.y - Player.worldY + Player.screenY + 45 + (Math.random() * 20 - 40));
            }
            case 4 -> dead = true;
        }
        spriteCounter++;
    }

    @Override
    public void update() {
        outOfBounds();
    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }
}
