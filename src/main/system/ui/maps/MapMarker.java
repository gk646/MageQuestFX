package main.system.ui.maps;

public class MapMarker {
    public final MarkerType type;
    public final String name;
    public final int xTile;
    public final int yTile;

    public MapMarker(String name, int xTile, int yTile, MarkerType type) {
        this.name = name;
        this.xTile = xTile;
        this.yTile = yTile;
        this.type = type;
    }
}
