package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_FlameWhirl extends CollisionProjectiles {


    public PRJ_FlameWhirl(float weapon_damage_percetn) {
        projectileType = ProjectileType.Continuous;
        this.weapon_damage_percent = weapon_damage_percetn;
        this.type = DamageType.Fire;
        this.resource = ProjectilePreloader.fireSword;
        //this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(Player.worldX + 24, Player.worldY + 24);
        collisionBox = new Rectangle(-96, -75, 185, 150);
        direction = "leftrightdownup";
        //playStartSound();
    }


    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 128 / 8) {
            case 0 ->
                    gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 1 ->
                    gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 2 ->
                    gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 3 ->
                    gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 4 ->
                    gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 5 ->
                    gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 6 ->
                    gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 7 ->
                    gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 8 ->
                    gc.drawImage(resource.images1.get(8), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 9 ->
                    gc.drawImage(resource.images1.get(9), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 10 ->
                    gc.drawImage(resource.images1.get(10), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 11 ->
                    gc.drawImage(resource.images1.get(11), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 12 ->
                    gc.drawImage(resource.images1.get(12), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 13 ->
                    gc.drawImage(resource.images1.get(13), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);
            case 14 ->
                    gc.drawImage(resource.images1.get(14), (int) worldPos.x - Player.worldX + Player.screenX - 96, (int) worldPos.y - Player.worldY + Player.screenY - 70);

            case 15 -> dead = true;
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
        worldPos.x = Player.worldX + 24;
        worldPos.y = Player.worldY + 24;
        outOfBounds();
        //tileCollision();
        if (dead) {
            // sounds[0].stop();
        }
    }
}
