package gameworld.dialogue;

import gameworld.entities.monsters.ENT_Grunt;
import main.MainGame;

public class SpawnTrigger {
    public boolean triggered;
    private int x, y, level;
    private Trigger trigger;
    private Type type;

    public SpawnTrigger(int x, int y, int level, Trigger trigger, Type type) {


    }

    private void singularTrigger(MainGame mg, int x, int y, int level) {
        MainGame.ENTITIES.add(new ENT_Grunt(mg, x * 48, y * 48, level));
    }

    public void activate(MainGame mg) {
        if (trigger == Trigger.SINGULAR) {
            MainGame.ENTITIES.add(new ENT_Grunt(mg, x * 48, y * 48, level));
        }
    }
}

