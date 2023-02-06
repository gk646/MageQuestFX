package gameworld.world.maps;

import gameworld.dialogue.SpawnTrigger;
import gameworld.dialogue.Trigger;
import gameworld.dialogue.Type;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MAP_Tutorial {
    private static Point worldSize;


    public static int[][] loadTutorial() {
        worldSize = new Point(99, 99);
        int[][] worldData = new int[worldSize.x][worldSize.y];
        String[] numbers;
        try {
            InputStream inputStream = MAP_OverWorld.class.getResourceAsStream("/Maps/Tutorial.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (int i = 0; i < worldSize.y; i++) {
                numbers = bufferedReader.readLine().split(" ");
                for (int b = 0; b < worldSize.x; b++) {
                    worldData[b][i] = Integer.parseInt(numbers[b]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return worldData;
    }


    public static Point loadMapSize() {
        return new Point(worldSize.x, worldSize.y);
    }

    public static SpawnTrigger[] getTriggers() {
        SpawnTrigger[] spawnTriggers = new SpawnTrigger[15];
        //top left
        spawnTriggers[0] = new SpawnTrigger(47, 9, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[1] = new SpawnTrigger(37, 11, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[2] = new SpawnTrigger(47, 16, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[4] = new SpawnTrigger(37, 21, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[5] = new SpawnTrigger(47, 22, 2, Trigger.SINGULAR, Type.Shooter);

        //top right
        spawnTriggers[6] = new SpawnTrigger(47, 9, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[7] = new SpawnTrigger(37, 11, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[8] = new SpawnTrigger(47, 16, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[9] = new SpawnTrigger(37, 21, 2, Trigger.SINGULAR, Type.Grunt);
        spawnTriggers[10] = new SpawnTrigger(47, 22, 2, Trigger.SINGULAR, Type.Shooter);


        return spawnTriggers;
    }
}
