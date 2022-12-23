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

    private final MainGame mg;
    private final Utilities utilities;
    private final int limit = 32;

    public SQLite(MainGame mg) {
        this.mg = mg;
        this.utilities = new Utilities();

    }

    public void readItemsFromDB() {
        Connection conn = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:items.sqlite");
            Statement stmt = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            //search through all tables
            searchAMULET(stmt);
            searchBOOTS(stmt);
            searchCHEST(stmt);
            searchHEAD(stmt);
            searchOFFHAND(stmt);
            searchONEHAND(stmt);
            searchPANTS(stmt);
            searchRELICS(stmt);
            searchRINGS(stmt);
            searchTWOHANDS(stmt);
            inverseArrayLists();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    private void inverseArrayLists() {
        mg.AMULET.sort(Comparator.comparingInt(o -> o.i_id));
        mg.BOOTS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.CHEST.sort(Comparator.comparingInt(o -> o.i_id));
        mg.HEAD.sort(Comparator.comparingInt(o -> o.i_id));
        mg.OFFHAND.sort(Comparator.comparingInt(o -> o.i_id));
        mg.ONEHAND.sort(Comparator.comparingInt(o -> o.i_id));
        mg.PANTS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.RELICS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.RINGS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.TWOHANDS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.AMULET.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.BOOTS.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.CHEST.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.HEAD.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.OFFHAND.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.ONEHAND.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.PANTS.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.RELICS.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.RINGS.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
        mg.TWOHANDS.add(0, new Item(0, "FILLER", 10, "2", "d", "OMEGA", "hey"));
    }

    private void searchAMULET(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_AMULET");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.AMULET.add(0, new_item);
        }
    }

    private void searchBOOTS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_BOOTS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.BOOTS.add(0, new_item);
        }
    }

    private void searchCHEST(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.CHEST.add(0, new_item);
        }
    }

    private void searchHEAD(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_HEAD");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.HEAD.add(0, new_item);
        }
    }

    private void searchOFFHAND(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_OFFHAND");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.OFFHAND.add(0, new_item);
        }
    }

    private void searchONEHAND(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_ONEHAND");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.ONEHAND.add(0, new_item);
        }
    }

    private void searchPANTS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_PANTS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.PANTS.add(0, new_item);
        }
    }

    private void searchRELICS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_RELICS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.RELICS.add(0, new_item);
        }
    }

    private void searchRINGS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_RINGS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.RINGS.add(0, new_item);
        }
    }

    private void searchTWOHANDS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_TWOHANDS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            Item new_item = new Item(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type"), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            if (new_item.description.length() >= limit) {
                new_item.description = new StringBuilder("\"" + new_item.description + "\"").insert(limit, "\n").toString();
            }
            if (new_item.description.length() >= limit * 2) {
                new_item.description = new StringBuilder(new_item.description).insert(limit * 2, "\n").toString();
            }
            new_item.icon = new_item.setup(utilities, new_item.imagePath + ".png");
            mg.TWOHANDS.add(0, new_item);
        }
    }
}