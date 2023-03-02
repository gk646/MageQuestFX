package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;

public class Buff_Effect extends Effect {
    public final int effectIndexAffected;
    protected boolean activated;

    public Buff_Effect(float duration, float amount, boolean fromPlayer, int effectIndexAffected) {
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

