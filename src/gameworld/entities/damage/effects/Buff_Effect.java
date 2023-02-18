package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;

abstract public class Buff_Effect extends Effect {


    public Buff_Effect(float duration, float amount, ENTITY giving) {
        this.full_duration = duration;
        this.rest_duration = full_duration;
        this.amount = amount;
    }
}

