package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_VoidEruption;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidEruption extends SKILL {

    public SKL_VoidEruption(MainGame mg) {
        super(mg);
        totalCoolDown = 300;
        actualCoolDown = totalCoolDown;
        manaCost = 35;
        icon = setup("voidEruption");
        type = DamageType.DarkMagic;
        this.damage = 50.0f;
        name = "Void Eruption";
        description = "Void Eruption is a powerful ability that channels the dark energies of the void to unleash a devastating blast on enemies in the area. After a short build-up, the energy explodes in a shockwave of pure void energy, dealing massive damage to all enemies within the radius.";
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
        if (actualCoolDown >= totalCoolDown && mg.player.getMana() >= manaCost) {
            mg.player.loseMana(manaCost);
            mg.player.playCastAnimation(2);
            actualCoolDown = 0;
            mg.PROJECTILES.add(new PRJ_VoidEruption());
        }
    }
}
