package gameworld.player.abilities.portals;

import gameworld.player.EnemyProjectile;
import gameworld.player.Player;
import gameworld.player.PlayerPrompts;
import gameworld.player.ProjectilePreloader;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_Teleporter extends EnemyProjectile {
    public boolean remove;
    MainGame mg;

    public PRJ_Teleporter(MainGame mg, int x, int y) {
        this.mg = mg;
        movementSpeed = 0;
        this.dead = false;
        this.damageDead = false;
        this.resource = ProjectilePreloader.gateTeleporter;
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
            switch (spriteCounter % 135 / 15) {
                case 0 ->
                        gc.drawImage(resource.images1.get(7), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 1 ->
                        gc.drawImage(resource.images1.get(8), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 2 ->
                        gc.drawImage(resource.images1.get(9), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 3 ->
                        gc.drawImage(resource.images1.get(10), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 4 ->
                        gc.drawImage(resource.images1.get(11), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 5 ->
                        gc.drawImage(resource.images1.get(12), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 6 ->
                        gc.drawImage(resource.images1.get(13), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 7 ->
                        gc.drawImage(resource.images1.get(14), (int) worldPos.x - Player.worldX + Player.screenX - 100, (int) worldPos.y - Player.worldY + Player.screenY - 40);
                case 8 ->
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

    public void teleportPlayer() {
        mg.loadGameState.loadSpawnLevel();
    }
}

