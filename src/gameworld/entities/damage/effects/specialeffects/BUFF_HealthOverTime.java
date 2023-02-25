package gameworld.entities.damage.effects.specialeffects;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Buff_Effect;

public class BUFF_HealthOverTime extends Buff_Effect {
    public BUFF_HealthOverTime(float duration, float amount, boolean fromPlayer, int effectIndexAffected) {
        super(duration, amount, fromPlayer, effectIndexAffected);
    }

    @Override
    public void tick(ENTITY entity) {
        entity.setHealth(entity.getHealth()+amount);
    }
}
