package gameworld.world;

import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;

public class MiniMap {
    private final MainGame mg;
    private final int pixelSize = 5;
    private final Color grey = new Color(124, 121, 121);
    private final Color green = new Color(101, 203, 101);
    private final Color blue = new Color(44, 109, 239);

    public MiniMap(MainGame mg) {
        this.mg = mg;
    }

    public void draw(Graphics2D g2) {
        int xTile = (mg.player.worldX + 24) / 48;
        int yTile = (mg.player.worldY + 24) / 48;
        g2.setColor(Color.black);
        g2.drawLine(1699, 24, 1899, 24);
        g2.drawLine(1699, 24, 1699, 225);
        g2.drawLine(1899, 225, 1699, 225);
        g2.drawLine(1900, 225, 1900, 24);
        for (int y = 0; y < 40 * 5; y += 5) {
            for (int x = 0; x < 40 * 5; x += 5) {
                int xTileOffset = xTile - 40 / 2 + x / 5;
                int yTileOffset = yTile - 40 / 2 + y / 5;
                if (xTileOffset > 0 && yTileOffset > 0 && mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    //System.out.println(yTile - height / 2 + y / 5 + " " + (xTile - width / 2 + x / 5));
                    g2.setColor(grey);
                    g2.fillRect(1700 + x, 25 + y, 5, 5);
                } else {
                    g2.setColor(green);
                    g2.fillRect(1700 + x, 25 + y, 5, 5);
                }
                if (x == 100 && y == 100) {
                    g2.setColor(blue);
                    g2.fillRect(1700 + x, 25 + y, 5, 5);
                }
            }
        }
    }
}

