package gameworld.world.maps;

import gameworld.quest.SpawnTrigger;
import gameworld.quest.Trigger;
import gameworld.quest.Type;
import gameworld.world.MapQuadrant;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {
    public GameMapType gameMapType;
    public Zone zone;
    public int[][] mapData;
    public Point mapSize;
    public ArrayList<SpawnTrigger> spawnTriggers;
    public MapQuadrant[] mapQuadrants;
    public int[][] mapCover;
    public String name;

    public Map(String name, Point mapSize, Zone zone) {
        this.name = name;
        this.zone = zone;
        this.gameMapType = GameMapType.NoMapCover;
        this.mapData = loadMapData(name, mapSize.x);
        this.spawnTriggers = getTriggers(name, zone);
        this.mapQuadrants = new MapQuadrant[100];
        this.mapSize = mapSize;
        this.mapCover = new int[mapSize.x][mapSize.x];
    }

    public Map(String name, Point mapSize, Zone zone, GameMapType gameMapType) {
        this.mapSize = mapSize;
        this.gameMapType = gameMapType;
        this.zone = zone;
        this.name = name;
        this.mapData = loadMapData(name, mapSize.x);
        this.spawnTriggers = getTriggers(name, zone);
        this.mapQuadrants = new MapQuadrant[100];
        this.mapCover = new int[mapSize.x][mapSize.x];
        if (gameMapType == GameMapType.MapCover) {
            mapCover = getMapCover();
        }
    }

    public int[][] loadMapData(String filename, int worldSize) {
        int[][] worldData = new int[worldSize][worldSize];
        String[] numbers;
        try (InputStream inputStream = MapLoader.class.getResourceAsStream("/Maps/" + filename + ".csv")) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 32_768)) {
                for (int i = 0; i < worldSize; i++) {
                    numbers = bufferedReader.readLine().split(",");
                    for (int b = 0; b < worldSize; b++) {
                        worldData[b][i] = Integer.parseInt(numbers[b]) + 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return worldData;
    }


    public ArrayList<SpawnTrigger> getTriggers(String fileName, Zone zone) {
        ArrayList<SpawnTrigger> spawnTriggers = new ArrayList<>();
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
                        spawnTriggers.add(new SpawnTrigger(newTriggerx / 16, newTriggery / 16, level, Trigger.SINGULAR, type, zone));
                    }
                }
            }
            return spawnTriggers;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public int[][] getMapCover() {
        Statement stmt;
        try {
            stmt = SQLite.mapCoverConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + name);
            int[][] temp = new int[mapSize.x][mapSize.x];
            int i = 0, j = 1;
            while (rs.next()) {
                temp[i][j] = rs.getInt("value");
                j++;
                if (j == mapSize.x) {
                    i++;
                    j = 0;
                }
                if (i == mapSize.x) {
                    break;
                }
            }
            rs.close();
            stmt.close();
            return temp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMapCover() throws SQLException {
        String sql = "UPDATE " + name + " SET value = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = SQLite.mapCoverConn.prepareStatement(sql);
        for (int i = 0; i < mapCover.length; i++) {
            for (int j = 0; j < mapCover[i].length; j++) {
                stmt.setInt(1, mapCover[i][j]);
                stmt.setInt(2, (i * mapCover.length) + j + 1);
                stmt.executeUpdate();
            }
        }
        stmt.close();
    }

    public void resetMapCover() {
        try {
            String sql = "UPDATE " + name + " SET value = ? WHERE _ROWID_ = ?";
            PreparedStatement stmt = SQLite.mapCoverConn.prepareStatement(sql);
            for (int i = 0; i < mapCover.length; i++) {
                for (int j = 0; j < mapCover[i].length; j++) {
                    stmt.setInt(1, 0);
                    stmt.setInt(2, (i * mapCover.length) + j + 1);
                    stmt.executeUpdate();
                }
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

