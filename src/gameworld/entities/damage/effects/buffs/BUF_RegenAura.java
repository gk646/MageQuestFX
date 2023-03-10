package gameworld.entities.damage.effects.buffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;


public class BUF_RegenAura extends Effect {
    /**
     * amount is per second!
     *
     * @param duration
     * @param amount
     * @param tickRate
     * @param fromPlayer
     */
    public BUF_RegenAura(float duration, float amount, int tickRate, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        name = "Regen Aura";
        description = "Heals you for" + amount + "every second";
        icon = setupPicture("health_regen");
        this.tickRate = tickRate;
        this.effectType = EffectType.BUFF;
    }

    /**
     *
     */
    @Override
    public void tick(ENTITY entity) {
        entity.setHealth(entity.getHealth() + amount / full_duration);
        rest_duration--;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return new BUF_RegenAura(full_duration, amount, tickRate, fromPlayer, sourceProjectile);
    }
}

