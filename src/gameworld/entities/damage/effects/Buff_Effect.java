package gameworld.entities.damage.effects;

abstract public class Buff_Effect extends Effect {


    public Buff_Effect(float duration, float amount, boolean fromPlayer) {
        super(duration, amount, fromPlayer);
        this.full_duration = duration;
        this.rest_duration = full_duration;
        this.amount = amount;
    }
}

