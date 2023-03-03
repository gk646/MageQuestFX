package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_ThunderSplash;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_ThunderSplash extends SKILL {

    public SKL_ThunderSplash(MainGame mg) {
        super(mg);
        totalCoolDown = 300;
        actualCoolDown = totalCoolDown;
        manaCost = 35;
        this.damage = 15.0f;
        icon = setup("thunderSplash");
        type = DamageType.Arcane;
        name = "Thunder Clap";
        description = "Thunder Clap is a powerful spell that unleashes a shockwave of lightning energy, stunning all enemies within a small radius and dealing a moderate amount of damage.";
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
            actualCoolDown = 0;
            mg.player.playCastAnimation(3);
            mg.PROJECTILES.add(new PRJ_ThunderSplash());
        }
    }
}


