/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld.quest;

import gameworld.entities.boss.BOSS_Knight;
import gameworld.entities.boss.BOSS_Slime;
import gameworld.entities.monsters.ENT_Goblin;
import gameworld.entities.monsters.ENT_Mushroom;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonShield;
import gameworld.entities.monsters.ENT_SkeletonSpearman;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.monsters.ENT_Snake;
import gameworld.entities.monsters.ENT_Wolf;
import gameworld.player.Player;
import gameworld.world.objects.drops.DRP_CoinSack;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

public class SpawnTrigger {
    private final int x;
    private final int y;
    private final int width, height;
    private final Trigger trigger;
    private final Type type;
    private int level;

    public final Zone zone;
    public boolean triggered;

    public SpawnTrigger(int x, int y, int level, Trigger trigger, Type type, Zone zone, int width, int height) {
        this.x = x;
        this.y = y;
        this.zone = zone;
        this.level = level;
        this.width = width;
        this.height = height;
        this.trigger = trigger;
        this.type = type;
        this.triggered = false;
    }


    public void activate(MainGame mg) {
        if (trigger == Trigger.SINGULAR && playerXCloseToTile(14, x, y)) {
            if (level == 0) {
                level = mg.player.level;
            }
            switch (type) {
                case Grunt:
                    mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, x * 48, y * 48, level, zone));
                    break;
                case Shooter:
                    mg.ENTITIES.add(new ENT_SkeletonArcher(mg, x * 48, y * 48, level, zone));
                    break;
                case BOSS_Slime:
                    mg.ENTITIES.add(new BOSS_Slime(mg, x * 48, y * 48, level, 150, zone));
                    break;
                case Spear:
                    mg.ENTITIES.add(new ENT_SkeletonSpearman(mg, x * 48, y * 48, level, zone));
                    break;
                case snake:
                    mg.ENTITIES.add(new ENT_Snake(mg, x, y, level, zone));
                    break;
                case wolf:
                    mg.ENTITIES.add(new ENT_Wolf(mg, x, y, level, zone));
                    break;
                case Mushroom:
                    mg.ENTITIES.add(new ENT_Mushroom(mg, x, y, level, zone));
                    break;
                case WizardBoss1, WizardBoss2:
                    break;
                case KnightBoss:
                    mg.ENTITIES.add(new BOSS_Knight(mg, x * 48, y * 48, level, 150, zone));
                    break;
                case CoinSack:
                    mg.WORLD_DROPS.add(new DRP_CoinSack(x * 48, y * 48, level, zone));
                    break;
                case GoblinGlobe:
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(x * 48, y * 48, mg.MISC.get(5), zone));
                    break;

            }
            triggered = true;
        } else if (trigger == Trigger.SPREAD_Random && isPointWithinDistanceOfRectangle(500)) {
            if (type == Type.MixedGoblin) {
                double num;
                int worldX, worldY;
                for (int i = 0; i < level; i++) {
                    num = Math.random();
                    worldX = (int) (Math.random() * width + x * 48);
                    worldY = (int) (Math.random() * height + y * 48);
                    if (num < 0.15) {
                        mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, worldX, worldY, mg.player.level, zone));
                    } else if (num < 0.3) {
                        mg.ENTITIES.add(new ENT_SkeletonArcher(mg, worldX, worldY, mg.player.level, zone));
                    } else if (num < 0.45) {
                        mg.ENTITIES.add(new ENT_SkeletonShield(mg, worldX, worldY, mg.player.level, zone));
                    } else if (num < 0.6) {
                        mg.ENTITIES.add(new ENT_SkeletonSpearman(mg, worldX, worldY, mg.player.level, zone));
                    } else {
                        mg.ENTITIES.add(new ENT_Goblin(mg, worldX, worldY, mg.player.level, zone));
                    }
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

    private boolean isPointWithinDistanceOfRectangle(int distance) {
        int playerX = (int) Player.worldX;
        int playerY = (int) Player.worldY;
        return (Math.abs(playerX - x * 48) <= distance
                || Math.abs(playerX - x * 48 - width) <= distance
                || Math.abs(playerY - y * 48) <= distance
                || Math.abs(playerY - y * 48 - height) <= distance);
    }
}

