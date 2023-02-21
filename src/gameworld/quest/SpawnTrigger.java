package gameworld.quest;

import gameworld.entities.boss.BOS_Slime;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

public class SpawnTrigger {
    private final int x;
    private final int y;
    private final int level;
    private final Trigger trigger;
    private final Type type;
    public final Zone zone;
    private boolean triggered;

    public SpawnTrigger(int x, int y, int level, Trigger trigger, Type type, Zone zone) {
        this.x = x;
        this.y = y;
        this.zone = zone;
        this.level = level;
        this.trigger = trigger;
        this.type = type;
        this.triggered = false;
    }


    public void activate(MainGame mg) {
        if (!triggered && playerXCloseToTile(15, x, y)) {
            if (trigger == Trigger.SINGULAR && type == Type.Grunt) {
                MainGame.ENTITIES.add(new ENT_Grunt(mg, x * 48, y * 48, level, zone));
            } else if (trigger == Trigger.SINGULAR && type == Type.Shooter) {
                MainGame.ENTITIES.add(new ENT_Shooter(mg, x * 48, y * 48, level, zone));
            } else if (trigger == Trigger.SINGULAR && type == Type.BOSS_Slime) {
                MainGame.ENTITIES.add(new BOS_Slime(mg, x * 48, y * 48, level, 150, zone));
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

