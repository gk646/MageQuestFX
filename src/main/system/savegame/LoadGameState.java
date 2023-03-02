package main.system.savegame;

import gameworld.entities.npcs.NPC_OldMan;
import gameworld.quest.QUEST;
import gameworld.quest.quests.QST_Tutorial;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;

public class LoadGameState {
    final MainGame mg;


    public LoadGameState(MainGame mg) {
        this.mg = mg;
    }

    public void loadGame() {
        loadSpawnLevel();
        loadQuests();
    }

    private void loadQuests() {
        loadTutorial();
        mg.qPanel.activeQuest = mg.qPanel.quests.get(0);
    }

    private void loadTutorial() {
        int quest_num = mg.sqLite.readQuestFacts(1, 1);
        String description = mg.sqLite.readQuestDescription(1);
        switch (description) {
            case "null":
                mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial"));
                mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 11, 4, Zone.Tutorial));
                break;
            case "active":
                mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial"));
                if (quest_num == 1) {
                    mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 45, 34, Zone.Tutorial));
                    mg.qPanel.setQuestStage("Tutorial", 13);
                    mg.player.setPosition(39, 34);
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 94, 48 * 44, mg.MISC.get(2), Zone.Tutorial));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 96, 48 * 13, mg.MISC.get(4), Zone.Tutorial));
                } else if (quest_num == 2) {
                    mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 58, 48, Zone.Tutorial));
                    mg.qPanel.setQuestStage("Tutorial", 26);
                    mg.player.setPosition(58, 35);
                    QUEST.openSquareDoor(58, 37);
                } else if (quest_num == 3) {
                    mg.npcControl.NPC_Clearing.add(new NPC_OldMan(mg, 20, 20, Zone.Clearing));
                    mg.qPanel.setQuestStage("Tutorial", 37);
                    mg.player.setPosition(1, 1);
                }
            case "finished":
        }
    }

    private void loadSpawnLevel() {
        int num = mg.sqLite.readStartLevel();
        switch (num) {
            case 0:
               // mg.wControl.loadMap(Zone.Tutorial, 4, 4);
            case 1:
                // mg.wControl.loadMap(Zone.City1, 40, 18);
        }
    }
}
