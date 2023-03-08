package main.system.savegame;

import gameworld.entities.NPC;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.npcs.quests.NPC_OldMan;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.hiddenquests.QST_StaturePuzzleHillcrest;
import gameworld.quest.quests.QST_MarlaFakeNecklace;
import gameworld.quest.quests.QST_TheAudition;
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
        loadAudition();
        loadHiddenQuests();
        if (mg.qPanel.quests.size() > 0) {
            mg.qPanel.activeQuest = mg.qPanel.quests.get(0);
        }
    }

    private void loadTutorial() {
        int quest_num = mg.sqLite.readQuestFacts(QUEST_NAME.Tutorial.val, 1);
        String description = mg.sqLite.readQuestDescription(1);
        switch (description) {
            case "null" -> {
                mg.qPanel.quests.add(new QST_Tutorial(mg, false));
                mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 11, 4, Zone.Woodland_Edge));
                mg.wControl.loadMap(Zone.Woodland_Edge, 4, 4);
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_Tutorial(mg, false));
                mg.wControl.loadMap(Zone.Woodland_Edge, 4, 4);
                mg.qPanel.getQuest(QUEST_NAME.Tutorial).questRecap = mg.sqLite.readQuestJournal(1, 150);
                if (quest_num == 1) {
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.Tutorial, 13, "Talk to the old man");
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 45, 34, Zone.Woodland_Edge));
                    mg.player.setPosition(39, 34);

                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 94, 48 * 44, mg.MISC.get(2), Zone.Woodland_Edge));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 96, 48 * 13, mg.MISC.get(4), Zone.Woodland_Edge));
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 58, 48, Zone.Woodland_Edge));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.Tutorial, 26, "Search the ruins for a way out");
                    mg.player.setPosition(58, 35);
                    QUEST.openSquareDoor(58, 37);
                } else if (quest_num == 3) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 1, 1, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.Tutorial, 37, "Follow the old man");
                    mg.wControl.loadMap(Zone.Hillcrest, 4, 4);
                }
            }
            case "finished" -> {
                mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 44, 21, Zone.Hillcrest));
                mg.qPanel.finishedQuests.add(new QST_Tutorial(mg, true));
                if (mg.player.spawnLevel < 1) {
                    mg.player.spawnLevel = 1;
                }
            }
        }
    }

    private void loadMarla() {
        int quest_num = mg.sqLite.readQuestFacts(QUEST_NAME.TheFakeNecklace.val, 1);
        String description = mg.sqLite.readQuestDescription(2);
        switch (description) {
            case "null" -> {
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_MarlaFakeNecklace(mg, false));
                mg.qPanel.getQuest(QUEST_NAME.TheFakeNecklace).questRecap = mg.sqLite.readQuestJournal(2, 150);
                for (NPC npc : mg.npcControl.NPC_Active) {
                    if (npc instanceof NPC_Marla) {
                        ((NPC_Marla) npc).gotQuest = true;
                    }
                }
                if (quest_num == -1) {
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 77, "Listen to the confrontation");
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 43, 31, Zone.Hillcrest));
                } else if (quest_num == 1) {
                    mg.qPanel.getQuest(QUEST_NAME.TheFakeNecklace).objective3Progress = 1;
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 11, "Head east and pickup the mysterious adventurers trail!");
                    mg.ENTITIES.add(new DeadWolf(82, 48, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(84, 51, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                    mg.ENTITIES.add(new DeadWolf(84, 53, Zone.Hillcrest));
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 59, 55, Zone.Hillcrest));
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 38, 61, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 19, "Help Aria find the mountain top");
                } else if (quest_num == 3) {
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 90, 57, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 25, "Take rest");
                }
            }
            case "finished" -> mg.qPanel.finishedQuests.add(new QST_MarlaFakeNecklace(mg, true));
        }
    }

    private void loadAudition() {
        int quest_num = mg.sqLite.readQuestFacts(2, 1);
        String description = mg.sqLite.readQuestDescription(2);
        switch (description) {
            case "null" -> {
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_TheAudition(mg, false));
                if (quest_num == 0) {

                }
            }
            case "finished" -> {
                mg.qPanel.finishedQuests.add(new QST_TheAudition(mg, true));
            }
        }
    }

    private void loadHiddenQuests() {
        String description = mg.sqLite.readQuestDescription(4);
        if (description.equals("finished")) {
            mg.qPanel.finishedQuests.add(new QST_StaturePuzzleHillcrest(mg, true));
        } else {
            mg.qPanel.hiddenQuests.add(new QST_StaturePuzzleHillcrest(mg, false));
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
