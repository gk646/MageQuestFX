package gameworld.quest;

import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import main.MainGame;

import java.awt.Point;

public class SpawnTrigger {
    private final int x;
    private final int y;
    private final int level;
    private final Trigger trigger;
    private final Type type;
    private boolean triggered;

    public SpawnTrigger(int x, int y, int level, Trigger trigger, Type type) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.trigger = trigger;
        this.type = type;
        this.triggered = false;
    }


    public void activate(MainGame mg) {
        if (!triggered && playerXCloseToTile(7, x, y)) {
            if (trigger == Trigger.SINGULAR && type == Type.Grunt) {
                MainGame.ENTITIES.add(new ENT_Grunt(mg, x * 48, y * 48, level));
            } else if (trigger == Trigger.SINGULAR && type == Type.Shooter) {
                MainGame.ENTITIES.add(new ENT_Shooter(mg, x * 48, y * 48, level));
            }
            triggered = true;
        }
    }

    /**
     * @param distance distance
     * @param tilex    x of tile
     * @param tily     y of tile
     * @return true if the player is X close to (tileX, tileY) or closer
     */
    private boolean playerXCloseToTile(int distance, int tilex, int tily) {
        return new Point((int) ((Player.worldX + 24) / 48), (int) ((Player.worldY + 24) / 48)).distance(tilex, tily) <= distance;
    }
}

