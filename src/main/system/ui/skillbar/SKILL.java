package main.system.ui.skillbar;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;

import java.util.Objects;

abstract public class SKILL {

    protected Image icon;
    public final MainGame mg;
    public float totalCoolDown;
    public float coolDownCoefficient;
    /*
       Posion = 0
       Fire = 1
       Ice = 2
       Lightning = 3
       Arcane = 4
       Dark = 5
     */
    public int type;
    public int side1, side2, side3, side4, side5;
    public String imagePath;
    public float actualCoolDown;
    private int i_id;
    private String name;
    private String description;

    public SKILL(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc, int x, int y) {

    }

    public void drawIcon(GraphicsContext gc, int x, int y) {
        gc.drawImage(icon, x, y);
    }

    public void drawCooldown(GraphicsContext gc, int skillBarX, int skillBarY) {
        if (actualCoolDown != totalCoolDown) {
            coolDownCoefficient = (actualCoolDown * (200.0f / totalCoolDown));
            side1 = 25;
            side2 = 0;
            side3 = 0;
            side4 = 0;
            side5 = 0;
        } else {
            coolDownCoefficient = 0;
            side1 = 100;
            side2 = 110;
            side3 = 110;
            side4 = 111;
            side5 = 110;
        }
        if (coolDownCoefficient > 0) {
            side1 = (int) (side1 + coolDownCoefficient);
        }
        if (coolDownCoefficient > 25) {
            side2 = (int) (side2 + (coolDownCoefficient - 25));
        }
        if (coolDownCoefficient > 75) {
            side3 = (int) (side3 + (coolDownCoefficient - 75));
        }
        if (coolDownCoefficient > 125) {
            side4 = (int) (side4 + (coolDownCoefficient - 125));
        }
        if (coolDownCoefficient > 175) {
            side5 = (int) (side5 + (coolDownCoefficient - 175));
        }
        gc.setStroke(Colors.LightGreyTransparent);
        for (int i = side1; i <= 50; i++) {
            gc.strokeLine(skillBarX + 25, skillBarY + 25, skillBarX + i, skillBarY);
        }
        for (int i = side2; i <= 50; i++) {
            gc.strokeLine(skillBarX + 25, skillBarY + 25, skillBarX + 50, skillBarY + i);
        }
        for (int i = side3; i <= 50; i++) {
            gc.strokeLine(skillBarX + 25, skillBarY + 25, skillBarX + 50 - i, skillBarY + 50);
        }
        for (int i = side4; i <= 50; i++) {
            gc.strokeLine(skillBarX + 25, skillBarY + 25, skillBarX, skillBarY + 50 - i);
        }
        for (int i = side5; i <= 25; i++) {
            gc.strokeLine(skillBarX + 25, skillBarY + 25, skillBarX + i, skillBarY);
        }
    }

    protected Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/" + imagePath))));
    }

    abstract public void update();


    abstract public void activate();
}
