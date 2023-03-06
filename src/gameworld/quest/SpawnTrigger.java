package gameworld.quest;

import gameworld.entities.boss.BOSS_Slime;
import gameworld.entities.monsters.ENT_Mushroom;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonSpearman;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.monsters.ENT_Snake;
import gameworld.entities.monsters.ENT_Wolf;
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
    public boolean triggered;

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
        if (!triggered && playerXCloseToTile(14, x, y)) {
            if (trigger == Trigger.SINGULAR) {
                if (type == Type.Grunt) {
                    mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, x * 48, y * 48, level, zone));
                } else if (type == Type.Shooter) {
                    mg.ENTITIES.add(new ENT_SkeletonArcher(mg, x * 48, y * 48, level, zone));
                } else if (type == Type.BOSS_Slime) {
                    mg.ENTITIES.add(new BOSS_Slime(mg, x * 48, y * 48, level, 150, zone));
                } else if (type == Type.Spear) {
                    mg.ENTITIES.add(new ENT_SkeletonSpearman(mg, x * 48, y * 48, level, zone));
                } else if (type == Type.snake) {
                    mg.ENTITIES.add(new ENT_Snake(mg, x, y, level, zone));
                } else if (type == Type.wolf) {
                    mg.ENTITIES.add(new ENT_Wolf(mg, x, y, level, zone));
                } else if (type == Type.Mushroom) {
                    mg.ENTITIES.add(new ENT_Mushroom(mg, x, y, level, zone));
                } else if (type == Type.WizardBoss1) {

                } else if (type == Type.WizardBoss2) {

                } else if (type == Type.KnightBoss) {

                }
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

