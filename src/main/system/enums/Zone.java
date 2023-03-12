package main.system.enums;

public enum Zone {
    Woodland_Edge(0), DeadPlains(0), GrassLands(1), City1(2), Ruin_Dungeon(3), Hillcrest(4), Treasure_Cave(3), Hillcrest_Mountain_Cave(4), TheGrove(5), TestRoom(5);
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

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
