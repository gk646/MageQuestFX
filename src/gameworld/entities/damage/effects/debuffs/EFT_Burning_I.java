package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.Debuff_Effect;

public class EFT_Burning_I extends Debuff_Effect {


    public EFT_Burning_I(float amount, float duration, boolean fromPlayer) {
        super(amount, duration, fromPlayer);
        this.type = DamageType.FireDMG;
        this.name = "Burning I";
        this.tickRate = 60;
        this.description = "The target is burning for " + this.amount + " damage every " + this.tickRate + " Ticks.";
    }

    /**
     * @param entity the receiving entity
     */
    @Override
    public void tick(ENTITY entity) {
        this.ticker++;
        if (ticker == tickRate) {
            ticker = 0;
            if (fromPlayer) {
                entity.getDamageFromPlayer(amount, type);
            } else {
                entity.getDamage(amount);
            }
        }
    }
}
