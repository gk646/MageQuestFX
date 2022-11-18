package gameworld.tiles;

import java.awt.image.BufferedImage;

public class Tile {
    public Tile(int tileSize){
        this.tileSize = tileSize;
    }
    public BufferedImage tileImage;
    public boolean collision= false;
    public int tileSize;

}
