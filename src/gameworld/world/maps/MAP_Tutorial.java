package gameworld.world.maps;

import gameworld.quest.SpawnTrigger;
import gameworld.quest.Trigger;
import gameworld.quest.Type;
import main.system.enums.Zone;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MAP_Tutorial {
    private static Point worldSize;


    public static int[][] loadTutorial() {
        worldSize = new Point(100, 100);
        int[][] worldData = new int[worldSize.x][worldSize.y + 1];
        String[] numbers;
        try {
            InputStream inputStream = MAP_OverWorld.class.getResourceAsStream("/Maps/Tutorial.csv");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (int i = 0; i < worldSize.y; i++) {
                numbers = bufferedReader.readLine().split(",");
                for (int b = 0; b < worldSize.x; b++) {
                    worldData[b][i] = Integer.parseInt(numbers[b]) + 1;
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

    public static SpawnTrigger[] getTriggers() throws IOException {
        SpawnTrigger[] spawnTriggers = new SpawnTrigger[50];
        InputStream inputStream = MAP_OverWorld.class.getResourceAsStream("/Maps/Tutorial.tmj");
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
        int counter = 14;
        int newTriggerx = 0, newTriggery = 0;
        xfinder = Pattern.compile("\"x\":([0-9]{0,4})");
        yfinder = Pattern.compile("\"y\":([0-9]{0,4})");
        namefinder = Pattern.compile("\"name\":\"([a-z]{3})([0-9])");
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
                    spawnTriggers[counter] = new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SINGULAR, type, Zone.Tutorial);
                    counter++;
                }
            }
            line = bufferedReader.readLine();
        }
        System.out.println(counter - 14 + " Spawns created!");

        //top left
        spawnTriggers[0] = new SpawnTrigger(47, 9, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[1] = new SpawnTrigger(37, 11, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[2] = new SpawnTrigger(47, 16, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[4] = new SpawnTrigger(37, 21, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[5] = new SpawnTrigger(47, 22, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial);

        //top right
        spawnTriggers[6] = new SpawnTrigger(91, 21, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[7] = new SpawnTrigger(86, 25, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[8] = new SpawnTrigger(97, 27, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[9] = new SpawnTrigger(94, 14, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[10] = new SpawnTrigger(94, 4, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial);


        //middle right
        spawnTriggers[11] = new SpawnTrigger(93, 46, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial);
        spawnTriggers[12] = new SpawnTrigger(87, 45, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial);
        spawnTriggers[13] = new SpawnTrigger(97, 48, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial);


        spawnTriggers[45] = new SpawnTrigger(75, 89, 1, Trigger.SINGULAR, Type.BOSS_Slime, Zone.Tutorial);


        return spawnTriggers;
    }
}
