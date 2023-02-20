package gameworld.entities;

import gameworld.player.Player;
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")

    public boolean playerTooFarAbsoluteBoss(int x) {
        return Math.abs(worldX - Player.worldX) >= x || Math.abs(worldY - Player.worldY) >= x;
    }

    protected boolean isOnPlayer() {
        return (int) (worldX + 24) / 48 == mg.playerX && (int) (worldY + 24) / 48 == mg.playerY;
    }
}
