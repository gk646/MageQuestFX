package gameworld.dialogue;

public enum Type {
    Grunt(1), Shooter(2), Mixed(3);
    public final int value;

    Type(int val) {
        value = val;
    }
}