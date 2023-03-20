package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.ARCANE.PRJ_Lightning;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_Lightning extends SKILL {


    public SKL_Lightning(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        this.actualCoolDown = 600;
        this.weapon_damage_percent = 450;
        manaCost = 15;
        icon = setup("lightning");
        name = "Lightning Strike";
        this.type = DamageType.Arcane;
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
        if (checkForActivation(3)) {
            mg.PROJECTILES.add(new PRJ_Lightning(weapon_damage_percent));
        }
    }
}
