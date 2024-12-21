/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.savegame;

import gameworld.entities.NPC;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_DyingHermit;
import gameworld.entities.npcs.quests.NPC_Grim;
import gameworld.entities.npcs.quests.NPC_HillcrestMayor;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.npcs.quests.NPC_OldMan;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import gameworld.quest.hiddenquests.QST_StaturePuzzleHillcrest;
import gameworld.quest.hiddenquests.QST_TheGrovesSecret;
import gameworld.quest.quests.QST_IntoTheGrassLands;
import gameworld.quest.quests.QST_MarlaFakeNecklace;
import gameworld.quest.quests.QST_Nietzsche;
import gameworld.quest.quests.QST_TheAudition;
import gameworld.quest.quests.QST_Tutorial;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.database.SQLite;
import main.system.enums.Zone;

import java.sql.SQLException;
import java.sql.Statement;

public class LoadGameState {
    private final MainGame mg;


    public LoadGameState(MainGame mg) {
        this.mg = mg;
    }

    public void loadGame() {
        loadQuests();
        loadPlayerSave();
    }

    private void loadQuests() {
        loadTutorial();
        loadMarla();
        loadAudition();
        loadHiddenQuests();
        loadIntoGrassLands();
        loadGroveSecret();
        loadNietzsche();
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
                if (quest_num == 0) {
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 11, 4, Zone.Woodland_Edge));
                } else if (quest_num == 1) {
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.Tutorial, 13, "Talk to the old man");
                    mg.npcControl.NPC_Active.add(new NPC_OldMan(mg, 45, 34, Zone.Woodland_Edge));
                    mg.player.setPosition(39, 34);
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 94, 48 * 44, mg.MISC.get(2), Zone.Woodland_Edge));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 96, 48 * 13, mg.MISC.get(3), Zone.Woodland_Edge));
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
        String description = mg.sqLite.readQuestDescription(QUEST_NAME.TheFakeNecklace.val);
        switch (description) {
            case "null" -> {
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_MarlaFakeNecklace(mg, false));
                mg.qPanel.getQuest(QUEST_NAME.TheFakeNecklace).questRecap = mg.sqLite.readQuestJournal(2, 150);
                for (NPC npc : mg.npcControl.NPC_Active) {
                    if (npc instanceof NPC_Marla) {
                        ((NPC_Marla) npc).gotQuest = true;
                        npc.blockInteraction = true;
                        npc.dialog.loadNewLine(DialogStorage.MarlaNecklace[11]);
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
                } else if (quest_num == 4) {
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 90, 97, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 40, "Get through the cave");
                } else if (quest_num == 5) {
                    mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 75, 84, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheFakeNecklace, 50, "Explore the mountain top!");
                }
            }
            case "finished" -> {
                mg.qPanel.finishedQuests.add(new QST_MarlaFakeNecklace(mg, true));
                for (NPC npc : mg.npcControl.NPC_Active) {
                    if (npc instanceof NPC_Marla) {
                        if (quest_num == -5) {
                            npc.dialog.loadNewLine("You brought me ruin! Away with you!");
                        } else if (quest_num == 10) {
                            npc.dialog.loadNewLine("Thanks for helping me again! Hope you stick around.");
                        } else if (quest_num == 100) {
                            npc.dialog.loadNewLine("I still can't believe she's dead. And to think I was a big part of it... ... ");
                        }
                        ((NPC_Marla) npc).gotQuest = true;
                        npc.blockInteraction = true;
                        break;
                    }
                }
            }
        }
    }

    private void loadAudition() {
        String description = mg.sqLite.readQuestDescription(QUEST_NAME.TheAudition.val);
        switch (description) {
            case "null" -> {
            }
            case "active" -> mg.qPanel.quests.add(new QST_TheAudition(mg, false));
            case "finished" -> {
                mg.qPanel.finishedQuests.add(new QST_TheAudition(mg, true));
                for (NPC npc : mg.npcControl.NPC_Active) {
                    if (npc instanceof NPC_HillcrestMayor) {

                    }
                }
            }
        }
    }

    private void loadIntoGrassLands() {
        int quest_num = mg.sqLite.readQuestFacts(QUEST_NAME.IntoTheGrassLands.val, 1);
        String description = mg.sqLite.readQuestDescription(QUEST_NAME.IntoTheGrassLands.val);
        switch (description) {
            case "null" -> {
            }
            case "active" -> {
                mg.qPanel.quests.add(new QST_IntoTheGrassLands(mg, false));
                if (quest_num == 0) {
                    mg.npcControl.NPC_Active.add(new NPC_Grim(mg, 4, 90, Zone.Hillcrest));
                } else if (quest_num == 1) {
                    mg.npcControl.NPC_Active.add(new NPC_Grim(mg, 4, 90, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.IntoTheGrassLands, 12, "Reach level 5 or", "Kill a Stone Knight");
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Active.add(new NPC_Grim(mg, 4, 90, Zone.Hillcrest));
                    mg.qPanel.setQuestStageAndObjective(QUEST_NAME.IntoTheGrassLands, 13, "Talk to Grim about the GrassLands");
                }
            }
            case "finished" -> mg.qPanel.finishedQuests.add(new QST_IntoTheGrassLands(mg, true));
        }
    }

    private void loadGroveSecret() {
        int quest_num = mg.sqLite.readQuestFacts(QUEST_NAME.TheGrovesSecret.val, 1);
        String description = mg.sqLite.readQuestDescription(QUEST_NAME.TheGrovesSecret.val);
        if (!description.equals("finished")) {
            mg.qPanel.quests.add(new QST_TheGrovesSecret(mg, false));
            if (quest_num > 0) {
                ((HiddenQUEST) mg.qPanel.getQuest(QUEST_NAME.TheGrovesSecret)).activated = true;
            }
            if (quest_num == 1) {
                mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheGrovesSecret, 2, "Follow the path to \"The Grove\"");
            }
            if (quest_num == 2) {
                mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheGrovesSecret, 13, "Check if tickets dont grow on trees");
                mg.npcControl.addToActive.add(new NPC_DyingHermit(mg, 3, 156, Zone.The_Grove));
            }
            if (quest_num == 3) {
                mg.qPanel.setQuestStageAndObjective(QUEST_NAME.TheGrovesSecret, 19, "Let the island manager show you around");
            }
        } else {
            mg.qPanel.finishedQuests.add(new QST_TheGrovesSecret(mg, true));
        }
    }

    private void loadNietzsche() {
        int quest_num = mg.sqLite.readQuestFacts(QUEST_NAME.Nietzsche.val, 1);
        String description = mg.sqLite.readQuestDescription(QUEST_NAME.Nietzsche.val);
        if (description.equals("active")) {
            mg.qPanel.quests.add(new QST_Nietzsche(mg, false));
            if (quest_num == 2) {
                mg.qPanel.setQuestStageAndObjective(QUEST_NAME.Nietzsche, 5, "Clear the cave");
                mg.qPanel.getQuest(QUEST_NAME.Nietzsche).objectives[1] = "Recover the book";
            }
        } else if (description.equals("finished")) {
            mg.qPanel.finishedQuests.add(new QST_Nietzsche(mg, true));
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
            Statement stmt = SQLite.PLAYER_SAVE.createStatement();
            mg.sqLite.readSKillPanel(stmt);
            mg.sqLite.readPlayerInventory(stmt);
            mg.sqLite.readPlayerBags(stmt);
            mg.player.updateEquippedItems();
            mg.gameStatistics.loadGameStatistics(SQLite.PLAYER_SAVE);
            mg.player.setHealth(mg.player.maxHealth);
            mg.player.setMana(mg.player.maxMana);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void loadSpawnLevel() {
        int num = mg.sqLite.readStartLevel();
        if (num == 1) {
            mg.wControl.loadMapNoDelay(Zone.Hillcrest, 20, 20);
        }
    }
}
