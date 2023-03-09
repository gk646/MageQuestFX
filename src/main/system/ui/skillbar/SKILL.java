package main.system.ui.skillbar;

import gameworld.entities.damage.DamageType;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.Effects;
import main.system.ui.FonT;
import main.system.ui.skillbar.skills.SKL_Filler;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.Objects;

abstract public class SKILL {
    private final DecimalFormat df = new DecimalFormat("#.##");

    public Image icon;
    protected final MainGame mg;
    protected Effects[] procEffects = new Effects[3];
    public float totalCoolDown;
    protected int manaCost;
    public float coolDownCoefficient;
    public DamageType type;
    public float weapon_damage_percent;
    protected int castTimeTotal;
    protected int castTimeActive;
    public final Rectangle hitBox = new Rectangle(53, 53);
    public String imagePath;
    public float actualCoolDown;
    protected int i_id;
    private final Image skillSlot = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/slot.png")));
    private final Image fancy = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/tooltip_fancy.png")));
    protected String description;
    public String name;
    public int toolTipTimer = 0;

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


    public void drawIcon(GraphicsContext gc, int x, int y) {
        gc.drawImage(icon, x, y);
    }

    protected void drawCooldown(GraphicsContext gc, int skillBarX, int skillBarY) {
        int side1;
        int side2;
        int side3;
        int side4;
        int side5;
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

    public void drawSkillSlot(GraphicsContext gc, int x, int y) {
        gc.drawImage(skillSlot, x, y);
        gc.drawImage(icon, x + 7, y + 7);
    }

    protected Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/icons/" + imagePath + ".png"))));
    }

    abstract public void update();

    protected void updateCooldown() {
        if (actualCoolDown < totalCoolDown) {
            actualCoolDown++;
        }
    }

    protected boolean checkForActivation(int animationNumber) {
        if (actualCoolDown >= totalCoolDown && mg.player.getMana() >= mg.player.getManaCost(manaCost)) {
            mg.player.loseMana(manaCost);
            actualCoolDown = 0;
            mg.player.playCastAnimation(animationNumber);
            return true;
        }
        return false;
    }

    protected boolean checkForActivationCasting(int animationNumber) {
        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.getMana() >= mg.player.getManaCost(manaCost)) {
            castTimeActive++;
            mg.player.playCastAnimation(animationNumber);
        }
        if (castTimeActive == castTimeTotal) {
            mg.player.loseMana(manaCost);
            castTimeActive = 0;
            actualCoolDown = 0;
            return true;
        }
        return false;
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

    public void drawToolTip(GraphicsContext gc, int startX, int startY) {
        if (this instanceof SKL_Filler) {
            return;
        }
        if (toolTipTimer > 30) {
            gc.setFont(FonT.editUndo19);
            gc.setFill(Colors.LightGrey);
            gc.fillRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.238), startY - (MainGame.SCREEN_HEIGHT * 0.234), MainGame.SCREEN_HEIGHT * 0.231, MainGame.SCREEN_HEIGHT * 0.224f, 15, 15);
            //OUTLINE
            gc.drawImage(fancy, startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.224));
            gc.setLineWidth(1);
            setStrokeTypeColor(gc);
            gc.strokeRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.235), startY - (MainGame.SCREEN_HEIGHT * 0.231), MainGame.SCREEN_HEIGHT * 0.225, MainGame.SCREEN_HEIGHT * 0.218f, 15, 15);
            //NAME
            setTypeColor(gc);
            drawCenteredTextToolTip(gc, name, (float) (startY - (MainGame.SCREEN_HEIGHT * 0.206)), (int) (startX - (MainGame.SCREEN_HEIGHT * 0.238)));
            gc.setFont(FonT.minecraftBoldItalic14);
            gc.fillText("DMG: " + this, startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.145));
            gc.setFill(Colors.Blue);
            gc.fillText("Mana Cost: " + mg.player.getManaCost(manaCost), startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.165));
            gc.setFill(Colors.darkBackground);
            gc.fillText("CD: " + df.format(totalCoolDown / 60.0f) + "s", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.125));
            gc.setFont(FonT.minecraftItalic11);
            int y = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.120));
            for (String string : Dialog.insertNewLine(description, 40).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.228), y += 12);
            }
        } else {
            toolTipTimer++;
        }
    }

    private void setTypeColor(GraphicsContext gc) {
        if (type == DamageType.Fire) {
            gc.setFill(Colors.fire_red);
        } else if (type == DamageType.Arcane) {
            gc.setFill(Colors.arcane_blue);
        } else if (type == DamageType.DarkMagic) {
            gc.setFill(Colors.dark_magic_purple);
        } else if (type == DamageType.Poison) {
            gc.setFill(Colors.poison_green);
        } else {
            gc.setFill(Colors.darkBackground);
        }
    }

    private void setStrokeTypeColor(GraphicsContext gc) {
        if (type == DamageType.Fire) {
            gc.setStroke(Colors.fire_red);
        } else if (type == DamageType.Arcane) {
            gc.setStroke(Colors.arcane_blue);
        } else if (type == DamageType.DarkMagic) {
            gc.setStroke(Colors.dark_magic_purple);
        } else if (type == DamageType.Poison) {
            gc.setStroke(Colors.poison_green);
        } else {
            gc.setStroke(Colors.darkBackground);
        }
    }

    private void drawCenteredTextToolTip(GraphicsContext gc, String text, float y, int offsetx) {
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double x = (MainGame.SCREEN_HEIGHT * 0.23 - textWidth) / 2 + offsetx;
        gc.fillText(text, x, y);
    }

    @Override
    public String toString() {
        float flat_damage = weapon_damage_percent;
        switch (type) {
            case DarkMagic -> flat_damage += (flat_damage / 100.0f) * mg.player.effects[2];
            case Fire -> flat_damage += (flat_damage / 100.0f) * mg.player.effects[19];
            case Arcane -> flat_damage += (flat_damage / 100.0f) * mg.player.effects[1];
            case Poison -> flat_damage += (flat_damage / 100.0f) * mg.player.effects[18];
            case Ice -> flat_damage += (flat_damage / 100.0f) * mg.player.effects[28];
        }
        return weapon_damage_percent + "% Weapon Damage as" + type;
    }
}
