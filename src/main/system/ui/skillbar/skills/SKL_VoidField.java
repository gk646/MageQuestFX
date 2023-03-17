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
        weapon_damage_percent = 2.0f;
        description = "Creates a localized zone of darkness and destruction. Upon activation, the caster summons a swirling void, consuming everything in its path. Enemies caught in the Void Field are taking damage over time and are weakened against further dark magic damage";
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

    @Override
    /**
     *
     */
    public String toString() {
        return weapon_damage_percent + "% Weapon Damage per Tick as " + type;
    }

    @Override
    public void activate() {
        if (checkForActivationCasting(3)) {
            mg.PROJECTILES.add(new PRJ_VoidField(300, weapon_damage_percent));
        }
    }
}
