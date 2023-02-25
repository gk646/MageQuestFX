package main.system.ui.skillbar.skills;

import gameworld.entities.damage.effects.Buff_Effect;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_MagicShield extends SKILL {
    private int castTimeActive, castTimeTotal;

    public SKL_MagicShield(MainGame mg) {
        super(mg);
        this.coolDownCoefficient = 0;
        this.totalCoolDown = 7200;
        actualCoolDown = totalCoolDown;
        castTimeTotal = 500;
        description = "This ability creates a shimmering, protective barrier around the character, made of pure magical energy. The Arcane Barrier not only absorbs incoming attacks, but it also actively repels enemies that come into contact with it, dealing damage and knocking them back.";
        name = "Arcane Barrier";
    }

    /**
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        //drawIcon(gc,x,y);
        drawCooldown(gc, x, y);
    }

    /**
     *
     */
    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    /**
     *
     */
    @Override
    public void activate() {
        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.mana >= 50) {
            castTimeActive++;
        }
        if (castTimeActive >= castTimeTotal) {
            mg.player.BuffsDeBuffEffects.add(new Buff_Effect(1200, 100, true, 24));
            mg.player.mana -= 15;
            actualCoolDown = 0;
        }
    }
}
