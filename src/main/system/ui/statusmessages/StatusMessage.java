package main.system.ui.statusmessages;

import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

public class StatusMessage {
    public final GeneralResourceLoader animation = new GeneralResourceLoader("ui/levelup");
    MainGame mg;
    private int spriteCounter;
    private int levelUpCounter = 1000, questComplete = 1000, notEnoughMana = 1000, onCooldown = 1000;

    public StatusMessage(MainGame mg) {
        this.mg = mg;
        animation.loadSound("levelup", "levelup");
    }


    public void draw(GraphicsContext gc) {
        if (levelUpCounter < 860) {
            drawLevelUp(gc);
            levelUpCounter++;
        } else if (questComplete < 480) {
            drawQuestComplete(gc);
            questComplete++;
        } else if (notEnoughMana < 140) {
            drawNotEnoughMana(gc);
            notEnoughMana++;
        } else if (onCooldown < 140) {
            drawOnCooldown(gc);
            onCooldown++;
        }
        spriteCounter++;
    }


    private void drawLevelUp(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold30);
        gc.setFill(Colors.darkBackground);
        gc.fillText("LEVEL UP", Player.screenX - 60, Player.screenY - 150);
        switch (Math.abs(spriteCounter / 12 % (2 * 6 - 2) - 6 + 1)) {
            case 0 -> gc.drawImage(animation.images1.get(0), Player.screenX - 118 + 24, Player.screenY - 150);
            case 1 -> gc.drawImage(animation.images1.get(1), Player.screenX - 118 + 24, Player.screenY - 150);
            case 2 -> gc.drawImage(animation.images1.get(2), Player.screenX - 118 + 24, Player.screenY - 150);
            case 3 -> gc.drawImage(animation.images1.get(3), Player.screenX - 118 + 24, Player.screenY - 150);
            case 4 -> gc.drawImage(animation.images1.get(4), Player.screenX - 118 + 24, Player.screenY - 150);
            case 5 -> gc.drawImage(animation.images1.get(5), Player.screenX - 118 + 24, Player.screenY - 150);
        }
    }

    private void drawQuestComplete(GraphicsContext gc) {
        gc.setFont(FonT.antParty30);
        gc.setFill(Colors.questNameBeige);
        String text = "Quest Completed:";
        mg.ui.drawCenteredText(gc, text, MainGame.SCREEN_HEIGHT / 2.0f - 420);
        gc.setFont(FonT.antParty20);
        text = mg.player.lastQuest;
        mg.ui.drawCenteredText(gc, text, MainGame.SCREEN_HEIGHT / 2.0f - 380);
    }

    private void drawNotEnoughMana(GraphicsContext gc) {
        gc.setFont(FonT.antParty20);
        gc.setFill(Colors.Red);
        mg.ui.drawCenteredText(gc, "Not enough Mana!", 150);
    }

    private void drawOnCooldown(GraphicsContext gc) {
        gc.setFont(FonT.antParty20);
        gc.setFill(Colors.Red);
        mg.ui.drawCenteredText(gc, "Ability on Cooldown!", 150);
    }

    public void setLevelUpTrue() {
        levelUpCounter = 0;
    }

    public void setNotEnoughManaTrue() {
        notEnoughMana = 0;
    }

    public void setQuestCompleteTrue() {
        questComplete = 0;
    }

    public void setOnCooldownTrue() {
        onCooldown = 0;
    }
}
