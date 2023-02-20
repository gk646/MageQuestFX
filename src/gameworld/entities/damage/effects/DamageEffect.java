package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;

public class DamageEffect extends Effect {

    public DamageEffect(float duration, float amount, boolean fromPlayer, DamageType type, int tickRate) {
        super(duration, amount, fromPlayer);
        this.type = type;
        this.tickRate = tickRate;
    }

    @Override
    public void tick(ENTITY entity) {
        ticker++;
        rest_duration--;
        if (ticker >= tickRate) {
            ticker = 0;
            if (fromPlayer) {
                entity.getDamageFromPlayer(amount, type);
            } else {
                entity.getDamage(amount);
            }
            entity.hpBarOn = true;
        }
    }
}
