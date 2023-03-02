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

    final AnimationList bigFish = new AnimationList(new int[]{1_118, 1119, 1120, 1121, 1122, 1123, 1124, 1125}, 20);
    final AnimationList smallFish = new AnimationList(new int[]{1131, 1132, 1133, 1134, 1135, 1136, 1137, 1138}, 15);
    final AnimationList lines3 = new AnimationList(new int[]{1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151}, 40);
    final AnimationList lines2 = new AnimationList(new int[]{1157, 1158, 1159, 1160, 1161, 1162, 1163, 1164}, 35);
    final AnimationList lines1 = new AnimationList(new int[]{1170, 1171, 1172, 1173, 1174, 1175, 1176, 1177}, 30);
    final AnimationList water = new AnimationList(new int[]{911, 1262, 1263, 1264, 1265, 1266, 1267, 1268}, 25);
    final AnimationList campfire = new AnimationList(new int[]{1528, 1529, 1530, 1531}, 25);
    final AnimationList lavaCasket = new AnimationList(new int[]{95, 96, 97}, 50);
    final AnimationList lavaBecken = new AnimationList(new int[]{108, 109, 110}, 50);
    final AnimationList waterCasket = new AnimationList(new int[]{121, 122, 123}, 50);
    final AnimationList waterBecken = new AnimationList(new int[]{134, 135, 136}, 50);
    final AnimationList spikes = new AnimationList(new int[]{222, 223, 224, 225}, 60);

    final StaticLightSource lava = new StaticLightSource(new int[]{95, 95, 97, 108, 109, 110}, Colors.fire_red);
    final StaticLightSource torch = new StaticLightSource(new int[]{1404, 1405, 1406, 1407, 1408, 1409, 1410, 1411}, Colors.fire_red);
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
                if (Math.abs(mg.playerX - point.x) < 25 && Math.abs(mg.playerY - point.y) < 25) {
                    gc.drawImage(torchs, point.x * 48 - 11 - Player.worldX + Player.screenX, point.y * 48 - 11 - Player.worldY + Player.screenY);
                }
            }
            for (Point point : lightSource.tilesIndices1) {
                if (Math.abs(mg.playerX - point.x) < 25 && Math.abs(mg.playerY - point.y) < 25) {
                    gc.setEffect(lightSource.glow);
                    gc.fillRect(point.x * 48 + 24, point.y * 48 + 24, 1, 1);
                    gc.setEffect(null);
                }
            }
        }
    }


    public void animateTiles() {
        for (AnimationList animList : animationList) {
            for (Point point : animList.tilesIndices) {
                WorldRender.worldData[point.x][point.y] = animList.progress(WorldRender.worldData[point.x][point.y]);
            }
            for (Point point : animList.tilesIndices1) {
                WorldRender.worldData1[point.x][point.y] = animList.progress(WorldRender.worldData[point.x][point.y]);
            }
            for (Point point : animList.tilesIndices2) {
                WorldRender.worldData2[point.x][point.y] = animList.progress(WorldRender.worldData[point.x][point.y]);
            }
        }
    }

    public void emptyAnimationLists() {
        for (AnimationList animList : animationList) {
            animList.tilesIndices.clear();
            animList.tilesIndices1.clear();
            animList.tilesIndices2.clear();
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
        lightList.add(torch);
        lightList.add(lantern);
        lightList.add(lava);
    }

    public boolean contains(int[] arr, int value) {
        for (int arrValue : arr) {
            if (arrValue == value) {
                return true;
            }
        }
        return false;
    }
}
