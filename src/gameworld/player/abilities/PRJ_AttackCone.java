package gameworld.player.abilities;

import gameworld.player.EnemyProjectile;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PRJ_AttackCone extends EnemyProjectile {


    public PRJ_AttackCone(int worldX, int worldY, int durationTicks, int sizeX, int sizeY, int offsetX, int offsetY, float damage) {
        this.worldPos = new Point2D.Double(worldX, worldY);
        this.duration = durationTicks;
        this.collisionBox = new Rectangle(offsetX, offsetY, sizeX, sizeY);
        this.weapon_damage_percent = damage;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
    }

    /**
     *
     */
    @Override
    public void update() {
        duration--;
        if (duration <= 0) {
            dead = true;
        }
    }
}
