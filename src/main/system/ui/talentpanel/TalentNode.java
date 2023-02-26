package main.system.ui.talentpanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Point;
import java.awt.Rectangle;

public class TalentNode {
    public Rectangle boundBox;
    public final TALENT talent;
    public final Point position;
    public boolean activated;
    public final int id;
    public int size = 0;


    public TalentNode(TALENT talent, int xCo, int yCo, int size, int activated) {
        this.size = size;
        if (size == 0) {
            this.boundBox = new Rectangle(944 + xCo, 524 + yCo, 32, 32);
        } else if (size == 2) {
            this.boundBox = new Rectangle(938 + xCo, 518 + yCo, 45, 45);
        } else if (size == 1) {
            this.boundBox = new Rectangle(935 + xCo, 515 + yCo, 50, 50);
        }

        this.talent = talent;
        this.position = new Point(xCo, yCo);
        this.id = talent.i_id;
        this.activated = activated == 1;
    }


    public void drawNode(GraphicsContext gc, int x, int y, Image image) {
        int drawx = position.x + x;
        int drawy = position.y + y;
        gc.drawImage(image, drawx, drawy);
        if (size == 1) {
            talent.drawIcon(gc, drawx + 8, drawy + 8);
        } else if (size == 2) {
            talent.drawIcon(gc, drawx + 6, drawy + 6);
        } else {
            talent.drawIcon(gc, drawx + 8, drawy + 8);
        }
        boundBox.x = drawx;
        boundBox.y = drawy;
    }
}


