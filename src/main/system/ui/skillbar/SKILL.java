package main.system.ui.skillbar;

import gameworld.entities.damage.DamageType;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.Effects;

import java.util.Objects;

abstract public class SKILL {

    protected Image icon;
    protected final MainGame mg;
    protected Effects[] procEffects = new Effects[5];
    public float totalCoolDown;
    protected int manaCost;
    public float coolDownCoefficient;
    public DamageType type;
    public float damage;
    protected int castTimeTotal;
    protected int castTimeActive;
    private int side1;
    private int side2;
    private int side3;
    private int side4;
    private int side5;
    public String imagePath;
    protected float actualCoolDown;
    protected int i_id;
    protected String name;
    protected String description;


    public SKILL(MainGame mg) {
        this.mg = mg;
    }

    /**
     * used for drawing the skill icon and the cooldown overlay
     *
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    abstract public void draw(GraphicsContext gc, int x, int y);


    protected void drawIcon(GraphicsContext gc, int x, int y) {
        gc.drawImage(icon, x, y);
    }

    protected void drawCooldown(GraphicsContext gc, int skillBarX, int skillBarY) {
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

    protected void drawCastBar(GraphicsContext gc) {
        if (castTimeActive > 0) {
            gc.setLineWidth(2);
            gc.setFill(Colors.arcane_blue);
            gc.fillRoundRect(Player.screenX - 24, Player.screenY + 60, (castTimeActive / (castTimeTotal * 1.0f)) * 94, 12, 10, 10);
            gc.setStroke(Colors.darkBackground);
            gc.strokeRoundRect(Player.screenX - 24, Player.screenY + 60, 94, 12, 10, 10);
        }
    }

    protected Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/icons/" + imagePath))));
    }

    abstract public void update();

    protected void updateCooldown() {
        if (actualCoolDown < totalCoolDown) {
            actualCoolDown++;
        }
    }

    protected void updateCastTimer() {
        if (castTimeActive > 0) {
            castTimeActive++;
            if (mg.player.isMoving) {
                castTimeActive = 0;
            } else if (castTimeActive >= castTimeTotal) {
                activate();
                castTimeActive = 0;
            }
        }
    }

    abstract public void activate();
}
