package main.system.ui.maps;

public class MapMarker {
    public MarkerType type;
    public String name;
    public int xTile, yTile;

    public MapMarker(String name, int xTile, int yTile, MarkerType type) {
        this.name = name;
        this.xTile = xTile;
        this.yTile = yTile;
        this.type = type;
    }
}
