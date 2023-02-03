package main.system.ui.talentpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Rectangle;
import java.util.Objects;

class TalentNode {
    public final Rectangle boundBox;
    private final int TALENT_SIZE = 45;
    private final TALENT talent;
    private final Image nodeImage;
    public int toolTipTimer;
    int pointsSpent;


    TalentNode(TALENT talent, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, TALENT_SIZE, TALENT_SIZE);
        this.nodeImage = setup();
        this.talent = talent;
    }


    public void drawNode(GraphicsContext gc, int x, int y, int slotSize) {
        gc.drawImage(nodeImage, x, y, slotSize, slotSize);
        talent.drawIcon(gc, x + slotSize / 2 - 8, y + slotSize / 2 - 8, slotSize);
    }

    private Image setup() {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/ui/talents/talentnode.png"))));
    }
}


