package main.system.ui.skillbar.skills;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidField extends SKILL {
    public SKL_VoidField(MainGame mg) {
        super(mg);
        totalCoolDown = 600;
        manaCost = 50;
        actualCoolDown = totalCoolDown;
        name = "Void Field";
        description = "Creates a localized zone of darkness and destruction. Upon activation, the caster summons a swirling void, consuming everything in its path. Enemies caught in the Void Field are taking damage over time as they struggle to escape the field's grasp. They are also weakened against further dark magic damage";
    }


    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCooldown(gc, x, y);
    }

    /**
     *
     */
    @Override
    public void update() {
        super.updateCooldown();
    }

    /**
     *
     */
    @Override
    public void activate() {
        if (actualCoolDown == totalCoolDown && mg.player.mana >= manaCost) {
            mg.player.mana -= manaCost;
            //TODO void field projectile
        }
    }
}
