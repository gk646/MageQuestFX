package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.abilities.PRJ_Lightning;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_Lightning extends SKILL {


    public SKL_Lightning(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        this.actualCoolDown = 600;
        this.damage = 25;
        icon = setup("lightning");
        name = "Lightning Strike";
        this.type = DamageType.ArcaneDMG;
        description = "Lightning Strike is a powerful ability that allows the player to call forth bolts of lightning to strike down enemies with electrifying force.The lightning strike is especially effective against groups of enemies or those that are vulnerable to electricity.";
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void activate() {
        if (actualCoolDown == 600 && mg.player.mana >= 15) {
            PROJECTILE projectile = new PRJ_Lightning(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y);
            projectile.playStartSound();
            mg.PROJECTILES.add(projectile);
            mg.player.playCastAnimation(3);
            mg.player.mana -= 15;
            actualCoolDown = 0;
        }
    }
}
