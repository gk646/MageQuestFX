package main.system.ui.skillbar.skills;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_MagicShield extends SKILL {
    private final int castTimeTotal;
    private int castTimeActive;

    public SKL_MagicShield(MainGame mg) {
        super(mg);
        this.coolDownCoefficient = 0;
        this.totalCoolDown = 7_200;
        actualCoolDown = totalCoolDown;
        manaCost = 25;
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
        drawCastBar(gc);
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

        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.getMana() >= 50) {
            castTimeActive++;
        }
        if (castTimeActive >= castTimeTotal) {
            //mg.player.BuffsDebuffEffects.add(new Buff_Effect(1_200, 100, true, 45));
            mg.player.loseMana(manaCost);
            mg.player.playCastAnimation(2);
            actualCoolDown = 0;
        }
    }
}
