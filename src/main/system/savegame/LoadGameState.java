package main.system.savegame;

import gameworld.entities.npcs.NPC_OldMan;
import gameworld.quest.QUEST;
import gameworld.quest.quests.QST_Tutorial;
import main.MainGame;
import main.system.enums.Zone;

public class LoadGameState {
    MainGame mg;


    public LoadGameState(MainGame mg) {
        this.mg = mg;
    }

    public void loadGame() {
        loadSpawnLevel();
        loadQuests();
    }

    private void loadQuests() {
        loadTutorial();
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
                switch (quest_num) {
                    case 1:
                        mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 45, 34, Zone.Tutorial));
                        mg.qPanel.setQuestStage("Tutorial", 13);
                        mg.player.setPosition(39, 34);
                    case 2:
                        mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 58, 48, Zone.Tutorial));
                        mg.qPanel.setQuestStage("Tutorial", 26);
                        mg.player.setPosition(58, 35);
                        QUEST.openSquareDoor(58, 37);
                    case 3:
                        mg.npcControl.NPC_Clearing.add(new NPC_OldMan(mg, 20, 20, Zone.Clearing));
                        mg.qPanel.setQuestStage("Tutorial", 37);
                }
                break;
            case "finished":
        }
    }

    private void loadSpawnLevel() {
        int num = mg.sqLite.readStartLevel();
        switch (num) {
            case 0:
                mg.wControl.loadMap(Zone.Tutorial, 4, 4);
            case 1:
                mg.wControl.loadMap(Zone.City1, 40, 18);
        }
    }
}
