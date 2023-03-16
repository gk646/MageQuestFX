package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_BlastHammer;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_BlastHammer extends SKILL {


    public SKL_BlastHammer(MainGame mg) {
        super(mg);
        this.totalCoolDown = 1200;
        actualCoolDown = totalCoolDown;
        this.coolDownCoefficient = 0;
        this.icon = setup("blastHammer");
        type = DamageType.Fire;
        name = "Blast Hammer";
        manaCost = 50;
        weapon_damage_percent = 1500;
        i_id = 18;
        description = "Blast Hammer: Unleash the fury of the forge as you summon a colossal, blazing hammer to strike down your foes. Channeling the raw power of fire, the Blast Hammer crushes your enemies with searing force, dealing massive area damage and leaving a trail of smoldering embers in its wake.";
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void activate() {
        if (checkForActivation(2)) {
            mg.PROJECTILES.add(new PRJ_BlastHammer(weapon_damage_percent));
        }
    }
}


