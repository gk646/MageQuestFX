package gameworld.entities.damage.effects;

abstract public class Debuff_Effect extends Effect {

    public Debuff_Effect(float duration, float amount, boolean fromPlayer) {
        super(duration, amount, fromPlayer);
    }
}
