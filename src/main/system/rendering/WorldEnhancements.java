/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
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

package main.system.rendering;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.rendering.enhancements.AnimationList;
import main.system.rendering.enhancements.StaticLightSource;
import main.system.ui.Colors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

public class WorldEnhancements {

    private final Image torchs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/effects/torch_effect.png")));

    private final AnimationList bigFish = new AnimationList(new int[]{1_118, 1_119, 1_120, 1_121, 1_122, 1_123, 1_124, 1_125}, 20);
    private final AnimationList smallFish = new AnimationList(new int[]{1_131, 1_132, 1_133, 1_134, 1_135, 1_136, 1_137, 1_138}, 15);
    private final AnimationList lines3 = new AnimationList(new int[]{1_144, 1_145, 1_146, 1_147, 1_148, 1_149, 1_150, 1_151}, 20);
    private final AnimationList lines2 = new AnimationList(new int[]{1_157, 1_158, 1_159, 1_160, 1_161, 1_162, 1_163, 1_164}, 20);
    private final AnimationList lines1 = new AnimationList(new int[]{1_170, 1_171, 1_172, 1_173, 1_174, 1_175, 1_176, 1_177}, 15);
    private final AnimationList water = new AnimationList(new int[]{911, 1_262, 1_263, 1_264, 1_265, 1_266, 1_267, 1_268}, 15);
    private final AnimationList wall_torch = new AnimationList(new int[]{1404, 1405, 1406, 1407, 1408, 1409, 1410, 1411}, 8);
    private final AnimationList campfire = new AnimationList(new int[]{1_528, 1_529, 1_530, 1_531}, 15);
    private final AnimationList flowersred = new AnimationList(new int[]{1235, 1240, 1245, 2140}, 25);
    private final AnimationList flowersred1 = new AnimationList(new int[]{1236, 1241, 1246, 2141}, 25);
    private final AnimationList flowersred2 = new AnimationList(new int[]{1237, 1242, 1247, 2142}, 25);
    private final AnimationList flowersred3 = new AnimationList(new int[]{1238, 1243, 2138, 2143}, 25);
    private final AnimationList flowersred4 = new AnimationList(new int[]{1239, 1244, 2139, 2144}, 25);

    private final AnimationList whiteflower1 = new AnimationList(new int[]{1248, 1253, 1258, 2153}, 25);
    private final AnimationList whiteflower2 = new AnimationList(new int[]{1249, 1254, 1259, 2154}, 25);
    private final AnimationList whiteflower3 = new AnimationList(new int[]{1250, 1255, 1260, 2155}, 25);
    private final AnimationList whiteflower4 = new AnimationList(new int[]{1251, 1256, 2151, 2156}, 25);
    private final AnimationList whiteflower5 = new AnimationList(new int[]{1252, 1257, 2152, 2157}, 25);

    private final AnimationList lavaCasket = new AnimationList(new int[]{95, 96, 97}, 35);
    private final AnimationList lavaBecken = new AnimationList(new int[]{108, 109, 110}, 35);
    private final AnimationList waterCasket = new AnimationList(new int[]{121, 122, 123}, 35);
    private final AnimationList waterBecken = new AnimationList(new int[]{134, 135, 136}, 35);
    private final AnimationList spikes = new AnimationList(new int[]{222, 223, 224, 225}, 35);
    private final StaticLightSource lava = new StaticLightSource(new int[]{95, 95, 97, 108, 109, 110}, Colors.fire_red);
    private final StaticLightSource torch = new StaticLightSource(new int[]{1_404, 1_405, 1_406, 1_407, 1_408, 1_409, 1_410, 1_411}, Colors.fire_red);
    private final StaticLightSource lantern = new StaticLightSource(new int[]{203, 190}, Colors.fire_red);

    private final ArrayList<AnimationList> animationList = new ArrayList<>();
    private final ArrayList<StaticLightSource> lightList = new ArrayList<>();
    private final MainGame mg;

    public WorldEnhancements(MainGame mg) {
        this.mg = mg;
        setupArrays();
    }


    public void cacheMapEnhancements() {
        for (int i = 0; i < WorldRender.worldData.length; i++) {
            for (int j = 0; j < WorldRender.worldData.length; j++) {
                for (AnimationList animList : animationList) {
                    if (contains(animList.tileProgression, WorldRender.worldData[i][j])) {
                        animList.tilesIndices.add(new Point(i, j));
                    }
                }
                for (StaticLightSource lightSource : lightList) {
                    if (contains(lightSource.tileProgression, WorldRender.worldData[i][j])) {
                        lightSource.tilesIndices.add(new Point(i, j));
                    }
                }
            }
        }

        for (int i = 0; i < WorldRender.worldData1.length; i++) {
            for (int j = 0; j < WorldRender.worldData1.length; j++) {
                for (AnimationList animList : animationList) {
                    if (contains(animList.tileProgression, WorldRender.worldData1[i][j])) {
                        animList.tilesIndices1.add(new Point(i, j));
                    }
                }
                for (StaticLightSource lightSource : lightList) {
                    if (contains(lightSource.tileProgression, WorldRender.worldData1[i][j])) {
                        lightSource.tilesIndices1.add(new Point(i, j));
                    }
                }
            }
        }
        for (int i = 0; i < WorldRender.worldData2.length; i++) {
            for (int j = 0; j < WorldRender.worldData2.length; j++) {
                for (AnimationList animList : animationList) {
                    if (contains(animList.tileProgression, WorldRender.worldData2[i][j])) {
                        animList.tilesIndices2.add(new Point(i, j));
                    }
                }
                for (StaticLightSource lightSource : lightList) {
                    if (contains(lightSource.tileProgression, WorldRender.worldData2[i][j])) {
                        lightSource.tilesIndices2.add(new Point(i, j));
                    }
                }
            }
        }
    }

    public void drawLayerOneTwo(GraphicsContext gc) {
        for (StaticLightSource lightSource : lightList) {
            for (Point point : lightSource.tilesIndices) {
                if (Math.abs(mg.playerX - point.x) + (mg.playerY - point.y) < 35) {
                    gc.drawImage(torchs, point.x * 48 - 16 - Player.worldX + Player.screenX, point.y * 48 - 16 - Player.worldY + Player.screenY);
                }
            }
            for (Point point : lightSource.tilesIndices1) {
                if (Math.abs(mg.playerX - point.x) + (mg.playerY - point.y) < 35) {
                    gc.drawImage(torchs, point.x * 48 - 16 - Player.worldX + Player.screenX, point.y * 48 - 16 - Player.worldY + Player.screenY);
                }
            }
            for (Point point : lightSource.tilesIndices2) {
                if (Math.abs(mg.playerX - point.x) + (mg.playerY - point.y) < 35) {
                    gc.drawImage(torchs, point.x * 48 - 16 - Player.worldX + Player.screenX, point.y * 48 - 16 - Player.worldY + Player.screenY);
                }
            }
        }
    }


    public void animateTiles() {
        for (AnimationList animList : animationList) {
            for (int i = 0; i < animList.tilesIndices.size(); i++) {
                Point point = animList.tilesIndices.get(i);
                WorldRender.worldData[point.x][point.y] = animList.progress(WorldRender.worldData[point.x][point.y], i, 0);
            }
        }
        for (AnimationList animList : animationList) {
            for (int i = 0; i < animList.tilesIndices1.size(); i++) {
                Point point = animList.tilesIndices1.get(i);
                WorldRender.worldData1[point.x][point.y] = animList.progress(WorldRender.worldData1[point.x][point.y], i, 1);
            }
        }
        for (AnimationList animList : animationList) {
            for (int i = 0; i < animList.tilesIndices2.size(); i++) {
                Point point = animList.tilesIndices2.get(i);
                WorldRender.worldData2[point.x][point.y] = animList.progress(WorldRender.worldData2[point.x][point.y], i, 2);
            }
        }
    }

    public void emptyAnimationLists() {
        for (AnimationList animList : animationList) {
            animList.tilesIndices.clear();
            animList.tilesIndices1.clear();
            animList.tilesIndices2.clear();
        }
        for (StaticLightSource lightSource : lightList) {
            lightSource.tilesIndices.clear();
            lightSource.tilesIndices1.clear();
            lightSource.tilesIndices2.clear();
        }
    }

    private void setupArrays() {
        animationList.add(bigFish);
        animationList.add(smallFish);
        animationList.add(lines3);
        animationList.add(lines2);
        animationList.add(lines1);
        animationList.add(water);
        animationList.add(lavaCasket);
        animationList.add(lavaBecken);
        animationList.add(waterBecken);
        animationList.add(waterCasket);
        animationList.add(spikes);
        animationList.add(campfire);
        animationList.add(wall_torch);

        animationList.add(flowersred);
        animationList.add(flowersred1);
        animationList.add(flowersred2);
        animationList.add(flowersred3);
        animationList.add(flowersred4);
        animationList.add(whiteflower1);
        animationList.add(whiteflower2);
        animationList.add(whiteflower3);
        animationList.add(whiteflower4);
        animationList.add(whiteflower5);

        lightList.add(torch);
        lightList.add(lantern);
        lightList.add(lava);
    }

    private boolean contains(int[] arr, int value) {
        for (int arrValue : arr) {
            if (value == arrValue) {
                return true;
            }
        }
        return false;
    }
}
