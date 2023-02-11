package gameworld.dialogue;

public enum Trigger {
    SINGULAR(1), SPREAD_Random(2), SPREAD_Circle(3);
    private final int value;

    Trigger(int val) {
        value = val;
    }
}
