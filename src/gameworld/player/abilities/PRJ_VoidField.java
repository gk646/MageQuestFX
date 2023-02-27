package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_VoidField extends PROJECTILE {

    public PRJ_VoidField(int durationInTicks) {
        this.damage = 0.5f;
        this.type = DamageType.DarkDMG;
        this.resource = ProjectilePreloader.voidField;
        this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(Player.worldX - Player.screenX + InputHandler.instance.lastMousePosition.x - 32, Player.worldY + InputHandler.instance.lastMousePosition.y - Player.screenY - 32);
        collisionBox = new Rectangle(-32, -32, 64, 64);
        direction = "leftrightdownup";
        this.duration = durationInTicks;
        playStartSound();
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
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
