package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.Debuff_Effect;

public class EFT_Burning_I extends Debuff_Effect {


    public EFT_Burning_I(float duration, float amount, boolean fromPlayer, int tickRate) {
        super(duration, amount, fromPlayer);
        this.type = DamageType.FireDMG;
        this.name = "Burning I";
        this.tickRate = tickRate;
        this.description = "The target is burning for " + this.amount + " damage every " + this.tickRate + " Ticks.";
    }

    /**
     * @param entity the receiving entity
     */
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
