package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;


public class DEBUF_Stun extends Effect {
    /**
     *
     */
    public DEBUF_Stun(float durationInTicks, float amount, int tickRate, boolean fromPlayer) {
        super(durationInTicks, amount, fromPlayer);
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
}

