package main.system.ui.talentpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class TalentNode {
    public final Rectangle boundBox;
    private final TALENT talent;
    private final Image nodeImage;
    public int toolTipTimer;
    int pointsSpent;
    public Point position;
    public boolean activated;


    TalentNode(TALENT talent, int xCo, int yCo) {
        this.boundBox = new Rectangle(944 + xCo, 524 + yCo, 32, 32);
        this.nodeImage = setup();
        this.talent = talent;
        this.position = new Point(xCo, yCo);
    }


    public void drawNode(GraphicsContext gc, int x, int y) {
        gc.drawImage(nodeImage, position.x + x, position.y + y);
        talent.drawIcon(gc, position.x + x + 8, position.y + y + 8);
        boundBox.x = position.x + x;
        boundBox.y = position.y + y;
    }

    public void drawHoverOverEffect(GraphicsContext gc, int x, int y) {
        gc.drawImage(nodeImage, position.x + x, position.y + y);
        talent.drawIcon(gc, position.x + x + 8, position.y + y + 8);
        boundBox.x = position.x + x;
        boundBox.y = position.y + y;
    }

    private Image setup() {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/ui/talents/talentnode.png"))));
    }
}


