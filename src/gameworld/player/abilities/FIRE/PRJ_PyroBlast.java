package gameworld.player.abilities.FIRE;

import gameworld.entities.damage.DamageType;
import gameworld.player.CollisionProjectiles;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PRJ_PyroBlast extends CollisionProjectiles {
    private final double RightAngle;

    public PRJ_PyroBlast(float weapon_damage_percent, int x, int y) {
        this.weapon_damage_percent = weapon_damage_percent;
        this.type = DamageType.Fire;
        this.resource = ProjectilePreloader.pyroBlast;
        this.sounds[0] = resource.sounds.get(0);
        this.worldPos = new Point2D.Double(Player.worldX, Player.worldY);
        collisionBox = new Rectangle(15, 0, 32, 32);
        direction = "leftrightdownup";
        projectileType = ProjectileType.OneHitCompletelyDead;
        projectileHeight = 32;
        damageDead = false;
        projectileWidth = 64;
        this.updateVector = getTrajectory(new Point(x, y));
        double dx = InputHandler.instance.lastMousePosition.getX() - (Player.screenX + 32);
        double dy = InputHandler.instance.lastMousePosition.getY() - (Player.screenY + 16);
        RightAngle = Math.toDegrees(Math.atan2(dy, dx));
        this.movementSpeed = 3.5f;
        playStartSound();
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 32, mousePosition.x - Player.screenX - 16);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + 32, worldPos.y - Player.worldY + Player.screenY + 16);
        gc.rotate(RightAngle);
        switch (spriteCounter % 60 / 15) {
            case 0 -> gc.drawImage(resource.images1.get(0), -32, -16);
            case 1 -> gc.drawImage(resource.images1.get(1), -32, -16);
            case 2 -> gc.drawImage(resource.images1.get(2), -32, -16);
            case 3 -> gc.drawImage(resource.images1.get(3), -32, -16);
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
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }
}

