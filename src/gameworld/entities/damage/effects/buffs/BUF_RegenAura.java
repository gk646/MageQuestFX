package gameworld.entities.damage.effects.buffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;


public class BUF_RegenAura extends Effect {
    /**
     * amount is per second!
     *
     * @param duration
     * @param amount
     * @param tickRate
     * @param fromPlayer
     */
    public BUF_RegenAura(float duration, float amount, int tickRate, boolean fromPlayer) {
        super(duration, amount, fromPlayer);
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
        entity.setHealth(entity.getHealth() + amount / 60.0f);
        rest_duration--;
    }
}

