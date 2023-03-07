package main.system.savegame;

import gameworld.entities.NPC;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.npcs.quests.NPC_OldMan;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.Dialog;
import gameworld.quest.QUEST;
import gameworld.quest.hiddenquests.QST_StaturePuzzleHillcrest;
import gameworld.quest.quests.QST_MarlaFakeNecklace;
import gameworld.quest.quests.QST_Tutorial;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;

import java.sql.SQLException;
import java.sql.Statement;

public class LoadGameState {
    final MainGame mg;


    public LoadGameState(MainGame mg) {
        this.mg = mg;
    }

    public void loadGame() {
        loadQuests();
        loadPlayerSave();
        loadSpawnLevel();
    }

    private void loadQuests() {
        loadTutorial();
        loadMarla();
        loadHiddenQuests();
        if (mg.qPanel.quests.size() > 0) {
            mg.qPanel.activeQuest = mg.qPanel.quests.get(0);
        }
    }

    private void loadTutorial() {
        int quest_num = mg.sqLite.readQuestFacts(1, 1);
        String description = mg.sqLite.readQuestDescription(1);
        switch (description) {
            case "null" -> {
                mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial", false));
                mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 11, 4, Zone.Woodland_Edge));
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial", false));
                mg.wControl.loadMap(Zone.Woodland_Edge, 4, 4);
                System.out.println("hey");
                mg.qPanel.getQuest("Tutorial").questRecap = mg.sqLite.readQuestJournal(1, 50);
                if (quest_num == 1) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 45, 34, Zone.Woodland_Edge));
                    mg.qPanel.setQuestStage("Tutorial", 13);
                    mg.player.setPosition(39, 34);
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 94, 48 * 44, mg.MISC.get(2), Zone.Woodland_Edge));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 96, 48 * 13, mg.MISC.get(4), Zone.Woodland_Edge));
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 58, 48, Zone.Woodland_Edge));
                    mg.qPanel.setQuestStage("Tutorial", 26);
                    mg.qPanel.getQuest("Tutorial").objectives[0] = ("Search the ruins for a way out");
                    mg.player.setPosition(58, 35);
                    QUEST.openSquareDoor(58, 37);
                } else if (quest_num == 3) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 1, 1, Zone.Hillcrest));
                    mg.qPanel.setQuestStage("Tutorial", 37);
                    mg.qPanel.getQuest("Tutorial").objectives[0] = ("Follow the old man");
                    mg.wControl.loadMap(Zone.Hillcrest, 0, 0);
                } else {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 11, 4, Zone.Woodland_Edge));
                }
            }
            case "finished" -> {
                mg.qPanel.finishedQuests.add(new QST_Tutorial(mg, "Tutorial", true));
                if (mg.player.spawnLevel < 1) {
                    mg.player.spawnLevel = 1;
                }
            }
        }
    }

    private void loadMarla() {
        int quest_num = mg.sqLite.readQuestFacts(2, 1);
        String description = mg.sqLite.readQuestDescription(2);
        switch (description) {
            case "null" -> {
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_MarlaFakeNecklace(mg, "The Fake Necklace", false));
                mg.qPanel.getQuest("The Fake Necklace").questRecap = mg.sqLite.readQuestJournal(2, 150);
                if (quest_num == 1) {
                    mg.qPanel.getQuest("The Fake Necklace").objective3Progress = 1;
                    mg.qPanel.setQuestStage("The Fake Necklace", 11);
                    for (NPC npc : mg.npcControl.NPC_Active) {
                        if (npc instanceof NPC_Marla) {
                            ((NPC_Marla) npc).gotQuest = true;
                        }
                    }
                    mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(84, 51, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 59, 55, Zone.Hillcrest));
                    mg.qPanel.getQuest("The Fake Necklace").objectives[0] = Dialog.insertNewLine("Head east and pickup the mysterious adventurers trail!", 20);
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 38, 61, Zone.Hillcrest));
                    mg.qPanel.setQuestStage("The Fake Necklace", 19);
                    mg.qPanel.getQuest("The Fake Necklace").objectives[0] = ("Help Aria find the mountain top");
                } else if (quest_num == 3) {

                } else {

                }
            }
            case "finished" -> mg.qPanel.finishedQuests.add(new QST_MarlaFakeNecklace(mg, "The Fake Necklace", true));
        }
    }

    private void loadHiddenQuests() {
        String description = mg.sqLite.readQuestDescription(4);
        if (description.equals("finished")) {
            mg.qPanel.hiddenQuests.add(new QST_StaturePuzzleHillcrest(mg, "The 4 Statures", true));
        } else {
            mg.qPanel.hiddenQuests.add(new QST_StaturePuzzleHillcrest(mg, "The 4 Statures", false));
        }
    }

    private void loadPlayerSave() {
        try {
            Statement stmt = mg.sqLite.conn.createStatement();
            mg.sqLite.readSKillPanel(stmt);
            mg.sqLite.readPlayerInventory(stmt);
            mg.sqLite.readPlayerBags(stmt);
            mg.player.updateEquippedItems();
            mg.player.health = mg.player.maxHealth;
            mg.player.setMana(mg.player.maxMana);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void loadSpawnLevel() {
        int num = mg.sqLite.readStartLevel();
        if (num == 1) {
            mg.wControl.loadMap(Zone.Hillcrest, 20, 20);
        }
    }
}
