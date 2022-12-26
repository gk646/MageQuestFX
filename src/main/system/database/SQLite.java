package main.system.database;

import gameworld.Item;
import gameworld.world.DroppedItem;
import main.MainGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Comparator;

public class SQLite {

    private final MainGame mg;

    private final int limit = 32;
    private Connection conn;

    public SQLite(MainGame mg) {
        this.mg = mg;
    }


    public void readItemsFromDB() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:items.sqlite");
            Statement stmt = this.conn.createStatement();
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
            readPlayerInventory(stmt);
            readPlayerBags(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayerAndBag() throws SQLException {
        savePlayerInventory();
        saveBagInventory();
    }

    private void saveBagInventory() throws SQLException {
        String sql = "UPDATE PLAYER_BAG SET i_id = ?, type = ?, quality = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < mg.inventP.bag_Slots.length; i++) {
            if (mg.inventP.bag_Slots[i].item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setInt(4, i);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.bag_Slots[i].item.i_id);
            stmt.setString(2, mg.inventP.bag_Slots[i].item.type);
            stmt.setInt(3, mg.inventP.bag_Slots[i].item.quality);
            stmt.setInt(4, i);
            stmt.executeUpdate();
        }
    }

    private void savePlayerInventory() throws SQLException {
        String sql = "UPDATE PLAYER_INV SET i_id = ?, type = ?, quality = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < 10; i++) {
            if (mg.inventP.char_Slots[i].item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setInt(4, i);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.char_Slots[i].item.i_id);
            stmt.setString(2, mg.inventP.char_Slots[i].item.type);
            stmt.setInt(3, mg.inventP.char_Slots[i].item.quality);
            stmt.setInt(4, i);
            stmt.executeUpdate();
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

    private void readPlayerInventory(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_INV");
        int counter = 0;
        while (rs.next()) {
            counter++;
            if (rs.getString("i_id") == null) {
                continue;
            }
            mg.inventP.char_Slots[counter].item = getItemWithQuality(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"));
        }
    }

    private void readPlayerBags(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_BAG");
        int counter = 0;
        while (rs.next()) {
            counter++;
            if (rs.getString("i_id") == null) {
                continue;
            }
            mg.inventP.bag_Slots[counter].item = getItemWithQuality(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"));
        }
    }

    private Item getItemWithQuality(int i_id, String type, int quality) {
        Item new_item;
        switch (type) {
            case "A":
                for (Item item : mg.AMULET) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "B":
                for (Item item : mg.BOOTS) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "C":
                for (Item item : mg.CHEST) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "H":
                for (Item item : mg.HEAD) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "O":
                for (Item item : mg.OFFHAND) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "W":
                for (Item item : mg.ONEHAND) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "P":
                for (Item item : mg.PANTS) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "T":
                for (Item item : mg.RELICS) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "R":
                for (Item item : mg.RINGS) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
            case "2":
                for (Item item : mg.TWOHANDS) {
                    if (item.i_id == i_id) {
                        new_item = DroppedItem.cloneItem(item);
                        new_item.rollQuality(quality);
                        return new_item;
                    }
                }
                break;
        }
        return null;
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
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
            new_item.icon = new_item.setup(mg.utilities, new_item.imagePath + ".png");
            mg.TWOHANDS.add(0, new_item);
        }
    }
}