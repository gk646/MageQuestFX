package main.system.enums;

public enum Zone {
    Tutorial(0), GrassLands(1), City1(2), Dungeon_Tutorial(3), Clearing(4);

    private final int value;

    Zone(int val) {
        value = val;
    }

    public boolean isDungeon() {
        return value == 3;
    }

    public boolean isForest() {
        return value == 1 || value == 0;
    }
}
