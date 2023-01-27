package main.system.ui.talentpane;

import main.system.ImageSetup;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class TalentNode {
    private final int TALENT_SIZE = 45;
    public Rectangle boundBox;
    public int toolTipTimer;
    private final Talent talent;
    int pointsSpent;
    String name;
    private final BufferedImage nodeImage;


    TalentNode(Talent talent, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, TALENT_SIZE, TALENT_SIZE);
        this.nodeImage = setup();
        this.talent = talent;
    }


    public void drawNode(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(nodeImage, x, y, slotSize, slotSize, null);
        talent.drawIcon(g2, x + slotSize / 2 - 8, y + slotSize / 2 - 8, slotSize);
    }

    private BufferedImage setup() {
        ImageSetup imageSetup = new ImageSetup();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/ui/talents/talentnode.png"))));
            scaledImage = imageSetup.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}


