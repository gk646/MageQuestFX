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


    public static void getTriggers(String fileName, Zone zone) {
        Pattern xfinder = Pattern.compile("\"x\":(\\d{0,4})");
        Pattern yfinder = Pattern.compile("\"y\":(\\d{0,4})");
        Pattern namefinder = Pattern.compile("\"name\":\"(gru|sho|slimeB)(\\d{0,3})");
        try (InputStream inputStream = MapLoader.class.getResourceAsStream("/Maps/" + fileName + ".tmj");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            Matcher matcher;
            String line;
            int level = 0;
            Type type = null;
            int newTriggerx = 0, newTriggery = 0;
            boolean xfound = false, typefound = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (!typefound) {
                    matcher = namefinder.matcher(line);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        level = Integer.parseInt(matcher.group(2));
                        switch (name) {
                            case "gru" -> type = Type.Grunt;
                            case "sho" -> type = Type.Shooter;
                            case "slimeB" -> type = Type.BOSS_Slime;
                        }
                        typefound = true;
                    }
                } else if (!xfound) {
                    matcher = xfinder.matcher(line);
                    if (matcher.find()) {
                        newTriggerx = Integer.parseInt(matcher.group(1));
                        xfound = true;
                    }
                } else {
                    matcher = yfinder.matcher(line);
                    if (matcher.find()) {
                        newTriggery = Integer.parseInt(matcher.group(1));
                        xfound = false;
                        typefound = false;
                        WorldController.globalTriggers.add(new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SINGULAR, type, zone));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

