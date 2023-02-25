package main.system.ui.skillbar.skills;

import gameworld.entities.damage.effects.Buff_Effect;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.skillbar.SKILL;

public class SKL_SelfRegen extends SKILL {
    private final int castTimeTotal;
    private int castTimeActive;

    public SKL_SelfRegen(MainGame mg) {
        super(mg);
        this.totalCoolDown = 3600;
        this.actualCoolDown = 3600;
        castTimeTotal = 300;
        name = "Regenerative Aura";
        description = "Regenerative Aura creates a soothing aura around the character, stimulating the natural healing processes of the body and allowing them to gradually recover from any wounds or injuries sustained in battle. As long as the aura remains active, the character will experience a steady stream of rejuvenation, gradually restoring their health over time.";
        //TODO icon
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCooldown(gc, x, y);
        if (castTimeActive > 0) {
            gc.setFill(Colors.arcane_blue);
            gc.fillRoundRect(Player.screenX - 50, Player.screenY - 30, 100, 15, 15, 15);
        }
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
            mg.player.BuffsDeBuffEffects.add(new Buff_Effect(1200, 100, true, 24));
            mg.player.mana -= 15;
            actualCoolDown = 0;
        }
    }
}
