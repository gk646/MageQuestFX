package gameworld.entities.damage.effects;

import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_ChestItem;
import main.MainGame;
import main.system.rendering.WorldRender;
import main.system.rendering.enhancements.ScriptedAnimationList;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TileBasedEffects {
    public static int activeTile = 0;
    public static int activeTile1 = 0;
    int[] tilesData, tilesData1, tilesData2;
    ArrayList<ScriptedAnimationList> animationList = new ArrayList<>();
    MainGame mg;

    public TileBasedEffects(MainGame mg) {
        this.mg = mg;
    }


    public void update() {
        activeTile = WorldRender.worldData[mg.playerX][mg.playerY];
        activeTile1 = WorldRender.worldData1[mg.playerX][mg.playerY];
        checkForTileEffects();
        if (animationList.size() > 0) {
            Iterator<ScriptedAnimationList> iter = animationList.iterator();
            while (iter.hasNext()) {
                ScriptedAnimationList list = iter.next();
                if (list.finished) {
                    iter.remove();
                } else {
                    list.progress();
                }
            }
        }
    }

    public void getNearbyTiles() {
        int[][] copy = WorldRender.worldData;
        int[][] copy1 = WorldRender.worldData1;
        int[][] copy2 = WorldRender.worldData2;
        tilesData = getValuesInRadius(copy, mg.playerX, mg.playerY);
        tilesData1 = getValuesInRadius(copy1, mg.playerX, mg.playerY);
        tilesData2 = getValuesInRadius(copy2, mg.playerX, mg.playerY);
    }

    private void checkForTileEffects() {
        mg.playerPrompts.E = false;
        if (activeTile == 225) {
            mg.player.health -= (mg.player.maxHealth * 0.4f);
            mg.sound.playEffectSound(6);
        } else if (activeTile1 == 137) {
            mg.playerPrompts.E = true;
            if (mg.inputH.e_typed && animationList.size() == 0) {
                mg.WORLD_DROPS.add(new DRP_ChestItem(mg, mg.playerX * 48 + 24, mg.playerY * 48 + 24, WorldController.currentWorld, mg.player.level));
                mg.sound.playEffectSound(4);
                animationList.add(new ScriptedAnimationList(new int[]{137, 138, 139}, 15, new Point(mg.playerX, mg.playerY)));
            }
        } else if (activeTile1 == 1382) {
            mg.playerPrompts.E = true;
            if (mg.inputH.e_typed && animationList.size() == 0) {
                animationList.add(new ScriptedAnimationList(new int[]{1382, 1383}, 15, new Point(mg.playerX, mg.playerY)));
            }
        }
    }

    public boolean isLavaNearby() {
        for (int i = 0; i < tilesData.length; i++) {
            int num1 = tilesData[i];
            int num2 = tilesData1[i];
            int num3 = tilesData2[i];
            if ((num1 >= 95 && num1 <= 97) || (num2 >= 95 && num2 <= 97) || (num3 >= 95 && num3 <= 97)) {
                return true;
            }
            if ((num1 >= 108 && num1 <= 110) || (num2 >= 108 && num2 <= 110) || (num3 >= 108 && num3 <= 110)) {
                return true;
            }
        }
        return false;
    }

    public boolean isWaterNearby() {
        for (int i = 0; i < tilesData.length; i++) {
            int num1 = tilesData[i];
            int num2 = tilesData1[i];
            int num3 = tilesData2[i];
            if ((num1 >= 910 && num1 <= 1_035) || (num2 >= 910 && num2 <= 1_035) || (num3 >= 910 && num3 <= 1_035)) {
                return true;
            }
            if ((num1 >= 1_118 && num1 <= 1177) || (num2 >= 1118 && num2 <= 1177) || (num3 >= 1118 && num3 <= 1177)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInOpen() {
        return !(activeTile >= 9 && activeTile <= 300);
    }


    private int[] getValuesInRadius(int[][] array, int centerX, int centerY) {
        int radius = 7;
        int size = (radius * 2 + 1) * (radius * 2 + 1);
        int[] values = new int[size];
        int index = 0;

        for (int i = centerX - radius; i <= centerX + radius; i++) {
            for (int j = centerY - radius; j <= centerY + radius; j++) {
                if (i >= 0 && i < array.length && j >= 0 && j < array[0].length) {
                    // Calculate the distance between the current point and the center point
                    double distance = Math.sqrt((i - centerX) * (i - centerX) + (j - centerY) * (j - centerY));

                    // If the distance is less than or equal to the radius, add the value to the array
                    if (distance <= radius) {
                        values[index] = array[i][j];
                        index++;
                    }
                }
            }
        }

        return Arrays.copyOf(values, index);
    }
}
