package main.system.savegame;

import gameworld.entities.npcs.NPC_OldMan;
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
        if (description.equals("null")) {
            mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial"));
            mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 11, 4));
        } else if (description.equals("active")) {
            mg.qPanel.quests.add(new QST_Tutorial(mg, "Tutorial"));
            switch (quest_num) {
                case 1:
                    mg.npcControl.NPC_Tutorial.add(new NPC_OldMan(mg, 45, 34));
                    mg.qPanel.setQuestStage("Tutorial", 13);
                case 2:
            }
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
