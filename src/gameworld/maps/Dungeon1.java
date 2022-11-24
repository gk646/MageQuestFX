package gameworld.maps;

import gameworld.World;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dungeon1 extends World {
    public static int[][] loadMap() {
        worldSize = new Point(50, 50);
        worldData = new int[worldSize.x][worldSize.y];
        try {
            InputStream inputStream = Dungeon1.class.getResourceAsStream("/resources/maps/map1.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int row = 0;
            int col = 0;
            while (row < 100 && col < 100) {
                String line = bufferedReader.readLine();
                while (col < 100) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    worldData[col][row] = num;
                    col++;
                }
                if (col == 100) {
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

}
