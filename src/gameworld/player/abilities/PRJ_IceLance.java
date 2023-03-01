package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import main.system.ui.Colors;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PRJ_IceLance extends PROJECTILE {

    private double angle;

    public PRJ_IceLance(int x, int y) {
        this.damage = 1.0f;
        this.type = DamageType.FrostDMG;
        projectileType = ProjectileType.OneHitCompletelyDead;
        this.resource = ProjectilePreloader.iceLance;
        this.sounds[0] = new MediaPlayer(resource.sounds.get(0).getMedia());
        this.worldPos = new Point2D.Double(Player.worldX + 24 - projectileWidth / 2.0f - 32, Player.worldY + 24 - projectileHeight / 2.0f - 32);
        this.updateVector = getTrajectory(new Point(x, y));
        collisionBox = new Rectangle(0, 0, 15, 15);
        direction = "leftrightdownup";
        projectileHeight = 64;
        projectileWidth = 64;
        playStartSound();
        movementSpeed = 4;
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 24, mousePosition.x - Player.screenX - 24);
        this.angle = Math.toDegrees(angle);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + projectileWidth / 2.0f, worldPos.y - Player.worldY + Player.screenY + projectileHeight / 2.0f);
        gc.rotate(angle);
        switch (spriteCounter % 3 / 3) {
            case 0 -> gc.drawImage(resource.images1.get(0), -projectileWidth / 2.0f, -projectileHeight / 2.0f);
            case 1 -> gc.drawImage(resource.images1.get(1), -projectileWidth, -projectileHeight);
            case 2 -> gc.drawImage(resource.images1.get(2), -projectileWidth, -projectileHeight);
            case 3 -> gc.drawImage(resource.images1.get(3), -projectileWidth, -projectileHeight);
            case 4 -> gc.drawImage(resource.images1.get(4), -projectileWidth, -projectileHeight);
            case 5 -> gc.drawImage(resource.images1.get(5), -projectileWidth, -projectileHeight);
            case 6 -> gc.drawImage(resource.images1.get(6), -projectileWidth, -projectileHeight);
            case 7 -> gc.drawImage(resource.images1.get(7), -projectileWidth, -projectileHeight);
            case 8 -> gc.drawImage(resource.images1.get(8), -projectileWidth, -projectileHeight);
            case 9 -> gc.drawImage(resource.images1.get(9), -projectileWidth, -projectileHeight);
            case 10 -> gc.drawImage(resource.images1.get(10), -projectileWidth, -projectileHeight);
            case 11 -> gc.drawImage(resource.images1.get(11), -projectileWidth, -projectileHeight);
            case 12 -> gc.drawImage(resource.images1.get(12), -projectileWidth, -projectileHeight);
            case 13 -> gc.drawImage(resource.images1.get(13), -projectileWidth, -projectileHeight);
            case 14 -> gc.drawImage(resource.images1.get(14), -projectileWidth, -projectileHeight);
            case 15 -> gc.drawImage(resource.images1.get(15), -projectileWidth, -projectileHeight);
            case 16 -> gc.drawImage(resource.images1.get(16), -projectileWidth, -projectileHeight);
            case 17 -> gc.drawImage(resource.images1.get(17), -projectileWidth, -projectileHeight);
            case 18 -> gc.drawImage(resource.images1.get(18), -projectileWidth, -projectileHeight);
            case 19 -> gc.drawImage(resource.images1.get(19), -projectileWidth, -projectileHeight);
            case 20 -> gc.drawImage(resource.images1.get(20), -projectileWidth, -projectileHeight);
            case 21 -> gc.drawImage(resource.images1.get(21), -projectileWidth, -projectileHeight);
            case 22 -> gc.drawImage(resource.images1.get(22), -projectileWidth, -projectileHeight);
            case 23 -> gc.drawImage(resource.images1.get(23), -projectileWidth, -projectileHeight);
            case 24 -> gc.drawImage(resource.images1.get(24), -projectileWidth, -projectileHeight);
            case 25 -> gc.drawImage(resource.images1.get(25), -projectileWidth, -projectileHeight);
            case 26 -> gc.drawImage(resource.images1.get(26), -projectileWidth, -projectileHeight);
            case 27 -> gc.drawImage(resource.images1.get(27), -projectileWidth, -projectileHeight);
            case 28 -> gc.drawImage(resource.images1.get(28), -projectileWidth, -projectileHeight);
            case 29 -> gc.drawImage(resource.images1.get(29), -projectileWidth, -projectileHeight);
        }
        gc.restore();
        gc.setFill(Colors.Red);
        gc.fillRect(worldPos.x + collisionBox.x - Player.worldX + Player.screenX, worldPos.y + collisionBox.y - Player.worldY + Player.screenY, collisionBox.width, collisionBox.height);
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
        tileCollision();
        if (dead) {
            sounds[0].stop();
        }
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }
}



