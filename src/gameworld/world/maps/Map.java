package gameworld.world.maps;

import gameworld.quest.SpawnTrigger;
import gameworld.quest.Trigger;
import gameworld.quest.Type;
import main.system.database.SQLite;
import main.system.enums.GameMapType;
import main.system.enums.Zone;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {
    public final GameMapType gameMapType;
    public final Zone zone;
    public int[][] mapDataBackGround;
    public int[][] mapDataBackGround2;
    public int[][] mapDataForeGround;
    public final Point mapSize;
    public final ArrayList<SpawnTrigger> spawnTriggers;
    // public final MapQuadrant[] mapQuadrants;
    public int[][] mapCover;
    private final String name;

    public Map(String name, Point mapSize, Zone zone, GameMapType gameMapType) {
        this.mapSize = mapSize;
        this.gameMapType = gameMapType;
        this.zone = zone;
        this.name = name;
        loadMap();
        this.spawnTriggers = getTriggers(name, zone);
        this.mapCover = new int[mapSize.x][mapSize.x];
        if (gameMapType == GameMapType.MapCover) {
            mapCover = getMapCover();
        }
    }

    public Map(Point mapSize, Zone zone, int[][] FG, int[][] BG1, int[][] BG) {
        this("", mapSize, zone, GameMapType.NoMapCover);
        this.mapDataForeGround = FG;
        this.mapDataBackGround2 = BG1;
        this.mapDataBackGround = BG;
    }

    private int[][] loadMapData(String filename, int worldSize) {
        int[][] worldData = new int[worldSize][worldSize];
        String[] numbers;
        try (InputStream inputStream = Map.class.getResourceAsStream("/Maps/" + name + "/" + filename + ".csv")) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)), 32_768)) {
                for (int i = 0; i < worldSize; i++) {
                    numbers = bufferedReader.readLine().split(",");
                    for (int b = 0; b < worldSize; b++) {
                        worldData[b][i] = Integer.parseInt(numbers[b]);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return worldData;
    }

    private void loadMap() {
        if (!name.equals("")) {
            this.mapDataForeGround = loadMapData(name + "_FG", mapSize.x);
            this.mapDataBackGround2 = loadMapData(name + "_BG1", mapSize.x);
            this.mapDataBackGround = loadMapData(name + "_BG", mapSize.x);
        }
    }

    private ArrayList<SpawnTrigger> getTriggers(String fileName, Zone zone) {
        ArrayList<SpawnTrigger> spawnTriggers = new ArrayList<>();
        Pattern xfinder = Pattern.compile("\"x\":(\\d{0,4})");
        Pattern yfinder = Pattern.compile("\"y\":(\\d{0,4})");
        Pattern widthfinder = Pattern.compile("\"width\":(\\d{0,4})");
        Pattern heightfinder = Pattern.compile("\"height\":(\\d{0,4})");
        Pattern namefinder = Pattern.compile("\"name\":\"(\\D+)(\\d{0,3})");
        try (InputStream inputStream = Map.class.getResourceAsStream("/Maps/" + name + "/" + fileName + ".tmj");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            Matcher matcher;
            String line;
            int width = 0, height = 0;
            int level = 0;
            Type type = null;
            int newTriggerx = 0, newTriggery;
            boolean xfound = false, typefound = false, heightfound = false, widthfound = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (!heightfound) {
                    matcher = heightfinder.matcher(line);
                    if (matcher.find()) {
                        height = Integer.parseInt(matcher.group(1));
                        heightfound = true;
                    }
                }
                if (!widthfound) {
                    matcher = widthfinder.matcher(line);
                    if (matcher.find()) {
                        width = Integer.parseInt(matcher.group(1));
                        widthfound = true;
                    }
                }
                if (!typefound) {
                    matcher = namefinder.matcher(line);
                    if (matcher.find()) {
                        String name = matcher.group(1);
                        switch (name) {
                            case "gru" -> type = Type.Grunt;
                            case "sho" -> type = Type.Shooter;
                            case "slimeboss" -> type = Type.BOSS_Slime;
                            case "spear" -> type = Type.Spear;
                            case "snake" -> type = Type.snake;
                            case "wolf" -> type = Type.wolf;
                            case "bossKnight" -> type = Type.KnightBoss;
                            case "mushroom" -> type = Type.Mushroom;
                            case "mixedGoblin" -> type = Type.MixedGoblin;
                            case "coinSack" -> type = Type.CoinSack;
                            case "goblinglobe" -> type = Type.GoblinGlobe;
                        }
                        if (type != null) {
                            level = Integer.parseInt(matcher.group(2));
                        } else {
                            level = 1;
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
                        if (type != Type.MixedGoblin) {
                            spawnTriggers.add(new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SINGULAR, type, zone, width * 3, height * 3));
                        } else {
                            spawnTriggers.add(new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SPREAD_Random, type, zone, width * 3, height * 3));
                        }
                        type = null;
                        widthfound = false;
                        heightfound = false;
                    }
                }
            }
            return spawnTriggers;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private int[][] getMapCover() {

        String prefix = "Z_MAPCOVER_";
        try {
            Statement stmt;
            stmt = SQLite.PLAYER_SAVE.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + prefix

                    + name);
            int[][] temp = new int[mapSize.x][mapSize.x];
            int length = mapSize.x;
            if (length > 100) {
                int i = 0, j = 0;
                int value;
                while (rs.next()) {
                    value = rs.getInt("value");
                    if (value == 1) {
                        for (int k = i; k < Math.min(length, i + 10); k++) {
                            for (int l = j; l < Math.min(length, j + 10); l++) {
                                temp[l][k] = 1;
                            }
                        }
                    }
                    j += 10;
                    if (j >= length) {
                        j = 0;
                        i += 10;
                    }
                }
            } else {
                int i = 0, j = 1;
                while (rs.next()) {
                    temp[i][j] = rs.getInt("value");
                    j++;
                    if (j == length) {
                        i++;
                        j = 0;
                    }
                    if (i == length) {
                        break;
                    }
                }
            }
            rs.close();
            stmt.close();
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveMapCover() throws SQLException {

        String prefix = "Z_MAPCOVER_";
        String sql = "UPDATE " + prefix + name + " SET value = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = SQLite.PLAYER_SAVE.prepareStatement(sql);
        int length = mapSize.x;
        if (length > 100) {
            int counter = 1;
            for (int i = 0; i < length; i += 10) {
                for (int j = 0; j < length; j += 10) {
                    stmt.setInt(1, mapCover[j][i]);
                    stmt.setInt(2, counter);
                    stmt.executeUpdate();
                    counter++;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    stmt.setInt(1, mapCover[i][j]);
                    stmt.setInt(2, (i * length) + j + 1);
                    stmt.executeUpdate();
                }
            }
        }
        stmt.close();
    }

    private void openMap() {
        int length = mapCover.length;
        for (int i = 0; i < length; i++) {
            for (int l = 0; l < length; l++) {
                mapCover[i][l] = 1;
            }
        }
    }
}

