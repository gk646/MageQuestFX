package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.buffs.BUF_RegenAura;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.SKILL;

public class SKL_RegenAura extends SKILL {


    public SKL_RegenAura(MainGame mg) {
        super(mg);
        this.totalCoolDown = 3_600;
        this.actualCoolDown = 3_600;
        castTimeTotal = 50;
        manaCost = 10;
        type = DamageType.Arcane;
        icon = setup("regenAura");
        castTimeActive = 0;
        name = "Regenerative Aura";
        description = "Regenerative Aura creates a soothing aura around the character, stimulating the natural healing processes of the body and allowing them to gradually recover from any wounds or injuries sustained in battle.";
    }

    @Override
    public void drawToolTip(GraphicsContext gc, int startX, int startY) {
        if (toolTipTimer > 30) {
            gc.setFont(FonT.editUndo19);
            gc.setFill(Colors.LightGrey);
            gc.fillRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.238), startY - (MainGame.SCREEN_HEIGHT * 0.334), MainGame.SCREEN_HEIGHT * 0.231, MainGame.SCREEN_HEIGHT * 0.324f, 15, 15);
            //OUTLINE
            gc.drawImage(fancy, startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.324));
            gc.setLineWidth(1);
            setStrokeTypeColor(gc);
            gc.strokeRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.235), startY - (MainGame.SCREEN_HEIGHT * 0.331), MainGame.SCREEN_HEIGHT * 0.225, MainGame.SCREEN_HEIGHT * 0.318f, 15, 15);
            //NAME
            setTypeColor(gc);
            drawCenteredTextToolTip(gc, name, (float) (startY - (MainGame.SCREEN_HEIGHT * 0.306)), (int) (startX - (MainGame.SCREEN_HEIGHT * 0.238)));
            gc.setFont(FonT.minecraftBoldItalic14);

            gc.setFill(Colors.Blue);
            gc.fillText("Mana Cost: " + mg.player.getManaCost(manaCost), startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.275));
            gc.setFill(Colors.darkBackground);
            gc.fillText("CD: " + df.format(totalCoolDown / 60.0f) + "s", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.225));

            gc.fillText("Heal:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.205));
            //gc.fillText("Soul Siphon:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.160));
            gc.setFont(FonT.minecraftItalic14);
            int y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.190));
            for (String string : Dialog.insertNewLine("Heals 2% of your max-health every second for " + Math.round((450 + 4.5 * Player.playerEffects[3]) / 60 * 10.0f) / 10.0f + " seconds.", 23).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.21), y1);
                y1 += 14;
            }

/*
            y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.145));
            for (String string : Dialog.insertNewLine("Makes all enemies vulnerable to dark magic damage by 30%.", 23).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.21), y1);
                y1 += 14;
            }

 */

            gc.setFont(FonT.minecraftItalic12);
            int y = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.11));
            for (String string : Dialog.insertNewLine(description, 37).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.228), y += 12);
            }
        } else {
            toolTipTimer++;
        }
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCastBar(gc);
        drawIcon(gc, x, y);
        drawCooldown(gc, x, y);
    }

    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    @Override
    public void activate() {
        if (checkForActivationCasting(3)) {
            mg.player.BuffsDebuffEffects.add(new BUF_RegenAura(450, 2, 1, true, null));
        }
    }
}
