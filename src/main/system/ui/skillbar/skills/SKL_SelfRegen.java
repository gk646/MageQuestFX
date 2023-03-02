package main.system.ui.skillbar.skills;

import gameworld.entities.damage.effects.Buff_Effect;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_SelfRegen extends SKILL {


    public SKL_SelfRegen(MainGame mg) {
        super(mg);
        this.totalCoolDown = 3_600;
        this.actualCoolDown = 3_600;
        castTimeTotal = 300;
        castTimeActive = 0;
        name = "Regenerative Aura";
        description = "Regenerative Aura creates a soothing aura around the character, stimulating the natural healing processes of the body and allowing them to gradually recover from any wounds or injuries sustained in battle. As long as the aura remains active, the character will experience a steady stream of rejuvenation, gradually restoring their health over time.";
        //TODO icon
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCooldown(gc, x, y);
        drawCastBar(gc);
    }

    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    @Override
    public void activate() {
        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.mana >= 15) {
            castTimeActive++;
        }
        if (castTimeActive >= castTimeTotal) {
            mg.player.BuffsDebuffEffects.add(new Buff_Effect(1_200, 100, true, 24));
            mg.player.mana -= 15;
            actualCoolDown = 0;
        }
    }
}
