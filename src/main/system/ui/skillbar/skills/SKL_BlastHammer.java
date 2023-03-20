package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.FIRE.PRJ_BlastHammer;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.SKILL;


public class SKL_BlastHammer extends SKILL {


    public SKL_BlastHammer(MainGame mg) {
        super(mg);
        this.totalCoolDown = 1200;
        actualCoolDown = totalCoolDown;
        this.coolDownCoefficient = 0;
        this.icon = setup("blastHammer");
        type = DamageType.Fire;
        name = "Blast Hammer";
        manaCost = 50;
        weapon_damage_percent = 1500;
        i_id = 18;
        description = "Blast Hammer: Unleash the fury of the forge as you summon a colossal, blazing hammer to strike down your foes. Channeling the raw power of fire, the Blast Hammer crushes your enemies with searing force, dealing massive area damage and leaving a trail of smoldering embers in its wake.";
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
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
            gc.fillText("DMG:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.255));
            int y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.255));
            for (String string : Dialog.insertNewLine(this.toString(), 22).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.19), y1);
                y1 += 14;
            }
            gc.setFill(Colors.Blue);
            gc.fillText("Mana Cost: " + mg.player.getManaCost(manaCost), startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.275));
            gc.setFill(Colors.darkBackground);
            gc.fillText("CD: " + df.format(totalCoolDown / 60.0f) + "s", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.225));

            gc.fillText("Shatter:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.205));
            //gc.fillText("Soul Siphon:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.160));
            gc.setFont(FonT.minecraftItalic14);
            y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.190));
            for (String string : Dialog.insertNewLine("Leaves the ground scorching after impact burning enemies within.", 23).split("\n")) {
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
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void activate() {
        if (checkForActivation(2)) {
            mg.PROJECTILES.add(new PRJ_BlastHammer(weapon_damage_percent));
        }
    }
}


