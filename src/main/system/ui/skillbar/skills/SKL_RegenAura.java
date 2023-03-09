package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.buffs.BUF_RegenAura;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_RegenAura extends SKILL {


    public SKL_RegenAura(MainGame mg) {
        super(mg);
        this.totalCoolDown = 3_600;
        this.actualCoolDown = 3_600;
        castTimeTotal = 50;
        manaCost = 10;
        type = DamageType.Arcane;
        icon = setup("frostNova");
        castTimeActive = 0;
        name = "Regenerative Aura";
        description = "Regenerative Aura creates a soothing aura around the character, stimulating the natural healing processes of the body and allowing them to gradually recover from any wounds or injuries sustained in battle. As long as the aura remains active, the character will experience a steady stream of rejuvenation, gradually restoring their health over time.";
        //TODO icon
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCastBar(gc);
        drawIcon(gc, x, y);
        drawCooldown(gc, x, y);
    }

    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    @Override
    public void activate() {
        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.getMana() >= manaCost) {
            castTimeActive++;
        }
        if (castTimeActive >= castTimeTotal) {
            mg.player.BuffsDebuffEffects.add(new BUF_RegenAura(1_200, mg.player.level * 2, 1, true));
            mg.player.loseMana(manaCost);
            actualCoolDown = 0;
        }
    }
}
