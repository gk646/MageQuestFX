package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_FrostNova;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_FrostNova extends SKILL {
    public SKL_FrostNova(MainGame mg) {
        super(mg);
        name = "Frost Nova";
        description = "";
        totalCoolDown = 600;
        actualCoolDown = totalCoolDown;
        type = DamageType.Ice;
        manaCost = 75;
    }

    /**
     * used for drawing the skill icon and the cooldown overlay
     *
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCooldown(gc, x, y);
    }

    /**
     *
     */
    @Override
    public void update() {
        updateCooldown();
    }

    /**
     *
     */
    @Override
    public void activate() {
        if (actualCoolDown >= totalCoolDown && mg.player.mana >= manaCost) {
            mg.player.mana -= manaCost;
            actualCoolDown = 0;
            mg.PROJECTILES.add(new PRJ_FrostNova());
        }
    }
}
