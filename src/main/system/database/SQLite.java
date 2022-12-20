package main.system.database;

import gameworld.Item;
import main.MainGame;
import main.system.Utilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;

public class SQLite {


    public MainGame mg;
    public Utilities utilities;

    public SQLite(MainGame mg) {
        this.mg = mg;
        this.utilities = new Utilities();
        readItemsfromDB();
    }

    public void readItemsfromDB() {
        Connection conn = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/resources/items.sqlite");
            Statement stmt = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            // Use the getTables method to retrieve a ResultSet containing information about the tables in the database
            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            // Iterate through the ResultSet and print the table names
            // while (tablesResultSet.next()) {
            // }
            searchARM_CHEST(stmt);
            inverseArrayLists();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Item item : mg.CHEST) {
            // System.out.println(item.imagePath);
        }
    }


    private void inverseArrayLists() {
        mg.CHEST.sort(Comparator.comparingInt(o -> o.i_id));
        mg.CHEST.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA"));
    }

    private void searchARM_CHEST(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"));
            if (new_item.description.length() >= 30) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(30, "\n").toString();
                System.out.println(new_item.description);
            }
            new_item.stats = rs.getString("stats");

            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.CHEST.add(0, new_item);
        }
    }
}

