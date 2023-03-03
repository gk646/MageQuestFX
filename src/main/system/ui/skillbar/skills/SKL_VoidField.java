package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_VoidField;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidField extends SKILL {
    public SKL_VoidField(MainGame mg) {
        super(mg);
        totalCoolDown = 600;
        manaCost = 50;
        castTimeTotal = 60;
        castTimeActive = castTimeTotal;
        actualCoolDown = totalCoolDown;
        icon = setup("voidField");
        name = "Void Field";
        type = DamageType.DarkMagic;
        damage = 0.1f;
        description = "Creates a localized zone of darkness and destruction. Upon activation, the caster summons a swirling void, consuming everything in its path. Enemies caught in the Void Field are taking damage over time as they struggle to escape the field's grasp. They are also weakened against further dark magic damage";
    }


    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawIcon(gc, x, y);
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
        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.mana >= 15) {
            castTimeActive++;
            mg.player.playCastAnimation(3);
        }
        if (castTimeActive == castTimeTotal) {
            mg.player.mana -= manaCost;
            castTimeActive = 0;
            actualCoolDown = 0;
            mg.PROJECTILES.add(new PRJ_VoidField(300));
        }
    }
}
