package gameworld.quest;

public enum Type {
    Grunt(1), Shooter(2), Mixed(3), BOSS_Slime(4), Skeleton_Spear(5);
    private final int value;

    Type(int val) {
        value = val;
    }
}