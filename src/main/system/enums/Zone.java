package main.system.enums;

public enum Zone {
    Tutorial(0), GrassLands(1), City1(2), Dungeon_Tutorial(3);

    private final int value;

    Zone(int val) {
        value = val;
    }
}
