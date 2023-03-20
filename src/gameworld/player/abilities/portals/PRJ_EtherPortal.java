package gameworld.player.abilities.portals;

import gameworld.player.EnemyProjectile;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_EtherPortal extends EnemyProjectile {

    public PRJ_EtherPortal() {
        this.resource = ProjectilePreloader.etherPortal;
        //this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(Player.worldX - Player.screenX + InputHandler.instance.lastMousePosition.x - 32, Player.worldY + InputHandler.instance.lastMousePosition.y - Player.screenY - 32);
        collisionBox = new Rectangle(-5, -5, 74, 74);
        direction = "leftrightdownup";
        projectileType = ProjectileType.Continuous;
        playStartSound();
    }

    /**
     *
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (spriteCounter < 160) {
            switch (spriteCounter % 160 / 10) {
                case 0 ->
                        gc.drawImage(resource.images1.get(0), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 1 ->
                        gc.drawImage(resource.images1.get(1), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 2 ->
                        gc.drawImage(resource.images1.get(2), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 3 ->
                        gc.drawImage(resource.images1.get(3), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 4 ->
                        gc.drawImage(resource.images1.get(4), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 5 ->
                        gc.drawImage(resource.images1.get(5), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 6 ->
                        gc.drawImage(resource.images1.get(6), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 7 ->
                        gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 8 ->
                        gc.drawImage(resource.images1.get(8), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 9 ->
                        gc.drawImage(resource.images1.get(9), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 10 ->
                        gc.drawImage(resource.images1.get(10), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 11 ->
                        gc.drawImage(resource.images1.get(11), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 12 ->
                        gc.drawImage(resource.images1.get(12), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 13 ->
                        gc.drawImage(resource.images1.get(13), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 14 ->
                        gc.drawImage(resource.images1.get(14), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 15 ->
                        gc.drawImage(resource.images1.get(15), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
            }
        } else if (duration - spriteCounter / 2 <= 30) {
            switch (spriteCounter % 60 / 15) {
                case 0 ->
                        gc.drawImage(resource.images1.get(25), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 1 ->
                        gc.drawImage(resource.images1.get(26), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 2 ->
                        gc.drawImage(resource.images1.get(27), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 3 ->
                        gc.drawImage(resource.images1.get(28), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
            }
        } else {
            switch (spriteCounter % 135 / 15) {
                case 0 ->
                        gc.drawImage(resource.images1.get(16), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 1 ->
                        gc.drawImage(resource.images1.get(17), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 2 ->
                        gc.drawImage(resource.images1.get(18), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 3 ->
                        gc.drawImage(resource.images1.get(19), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 4 ->
                        gc.drawImage(resource.images1.get(20), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 5 ->
                        gc.drawImage(resource.images1.get(21), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 6 ->
                        gc.drawImage(resource.images1.get(22), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 7 ->
                        gc.drawImage(resource.images1.get(23), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
                case 8 ->
                        gc.drawImage(resource.images1.get(24), (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY);
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
        // outOfBounds();
        //tileCollision();
        if (spriteCounter / 2 >= duration) {
            dead = true;
        }
        if (dead) {
            sounds[0].stop();
        }
    }
}

