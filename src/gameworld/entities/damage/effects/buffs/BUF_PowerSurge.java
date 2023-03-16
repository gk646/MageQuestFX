package gameworld.entities.damage.effects.buffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;


public class BUF_PowerSurge extends Effect {
    Player player;

    /**
     * amount is per second!
     *
     * @param duration
     * @param amount
     * @param tickRate
     * @param fromPlayer
     */
    public BUF_PowerSurge(Player player, float duration, float amount, int tickRate, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        this.player = player;
        name = "Power Surge";
        description = "Increases all damage by 15% but decreases maximum mana by 20% and increases mana cost by 30%";
        icon = setupPicture("powerSurge");
        this.tickRate = tickRate;
        this.effectType = EffectType.BUFF;
    }

    /**
     *
     */
    @Override
    public void tick(ENTITY entity) {
        player.manaBarrier = player.maxMana * 0.8f;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return new BUF_RegenAura(full_duration, amount, tickRate, fromPlayer, sourceProjectile);
    }
}


