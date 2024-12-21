/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.statistics;

import main.MainGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameStatistics {
    MainGame mg;
    private double TOTAL_PLAYTIME;
    private int TOTAL_MONSTERS_KILLED;
    private int DISTANCE_TRAVELLED;
    private int ABILITIES_USED;

    public GameStatistics(MainGame mg) {
        this.mg = mg;
    }

    public void saveGameStatistics(Connection playerSaveConnection) throws SQLException {
        String sql = "UPDATE GAME_STATISTICS SET total_playtime = ?, total_killedmonsters = ?,distance_travelled = ? , abilities_used = ? WHERE _ROWID_ = 1";
        try (PreparedStatement stmt = playerSaveConnection.prepareStatement(sql)) {
            stmt.setInt(1, (int) TOTAL_PLAYTIME);
            stmt.setInt(2, TOTAL_MONSTERS_KILLED);
            stmt.setInt(3, DISTANCE_TRAVELLED);
            stmt.setInt(4, ABILITIES_USED);
            stmt.executeUpdate();
        }
    }

    public void loadGameStatistics(Connection playerSaveConnection) throws SQLException {
        try (Statement stmt = playerSaveConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAME_STATISTICS WHERE  _ROWID_ = 1");
            TOTAL_PLAYTIME = rs.getInt("total_playtime");
            TOTAL_MONSTERS_KILLED = rs.getInt("total_killedmonsters");
            DISTANCE_TRAVELLED = rs.getInt("distance_travelled");
            ABILITIES_USED = rs.getInt("abilities_used");
        }
    }

    public void resetGameStatistics(Connection playerSaveConnection) throws SQLException {
        String sql = "UPDATE GAME_STATISTICS SET total_playtime = ?, total_killedmonsters = ?,distance_travelled = ? , abilities_used = ? WHERE _ROWID_ = 1";
        try (PreparedStatement stmt = playerSaveConnection.prepareStatement(sql)) {
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setInt(3, 0);
            stmt.setInt(4, 0);
            stmt.executeUpdate();
        }
    }

    public void updateGameStatistics() {
        TOTAL_PLAYTIME += 0.5;
    }

    public void updateDistanceTravelled(double movementSpeed) {
        DISTANCE_TRAVELLED += movementSpeed;
    }

    public void updateAbilitiesUsed() {
        ABILITIES_USED++;
    }

    public void updateMonstersKilled() {
        TOTAL_MONSTERS_KILLED++;
    }

    public String getPlayTimeFormatted() {
        int hours = (int) (TOTAL_PLAYTIME / 3600);
        int minutes = (int) (TOTAL_PLAYTIME / 60);
        return hours + ":" + (minutes) + ":" + (int) (TOTAL_PLAYTIME - (hours * 3600 + minutes * 60));
    }

    public double getTOTAL_PLAYTIME() {
        return TOTAL_PLAYTIME;
    }

    public int getTOTAL_MONSTERS_KILLED() {
        return TOTAL_MONSTERS_KILLED;
    }


    public String getDISTANCE_TRAVELLED() {
        float kilometers = Math.round(((DISTANCE_TRAVELLED / 48.0f) / 1000.0f) * 100.0f) / 100.0f;
        if (kilometers > 1) {
            return kilometers + " km";
        }
        return DISTANCE_TRAVELLED / 48 + " m";
    }

    public int getABILITIES_USED() {
        return ABILITIES_USED;
    }
}
