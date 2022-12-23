package main.system.ui.talentpane;

import gameworld.Item;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TalentNode {
    private final int TALENT_SIZE = 45;
    public Rectangle boundBox;
    public int toolTipTimer;
    public Talent talent;
    int pointsSpent;
    String name;
    BufferedImage nodeImage;
    private BufferedImage talentIcon;

    TalentNode(Item item, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, TALENT_SIZE, TALENT_SIZE);
        this.nodeImage = setup();
    }


    public void drawNode(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(talentIcon, x, y, slotSize, slotSize, null);
        talent.drawIcon(g2, x + slotSize / 2 - 8, y + slotSize / 2 - 8, slotSize);
    }


    public void drawNode(Graphics2D g2, int startX, int startY) {
        g2.drawRoundRect(startX, startY, TALENT_SIZE, TALENT_SIZE, 20, 20);

    }

    public BufferedImage setup() {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/ui/talents/talentnode.png"))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}


