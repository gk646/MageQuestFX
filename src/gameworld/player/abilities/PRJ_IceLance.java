package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.EffectType;
import gameworld.entities.damage.effects.arraybased.Effect_ArrayBased;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PRJ_IceLance extends CollisionProjectiles {

    private final double RightAngle;

    public PRJ_IceLance(int x, int y, float weaponDMG) {
        this.type = DamageType.Ice;
        projectileType = ProjectileType.OneHitCompletelyDead;
        this.resource = ProjectilePreloader.iceLance;
        this.weapon_damage_percent = weaponDMG;
        this.procEffects[0] = new Effect_ArrayBased(180, 30, true, 45, EffectType.DEBUFF, this.getClass());
        this.sounds[0] = new MediaPlayer(resource.sounds.get(0).getMedia());
        this.worldPos = new Point2D.Double(Player.worldX, Player.worldY);
        this.updateVector = getTrajectory(new Point(x, y));
        collisionBox = new Rectangle(25, 25, 15, 15);
        direction = "leftrightdownup";
        projectileHeight = 64;
        projectileWidth = 64;
        playStartSound();
        movementSpeed = 3;
        double dx = InputHandler.instance.lastMousePosition.getX() - (Player.screenX + 32);
        double dy = InputHandler.instance.lastMousePosition.getY() - (Player.screenY + 32);
        RightAngle = Math.toDegrees(Math.atan2(dy, dx));
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 32, mousePosition.x - Player.screenX - 32);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + 32, worldPos.y - Player.worldY + Player.screenY + 32);
        gc.rotate(RightAngle);
        switch (spriteCounter % 90 / 3) {
            case 0 -> gc.drawImage(resource.images1.get(0), -32, -32);
            case 1 -> gc.drawImage(resource.images1.get(1), -32, -32);
            case 2 -> gc.drawImage(resource.images1.get(2), -32, -32);
            case 3 -> gc.drawImage(resource.images1.get(3), -32, -32);
            case 4 -> gc.drawImage(resource.images1.get(4), -32, -32);
            case 5 -> gc.drawImage(resource.images1.get(5), -32, -32);
            case 6 -> gc.drawImage(resource.images1.get(6), -32, -32);
            case 7 -> gc.drawImage(resource.images1.get(7), -32, -32);
            case 8 -> gc.drawImage(resource.images1.get(8), -32, -32);
            case 9 -> gc.drawImage(resource.images1.get(9), -32, -32);
            case 10 -> gc.drawImage(resource.images1.get(10), -32, -32);
            case 11 -> gc.drawImage(resource.images1.get(11), -32, -32);
            case 12 -> gc.drawImage(resource.images1.get(12), -32, -32);
            case 13 -> gc.drawImage(resource.images1.get(13), -32, -32);
            case 14 -> gc.drawImage(resource.images1.get(14), -32, -32);
            case 15 -> gc.drawImage(resource.images1.get(15), -32, -32);
            case 16 -> gc.drawImage(resource.images1.get(16), -32, -32);
            case 17 -> gc.drawImage(resource.images1.get(17), -32, -32);
            case 18 -> gc.drawImage(resource.images1.get(18), -32, -32);
            case 19 -> gc.drawImage(resource.images1.get(19), -32, -32);
            case 20 -> gc.drawImage(resource.images1.get(20), -32, -32);
            case 21 -> gc.drawImage(resource.images1.get(21), -32, -32);
            case 22 -> gc.drawImage(resource.images1.get(22), -32, -32);
            case 23 -> gc.drawImage(resource.images1.get(23), -32, -32);
            case 24 -> gc.drawImage(resource.images1.get(24), -32, -32);
            case 25 -> gc.drawImage(resource.images1.get(25), -32, -32);
            case 26 -> gc.drawImage(resource.images1.get(26), -32, -32);
            case 27 -> gc.drawImage(resource.images1.get(27), -32, -32);
            case 28 -> gc.drawImage(resource.images1.get(28), -32, -32);
            case 29 -> gc.drawImage(resource.images1.get(29), -32, -32);
        }
        gc.restore();
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
        outOfBounds();
        if (dead) {
            sounds[0].stop();
            sounds[0].dispose();
            sounds[0] = null;
        }
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }
}



