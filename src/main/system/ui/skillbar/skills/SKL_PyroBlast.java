package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_PyroBlast;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_PyroBlast extends SKILL {
    public SKL_PyroBlast(MainGame mg) {
        super(mg);
        totalCoolDown = 300;
        actualCoolDown = totalCoolDown;
        manaCost = 10;
        weapon_damage_percent = 250;
        type = DamageType.Fire;
        icon = setup("pyroBlast");
        name = "Pyro Blast";
        description = "A powerful spell that unleashes a powerul fireball upon enemies, dealing big damage upon impact. Enemies hit by Pyroblast also suffer a small burn effect, causing them to take additional fire damage over time.";
    }

    /**
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
        if (checkForActivation(1)) {
            mg.PROJECTILES.add(new PRJ_PyroBlast(weapon_damage_percent, mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
        }
    }
}

