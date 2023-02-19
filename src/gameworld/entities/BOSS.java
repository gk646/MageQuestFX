package gameworld.entities;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;


abstract public class BOSS extends ENTITY {


    public BOSS(MainGame mg, int x, int y, int level, int health, Zone zone) {
        this.mg = mg;
        this.zone = zone;
        this.worldX = x;
        this.worldY = y;
        this.level = level;
        this.maxHealth = ((9 + level) * (level + level - 1)) * 15;
        movementSpeed = 2;
        this.health = maxHealth;
        this.direction = "leftrightdownup";
    }

    /**
     * @param gc Graphics context
     */
    @Override
    abstract public void draw(GraphicsContext gc);


    /**
     *
     */
    @Override
    public void update() {
        tickEffects();
    }
}
