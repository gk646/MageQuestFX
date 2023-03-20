package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.FIRE.PRJ_FlameWhirl;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_FlameWhirl extends SKILL {


    public SKL_FlameWhirl(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        actualCoolDown = totalCoolDown;
        this.weapon_damage_percent = 25;
        manaCost = 35;
        i_id = 17;
        type = DamageType.Fire;
        this.icon = setup("fireSword");
        name = "Flame Whirl";
        description = "Unleash a furious maelstrom of fire as you swing your blazing sword in a wide arc, creating a devastating whirlwind of flame. The sheer power of the Flame Whirl sends out a palpable heatwave, momentarily staggering foes and leaving them vulnerable to follow-up attacks.";
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
            mg.PROJECTILES.add(new PRJ_FlameWhirl(weapon_damage_percent));
        }
    }
}
