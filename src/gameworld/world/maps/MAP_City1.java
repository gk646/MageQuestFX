package gameworld.world.maps;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MAP_City1 {
    private static Point worldSize;
    private static int[][] worldData;

    public static int[][] loadCity() {
        worldSize = new Point(100, 100);
        worldData = new int[worldSize.x][worldSize.y];
        String[] numbers;
        try {
            InputStream inputStream = MAP_OverWorld.class.getResourceAsStream("/Maps/city.txt");
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
}
