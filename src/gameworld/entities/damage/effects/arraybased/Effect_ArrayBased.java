package gameworld.entities.damage.effects.arraybased;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;

public class Effect_ArrayBased extends Effect {
    public final int effectIndexAffected;
    protected boolean activated;

    public Effect_ArrayBased(float duration, float amount, boolean fromPlayer, int effectIndexAffected, String name, String description) {
        super(duration, amount, fromPlayer);
        this.effectIndexAffected = effectIndexAffected;
    }

    @Override
    public void tick(ENTITY entity) {
        if (!activated) {
            activated = true;
            entity.updateEquippedItems();
        }
        rest_duration--;
    }
}

