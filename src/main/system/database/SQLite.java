package main.system.database;

import gameworld.player.Player;
import gameworld.quest.QUEST;
import gameworld.world.maps.Map;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
import javafx.application.Platform;
import main.MainGame;
import main.system.enums.GameMapType;
import main.system.ui.skillbar.SKILL;
import main.system.ui.skillbar.skills.SKL_Filler;
import main.system.ui.talentpanel.TALENT;
import main.system.ui.talentpanel.TalentNode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class SQLite {

    private final MainGame mg;
    public Connection conn;
    public static Connection mapCoverConn;

    public SQLite(MainGame mg) {
        this.mg = mg;
    }

    public void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:MageQuestDB.sqlite");
            mapCoverConn = DriverManager.getConnection("jdbc:sqlite:MageQuestMapCoverDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readAllGameData() {
        try {
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
            readPlayerBagEquip(stmt);
            inverseArrayLists();
            readPlayerStats(stmt);
            readSkillTree(stmt);
            readSettings();
        } catch (SQLException e) {
            e.printStackTrace();
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
            searchBAGS(stmt);
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
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void setQuestActive(int Quest_ID) {
        try {
            String sql = "UPDATE QUEST_FACTS SET DESCRIPTION = ?  where _ROWID_ = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "active");
            ps.setInt(2, Quest_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void finishQuest(int Quest_ID) {
        try {
            String sql = "UPDATE QUEST_FACTS SET DESCRIPTION = ?  where _ROWID_ = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "finished");
            ps.setInt(2, Quest_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * @param Quest_id the quests id
     * @param fact_id  the quests fact id
     * @return the number value at this place in the table
     */
    public int readQuestFacts(int Quest_id, int fact_id) {
        String columnName = "FACT_" + fact_id;
        ResultSet rs;
        try {
            String sql = " SELECT " + columnName + " FROM  QUEST_FACTS  WHERE _ROWID_ = " + Quest_id;
            rs = conn.createStatement().executeQuery(sql);
            return rs.getInt(columnName);
        } catch (SQLException ignored) {
            // mg.ui.sqlException();
        }
        return -1;
    }

    public String readQuestDescription(int Quest_id) {
        ResultSet rs;
        try {
            String sql = "SELECT DESCRIPTION FROM QUEST_FACTS WHERE _ROWID_ = " + Quest_id;
            rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                String description = rs.getString("DESCRIPTION");
                if (rs.wasNull()) {
                    return "null";
                } else {
                    return description;
                }
            } else {
                return "null";
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    public int readStartLevel() {
        ResultSet rs;
        try {
            String sql = "SELECT * FROM PLAYER_STATS LIMIT 1 ";
            rs = conn.createStatement().executeQuery(sql);
            return rs.getInt("startLevel");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    private void saveGameData() {
        try {
            savePlayerInventory();
            saveBagInventory();
            savePlayerStats();
            saveTalentTree();
            saveSkillPanel();
            saveQuests();
            saveSettings();
            for (Map map : mg.wControl.MAPS) {
                if (map.gameMapType == GameMapType.MapCover) {
                    map.saveMapCover();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSettings() throws SQLException {
        String sql = "UPDATE SETTINGS_SAVE SET AudioSettings = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, (int) mg.ui.musicSlider);
        stmt.setInt(2, 1);
        stmt.executeUpdate();
        stmt.setInt(1, (int) mg.ui.ambientSlider);
        stmt.setInt(2, 2);
        stmt.executeUpdate();
        stmt.setInt(1, (int) mg.ui.effectsSlider);
        stmt.setInt(2, 3);
        stmt.executeUpdate();
    }

    private void readSettings() throws SQLException {

        for (int i = 1; i < 4; i++) {
            String sql = "SELECT AudioSettings FROM  SETTINGS_SAVE WHERE _ROWID_ =" + i;
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (i == 1) {
                mg.ui.musicSlider = rs.getInt("AudioSettings") > 0 ? rs.getInt("AudioSettings") : 100;
                mg.ui.musicSliderHitBox.x = (int) (650 + mg.ui.musicSlider * 2 - 12);
            } else if (i == 2) {
                mg.ui.ambientSlider = rs.getInt("AudioSettings") > 0 ? rs.getInt("AudioSettings") : 75;
                mg.ui.ambientSliderHitBox.x = (int) (650 + mg.ui.ambientSlider * 2 - 12);
            } else {
                mg.ui.effectsSlider = rs.getInt("AudioSettings") > 0 ? rs.getInt("AudioSettings") : 75;
                mg.ui.effectsSliderHitBox.x = (int) (650 + mg.ui.effectsSlider * 2 - 12);
            }
        }
    }

    private void saveQuests() throws SQLException {
        String sql = "UPDATE QUEST_FACTS SET JournalText = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        String delimiter;
        String myString;
        for (QUEST quest : mg.qPanel.quests) {
            delimiter = ">";
            String[] nonNullValues = Arrays.stream(quest.questRecap)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
            myString = String.join(delimiter, nonNullValues);
            stmt.setString(1, myString);
            stmt.setInt(2, quest.quest_id);
            stmt.executeUpdate();
        }
    }

    public String[] readQuestJournal(int questID, int fixedSize) {
        try {
            String sql = "SELECT JournalText FROM QUEST_FACTS WHERE _ROWID_ = " + questID;
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            String[] entries = rs.getString("JournalText").split(">");
            String[] result = new String[fixedSize];
            System.arraycopy(entries, 0, result, 0, entries.length);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return new String[150];
        }
    }


    private void saveSkillPanel() throws SQLException {
        String sql = "UPDATE SKL_Skills SET skill_index = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        int counter = 1;
        for (SKILL skill : mg.skillPanel.arcaneSkills) {
            if (!(skill instanceof SKL_Filler)) {
                stmt.setInt(1, getSkillNumber(skill));
                stmt.setInt(2, counter);
                stmt.executeUpdate();
                counter++;
            }
        }
        for (SKILL skill : mg.skillPanel.fireSkills) {
            if (!(skill instanceof SKL_Filler)) {
                stmt.setInt(1, getSkillNumber(skill));
                stmt.setInt(2, counter);
                stmt.executeUpdate();
                counter++;
            }
        }
        for (SKILL skill : mg.skillPanel.poisonSkills) {
            if (!(skill instanceof SKL_Filler)) {
                stmt.setInt(1, getSkillNumber(skill));
                stmt.setInt(2, counter);
                stmt.executeUpdate();
                counter++;
            }
        }
        for (SKILL skill : mg.skillPanel.iceSkills) {
            if (!(skill instanceof SKL_Filler)) {
                stmt.setInt(1, getSkillNumber(skill));
                stmt.setInt(2, counter);
                stmt.executeUpdate();
                counter++;
            }
        }
        for (SKILL skill : mg.skillPanel.darkSkills) {
            if (!(skill instanceof SKL_Filler)) {
                stmt.setInt(1, getSkillNumber(skill));
                stmt.setInt(2, counter);
                stmt.executeUpdate();
                counter++;
            }
        }
        sql = "UPDATE SKL_Skills SET activeSkills = ? WHERE _ROWID_ = ?";
        stmt = conn.prepareStatement(sql);
        counter = 1;
        for (SKILL skill : mg.sBar.skills) {
            if (skill instanceof SKL_Filler) {
                stmt.setNull(1, getSkillNumber(skill));
            } else {
                stmt.setInt(1, getSkillNumber(skill));
            }
            stmt.setInt(2, counter);
            stmt.executeUpdate();
            counter++;
        }
    }


    private int getSkillNumber(SKILL skill) {
        for (int i = 0; i < mg.skillPanel.allSkills.length; i++) {
            if (skill.getClass() == mg.skillPanel.allSkills[i].getClass()) {
                return i;
            }
        }
        return 0;
    }

    public void readSKillPanel(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT skill_index FROM  SKL_Skills ");
        while (rs.next()) {
            int num = rs.getInt("skill_Index");
            if (rs.wasNull()) {
                break;
            } else {
                mg.skillPanel.addSKill(mg.skillPanel.allSkills[rs.getInt("skill_Index")]);
            }
        }
        rs = stmt.executeQuery("SELECT activeSkills FROM  SKL_Skills ");
        while (rs.next()) {
            int num = rs.getInt("activeSkills");
            if (!rs.wasNull()) {
                mg.sBar.skills[rs.getRow() - 1] = mg.skillPanel.allSkills[rs.getInt("activeSkills")];
            }
        }
    }

    public void saveGame() {
        Thread saveThread = new Thread(() -> {
            mg.ui.drawSaveMessage = true;
            saveGameData();
            mg.ui.saveMessageStage = 400;
        });
        saveThread.start();
    }

    public void saveGameAndExit() {
        Thread saveThread = new Thread(() -> {
            mg.ui.drawSaveMessage = true;
            saveGameData();
            mg.ui.saveMessageStage = 400;
            System.exit(0);
        });
        saveThread.start();
        Platform.exit();
    }

    public void savePlayerStats() throws SQLException {
        String sql = "UPDATE PLAYER_STATS SET coins = ?, experience = ?,startLevel = ? , talentPointsToSpend = ? WHERE _ROWID_ = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, mg.player.coins);
        stmt.setInt(2, (int) mg.player.experience);
        stmt.setInt(3, mg.player.spawnLevel);
        stmt.setInt(4, mg.talentP.pointsToSpend);
        stmt.executeUpdate();
    }

    private void saveBagInventory() throws SQLException {
        String sql = "UPDATE PLAYER_BAG SET i_id = ?, type = ?, quality = ?,level = ?, effect = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 1; i <= mg.inventP.bag_Slots.size(); i++) {
            if (mg.inventP.bag_Slots.get(i - 1).item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setString(5, null);
                stmt.setInt(6, i);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.bag_Slots.get(i - 1).item.i_id);
            stmt.setString(2, String.valueOf(mg.inventP.bag_Slots.get(i - 1).item.type));
            stmt.setInt(3, mg.inventP.bag_Slots.get(i - 1).item.quality);
            stmt.setInt(4, mg.inventP.bag_Slots.get(i - 1).item.level);
            stmt.setString(5, getEffectString(mg.inventP.bag_Slots.get(i - 1).item));
            stmt.setInt(6, i);
            stmt.executeUpdate();
        }
    }

    private String getEffectString(ITEM item) {
        StringBuilder effect = new StringBuilder();
        for (int i = 1; i < Player.effectsSizeRollable; i++) {
            if (item.effects[i] != 0) {
                effect.append("[").append(i).append("]").append(item.effects[i]);
            }
        }
        if (effect.toString().length() > 0) {
            return effect.toString();
        } else {
            return null;
        }
    }

    private void savePlayerInventory() throws SQLException {
        String sql = "UPDATE PLAYER_INV SET i_id = ?, type = ?, quality = ?, level = ?, effect = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < 10; i++) {
            if (mg.inventP.char_Slots[i].item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setString(5, null);
                stmt.setInt(6, i + 1);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.char_Slots[i].item.i_id);
            stmt.setString(2, String.valueOf(mg.inventP.char_Slots[i].item.type));
            stmt.setInt(3, mg.inventP.char_Slots[i].item.quality);
            stmt.setInt(4, mg.inventP.char_Slots[i].item.level);
            stmt.setString(5, getEffectString(mg.inventP.char_Slots[i].item));
            stmt.setInt(6, i + 1);
            stmt.executeUpdate();
        }
        for (int i = 0; i < 4; i++) {
            if (mg.inventP.bagEquipSlots[i].item == null) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setString(2, null);
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setString(5, null);
                stmt.setInt(6, i + 12);
                stmt.executeUpdate();
                continue;
            }
            stmt.setInt(1, mg.inventP.bagEquipSlots[i].item.i_id);
            stmt.setString(2, String.valueOf(mg.inventP.bagEquipSlots[i].item.type));
            stmt.setInt(3, mg.inventP.bagEquipSlots[i].item.quality);
            stmt.setInt(4, mg.inventP.bagEquipSlots[i].item.level);
            stmt.setString(5, null);
            stmt.setInt(6, i + 12);
            stmt.executeUpdate();
        }
    }

    private void saveTalentTree() throws SQLException {
        String sql = "UPDATE TALENTS SET activated = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < mg.talentP.talent_Nodes.length; i++) {
            if (mg.talentP.talent_Nodes[i] != null) {
                if (mg.talentP.talent_Nodes[i].activated) {
                    stmt.setInt(1, 1);
                } else {
                    stmt.setInt(1, 0);
                }
                stmt.setString(2, String.valueOf(i));
                stmt.executeUpdate();
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
        mg.BAGS.sort(Comparator.comparingInt(o -> o.i_id));
        mg.MISC.sort(Comparator.comparingInt(o -> o.i_id));
        mg.AMULET.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.BOOTS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.CHEST.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.HEAD.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.OFFHAND.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.ONEHAND.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.PANTS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.RELICS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.RINGS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.TWOHANDS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.BAGS.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
        mg.MISC.add(0, new ITEM(0, "FILLER", 10, '2', "/resources/items/filler/smiley", "OMEGA", "hey", "hey"));
    }

    public void readPlayerInventory(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM  PLAYER_INV");
        int counter = 0;
        while (rs.next()) {
            if (rs.getString("i_id") == null) {
                counter++;
                continue;
            } else if (rs.getRow() > 10) {
                break;
            }
            mg.inventP.char_Slots[counter].item = getItemWithQualityEffect(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"), rs.getInt("level"), rs.getString("effect"));
            counter++;
        }
    }

    private void readPlayerBagEquip(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM  PLAYER_INV");
        while (rs.next()) {
            if (rs.getRow() < 12 || rs.getString("i_id") == null) {
                continue;
            } else if (rs.getRow() > 15) {
                break;
            }
            mg.inventP.bagEquipSlots[rs.getRow() - 12].item = getItemWithQuality(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"), rs.getInt("level"));
        }
        mg.inventP.updateItemEffects();
    }

    private void readSkillTree(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM TALENTS");
        while (rs.next()) {
            if (rs.getString("id") == null) {
                continue;
            }
            mg.talentP.talent_Nodes[rs.getInt("id")] = new TalentNode(new TALENT(rs.getInt("id"), rs.getString("name"), rs.getString("imagePath"), rs.getString("description"), rs.getString("effect")),
                    rs.getInt("xCoordinate"), rs.getInt("yCoordinate"), rs.getInt("size"), rs.getInt("activated"));
        }
    }

    public void readPlayerBags(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_BAG");
        int counter = 1;
        while (rs.next()) {
            if (rs.getString("i_id") == null) {
                counter++;
                continue;
            }
            mg.inventP.bag_Slots.get(counter - 1).item = getItemWithQualityEffect(rs.getInt("i_id"), rs.getString("type"), rs.getInt("quality"), rs.getInt("level"), rs.getString("effect"));
            counter++;
        }
    }

    private void readPlayerStats(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER_STATS");
        mg.player.spawnLevel = rs.getInt("startLevel");
        mg.player.coins = rs.getInt("coins");
        mg.player.setLevel(rs.getInt("experience"));
        mg.talentP.pointsToSpend = rs.getInt("talentPointsToSpend");
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
                        System.out.println("hey");
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
            case "G":
                for (ITEM item : mg.BAGS) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
                    }
                }
                break;
            case "M":
                for (ITEM item : mg.MISC) {
                    if (item.i_id == i_id) {
                        new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                        return new_ITEM;
                    }
                }
                break;
        }

        return null;
    }

    private ITEM getItemWithQualityEffect(int i_id, String type, int quality, int level, String effect) {
        ITEM new_ITEM;
        if (effect != null) {
            switch (type) {
                case "A":
                    for (ITEM item : mg.AMULET) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "B":
                    for (ITEM item : mg.BOOTS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "C":
                    for (ITEM item : mg.CHEST) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "H":
                    for (ITEM item : mg.HEAD) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "O":
                    for (ITEM item : mg.OFFHAND) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "W":
                    for (ITEM item : mg.ONEHAND) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "P":
                    for (ITEM item : mg.PANTS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "T":
                    for (ITEM item : mg.RELICS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "R":
                    for (ITEM item : mg.RINGS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "2":
                    for (ITEM item : mg.TWOHANDS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "G":
                    for (ITEM item : mg.BAGS) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
                case "M":
                    for (ITEM item : mg.MISC) {
                        if (item.i_id == i_id) {
                            new_ITEM = DRP_DroppedItem.cloneItemWithLevelQuality(item, quality, level);
                            new_ITEM.getEffects(effect);
                            return new_ITEM;
                        }
                    }
                    break;
            }
        } else {
            return getItemWithQuality(i_id, type, quality, level);
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
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
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.MISC.add(0, new_ITEM);
        }
    }

    private void searchBAGS(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM ARM_BAG");
        while (rs.next()) {
            if (rs.getString("name") == null) {
                continue;
            }
            //ADDED ID + NAME + RARITY + TYPE + IMGAGEPATH
            ITEM new_ITEM = new ITEM(rs.getInt("i_id"), rs.getString("name"), rs.getInt("rarity"), rs.getString("type").charAt(0), rs.getString("imagePath"), rs.getString("description"), rs.getString("stats"), rs.getString("effect"));
            new_ITEM.description = insertNewLine(new_ITEM.description);
            new_ITEM.icon = new_ITEM.setup(new_ITEM.imagePath);
            mg.BAGS.add(0, new_ITEM);
        }
    }


    public void resetGame() {
        System.out.println("STARTING RESET");
        try {
            resetQuests();
            System.out.println("QUESTS RESET");
            resetInventory();
            System.out.println("INVENTORY RESET");
            resetTalents();
            System.out.println("TALENTS RESET");
            resetMapCovers();
            System.out.println("MAP COVER RESET");
            resetSKills();
            System.out.println("SKILLS RESET");
            mg.player.spawnLevel = 0;
            mg.player.experience = 0;
            mg.player.coins = 0;
            mg.talentP.pointsToSpend = 0;
            savePlayerStats();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void resetSKills() throws SQLException {
        String sql = "UPDATE SKL_Skills SET skill_index = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 1; i < 50; i++) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setInt(2, i);
            stmt.executeUpdate();
        }
        sql = "UPDATE SKL_Skills SET activeSkills = ? WHERE _ROWID_ = ?";
        stmt = conn.prepareStatement(sql);
        for (int i = 1; i < 50; i++) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setInt(2, i);
            stmt.executeUpdate();
        }
    }

    private void resetInventory() throws SQLException {
        String sql = "UPDATE PLAYER_BAG SET i_id = ?, type = ?, quality = ?,level = ?, effect = ? WHERE _ROWID_ = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 1; i <= 41; i++) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, null);
            stmt.setNull(3, Types.INTEGER);
            stmt.setNull(4, Types.INTEGER);
            stmt.setString(5, null);
            stmt.setInt(6, i);
            stmt.executeUpdate();
        }
        sql = "UPDATE PLAYER_INV SET i_id = ?, type = ?, quality = ?,level = ?, effect = ? WHERE _ROWID_ = ?";
        stmt = conn.prepareStatement(sql);
        for (int i = 1; i <= 15; i++) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, null);
            stmt.setNull(3, Types.INTEGER);
            stmt.setNull(4, Types.INTEGER);
            stmt.setString(5, null);
            stmt.setInt(6, i);
            stmt.executeUpdate();
        }
    }

    private void resetTalents() throws SQLException {
        String sql = "UPDATE TALENTS SET activated = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < mg.talentP.talent_Nodes.length; i++) {
            if (mg.talentP.talent_Nodes[i] != null) {
                stmt.setInt(1, 0);
                stmt.setString(2, String.valueOf(i));
                stmt.executeUpdate();
            }
        }
    }

    private void resetQuests() throws SQLException {
        String sql = "UPDATE QUEST_FACTS SET DESCRIPTION = ?, JournalText = NULL , FACT_1 = NULL, FACT_2 = NULL, FACT_3 = NULL WHERE _ROWID_ = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 1; i < 29; i++) {
            ps.setString(1, "null");
            ps.setInt(2, i);
            ps.executeUpdate();
        }
    }


    private void resetMapCovers() throws SQLException {
        for (Map map : mg.wControl.MAPS) {
            if (map.gameMapType == GameMapType.MapCover) {
                map.mapCover = new int[map.mapSize.x][map.mapSize.x];
                map.saveMapCover();
            }
        }
        System.out.println("FINISHED RESETTING");
    }
}