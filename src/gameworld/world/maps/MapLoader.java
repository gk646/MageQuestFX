package gameworld.world.maps;

import gameworld.quest.SpawnTrigger;
import gameworld.quest.Trigger;
import gameworld.quest.Type;
import gameworld.world.WorldController;
import main.system.enums.Zone;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class MapLoader {
    private static Point worldSize;

    public static int[][] loadMapData(String filename, int worldsize) {
        worldSize = new Point(worldsize, worldsize);
        int[][] worldData = new int[worldSize.x][worldSize.y];
        String[] numbers;
        try (InputStream inputStream = MapLoader.class.getResourceAsStream("/Maps/" + filename + ".csv")) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 32_768)) {
                for (int i = 0; i < worldSize.y; i++) {
                    numbers = bufferedReader.readLine().split(",");
                    for (int b = 0; b < worldSize.x; b++) {
                        worldData[b][i] = Integer.parseInt(numbers[b]) + 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return worldData;
    }


    public static void getTriggers(String fileName) {
        try {
            InputStream inputStream = MapLoader.class.getResourceAsStream("/Maps/" + fileName + ".tmj");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            boolean xfound = false, typefound = false;
            Pattern xfinder;
            Pattern yfinder;
            Pattern namefinder;
            Matcher x;
            Matcher y;
            Matcher name;
            int level = 0;
            Type type = null;
            int counter = 0;
            int newTriggerx = 0, newTriggery = 0;
            xfinder = Pattern.compile("\"x\":([0-9]{0,4})");
            yfinder = Pattern.compile("\"y\":([0-9]{0,4})");
            namefinder = Pattern.compile("\"name\":\"([a-z]{3})([0-9]{0,3})");
            while (line != null) {
                x = xfinder.matcher(line);
                y = yfinder.matcher(line);
                name = namefinder.matcher(line);
                if (!typefound) {
                    if (name.find()) {
                        if (name.group(1).equals("gru")) {
                            type = Type.Grunt;
                        } else if (name.group(1).equals("sho")) {
                            type = Type.Shooter;
                        }
                        level = Integer.parseInt(name.group(2));
                        typefound = true;
                    }
                } else if (!xfound) {
                    if (x.find()) {
                        newTriggerx = Integer.parseInt(x.group(1));
                        xfound = true;
                    }
                } else {
                    if (y.find()) {
                        newTriggery = Integer.parseInt(y.group(1));
                        xfound = false;
                        typefound = false;
                        WorldController.globalTriggers.add(new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SINGULAR, type, Zone.Tutorial));
                        counter++;
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
