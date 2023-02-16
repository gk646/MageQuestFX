package main.system.enums;

public enum GameMapType {
    MapCover(0), NoMapCover(1);

    private final int value;

    GameMapType(int val) {
        value = val;
    }
}
