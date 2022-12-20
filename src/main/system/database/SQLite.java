package main.system.database;

import gameworld.Item;
import main.MainGame;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLite {
    public void readItemsfromDB
    MainGame mg;

    {
        ArrayList<Item> ITEMS = new ArrayList<>();

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
            while (tablesResultSet.next()) {
            }
            // Execute a SELECT query
            //this.s_id = String.format("%04d", i_id);
            ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");

            // Iterate through the results
            while (rs.next()) {
                Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("stats"));
                ITEMS.add(0, new_item);
            }
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
        for (Item item : ITEMS) {
            System.out.println(item.stats);
        }
    }

    SQLite(MainGame mg) {
        this.mg = mg;
    }
}
