package gameworld.tiles;

import main.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    Display display;
    Tile[] tileArray;
    public TileManager (Display display){
        this.display = display;
        this.tileArray = new Tile[5];
        getTileImage();

    }
    private void getTileImage(){
        try {
            tileArray[0] = new Tile(48);
            tileArray[0].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/grass.png")));
            tileArray[1] = new Tile(48);
            tileArray[1].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/wall.png")));
            tileArray[2] = new Tile(48);
            tileArray[2].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/water.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D g2){

        g2.drawImage(tileArray[0].tileImage,0,0,tileArray[0].tileSize,tileArray[0].tileSize,null);
    }

}
