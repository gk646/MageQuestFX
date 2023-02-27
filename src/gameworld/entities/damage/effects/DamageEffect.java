package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.player.Player;

public class DamageEffect extends Effect {

    public DamageEffect(float duration, float amount, boolean fromPlayer, DamageType type, int tickRate) {
        super(duration, amount, fromPlayer);
        if (fromPlayer) {
            this.full_duration += (this.full_duration / 100.0f) * Player.playerEffects[5];
            this.amount += (this.amount / 100.0f) * Player.playerEffects[4];
        }
        this.type = type;
        this.tickRate = tickRate;
    }

    @Override
    public void tick(ENTITY entity) {
        if (ticker >= tickRate) {
            ticker = 0;
            if (fromPlayer) {
                entity.getDamageFromPlayer(amount, type);
            } else {
                entity.getDamage(amount);
            }
            entity.hpBarOn = true;
        }
        ticker++;
        rest_duration--;
    }
}
