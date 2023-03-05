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

    final Image torchs = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/effects/torch_effect.png")));

    final AnimationList bigFish = new AnimationList(new int[]{1_118, 1_119, 1_120, 1_121, 1_122, 1_123, 1_124, 1_125}, 20);
    final AnimationList smallFish = new AnimationList(new int[]{1_131, 1_132, 1_133, 1_134, 1_135, 1_136, 1_137, 1_138}, 15);
    final AnimationList lines3 = new AnimationList(new int[]{1_144, 1_145, 1_146, 1_147, 1_148, 1_149, 1_150, 1_151}, 20);
    final AnimationList lines2 = new AnimationList(new int[]{1_157, 1_158, 1_159, 1_160, 1_161, 1_162, 1_163, 1_164}, 20);
    final AnimationList lines1 = new AnimationList(new int[]{1_170, 1_171, 1_172, 1_173, 1_174, 1_175, 1_176, 1_177}, 15);
    final AnimationList water = new AnimationList(new int[]{911, 1_262, 1_263, 1_264, 1_265, 1_266, 1_267, 1_268}, 15);
    final AnimationList campfire = new AnimationList(new int[]{1_528, 1_529, 1_530, 1_531}, 8);
    final AnimationList wall_torch = new AnimationList(new int[]{1404, 1405, 1406, 1407, 1408, 1409, 1410, 1411}, 8);
    final AnimationList lavaCasket = new AnimationList(new int[]{95, 96, 97}, 35);
    final AnimationList lavaBecken = new AnimationList(new int[]{108, 109, 110}, 35);
    final AnimationList waterCasket = new AnimationList(new int[]{121, 122, 123}, 35);
    final AnimationList waterBecken = new AnimationList(new int[]{134, 135, 136}, 35);
    final AnimationList spikes = new AnimationList(new int[]{222, 223, 224, 225}, 35);
    final StaticLightSource lava = new StaticLightSource(new int[]{95, 95, 97, 108, 109, 110}, Colors.fire_red);
    final StaticLightSource torch = new StaticLightSource(new int[]{1_404, 1_405, 1_406, 1_407, 1_408, 1_409, 1_410, 1_411}, Colors.fire_red);
    final StaticLightSource lantern = new StaticLightSource(new int[]{203, 190}, Colors.fire_red);

    final ArrayList<AnimationList> animationList = new ArrayList<>();
    final ArrayList<StaticLightSource> lightList = new ArrayList<>();
    final MainGame mg;

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
                    gc.drawImage(torchs, point.x * 48 - 11 - Player.worldX + Player.screenX, point.y * 48 - 11 - Player.worldY + Player.screenY);
                }
            }
            for (Point point : lightSource.tilesIndices1) {
                if (Math.abs(mg.playerX - point.x) + (mg.playerY - point.y) < 35) {
                    gc.drawImage(torchs, point.x * 48 - 11 - Player.worldX + Player.screenX, point.y * 48 - 11 - Player.worldY + Player.screenY);
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
        lightList.add(torch);
        lightList.add(lantern);
        lightList.add(lava);
    }

    public boolean contains(int[] arr, int value) {
        for (int arrValue : arr) {
            if (value == arrValue) {
                return true;
            }
        }
        return false;
    }
}
