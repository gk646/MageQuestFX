package gameworld.maps;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OverWorld {
    public static Point worldSize;
    public static int[][] worldData;

    public static int[][] loadOverWorld() {
        worldSize = new Point(500, 500);
        worldData = new int[worldSize.x][worldSize.y];
        try {
            InputStream inputStream = OverWorld.class.getResourceAsStream("/Maps/overworld.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int num;
            for (int i = 0; i < worldSize.y; i++) {
                String[] numbers = bufferedReader.readLine().split(" ");
                for (int b = 0; b < worldSize.x; b++) {
                    num = Integer.parseInt(numbers[b]);
                    worldData[b][i] = num;
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
