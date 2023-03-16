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
        weapon_damage_percent = 50;
        icon = setup("frostNova");
        actualCoolDown = totalCoolDown;
        type = DamageType.Ice;
        manaCost = 75;
        description = "Unleash a chilling wave of frost emanating from the player, instantly freezing all enemies within a circular area around the caster. Frozen foes become immobilized, their movements halted by the encasing ice. Frozen enemies struck by Glacial Thorns shatter and suffer 1000% Weapon damage as ice.";
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
        drawIcon(gc, x, y);
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
        if (checkForActivation(0)) {
            mg.PROJECTILES.add(new PRJ_FrostNova(weapon_damage_percent));
        }
    }
}
