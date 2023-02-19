package main.system.rendering;

import main.MainGame;

import java.awt.Point;
import java.util.ArrayList;

public class WorldAnimation {

    AnimationList bigFish = new AnimationList(new int[]{1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125}, 20);
    AnimationList smallFish = new AnimationList(new int[]{1131, 1132, 1133, 1134, 1135, 1136, 1137, 1138}, 15);

    AnimationList lines3 = new AnimationList(new int[]{1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151}, 40);
    AnimationList lines2 = new AnimationList(new int[]{1157, 1158, 1159, 1160, 1161, 1162, 1163, 1164}, 35);
    AnimationList lines1 = new AnimationList(new int[]{1170, 1171, 1172, 1173, 1174, 1175, 1176, 1177}, 30);
    AnimationList water = new AnimationList(new int[]{911, 1262, 1263, 1264, 1265, 1266, 1267, 1268}, 25);


    ArrayList<AnimationList> animationList = new ArrayList<>();

    MainGame mg;

    public WorldAnimation(MainGame mg) {
        this.mg = mg;
        addAnimations();
    }


    public void cacheAnimationTiles() {
        for (int i = 0; i < WorldRender.worldData.length; i++) {
            for (int j = 0; j < WorldRender.worldData.length; j++) {
                for (AnimationList animList : animationList) {
                    if (contains(animList.tileProgression, WorldRender.worldData[i][j])) {
                        animList.tilesIndices.add(new Point(i, j));
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
            }
        }
        for (int i = 0; i < WorldRender.worldData2.length; i++) {
            for (int j = 0; j < WorldRender.worldData2.length; j++) {
                for (AnimationList animList : animationList) {
                    if (contains(animList.tileProgression, WorldRender.worldData2[i][j])) {
                        animList.tilesIndices2.add(new Point(i, j));
                    }
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

    private void addAnimations() {
        animationList.add(bigFish);
        animationList.add(smallFish);
        animationList.add(lines3);
        animationList.add(lines2);
        animationList.add(lines1);
        animationList.add(water);
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
