package main.system.ui.skillbar.skills;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_ManaShield extends SKILL {
    public SKL_ManaShield(MainGame mg) {
        super(mg);
        name = "Mana Shield";
        description = "Mana Shield is a powerful defensive ability that allows the caster to create a protective shield around themselves using their own magical energy. Upon activation, the shield generates a shimmering barrier of pure mana that absorbs incoming attacks. It has no cast time,however tt has a continuous mana cost that drains the caster's magical reserves while it remains active. Regenerates a small amount of mana upon getting hit.";
        //TODO mana restoration upon taking hit
    }

    /**
     * @param gc
     * @param x
     * @param y
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {

    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     *
     */
    @Override
    public void activate() {

    }
}
