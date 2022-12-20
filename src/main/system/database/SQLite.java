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

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:sqlite:src/resources/items.sqlite");

            // Create a statement
            Statement stmt = conn.createStatement();
            // First, get the DatabaseMetaData object for the connection
            DatabaseMetaData metaData = conn.getMetaData();

            // Use the getTables method to retrieve a ResultSet containing information about the tables in the database
            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            // Iterate through the ResultSet and print the table names
            // while (tablesResultSet.next()) {
            // }
            // Execute a SELECT query
            searchARM_CHEST(stmt);
            //this.s_id = String.format("%04d", i_id);
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
        mg.CHEST.add(0, new Item(0, "FILLER", 10, "2", "d", "INT 1000"));
    }

    private void searchARM_CHEST(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");

        // Iterate through the results
        while (rs.next()) {
            if (rs.getInt("i_id") == 0 || rs.getString("imagePath") == null || rs.getString("name") == null) {
                continue;
            }
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("stats"));
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.CHEST.add(0, new_item);
        }

    }
}

