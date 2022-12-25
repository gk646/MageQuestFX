package gameworld.world.maps;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dungeon1 {
    public static Point worldSize;
    public static int[][] worldData;

    public static int[][] loadDungeon1() {
        worldSize = new Point(500, 500);
        worldData = new int[worldSize.x][worldSize.y];
        String[] numbers;
        try {
            InputStream inputStream = OverWorld.class.getResourceAsStream("/Maps/overworld.txt");
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
}
