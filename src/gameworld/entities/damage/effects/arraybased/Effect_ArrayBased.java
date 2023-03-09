package gameworld.entities.damage.effects.arraybased;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;

public class Effect_ArrayBased extends Effect {

    protected boolean activated;

    public Effect_ArrayBased(float duration, float amount, boolean fromPlayer, int effectIndexAffected, EffectType effecttype, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        this.indexAffected = effectIndexAffected;
        this.effectType = effecttype;
    }

    @Override
    public void tick(ENTITY entity) {
        if (!activated) {
            activated = true;
            entity.updateEquippedItems();
        }
        rest_duration--;
    }

    @Override
    public Effect clone() {
        return new Effect_ArrayBased(full_duration, amount, fromPlayer, indexAffected, effectType, sourceProjectile);
    }
}

