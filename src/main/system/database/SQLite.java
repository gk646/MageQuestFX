package main.system.database;

import java.sql.*;

public class SQLite {
    public static void main(String[] args) {
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
                String tableName = tablesResultSet.getString("TABLE_NAME");
                System.out.println("Table name: " + tableName);
            }

            // Execute a SELECT query
            ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");

            // Iterate through the results
            while (rs.next()) {
                int i_id = rs.getInt("i_id");
                String name = rs.getString("name");
                int rarity = rs.getInt("rarity");
                String type = rs.getString("type");
                String imagePath = rs.getString("imagePath");
                System.out.println(name);
                // Process the retrieved values ...
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
    }
}
