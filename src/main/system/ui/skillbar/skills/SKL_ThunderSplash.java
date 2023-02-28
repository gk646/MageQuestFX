package main.system.ui.skillbar.skills;

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
        icon = setup("thunderSplash");
        name = "Thunder Splash";
        description = "Thunder Splash is a lightning-based ability that allows the player to call down a powerful bolt of lightning onto enemies in a targeted area. When activated, the sky darkens and crackles with electricity, signaling the impending strike. The player selects a target area, and after a short delay, a bolt of lightning strikes down, dealing massive area of effect damage to all enemies within the target area.";
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
        if (actualCoolDown >= totalCoolDown && mg.player.mana >= manaCost) {
            mg.player.mana -= manaCost;
            actualCoolDown = 0;
            mg.PROJECTILES.add(new PRJ_ThunderSplash());
        }
    }
}


