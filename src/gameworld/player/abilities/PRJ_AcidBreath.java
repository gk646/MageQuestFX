package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.player.EnemyProjectile;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;
import main.system.Storage;

import java.awt.Point;
import java.awt.geom.Point2D;


public class PRJ_AcidBreath extends EnemyProjectile {


    public PRJ_AcidBreath(int x, int y, int level, int goalx, int goaly) {
        //-------VALUES-----------
        this.movementSpeed = 3;
        projectileType = ProjectileType.OneHitCompletelyDead;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = Storage.acidBreathBox;
        this.level = level;
        this.direction = "downleftrightup";
        type = DamageType.Poison;
        this.damage = 1;

        //------POSITION-----------
        this.worldPos = new Point2D.Double(x + 24 - projectileWidth / 2.0f, y + 24 - projectileHeight / 2.0f);
        this.updateVector = getTrajectory(goalx, goaly);
        this.projectileImage1 = Storage.acidBreath;
        this.endPos = new Point((int) (worldPos.x + 850), (int) (worldPos.y + 850));
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.drawImage(projectileImage1, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBoundsEnemy();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }

    private Point2D.Double getTrajectory(int x, int y) {
        double angle = Math.atan2(y + 16 - worldPos.y, x + 16 - worldPos.x);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }
}

