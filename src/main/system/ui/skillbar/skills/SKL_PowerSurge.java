package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.EffectType;
import gameworld.entities.damage.effects.arraybased.Effect_ArrayBased;
import gameworld.entities.damage.effects.buffs.BUF_PowerSurge;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_PowerSurge extends SKILL {
    private final GeneralResourceLoader resource;
    private boolean active;
    private int spriteCounter;

    public SKL_PowerSurge(MainGame mg) {
        super(mg);
        resource = ProjectilePreloader.powerSurge;
        active = false;
        this.totalCoolDown = 30;
        this.actualCoolDown = totalCoolDown;
        castTimeTotal = 0;
        manaCost = 0;
        type = DamageType.Arcane;
        icon = setup("powerSurge");
        castTimeActive = 0;
        name = "Power Surge";
        description = "Increases all damage by 15% but decreases maximum mana by 20% and increases mana cost by 30%";
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCastBar(gc);
        drawIcon(gc, x, y);
        drawCooldown(gc, x, y);
        if (active) {
            switch (spriteCounter % 120 / 15) {
                case 0 -> gc.drawImage(resource.images1.get(0), Player.screenX - 12, Player.screenY - 12);
                case 1 -> gc.drawImage(resource.images1.get(1), Player.screenX - 12, Player.screenY - 12);
                case 2 -> gc.drawImage(resource.images1.get(2), Player.screenX - 12, Player.screenY - 12);
                case 3 -> gc.drawImage(resource.images1.get(3), Player.screenX - 12, Player.screenY - 12);
                case 4 -> gc.drawImage(resource.images1.get(4), Player.screenX - 12, Player.screenY - 12);
                case 5 -> gc.drawImage(resource.images1.get(5), Player.screenX - 12, Player.screenY - 12);
                case 6 -> gc.drawImage(resource.images1.get(6), Player.screenX - 12, Player.screenY - 12);
                case 7 -> gc.drawImage(resource.images1.get(7), Player.screenX - 12, Player.screenY - 12);
            }
            spriteCounter++;
        } else {
            spriteCounter = 0;
        }
    }

    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    @Override
    public void activate() {
        if (!active && actualCoolDown >= totalCoolDown) {
            mg.player.BuffsDebuffEffects.add(new BUF_PowerSurge(mg.player, 1200, mg.player.level * 2, 1, true, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, 15, true, 1, EffectType.BUFF, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, 15, true, 2, EffectType.BUFF, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, 15, true, 18, EffectType.BUFF, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, 15, true, 19, EffectType.BUFF, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, 15, true, 28, EffectType.BUFF, null));
            mg.player.BuffsDebuffEffects.add(new Effect_ArrayBased(1900000, -30, true, 26, EffectType.BUFF, null));
            active = true;
            resource.playSoundFromSounds(0);
            actualCoolDown = 0;
        } else if (active && actualCoolDown >= totalCoolDown) {
            synchronized (mg.player.BuffsDebuffEffects) {
                mg.player.BuffsDebuffEffects.removeIf(effect -> effect.rest_duration > 50000 || effect instanceof BUF_PowerSurge);
                mg.player.manaBarrier = mg.player.maxMana;
                mg.player.updateEquippedItems();
                active = false;
                resource.playSoundFromSounds(1);
                actualCoolDown = 0;
            }
        }
    }
}

