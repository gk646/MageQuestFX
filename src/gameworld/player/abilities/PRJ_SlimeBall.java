package gameworld.player.abilities;

import gameworld.player.PROJECTILE;
import gameworld.player.ProjectileType;
import javafx.scene.canvas.GraphicsContext;

public class PRJ_SlimeBall extends PROJECTILE {


    PRJ_SlimeBall() {
        projectileType = ProjectileType.OneHitCompletelyDead;
    }

    /**
     */
    @Override
    public void draw(GraphicsContext gc) {

    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     *
     */
    @Override
    public void playHitSound() {

    }
}
