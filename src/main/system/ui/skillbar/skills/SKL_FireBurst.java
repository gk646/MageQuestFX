package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_FireBurst;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_FireBurst extends SKILL {


    public SKL_FireBurst(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        actualCoolDown = totalCoolDown;
        this.weapon_damage_percent = 15;
        manaCost = 15;
        type = DamageType.Fire;
        this.icon = setup("fireBurst");
        name = "Fire Burst";
        description = "Unleash a wave of searing flames that scorches all enemies in its path. While Fire Blast may not deal as much damage as other fire-based abilities, its intense heat lingers long after impact, leaving enemies smoldering with a burn debuff that deals additional damage over time.";
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
            for (int i = 0; i <= 7; i++) {
                mg.PROJECTILES.add(new PRJ_FireBurst(i, weapon_damage_percent));
            }
        }
    }
}
