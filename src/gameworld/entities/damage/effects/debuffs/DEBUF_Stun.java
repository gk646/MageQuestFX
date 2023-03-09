package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;


public class DEBUF_Stun extends Effect {
    /**
     *
     */
    public DEBUF_Stun(float durationInTicks, float amount, int tickRate, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        super(durationInTicks, amount, fromPlayer, sourceProjectile);
        name = "Stunned!";
        description = "Unable to do anything for" + amount;
        icon = setupPicture("health_regen");
        this.effectType = EffectType.DEBUFF;
    }

    /**
     *
     */
    @Override
    public void tick(ENTITY entity) {
        entity.stunned = rest_duration > 1;
        rest_duration--;
    }

    @Override
    public Effect clone() {
        return new DEBUF_Stun(full_duration, amount, tickRate, fromPlayer, sourceProjectile);
    }
}

