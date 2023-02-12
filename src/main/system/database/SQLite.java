package main.system.database;

import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
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
    public Connection conn;

    public SQLite(MainGame mg) {
        this.mg = mg;
    }


    public void readItemsFromDB() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:MageQuestDB.sqlite");
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
            searchBAGS(stmt);
            searchMISC(stmt);
            inverseArrayLists();
            readPlayerInventory(stmt);
            readPlayerStats(stmt);
            readPlayerBags(stmt);
            readStartLevel(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readItemsOnly() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:MageQuestDB.sqlite");
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String insertNewLine(String str) {
        StringBuilder sb = new StringBuilder();
        String[] words = str.split("\\s+");
        int count = 0;
        for (String word : words) {
            if (count + word.length() > 38) {
                sb.append("\n");
                count = 0;
            }
            count += word.length();
            sb.append(word);
            sb.append(" ");
            count++;
        }
        return "\"" + sb + "\"";
    }

    public void updateQuestFacts(int Quest_ID, int Fact_ID, int Fact_Value) {
        String columnName = "FACT_" + Fact_ID;
        try {
            String sql = "UPDATE QUEST_FACTS SET " + columnName + " = ? where _ROWID_ = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Fact_Value);
            ps.setInt(2, Quest_ID);
            ps.executeUpdate();
        } catch (SQLException ignored) {
            //mg.ui.sqlException();
        }
    }

    private void readStartLevel(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_STATS");
        mg.player.spawnLevel = rs.getInt("startLevel");
    }


    public void savePlayerData() throws SQLException {
        savePlayerInventory();
        saveBagInventory();
        savePlayerStats();
    }

    private void savePlayerStats() throws SQLException {
        String sql = "UPDATE PLAYER_STATS SET coins = ?, experience = ?,startLevel = ? WHERE _ROWID_ = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, mg.player.coins);
        stmt.setInt(2, (int) mg.player.experience);
        stmt.setInt(3, mg.player.spawnLevel);
        stmt.executeUpdate();
    }

    private void saveBagInventory() throws SQLException {
        String sql = "UPDATE PLAYER_BAG SET i_id = ?, type = ?, quality = ?,level = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 1; i <= mg.inventP.bag_Slots.size(); i++) {
            if (mg.inventP.bag_Slots.get(i - 1).item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setInt(5, i);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.bag_Slots.get(i - 1).item.i_id);
            stmt.setString(2, String.valueOf(mg.inventP.bag_Slots.get(i - 1).item.type));
            stmt.setInt(3, mg.inventP.bag_Slots.get(i - 1).item.quality);
            stmt.setInt(4, mg.inventP.bag_Slots.get(i - 1).item.level);
            stmt.setInt(5, i);
            stmt.executeUpdate();
        }
    }

    private void savePlayerInventory() throws SQLException {
        String sql = "UPDATE PLAYER_INV SET i_id = ?, type = ?, quality = ?, level = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < 10; i++) {
            if (mg.inventP.char_Slots[i].item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setInt(5, i + 1);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.char_Slots[i].item.i_id);
            stmt.setString(2, String.valueOf(mg.inventP.char_Slots[i].item.type));
            stmt.setInt(3, mg.inventP.char_Slots[i].item.quality);
            stmt.setInt(4, mg.inventP.char_Slots[i].item.level);
            stmt.setInt(5, i + 1);
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
        mg.BAGS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.MISC.sort(Comparator.comparingInt(o -> o.i_id));
        mg.AMULET.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.BOOTS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.CHEST.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.HEAD.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.OFFHAND.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.ONEHAND.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.PANTS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.RELICS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.RINGS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.TWOHANDS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.BAGS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
        mg.MISC.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey"));
    }

    private void readPlayerInventory(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_INV");
        int counter = 0;
        while (rs.next()) {
            if (rs.getString("i_id") == null) {
                counter++;
                continue;
            }
            mg.inventP.char_Slots[counter].item = getItemWithQuality(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"), rs.getInt("level"));
            counter++;
        }
    }

    private void readPlayerBags(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_BAG");
        int counter = 1;
        while (rs.next()) {
            if (rs.getString("i_id") == null) {
                counter++;
                continue;
            }
            mg.inventP.bag_Slots.get(counter - 1).item = getItemWithQuality(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"), rs.getInt("level"));
            counter++;
        }
    }

    private void readPlayerStats(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_STATS");
        mg.player.coins = rs.getInt("coins");
        mg.player.setLevel(rs.getInt("experience"));
    }

    private ITEM getItemWithQuality(int i_id, String type, int quality, int level) {
        ITEM new_ITEM;
        switch (type) {
            case "A":
                for (ITEM item : mg.AMULET) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
                    }
                }
                break;
            case "B":
                for (ITEM item : mg.BOOTS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "C":
                for (ITEM item : mg.CHEST) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "H":
                for (ITEM item : mg.HEAD) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "O":
                for (ITEM item : mg.OFFHAND) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "W":
                for (ITEM item : mg.ONEHAND) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "P":
                for (ITEM item : mg.PANTS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);

                        return new_ITEM;
                    }
                }
                break;
            case "T":
                for (ITEM item : mg.RELICS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
                    }
                }
                break;
            case "R":
                for (ITEM item : mg.RINGS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
                    }
                }
                break;
            case "2":
                for (ITEM item : mg.TWOHANDS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.AMULET.add(0, new_ITEM);
        }
    }

    private void searchBOOTS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_BOOTS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.BOOTS.add(0, new_ITEM);
        }
    }

    private void searchCHEST(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_CHEST");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.CHEST.add(0, new_ITEM);
        }
    }

    private void searchHEAD(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_HEAD");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.HEAD.add(0, new_ITEM);
        }
    }

    private void searchOFFHAND(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_OFFHAND");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.OFFHAND.add(0, new_ITEM);
        }
    }

    private void searchONEHAND(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_ONEHAND");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.ONEHAND.add(0, new_ITEM);
        }
    }

    private void searchPANTS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_PANTS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.PANTS.add(0, new_ITEM);
        }
    }

    private void searchRELICS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_RELICS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.RELICS.add(0, new_ITEM);
        }
    }

    private void searchRINGS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_RINGS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.RINGS.add(0, new_ITEM);
        }
    }

    private void searchTWOHANDS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_TWOHANDS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.TWOHANDS.add(0, new_ITEM);
        }
    }

    private void searchMISC(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM MISC_ITEMS");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.MISC.add(0, new_ITEM);
        }
    }

    private void searchBAGS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ITEM_BAG");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.BAGS.add(0, new_ITEM);
        }
    }
}