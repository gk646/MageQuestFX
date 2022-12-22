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
            InputStream inputStream = OverWorld.class.getResourceAsStream("/resources/maps/map01.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int row = 0;
            int col = 0;
            while (row < worldSize.x && col < worldSize.y) {
                String line = bufferedReader.readLine();
                while (col < worldSize.y) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    worldData[col][row] = num;
                    col++;
                }
                if (col == worldSize.x) {
                    col = 0;
                    row++;
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
